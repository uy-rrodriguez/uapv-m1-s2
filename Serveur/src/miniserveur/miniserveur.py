# coding: utf8

import sys, traceback, time
import Ice, IceStorm
import AppMP3Player
import config, proxy_metaserveur, demon, subscriber_commandes
#import CLI


class Miniserveur:
    def __init__(self, metaserveur, demon, subscriber):
        self.metaserveur = metaserveur
        self.demon = demon
        self.subscriber = subscriber

    def mainloop(self):
        self.demon.run()


def main():
    metaserveur = None
    publisher = None
    subscriber = None

    status = 0
    ic = None
    try:
        props = Ice.createProperties(sys.argv)
        props.setProperty("TopicChansons.Subscriber.Endpoints", "tcp:udp")
        iniData = Ice.InitializationData()
        iniData.properties = props
        ic = Ice.initialize(iniData)

        # Récupération du métaserveur
        metaserveur = proxy_metaserveur.ProxyMetaserveur(ic)

        # Récupération du publisher pour les chansons
        demonChansons = demon.DemonChansons(ic)

        # Récupération du subscriber pour recevoir les commandes
        subscriber = None#subscriber_commandes.SubscriberCommandes(ic)


        # Client
        #client = CLI.App(serveur)

        # Boucle principale
        #client.mainloop()

        miniserveur = Miniserveur(metaserveur, demonChansons, subscriber)
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
