package fr.doflegend.legendemu.kernel;

import java.util.TimerTask;

import fr.doflegend.legendemu.common.Constant;
import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.SocketManager;
import fr.doflegend.legendemu.common.World;
import fr.doflegend.legendemu.game.GameServer;
import fr.doflegend.legendemu.kernel.Console.Color;
import fr.doflegend.legendemu.login.server.LoginServer;

// Kevin don't rename the emulator ! =)

/**
 * Reprise d'ICore par Return pour Himalaya
 * Rebrum Solem est développé par -WalakaZ-
 * 
 * Changelog : 
 * 
 * revision 0.1 : 
 * 	-Nombreuse correction au niveau des conventions de nommages - 3 heures de taff
 * 	-Nouvelle console, plus jolie et simple à utiliser - 1 heure de taff
 *  -Chargement plus efficace, rapide et comprehenssible - 2 heures de taff
 *  -Nouveau serveur de socket pour le serveur de connection - 12 heures de taff
 *  -Generation de pseudo à la connection moins absurde - 3 heures de taff
 *  -UI de création de pseudo à la connection s'il n'y en à pas déjà - 30 minutes de taff
 *  -Recherche d'amis par serveur avant la création du perso - 1 heure de taff
 *  -Sécurisation des connections(wpe pro, flood conenctions, bot, etc....) - 1 heure de taff
 *  -Enorme faille de création de chacha et de téleportation corrigée - 5 minutes de taff
 *  -Faille de géneration de kamas corrigée - 5 minutes de taff
 *  -Faille de géneration de dofus cawotte corrigée - 5 minutes de taff
 *  -Faille d'ouverture de banque n'importe ou corrigée - 5 minutes de taff
 *  -Faille de duplication de n'importe quel objet via supression corrigé - 30 minutes de taff
 *  -Faille de duplication de n'importe quel objet via poses au sol corrigé - 20 minutes de taff
 *  -Correction d'un petit bug sur la commande .stuff - 5 minutes de taff
 *  -Correction de 25 failles au niveau des échanges d'item(hdv compris) pouvant engendrer duplication(valeur négative) - 25 minutes de taff
 *  -Correction des soins des baguettes Rhon et diverses autres CaC - 1 heures 30 de taff
 *  -Refonte du system de création de personnage pour éviter certainnes failles et bug - 45 minutes de taff
 *  -Correction du bug punition qui donné de la vie avec Immunité - 20 minutes de taff
 *  -Correction du piège repulsif - 10 minutes de taff
 *  -Correction du bug double tour avec sac de l'énutrof - 10 minutes de taff
 *  -Correction du bug d'invisibilité d'autrui avec punition, le joueur s'affiche maintenant - 30 minutes de taff
 *  -Correction des invocations static impossible de les déplacer à présent - 30 minutes de taff
 *  -Correction des résistances 50 au lieu de 100 ( graphiquement ) - 30 minutes de taff
 *  
 * revision 0.2 : 
 * 	-Correction d'un bug d'affichage de la guilde et de la dragodinde - 35 minutes de taff
 *  -Correction des ailes du doubles, elle s'affiche maintenant correctement - 1 heure 30 de taff
 *  -Re-Correction des invocations static ( oublie :s ) - 2 heure 45 minutes de taff
 *  -Correction sur la connection
 *  -Reboot automatique avec nétoyage de la JVM à chaque fois que le system est kill
 *  ----> Cette révision n'est pas terminée!
 * 
 *


 * @author Starlight
 */

public class LegendEmu {
	
	//Thread Groups
	public static ThreadGroup THREAD_GAME = null;
	public static ThreadGroup THREAD_GAME_SEND = null;
	public static ThreadGroup THREAD_REALM = null;
	public static ThreadGroup THREAD_IA = null;
	public static ThreadGroup THREAD_SAVE = null;
	
	//System
	public static double revision = 0.3;
	public static boolean isRunning = false;
	
