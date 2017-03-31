# coding: utf8

import sys, traceback, time
import Ice, IceStorm
import AppMP3Player
import config
from publisher_commandes import PublisherCommandes
from subscriber_chansons import SubscriberChansons
from subscriber_mini import SubscriberMiniserveurs


'''
    Cette classe implémente le servant su Metaserveur.
    Il va recevoir et traiter les demandes provenant du serveur Manager et les envoyer
    aux miniserveurs si nécessaire.
'''
class MetaserveurI(AppMP3Player.Metaserveur):
    def __init__(self):
        pass

    def traiterCommande(self, ipClient, commande, current=None):
        print "MetaserveurI->traiterCommande"
        return commande #Commande


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

        # Création du publisher qui enverra les commandes aux miniserveurs
        publisherCommandes = PublisherCommandes(ic)

        # Création du souscripteur au TopicChansons
        subscriberChansons = SubscriberChansons(ic)

        # Création du souscripteur au TopicMiniserveurs
        subscriberMini = SubscriberMiniserveurs(ic)

        # Création du servant pour le Métaserveur
        adapter = ic.createObjectAdapterWithEndpoints("Metaserveur", "default -p " + str(config.PORT_ICE))
        meta = MetaserveurI()
        adapter.add(meta, ic.stringToIdentity("Metaserveur"))
        adapter.activate()

        # Finalement on reste à l'écoute de messages entrant
        ic.waitForShutdown()


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
