# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config

'''
    Classe Subscriber qui sera étendue par tout subscriber IceStorm.
'''
class Subscriber(object):
    topic = None
    subscriber = None

    def __init__(self, ic, topicName, subscriberName, topicManagerInstance):
        obj = ic.stringToProxy("MP3_IceStorm/TopicManager:tcp -p " + str(config.PORT_ICESTORM))
        topicManager = IceStorm.TopicManagerPrx.checkedCast(obj)

        # On récupère le topic
        try:
            self.topic = topicManager.retrieve(topicName)
        except IceStorm.NoSuchTopic as nst:
            try:
                self.topic = topicManager.create(topicName)
            except IceStorm.TopicExists as te:
                # Créé par un autre subscriber
                pass

        # On crée un adapter qui va rester à l'écoute des messages
        adapter = ic.createObjectAdapterWithEndpoints(topicName + "Subscriber", "tcp:udp")

        # On ajoute une instance de la classe qui va traiter les messages.
        # L'identifiant de cet objet identifie le subscriber dans IceStorm.
        subId = Ice.Identity()
        subId.name = subscriberName #Ice.generateUUID()
        self.subscriber = adapter.add(topicManagerInstance, subId).ice_oneway()

        # Activation de l'adapter
        adapter.activate()

        # Souscription
        try:
            qos = {}
            self.topic.subscribeAndGetPublisher(qos, self.subscriber)
        except IceStorm.AlreadySubscribed as ex:
            print "icestormutils.Subscriber : Subscriber existant"
            self.topic.unsubscribe(self.subscriber)
            self.topic.subscribeAndGetPublisher(qos, self.subscriber)

        print "icestormutils.Subscriber : Subscriber pour topic", topicName, "obtenu"
