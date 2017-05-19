# coding: utf8

import sys, traceback, time, json, string
import Ice, IceStorm
import AppMP3Player
import config
from publisher_commandes import PublisherCommandes
from publisher_commandes import PublisherTwoWayCommandes
from subscriber_chansons import SubscriberChansons
from utils import *


'''
    Cette classe implémente le servant du Metaserveur.
    Il va recevoir et traiter les demandes provenant du serveur Manager et les envoyer
    aux miniserveurs si nécessaire.
'''
class MetaserveurI(AppMP3Player.Metaserveur):

    def __init__(self):
        # Liste globale de chansons
        # La structure correspondra a un dictionnaire ayant comme cles les noms des chansons et
        # et comme valeur une liste avec les objets Chanson
        self.chansons = {}

        # Dictionnaire avec la derniere adresse de streaming par client
        self.dictStreaming = {}

        
    def set_proxies(self, publisherCommandes, subscriberChansons):
        self.publisherCommandes = publisherCommandes
        self.subscriberChansons = subscriberChansons



    # ####################################################################################### #
    #                                                                                         #
    #  Ci-dessous: Méthodes appelées par les publishers et subscribers afin d'accéder         #
    #              a des données communes                                                     #
    #                                                                                         #
    # ####################################################################################### #

    ''' ------------------------------------------------------------------------
        Reception de l'adresse a renvoyer au client concernant son streaming
        ------------------------------------------------------------------------
    '''
    def notifier_adresse_streaming(self, ipClient, ip, port):
        self.dictStreaming[ipClient] = {"ip": ip, "port": port}


    ''' ------------------------------------------------------------------------
        Set la liste de chansons pour un miniserveur
            ATTENTION: on ne gere pas le multithreading, le liste de chansons peut
            donc etre accedee pendant qu'on la modifie.
        ------------------------------------------------------------------------
    '''
    def set_chansons(self, miniserveur, chansons):
        # D'abord, on supprime toutes les chansons du miniserveur
        self.remove_chansons(miniserveur)
        # Puis on ajoute les chansons
        for c in chansons:
            if c.nom in self.chansons:
                self.chansons[c.nom].append(c)
            else:
                self.chansons[c.nom] = [c]


    ''' ------------------------------------------------------------------------
        Supprimer toutes les chansons d'un miniserveur
        ------------------------------------------------------------------------
    '''
    def remove_chansons(self, miniserveur):
        suppr = []
        cSuppr = []
        for nom, liste in self.chansons.iteritems():
            for c in liste:
                if c.miniserveur == miniserveur:
                    liste.remove(c)
            if len(liste) == 0:
                suppr.append(nom)
        
        # Suppression de listes vides
        for nom in suppr:
            del self.chansons[nom]


    ''' ------------------------------------------------------------------------
        La structure de chansons contient un dictionnaire avec une liste par
        chaque nom de chanson. Avant de retourner une chanson de la liste, on
        va deplaer cet element a la fin. Comme ca, si jamais on a plusieurs
        miniserveurs, on peut faire en sorte de gerer la charge, en renvoyant
        au client des chansons appartenant a des miniserveurs differents chaque
        fois.
        
        On retourne le premier element de la liste avant le "shift".
        ------------------------------------------------------------------------
    '''
    def get_premier_puis_shift(self, liste):
        c = liste[0]
        if len(liste) > 1:
            liste = liste[1:]
        liste.append(c)
        return c
    
    ''' ------------------------------------------------------------------------
        Chercher une chanson par le nom
            1) Si on trouve une chanson avec le nom exacte, on retourne celle-là.
            2) Sinon, on retourne celle dont son nom contient le string cherché.
            3) Si plusieurs chansons contiennent le string cherché, on retourne
                celle dont la différence entre les noms est minimale (pour cela
                on regarde les longueurs des noms).
            Pour simplifier la recherche par nom, tous les espaces et caractères
            spéciaux sont supprimés avant de faire la comparaison.
        ------------------------------------------------------------------------
    '''
    def nettoyer_nom(self, nom):
        nom = str.lower(nom)
        nomPropre = str()
        for c in nom:
            if c in string.ascii_lowercase or c in string.digits:
                nomPropre += c;
        return nomPropre

    def get_chanson_by_nom(self, nomCherche):
        nomCherchePropre = self.nettoyer_nom(nomCherche)
        minDiff = None
        plusProche = None
        
        for nom, liste in self.chansons.iteritems():
            nomPropre = self.nettoyer_nom(nom)
            
            if nomPropre == nomCherchePropre:
                # Shift des chansons
                return self.get_premier_puis_shift(liste)
                
            elif nomCherchePropre in nomPropre or nomPropre in nomCherchePropre:
                diff = abs(len(nomCherchePropre) - len(nomPropre))
                if minDiff == None or diff < minDiff:
                    minDiff = diff
                    plusProche = self.get_premier_puis_shift(liste)
                
        return plusProche


    ''' ------------------------------------------------------------------------
        Transfomrer la liste de chansons dans un dictionnaire Python
        ------------------------------------------------------------------------
    '''
    def get_chansons_dict(self):
        try:
            # Conversion de Chanson a dict Python
            chansonsDict = []
            for nom, liste in self.chansons.iteritems():
                c = liste[0]
                chansonsDict.append({
                        "nom"         : c.nom,
                        "artiste"     : c.artiste,
                        "categorie"   : c.categorie
                    })
            return chansonsDict
        except Exception as e:
            print_exc_()
            raise e



    # ####################################################################################### #
    #                                                                                         #
    #  Ci-dessous: Méthodes qui implementent l'interface Ice du Métaserveur                   #
    #                                                                                         #
    # ####################################################################################### #

    ''' ------------------------------------------------------------------------
        Traitement des commandes, appel a des methodes specifiques
        ------------------------------------------------------------------------
    '''
    def traiterCommande(self, ipClient, commande, current=None):
        #print_("MetaserveurI->traiterCommande :", ipClient, commande.commande, ";".join(commande.params))

        try:
            # On stocke l'ip du client concerné dans un attribut local pour pouvoir
            # y faire référence depuis les différentes actions
            self.ipClientActuel = ipClient

            # Appel à l'action correspondante
            action = getattr(self, commande.commande)
            res = action(commande.params)

            # Traitement du resultat
            commande.retour = json.dumps(res)
            commande.erreur = False
            commande.msgErreur = ""

        except AttributeError as e:
            commande.erreur = True
            commande.msgErreur = "La commande " + commande.commande + " n'existe pas"

        except ValueError as e:
            commande.erreur = True
            commande.msgErreur = e.message

        except Exception as e:
            commande.erreur = True
            commande.msgErreur = e.message
            print_exc_()

        return commande #Commande


    ''' ------------------------------------------------------------------------
        Lister toutes les chansons
        ------------------------------------------------------------------------
    '''
    def to_list(self, *args):
        print_("MetaserveurI->to_list")
        return self.get_chansons_dict()


    ''' ------------------------------------------------------------------------
        recherche de chansons
        ------------------------------------------------------------------------
    '''
    def search(self, *args):
        print_("MetaserveurI->search :", args[0])
        return self.get_chansons_dict()


    ''' ------------------------------------------------------------------------
        Jouer une chanson en streaming
        ------------------------------------------------------------------------
    '''
    def play(self, *args):
        print_("MetaserveurI->play :", args[0])

        c = self.get_chanson_by_nom(args[0][0])
        if c is None:
            raise ValueError("La chanson '" + self.nettoyer_nom(args[0][0]) + "' n'existe pas")

        self.publisherCommandes.jouerChanson(self.ipClientActuel, c)

        # Apres de lancer le streaming, il faut attendre l'adresse de celui-ci
        # Le client viendra la demander
        self.notifier_adresse_streaming(self.ipClientActuel, None, None)

        return True


    ''' ------------------------------------------------------------------------
        Retourner l'adresse du streaming
        ------------------------------------------------------------------------
    '''
    def stream_addr(self, *args):
        print_("MetaserveurI->stream_addr")
        if self.ipClientActuel not in self.dictStreaming:
            return False
            
        dataStream = self.dictStreaming[self.ipClientActuel]

        if dataStream["ip"] == None:
            return False
        else:
            addr = config.STREAM_PROTOCOL + "://" + dataStream["ip"] + ":" + str(dataStream["port"])
            del self.dictStreaming[self.ipClientActuel]
            return addr


    ''' ------------------------------------------------------------------------
        Mettre en pause le streaming actuel
        ------------------------------------------------------------------------
    '''
    def pause(self, *args):
        print_("Metaserveur->pause")
        # Envoi de message au miniserveur concerné
        self.publisherCommandes.pauseChanson(self.ipClientActuel)
        return True


    ''' ------------------------------------------------------------------------
        Arreter le streaming actuel
        ------------------------------------------------------------------------
    '''
    def stop(self, *args):
        print_("Metaserveur->stop")
        # Envoi de message au miniserveur concerné
        self.publisherCommandes.arreterChanson(self.ipClientActuel)
        return True



