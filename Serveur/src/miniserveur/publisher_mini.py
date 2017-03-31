# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils

'''
    Ce Publisher IceStorm enverra des messages au topic TopicMiniserveurs
    pour notifier le Métaserveur du démarrage ou arrêt d'un miniserveur.
'''
class PublisherMiniserveurs(icestormutils.Publisher):

    def __init__(self, ic):
        super(PublisherMiniserveurs, self).__init__(ic, "TopicMiniserveurs")
        self.manager = AppMP3Player.TopicMiniserveursManagerPrx.uncheckedCast(self.publisher);

    def enregistrer_serveur(self):
        try:
            print "PublisherMiniserveur->enregistrer_serveur"
            self.manager.enregistrerServeur("127.0.0.1")
        except:
            traceback.print_exc()

    def supprimer_serveur(self):
        try:
            print "PublisherMiniserveur->supprimer_serveur"
            self.manager.supprimerServeur("127.0.0.1")
        except:
            traceback.print_exc()
