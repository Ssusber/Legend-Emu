package fr.doflegend.legendemu.login.server.packet;

import fr.doflegend.legendemu.client.Accounts;
import fr.doflegend.legendemu.common.World;
import fr.doflegend.legendemu.login.server.Client;
import fr.doflegend.legendemu.login.server.Client.Status;

/**
 * Copyright 2012 Lucas de Chaussé - Sérapsis
 */

public class ChooseNickName {
	
	public static void verify(Client client, String nickname) {
		Accounts account = client.getAccount();
		
		if(!account.get_pseudo().isEmpty()) {
			client.kick();
			return;
		}
		
		if(nickname.toLowerCase().equals(account.get_name().toLowerCase())) {
			client.send("AlEr");
			return;
		}
		
		String s[] = {"admin", "modo", " ", "&", "é", "\"", "'", 
				"(", "-", "è", "_", "ç", "à", ")", "=", "~", "#",
				"{", "[", "|", "`", "^", "@", "]", "}", "°", "+",
				"^", "$", "ù", "*", ",", ";", ":", "!", "<", ">",
				"¨", "£", "%", "µ", "?", ".", "/", "§", "\n"};
		
		for(int i = 0; i < s.length; i++) {
			if(nickname.contains(s[i])) {
				client.send("AlEs");
				break;
			}
		}
		
		if(World.getCompteByPseudo(nickname) != null) {
			client.send("AlEs");
			return;
		}
		
		client.getAccount().set_pseudo(nickname);
		client.setStatus(Status.SERVER);
		
		AccountQueue.verify(client);
	}
}
