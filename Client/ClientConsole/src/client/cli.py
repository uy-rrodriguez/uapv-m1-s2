# coding: utf8

import os, sys, traceback
import json
import AppMP3Player
import config, player


class App:
    def __init__(self, srvProxy):
        self.srv = srvProxy
        self.mp3player = player.Player(config.CLIENT_VLC_PORT)
        self.songs = []


    def mainloop(self):
        # Affichage menu d'options
        print "\nClient start"
        print "==============\n"
        self.menu()

        # Boucle pour lire commandes utilisateur
        cmd = raw_input("\nChoisir une option : ")
        while cmd != "0":
            try:
                if cmd == "1":
                    self.listSongs()

                elif cmd == "2":
                    self.playSong()

                elif cmd == "3":
                    self.stopSong()

                else:
                    print("Commande non reconnue")
                # Fin if-elif-else

            except:
                traceback.print_exc()
            # Fin try-catch

            #raw_input()
            print("="*80 + "\n")
            self.menu()
            cmd = raw_input("\nChoisir une option : ")

        # Fin while


    def menu(self):
        print("Options :")
        print("1 - Lister chansons")
        print("2 - Jouer chanson \t\t\t (id)")
        print("3 - Arreter chanson \t\t")
        print("0 - Fermer le programme")


    def askForParams(self, params):
        values = {}
        for p in params:
            values[p] = raw_input("Inserez la valeur pour " + p + " : ")
        return values


    def printSongs(self):
        print "\n", "-"*60
        print "Liste de chansons :"
        print "\t\t".join(["ID", "Nom"])
        i = 1
        for s in self.songs:
            print "\t\t".join([str(i), s["nom"]])
            i = i+1

        print "\n"


    def listSongs(self):
        #print("listSongs")
        try:
            # Création de l'objet Commande
            c = AppMP3Player.Commande()
            c.commande = config.COM_LIST
            c.params = []

            # Traitement du retour
            c = self.srv.commandeManuelle(c)
            if (c.erreur):
                print c.msgErreur
            else :
                self.songs = json.loads(c.retour)
                self.printSongs()

        except Exception as e:
            traceback.print_exc()


    def playSong(self):
        #print("playSong")
        values = self.askForParams(["id"])

        try:
            # Création de l'objet Commande
            c = AppMP3Player.Commande()
            c.commande = config.COM_PLAY

            # Selection de la chanson
            sel = int(values["id"]) - 1
            #name = self.songs[sel]["nom"]
            c.params = [str(sel)]

            # Appel au serveur pour qu'il commence le streaming
            self.srv.commandeManuelle(c)

            # Lecture du streaming
            self.mp3player.stop()
            self.mp3player.play()

        except Exception as e:
            traceback.print_exc()


    def stopSong(self):
        #print("stopSong")
        try:
            # Création de l'objet Commande
            c = AppMP3Player.Commande()
            c.commande = config.COM_STOP
            c.params = []

            # Appel au serveur pour qu'il arrete le streaming
            self.srv.commandeManuelle(c)

            # Arret de lecture du streaming
            self.mp3player.stop()

        except Exception as e:
            traceback.print_exc()
