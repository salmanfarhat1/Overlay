#!/usr/bin/env bash
javac -d classes -classpath .:classes src/ServerReg_itf.java
cd classes
jar cvf ../lib/ServerReg_itf.jar ServerReg_itf.class
cd ..

javac -d classes -classpath .:classes src/ServerReg_imp.java
cd classes
jar cvf ../lib/ServerReg_imp.jar ServerReg_imp.class
cd ..

javac -d classes -classpath .:classes src/Initialize_itf.java
cd classes
jar cvf ../lib/Initialize_itf.jar Initialize_itf.class
cd ..

javac -d classes -classpath .:classes src/Initialize_impl.java
cd classes
jar cvf ../lib/Initialize_impl.jar Initialize_impl.class
cd ..



javac -d classes -cp .:classes:lib/ServerReg_imp.jar:lib/Initialize_itf.jar:lib/Initialize_itf.jar src/MainNode.java
javac -d classes -cp .:classes:lib/ServerReg_imp.jar:lib/Initialize_itf.jar:lib/Initialize_itf.jar src/Servers.java

export CLASSPATH="lib/Initialize_itf.jar:lib/ServerReg_itf.jar"