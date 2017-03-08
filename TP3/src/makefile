USB=/media/uapv1601663/KINGSTON
#USB=/media/ricardo/KINGSTON
#USB=H:

#export PATH=$PATH:/opt/lampp/bin

all: testLire testCharger testShell

testLire:
	# Test avec un fichier de 1 million de lignes
	php "./profile/TestEnLisantLeFichier.php" $(USB)/test.txt

testCharger:
	# Test avec un fichier de 1 million de lignes
	php "./profile/TestEnChargeantLeFichierEtCherchant.php" $(USB)/test.txt

testShell:
	# Test avec un fichier de 1 million de lignes
	php "./profile/TestEnUtilisantLeShellUnix.php" $(USB)/test.txt
