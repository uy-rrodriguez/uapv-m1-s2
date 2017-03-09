# coding: utf8

import sys, traceback, time
import Ice
import AppMP3Player
import reconnaissance, parsing, traitement


class ManagerI(AppMP3Player.Manager):
    def __init__(self):
        pass

    '''
        Reception et traitement des commandes vocales.
    '''
    def commandeVocale(self, parole, current=None):
        print "ManagerI->commandeVocale"

        # Reconnaissance
        r = reconnaissance.Reconnaissance()
        phrase = r.reconnaitreVoix(parole)

        # Parsing
        p = parsing.Parsing()
        commande = p.parsingPhrase(phrase)

        # Traitement
        t = traitement.Traitement()
        commande = t.traiterCommande("localhost", commande)

        print "ManagerI->return\n"
        return commande


    '''
        Reception et traitement des commandes manuelles.
    '''
    def commandeManuelle(self, commande, current=None):
        print "ManagerI->commandeManuelle"

        # Traitement
        t = traitement.Traitement()
        commande = t.traiterCommande("localhost", commande)

        print "ManagerI->return\n"
        return commande


'''
    Main pour demarrer le serveur Manager
'''
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
        adapter = ic.createObjectAdapterWithEndpoints("Manager", "default -p 10000")
        manager = ManagerI()
        adapter.add(manager, ic.stringToIdentity("Manager"))
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
