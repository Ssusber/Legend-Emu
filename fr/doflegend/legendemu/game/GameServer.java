package fr.doflegend.legendemu.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fr.doflegend.legendemu.check.IpCheck;
import fr.doflegend.legendemu.client.Accounts;
import fr.doflegend.legendemu.common.CryptManager;
import fr.doflegend.legendemu.common.Formulas;
import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.SocketManager;
import fr.doflegend.legendemu.common.World;
import fr.doflegend.legendemu.kernel.*;
import fr.doflegend.legendemu.kernel.Console.Color;


public class GameServer implements Runnable{

	private ServerSocket _SS;
	private Thread _t;
	private ArrayList<GameThread> _clients = new ArrayList<GameThread>();
	private ArrayList<Accounts> _waitings = new ArrayList<Accounts>();
	private Timer _saveTimer;
	//private Timer _loadActionTimer;
	private Timer _reloadMobTimer;
	private long _startTime;
	private int _maxPlayer = 0;
	private Timer _loadPubTimer;
	private Timer _loadMountTrash;
	private Timer _reboot;
	private int _rebootTime = 0;
	public static boolean isFirstLoad = false;
	//private boolean firstMess = false;
	public static Map<String, Long> lastIpTiming = new HashMap<String, Long>();
	public static String ip;
	
	//save auto
	static int saveAuto = 30 * 60 * 1000;
	
