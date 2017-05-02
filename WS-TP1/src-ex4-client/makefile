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
EX=ex4
NOM_EXE=client-$(TP)-$(EX).exe

client: client-generate client-compile client-run

client-generate:
	wsdl http://localhost:8080/WSSOAP-$(TP)-$(EX)/MonSOAPService?wsdl

client-compile:
	mcs $(EX)/MonClientC.cs MonSOAPService.cs -r:System.Web.Services -out:dist/$(NOM_EXE)

client-run:
	./dist/$(NOM_EXE)


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./*.cs
	rm -R ./dist/*.exe
