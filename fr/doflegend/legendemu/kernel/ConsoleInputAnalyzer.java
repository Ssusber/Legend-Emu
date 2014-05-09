package fr.doflegend.legendemu.kernel;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.doflegend.legendemu.client.Characters;
import fr.doflegend.legendemu.common.Constant;
import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.SocketManager;
import fr.doflegend.legendemu.common.World;
import fr.doflegend.legendemu.game.GameThread;
import fr.doflegend.legendemu.game.GameServer.SaveThread;
import fr.doflegend.legendemu.kernel.Config;
import fr.doflegend.legendemu.kernel.Console.Color;

public class ConsoleInputAnalyzer implements Runnable{
	private Thread _t;
	Characters _perso;

	public ConsoleInputAnalyzer() {
		this._t = new Thread(this);
		_t.setDaemon(true);
		_t.start();
	}
	
	@Override
	public void run() {
		while (LegendEmu.isRunning){
			Console console = System.console();
		    String command = console.readLine();
		    try{
		    evalCommand(command);
		    }catch(Exception e){}
		    finally {
		    	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
		    }
		}
	}
	public void evalCommand(String command)
	{
		String[] args = command.split(" ");
		String fct =args[0].toUpperCase();
		if(fct.equals("SAVE"))
		{
			Thread t = new Thread(new SaveThread());
			t.start();
		      try {
		          Thread.sleep(2000);
		        }
		        catch (InterruptedException ex) {}
			fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
		} else if(fct.equals("EXIT")) {
			
			SQLManager.LOAD_ACTION();
			System.exit(0);
			
		} else if (fct.equalsIgnoreCase("ALLGIFT")) {
			int gift = 0;
			
			try {
				gift = Integer.parseInt(args[1]);
			} catch (Exception e) {
			}
			for (Characters pj : World.getOnlinePersos()) {
				pj.get_compte().setCadeau(gift);
			}
			sendEcho("The item "
					+ gift + " is given to all connected players.");
			fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			
		} else if(fct.equals("STAFF")) {
			
	      	  try{
	      		 
	      		sendInfo("------------ Staff connected ---------------");
	      		ResultSet RS = SQLManager.executeQuery("SELECT pseudo from accounts WHERE logged = '1' AND level > '0';", Config.DB_NAME);
	      		while (RS.next()) {
	      			sendInfo("- "+RS.getString("pseudo"));
	 		    }
	     		RS.getStatement().close();
	    		RS.close();
	    		
	    		sendInfo("----------------------------------------");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
	      	  } catch(SQLException e){ e.printStackTrace();}
		} else if(fct.equals("TOOGLE_DEBUG")) {
			
			Config.debug = !Config.debug;
			if(Config.debug)
			{
				sendInfo("Debug actived !");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			}else
			{
				sendInfo("Debug disabled !");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			}
			
		} else if(fct.equals("TOOGLE_LOG")) {
			
			Config.canLog = !Config.canLog;
			if(Config.canLog) {
				sendInfo("Log actived !");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			}else {
				sendInfo("Log disabled !");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			}
		} else if(fct.equalsIgnoreCase("ANNOUNCE")) {	
			String announce = command.substring(9);
			String PrefixConsole = "<b>Server</b> : ";
			SocketManager.GAME_SEND_MESSAGE_TO_ALL(PrefixConsole+announce, Config.CONFIG_MOTD_COLOR);
			sendEcho("<Announce:> "+announce);
			fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
		} else if(fct.equals("CLS")) {
			
			LegendEmu.Clear();
			
		} else if(fct.equals("KICK")) {
		
			Characters perso = _perso;
			String name = null;
			try {
				name = command.substring(5);
			} catch(Exception e){};
			perso = World.getPersoByName(name);
			if(perso == null) {
				String mess = "The player don't exist.";
				sendError(mess);
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
				return;
			}
			if(perso.isOnline()) {
				perso.get_compte().getGameThread().kick();
				String mess = "Kick : "+perso.get_name();
				sendInfo(mess);
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			} else {
				String mess = "The player "+perso.get_name()+" is not connected.";
				sendError(mess);
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			}
			
		} else if(fct.equals("RELOADSERV")) {
			
			sendEcho("Reload configuration");
			Config.load();
            sendEcho("Reload datas :\n");
			SQLManager.LOAD_ITEMS_FULL();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_MOB_TEMPLATE();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_NPC_TEMPLATE();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_NPC_QUESTIONS();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_NPC_ANSWERS();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_COMPTES();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_MOUNTPARKS();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_MOUNTS();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_TRIGGERS();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_OBJ_TEMPLATE();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_ITEM_ACTIONS();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			SQLManager.LOAD_SORTS();
			fr.doflegend.legendemu.kernel.Console.print("\n");
			fr.doflegend.legendemu.kernel.Console.print("All datas has been reloaded.\n", Color.GREEN);
			fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
				
		} else if(fct.equals("?")||command.equals("HELP")) {
			
			sendInfo("------------Commands:------------");
			sendInfo("- SAVE for save the server.");
			sendInfo("- EXIT for close the server.");
			sendInfo("- PURGERAM for purge the ram.");
			sendInfo("- LOCK [1][0][2] for change server state.");
			sendInfo("- TOOGLE_DEBUG for enable/disable the debug mode.");
			sendInfo("- TOOGLE_LOG for enable/disable the logs system.");
			sendInfo("- INFOS for show server infos.");
			sendInfo("- WHO for show connected players.");
			sendInfo("- STAFF for find connected staff members.");
			sendInfo("- ANNOUNCE for send a message to player.");
			sendInfo("- TELEPORT [MapId][CellId][Pseudo] for teleport a player.");
			sendInfo("- KICK [Pseudo] for kick a player.");
			sendInfo("- ALLGIFT [Item] for give a item to all connected players.");
			sendInfo("- RELOADSERV for reload the server.");
			sendInfo("- CLS for clean the console.");
			sendInfo("- CTRL+C for stop the server.");
			sendInfo("- HELP or ? for the command list.");
			sendInfo("----------------------------------");
			fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			
		} else if(fct.equals("INFOS")) {
			
			long uptime = System.currentTimeMillis() - LegendEmu.gameServer.getStartTime();
			int jour = (int) (uptime/(1000*3600*24));
			uptime %= (1000*3600*24);
			int hour = (int) (uptime/(1000*3600));
			uptime %= (1000*3600);
			int min = (int) (uptime/(1000*60));
			uptime %= (1000*60);
			int sec = (int) (uptime/(1000));
			
			String mess =		"Legend'Emu V"+Constant.EMU_VERSION+" by Starlight for Dofus 1.29.1\n"
					+			"Uptime: "+jour+"d "+hour+"h "+min+"m "+sec+"s\n"
					+			"Connected : "+LegendEmu.gameServer.getPlayerNumber()+"\n"
					+			"Max Connected : "+LegendEmu.gameServer.getMaxPlayer()+"\n";		
			sendInfo(mess);
			fr.doflegend.legendemu.kernel.Console.print("Legend'Emu > ", Color.RED);
		} else if (fct.equalsIgnoreCase("WHO")) {
			String mess = "==========\n" + "List of connected players :";
			sendInfo(mess);
			int diff = LegendEmu.gameServer.getClients().size() - 30;
			for (byte b = 0; b < 30; b++) {
				if (b == LegendEmu.gameServer.getClients().size())
					break;
				GameThread GT = LegendEmu.gameServer.getClients().get(b);
				Characters P = GT.getPerso();
				if (P == null)
					continue;
				mess = P.get_name() + "(" + P.get_GUID() + ") ";

				switch (P.get_classe()) {
				case Constant.CLASS_FECA:
					mess += "Fec";
					break;
				case Constant.CLASS_OSAMODAS:
					mess += "Osa";
					break;
				case Constant.CLASS_ENUTROF:
					mess += "Enu";
					break;
				case Constant.CLASS_SRAM:
					mess += "Sra";
					break;
				case Constant.CLASS_XELOR:
					mess += "Xel";
					break;
				case Constant.CLASS_ECAFLIP:
					mess += "Eca";
					break;
				case Constant.CLASS_ENIRIPSA:
					mess += "Eni";
					break;
				case Constant.CLASS_IOP:
					mess += "Iop";
					break;
				case Constant.CLASS_CRA:
					mess += "Cra";
					break;
				case Constant.CLASS_SADIDA:
					mess += "Sad";
					break;
				case Constant.CLASS_SACRIEUR:
					mess += "Sac";
					break;
				case Constant.CLASS_PANDAWA:
					mess += "Pan";
					break;
				default:
					mess += "Unk";
				}
				mess += " ";
				mess += (P.get_sexe() == 0 ? "M" : "F") + " ";
				mess += P.get_lvl() + " ";
				mess += P.get_curCarte().get_id() + "("
						+ P.get_curCarte().getX() + "/"
						+ P.get_curCarte().getY() + ") ";
				mess += P.get_fight() == null ? "" : "Fight ";
				sendInfo(mess);
			}
			if (diff > 0) {
				mess = "And " + diff + " other characters.";
				sendInfo(mess);
			}
			mess = "==========";
			sendInfo(mess);
			fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			
		} else if (fct.equalsIgnoreCase("TELEPORT")) {
			short mapID = -1;
			int cellID = -1;
			try {
				mapID = Short.parseShort(args[1]);
				cellID = Integer.parseInt(args[2]);
			} catch (Exception e) {
			}
			;
			if (mapID == -1 || cellID == -1 || World.getCarte(mapID) == null) {
				String str = "MapID or cellID invalid";
				sendError(str);
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
				return;
			}
			if (World.getCarte(mapID).getCase(cellID) == null) {
				String str = "MapID or cellID invalid";
				sendError(str);
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
				return;
			}
			Characters target = _perso;
			if (args.length > 3)// Si un nom de perso est spécifié
			{
				target = World.getPersoByName(args[3]);
				if (target == null || target.get_fight() != null) {
					String str = "The player does not exist.";
					sendError(str);
					fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
					return;
				}
			}
			target.teleport(mapID, cellID);
			String str = "The player has been teleported";
			sendInfo(str);
			fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			
		} else if (fct.equalsIgnoreCase("PURGERAM")) {
			try {
				sendEcho("Try to purge Ram...");
				Runtime r=Runtime.getRuntime();
				try
				{
					r.runFinalization();
					r.gc();
					System.gc();
					sendInfo("Ram purged.");
					fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
				}
				catch(Exception e)
				{
					sendError("Impossible to purge the Ram.");
					fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
				}
			} catch (Exception e) {
				sendError("Impossible to purge the Ram.");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			}

		} else if (fct.equalsIgnoreCase("LOCK")) {
			byte LockValue = 1;// Accessible
				LockValue = Byte.parseByte(args[1]);

			if (LockValue > 2)
				LockValue = 2;
			if (LockValue < 0)
				LockValue = 0;
			World.set_state((short) LockValue);
			if (LockValue == 1) {
				sendEcho("Server accessible.");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			} else if (LockValue == 0) {
				sendEcho("Server no available.");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			} else if (LockValue == 2) {
				sendEcho("Serveur in save.");
				fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
			}
			
		} else {
			
			sendError("Unrecognized or incomplete command");
			fr.doflegend.legendemu.kernel.Console.print("\nLegend'Emu > ", Color.RED);
		}		
		}

	public static void sendInfo(String msg)
	{
		fr.doflegend.legendemu.kernel.Console.println(msg, Color.GREEN);
	}
	public static void sendError(String msg)
	{
		fr.doflegend.legendemu.kernel.Console.println(msg, Color.RED);
	}
	public static void send(String msg)
	{
		fr.doflegend.legendemu.kernel.Console.println(msg);
	}
	public static void sendDebug(String msg)
	{
		if(Config.debug) fr.doflegend.legendemu.kernel.Console.println(msg, Color.YELLOW);
	}
	public static void sendEcho(String msg)
	{
		fr.doflegend.legendemu.kernel.Console.println(msg, Color.BLUE);
	}
	
}
