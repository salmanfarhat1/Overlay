# Overlay
#Two versions of Overlay Grid 
#V1 using RMI 
#V2 using RabbitMQ
#How to run
* ./runScript.sh
* export CLASSPATH="lib/Initialize_itf.jar:lib/ServerReg_itf.jar:lib/VirtualTranslation_itf.jar"
* rmiregistry &
#4- ./mainNodeServer.sh
#then open new terminal run as much as you want nodes using 
#5- ./serversScript.sh

#don't forget to kill the rmiregistry before compiling agian  
