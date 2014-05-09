package fr.doflegend.legendemu.kernel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import fr.doflegend.legendemu.common.Constant;
import fr.doflegend.legendemu.kernel.Console.Color;

public class Config {
	public static Timer repeatFmTimer = new Timer(); //Skryn/Return
	public static  Timer fightTimers = new Timer();
	public static String Time = "";
	public static boolean Allow_Clear_Console = true;
	public static boolean Allow_Refresh_Title = true;
	public static final double REDUCTION = 0.5;
	public static final long CONFIG_SPAM_VOTE = 15; //En minutes
	public static String CONFIG_LINK_VOTE;
	public static int PRISMES_DELAIS_NEW_POSE = 60;
	public static ArrayList<Integer> INCARNATIONS_ARMES = new ArrayList<Integer>();
	private static Map<Integer, Long> WhenHasPosePrism = new HashMap<Integer, Long>();
	private static int PosePrism = 0;
	public static ArrayList<Integer> CartesWithoutPrismes = new ArrayList<Integer>();
	public static String DB_NAME;
	public static int RATE_FM = 1;
	public static String IP = "127.0.0.1";
	public static int CONFIG_SERVER_ID = 1;
	public static boolean isInit = false;
	public static short CONFIG_MAP_JAIL = 8534;
	public static int CONFIG_CELL_JAIL = 297;
	public static boolean ANTI_SURCHARGE = false;
	public static String DB_HOST;
	public static String DB_USER;
	public static String DB_PASS;
	public static long FLOOD_TIME = 30000;
	public static String GAMESERVER_IP;
	public static int CONFIG_TRAQUE_DIFFERENCE = 15;
	public static String CONFIG_MOTD = "B30016";
	public static String CONFIG_MOTD_COLOR = "";
	public static boolean debug = false;
	public static PrintStream PS;
	public static boolean CONFIG_POLICY = false;
	public static int CONFIG_REALM_PORT = 443;
	public static int CONFIG_GAME_PORT 	= 5555;
	public static int CONFIG_MAX_PERSOS = 5;
	public static short CONFIG_START_MAP = 10298;
	public static int CONFIG_START_CELL = 314;
	public static short CONFIG_ZAAP_MAP = 7411;
	public static int CONFIG_ZAAP_CELL = 255;
	public static short CONFIG_MERCENAIRE_MAP = 10222;
	public static int CONFIG_MERCENAIRE_CELL = 416;
	public static short CONFIG_GOSTAFF_MAP = 1674;
	public static int CONFIG_GOSTAFF_CELL = 354;
	public static short CONFIG_GOEVENT_MAP = 164;
	public static int CONFIG_GOEVENT_CELL = 298;
	public static short CONFIG_SQUAT_MAP = 298;
	public static int CONFIG_SQUAT_CELL = 310;
	public static short CONFIG_PHENIX_MAP = 8534;
	public static int CONFIG_PHENIX_CELL = 297;
	public static short CONFIG_ENCLOS_MAP = 8747;
	public static int CONFIG_ENCLOS_CELL = 747;
	public static boolean CONFIG_ALLOW_MULTI = false;
	public static int CONFIG_START_LEVEL = 1;
	public static int CONFIG_START_KAMAS = 0;
	//public static int CONFIG_SAVE_TIME = 10*60*10000;
	public static int CONFIG_DROP = 1;
	public static boolean CONFIG_ZAAP = false;
	public static int CONFIG_LOAD_DELAY = 60000;
	public static int CONFIG_RELOAD_MOB_DELAY = 18000000;
	public static int CONFIG_PLAYER_LIMIT = 200;
	public static boolean CONFIG_IP_LOOPBACK = true;
	public static int XP_PVP = 10;
	public static boolean ALLOW_MULE_PVP = false;
	public static int XP_PVM = 1;
	public static int KAMAS = 1;
	public static int HONOR = 1;
	public static int XP_METIER = 1;
	public static boolean CONFIG_CUSTOM_STARTMAP;
	public static boolean CONFIG_USE_MOBS = false;
	public static boolean CONFIG_USE_IP = false;
	public static BufferedWriter Log_GameSock;
	public static BufferedWriter Log_Game;
	public static BufferedWriter Log_Realm;
	public static BufferedWriter Log_IpCheck;
	public static BufferedWriter Log_MJ;
	public static BufferedWriter Log_RealmSock;
	public static BufferedWriter Log_Shop;
	public static BufferedWriter Log_Debug;
	public static BufferedWriter Log_Chat;
	public static BufferedWriter Log_FM; //Return / Skryn ;D
	public static boolean canLog;
	public static boolean isSaving = false;
	public static boolean AURA_SYSTEM = false;
	public static ArrayList<Integer> craqueleurMap = new ArrayList<Integer>(41);
	public static ArrayList<Integer> abraMap = new ArrayList<Integer>(11);
	//Arene
	public static ArrayList<Integer> arenaMap = new ArrayList<Integer>(8);
	public static int CONFIG_ARENA_TIMER = 10*60*1000;// 10 minutes
	//BDD
	public static int CONFIG_DB_COMMIT = 30*1000;
	//Inactivité
	public static int CONFIG_MAX_IDLE_TIME = 60000;//En millisecondes
	//HDV
	public static ArrayList<Integer> NOTINHDV = new ArrayList<Integer>();
	//Challenges et Etoiles
	public static int CONFIG_SECONDS_FOR_BONUS = 3600; 
	public static int CONFIG_BONUS_MAX = 100;
	// Temps en combat
	public static long CONFIG_MS_PER_TURN = 45000;
	public static long CONFIG_MS_FOR_START_FIGHT = 45000;
	// Taille Percepteur
	public static boolean CONFIG_TAILLE_VAR = true;
	//Sauvegarde automatique
	/////////////////////////////////////////////////public static int CONFIG_LOAD_SAVE = 6800;
	//Xp en défi
	public static boolean CONFIG_XP_DEFI = false;
	//Message de "bienvenue"
	public static String CONFIG_MESSAGE_BIENVENUE = "";
	public static short CONFIG_MAP_SHOP = 10298;
	public static int CONFIG_CELL_SHOP = 314;
	public static short CONFIG_MAP_PVM = 10298;
	public static int CONFIG_CELL_PVM = 314;
	public static short CONFIG_MAP_PVM2 = 10298;
	public static int CONFIG_CELL_PVM2 = 314;
	public static short CONFIG_MAP_PVM3 = 10298;
	public static int CONFIG_CELL_PVM3 = 314;
	public static short CONFIG_MAP_PVP = 10298;
	public static int CONFIG_CELL_PVP = 314;
	public static short CONFIG_MAP_SQUATT = 10298;
	public static int CONFIG_CELL_SQUATT = 314;
	public static String CONFIG_COLOR_GLOBAL = "002CBB";
	// Système de pub : Taparisse
	public static int CONFIG_LOAD_PUB = 60000;
    public static String PUB1 = "";
	public static String PUB2 = "";
	public static String PUB3 = "";
	public static String RESTRICTED_MAPS = "";
	public static String CONFIG_COLOR_BLEU = "3366FF";
	public static boolean CONFIG_PUB = false;
	// Reben
	public static String CONFIG_SORT_INDEBUFFABLE = new String();
	public static ArrayList<Integer> CONFIG_MORPH_PROHIBIDEN = new ArrayList<Integer>();
	//KOLIMAPS
	public static String KOLIMAPS;
	//Other Koli
	public static int KOLIMAX_PLAYER;
	public static int KOLI_LEVEL;
	public static int COINS_ID;
	public static int RATE_COINS;
	//Formule de tacle
	//0% | 50% | 50% | 80% | 100%
	//AgiTacleur>X , AgiTacleur>X,X , AgiCible>X,X , AgiCible>X,X , AgiCible>X
	public static String TACLE_FORMULA = "50,1,50,1,50,50,100,100";
	public static String TACLE_PERCENTAGE = "0,50,50,80,100";
	public static int KOLIZEUM_DELAIS_LEFT_FIGHT = 5; //Par default 5 minutes
	public static int PRICE_FM_CAC; // Prix en points boutiques pour FM un cac.
	//Abonnement
	public static boolean USE_SUBSCRIBE = false;
	static BufferedReader config;
	//Achat d'Abonnement IG by Starlight
	public static boolean ENABLE_IG_SUBSCRIPTION_BUY = false;
	public static int PRICE_BUY_7DAY = 0;
	public static int PRICE_BUY_1MONTH = 0;
	public static int PRICE_BUY_3MONTH = 0;
	public static int PRICE_BUY_6MONTH = 0;
	public static int PRICE_BUY_YEAR = 0;
	
