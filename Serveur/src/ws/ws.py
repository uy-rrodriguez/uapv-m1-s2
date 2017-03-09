# coding: utf8

import sys, traceback, Ice
import AppMP3Player
import web


# ############################################################### #
#    Configuration                                                #
#                                                                 #
# ############################################################### #

SERVER_IP = "localhost"


# Formats d'URLs acceptés
urls = (
    "/die",                          "Suicide",
    "/commande_vocale/(.*)",         "CommandeVocale",
    "/commande_manuelle/(.*)/(.*)",  "CommandeManuelle"
)

# Application qui attendra les requêtes
app = web.application(urls, globals())



# ############################################################### #
#    Classe pour stocker le proxy du manager et pouvoir           #
#    l'accéder depuis les classes du webservice.                  #
#                                                                 #
# ############################################################### #

class ManagerProxy:
    proxy = None

    @staticmethod
    def set_proxy(proxy):
        ManagerProxy.proxy = proxy



# ############################################################### #
#    Classes pour traiter les requêtes                            #
#                                                                 #
# ############################################################### #


# -- CommandeVocale --------------------------------------------- #

'''
    Traitement d'une commande vocale.
    On va recevoir l'enregistrement audio en forme de string.
'''
class CommandeVocale:
    def GET(self, strParole):
        try:
            if (ManagerProxy.proxy and ManagerProxy.proxy != None):
                parole = AppMP3Player.Parole()
                parole.data = []
                return ManagerProxy.proxy.commandeVocale(parole)

            else:
                return ManagerProxy.proxy #return "Erreur de connection au serveur Manager"

        except:
            return sys.exc_info()


# -- CommandeManuelle ------------------------------------------- #

'''
    Traitement d'une commande manuelle.
    On va recevoir la commande et le nom du fichier audio correspondant.
'''
class CommandeManuelle:
    def GET(self, commande, chanson):
        try:
            if (ManagerProxy.proxy and ManagerProxy.proxy != None):
                # Création de l'objet Commande
                c = AppMP3Player.Commande()
                c.commande = commande
                c.params = [chanson]
                return ManagerProxy.proxy.commandeManuelle(c)

            else:
                return "Erreur de connection au serveur Manager"

        except:
            return sys.exc_info()


# -- Suicide ---------------------------------------------------- #

'''
    Classe pour arrêter le serveur à distance.
    (ceci est extrêmement insecure mais tellement utile pour les test...)
'''
class Suicide:
    def GET(self):
        app.stop()



# ############################################################### #
#    Main                                                         #
#                                                                 #
# ############################################################### #

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
        base = ic.stringToProxy("Manager:default -h " + SERVER_IP + " -p 10000")
        manager = AppMP3Player.ManagerPrx.checkedCast(base)
        if not manager:
            raise RuntimeError("Invalid proxy")


        # On stocke le proxy
        ManagerProxy.set_proxy(manager)

        # Démarrage du webservice
        app.run()


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
