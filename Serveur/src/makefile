
all: build deploy

build: build-py

build-py:
	@echo "---------------- Build Python start ----------------"
	# Interfaces utilisees en Python
	slice2py --all ../../Interfaces/Manager.ice
	slice2py --all ../../Interfaces/Metaserveur.ice
	slice2py --all ../../Interfaces/Messagerie.ice

run:
	python ClientMP3.py

run-manager:
	@echo "---------------- Run manager start ----------------"
	python -m manager.manager

run-ws:
	@echo "---------------- Run webservice ----------------"
	python -m ws.ws

run-meta:
	@echo "---------------- Run metaserveur start ----------------"
	python -m metaserveur.metaserveur

run-mini:
	@echo "---------------- Run miniserveur start ----------------"
	python -m miniserveur.miniserveur

run-icebox:
	@echo "---------------- Run icebox start ----------------"
	icebox --Ice.Config=config.icebox


run-pyglet:
	# set PYTHONPATH ~/Donnees_itinerantes/FAC/ArchDistrib/TPs/ProjetMP3/src/client/lib/pyglet/pyglet-1.2.4/:$(PYTHONPATH)
	# export PYTHONPATH=lib/pyglet/pyglet-1.2.4/:$(PYTHONPATH)
	# echo $$PYTHONPATH
	python GUI_pyglet.py

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
