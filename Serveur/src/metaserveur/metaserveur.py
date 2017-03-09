# coding: utf8

import sys, traceback, time
import Ice
import AppMP3Player


class MetaserveurI(AppMP3Player.Metaserveur):
    def __init__(self):
        pass

    def traiterCommande(self, ipClient, commande, current=None):
        print "MetaserveurI->traiterCommande"
        return commande #Commande

    def enregistrerServeur(self, ipServeur, current=None):
        print "MetaserveurI->enregistrerServeur"
        return 0 #int isServeur

    def supprimerServeur(self, idServeur, current=None):
        print "MetaserveurI->supprimerServeur"
        return #void


def main():
    status = 0
    ic = None
    try:
        props = Ice.createProperties(sys.argv)
        #props.setProperty("Ice.MessageSizeMax", "20480")
        iniData = Ice.InitializationData()
        iniData.properties = props

        # Initialisation du serveur et Ice
        ic = Ice.initialize(iniData)
        adapter = ic.createObjectAdapterWithEndpoints("Metaserveur", "default -p 10000")
        meta = MetaserveurI()
        adapter.add(meta, ic.stringToIdentity("Metaserveur"))
        adapter.activate()
        ic.waitForShutdown()

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
