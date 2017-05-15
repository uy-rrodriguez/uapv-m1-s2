# coding: utf8

import sys, traceback, time, json
import AppMP3Player
import config
from utils import *


SYNONYMES = {
    config.COM_PLAY : ["play", "jouer", "joue", "ecouter", "ecoute"],
    config.COM_STOP : ["stop", "arreter", "arrete", "arretes"],
    config.COM_LIST : ["list", "lister", "liste", "listes", "afficher", "affiche", "listing", "listings"]
}
class Parsing:
    syn = {}

    def __init__(self):
        # On gere un dictionnaire de synonymes. Par exemple, pour la commande
        # play on peut envoyer "play", "jouer", "joue"
        for commande, listeSyn in SYNONYMES.iteritems():
            for mot in listeSyn:
                self.syn[mot] = commande


    def parsingPhrase(self, phrase):
        print_("Parsing->parsingPhrase")

        # Parsing
        phraseParts = phrase.split(" ")
        commande = phraseParts[0];
        commande = self.syn[commande]

        param = None
        if len(phraseParts) > 1:
            param = " ".join(phraseParts[1:]);


        # Creation de Commande
        c = AppMP3Player.Commande()
        c.commande = commande
        c.erreur = False
        c.msgErreur = ""
        c.retour = json.dumps(True)

        if param is not None:
            c.params = [param]
        else:
            c.params = []

        return c #Commande
