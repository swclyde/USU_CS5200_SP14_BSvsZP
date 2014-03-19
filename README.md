USU_CS5200_SP14_BSvsZP
======================

Brilliant Students vs. Zombie Professors

This repository contains the following directories: 

BSvsZP-GameRegistry -- Source Code

	This directory contains the complete solution for game registry.
	You can examine the source code and test cases, and run a local
	instance of the registry for testing.  Simply open the solution
	in VS and runs. You can then execute one or game servers (see next
	directory).  Just be sure to change the app.config file for the
	game servers so it references the local address for the registry.
 
BSvsZP-GameServer -- Executables only

	This directory containing the game server executable and libraries.
	Run the executable to start up an instance of the game server.  You
	can point it to your own instance of the game registry by editing
	the app.config file so the "GameRegistryEndPoint" setting is referrring
	the local end point, e.g.,

	<add key="GameRegistryEndPoint" value="LocalHttpBinding_IRegistrar" />

BSvsZP-Common -- Source Code

	The Common and Message packages, with include class definitions
	for all of the objects that will be passed among process.
