# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, icestormutils


'''
    Cette classe implémente l'interface qui va gérer le TopicCommandes.
    À chaque fois que le subscriber IceStorm associé au topic reçoit
    un nouveau message, on fera automatiquement appel aux méthodes de
    cette classe.
'''
class TopicChansonsManagerI(AppMP3Player.TopicChansonsManager):
    def ajouterChanson(self, chanson, current=None):
        print "SubscriberChansons->ajouterChanson :", chanson.nom, chanson.path

    def supprimerChanson(self, chanson, current=None):
        print "SubscriberChansons->supprimerChanson :", chanson.nom


'''
    Classe qui implémente un souscripteur du topic TopicChansons
'''
class SubscriberChansons(icestormutils.Subscriber):
    def __init__(self, ic):
        super(SubscriberChansons, self).__init__(ic, "TopicChansons", "TopicChansons_Metaserveur", TopicChansonsManagerI())

