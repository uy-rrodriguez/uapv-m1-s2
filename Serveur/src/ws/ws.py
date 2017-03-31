# coding: utf8

import sys, traceback, Ice
import AppMP3Player
import web
import config


# ############################################################### #
#    Configuration                                                #
#                                                                 #
# ############################################################### #



# Formats d'URLs acceptés
urls = (
    "/die",                          "Suicide",
    "/commande_vocale/(.*)",         "CommandeVocale",
    "/commande_manuelle/(.*)/(.*)",  "CommandeManuelle"
)

# Variable qui va stocker l'instance du WS (sera de type MonWebservice)
instanceWS = None



# ############################################################### #
#    Classe pour stocker le proxy du manager et pouvoir           #
#    l'accéder depuis les classes du webservice.                  #
#                                                                 #
# ############################################################### #

class ManagerProxy:
    proxy = None

    def __init__(self, proxy):
        ManagerProxy.proxy = proxy



# ############################################################### #
#    Classes pour traiter les requêtes                            #
#                                                                 #
# ############################################################### #


# -- MonWebservice ---------------------------------------------- #
'''
    Hérite de web.application pour étendre ses fonctionnalités
'''
class MonWebservice(web.application, object):
    instance = None

    def __init__(self, urls, vars_globals):
        super(MonWebservice, self).__init__(urls, vars_globals)
        MonWebservice.instance = self

    def run(self, port=8080, *middleware):
        func = self.wsgifunc(*middleware)
        return web.httpserver.runsimple(func, ('0.0.0.0', port))



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
        MonWebservice.instance.stop()



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
        base = ic.stringToProxy("Manager:default -h " + config.IP_MANAGER + " -p " + str(config.PORT_ICE))
        manager = AppMP3Player.ManagerPrx.checkedCast(base)
        if not manager:
            raise RuntimeError("Invalid proxy")


        # On stocke le proxy dans une instance de ManagerProxy
        ManagerProxy(manager)

        # Démarrage du webservice
        instanceWS = MonWebservice(urls, globals())
        instanceWS.run(port=config.PORT_WS)


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
