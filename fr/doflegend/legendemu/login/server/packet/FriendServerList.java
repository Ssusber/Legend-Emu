package fr.doflegend.legendemu.login.server.packet;

import fr.doflegend.legendemu.client.Accounts;
import fr.doflegend.legendemu.common.World;
import fr.doflegend.legendemu.kernel.Config;
import fr.doflegend.legendemu.login.server.Client;

/**
 * Copyright 2012 Lucas de Chaussé - Sérapsis
 */

public class FriendServerList {
	
	public static void get(Client client, String packet) {
		try {
			Accounts account = World.getCompteByPseudo(packet);
			
			if(account == null) {
				client.send("AF");
				return;
			}
			
			client.send("AF" + Config.CONFIG_SERVER_ID + ";" + account.GET_PERSO_NUMBER() + ';');
		} catch(Exception e) { }
	}
}