	public GameServer(String Ip)
	{
		ip = Ip;
		try {
			_saveTimer = new Timer("SaveTimer");
			_saveTimer.schedule(new TimerTask() {
				public void run() {
					World.saveAll(null);
				}
			}, saveAuto, saveAuto);
			
			// TODO : Systéme de pub : Taparisse
			if(Config.CONFIG_PUB == true) {
				_loadPubTimer = new Timer("TimerPub");
				_loadPubTimer.schedule(new TimerTask()
				{
					public void run()
					{
						int rand = Formulas.getRandomValue(1, 3);
						switch(rand)
						{
						case 1:
							SocketManager.GAME_SEND_MESSAGE_TO_ALL(Config.PUB1, Config.CONFIG_COLOR_BLEU);
							break;
						case 2:
							SocketManager.GAME_SEND_MESSAGE_TO_ALL(Config.PUB2, Config.CONFIG_COLOR_BLEU);
							break;
						case 3:
							SocketManager.GAME_SEND_MESSAGE_TO_ALL("<a href='"+Config.PUB3, Config.CONFIG_COLOR_BLEU);
							break;
						}     
					}
				}, Config.CONFIG_LOAD_PUB,Config.CONFIG_LOAD_PUB);
			}
			
			//Reben
			_reboot = new Timer("TimerReboot");
			_reboot.schedule(new TimerTask()
			{
				public void run()
				{
					_rebootTime++;
					if(_rebootTime < Reboot.TIME)
					{
						SocketManager.GAME_SEND_MESSAGE_TO_ALL("Le serveur redémarre dans "+(Reboot.TIME - _rebootTime)+" heure"+(_rebootTime > 1 ?"s":""), Config.CONFIG_COLOR_BLEU);
						return;
					}
					SocketManager.GAME_SEND_MESSAGE_TO_ALL("Démarrage du reboot !", Config.CONFIG_COLOR_BLEU);
					SocketManager.GAME_SEND_MESSAGE_TO_ALL("Une sauvegarde du serveur est en cours... Vous pouvez continuer de jouer, mais l'accès au serveur est temporairement bloqué. La connexion sera de nouveau disponible d'ici quelques instants. Merci de votre patience.", Config.CONFIG_MOTD_COLOR);
					World.saveAll(null);
					SocketManager.GAME_SEND_MESSAGE_TO_ALL("La sauvegarde du serveur est terminée. L'accès au serveur est de nouveau disponible. Merci de votre compréhension.", Config.CONFIG_MOTD_COLOR);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
			}, 3600000,3600000);
			//_loadActionTimer = new Timer("TimerLiveActions");
			//_loadActionTimer.schedule(new TimerTask()
			//{
			//	public void run()
			//	{
			//		for(Characters perso : World.getOnlinePersos()){ 
			//			SQLManager.LOAD_PERSO_PACKS(perso);
			//		}
			//		if (!firstMess) {
			//			Console.println(">Shop pack systeme Online !", Color.RED);
			//			firstMess = true;
			//		}
			//	}
			//}, Config.CONFIG_LOAD_DELAY,Config.CONFIG_LOAD_DELAY);
			
			_reloadMobTimer = new Timer("TimerReloadMobs");
			_reloadMobTimer.schedule(new TimerTask()
			{
				public void run()
				{
					World.RefreshAllMob();
					GameServer.addToLog(">La recharge des mobs est finie\n");
				}
			}, Config.CONFIG_RELOAD_MOB_DELAY,Config.CONFIG_RELOAD_MOB_DELAY);
			
			_loadMountTrash = new Timer("TimerMountTrash");
			_loadMountTrash.schedule(new TimerTask()
			{
				public void run()
				{
					SQLManager.RESET_MOUNTPARKS();
					SQLManager.LOAD_MOUNTPARKS();
					System.out.println(">Supression montures enclos 8747\n");
				}
			}, 10*60*1000,10*60*1000);
			
        	java.util.Timer timer = new java.util.Timer();
            timer.schedule (new TimerTask() {
                public void run()
                {
					World.MoveMobsOnMaps();
                }
            }, 0, 60000);
			
			_SS = new ServerSocket(Config.CONFIG_GAME_PORT);
			if(Config.CONFIG_USE_IP)
				Config.GAMESERVER_IP = CryptManager.CryptIP(Ip)+CryptManager.CryptPort(Config.CONFIG_GAME_PORT);
			_startTime = System.currentTimeMillis();
			_t = new Thread(this,"GameServer");
			_t.start();
		} catch (IOException e) {
			addToLog("IOException: "+e.getMessage());
			Logs.addToDebug("IOException Game-Config: "+e.getMessage());
			System.exit(0);
		}
		
		Console.println("Game server started on port " + Config.CONFIG_GAME_PORT + 
				" with ip " + Ip, Color.GREEN);
	}

	public void restartGameServer()
	{
		if(!_t.isAlive())
		{
			Logs.addToDebug("GameServer planté, tentative de le redémarer.");
			_t.start();
		}
	}
	
	public static class SaveThread implements Runnable
	{
		public void run()
		{
			if(!Config.isSaving) {
				World.saveAll(null);
			}
		}
	}
	
	public void stop()
	{
		this.stop();
	}
	
	public ArrayList<GameThread> getClients() {
		return _clients;
	}

	public long getStartTime()
	{
		return _startTime;
	}
	
	public int getMaxPlayer()
	{
		return _maxPlayer;
	}
	
	public int getPlayerNumber()
	{
		return _clients.size();
	}
	
	public void run()
	{	
		while(LegendEmu.isRunning)//bloque sur _SS.accept()
		{
			try
			{
				Socket _s = _SS.accept();
				if(!IpCheck.canGameConnect(_s.getInetAddress().getHostAddress()))
				{
					_s.close();
				}
				else
				{
					_clients.add(new GameThread(_s));
					if(_clients.size() > _maxPlayer)_maxPlayer = _clients.size();
				}
			}catch(IOException e)
			{
				addToLog("IOException: "+e.getMessage());
				Logs.addToDebug("IOException Game-run: "+e.getMessage());
				e.printStackTrace();
				if(_SS.isClosed())
				{
					Logs.addToDebug("Le GameServer est HS, redémarage obligé.");
					System.exit(0);
				}
			}
		}
	}
	
	public void kickAll() throws Exception
	{
		try {
			_SS.close();
		} catch (IOException e) {}
		//Copie
		ArrayList<GameThread> c = new ArrayList<GameThread>();
		c.addAll(_clients);
		for(GameThread GT : c)
		{
			try
			{
				GT.closeSocket();
			}catch(Exception e){};	
		}
	}

	public synchronized static void addToLog(String str)
	{
		if(Config.debug)
		Console.println(str, Color.YELLOW);
		if(Config.canLog)
		{
			try {
				String date = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(+Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND);
				Config.Log_Game.write(date+": "+str);
				Config.Log_Game.newLine();
				Config.Log_Game.flush();
			} catch (IOException e) {e.printStackTrace();}//ne devrait pas avoir lieu
		}
	}
	
	public synchronized static void addToSockLog(String str)
	{
		if(Config.debug)
		Console.println(str, Color.YELLOW);
		if(Config.canLog)
		{
			try {
				String date = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(+Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND);
				Config.Log_GameSock.write(date+": "+str);
				Config.Log_GameSock.newLine();
				Config.Log_GameSock.flush();
			} catch (IOException e) {}//ne devrait pas avoir lieu
		}
	}
	
	public void delClient(GameThread gameThread)
	{
		_clients.remove(gameThread);
		 if(_clients.size() > _maxPlayer)_maxPlayer = _clients.size();
	}

	public synchronized Accounts getWaitingCompte(int guid)
	{
		for (int i = 0; i < _waitings.size(); i++)
		{
			if(_waitings.get(i).get_GUID() == guid)
				return _waitings.get(i);
		}
		return null;
	}
	
	public synchronized void delWaitingCompte(Accounts _compte)
	{
		_waitings.remove(_compte);
	}
	
	public synchronized void addWaitingCompte(Accounts _compte)
	{
		_waitings.add(_compte);
	}
	
	public static String getServerTime()
	{
		Date actDate = new Date();
		return "BT"+(actDate.getTime()+3600000);
	}
	
	public static String getServerDate() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd");
		
		String day = Integer.parseInt(dateFormat.format(date)) + "";
		
		while(day.length() <2) day = "0"+day;
		
		dateFormat = new SimpleDateFormat("MM");
		String mounth = (Integer.parseInt(dateFormat.format(date))-1) + "";
		
		while(mounth.length() <2) mounth = "0"+mounth;
		
		dateFormat = new SimpleDateFormat("yyyy");
		String year = (Integer.parseInt(dateFormat.format(date))-1370) + "";
		return "BD" + year + "|" + mounth + "|" + day;
	}

	public Thread getThread()
	{
		return _t;
	}
}
