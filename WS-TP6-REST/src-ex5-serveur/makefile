ifeq ($(OS),Windows_NT)
	# Config Windows (PowerShell)
	SEP=;
else
	# Config. Linux (faculte)
	SEP=:
endif

SERVER=G:/Dev/glassfish4/glassfish
LIB=.$(SEP)$(SERVER)/lib/appserv-rt.jar$(SEP)$(SERVER)/modules/javax.ws.rs-api.jar$(SEP)$(SERVER)/modules/jersey-client.jar$(SEP)$(SERVER)/modules/jersey-common.jar


#################################################################
#  COMPILATION                                                  #
#################################################################

TP=tp6
EX=ex5
NOM_WAR=WSREST-$(TP)-$(EX).war

all: compile build deploy clean

compile:
	javac $(EX)/*.java -d WEB-INF/classes -classpath "$(LIB)"

build:
	jar cvf dist/$(NOM_WAR) WEB-INF/* ./*.html

deploy:
	asadmin deploy --force dist/$(NOM_WAR)


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./WEB-INF/classes/*
	rm -R ./dist/*.war
