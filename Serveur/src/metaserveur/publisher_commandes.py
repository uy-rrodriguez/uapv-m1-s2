# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils


'''
    Ce Publisher IceStorm enverra des messages au topic TopicCommandes
    pour communiquer avec les miniserveurs.
'''
class PublisherCommandes(icestormutils.Publisher):

    def __init__(self, ic):
        super(PublisherCommandes, self).__init__(ic, "TopicCommandes")
        self.manager = AppMP3Player.TopicMiniserveursManagerPrx.uncheckedCast(self.publisher);

    def jouer_chanson(self):
        try:
            print "PublisherCommandes->jouer_chanson"
            c = AppMP3Player.Chanson()
            c.nom = "Bidon"
            self.manager.jouerChanson(c)
        except:
            traceback.print_exc()

    def pause_chanson(self):
        try:
            print "PublisherCommandes->pause_chanson"
            c = AppMP3Player.Chanson()
            c.nom = "Bidon"
            self.manager.pauseChanson(c)
        except:
            traceback.print_exc()

    def arreter_chanson(self):
        try:
            print "PublisherCommandes->arreter_chanson"
            c = AppMP3Player.Chanson()
            c.nom = "Bidon"
            self.manager.arreterChanson(c)
        except:
            traceback.print_exc()
