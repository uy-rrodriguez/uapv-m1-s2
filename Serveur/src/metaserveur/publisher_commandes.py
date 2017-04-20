# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils


'''
    Ce Publisher IceStorm enverra des messages au topic TopicCommandes
    pour communiquer aux miniserveurs le demarrage et arret des chansons.
'''
class PublisherCommandes(icestormutils.Publisher):

    def __init__(self, ic, metaserveur):
        super(PublisherCommandes, self).__init__(ic, "TopicCommandes")
        self.manager = AppMP3Player.TopicCommandesManagerPrx.uncheckedCast(self.publisher);

        self.metaserveur = metaserveur


    def listerChansons(self):
        try:
            #print "PublisherCommandes->listerChansons"
            self.manager.listerChansons()
        except:
            traceback.print_exc()


    def jouerChanson(self, ipClient, nomChanson):
        try:
            print "PublisherCommandes->jouerChanson : ", ipClient, nomChanson
            c = self.metaserveur.get_chanson_by_nom(nomChanson)
            if (c is not None):
                self.manager.jouerChanson(ipClient, c)
            else:
                raise RuntimeException("La chanson '" + nomChanson + "' n'existe pas")

        except:
            traceback.print_exc()

    def pauseChanson(self, ipClient):
        try:
            print "PublisherCommandes->pauseChanson : ", ipClient
            self.manager.pauseChanson(ipClient)
        except:
            traceback.print_exc()

    def arreterChanson(self, ipClient):
        try:
            print "PublisherCommandes->arreterChanson : ", ipClient
            self.manager.arreterChanson(ipClient)
        except:
            traceback.print_exc()
