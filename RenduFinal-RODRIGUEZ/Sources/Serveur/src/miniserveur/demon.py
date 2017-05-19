# coding: utf8

import sys, traceback, time, os, re
import Ice, IceStorm
import AppMP3Player
import config, icestormutils
from utils import *


'''
    Le démon va lire constamment le répertoire configuré pour les chansons afin de
    détecter les ajouts et suppressions.
    Il est aussi un Publisher IceStorm. Il enverra un message au topic TopicChansons
    pour notifier chaque modification du catalogue de musiques.
'''
class DemonChansons(icestormutils.Publisher):

    def __init__(self, ic, nomMini):
        super(DemonChansons, self).__init__(ic, "TopicChansons")
        self.nomMini = nomMini
        self.manager = AppMP3Player.TopicChansonsManagerPrx.uncheckedCast(self.publisher)

        # Pour la gestion des fichiers musicaux
        self.actualSongs = []
        self.check_folder()


    def ajouter_chanson(self, nom, artiste, categorie, path):
        try:
            print_("DemonChansons->ajouter_chanson");
            c = AppMP3Player.Chanson()
            c.nom = nom
            c.artiste = artiste
            c.categorie = categorie
            c.path = path
            c.miniserveur = self.nomMini
            self.manager.ajouterChanson(c)

        except:
            sys.print_exc()


    def supprimer_chanson(self, nom):
        try:
            print_("DemonChansons->supprimer_chanson")
            c = AppMP3Player.Chanson()
            c.nom = nom
            c.miniserveur = self.nomMini
            self.manager.supprimerChanson(c)
        except:
            print_exc_()


    def check_folder(self):
        songs = []

        for root, dirs, files in os.walk(config.MINISERVEUR_SONGS_PATH):
            for name in files:
                if re.match("^.+\.mp3$", name):
                    songs.append(os.path.join(root, name))


        setSongs = set(songs)
        setActual = set(self.actualSongs)

        supprimees = [item for item in self.actualSongs if item not in setSongs]
        ajoutees = [item for item in songs if item not in setActual]


        # Traitement des fichiers supprimés
        for s in supprimees:
            self.supprimer_chanson(os.path.basename(s))


        # Traitement des fichiers ajoutés
        for s in ajoutees:
            self.ajouter_chanson(os.path.basename(s), "", "", s)

        # Mise-à-jour de la liste de chansons actuelle
        self.actualSongs = songs


    def run(self):
        try:
            print_("DemonChansons->run")

            while True:
                self.check_folder()
                time.sleep(10)

        except:
            raise

        print_("DemonChansons->end")


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

        demon = DemonChansons(ic, argv[1])
        demon.run()

    except (KeyboardInterrupt, SystemExit):
        pass

    except:
        print_exc_();
        status = 1

    # Clean up
    if ic:
        try:
            ic.destroy()
        except:
            status = 1
            print_exc_()

    exit(status)

if __name__ == "__main__":
    sys.exit(main())
