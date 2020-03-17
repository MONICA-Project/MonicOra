The Server side for the Ora glasses used to communicate. It will open a server socket on the port configure in the config file and listen to incomming connection from the glasses,
using the key file for encryption. 
Comunnication is done through encrypted (AES) TCP using raw sockets and Bytebuffer.

This package is used for the integration with MonicOra by providing the interface for communication: 

https://gitlab.com/Smixi/monicoraintegration/
https://gitlab.com/Smixi/MNC_OPT

Toolbox and instruction are also available inside the .7z file, with the javadoc.