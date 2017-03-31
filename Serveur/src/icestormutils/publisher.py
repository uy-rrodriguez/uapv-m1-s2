# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config, log

'''
    Classe Publisher qui sera étendue par tout publisher IceStorm.
'''
class Publisher(object):

    def __init__(self, ic, topicName):
        # Récupération du topic et un publisher associé
        obj = ic.stringToProxy("MP3_IceStorm/TopicManager:tcp -p " + str(config.PORT_ICESTORM))
        topicManager = IceStorm.TopicManagerPrx.checkedCast(obj)

        topic = None;
        while (topic == None):
            try:
                topic = topicManager.retrieve(topicName)
            except IceStorm.NoSuchTopic as nst:
                try:
                    topic = topicManager.create(topicName)
                except IceStorm.TopicExists as te:
                    # Créé par un autre publisher
                    pass

        self.publisher = topic.getPublisher().ice_oneway()

        print "icestormutils.Publisher : Publisher pour topic", topicName, "obtenu"
        sys.stdout.flush()
