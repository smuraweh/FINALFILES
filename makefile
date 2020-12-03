make application: Frame.java
	#javac Add.java Date.java Frame.java Load.java Node.java Save.java Student.java
	#javac -cp ".:./jars/jcommon-1.0.23.jar .:./jars/jfreechart-1.0.19.jar" Plot.java
	#Add.class Date.class Frame.class Load.class Node.class Plot.class Save.class Student.class -o application
	#java Frame -o application

	#javac Add.java Date.java Frame.java Load.java Node.java Save.java Student.java
	javac Date.java Frame.java Load.java Node.java Save.java Student.java
	java Frame -o application
