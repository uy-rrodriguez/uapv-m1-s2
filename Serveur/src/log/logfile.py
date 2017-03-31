# coding: utf8

import sys, traceback
from datetime import datetime

class LogFile(object):

    f = None

    def __init__(self, f):
        self.f = f
        self.println("")
        self.println("Start log at " + str(datetime.now()))

    def print_(self, string):
        self.f.write(string)
        self.f.flush()

    def println(self, string):
        self.print_(string + "\n")

    def print_exc(self):
        exc_type, exc_value, exc_traceback = sys.exc_info()
        traceback.print_tb(exc_traceback, limit=None, file=self.f)
        self.f.flush()

    def close(self):
        self.f.close()
