# coding: utf8

import sys, traceback, time, os, re
import Ice, IceStorm
import AppMP3Player
import config, icestormutils


'''
    Le démon va lire constamment le répertoire configuré pour les chansons afin de
    détecter les ajouts et suppressions.
    Il est aussi un Publisher IceStorm. Il enverra un message au topic TopicChansons
    pour notifier chaque modification du catalogue de musiques.
'''
class DemonChansons(icestormutils.Publisher):

    def __init__(self, ic):
        super(DemonChansons, self).__init__(ic, "TopicChansons")
        self.manager = AppMP3Player.TopicChansonsManagerPrx.uncheckedCast(self.publisher)

        # Pour la gestion des fichiers musicaux
        self.actualSongs = []
        self.check_folder()


    def ajouter_chanson(self, nom, artiste, categorie, path):
        try:
            print "DemonChansons->ajouter_chanson"; sys.stdout.flush()

            c = AppMP3Player.Chanson()
            c.nom = nom
            c.artiste = artiste
            c.categorie = categorie
            c.path = path
            self.manager.ajouterChanson(c)
        except:
            self.log.print_exc()

    def supprimer_chanson(self, nom):
        try:
            print "DemonChansons->supprimer_chanson"; sys.stdout.flush()
            c = AppMP3Player.Chanson()
            c.nom = nom
            self.manager.supprimerChanson(c)
        except:
            self.log.print_exc()


    def check_folder(self):
        songs = []

        for root, dirs, files in os.walk(config.DEMON_SONGS_PATH):
            for name in files:
                if re.match("^.+\.mp3$", name):
                    songs.append(os.path.join(root, name))

        print "Liste actuelle"
        for s in songs:
            print s

        print ""

        setSongs = set(songs)
        setActual = set(self.actualSongs)

        supprimees = [item for item in self.actualSongs if item not in setSongs]
        ajoutees = [item for item in songs if item not in setActual]

        print "Supressions"
        for s in supprimees:
            print s

        print ""

        print "Ajouts"
        for s in ajoutees:
            print s

        print ""

        self.actualSongs = songs


    def run(self):
        try:
            print "DemonChansons->run"; sys.stdout.flush()

            while True:
                '''
                self.ajouter_chanson("Hotel California", "The Eagles",
                                     "Rock", "mp3/Hotel_California.mp3")
                time.sleep(1)
                self.supprimer_chanson("Hotel California")
                time.sleep(1)
                '''

                self.check_folder()
                time.sleep(10)

        except:
            raise

        print "DemonChansons->end"; sys.stdout.flush()


def main(argv=None):
    if argv is None:
        argv = sys.argv

    ic = None
    status = 0

    try:
        props = Ice.createProperties(argv)
        iniData = Ice.InitializationData()
        iniData.properties = props
        ic = Ice.initialize(iniData)

        demon = DemonChansons(ic)
        demon.run()

    except (KeyboardInterrupt, SystemExit):
        pass

    except:
        traceback.print_exc(); sys.stdout.flush()
        status = 1

    # Clean up
    if ic:
        try:
            ic.destroy()
        except:
            status = 1
            traceback.print_exc(); sys.stdout.flush()

    exit(status)

if __name__ == "__main__":
    sys.exit(main())
