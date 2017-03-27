# coding: utf8

import sys, traceback, time, socket
import Ice, IceStorm
import AppMP3Player
import config

'''
    Le démon va lire constamment le répertoire configuré pour les chansons afin de
    détecter les ajouts et suppressions.
    Il est aussi un Publisher IceStorm. Il enverra un message au topic TopicChansons
    pour notifier chaque modification du catalogue de musiques.
'''
class DemonChansons:
    managerChansons = None

    def __init__(self, ic):
        # Récupération du topic et un publisher associé
        obj = ic.stringToProxy("MP3_IceStorm/TopicManager:tcp -p " + str(config.PORT_ICESTORM))
        topicManager = IceStorm.TopicManagerPrx.checkedCast(obj)

        topic = None;
        while (topic == None):
            try:
                topic = topicManager.retrieve("TopicChansons")
            except IceStorm.NoSuchTopic as nst:
                try:
                    topic = topicManager.create("TopicChansons")
                except IceStorm.TopicExists as te:
                    # Créé par un autre publisher
                    pass

        pub = topic.getPublisher().ice_oneway()
        self.managerChansons = AppMP3Player.TopicChansonsManagerPrx.uncheckedCast(pub);

        print "DemonChansons->Publisher TopicChansons obtenu"


    def ajouter_chanson(self, nom, artiste, categorie, path):
        try:
            print "DemonChansons->Ajouter chanson"
            c = AppMP3Player.Chanson()
            c.nom = nom
            c.artiste = artiste
            c.categorie = categorie
            c.path = path
            self.managerChansons.ajouterChanson(c)
        except:
            traceback.print_exc()

    def supprimer_chanson(self, nom):
        try:
            print "DemonChansons->Supprimer chanson"
            c = AppMP3Player.Chanson()
            c.nom = nom
            self.managerChansons.supprimerChanson(c)
        except:
            traceback.print_exc()


    def run(self):
        try:
            print "DemonChansons->Run start"

            while True:
                self.ajouter_chanson("Hotel California", "The Eagles",
                                     "Rock", "mp3/Hotel_California.mp3")
                time.sleep(1)
                self.supprimer_chanson("Hotel California")
                time.sleep(1)

        except:
            print "DemonChansons->Run end"







