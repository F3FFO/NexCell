LIB1 = flatlaf-2.0-rc1.jar
LIB2 = miglayout-core-11.0.jar
LIB3 = miglayout-swing-11.0.jar
JFLAGSCLASSPATH = -cp lib/$(LIB1):lib/$(LIB2):lib/$(LIB3):.
JFLAGS = -g
JFLAGS = -s 11
JC = javac
JVM = java
MAIN = nexCell/Main

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGSCLASSPATH) $(JFLAGS) $*.java

CLASSES = $(MAIN).java

classes: $(CLASSES:.java=.class)

default: classes

run: default
	$(JVM) $(JFLAGSCLASSPATH) $(MAIN)

clean: run
	find . -name "*.class" -type f -delete