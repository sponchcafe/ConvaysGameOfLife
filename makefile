all: GameOfLife.jar

clean:
	rm -f *.class GameOfLife.jar

run: GameOfLife.jar
	java -jar GameOfLife.jar

GameOfLife.jar: *.class MANIFEST.mf
	jar -cfvm GameOfLife.jar MANIFEST.mf *.class 

*.class: *.java
	javac *.java

