# coding: utf8

import sys, traceback, time, subprocess, io
import config


class Logger(object):
    def __init__(self):
        self.logs = {}

    def add_log(self, key):
        filePath = "launcher/logs/" + key + ".log"
        writer = io.open(filePath, "wb")
        reader = io.open(filePath, "rb", 1)
        if self.logs.has_key(key):
            self.logs[key].close()
        self.logs[key] = writer
        return writer, reader

    def remove_log(self, key):
        if self.logs.has_key(key):
            self.logs[key].close()
            del self.logs[key]

    def flush_log(self, key):
        if self.logs.has_key(key):
            print "TRUE"; sys.stdout.flush()
            self.logs[key].flush()

    def get_log(self, key):
        if self.logs.has_key(key):
            return self.logs[key]
        else:
            return None
