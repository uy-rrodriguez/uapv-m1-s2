# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils
from utils import *


'''
    Ce Publisher IceStorm enverra des messages au topic TopicCommandes
    pour communiquer aux miniserveurs le demarrage et arret des chansons.
'''
class PublisherCommandes(icestormutils.Publisher):

    def __init__(self, ic, metaserveur):
        super(PublisherCommandes, self).__init__(ic, "TopicCommandes")
        self.topicManager = AppMP3Player.TopicCommandesManagerPrx.uncheckedCast(self.publisher);

        self.metaserveur = metaserveur


    def listerChansons(self):
        try:
            #print_("PublisherCommandes->listerChansons")
            self.topicManager.listerChansons()
        except:
            print_exc_()


    def jouerChanson(self, ipClient, chanson):
        print_("PublisherCommandes->jouerChanson : ", ipClient, chanson.nom)
        self.topicManager.jouerChanson(ipClient, chanson)
        return True


    def pauseChanson(self, ipClient):
        try:
            print_("PublisherCommandes->pauseChanson : ", ipClient)
            self.topicManager.pauseChanson(ipClient)
        except:
            print_exc_()

    def arreterChanson(self, ipClient):
        try:
            print_("PublisherCommandes->arreterChanson : ", ipClient)
            self.topicManager.arreterChanson(ipClient)
        except:
            print_exc_()



########################################################################################################
########################################################################################################


'''
    Ce Publisher IceStorm peut envoyer des messages et attendre la reponse.
'''
class PublisherTwoWayCommandes(icestormutils.PublisherTwoWay):

    def __init__(self, ic, metaserveur):
        super(PublisherTwoWayCommandes, self).__init__(ic, "TopicCommandes")
        self.topicManager = AppMP3Player.TopicCommandesManagerPrx.uncheckedCast(self.publisher);

        self.metaserveur = metaserveur


    def jouerChanson(self, ipClient, chanson):
        print_("PublisherTwoWayCommandes->jouerChanson : ", ipClient, chanson.nom)
        adresseStream = self.topicManager.jouerChanson(ipClient, chanson)
        return adresseStream
