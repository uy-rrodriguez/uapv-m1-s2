# coding: utf8

import sys, traceback, json
import Ice
import AppMP3Player
import web
import config


# ############################################################### #
#    Configuration                                                #
#                                                                 #
# ############################################################### #



# Formats d'URLs acceptés
urls = (
    "/vocale/(.*)",         "CommandeVocale",
    "/manuelle/(.*)/(.*)",  "CommandeManuelle",
    "/(.*)",                "NotFound"
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
        web.header('Content-Type', 'application/json')

        reponse = {"erreur" : True,
                   "msgErreur" : "Erreur inconnue",
                   "commande" : "", "retour" : ""}

        try:
            if (ManagerProxy.proxy and ManagerProxy.proxy != None):
                parole = AppMP3Player.Parole()
                parole.data = []

                # Appel
                c = ManagerProxy.proxy.commandeVocale(parole)

                # Reponse
                reponse = {}
                reponse["commande"] = c.commande
                reponse["erreur"] = c.erreur
                reponse["msgErreur"] = c.msgErreur
                reponse["retour"] = c.retour

            else:
                reponse["msgErreur"] = "Erreur de connection au serveur Manager"

            return json.dumps(reponse)

        except Exception, e:
            traceback.print_exc()

            reponse = {"erreur" : True,
                       "msgErreur" : "%s" % e,
                       "commande" : "", "retour" : ""}
            return json.dumps(reponse)


# -- CommandeManuelle ------------------------------------------- #

'''
    Traitement d'une commande manuelle.
    On va recevoir la commande et le nom du fichier audio correspondant.
'''
class CommandeManuelle:
    def GET(self, commande, chanson):
        web.header('Content-Type', 'application/json')

        reponse = {"erreur" : True,
                   "msgErreur" : "Erreur inconnue",
                   "commande" : "", "retour" : ""}

        try:
            if (ManagerProxy.proxy and ManagerProxy.proxy != None):
                # Création de l'objet Commande
                c = AppMP3Player.Commande()
                c.commande = commande
                c.params = [chanson]

                # Appel
                c = ManagerProxy.proxy.commandeManuelle(c)

                # Reponse
                reponse = {}
                reponse["commande"] = c.commande
                reponse["erreur"] = c.erreur
                reponse["msgErreur"] = c.msgErreur
                reponse["retour"] = c.retour

            else:
                reponse["msgErreur"] = "Erreur de connection au serveur Manager"

            return json.dumps(reponse)

        except Exception, e:
            traceback.print_exc()

            reponse = {"erreur" : True,
                       "msgErreur" : "%s" % e,
                       "commande" : "", "retour" : ""}
            return json.dumps(reponse)


# -- NotFound --------------------------------------------- #

'''
    Traitement d'une URL qui ne correspond a aucune autre regle.
'''
class NotFound:
    def reponse(self):
        reponse = {"erreur" : True,
                   "msgErreur" : "URL incorrecte",
                   "commande" : "", "retour" : ""}
        return json.dumps(reponse)

    def GET(self, data):
        return self.reponse()

    def POST(self, data):
        return self.reponse()



# ############################################################### #
#    Main                                                         #
#                                                                 #
# ############################################################### #

def main():
    status = 0
    ic = None
    try:
        props = Ice.createProperties(sys.argv)
        iniData = Ice.InitializationData()
        iniData.properties = props

        # Initialisation du serveur et Ice
        ic = Ice.initialize(iniData)
        base = ic.stringToProxy("Manager:default -h " + config.IP_MANAGER + " -p " + str(config.PORT_ICE_MANAGER))
        manager = AppMP3Player.ManagerPrx.checkedCast(base)
        if not manager:
            raise RuntimeError("Invalid proxy")


        # On stocke le proxy dans une instance de ManagerProxy
        ManagerProxy(manager)

        # Démarrage du webservice
        instanceWS = MonWebservice(urls, globals())
        instanceWS.run(port=int(config.PORT_WS))


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
