# coding: utf8

import sys, traceback
import os, re
import Ice, IceStorm
import AppMP3Player
import config, icestormutils, player
from utils import *


'''
    Cette classe implémente l'interface qui va gérer le TopicCommandes.
    À chaque fois que le subscriber IceStorm associé au topic reçoit
    un nouveau message, on fera automatiquement appel aux méthodes de
    cette classe.
'''
class TopicCommandesManagerI(AppMP3Player.TopicCommandesManager):

    def __init__(self, publisherTopicChansons, nomMini, pathFichiers):
        self.nomMini = nomMini
        self.pathFichiers = pathFichiers
        self.listePlayers = {}
        self.publisherTopicChansons = publisherTopicChansons


    def listerChansons(self, current=None):
        #print_("SubscriberCommandes (" + self.nomMini + ")->listerChansons")

        chansons = []
        for root, dirs, files in os.walk(self.pathFichiers):
            for name in files:
                if re.match("^.+\.mp3$", name):
                    cPath = os.path.join(root, name)
                    c = AppMP3Player.Chanson()
                    c.nom = os.path.basename(cPath).replace(".mp3", "")
                    c.artiste = ""
                    c.categorie = ""
                    c.path = cPath
                    c.miniserveur = self.nomMini
                    chansons.append(c)
                    #chansons.append(os.path.join(root, name))

        # Envoie des fichiers existants à travers du topic de chansons
        self.publisherTopicChansons.listerChansons(chansons)


    def jouerChanson(self, ipClient, chanson, current=None):
        print_("SubscriberCommandes (" + self.nomMini + ")->jouerChanson :", ipClient, chanson.nom, chanson.miniserveur)

        if (chanson.miniserveur != self.nomMini):
            print_("SubscriberCommandes (" + self.nomMini + ")->Chanson demandee a", chanson.miniserveur)
            return

        if (ipClient not in self.listePlayers.keys()):
            mp3player = player.Player(get_ip(), get_free_port())
            self.listePlayers[ipClient] = mp3player
        else:
            mp3player = self.listePlayers[ipClient]
            mp3player.stop()

        mp3player.play(chanson.path)

        # On retourne l'addresse du streaming Grace au TopicChansons
        self.publisherTopicChansons.adresseStreaming(ipClient,
                                                     mp3player.ipStream,
                                                     mp3player.portStream)


    def pauseChanson(self, ipClient, current=None):
        print_("SubscriberCommandes (" + self.nomMini + ")->pauseChanson :", ipClient)

        if (ipClient in self.listePlayers.keys()):
            mp3player = self.listePlayers[ipClient]
            mp3player.stop()
            del self.listePlayers[ipClient]
        else:
            pass
            #raise RuntimeError("Le client n'est pas en train de jouer aucune chanson")


    def arreterChanson(self, ipClient, current=None):
        print_("SubscriberCommandes (" + self.nomMini + ")->arreterChanson :", ipClient)

        if (ipClient in self.listePlayers.keys()):
            mp3player = self.listePlayers[ipClient]
            mp3player.stop()
            del self.listePlayers[ipClient]
        else:
            pass
            #raise RuntimeError("Le client n'est pas en train de jouer aucune chanson")



class SubscriberCommandes(icestormutils.Subscriber):

    def __init__(self, ic, publisherTopicChansons, nomMini, pathFichiers):
        super(SubscriberCommandes, self).__init__(ic,
                                                  "TopicCommandes",
                                                  "TopicCommandes_Miniserveur_" + nomMini,
                                                  TopicCommandesManagerI(publisherTopicChansons, nomMini, pathFichiers))