	public static GameServer gameServer;
	public static LoginServer loginServer;
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Reboot.start();
			}
		});
		
		THREAD_GAME = new ThreadGroup(Thread.currentThread().getThreadGroup(), "GameThread");
		THREAD_REALM = new ThreadGroup(Thread.currentThread().getThreadGroup(), "RealmThread");
		THREAD_IA = new ThreadGroup(Thread.currentThread().getThreadGroup(), "IaThread");
		THREAD_SAVE = new ThreadGroup(Thread.currentThread().getThreadGroup(), "SaveThread");
		THREAD_GAME_SEND = new ThreadGroup(Thread.currentThread().getThreadGroup(), "GameThreadSend");
	}
	
	public static void main(String[] arg) {
		Console.setTitle("Loading...");
		Console.clear();
		header();
		
		long startTime = System.currentTimeMillis();
				
		Console.setTitle("Loading Configuration...");
		Console.println("Loading Configuration : " + Config.load(), Color.MAGENTA);
		
		Console.setTitle("Loading Database...");
		if(SQLManager.setUpConnexion()) Console.println("Connection to the database : Ok", Color.GREEN);
		
		else {
			Console.println("Connection to the database : Error", Color.RED);
			System.exit(0);
		}
		
		switch(Config.DB_PASS) {
		  case "":
				Console.println("[WARNING] Your database is not secure, please add a password.\n", Color.RED);
		}
		
		//Chargement de la base de donnée
		Console.setTitle("Loading World...");
		World.createWorld();
		
		isRunning = true;
		Console.setTitle("Launching LoginServer...");
		loginServer = new LoginServer();
		loginServer.start();
		Console.setTitle("Launching GameServer...");
		gameServer = new GameServer(Config.IP);
		DynamicConsoleTitle();
		String start = ((System.currentTimeMillis() - startTime) / 1000) 
				+ "s ("+(System.currentTimeMillis() - startTime)+" ms)";
		
		Console.bright();
		Console.println("\nLegend'Emu started in " + start, Color.GREEN);
	      try {
	          Thread.sleep(1000);
	        }
	        catch (InterruptedException ex) {}
	    Clear();
		new ConsoleInputAnalyzer();
	}
	
	public static void header() {
		Console.print("			Legend'Emu for Dofus 1.29.1\n", Color.WHITE);
		Console.print("			Version : ", Color.WHITE); Console.print(Constant.EMU_VERSION + "\n", Color.WHITE);
		Console.print("			by Starlight (http://doflegend.fr/)\n\n\n", Color.WHITE);
		Console.print("Thanks to : WalakaZ, Return, Deathdown, Diabu\nDon't forget to recense your server before use !\n", Color.WHITE);
		Console.print("-------------------------------------------------------------------------------\n\n", Color.WHITE);
	}
	
	public static void DynamicConsoleTitle() {
	       {
	        	java.util.Timer timer = new java.util.Timer();
	            timer.schedule (new TimerTask() {
	                public void run()
	                {
	            		long uptime = System.currentTimeMillis() - LegendEmu.gameServer.getStartTime();
	            		int jour = (int) (uptime/(1000*3600*24));
	            		uptime %= (1000*3600*24);
	            		int hour = (int) (uptime/(1000*3600));
	            		uptime %= (1000*3600);
	            		int min = (int) (uptime/(1000*60));
	            		uptime %= (1000*60);
	            		int sec = (int) (uptime/(1000));
	        			Console.setTitle("Connected : " + LegendEmu.gameServer.getPlayerNumber() + "|" + "Max Connected : " + LegendEmu.gameServer.getMaxPlayer() + "|" + "UpTime : " + jour + "j " + hour + "h " + min + "m " + sec + "s");
	                }
	            }, 0, 1000);
	        }
	}
	
	public static void Clear()
	{
		Console.clear();
		Console.print("Legend'Emu > ", Color.RED);
	}
	
	static void printRed(Object o) {
		Console.bright();
		Console.print(o, Color.RED);
	}
	
	static void printBlue(Object o) {
		Console.print(o, Color.BLUE);
	}

	
	public static void listThreads(boolean isError) throws Exception {
		Console.println("\nListage des threads", Color.YELLOW);
		
		if(isError) SocketManager.GAME_SEND_cMK_PACKET_TO_ADMIN("@", 0, "Thread", "La RAM est surchargée !");
		
		try {
			Console.println(gameServer.getPlayerNumber() + " player online", Color.GREEN);
			ThreadGroup threadg = Thread.currentThread().getThreadGroup();
			
			if(!Thread.currentThread().getThreadGroup().getName().equalsIgnoreCase("main")) threadg = threadg.getParent();
			
			Console.println(threadg.activeCount()+" thread active", Color.YELLOW);
			Thread[] threads = new Thread[threadg.activeCount()];
			threadg.enumerate(threads);
			
			for(Thread t : threads)
				if(!isError)
					Console.println(t.getThreadGroup().getName() + 
							" " + t.getName() + " (" + t.getState() + 
							") => " + t.getId(), Color.YELLOW);
		} catch(Exception e) { Console.println("listing error", Color.RED); }
		
		if(isError) {
			try {
				Console.println(THREAD_IA.activeCount() + " threads IA deleted", Color.YELLOW);
				Thread[] threadd = new Thread[THREAD_IA.activeCount()];
				THREAD_IA.enumerate(threadd);
				
				for(Thread t : threadd) t.interrupt();
				
				Console.println(THREAD_IA.activeCount() + " threads IA remaining", Color.GREEN);
			} catch(Exception e){
				e.printStackTrace();
				SocketManager.GAME_SEND_cMK_PACKET_TO_ADMIN("@", 0, "Thread", "Suppression impossible"); 
			} try {
				Console.println("Attempt to purge the ram", Color.YELLOW);
				Runtime r = Runtime.getRuntime();
				r.runFinalization();
				r.gc();
				System.gc();
				Console.println("Ram purged", Color.GREEN);
				gameServer.restartGameServer();
			} catch(Exception e1) { Console.println("impossible to purge the ram", Color.RED); }
		}
	}
}
