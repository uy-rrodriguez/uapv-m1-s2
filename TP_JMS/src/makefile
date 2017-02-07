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

run-q1:
	javac q1/*.java -classpath "$(LIB)"
	java -cp "q1" Emetteur
	java -cp "q1" Recepteur

clean:
	rm -R q1/*.class
