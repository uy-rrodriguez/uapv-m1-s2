# coding: utf8

import sys, traceback, time, subprocess, random, string
import Ice, IceStorm
import AppMP3Player
import config
from publisher_chansons import PublisherChansons
from subscriber_commandes import SubscriberCommandes
from utils import *

#import CLI

def main(argv=None):
    if argv is None:
        argv = sys.argv

    status = 0
    ic = None
    nom = ""
    path = "miniserveur"

    try:
        # Lors du lancement du Miniserveur, on genere un nom aleatoire
        ip = get_ip()
        #nom = ip + "_" + "".join(random.choice(string.ascii_uppercase + string.digits) for _ in range(8))
        nom = argv[1]
        path = argv[2]
        print_("Miniserveur-> start", nom, path)

        # Configuration Ice
        props = Ice.createProperties([])
        iniData = Ice.InitializationData()
        iniData.properties = props
        ic = Ice.initialize(iniData)


        # Création du publisher pour envoyer les mises-à-jour des chansons
        #processDemon = subprocess.Popen("python -m miniserveur.demon " + nom)
        
        # ==> NE PLUS ACTIVER LE DEMON. Le metaserveur se charge de demander la liste de chansons

        # Création du publisher pour indiquer au Métaserveur les mises-à-jour dans la liste de chansons
        publisherChansons = PublisherChansons(ic, nom)

        # Création du subscriber pour recevoir les commandes provenantes du Métaserveur
        subscriberCommandes = SubscriberCommandes(ic, publisherChansons, nom, path)


        # Client graphique (TODO)
        #client = CLI.App(serveur)
        #client.mainloop()

        #processDemon.wait()
        ic.waitForShutdown()


    except (KeyboardInterrupt, SystemExit):
        pass

    except:
        print_exc_()
        status = 1


    #if processDemon:
    #    try:
    #        processDemon.kill()
    #    except:
    #        print_exc_()
    #        status = 1

    # Clean up
    if ic:
        try:
            ic.destroy()
        except:
            print_exc_()
            status = 1


    print_("Miniserveur-> end", nom)
    sys.exit(status)


if __name__ == "__main__":
    sys.exit(main())
