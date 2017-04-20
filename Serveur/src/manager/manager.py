# coding: utf8

import sys, traceback, time
import Ice
import AppMP3Player
import config, reconnaissance, parsing, traitement


class ManagerI(AppMP3Player.Manager):
    def __init__(self, metaserveur):
        self.metaserveur = metaserveur


    '''
        Recuperation de l'IP du client
    '''
    def getClientIP(self, current): # current = Ice.Current
        #String currentStr = current.con.toString();
        #String regex = ".*remote address = ([0-9.]+):[0-9]+";
        #Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        #Matcher m = p.matcher(currentStr);
        #
        #if (m.find()):
        #    return m.group(1);
        #else:
        #    return "Inconnu";
        return "127.0.0.1"


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

        # Traitement, on lui passe le Métaserveur
        t = traitement.Traitement(self.metaserveur)
        commande = t.traiterCommande(self.getClientIP(current), commande)

        print "ManagerI->return\n"
        return commande


    '''
        Reception et traitement des commandes manuelles.
    '''
    def commandeManuelle(self, commande, current=None):
        print "ManagerI->commandeManuelle"

        # Traitement, on lui passe le Métaserveur
        t = traitement.Traitement(self.metaserveur)
        commande = t.traiterCommande(self.getClientIP(current), commande)

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

        # On récupere un proxy pour accéder au Métaserveur (le module de Traitement
        # va lui envoyer les demandes pour lister et jouer des chansons)
        base = ic.stringToProxy("Metaserveur:default -h " + config.IP_METASERVEUR + " -p " + str(config.PORT_ICE_METASERVEUR))
        meta = AppMP3Player.MetaserveurPrx.checkedCast(base)
        if not meta:
            raise RuntimeError("Invalid proxy Metaserveur")


        # Puis, on va créer le servant pour le Manager et on va lui passer le proxy du Métaserveur
        adapter = ic.createObjectAdapterWithEndpoints("Manager", "default -p " + str(config.PORT_ICE_MANAGER))

        manager = ManagerI(meta)

        adapter.add(manager, ic.stringToIdentity("Manager"))
        adapter.activate()

        # Et on reste a l'écoute des messages entrant
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