''' =======================================================================================
    Main :
        Création d'un souscripteur au TopicChansons et du servant pour le Metaserveur
    =======================================================================================
'''
def main():
    status = 0
    ic = None
    try:
        # Configuration de Ice. Soit on le fait ici, soit dans un fichier de configuration
        # On va configurer un souscripteur
        props = Ice.createProperties(sys.argv)
        #props.setProperty("TopicChansons.Subscriber.Endpoints", "tcp:udp")
        iniData = Ice.InitializationData()
        iniData.properties = props
        ic = Ice.initialize(iniData)


        # Création du servant pour le Métaserveur
        adapter = ic.createObjectAdapterWithEndpoints("Metaserveur", "default -p " + str(config.PORT_ICE_METASERVEUR))
        meta = MetaserveurI()
        adapter.add(meta, ic.stringToIdentity("Metaserveur"))
        adapter.activate()


        # Création du publisher qui enverra les commandes aux miniserveurs
        publisherCommandes = PublisherCommandes(ic, meta)

        # Création du souscripteur au TopicChansons pour recevoir les mises-a-jour
        # des listes de chansons existantes dans les miniserveurs
        subscriberChansons = SubscriberChansons(ic, meta)


        # On assigne à l'instance du Métaserveur tous les publishers et subscribers
        meta.set_proxies(publisherCommandes, subscriberChansons)


        # Finalement on reste à l'écoute de messages entrant
        #ic.waitForShutdown()

        # Boucle qui va demander la liste de chansons toutes les X secondes
        while True:
            publisherCommandes.listerChansons()
            time.sleep(config.META_PERIODE_DEMANDER_CHANSONS)


    except (KeyboardInterrupt, SystemExit):
        pass

    except:
        print_exc_()
        status = 1

    if ic:
        # Clean up
        try:
            ic.destroy()
        except:
            print_exc_()
            status = 1

    sys.exit(status)


if __name__ == "__main__":
    sys.exit(main())
