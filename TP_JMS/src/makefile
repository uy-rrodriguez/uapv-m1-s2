ifeq ($(OS),Windows_NT)
	# Config Windows (PowerShell)
	SEP=;
else
	# Config. Linux (faculté)
	SEP=:
endif

SERVER=G:/Dev/glassfish4/glassfish
LIB=.$(SEP)$(SERVER)/lib/appserv-rt.jar
#WAR=JEE-TP1.war

#all: compile build run
#
#compile:
#	javac -d WEB-INF/classes java/partie1/*.java -classpath "$(LIB)"
#	javac -d WEB-INF/classes java/partie2/*.java -classpath "$(LIB)"
#
#build:
#	jar cvf dist/$(WAR) WEB-INF css *.html
#
#deploy:
#	asadmin deploy --force dist/$(WAR)
#

q1-run:
	javac q1/*.java -classpath "$(LIB)"
	java -classpath "$(LIB)" q1.Emetteur
	java -classpath "$(LIB)" q1.Recepteur

q2-run: q2-compile q2-sender q2-receiver

q2-compile:
	javac q2/*.java -classpath "$(LIB)"

q2-sender:
	java -classpath "$(LIB)" q2.Emetteur "Message de test"

q2-receiver:
	java -classpath "$(LIB)" q2.Recepteur
	
clean:
	rm -R q1/*.class
