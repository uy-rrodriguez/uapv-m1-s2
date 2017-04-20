# coding: utf8

import sys, traceback, Ice
import AppMP3Player
import config, cli


def main():
    status = 0
    ic = None
    try:
        props = Ice.createProperties(sys.argv)
        iniData = Ice.InitializationData()
        iniData.properties = props

        # Initialisation du serveur et Ice
        ic = Ice.initialize(iniData)
        base = ic.stringToProxy("Manager:default -h " + config.IP_MANAGER + " -p " + str(config.PORT_ICE_MANAGER))
        manager = AppMP3Player.ManagerPrx.checkedCast(base)
        if not manager:
            raise RuntimeError("Invalid proxy")


        # On lance le client en lui donnant le manager
        client = cli.App(manager)

        # Boucle principale
        client.mainloop()

    except (KeyboardInterrupt, SystemExit):
        pass

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
