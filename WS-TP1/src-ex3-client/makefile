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
EX=ex3
NOM_JAR=client-$(TP)-$(EX).jar

client: client-wsimport client-compile client-build client-run

client-wsimport:
	wsimport -verbose http://localhost:8080/WSSOAP-$(TP)-$(EX)/MonSOAPService?wsdl

client-compile:
	javac $(EX)/*.java -classpath "$(LIB)"

client-build:
	jar cvfe dist/$(NOM_JAR) $(EX).MonClientSOAP $(EX)/*.class

client-run:
	java -jar dist/$(NOM_JAR)


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./*/*.class
	rm -R ./dist/*.jar
