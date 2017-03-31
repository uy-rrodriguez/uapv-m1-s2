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
    def ajouterChanson(self, chanson):
        print "SubscriberCommandes->ajouterChanson :", chanson.nom, chanson.path

    def supprimerChanson(self, chanson):
        print "SubscriberCommandes->supprimerChanson :", chanson.nom



class SubscriberCommandes(icestormutils.Subscriber):

    def __init__(self, ic):
        super(SubscriberCommandes, self).__init__(ic, "TopicCommandes",
                                                  "TopicCommandes_Miniserveur",
                                                  TopicChansonsManagerI())

