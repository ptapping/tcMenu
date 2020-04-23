all: plugins menugenerator

plugins:
	mvn clean install -f baseInputDisplayPlugin
	mvn clean install -f dfRobotCodePlugin

menugenerator:
	mvn -DskipTests clean install -f tcMenuGenerator

run: plugins menugenerator
	CLASSPATH="tcMenuGenerator/target/jfx/app" java --module-path tcMenuGenerator/target/jfx/deps --add-modules com.thecoderscorner.tcmenu.menuEditorUI com.thecoderscorner.menu.editorui.MenuEditorApp

clean: cleanplugins cleanmenugenerator

cleanplugins:
	mvn clean -f baseInputDisplayPlugin
	mvn clean -f dfRobotCodePlugin

cleanmenugenerator:
	mvn clean -f tcMenuGenerator
