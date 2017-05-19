# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils
from utils import *


'''
    Cette classe implémente l'interface qui va gérer le TopicCommandes.
    À chaque fois que le subscriber IceStorm associé au topic reçoit
    un nouveau message, on fera automatiquement appel aux méthodes de
    cette classe.
'''
class TopicChansonsManagerI(AppMP3Player.TopicChansonsManager):

    def __init__(self, metaserveur):
        self.metaserveur = metaserveur


    def listerChansons(self, miniserveur, chansons, current=None):
        #print_("SubscriberChansons->listerChansons")
        self.metaserveur.set_chansons(miniserveur, chansons)


    def adresseStreaming(self, ipClient, ip, port, current=None):
        #print_("SubscriberChansons->listerChansons")
        self.metaserveur.notifier_adresse_streaming(ipClient, ip, port)


'''
    Classe qui implémente un souscripteur du topic TopicChansons
'''
class SubscriberChansons(icestormutils.Subscriber):
    def __init__(self, ic, metaserveur):
        super(SubscriberChansons, self).__init__(ic, "TopicChansons",
                                                 "TopicChansons_Metaserveur",
                                                 TopicChansonsManagerI(metaserveur))

