ifeq ($(OS),Windows_NT)
	# Config Windows (PowerShell)
	SEP=;
else
	# Config. Linux (faculte)
	SEP=:
endif

SERVER=G:/Dev/glassfish4/glassfish
LIB=.$(SEP)$(SERVER)/lib/appserv-rt.jar


#################################################################
#  COMPILATION                                                  #
#################################################################

TP=tp1
EX=ex5
NOM_WAR=WSSOAP-$(TP)-$(EX).war

serveur: serveur-compile serveur-build serveur-deploy

serveur-compile:
	javac $(EX)/*.java -d WEB-INF/classes -classpath "$(LIB)"
	wsgen -cp "WEB-INF/classes$(SEP)$(LIB)" -d WEB-INF/classes -wsdl $(EX).MonSOAP

serveur-build:
	jar cvf dist/$(NOM_WAR) WEB-INF/* ./*.jsp

serveur-deploy:
	asadmin deploy --force dist/$(NOM_WAR)


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./WEB-INF/classes/*
	rm -R ./dist/*.war
