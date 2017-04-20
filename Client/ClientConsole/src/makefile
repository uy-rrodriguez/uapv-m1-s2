all: build run clean

build:
	@echo "---------------- Build Ice ----------------"
	slice2py --all ../../../Interfaces/Manager.ice

run:
	@echo "---------------- Run client ----------------"
	python -m client.client

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
