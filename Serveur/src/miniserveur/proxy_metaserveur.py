# coding: utf8

import sys, traceback
import Ice, IceStorm
import AppMP3Player
import config


class ProxyMetaserveur:
    proxy = None

    def __init__(self, ic):
        # Récupération du proxy
        base = ic.stringToProxy("Metaserveur:default -h " + config.IP_METASERVEUR + " -p " + str(config.PORT_ICE))
        self.proxy = AppMP3Player.MetaserveurPrx.checkedCast(base)
        if not self.proxy:
            raise RuntimeError("Invalid proxy")

        print "Metaserveur obtenu"
