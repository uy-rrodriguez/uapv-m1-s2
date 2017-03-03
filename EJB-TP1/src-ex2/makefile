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
#  QUESTION 2                                                   #
#################################################################

all: serveur client

# Serveur
serveur: serveur-compile serveur-build serveur-deploy

serveur-compile:
	javac serveur/*.java -classpath "$(LIB)"

serveur-build:
	jar cvf dist/ejb-tp1-ex2-serveur.jar serveur/*.class META-INF/*

serveur-deploy:
	asadmin deploy --force dist/ejb-tp1-ex2-serveur.jar

# Client
client: client-compile client-build client-run

client-compile:
	javac client/*.java -classpath "$(LIB)"

client-build:
	jar cvf dist/ejb-tp1-ex2-client.jar serveur/*.class

client-run:
	java -cp "$(LIB)$(SEP)dist/ejb-tp1-ex2-client.jar" Client


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./*/*.class
	rm -R ./dist/*.jar



#################################################################
#  EXTRA : Configuration GlassFish                              #
#################################################################

# Creation des ressources BDD en ligne
glassfish-connection-bdd:
	asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.ConnectionPoolDataSource --property portNumber=5432:password=3tud14nt:user=etdsn:serverName=pedago02a.univ-avignon.fr:databaseName=coursSN CoursSNPool
	asadmin create-jdbc-resource --connectionpoolid CoursSNPool jdbc/coursSN 
