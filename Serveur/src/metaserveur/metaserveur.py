# coding: utf8

import sys, traceback, time, json, string
import Ice, IceStorm
import AppMP3Player
import config
from publisher_commandes import PublisherCommandes
from publisher_commandes import PublisherTwoWayCommandes
from subscriber_chansons import SubscriberChansons
from subscriber_mini import SubscriberMiniserveurs
from utils import *


'''
    Cette classe implémente le servant du Metaserveur.
    Il va recevoir et traiter les demandes provenant du serveur Manager et les envoyer
    aux miniserveurs si nécessaire.
'''
class MetaserveurI(AppMP3Player.Metaserveur):

    def __init__(self):
        # Liste globale de chansons
        self.chansons = []

        # Dictionnaire avec la derniere adresse de streaming par client
        self.dictStreaming = {}


    #def set_proxies(self, publisherCommandes, subscriberChansons, subscriberMini, publisherTwoWayCommandes):
    def set_proxies(self, publisherCommandes, subscriberChansons, subscriberMini):
        self.publisherCommandes = publisherCommandes
        self.subscriberChansons = subscriberChansons
        self.subscriberMini = subscriberMini

        #self.publisherTwoWayCommandes = publisherTwoWayCommandes



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
        Set la liste de chansons
        ------------------------------------------------------------------------
    '''
    def set_chansons(self, chansons):
        self.chansons = chansons


    ''' ------------------------------------------------------------------------
        Supprimer toutes les chansons d'un miniserveur
        ------------------------------------------------------------------------
    '''
    def remove_chansons(self, nomMiniserveur):
        #suppr = []
        for c in self.chansons:
            if (c.miniserveur == nomMiniserveur):
                #suppr.append(c)
                self.chansons.remove(c)

        #for c in suppr:
        #    self.chansons.remove(c)


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

    def get_chanson_by_nom(self, nomChanson):
        nomPropre = self.nettoyer_nom(nomChanson)
        minDiff = None
        plusProche = None
        
        for c in self.chansons:
            cNomPropre = self.nettoyer_nom(c.nom)
            if cNomPropre == nomPropre:
                return c
            elif nomPropre in cNomPropre or cNomPropre in nomPropre:
                diff = abs(len(cNomPropre) - len(nomPropre))
                if minDiff == None or diff < minDiff:
                    minDiff = diff
                    plusProche = c
                
        return plusProche


    ''' ------------------------------------------------------------------------
        Chercher une chanson par le numero
        ------------------------------------------------------------------------
    '''
    def get_chanson_by_number(self, indexChanson):
        if len(self.chansons) > indexChanson:
            return self.chansons[indexChanson]
        return None


    ''' ------------------------------------------------------------------------
        Transofmrer la liste de chansons dans un dictionnaire Python
        ------------------------------------------------------------------------
    '''
    def get_chansons_dict(self):
        # Conversion de Chanson a dict Python
        chansonsDict = []
        for c in self.chansons:
            chansonsDict.append({
                    "nom"         : c.nom,
                    "artiste"     : c.artiste,
                    "categorie"   : c.categorie,
                    "path"        : c.path,
                    "miniserveur" : str(c.miniserveur)
                })
        return chansonsDict



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

        # Création du publisher two way qui enverra la commande playChanson
        #publisherTwoWayCommandes = PublisherTwoWayCommandes(ic, meta)

        # Création du souscripteur au TopicChansons pour recevoir les mises-a-jour
        # des listes de chansons existantes dans les miniserveurs
        subscriberChansons = SubscriberChansons(ic, meta)

        # Création du souscripteur au TopicMiniserveurs
        subscriberMini = SubscriberMiniserveurs(ic)


        # On assigne à l'instance du Métaserveur tous les publishers et subscribers
        #meta.set_proxies(publisherCommandes, subscriberChansons, subscriberMini, publisherTwoWayCommandes)
        meta.set_proxies(publisherCommandes, subscriberChansons, subscriberMini)


        # Finalement on reste à l'écoute de messages entrant
        #ic.waitForShutdown()

        # Boucle qui va demander la liste de chansons toutes les X secondes
        while True:
            publisherCommandes.listerChansons()
            time.sleep(10)


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
