
all: build run-launcher

build:
	@echo "---------------- Build Ice ----------------"
	# Interfaces utilisees en Python
	slice2py --all ../../Interfaces/Manager.ice
	slice2py --all ../../Interfaces/Metaserveur.ice
	slice2py --all ../../Interfaces/Messagerie.ice

run-launcher:
	@echo "---------------- Run launcher ----------------"
	python -m launcher.launcher

run-manager:
	@echo "---------------- Run manager ----------------"
	python -m manager.manager

run-ws:
	@echo "---------------- Run webservice ----------------"
	python -m ws.ws

run-meta:
	@echo "---------------- Run metaserveur ----------------"
	python -m metaserveur.metaserveur

run-mini-1:
	@echo "---------------- Run miniserveur 1 ----------------"
	python -m miniserveur.miniserveur MINI_1 ./miniserveur/musique_1

run-mini-2:
	@echo "---------------- Run miniserveur 2 ----------------"
	python -m miniserveur.miniserveur MINI_2 ./miniserveur/musique_2

run-icebox:
	@echo "---------------- Run icebox ----------------"
	icebox --Ice.Config=config/config.icebox

run-icestormadmin:
	@echo "---------------- Run icestormadmin ----------------"
	icestormadmin --Ice.Config=config/config.icestormadmin

clean:
	@echo "---------------- Clean start ----------------"
	rm -r "./AppMP3Player/"
	rm *_ice.py
	rm *.pyc
	rm */*.pyc
	@echo "---------------- Clean end ----------------"

dist:
	cp "CLI.py" "../../dist/src/client/"
	cp "ClientMP3.py" "../../dist/src/client/"
	cp "makefile" "../../dist/src/client/"
	cp "Player.py" "../../dist/src/client/"
	cp "vlc.py" "../../dist/src/client/"
	cp "../interface/InterfaceMP3.ice" "../../dist/src/interface/"
