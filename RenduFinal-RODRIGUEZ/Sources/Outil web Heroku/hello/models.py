from django.db import models

# Create your models here.
class Greeting(models.Model):
    when = models.DateTimeField('date created', auto_now_add=True)

class Session(models.Model):
    key = models.CharField(max_length=30)
    value = models.CharField(max_length=250)
