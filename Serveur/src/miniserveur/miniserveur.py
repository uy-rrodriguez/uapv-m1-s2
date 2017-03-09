# coding: utf8

import sys, traceback, Ice
import AppMP3Player
import CLI


SERVER_IP = "localhost"

def main():
    status = 0
    ic = None
    try:
        props = Ice.createProperties(sys.argv)
        #props.setProperty("Ice.MessageSizeMax", "20480")
        iniData = Ice.InitializationData()
        iniData.properties = props

        # Initialisation du serveur et Ice
        ic = Ice.initialize(iniData)
        base = ic.stringToProxy("Metaserveur:default -h " + SERVER_IP + " -p 10000")
        serveur = AppMP3Player.MetaserveurPrx.checkedCast(base)
        if not serveur:
            raise RuntimeError("Invalid proxy")

        # Client
        client = CLI.App(serveur)

        # Boucle principale
        client.mainloop()

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
