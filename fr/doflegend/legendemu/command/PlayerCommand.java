package fr.doflegend.legendemu.command;

import fr.doflegend.legendemu.client.Accounts;
import fr.doflegend.legendemu.client.Characters;
import fr.doflegend.legendemu.command.player.FmCac;
import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.SocketManager;
import fr.doflegend.legendemu.fight.object.Stalk;
import fr.doflegend.legendemu.game.GameSendThread;
import fr.doflegend.legendemu.kernel.Config;
import fr.doflegend.legendemu.kernel.LegendEmu;

public class PlayerCommand {

	public static boolean launchNewCommand(String msg, Characters _perso, Accounts _compte, long _timeLastsave, GameSendThread _out){
		
		if(msg.charAt(0) == '.') {
			
		    if(msg.length() > 4 && msg.substring(1, 5).equalsIgnoreCase("help")) {
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<b><u>Liste des commandes :</b></u>" +
					"\n.<b><u>infos</b></u> - Affiche les infos du serveur" +
					"\n.<b><u>aboinfos</b></u> - Instructions pour s'abonner" +
					"\n.<b><u>points</b></u> - Affiche le nombre de Points Boutique" +
					"\n.<b><u>debug</b></u> - Téléporte au dernier point de sauvegarde" +
										
					
					"\n\n<b><u>Commandes pour les abonnés :</b></u>" +
					"\n.<b><u>gc</b></u> + [MESSAGE] - Chat Global" +
					"\n.<b><u>fmcac</b></u> - FM son CaC (Points Requis)" +
					
					"\n\n.<b><u>traque</b></u> - Permet de lancer une traque" +
					"\n.<b><u>chercher</b></u> - Permet de chercher une cible" +
					"\n.<b><u>recompense</b></u> - Pour gagner la récompense");
				
				return true;
			}
			// DON'T RENAME IF YOU DON'T WANT TO BE HACKED ! by Starlight (Start)
			else if(msg.length() > 5 && msg.substring(1, 6).equalsIgnoreCase("infos")){
			// RESPECT MY WORK !
				// Kevin, don't rename !!!!!!!
				String mess =	"<b>Legend'Emu</b> by <b>Starlight</b>\n" // Don't rename Kevin !
					+			"Thanks to : WalakaZ, Return, Deathdown, Diabu\n\n"
					+			"Players : <b>"+LegendEmu.gameServer.getPlayerNumber()+"</b>\n"
					+			"Max Players : <b>"+LegendEmu.gameServer.getMaxPlayer()+"</b>";
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, mess);
				return true;
			} // PLEASE RESPECT MY WORK : Don't belete !
			// DON'T RENAME IF YOU DON'T WANT TO BE HACKED ! by Starlight (End)
		    
			else if(msg.length() > 5 && msg.substring(1, 6).equalsIgnoreCase("debug")){
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<u>Avertissement :</u>\n\nLa commande debug a été conçu uniquement pour vous aider lorsque vous êtes bloqué sur une map noire ou une map sans triggers. Toute utilisation de cette commande sera strictement surveillé par Starlight. Vous risquez un ban définitif si vous l'utilisez à mauvais escient.\n\nPour continuer, écrivez dans le tchat .godebug");
				return true;
			}
		    
			else if(msg.length() > 7 && msg.substring(1, 8).equalsIgnoreCase("godebug")){
                if(_perso.get_fight() != null || _perso.get_kamas() == 0 || _perso.get_savePos() == null) { // On vérifie que le joueur n'est pas en combat et qu'il a au moins 1 kamas sur son perso (pour éviter le flood)
    				SocketManager.GAME_SEND_MESSAGE(_perso, "<b>Vous êtes en combat ou vous n'avez pas de Kamas. Action impossible.</b>", Config.CONFIG_MOTD_COLOR);
                    return true;
                } else {
				// TODO : Antiflood strict
				short mapid = _perso.get_curCarte().get_id(); // On cherche la MapID actuelle du personnage
				int cellid = _perso.get_curCell().getID(); // On cherche la cell id actuelle du personnage
				SQLManager.INSERT_DEBUG_LOG(_perso.get_name(), _perso.get_compte().get_name(), _perso.get_compte().get_gmLvl(), mapid, cellid); // On log le tout dans la base de donnée
				
				_perso.warpToSavePos(); //On tp le perso à son dernier point de sauvegarde
				SocketManager.GAME_SEND_MESSAGE(_perso, "<b>Vous avez été téléporté au dernier point de sauvegarde avec succès.</b>", Config.CONFIG_MOTD_COLOR);
				return true;
			}
			}
		    
