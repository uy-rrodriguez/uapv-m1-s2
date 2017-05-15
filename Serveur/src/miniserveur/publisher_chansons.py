# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils
from utils import *


'''
    Ce Publisher IceStorm enverra des messages au topic TopicMiniserveurs
    pour notifier le Métaserveur du démarrage ou arrêt d'un miniserveur.
'''
class PublisherChansons(icestormutils.Publisher):

    def __init__(self, ic, nomMini):
        super(PublisherChansons, self).__init__(ic, "TopicChansons")
        self.nomMini = nomMini
        self.managerTopic = AppMP3Player.TopicChansonsManagerPrx.uncheckedCast(self.publisher)


    def listerChansons(self, chansons):
        try:
            #print_("PublisherChansons->listerChansons")
            self.managerTopic.listerChansons(chansons)

        except:
            print_exc_()

    def adresseStreaming(self, ipClient, ip, port):
        try:
            self.managerTopic.adresseStreaming(ipClient, ip, port)

        except:
            print_exc_()
