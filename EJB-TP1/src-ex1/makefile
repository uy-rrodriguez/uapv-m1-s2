ifeq ($(OS),Windows_NT)
	# Config Windows (PowerShell)
	SEP=;
else
	# Config. Linux (facult�)
	SEP=:
endif

SERVER=G:/Dev/glassfish4/glassfish
LIB=.$(SEP)$(SERVER)/lib/appserv-rt.jar


#################################################################
#  QUESTION 1                                                   #
#################################################################

all: serveur client

# Serveur
serveur: serveur-compile serveur-build serveur-deploy

serveur-compile:
	javac serveur/*.java -classpath "$(LIB)"

serveur-build:
	jar cvf dist/ejb-tp1-ex1-serveur.jar serveur/*.class

serveur-deploy:
	asadmin deploy --force dist/ejb-tp1-ex1-serveur.jar

# Client
client: client-compile client-build client-run

client-compile:
	javac client/*.java -classpath "$(LIB)"

client-build:
	jar cvf dist/ejb-tp1-ex1-client.jar client/*.class

client-run:
	java -cp "$(LIB)$(SEP)dist/ejb-tp1-ex1-client.jar" client.ClientEx1


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./*/*.class
	rm -R ./dist/*.jar

	