			else if(msg.length() > 3 && msg.substring(1, 4).equalsIgnoreCase("vie")){
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		   
			else if(msg.length() > 4 && msg.substring(1, 5).equalsIgnoreCase("life")){
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		    
			else if(msg.length() > 4 && msg.substring(1, 5).equalsIgnoreCase("vida")){
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		    
			else if(msg.length() > 4 && msg.substring(1, 5).equalsIgnoreCase("save")){
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		    
			else if(msg.length() > 3 && msg.substring(1, 4).equalsIgnoreCase("vip")){
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		    
			else if(msg.length() > 4 && msg.substring(1, 5).equalsIgnoreCase("shop")){
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		    
			else if(msg.length() > 5 && msg.substring(1, 6).equalsIgnoreCase("staff")){
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		    
			else if(msg.length() > 4 && msg.substring(1, 5).equalsIgnoreCase("koli")){
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		    
			else if(msg.length() > 6 && msg.substring(1, 7).equalsIgnoreCase("traque")){
				if(_perso.get_compte().get_subscriber() == 0 && Config.USE_SUBSCRIBE)
				{
					SocketManager.PERSO_SEND_EXCHANGE_REQUEST_ERROR(_perso,'S');
					return true;
				}
				Stalk.newTraque(_perso);
				return true;
			}
			
			else if(msg.length() > 8 && msg.substring(1, 9).equalsIgnoreCase("chercher")){
				if(_perso.get_compte().get_subscriber() == 0 && Config.USE_SUBSCRIBE)
				{
					SocketManager.PERSO_SEND_EXCHANGE_REQUEST_ERROR(_perso,'S');
					return true;
				}
				Stalk.getTraquePosition(_perso);
				return true;
			}
			
			else if(msg.length() > 10&& msg.substring(1,11).equalsIgnoreCase("recompense")){
				if(_perso.get_compte().get_subscriber() == 0 && Config.USE_SUBSCRIBE)
				{
					SocketManager.PERSO_SEND_EXCHANGE_REQUEST_ERROR(_perso,'S');
					return true;
				}
				Stalk.getRecompense(_perso);
				return true;
			}
			
			else if(msg.length() > 5 && msg.substring(1, 6).equalsIgnoreCase("fmcac")) {
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, "Attention ! Fm votre CaC vous coûtera " + Config.PRICE_FM_CAC + " points.\n Si vous souhaitez continuer, entrez la commande .gofmcac dans le chat.\n\nAttention ! Vous devez être abonné pour FM votre Cac !");
				return true;
			}

			else if(msg.length() > 7 && msg.substring(1, 8).equalsIgnoreCase("gofmcac")) {
				return FmCac.exec(_perso, msg);
			}
			
			else if(msg.length() > 2 && msg.substring(1, 3).equalsIgnoreCase("gc")){
				
				if(_perso.get_compte().get_subscriber() == 0 && Config.USE_SUBSCRIBE || _perso.get_lvl() < 6)
				{
					SocketManager.GAME_SEND_Im_PACKET(_perso, "0157;"+ "6");
					return true;
				}
				
						String RangP = "";
						if (_perso.get_compte().get_gmLvl() == 0)
							RangP = "Joueur";
						if (_perso.get_compte().get_gmLvl() >= 1)
							RangP = "Staff";
						String prefix = _perso.get_name();
						String infos = msg.substring(4, msg.length() - 1);
						String clicker_name = "<a href='asfunction:onHref,ShowPlayerPopupMenu,"+_perso.get_name()+"'>"+prefix+"</a>";
						SocketManager.GAME_SEND_MESSAGE_TO_ALL((new StringBuilder("("+RangP+")<b> ")).append(clicker_name).append("</b> : ").append(infos).toString(), "000099");
						
					return true;
			}
			
			else if(msg.length() > 8 && msg.substring(1, 9).equalsIgnoreCase("aboinfos")) {
				if (_perso.get_compte().get_subscriber() == 0)
					SocketManager.GAME_SEND_BAIO_PACKET(_perso, 
				"<U>Statut du compte :</U>" + 
				"\nStatut : Non Abonné" +
				"\nPoints Boutique : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) +
						
				"\n\n<U>Temps et prix de l'abonnement :</U>" + 
				"\n.aboS pour une Semaine " + "(" + Config.PRICE_BUY_7DAY + " Points)." + 
				"\n.abo1M pour 1 Mois " + "(" + Config.PRICE_BUY_1MONTH + " Points)." + 
				"\n.abo3M pour 3 Mois " + "(" + Config.PRICE_BUY_3MONTH + " Points)." + 
				"\n.abo6M pour 6 Mois " + "(" + Config.PRICE_BUY_6MONTH + " Points)." + 
				"\n.aboY pour une Année " + "(" + Config.PRICE_BUY_YEAR + " Points)." + 
				"\n\n<U>Comment s'abonner ?</U>" + 
				"\nIl vous suffit d'entrer le premier mot de la commande souhaité dans le chat." + 
				"\nExemple : Pour acheter un abonnement de une semaine, vous devrez écrire dans le chat : .aboS)");
				if (_perso.get_compte().get_subscriber() != 0)
					SocketManager.GAME_SEND_BAIO_PACKET(_perso, 
							"<U>Statut du compte :</U>" + 
							"\nStatut : Abonné" +
							"\nPoints Boutique : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) +
									
							"\n\n<U>Temps et prix de l'abonnement :</U>" + 
							"\n.aboS pour une Semaine " + "(" + Config.PRICE_BUY_7DAY + " Points)." + 
							"\n.abo1M pour 1 Mois " + "(" + Config.PRICE_BUY_1MONTH + " Points)." + 
							"\n.abo3M pour 3 Mois " + "(" + Config.PRICE_BUY_3MONTH + " Points)." + 
							"\n.abo6M pour 6 Mois " + "(" + Config.PRICE_BUY_6MONTH + " Points)." + 
							"\n.aboY pour une Année " + "(" + Config.PRICE_BUY_YEAR + " Points)." + 
							"\n\n<U>Comment s'abonner ?</U>" + 
							"\nIl vous suffit d'entrer le premier mot de la commande souhaité dans le chat." + 
							"\nExemple : Pour acheter un abonnement de une semaine, vous devrez écrire dans le chat : .aboS)");	
				return true;
			}
			
			else if(msg.length() > 4 && msg.substring(1, 5).equalsIgnoreCase("aboS")) {
				long ActualTimeStamp = System.currentTimeMillis()/1000;
				int AddSubscriptionTime;
				int AddNewSubscription;
				int timestamp7d = 604800;
				int newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_7DAY;
								
				if (SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) < Config.PRICE_BUY_7DAY) {
					SocketManager.GAME_SEND_MESSAGE(_perso, "Action impossible, il te manque "
					+ (Config.PRICE_BUY_7DAY - SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + " Points Boutique."), Config.CONFIG_MOTD_COLOR);
					return true;
				}
				
				if(_perso.get_compte().get_subscriber() == 0)
				{
					newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_7DAY;
					SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
					
					AddNewSubscription = (int) (ActualTimeStamp + timestamp7d);
					_perso.get_compte().set_subscribe(AddNewSubscription);
					SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
					SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : Une Semaine" + "\nPrix : " + Config.PRICE_BUY_7DAY + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
					return true;
				}
								
				newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_7DAY;
				SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
				
				AddSubscriptionTime = (int) (_perso.get_compte().get_subscriberInt() + timestamp7d);
				_perso.get_compte().set_subscribe(AddSubscriptionTime);
				SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : Une Semaine" + "\nPrix : " + Config.PRICE_BUY_7DAY + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
				return true;
			}
			
		    else if(msg.length() > 5 && msg.substring(1, 6).equalsIgnoreCase("abo1M")) {
				long ActualTimeStamp = System.currentTimeMillis()/1000;
				int AddSubscriptionTime;
				int AddNewSubscription;
				int timestamp1m = 2592000;
				int newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_1MONTH;
								
				if (SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) < Config.PRICE_BUY_1MONTH) {
					SocketManager.GAME_SEND_MESSAGE(_perso, "Action impossible, il te manque "
					+ (Config.PRICE_BUY_1MONTH - SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + " Points Boutique."), Config.CONFIG_MOTD_COLOR);
					return true;
				}
				
				if(_perso.get_compte().get_subscriber() == 0)
				{
					newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_1MONTH;
					SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
					
					AddNewSubscription = (int) (ActualTimeStamp + timestamp1m);
					_perso.get_compte().set_subscribe(AddNewSubscription);
					SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
					SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : 1 Mois" + "\nPrix : " + Config.PRICE_BUY_1MONTH + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
					return true;
				}
								
				newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_1MONTH;
				SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
				
				AddSubscriptionTime = (int) (_perso.get_compte().get_subscriberInt() + timestamp1m);
				_perso.get_compte().set_subscribe(AddSubscriptionTime);
				SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : 1 Mois" + "\nPrix : " + Config.PRICE_BUY_1MONTH + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
				return true;
			}
			
		    else if(msg.length() > 5 && msg.substring(1, 6).equalsIgnoreCase("abo3M")) {
				long ActualTimeStamp = System.currentTimeMillis()/1000;
				int AddSubscriptionTime;
				int AddNewSubscription;
				int timestamp3m = 7776000;
				int newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_3MONTH;
								
				if (SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) < Config.PRICE_BUY_3MONTH) {
					SocketManager.GAME_SEND_MESSAGE(_perso, "Action impossible, il te manque "
					+ (Config.PRICE_BUY_3MONTH - SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + " Points Boutique."), Config.CONFIG_MOTD_COLOR);
					return true;
				}
				
				if(_perso.get_compte().get_subscriber() == 0)
				{
					newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_3MONTH;
					SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
					
					AddNewSubscription = (int) (ActualTimeStamp + timestamp3m);
					_perso.get_compte().set_subscribe(AddNewSubscription);
					SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
					SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : 3 Mois" + "\nPrix : " + Config.PRICE_BUY_3MONTH + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
					return true;
				}
								
				newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_3MONTH;
				SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
				
				AddSubscriptionTime = (int) (_perso.get_compte().get_subscriberInt() + timestamp3m);
				_perso.get_compte().set_subscribe(AddSubscriptionTime);
				SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : 3 Mois" + "\nPrix : " + Config.PRICE_BUY_3MONTH + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
				return true;
			}
			
		    else if(msg.length() > 5 && msg.substring(1, 6).equalsIgnoreCase("abo6M")) {
				long ActualTimeStamp = System.currentTimeMillis()/1000;
				int AddSubscriptionTime;
				int AddNewSubscription;
				int timestamp6m = 15638400;
				int newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_6MONTH;
								
				if (SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) < Config.PRICE_BUY_6MONTH) {
					SocketManager.GAME_SEND_MESSAGE(_perso, "Action impossible, il te manque "
					+ (Config.PRICE_BUY_6MONTH - SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + " Points Boutique."), Config.CONFIG_MOTD_COLOR);
					return true;
				}
				
				if(_perso.get_compte().get_subscriber() == 0)
				{
					newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_6MONTH;
					SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
					
					AddNewSubscription = (int) (ActualTimeStamp + timestamp6m);
					_perso.get_compte().set_subscribe(AddNewSubscription);
					SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
					SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : 6 Mois" + "\nPrix : " + Config.PRICE_BUY_6MONTH + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
					return true;
				}
								
				newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_6MONTH;
				SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());

				
				AddSubscriptionTime = (int) (_perso.get_compte().get_subscriberInt() + timestamp6m);
				_perso.get_compte().set_subscribe(AddSubscriptionTime);
				SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : 6 Mois" + "\nPrix : " + Config.PRICE_BUY_6MONTH + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
				return true;
			}
			
		    else if(msg.length() > 4 && msg.substring(1, 5).equalsIgnoreCase("aboY")) {
				long ActualTimeStamp = System.currentTimeMillis()/1000;
				int AddSubscriptionTime;
				int AddNewSubscription;
				int timestampYear = 31536000;
				int newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_YEAR;
								
				if (SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) < Config.PRICE_BUY_YEAR) {
					SocketManager.GAME_SEND_MESSAGE(_perso, "Action impossible, il te manque "
					+ (Config.PRICE_BUY_YEAR - SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + " Points Boutique."), Config.CONFIG_MOTD_COLOR);
					return true;
				}
				
				if(_perso.get_compte().get_subscriber() == 0)
				{
					newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_YEAR;
					SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
					
					AddNewSubscription = (int) (ActualTimeStamp + timestampYear);
					_perso.get_compte().set_subscribe(AddNewSubscription);
					SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
					SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : 1 Année" + "\nPrix : " + Config.PRICE_BUY_YEAR + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
					return true;
				}
								
				newpoints = SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) - Config.PRICE_BUY_YEAR;
				SQLManager.SET_ACCOUNT_POINTS(newpoints, _perso.getAccID());
				
				AddSubscriptionTime = (int) (_perso.get_compte().get_subscriberInt() + timestampYear);
				_perso.get_compte().set_subscribe(AddSubscriptionTime);
				SQLManager.UPDATE_SUBSCRIPTION(_perso.get_compte(), _perso.get_compte().get_subscriberInt());
				SocketManager.GAME_SEND_BAIO_PACKET(_perso, "<U>Résumé de l'achat :</U>" + "\n\nTemps d'abonnement acheté : 1 Année" + "\nPrix : " + Config.PRICE_BUY_YEAR + " Points Boutique" + "\nPoint(s) restant(s) : " + SQLManager.GET_ACCOUNT_POINTS(_perso.getAccID()) + "\n\nImportant : N'oublie pas de déco/reco pour appliquer ton abonnement.");
				return true;
			}
		    
			   else if (msg.substring(1, 7).equalsIgnoreCase("points")) {
				   SocketManager.GAME_SEND_MESSAGE(_perso, "Vous avez " + SQLManager.GET_ACCOUNT_POINTS(_perso.get_compte().get_GUID()) + " Points Boutique.", Config.CONFIG_MOTD_COLOR);
			        return true;
				}
			
			else {
				SocketManager.GAME_SEND_MESSAGE(_perso, "Commande non reconnue ou incomplète ! Exécutez <b>.help</b> pour la liste !", Config.CONFIG_MOTD_COLOR);
				return true;
			}
		}
		return false;
	}
}
