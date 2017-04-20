# coding: utf8

import sys, traceback, time, json
import Ice, IceStorm
import AppMP3Player
import config
from publisher_commandes import PublisherCommandes
from subscriber_chansons import SubscriberChansons
from subscriber_mini import SubscriberMiniserveurs


'''
    Cette classe implémente le servant du Metaserveur.
    Il va recevoir et traiter les demandes provenant du serveur Manager et les envoyer
    aux miniserveurs si nécessaire.
'''
class MetaserveurI(AppMP3Player.Metaserveur):

    def __init__(self):
        # Liste globale de chansons
        self.chansons = []


    def set_proxies(self, publisherCommandes, subscriberChansons, subscriberMini):
        self.publisherCommandes = publisherCommandes
        self.subscriberChansons = subscriberChansons
        self.subscriberMini = subscriberMini


    '''
        Méthodes appelées par les publishers et subscribers afin d'accéder aux données communes
    '''

    def set_chansons(self, chansons):
        self.chansons = chansons

    def add_chanson(self, chanson):
        self.chansons.append(chanson)

    def remove_chanson(self, chanson):
        suppr = None
        for c in self.chansons:
            if (c.path == chanson.path):
                suppr = c
        if (suppr is not None):
            self.chansons.remove(suppr)

    def get_chanson_by_nom(self, nomChanson):
        for c in self.chansons:
            if c.nom == nomChanson:
                return c
        return None


    '''
        Méthodes qui implementen l'intarface Ice du Métaserveur
    '''
    def traiterCommande(self, ipClient, commande, current=None):
        print "MetaserveurI->traiterCommande :", ipClient, commande.commande, ";".join(commande.params)

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
            traceback.print_exc()

        except Exception as e:
            commande.erreur = True
            commande.msgErreur = e.message
            traceback.print_exc()

        return commande #Commande



    def get_chansons_dict(self):
        # Conversion de Chanson a dict Python
        chansonsDict = []
        for c in self.chansons:
            chansonsDict.append({
                    "nom"         : c.nom,
                    "artiste"     : c.artiste,
                    "categorie"   : c.categorie,
                    "path"        : c.path
                })
        return chansonsDict


    def to_list(self, *args):
        print "Metaserveur->to_list"
        return self.get_chansons_dict()


    def search(self, *args):
        print "Metaserveur->search :", args[0]
        return self.get_chansons_dict()


    def play(self, *args):
        print "Metaserveur->play :", args[0]
        # Envoi de message au miniserveur concerné
        self.publisherCommandes.jouerChanson(self.ipClientActuel, args[0][0])
        return True


    def pause(self, *args):
        print "Metaserveur->pause"
        # Envoi de message au miniserveur concerné
        self.publisherCommandes.pauseChanson(self.ipClientActuel)
        return True

    def stop(self, *args):
        print "Metaserveur->stop"
        # Envoi de message au miniserveur concerné
        self.publisherCommandes.arreterChanson(self.ipClientActuel)
        return True


'''
    Main
    Création d'un souscripteur au TopicChansons et du servant pour le Metaserveur
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

        # Création du souscripteur au TopicMiniserveurs
        subscriberMini = SubscriberMiniserveurs(ic)


        # On assigne à l'instance du Métaserveur tous les publishers et subscribers
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
        traceback.print_exc()
        status = 1

    if ic:
        # Clean up
        try:
            ic.destroy()
        except:
            traceback.print_exc()
            status = 1

    sys.exit(status)


if __name__ == "__main__":
    main()
