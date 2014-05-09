package fr.doflegend.legendemu.login.server.packet;

import fr.doflegend.legendemu.client.Accounts;
import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.World;
import fr.doflegend.legendemu.login.server.Client;
import fr.doflegend.legendemu.login.server.Client.Status;


public class AccountName {
	
	public static void verify(Client client, String name) {
		try {
			if(World.getCompteByName(name.toLowerCase()) == null)
				SQLManager.LOAD_ACCOUNT_BY_USER(name.toLowerCase());
			
			Accounts account = World.getCompteByName(name.toLowerCase());
			
			client.setAccount(account);
		} catch(Exception e) {
			client.send("AlEf");
			client.kick();
			return;
		}
		
		if(client.getAccount() == null) {
			client.send("AlEf");
			client.kick();
			return;
		}
		
		client.setStatus(Status.WAIT_PASSWORD);
	}
}
