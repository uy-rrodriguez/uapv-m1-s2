ifeq ($(OS),Windows_NT)
	# Config Windows (PowerShell)
	SEP=;
else
	# Config. Linux (faculte)
	SEP=:
endif

SERVER=G:/Dev/glassfish4/glassfish
LIB=.$(SEP)./dist/*$(SEP)$(SERVER)/lib/appserv-rt.jar$(SEP)$(SERVER)/modules/javax.ws.rs-api.jar$(SEP)$(SERVER)/modules/jersey-client.jar$(SEP)$(SERVER)/modules/jersey-common.jar$(SEP)$(SERVER)/modules/jersey-guava.jar$(SEP)$(SERVER)/modules/jersey-media-jaxb.jar


#################################################################
#  COMPILATION                                                  #
#################################################################

TP=tp6
EX=ex8
NOM_JAR=client-$(TP)-$(EX).jar

client: compile build run clean

compile:
	javac ../src-$(EX)-serveur/$(EX)/Contact.java -d . -cp "$(LIB)" 
	javac $(EX)/*.java -cp "$(LIB)"

build:
	jar cvf dist/$(NOM_JAR) $(EX)/*.class

run:
	java -cp "$(LIB)" $(EX).ClientREST Pepito2 1002 #${ARGS}


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./*/*.class
	rm -R ./dist/*.jar
