# coding: utf8

# Commandes
COM_PLAY         = "play"
COM_PAUSE        = "pause"
COM_STOP         = "stop"
COM_LIST         = "to_list"
COM_SEARCH       = "search"
COM_VOL_UP       = "vol_up"
COM_VOL_DOWN     = "vol_down"
COM_SILENCE      = "silence"
COM_FAV_ADD      = "fav_add"
COM_FAV_SUPP     = "fav_supp"
COM_SHUTDOWN     = "shutdown"
COM_SHUTDOWN_X   = "shutdown_x"

# IPs serveurs
IP_MANAGER = "127.0.0.1"
IP_METASERVEUR = "127.0.0.1"

# Configuration de ports
PORT_ICEBOX              = "9996"
PORT_ICE_MANAGER         = "10000"
PORT_ICE_METASERVEUR     = "10001"
PORT_ICESTORM            = "20000"
PORT_ICESTORM_PUBLISHER  = "20001"
PORT_WS                  = "30000"

# Fichiers logs
LOG_MINISERVEUR_DEMON     = "log/miniserveur_demon.log"

# Configuration du démon qui va lire les chansons
MINISERVEUR_SONGS_PATH = "miniserveur/musique"
MINISERVEUR_VLC_OPTIONS = "sout=#rtp{access=udp,mux=ts,dst=<ip>,port=<port>}"

# Configuration du port écouté par le client
CLIENT_VLC_PORT = 1233
