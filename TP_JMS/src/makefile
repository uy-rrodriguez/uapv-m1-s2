ifeq ($(OS),Windows_NT)
	# Config Windows (PowerShell)
	SEP=;
else
	# Config. Linux (faculté)
	SEP=:
endif

SERVER=G:/Dev/glassfish4/glassfish
LIB=.$(SEP)$(SERVER)/lib/appserv-rt.jar
#WAR=JEE-TP1.war

#all: compile build run
#
#compile:
#	javac -d WEB-INF/classes java/partie1/*.java -classpath "$(LIB)"
#	javac -d WEB-INF/classes java/partie2/*.java -classpath "$(LIB)"
#
#build:
#	jar cvf dist/$(WAR) WEB-INF css *.html
#
#deploy:
#	asadmin deploy --force dist/$(WAR)
#

#################################################################
#  QUESTION 1                                                   #
#################################################################

q1-run:
	javac q1/*.java -classpath "$(LIB)"
	java -classpath "$(LIB)" q1.Emetteur
	java -classpath "$(LIB)" q1.Recepteur


#################################################################
#  QUESTION 2                                                   #
#################################################################

q2-run: q2-compile q2-sender q2-receiver

q2-compile:
	javac q2/*.java -classpath "$(LIB)"

q2-sender:
	java -classpath "$(LIB)" q2.Emetteur "Message de test"

q2-receiver:
	java -classpath "$(LIB)" q2.Recepteur


#################################################################
#  QUESTION 3                                                   #
#################################################################

q3-compile:
	javac q3/*.java -classpath "$(LIB)"

q3-sender:
	java -classpath "$(LIB)" q3.Emetteur "Message de test"

q3-receiver-1:
	java -classpath "$(LIB)" q3.Recepteur 1000 "Recepteur_1"

q3-receiver-2:
	java -classpath "$(LIB)" q3.Recepteur 900 "Recepteur_2"


#################################################################
#  QUESTION 4                                                   #
#################################################################

q4-compile:
	javac q4/*.java -classpath "$(LIB)"

q4-sender:
	java -classpath "$(LIB)" q4.Emetteur "Message de test" $(FILTRES)

q4-receiver:
	java -classpath "$(LIB)" q4.Recepteur $(FILTRES)


#################################################################
#  QUESTION 5                                                   #
#################################################################

q5-compile:
	javac q5/*.java -classpath "$(LIB)"

q5-sender:
	java -classpath "$(LIB)" q5.Emetteur "Message de test" $(FILTRES)

q5-receiver:
	java -classpath "$(LIB)" q5.Recepteur $(FILTRES)


#################################################################
#  QUESTION 6                                                   #
#################################################################

q6-compile:
	javac q6/*.java -classpath "$(LIB)"

q6-sender:
	java -classpath "$(LIB)" q6.Emetteur "Message de test" $(FILTRES)

q6-receiver:
	java -classpath "$(LIB)" q6.Recepteur $(FILTRES)


#################################################################
#  QUESTION 7                                                   #
#################################################################

q7-compile:
	javac q7/*.java -classpath "$(LIB)"

q7-sender:
	java -classpath "$(LIB)" q7.Emetteur "Message de test"

q7-receiver: q7-receiver-build q7-receiver-deploy

q7-receiver-build:
	jar cvf q7/q7_recepteur.jar q7/Recepteur.class

q7-receiver-deploy:
	asadmin deploy --force q7/q7_recepteur.jar


#################################################################
#  QUESTION 8                                                   #
#################################################################

q8-compile:
	javac q8/*.java -classpath "$(LIB)"

q8-sender:
	java -classpath "$(LIB)" q8.Emetteur "Message de test" $(FILTRES)

q8-receiver: q8-receiver-build q8-receiver-deploy

q8-receiver-build:
	jar cvf q8/q8_recepteur.jar q8/Recepteur.class

q8-receiver-deploy:
	asadmin deploy --force q8/q8_recepteur.jar


#################################################################
#  QUESTION 9                                                   #
#################################################################

q9-all: q9-compile q9-build q9-deploy q9-clean

q9-compile:
	javac q9/*.java -classpath "$(LIB)"

q9-build:
	mkdir -p "WEB-INF/classes/q9"
	cp q9/*.class "WEB-INF/classes/q9"
	cp "q9/index.html" "."
	cp "q9/web.xml" "WEB-INF"
	jar cvf q9.war WEB-INF *.html

q9-deploy:
	asadmin deploy --force q9.war 
 
q9-clean: 
	rm -R "WEB-INF"
	rm "index.html"
	rm "q9.war"


#################################################################
#  QUESTION 10                                                  #
#################################################################

q10-all: q10-compile q10-build q10-deploy q10-clean

q10-compile:
	javac q10/*.java -classpath "$(LIB)"

q10-build:
	mkdir -p "WEB-INF/classes/q10"
	cp q10/*.class "WEB-INF/classes/q10"
	cp "q10/index.html" "."
	cp "q10/web.xml" "WEB-INF"
	jar cvf q10.war WEB-INF *.html

q10-deploy:
	asadmin deploy --force q10.war 
 
q10-clean: 
	rm -R "WEB-INF"
	rm "index.html"
	rm "q10.war"


#################################################################
#  QUESTION 11                                                   #
#################################################################

q11-compile:
	javac q11/*.java -classpath "$(LIB)"

q11-sender:
	java -classpath "$(LIB)" q11.Emetteur "Message de test" $(FILTRES)

q11-receiver:
	java -classpath "$(LIB)" q11.Recepteur $(FILTRES)


#################################################################
#  QUESTION 12                                                   #
#################################################################

q12-compile:
	javac q12/*.java -classpath "$(LIB)"

q12-sender:
	java -classpath "$(LIB)" q12.Emetteur "Message de test" $(FILTRES)

q12-receiver:
	java -classpath "$(LIB)" q12.Recepteur $(FILTRES)


#################################################################
#  QUESTION 13                                                   #
#################################################################

q13-compile:
	javac q13/*.java -classpath "$(LIB)"

q13-sender:
	java -classpath "$(LIB)" q13.Emetteur "Message de test" $(FILTRES)

q13-receiver:
	java -classpath "$(LIB)" q13.Recepteur $(FILTRES)


#################################################################
#  QUESTION 14                                                  #
#################################################################

q14-compile:
	javac q14/*.java -classpath "$(LIB)"

q14-sender:
	java -classpath "$(LIB)" q14.Emetteur "Message de test" $(FILTRES)

q14-receiver:
	java -classpath "$(LIB)" q14.Recepteur $(NOM) $(FILTRES)

q14-inscrire:
	java -classpath "$(LIB)" q14.Inscription $(NOM)

q14-desinscrire:
	java -classpath "$(LIB)" q14.Desinscription $(NOM)


#################################################################
#  QUESTION 15                                                  #
#################################################################

q15-compile:
	javac q15/*.java -classpath "$(LIB)"

q15-sender:
	java -classpath "$(LIB)" q15.Emetteur "Message de test" $(FILTRES)

q15-receiver:
	java -classpath "$(LIB)" q15.Recepteur $(NOM) $(FILTRES)

q15-inscrire:
	java -classpath "$(LIB)" q15.Inscription $(NOM)

q15-desinscrire:
	java -classpath "$(LIB)" q15.Desinscription $(NOM)


#################################################################
#  CLEAN                                                        #
#################################################################

clean:
	rm -R ./*/*.class



#################################################################
#  EXTRA : Configuration GlassFish                              #
#################################################################

# Création des ressources jms en ligne de commande
QueueConnectionFactory:
	asadmin create-jms-resource --restype javax.jms.QueueConnectionFactory jms/tpJMSConnFactory

Queue:
	asadmin create-jms-resource --restype javax.jms.Queue --property Name=tpJMSConnFactory jms/tpJMSDestination

TopicConnectionFactory:
	asadmin create-jms-resource --restype javax.jms.TopicConnectionFactory jms/tpJMSTopicConnFactory

Topic:
	asadmin create-jms-resource --restype javax.jms.Topic --property Name=tpJMSTopicConnFactory jms/tpJMSTopicDestination
