# coding: utf8

# http://blog.computerbacon.com/playing-audio-in-python-with-libvlc.html
# https://www.codementor.io/princerapa/tutorials/python-media-player-vlc-gtk-favehuy2b

import sys, os
import vlc
import config

class Player:
  def __init__(self, ipClient, portClient):
    self.instance = vlc.Instance()

    # Créer un MediaPlayer avec l'instance par défaut
    self.player = self.instance.media_player_new()

    # Options pour jouer en streaming
    self.options = config.MINISERVEUR_VLC_OPTIONS
    self.options = self.options.replace("<port>", str(portClient))
    self.options = self.options.replace("<ip>", ipClient)


  def play(self, mrl):
    # Chargement du media
    self.media = self.instance.media_new(mrl, self.options)

    # Ajout du media au player
    self.player.set_media(self.media)

    # Jouer !
    self.player.play()


  def stop(self):
    # Baisser le volume
    #player.audio_set_volume(50)

    # Arreter la musique en cours
    self.player.stop()

