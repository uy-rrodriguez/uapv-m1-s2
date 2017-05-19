#EMU=G:\Users\Ricci\AppData\Local\Android\sdk\tools/emulator.exe
EMU=G:\Users\Ricci\AppData\Local\Android\sdk\emulator/emulator.exe
ADB=G:/Users/Ricci/AppData/Local/Android/sdk/platform-tools/adb.exe
APK=G:\Dev\RepoGit\uapv-m1-s2\Client\PlayerDistribue\app\build\outputs/apk/app-debug.apk

default: build-install
all: run-emu build install

build:
	@echo "---------------- Build APK ----------------"
	cd "G:\Dev\RepoGit\uapv-m1-s2\Client\PlayerDistribue"
	./gradlew.bat assembleDebug
	cd ..

build-install:
	@echo "---------------- Build-Install APK ----------------"
	cd "G:\Dev\RepoGit\uapv-m1-s2\Client\PlayerDistribue"
	./gradlew.bat installDebug
	cd ..

run-emu:
	@echo "---------------- Run emulator ----------------"
	"$(EMU)" -avd Default

install:
	@echo "---------------- Install APK ----------------"
	$(ADB) install "$(APK)"

log:
	@echo "---------------- Logs Android ----------------"
	$(ADB) logcat ActivityManager:E AndroidRuntime:E PlayerActivity:D *:S

log-clean:
	@echo "---------------- Clean Logs Android ----------------"
	$(ADB) logcat -c

clean:
	@echo "---------------- Clean start ----------------"
	rm "$(APK)"
	@echo "---------------- Clean end ----------------"

dist:
	cp "CLI.py" "../../dist/src/client/"
	cp "ClientMP3.py" "../../dist/src/client/"
	cp "makefile" "../../dist/src/client/"
	cp "Player.py" "../../dist/src/client/"
	cp "vlc.py" "../../dist/src/client/"
	cp "../interface/InterfaceMP3.ice" "../../dist/src/interface/"
