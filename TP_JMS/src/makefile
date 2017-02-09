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

#################################################################
#  QUESTION 1                                                   #
#################################################################

q1-run:
	javac q1/*.java -classpath "$(LIB)"
	java -classpath "$(LIB)" q1.Emetteur
	java -classpath "$(LIB)" q1.Recepteur


#################################################################
#  QUESTION 2                                                   #
#################################################################

q2-run: q2-compile q2-sender q2-receiver

q2-compile:
	javac q2/*.java -classpath "$(LIB)"

q2-sender:
	java -classpath "$(LIB)" q2.Emetteur "Message de test"

q2-receiver:
	java -classpath "$(LIB)" q2.Recepteur


#################################################################
#  QUESTION 3                                                   #
#################################################################

q3-compile:
	javac q3/*.java -classpath "$(LIB)"

q3-sender:
	java -classpath "$(LIB)" q3.Emetteur "Message de test"

q3-receiver-1:
	java -classpath "$(LIB)" q3.Recepteur 1000 "Recepteur_1"

q3-receiver-2:
	java -classpath "$(LIB)" q3.Recepteur 900 "Recepteur_2"


#################################################################
#  QUESTION 4                                                   #
#################################################################

q4-compile:
	javac q4/*.java -classpath "$(LIB)"

q4-sender:
	java -classpath "$(LIB)" q4.Emetteur "Message de test" $(NOMS)

q4-receiver:
	java -classpath "$(LIB)" q4.Recepteur $(NOMS)


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./*/*.class
