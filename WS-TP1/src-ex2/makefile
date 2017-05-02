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
EX=ex2
NOM_WAR=WSSOAP-$(TP)-$(EX).war
NOM_CLIENT=client-$(TP)-$(EX).jar

serveur: serveur-compile serveur-build serveur-deploy
client: client-compile client-build client-run

serveur-compile:
	javac $(EX)/*.java -d WEB-INF/classes -classpath "$(LIB)"
	wsgen -cp "WEB-INF/classes$(SEP)$(LIB)" -d WEB-INF/classes -wsdl $(EX).MonSOAP

serveur-build:
	jar cvf dist/$(NOM_WAR) WEB-INF/*

serveur-deploy:
	asadmin deploy --force dist/$(NOM_WAR)
	

client-compile:
	cp "WEB-INF/classes/$(EX)/IMonSOAP.class" $(EX)
	javac $(EX)client/*.java -classpath "$(EX)/IIMonSOAP.class$(SEP)$(LIB)"

client-build:
	jar cvfe dist/$(NOM_CLIENT) $(EX)client.MonClientSOAP $(EX)client/*.class $(EX)/IMonSOAP.class

client-run:
	java -jar dist/$(NOM_CLIENT)


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./WEB-INF/classes/*
	rm -R ./*/*.class
	rm -R ./*/*/*.class
	rm -R ./dist/*.war
	rm -R ./dist/*.jar
