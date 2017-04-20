# coding: utf8

import sys, traceback, json
import AppMP3Player
import config


ACTIONS_META = [config.COM_PLAY,
               config.COM_PAUSE,
               config.COM_STOP,
               config.COM_LIST,
               config.COM_SEARCH]


class Traitement:
    def __init__(self, metaserveur):
        self.metaserveur = metaserveur


    def traiterCommande(self, ipClient, commande):
        print "Traitement->traiterCommande :", ipClient, commande.commande, ";".join(commande.params)

        try:
            # Appel à l'action correspondante
            # Quelques actions seront traitées par le Métaserveur
            # D'autres seront traités par d'autres modules (ceci permet une évolution du systeme)

            # Appel Métaserveur
            if (commande.commande in ACTIONS_META):
                commande = self.metaserveur.traiterCommande(ipClient, commande)

            else:
                action = getattr(self, commande.commande)
                res = action(commande.params)

                # Traitement du résultat
                commande.retour = json.dumps(res)
                commande.erreur = False
                commande.msgErreur = ""


        except AttributeError as e:
            commande.erreur = True
            commande.msgErreur = "La commande " + commande.commande + " n'existe pas"
            traceback.print_exc()

        except Exception as e:
            commande.erreur = True
            commande.msgErreur = e.message
            traceback.print_exc()

        return commande #Commande


    def vol_up(self, *args):
        print "Traitement->vol_up"
        return True

    def vol_down(self, *args):
        print "Traitement->vol_down"
        return True

    def silence(self, *args):
        print "Traitement->silence"
        return True

    def fav_add(self, *args):
        print "Traitement->fav_add"
        return True

    def fav_supp(self, *args):
        print "Traitement->fav_supp"
        return True

    def shutdown(self, *args):
        print "Traitement->shutdown"
        return True

    def shutdown_x(self, *args):
        print "Traitement->shutdown_x :", args[0]
        return True
