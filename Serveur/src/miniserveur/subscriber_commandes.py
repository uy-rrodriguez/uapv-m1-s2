# coding: utf8

import sys, traceback, time, socket
import Ice, IceStorm
import AppMP3Player
import config


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



class SubscriberCommandes:
    managerCommandes = None

    def __init__(self, ic):
        # Récupération du topic et un subscriber associé
        obj = ic.stringToProxy("MP3_IceStorm/TopicManager:tcp -p " + str(config.PORT_ICESTORM))
        topicManager = IceStorm.TopicManagerPrx.checkedCast(obj)

        #
        # Retrieve the topic.
        #
        try:
            topic = topicManager.retrieve("TopicChansons")
        except IceStorm.NoSuchTopic as nst:
            try:
                topic = topicManager.create("TopicChansons")
            except IceStorm.TopicExists as te:
                # Créé par un autre publisher
                pass


        adapter = ic.createObjectAdapter("TopicChansons.Subscriber")

        # Add a servant for the Ice object. The identity is UUID.
        #
        # id is not directly altered since it is used below to detect
        # whether subscribeAndGetPublisher can raise AlreadySubscribed.
        #
        subId = Ice.Identity()
        subId.name = Ice.generateUUID()
        subscriber = adapter.add(TopicChansonsManagerI(), subId)


        # Activate the object adapter before subscribing.
        adapter.activate()


        # Subscription
        subscriber = subscriber.ice_oneway()
        try:
            qos = {}
            topic.subscribeAndGetPublisher(qos, subscriber)

        except IceStorm.AlreadySubscribed as ex:
            print("SubscriberCommandes->Reactivation d'un souscripteur existant")


        #self.shutdownOnInterrupt()
        #ic.waitForShutdown()

        print "SubscriberCommandes->Subscriber TopicCommandes obtenu"
