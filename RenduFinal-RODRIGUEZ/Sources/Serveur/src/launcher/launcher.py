# coding: utf8

import sys, traceback, time, subprocess, threading
import config
from logger import Logger


def log_worker(module, reader):
    if module != "icebox":
        while True:
            sys.stdout.write(reader.read())
            time.sleep(0.5)


def main(argv=None):
    if argv is None:
        argv = sys.argv

    status = 0

    logger = None
    processus = {}

    try:
        # Gestion des logs
        logger = Logger()

        # Modules dans l'order de lancement qu'il faut
        modules = ["icebox", "meta", "mini-1", "mini-2", "manager", "ws"]
        #modules = ["icebox", "meta", "mini-1", "manager"]

        # Lancement de chaque processus a travers la configuration dans le makefile
        for module in modules:
            writer, reader = logger.add_log(module)
            proc = subprocess.Popen("make run-" + module, stdout=writer, stderr=writer)
            #if module != "icebox":
            #    proc = subprocess.Popen("make run-" + module, stdout=writer)
            #else:
            #    proc = subprocess.Popen("make run-" + module, stdout=writer, stderr=writer)

            t = threading.Thread(target=log_worker, args=(module, reader))
            t.daemon = True # le thread finit avec le programme principal
            t.start()

            processus[module] = {"process": proc, "log": writer}


        # Boucle infinie... Bon, en fait on boucle tant qu'il y a des processus actifs
        loop = True
        while loop:
            loop = False
            for key in processus:
                data = processus[key]
                if data["process"].poll() is None:
                    loop = True

            time.sleep(0.5)


    except (KeyboardInterrupt, SystemExit):
        pass

    except:
        traceback.print_exc()
        #status = 1


    # Finalisation

    # On envoie le signal SIGKILL a tous les processus
    for key in processus:
        try:
            data = processus[key]
            if data["process"].poll() is None:
                data["process"].kill()
        except:
            traceback.print_exc()
            #status = 1

    # On attend la fin de tous avant d'afficher les dernieres lignes des logs
    for key in processus:
        try:
            data = processus[key]
            data["process"].wait()
            logger.remove_log(key)

        except:
            traceback.print_exc()
            #status = 1


    sys.exit(status)


if __name__ == "__main__":
    sys.exit(main())
