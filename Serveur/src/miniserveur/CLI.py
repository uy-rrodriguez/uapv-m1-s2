import os
import traceback
from Player import Player

class App:
  def __init__(self, srvProxy):
    self.srv = srvProxy
    self.player = Player("1233")


  def mainloop(self):
    # Affichage menu d'options
    print "\nClient demarre"
    print "==============\n"
    self.menu()

    # Boucle pour lire commandes utilisateur
    cmd = raw_input("\nChoisir une option : ")
    while cmd != "0":
      try:
        if cmd == "1":
          self.addSong()

        elif cmd == "2":
          self.removeSong()

        elif cmd == "3":
          self.listSongs()

        elif cmd == "4":
          self.searchSongs()

        elif cmd == "5":
          self.playSong()

        elif cmd == "6":
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
    print("1 - Ajouter chanson \t\t\t (name artist path)")
    print("2 - Supprimer chanson \t\t\t (id)")
    print("3 - Lister chansons")
    print("4 - Chercher par expr. reguliere \t (nameRegex artistRegex)")
    print("5 - Jouer chanson \t\t\t (id)")
    print("6 - Arreter chanson \t\t")
    print("0 - Fermer le programme")


  def askForParams(self, params):
    values = {}
    for p in params:
      values[p] = raw_input("Inserez la valeur pour " + p + " : ")
    return values


  def printSongList(self, songs):
    print "\n", "-"*60
    print "Liste de chansons :"
    print "\t\t".join(["ID", "Nom", "Artiste"])
    for s in songs:
      print "\t\t".join([str(s.id), s.name, s.artist])

    print "\n"


  def addSong(self):
    #print("addSong name=" + name + "; artist=" + artist + "; path=" + path)
    values = self.askForParams(["nom", "artiste", "path"])

    if (os.path.isfile(values["path"])):
        bytes = open(values["path"], "rb").read()
        self.srv.addSong(values["nom"], values["artiste"], bytes)
    else:
        print "\nLe fichier '", values["path"], "' n'existe pas !\n"


  def removeSong(self):
    #print("removeSong id=" + str(id))
    values = self.askForParams(["id"])
    self.srv.removeSong(int(values["id"]))


  def listSongs(self):
    #print("listSongs")
    songs = self.srv.listSongs()
    self.printSongList(songs)


  def searchSongs(self):
    #print("searchSongs nameRegex=" + nameRegex + "; artistRegex=" + artistRegex)
    values = self.askForParams(["nomRegex", "artisteRegex"])

    if (values["nomRegex"] == ""):
        values["nomRegex"] = ".*"

    if (values["artisteRegex"] == ""):
        values["artisteRegex"] = ".*"

    songs = self.srv.searchSongs(values["nomRegex"], values["artisteRegex"])
    self.printSongList(songs)


  def playSong(self):
    #print("playSong id=" + str(id))
    values = self.askForParams(["id"])

    try:
      # Appel au serveur pour qu'il commence le streaming
      self.srv.playSong(int(values["id"]))

      # Lecture du streaming
      self.player.stop()
      self.player.play()

    except Exception as e:
      pass


  def stopSong(self):
    #print("stopSong")
    try:
      # Appel au serveur pour qu'il commence le streaming
      self.srv.stopSong()

      # Arret de lecture du streaming
      self.player.stop()

    except Exception as e:
      pass
