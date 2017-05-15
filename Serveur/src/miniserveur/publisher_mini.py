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
class PublisherMiniserveurs(icestormutils.Publisher):

    def __init__(self, ic, nomMini):
        super(PublisherMiniserveurs, self).__init__(ic, "TopicMiniserveurs")
        self.nomMini = nomMini
        self.managerTopic = AppMP3Player.TopicMiniserveursManagerPrx.uncheckedCast(self.publisher);

    def enregistrer_serveur(self):
        try:
            print_("PublisherMiniserveur->enregistrer_serveur")
            self.managerTopic.enregistrerServeur(get_ip(), self.nomMini)
        except:
            print_exc_()

    def supprimer_serveur(self):
        try:
            print_("PublisherMiniserveur->supprimer_serveur")
            self.managerTopic.supprimerServeur(get_ip(), self.nomMini)
        except:
            print_exc_()
