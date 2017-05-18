# coding: utf8

# coding: utf8

import sys, traceback, time
from six import *
import nltk


'''
    Main pour demarrer le serveur Manager
'''
def main():
    status = 0

    try:
        print "HOLA"




    except (KeyboardInterrupt, SystemExit):
        pass

    except:
        traceback.print_exc()
        status = 1

    sys.exit(status)


if __name__ == "__main__":
    sys.exit(main())