	boolean isLoaded = false;
	
	public static boolean load() {
		try {
			config = new BufferedReader(new FileReader("config.properties"));
			String line = "";
			
			while((line = config.readLine()) != null) {
				
				if(line.split("=").length == 1) continue;
				String param = line.split("=")[0].trim();
				String value = line.split("=")[1].trim();
				
				if(param.equalsIgnoreCase("DEBUG") && 
						value.equalsIgnoreCase("true")) debug = true;
				
				else if(param.equalsIgnoreCase("SEND_POLICY") && 
						value.equalsIgnoreCase("true")) CONFIG_POLICY = true;
				
				else if(param.equalsIgnoreCase("BONUS_MAX")) {
					CONFIG_BONUS_MAX = Integer.parseInt(value);
					
					if(CONFIG_BONUS_MAX < 0) CONFIG_BONUS_MAX = 0;
					if(CONFIG_BONUS_MAX > 1000) CONFIG_BONUS_MAX = 1000;
					
				} else if(param.equalsIgnoreCase("SECONDS_PER_TURN")) {
					CONFIG_MS_PER_TURN = Integer.parseInt(value);
					if(CONFIG_MS_PER_TURN < 1) CONFIG_MS_PER_TURN = 1;
					if(CONFIG_MS_PER_TURN > 300) CONFIG_MS_PER_TURN = 300;
					CONFIG_MS_PER_TURN *= 1000;
					
				} else if(param.equalsIgnoreCase("INDUNGEON_CHALLENGE")) {
					CONFIG_MS_FOR_START_FIGHT = Integer.parseInt(value);
					
					if(CONFIG_MS_FOR_START_FIGHT < 1) CONFIG_MS_FOR_START_FIGHT = 1;
					if(CONFIG_MS_FOR_START_FIGHT > 300) CONFIG_MS_FOR_START_FIGHT = 300;
					
					CONFIG_MS_FOR_START_FIGHT *= 1000;
					
				} else if(param.equalsIgnoreCase("LOG") && 
						value.equalsIgnoreCase("true")) Logs.isUsed = true;
				
				else if(param.equalsIgnoreCase("PERCO_TAILLE_VAR") && 
						value.equalsIgnoreCase("false")) CONFIG_TAILLE_VAR = false;
				
				else if(param.equalsIgnoreCase("PRICE_FM_CAC")) 
					PRICE_FM_CAC = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("USE_SUBSCRIBE") && 
						value.equalsIgnoreCase("true")) USE_SUBSCRIBE = true;
				
				else if(param.equalsIgnoreCase("ENABLE_IG_SUBSCRIPTION_BUY") && 
						value.equalsIgnoreCase("true")) ENABLE_IG_SUBSCRIPTION_BUY = true;
				
				else if(param.equalsIgnoreCase("PRICE_BUY_7DAY")) 
					PRICE_BUY_7DAY = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("PRICE_BUY_1MONTH")) 
					PRICE_BUY_1MONTH = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("PRICE_BUY_3MONTH")) 
					PRICE_BUY_3MONTH = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("PRICE_BUY_6MONTH")) 
					PRICE_BUY_6MONTH = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("PRICE_BUY_YEAR")) 
					PRICE_BUY_YEAR = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("USE_CUSTOM_START") && 
						value.equalsIgnoreCase("true")) CONFIG_CUSTOM_STARTMAP = true;
				
				else if(param.equalsIgnoreCase("START_KAMAS")) {
					
					CONFIG_START_KAMAS = Integer.parseInt(value);
					
					if(CONFIG_START_KAMAS < 0) CONFIG_START_KAMAS = 0;
					if(CONFIG_START_KAMAS > 1000000000) CONFIG_START_KAMAS = 1000000000;
				} else if(param.equalsIgnoreCase("START_LEVEL")) {
					CONFIG_START_LEVEL = Integer.parseInt(value);
					
					if(CONFIG_START_LEVEL < 1) CONFIG_START_LEVEL = 1;
					if(CONFIG_START_LEVEL > 200) CONFIG_START_LEVEL = 200;
					
				} else if(param.equalsIgnoreCase("START_MAP")) 
					CONFIG_START_MAP = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("START_CELL")) 
					CONFIG_START_CELL = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("KAMAS")) 
					KAMAS = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("HONOR")) 
					HONOR = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("XP_PVM")) 
					XP_PVM = Integer.parseInt(value);
				
				else if (param.equalsIgnoreCase("MAPS_NO_PRISMES")) 
					for (String curID : value.split(",")) 
						CartesWithoutPrismes.add(Integer.parseInt(curID));
				
				else if (param.equalsIgnoreCase("PRISMES_DELAIS_NEW_POSE")) 
					PRISMES_DELAIS_NEW_POSE = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("XP_PVP")) 
					XP_PVP = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("INSCRIPTION_KOLIZEUM_DELAIS")) 
					KOLIZEUM_DELAIS_LEFT_FIGHT = Integer.parseInt(value);
	            
				else if(param.equalsIgnoreCase("DROP"))
					CONFIG_DROP = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("LOCALIP_LOOPBACK") && 
						value.equalsIgnoreCase("true")) CONFIG_IP_LOOPBACK = true;
				
				else if(param.equalsIgnoreCase("ZAAP") && 
						value.equalsIgnoreCase("true")) CONFIG_ZAAP = true;
				
				else if(param.equalsIgnoreCase("XP_DEFI") && 
						value.equalsIgnoreCase("false")) CONFIG_XP_DEFI = true;
				
				else if(param.equalsIgnoreCase("ANTI_SURCHARGE") && 
						value.equalsIgnoreCase("true")) ANTI_SURCHARGE = true;
				
				else if(param.equalsIgnoreCase("LINK_VOTE")) CONFIG_LINK_VOTE = value;
				
				else if(param.equalsIgnoreCase("USE_IP") && 
						value.equalsIgnoreCase("true")) CONFIG_USE_IP = true;
				
				else if(param.equalsIgnoreCase("MOTD")) 
					CONFIG_MOTD = line.split("=",2)[1];
				
				else if(param.equalsIgnoreCase("RESTRICTED_MAPS")) 
					RESTRICTED_MAPS = line.split("=",2)[1].trim();
				
				else if(param.equalsIgnoreCase("MOTD_COLOR")) 
					CONFIG_MOTD_COLOR = value;
				
				else if(param.equalsIgnoreCase("XP_METIER")) 
					XP_METIER = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("SERVER_ID")) 
					CONFIG_SERVER_ID = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("GAME_PORT")) 
					CONFIG_GAME_PORT = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("REALM_PORT"))
					CONFIG_REALM_PORT = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("FLOODER_TIME")) 
					FLOOD_TIME = Integer.parseInt(value);
				
				else if (param.equalsIgnoreCase("RATE_COINS")) 
					RATE_COINS = Integer.parseInt(value);
				
				else if (param.equalsIgnoreCase("PLAYERS_PER_TEAM")) 
					KOLIMAX_PLAYER = Integer.parseInt(value);
				
				else if (param.equalsIgnoreCase("RATE_FM")) 
					RATE_FM = Integer.parseInt(value);
				
				else if (param.equalsIgnoreCase("TACLE_FORMULA")) 
					TACLE_FORMULA = value;
				
				else if (param.equalsIgnoreCase("TACLE_PERCENTAGE")) 
					TACLE_PERCENTAGE = value;
				
				else if(param.equalsIgnoreCase("HOST_IP")) IP = value;
								
				else if (param.equalsIgnoreCase("KOLIMAPS")) KOLIMAPS = value;
				
				else if (param.equalsIgnoreCase("COINS_ID")) 
					COINS_ID = Integer.parseInt(value);
				
				else if (param.equalsIgnoreCase("TRANCHE_LVL")) 
					KOLI_LEVEL = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("DB_HOST")) DB_HOST = value;
				
				else if(param.equalsIgnoreCase("DB_USER")) DB_USER = value;
				
				else if(param.equalsIgnoreCase("DB_PASS")) 
					DB_PASS = value == null?"":value;
				
				else if(param.equalsIgnoreCase("DB_NAME")) DB_NAME = value;
				
				else if(param.equalsIgnoreCase("MAX_CHARACTERS_PER_ACCOUNT")) 
					CONFIG_MAX_PERSOS = Integer.parseInt(value);
				
				else if (param.equalsIgnoreCase("USE_MOBS")) 
					CONFIG_USE_MOBS = value.equalsIgnoreCase("true");
				
				else if (param.equalsIgnoreCase("ALLOW_MULTI_ACCOUNT")) 
					CONFIG_ALLOW_MULTI = value.equalsIgnoreCase("true");
				
				else if (param.equalsIgnoreCase("LOAD_ACTION_DELAY")) 
					CONFIG_LOAD_DELAY = (Integer.parseInt(value) * 1000);
				
				else if (param.equalsIgnoreCase("PLAYER_LIMIT")) 
					CONFIG_PLAYER_LIMIT = Integer.parseInt(value);
				
				else if (param.equalsIgnoreCase("ARENA_MAP")) 
					for(String curID : value.split(",")) 
						arenaMap.add(Integer.parseInt(curID));
				
				else if (param.equalsIgnoreCase("ARENA_TIMER")) 
					CONFIG_ARENA_TIMER = Integer.parseInt(value);
						
				else if (param.equalsIgnoreCase("CRAQUELEUR_MAP")) 
					for(String curID : value.split(",")) 
						craqueleurMap.add(Integer.parseInt(curID));
				
				else if (param.equalsIgnoreCase("ABRA_MAP")) 
					for(String curID : value.split(",")) 
						abraMap.add(Integer.parseInt(curID));
				
				else if (param.equalsIgnoreCase("AURA_SYSTEM")) 
					AURA_SYSTEM = value.equalsIgnoreCase("true");
				
				else if (param.equalsIgnoreCase("ALLOW_MULE_PVP")) 
					ALLOW_MULE_PVP = value.equalsIgnoreCase("true");
				
				else if (param.equalsIgnoreCase("MAX_IDLE_TIME")) 
					CONFIG_MAX_IDLE_TIME = (Integer.parseInt(value)*60000);
				
				else if (param.equalsIgnoreCase("NOT_IN_HDV")) 
					for(String curID : value.split(",")) 
						NOTINHDV.add(Integer.parseInt(curID));
				
				else if (param.equalsIgnoreCase("LOAD_PUB_DELAY")) 
					CONFIG_LOAD_PUB=(Integer.parseInt(value) * 1000);
				
				else if (param.equalsIgnoreCase("PUB1")) PUB1 = value;
				
				else if (param.equalsIgnoreCase("PUB2")) PUB2 = value;
				
				else if (param.equalsIgnoreCase("PUB3")) PUB3 = value;
				
				else if(param.equalsIgnoreCase("ENABLE_PUBLICITY_SYSTEM") && 
						value.equalsIgnoreCase("true")) CONFIG_PUB = true;
				
				else if(param.equalsIgnoreCase("ALLOW_CLEAR_CONSOLE")) {
					if(!value.equalsIgnoreCase("true")) continue;
					Allow_Clear_Console = true;
					
				} else if(param.equalsIgnoreCase("ALLOW_REFRESH_TITLE")) {
					if(!value.equalsIgnoreCase("true")) continue;
					Allow_Refresh_Title = true;	
				}
				else if(param.equalsIgnoreCase("MAP_SHOP")) 
					CONFIG_MAP_SHOP = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("CELL_SHOP")) 
					CONFIG_CELL_SHOP = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("MAP_SQUATT")) 
					CONFIG_MAP_SQUATT = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("CELL_SQUATT")) 
					CONFIG_CELL_SQUATT = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("MAP_PVP")) 
					CONFIG_MAP_PVP = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("CELL_PVP")) 
					CONFIG_CELL_PVP = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("MAP_PVM")) 
					CONFIG_MAP_PVM = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("CELL_PVM")) 
					CONFIG_CELL_PVM = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("MAP_PVM2")) 
					CONFIG_MAP_PVM2 = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("CELL_PVM2")) 
					CONFIG_CELL_PVM2 = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("MAP_JAIL")) 
					CONFIG_MAP_JAIL = Short.parseShort(value);
					
				else if(param.equalsIgnoreCase("CELL_JAIL")) 
					CONFIG_CELL_JAIL = Integer.parseInt(value);
				
				else if(param.equalsIgnoreCase("MAP_PVM3")) 
					CONFIG_MAP_PVM3 = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("CELL_PVM3")) 
					CONFIG_CELL_PVM3 = Integer.parseInt(value);
			
				else if(param.equalsIgnoreCase("MAP_EVENT")) 
					CONFIG_GOEVENT_MAP = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("CELL_EVENT")) 
					CONFIG_GOEVENT_CELL = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("MAP_ENCLOS")) 
					CONFIG_ENCLOS_MAP = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("CELL_ENCLOS")) 
					CONFIG_ENCLOS_CELL = Short.parseShort(value);
				
				else if(param.equalsIgnoreCase("MESSAGE_BIENVENUE")) 
					CONFIG_MESSAGE_BIENVENUE = line.split("=",2)[1];
				
				else if(param.equalsIgnoreCase("CONFIG_SORT_INDEBUFFABLE")) 
					CONFIG_SORT_INDEBUFFABLE = value;
				
				else if(param.equalsIgnoreCase("CONFIG_MORPH_PROHIBIDEN")) 
					for(String split : value.split("\\|")) 
						CONFIG_MORPH_PROHIBIDEN.add(Integer.parseInt(split.split(":")[0]));
			}
			
			if(DB_NAME == null || DB_HOST == null || DB_PASS == null || DB_USER == null)
				throw new Exception();
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("config.properties file not found or unreadable");
			System.exit(0);
			return false;
		}
		
		if(debug)Constant.DEBUG_MAP_LIMIT = 20000;
		
		try {
			String date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) 
					+ "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1) 
					+ "-" + Calendar.getInstance().get(Calendar.YEAR);
			
			if(Logs.isUsed) {
				new File("Logs").mkdir(); 
				new File("Logs/"+"Game_logs").mkdir(); 
				new File("Logs/"+"Realm_logs").mkdir(); 
				new File("Logs/"+"Shop_logs").mkdir(); 
				new File("Logs/"+"Error_logs").mkdir();
				new File("Logs/"+"IpCheck_logs").mkdir();
				new File("Logs/"+"FM_logs").mkdir();
				new File("Logs/"+"Thread_logs").mkdir();
				new File("Logs/"+"Gms_logs").mkdir(); 
				new File("Logs/"+"Chat_logs").mkdir(); 
				Log_FM = new BufferedWriter(new FileWriter("Logs/"+"FM_logs/"+date+".txt", true));
				Log_Realm = new BufferedWriter(new FileWriter("Logs/"+"Realm_logs/"+date+".txt", true));
				Log_IpCheck = new BufferedWriter(new FileWriter("Logs/"+"IpCheck_logs/"+date+".txt", true));
				Log_RealmSock = new BufferedWriter(new FileWriter("Logs/"+"Realm_logs/"+date+"_packets.txt", true));
				Log_Shop = new BufferedWriter(new FileWriter("Logs/"+"Shop_logs/"+date+".txt", true));
				Log_GameSock = new BufferedWriter(new FileWriter("Logs/"+"Game_logs/"+date+"_packets.txt", true));
				Log_Game = new BufferedWriter(new FileWriter("Logs/"+"Game_logs/"+date+".txt", true));
				
				if("Logs/".isEmpty()) {
					Log_MJ = new BufferedWriter(new FileWriter("Logs/"+"Gms_logs/"+date+"_GM.txt",true));
					Log_Debug = new BufferedWriter(new FileWriter("Logs/"+"Thread_logs/"+date+"_Thread.txt",true));
					Log_Chat = new BufferedWriter(new FileWriter("Logs/"+"Chat_logs/"+date+"_Chat.txt",true));
					String nom = "Logs/"+"Error_logs/"+date+"_error.txt";
					int i = 0;
					while(new File(nom).exists()) {
						nom = "Logs/"+"Error_logs/"+date+"_error_"+i+".txt";
						i++;
					}
					PS = new PrintStream(new File(nom));
				} else {
					Log_MJ = new BufferedWriter(new FileWriter("Logs"+"/Gms_logs/"+date+"_GM.txt",true));
					Log_Debug = new BufferedWriter(new FileWriter("Logs"+"/Thread_logs/"+date+"_Thread.txt",true));
					Log_Chat = new BufferedWriter(new FileWriter("Logs"+"/Chat_logs/"+date+"_Chat.txt",true));
					
					String nom = "Logs"+"/Error_logs/"+date+"_error.txt";
					int i = 0;
					while(new File(nom).exists()) {
						nom = "Logs"+"/Error_logs/"+date+"_error_"+i+".txt";
						i++;
					}
					PS = new PrintStream(new File(nom));
				}
				canLog = true;
				System.setErr(PS);
				PS.flush();
				Log_GameSock.flush();
				Log_Game.flush();
				Log_MJ.flush();
				Log_Realm.flush();
				Log_Shop.flush();
				Log_FM.flush();
				Log_Chat.flush();
				Log_Debug.newLine();
				Log_Debug.flush();
				Log_IpCheck.flush();
				Log_RealmSock.flush();
			}
		} catch(IOException e) {
			/*On créer les dossiers*/
			
			Console.println(e.getMessage(), Color.RED);
			load();
			return false;
		}
		return true;
	}
	
	public void setWhenHasPosePrism(Map<Integer, Long> whenHasPosePrism) {
		WhenHasPosePrism = whenHasPosePrism;
	}

	public static Map<Integer, Long> getWhenHasPosePrism() {
		return WhenHasPosePrism;
	}

	public static void setPosePrism(int posePrism) {
		PosePrism = posePrism;
	}

	public static int getPosePrism() {
		return PosePrism;
	}
}