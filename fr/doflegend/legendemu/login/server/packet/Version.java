package fr.doflegend.legendemu.login.server.packet;

import fr.doflegend.legendemu.login.server.Client;
import fr.doflegend.legendemu.login.server.LoginServer;
import fr.doflegend.legendemu.login.server.Client.Status;

/**
 * Copyright 2012 Lucas de Chaussé - Sérapsis
 */

public class Version {
	
	public static void verify(Client client, String version) {
		if(!version.equalsIgnoreCase(LoginServer.version)){ 
			client.send("AlEv" + LoginServer.version);
			client.kick();
		}
		
		client.setStatus(Status.WAIT_ACCOUNT);
	}
}
