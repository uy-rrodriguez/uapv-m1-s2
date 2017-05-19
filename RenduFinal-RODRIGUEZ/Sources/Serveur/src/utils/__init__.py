import sys, traceback
import socket
import urllib2

def print_(*args):
    strArgs = []
    for a in args:
        if isinstance(a, list):
            strArgs.append(", ".join(a))
        else:
            strArgs.append(a)

    print " ".join(strArgs)
    sys.stdout.flush()

def print_exc_():
    traceback.print_exc(file=sys.stdout)
    sys.stdout.flush()

def get_ip():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(("8.8.8.8", 80))
    ip = s.getsockname()[0]
    s.close()
    return ip

def get_free_port():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.bind(("", 0))
    port = s.getsockname()[1]
    s.close()
    return port

def set_public_ip():
    ip = get_ip()
    content = urllib2.urlopen("https://uapv-m1-s2-util.herokuapp.com/set_util_ip/" + ip + "/").read()

    if ("IP set" in content):
        return True, None
    else:
        return False, content
