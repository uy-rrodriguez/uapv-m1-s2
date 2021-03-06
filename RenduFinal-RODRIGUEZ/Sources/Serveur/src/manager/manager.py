# coding: utf8

import sys, traceback, time, json
import Ice
import AppMP3Player
import config, reconnaissance, parsing, traitement
from utils import *


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
        print_("ManagerI->commandeVocale")

        # Reconnaissance
        r = reconnaissance.Reconnaissance()
        phrase = r.reconnaitreVoix(parole)

        # Parsing
        p = parsing.Parsing()
        commande = p.parsingPhrase(phrase)

        # Traitement, on lui passe le Métaserveur
        t = traitement.Traitement(self.metaserveur)
        commande = t.traiterCommande(self.getClientIP(current), commande)

        print_("ManagerI->return\n")
        return commande


    '''
        Reception et traitement des commandes vocales émulées avec une liste de phrases du côté Android.
    '''
    def creerCommandeErreur(self, message):
        c = AppMP3Player.Commande()
        c.commande = ""
        c.erreur = True
        c.msgErreur = message
        c.retour = json.dumps(False)
        return c

    def commandePhrase(self, phrase, current=None):
        print_("ManagerI->commandePhrase")

        # Parsing
        p = parsing.Parsing()
        commande = p.parsingPhrase(phrase)

        if commande == parsing.ERR_COMMANDE_INCORRECTE:
            print_("ManagerI->Erreur de parsing : Commande incorrecte")
            commande = self.creerCommandeErreur("Commande incorrecte : " + phrase)
            
        elif commande == parsing.ERR_COMMANDE_NON_RECONNUE:
            print_("ManagerI->Erreur de parsing : Impossible de reconnaitre la commande")
            commande = self.creerCommandeErreur("Impossible de reconnaitre la commande : " + phrase)    
            
        else:
            print_("ManagerI->Commande reconnue : " + commande.commande)
        
            # Traitement, on lui passe le Métaserveur
            t = traitement.Traitement(self.metaserveur)
            commande = t.traiterCommande(self.getClientIP(current), commande)

        print_("ManagerI->return")
        return commande


    '''
        Reception et traitement des commandes manuelles.
    '''
    def commandeManuelle(self, commande, current=None):
        print_("ManagerI->commandeManuelle")

        # Traitement, on lui passe le Métaserveur
        t = traitement.Traitement(self.metaserveur)
        commande = t.traiterCommande(self.getClientIP(current), commande)

        print_("ManagerI->return\n")
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
    sys.exit(main())
