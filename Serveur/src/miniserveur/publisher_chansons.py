# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils

'''
    Ce Publisher IceStorm enverra des messages au topic TopicMiniserveurs
    pour notifier le Métaserveur du démarrage ou arrêt d'un miniserveur.
'''
class PublisherChansons(icestormutils.Publisher):

    def __init__(self, ic):
        super(PublisherChansons, self).__init__(ic, "TopicChansons")
        self.manager = AppMP3Player.TopicChansonsManagerPrx.uncheckedCast(self.publisher)


    def listerChansons(self, chansons):
        try:
            #print "PublisherChansons->listerChansons"
            self.manager.listerChansons(chansons)

        except:
            sys.print_exc()


    def ajouterChanson(self, nom, artiste, categorie, path):
        try:
            print "PublisherChansons->ajouterChanson :", nom, path
            c = AppMP3Player.Chanson()
            c.nom = nom
            c.artiste = artiste
            c.categorie = categorie
            c.path = path
            self.manager.ajouterChanson(c)

        except:
            sys.print_exc()


    def supprimerChanson(self, nom):
        try:
            print "PublisherChansons->supprimerChanson :", nom
            c = AppMP3Player.Chanson()
            c.nom = nom
            self.manager.supprimerChanson(c)
        except:
            sys.print_exc()
