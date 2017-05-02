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

TP=tp1
EX=ex1
NOM_WS=MonSOAP
NOM_WAR=WSSOAP-$(TP)-$(EX).war

all: compile build deploy clean

compile:
	javac $(EX)/*.java -d WEB-INF/classes -classpath "$(LIB)"
	wsgen -cp "WEB-INF/classes$(SEP)$(LIB)" -d WEB-INF/classes -wsdl $(EX).$(NOM_WS)

build:
	jar cvf dist/$(NOM_WAR) WEB-INF/*

deploy:
	asadmin deploy --force dist/$(NOM_WAR)


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./WEB-INF/classes/*
	rm -R ./dist/*.war
