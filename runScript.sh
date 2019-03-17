#!/usr/bin/env bash
javac -d classes -classpath .:classes src/UserAccount_itf.java
cd classes
jar cvf ../lib/UserAccount_itf.jar UserAccount_itf.class
cd ..

javac -d classes -classpath .:classes src/UserAccount_impl.java
cd classes
jar cvf ../lib/UserAccount_impl.jar UserAccount_impl.class
cd ..

javac -d classes -classpath .:classes src/Initialize_itf.java
cd classes
jar cvf ../lib/Initialize_itf.jar Initialize_itf.class
cd ..

javac -d classes -classpath .:classes src/Initialize_impl.java
cd classes
jar cvf ../lib/Initialize_impl.jar Initialize_impl.class
cd ..



javac -d classes -cp .:classes:lib/UserAccount_impl.jar:lib/Initialize_itf.jar:lib/Initialize_itf.jar src/MainNode.java
javac -d classes -cp .:classes:lib/UserAccount_impl.jar:lib/Initialize_itf.jar:lib/Initialize_itf.jar src/Clients.java


export CLASSPATH="lib/Initialize_itf.jar:lib/UserAccount_itf.jar"

