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
EX=ex6
NOM_EXE=client-$(TP)-$(EX).exe
NOM_JAR=client-$(TP)-$(EX).jar
URL=http://www.dataaccess.com/webservicesserver/textcasing.wso?WSDL

client-cs: client-cs-generate client-cs-compile client-cs-run clean-cs

client-cs-generate:
	wsdl -o:generated/TextCasing.cs $(URL)

client-cs-compile:
	mcs $(EX)/MonClientC.cs generated/TextCasing.cs -r:System.Web.Services -out:dist/$(NOM_EXE)

client-cs-run:
	./dist/$(NOM_EXE) PePitO


client-java: client-java-generate client-java-compile client-java-run clean-java

client-java-generate:
	wsimport -keep -verbose $(URL)

client-java-compile:
	javac $(EX)/*.java -classpath "$(LIB)"

client-java-run:
	appclient $(EX).MonClientSOAP PePitO


#################################################################
#  CLEAN                                                        #
#################################################################

clean-cs:
	rm -R ./generated/*
	rm -R ./dist/*.exe
	
clean-java:
	rm -R "./com"
	rm -R ./*/*.class

