# coding: utf8

import sys, traceback, json
import AppMP3Player
import config

class Traitement:
    def __init__(self):
        pass

    def traiterCommande(self, ipClient, commande):
        print "Traitement->traiterCommande :", commande.commande, ";".join(commande.params)

        try:
            # Appel à l'action correspondante
            action = getattr(self, commande.commande)
            res = action(commande.params)

            # Traitement du resultat
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


    def play(self, *args):
        print "Traitement->play :", args[0]
        return True


    def pause(self, *args):
        print "Traitement->pause"
        return True

    def stop(self, *args):
        print "Traitement->stop"
        return True

    def search(self, *args):
        print "Traitement->search :", args[0]
        return True

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
