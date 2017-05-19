# coding: utf8

import sys, traceback, time, json, string
import AppMP3Player
import config
from utils import *


ERR_COMMANDE_INCORRECTE = -1
ERR_COMMANDE_NON_RECONNUE = -2

SYNONYMES = {
    config.COM_PLAY : ["play", "jouer", "joues", "jouez", "joue", "ecouter", "ecoutes", "ecoutez", "ecoute", "lire", "lis", "lit", "lisez"],
    config.COM_STOP : ["stop", "arreter", "arretes", "arretez", "arrete"],
    config.COM_LIST : ["list", "lister", "listes", "listez", "liste", "afficher", "affiches", "affichez", "affiche", "listing", "listings"]
}
class Parsing:
    syn = {}

    def __init__(self):
        # On gere un dictionnaire de synonymes. Par exemple, pour la commande
        # play on peut envoyer "play", "jouer", "joue"
        for commande, listeSyn in SYNONYMES.iteritems():
            for mot in listeSyn:
                self.syn[mot] = commande


    def nettoyer_phrase(self, phrase):
        phrase = str.lower(phrase)
        phrasePropre = str()
        for c in phrase:
            if c in string.ascii_lowercase or c in string.digits or c == ' ':
                phrasePropre += c;
        return phrasePropre
        
    def parsingPhrase(self, phrase):
        print_("Parsing->parsingPhrase", self.nettoyer_phrase(phrase))

        # Nettoyage
        phrase = self.nettoyer_phrase(phrase)
        
        # Parsing
        phraseParts = str.lower(phrase).split(" ")
        
        # Premier contrôle, on s'attend à une pharse avec plus d'un mot
        if len(phraseParts) <= 1:
            return ERR_COMMANDE_INCORRECTE
        
        # On cherche le premier mot reconnu comme une commande
        commande = None
        i = 0
        for i in range(len(phraseParts)):
            mot = phraseParts[i]
            if mot in self.syn:
                commande = self.syn[mot]
                break

        if commande != None:
            # Si la commande reconnue est le dernier mot, on se dit que le nom de la chanson
            # est au début
            if i == (len(phraseParts) - 1):
                param = " ".join(phraseParts[:i])
            else:
                param = " ".join(phraseParts[i+1:])

            # Création de Commande
            c = AppMP3Player.Commande()
            c.commande = commande
            c.erreur = False
            c.msgErreur = ""
            c.retour = json.dumps(True)

            if param is not None:
                c.params = [param]
            else:
                c.params = []

            return c # Commande
            
        else:
            return ERR_COMMANDE_NON_RECONNUE
