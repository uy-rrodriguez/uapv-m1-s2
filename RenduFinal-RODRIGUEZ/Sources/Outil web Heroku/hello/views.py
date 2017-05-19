from django.shortcuts import render
from django.http import HttpResponse

from .models import Greeting
from .models import Session

# Create your views here.
def index(request):
    # return HttpResponse('Hello from Python!')
    return render(request, 'index.html')


def db(request):

    greeting = Greeting()
    greeting.save()

    greetings = Greeting.objects.all()

    return render(request, 'db.html', {'greetings': greetings})

def get_util_ip(request):
    sessions = Session.objects.filter(key="ip")
    if len(sessions) > 0:
        ip = sessions[0].value
    else:
        ip = "0.0.0.0"
    return HttpResponse("%s" % ip)

def set_util_ip(request, ip):
    sessions = Session.objects.filter(key="ip")
    if len(sessions) > 0:
        s = sessions[0]
        s.value = ip
    else:
        s = Session(key="ip", value=ip)
    
    s.save()
    return HttpResponse("IP set %s" % s.value)
