# coding: utf8

import sys, traceback, time, subprocess
import Ice, IceStorm
import AppMP3Player
import config, log
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
        print self.demon.wait()


def main(argv=None):
    if argv is None:
        argv = sys.argv

    status = 0
    ic = None
    processDemon = None

    try:
        props = Ice.createProperties(argv)
        iniData = Ice.InitializationData()
        iniData.properties = props
        ic = Ice.initialize(iniData)

        # Création du publisher pour envoyer les mises-à-jour des chansons
        #processDemon = subprocess.Popen("python -m miniserveur.demon", stdout=open(config.LOG_MINISERVEUR_DEMON, "a"))
        processDemon = subprocess.Popen("python -m miniserveur.demon")

        # Création du publisher pour indiquer au Métaserveur le démarrage ou arrêt du miniserveur
        publisherMiniserveurs = PublisherMiniserveurs(ic)

        # Création du subscriber pour recevoir les commandes provenantes du Métaserveur
        subscriberCommandes = SubscriberCommandes(ic)


        # Client
        #client = CLI.App(serveur)

        # Boucle principale
        #client.mainloop()

        miniserveur = Miniserveur(publisherMiniserveurs, processDemon, subscriberCommandes)
        miniserveur.mainloop()


    except (KeyboardInterrupt, SystemExit):
        pass

    except:
        traceback.print_exc()
        status = 1


    if processDemon:
        try:
            processDemon.kill()
        except:
            traceback.print_exc()
            status = 1

    # Clean up
    if ic:
        try:
            ic.destroy()
        except:
            traceback.print_exc()
            status = 1


    sys.exit(status)


if __name__ == "__main__":
    sys.exit(main())
