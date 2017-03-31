# coding: utf8

import sys, traceback, time
import Ice, IceStorm
import AppMP3Player
import config, icestormutils

'''
    Le démon va lire constamment le répertoire configuré pour les chansons afin de
    détecter les ajouts et suppressions.
    Il est aussi un Publisher IceStorm. Il enverra un message au topic TopicChansons
    pour notifier chaque modification du catalogue de musiques.
'''
class DemonChansons(icestormutils.Publisher):
    managerChansons = None

    def __init__(self, ic):
        super(DemonChansons, self).__init__(ic, "TopicChansons")
        self.manager = AppMP3Player.TopicChansonsManagerPrx.uncheckedCast(self.publisher);


    def ajouter_chanson(self, nom, artiste, categorie, path):
        try:
            print "DemonChansons->ajouter_chanson"
            c = AppMP3Player.Chanson()
            c.nom = nom
            c.artiste = artiste
            c.categorie = categorie
            c.path = path
            self.manager.ajouterChanson(c)
        except:
            traceback.print_exc()

    def supprimer_chanson(self, nom):
        try:
            print "DemonChansons->supprimer_chanson"
            c = AppMP3Player.Chanson()
            c.nom = nom
            self.manager.supprimerChanson(c)
        except:
            traceback.print_exc()


    def run(self):
        try:
            print "DemonChansons->run"

            while True:
                self.ajouter_chanson("Hotel California", "The Eagles",
                                     "Rock", "mp3/Hotel_California.mp3")
                time.sleep(1)
                self.supprimer_chanson("Hotel California")
                time.sleep(1)

        except:
            traceback.print_exc()

        print "DemonChansons->end"







