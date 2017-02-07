ifeq ($(OS),Windows_NT)
	# Config Windows (PowerShell)
	SEP=;
else
	# Config. Linux (faculté)
	SEP=:
endif

SERVER=G:/Dev/glassfish4/glassfish
LIB=.$(SEP)$(SERVER)/modules/gf-client-module.jar$(SEP)$(SERVER)/lib/appservrt.jar$(SEP)$(SERVER)/lib/javaee.jar
WAR=JEE-TP1.war

all: compile build deploy

compile:
	javac -d WEB-INF/classes java/partie1/*.java -classpath "$(LIB)"
	javac -d WEB-INF/classes java/partie2/*.java -classpath "$(LIB)"
	#javac -d WEB-INF/classes java/partie3/*.java -classpath "$(LIB)"

build:
	jar cvf dist/$(WAR) WEB-INF css *.html

deploy:
	asadmin deploy --force dist/$(WAR)

run-ex3:
	javac ex3-client/Ex3Client.java
	java -cp "ex3-client" Ex3Client nom

clean:
	rm -R dist/*
	rm -R WEB-INF/classes/*