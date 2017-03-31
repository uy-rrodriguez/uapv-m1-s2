# coding: utf8

import sys, traceback, time
import Ice, IceStorm
import AppMP3Player
import config
from publisher_mini import PublisherMiniserveurs
from demon import DemonChansons
from subscriber_commandes import SubscriberCommandes

#import CLI


class Miniserveur:
    def __init__(self, publisherMini, demon, subscriberCommandes):
        self.publisherMini = publisherMini
        self.demon = demon
        self.subscriberCommandes = subscriberCommandes

    def mainloop(self):
        self.demon.run()


def main():
    status = 0
    ic = None
    try:
        props = Ice.createProperties(sys.argv)
        props.setProperty("TopicChansons.Subscriber.Endpoints", "tcp:udp")
        iniData = Ice.InitializationData()
        iniData.properties = props
        ic = Ice.initialize(iniData)

        # Création du publisher pour envoyer les mises-à-jour des chansons
        demonChansons = DemonChansons(ic)

        # Création du publisher pour indiquer au Métaserveur le démarrage ou arrêt du miniserveur
        publisherMiniserveurs = None#PublisherMiniserveur(ic)

        # Création du subscriber pour recevoir les commandes provenantes du Métaserveur
        subscriberCommandes = None#SubscriberCommandes(ic)


        # Client
        #client = CLI.App(serveur)

        # Boucle principale
        #client.mainloop()

        miniserveur = Miniserveur(publisherMiniserveurs, demonChansons, subscriberCommandes)
        miniserveur.mainloop()


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
