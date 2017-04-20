# coding: utf8

import sys, traceback
import os, re
import Ice, IceStorm
import AppMP3Player
import config, icestormutils, player


'''
    Cette classe implémente l'interface qui va gérer le TopicCommandes.
    À chaque fois que le subscriber IceStorm associé au topic reçoit
    un nouveau message, on fera automatiquement appel aux méthodes de
    cette classe.
'''
class TopicCommandesManagerI(AppMP3Player.TopicCommandesManager):

    def __init__(self, publisherTopicChansons):
        self.listePlayers = {}
        self.publisherTopicChansons = publisherTopicChansons


    def listerChansons(self, current=None):
        #print "SubscriberCommandes->listerChansons"

        chansons = []
        for root, dirs, files in os.walk(config.MINISERVEUR_SONGS_PATH):
            for name in files:
                if re.match("^.+\.mp3$", name):
                    cPath = os.path.join(root, name)
                    c = AppMP3Player.Chanson()
                    c.nom = os.path.basename(cPath)
                    c.artiste = ""
                    c.categorie = ""
                    c.path = cPath
                    chansons.append(c)
                    #chansons.append(os.path.join(root, name))

        # Envoie des fichiers existants à travers du topic de chansons
        self.publisherTopicChansons.listerChansons(chansons)


    def jouerChanson(self, ipClient, chanson, current=None):
        print "SubscriberCommandes->jouerChanson :", ipClient, chanson.nom, chanson.path

        if (ipClient not in self.listePlayers.keys()):
            mp3player = player.Player(ipClient, config.CLIENT_VLC_PORT)
            self.listePlayers[ipClient] = mp3player
        else:
            mp3player = self.listePlayers[ipClient]
            mp3player.stop()

        mp3player.play(chanson.path)


    def pauseChanson(self, ipClient, current=None):
        print "SubscriberCommandes->pauseChanson :", ipClient

        if (ipClient in self.listePlayers.keys()):
            mp3player = self.listePlayers[ipClient]
            mp3player.stop()
            del self.listePlayers[ipClient]
        else:
            raise RuntimeError("Le client n'est pas en train de jouer aucune chanson")


    def arreterChanson(self, ipClient, current=None):
        print "SubscriberCommandes->arreterChanson :", ipClient

        if (ipClient in self.listePlayers.keys()):
            mp3player = self.listePlayers[ipClient]
            mp3player.stop()
            del self.listePlayers[ipClient]
        else:
            raise RuntimeError("Le client n'est pas en train de jouer aucune chanson")



class SubscriberCommandes(icestormutils.Subscriber):

    def __init__(self, ic, publisherTopicChansons):
        super(SubscriberCommandes, self).__init__(ic,
                                                  "TopicCommandes",
                                                  "TopicCommandes_Miniserveur",
                                                  TopicCommandesManagerI(publisherTopicChansons))

