package fr.doflegend.legendemu.game;

import fr.doflegend.legendemu.client.Accounts;
import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.SocketManager;
import fr.doflegend.legendemu.kernel.*;

public class CharacterName {
	
	public static void isValid(String packet, GameThread gameThread) {
		GameSendThread out = gameThread.getOut();
		String name = null;
		
		//verification des failles
		if(Security.isCompromised(packet, gameThread.getPerso())) return;
		
		try {
			Accounts account = gameThread.getAccount();
			
			if(account.get_curPerso() != null) {
				SocketManager.send(out, "BN");
				SocketManager.send(out, "AAE");
				return;
			}
			
			String[] infos = packet.substring(2).split("\\|");
			name = infos[0];
			int race = Integer.parseInt(infos[1]);
			
			//test if name exist
			if(SQLManager.persoExist(name)) {
				SocketManager.send(out, "BN");
				SocketManager.send(out, "AAEa");
				return;
			}
			
			if(name.toLowerCase().contains("modo") || 
					name.toLowerCase().contains("mj") || 
					name.toLowerCase().contains("admin")) {
				SocketManager.send(out, "BN");
				SocketManager.send(out, "AAEa");
				return;
			}
			
			//test if account is full
			if(account.get_persos().size() > Config.CONFIG_MAX_PERSOS - 1) {
				SocketManager.send(out, "BN");
				SocketManager.send(out, "AAEf");
				return;
			}
			
			//for the wpe pro user :p
			if(race > 12 || race < 1) {
				SocketManager.send(out, "BN");
				SocketManager.send(out, "AAE");
				return;
			}
			
			if(account.createPerso(infos[0], 
					Integer.parseInt(infos[2]), 
					Integer.parseInt(infos[1]), 
					Integer.parseInt(infos[3]),
					Integer.parseInt(infos[4]), 
					Integer.parseInt(infos[5]))) {
				SocketManager.send(out, "BN");
				SocketManager.send(out, "AAK");
				SocketManager.GAME_SEND_PERSO_LIST(out, account.get_persos(), account.get_subscriber());
				SocketManager.send(out, "TB");
			} else SocketManager.GAME_SEND_CREATE_FAILED(out);
		} catch(Exception e) {
			SocketManager.send(out, "BN");
			SocketManager.send(out, "AAE");
			return;
		}
	}
}
