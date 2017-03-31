# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils


'''
    Cette classe implémente l'interface qui va gérer le TopicMiniserveurs.
    À chaque fois que le subscriber IceStorm associé au topic reçoit
    un nouveau message, on fera automatiquement appel aux méthodes de
    cette classe.
'''
class TopicMiniserveursManagerI(AppMP3Player.TopicMiniserveursManager):
    def enregistrerServeur(self, ip, current=None):
        print "SubscriberMiniserveurs->enregistrerServeur :", ip

    def supprimerServeur(self, ip, current=None):
        print "SubscriberMiniserveurs->supprimerServeur :", ip


'''
    Classe qui implémente un souscripteur du topic TopicMiniserveurs
'''
class SubscriberMiniserveurs(icestormutils.Subscriber):
    def __init__(self, ic):
        super(SubscriberMiniserveurs, self).__init__(ic, "TopicMiniserveurs",
                                                     "TopicMiniserveurs_Metaserveur",
                                                     TopicMiniserveursManagerI())

