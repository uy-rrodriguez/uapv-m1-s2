# coding: utf8

import sys, traceback, time
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
    def ajouterChanson(self, chanson, current=None):
        print "SubscriberCommandes->ajouterChanson :", chanson.nom, chanson.path

    def supprimerChanson(self, chanson, current=None):
        print "SubscriberCommandes->supprimerChanson :", chanson.nom


'''
    Cette classe implémente le servant su Metaserveur.
    Il va recevoir et traiter les demandes provenant du serveur Manager et des
    mini-serveurs.
'''
class MetaserveurI(AppMP3Player.Metaserveur):
    def __init__(self):
        pass

    def traiterCommande(self, ipClient, commande, current=None):
        print "MetaserveurI->traiterCommande"
        return commande #Commande

    def enregistrerServeur(self, ipServeur, current=None):
        print "MetaserveurI->enregistrerServeur"
        return 0 #int isServeur

    def supprimerServeur(self, idServeur, current=None):
        print "MetaserveurI->supprimerServeur"
        return #void


def creer_souscripteur(ic):
    obj = ic.stringToProxy("MP3_IceStorm/TopicManager:tcp -p " + str(config.PORT_ICESTORM))
    topicManager = IceStorm.TopicManagerPrx.checkedCast(obj)

    # On récupère le topic
    try:
        topic = topicManager.retrieve("TopicChansons")
    except IceStorm.NoSuchTopic as nst:
        try:
            topic = topicManager.create("TopicChansons")
        except IceStorm.TopicExists as te:
            # Créé par un autre publisher
            pass

    # On crée un adapter qui va rester à l'écoute des messages
    adapter = ic.createObjectAdapterWithEndpoints("TopicChansonsSubscriber", "tcp:udp")

    # On ajoute une instance de la classe qui va traiter les messages.
    # L'identifiant de cet objet sera généré automatiquement.
    subId = Ice.Identity()
    subId.name = Ice.generateUUID()
    subscriber = adapter.add(TopicChansonsManagerI(), subId)

    # Activation de l'adapter
    adapter.activate()

    # Suouscription
    subscriber = subscriber.ice_oneway()
    try:
        qos = {}
        topic.subscribeAndGetPublisher(qos, subscriber)
    except IceStorm.AlreadySubscribed as ex:
        print("SubscriberCommandes->Reactivation d'un souscripteur existant")


def creer_servant(ic):
    adapter = ic.createObjectAdapterWithEndpoints("Metaserveur", "default -p " + str(config.PORT_ICE))
    meta = MetaserveurI()
    adapter.add(meta, ic.stringToIdentity("Metaserveur"))
    adapter.activate()


def main():
    status = 0
    ic = None
    try:
        # Configuration de Ice. Soit on le fait ici, soit dans un fichier de configuration
        # On va configurer un souscripteur
        props = Ice.createProperties(sys.argv)
        #props.setProperty("TopicChansons.Subscriber.Endpoints", "tcp:udp")
        iniData = Ice.InitializationData()
        iniData.properties = props
        ic = Ice.initialize(iniData)

        # D'abord, on crée un souscripteur au TopicChansons
        creer_souscripteur(ic)

        # Puis on crée le servant pour le Metserveur
        creer_servant(ic)

        # Finalement on reste à l'écoute de messages entrant
        ic.waitForShutdown()

    except:
        traceback.print_exc()
        status = 1

    if ic:
        # Clean up
        try:
            ic.destroy()
        except:
            traceback.print_exc()
            status = 1

    sys.exit(status)


if __name__ == "__main__":
    main()
