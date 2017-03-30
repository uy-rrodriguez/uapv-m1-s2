ifeq ($(OS),Windows_NT)
	# Config Windows (PowerShell)
	SEP=;
else
	# Config. Linux (faculté)
	SEP=:
endif

SERVER=G:/Dev/glassfish4/glassfish
LIB=.$(SEP)$(SERVER)/lib/appserv-rt.jar$(SEP)$(SERVER)/domains/domain1/lib/postgresql-9.1-902.jdbc4.jar


#################################################################
#  QUESTION 1                                                   #
#################################################################

all: serveur client

# Serveur
serveur: serveur-compile serveur-build serveur-deploy

serveur-compile:
	javac ex1/serveur/*.java -classpath "$(LIB)"

serveur-build:
	jar cvf dist/ejb-tp2-ex1-serveur.jar ex1/serveur/*.class META-INF/*

serveur-deploy:
	asadmin deploy --force dist/ejb-tp2-ex1-serveur.jar

# Client
client: client-compile client-build client-run

client-compile:
	javac ex1/client/*.java -classpath "$(LIB)"

client-build:
	jar cvf dist/ejb-tp2-ex1-client.jar ex1/client/*.class

client-run:
	java -cp "$(LIB)$(SEP)dist/ejb-tp2-ex1-client.jar" ex1.client.Client


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./*/serveur/*_.java
	rm -R ./*/*.class
	rm -R ./dist/*.jar



#################################################################
#  EXTRA : Configuration GlassFish                              #
#################################################################

# Creation des ressources BDD en ligne
glassfish-connection-bdd:
	asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.ConnectionPoolDataSource --property portNumber=5432:password=3tud14nt:user=etdsn:serverName=pedago02a.univ-avignon.fr:databaseName=coursSN CoursSNPool
	asadmin create-jdbc-resource --connectionpoolid CoursSNPool jdbc/coursSN 
