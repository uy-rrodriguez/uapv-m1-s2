import sys, traceback, time
import AppMP3Player

class Parsing:
    def __init__(self):
        pass

    def parsingPhrase(self, phrase):
        print "Parsing->parsingPhrase"

        c = AppMP3Player.Commande()
        c.commande = "JOUER"
        c.chanson = "Hotel California"
        c.error = False

        return c #Commande
