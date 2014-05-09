package fr.doflegend.legendemu.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import fr.doflegend.legendemu.arena.Arena;
import fr.doflegend.legendemu.arena.Kolizeum;
import fr.doflegend.legendemu.arena.Team;
import fr.doflegend.legendemu.common.CryptManager;
import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.SocketManager;
import fr.doflegend.legendemu.common.World;
import fr.doflegend.legendemu.fight.object.Stalk;
import fr.doflegend.legendemu.game.GameThread;
import fr.doflegend.legendemu.kernel.Config;
import fr.doflegend.legendemu.login.server.Client;
import fr.doflegend.legendemu.object.Objects;
import fr.doflegend.legendemu.object.AuctionHouse.HdvEntry;

public class Accounts {

	private int _GUID;
	private String _name;
	private String _pass;
	private String _pseudo;
	private String _key;
	private String _lastIP = "";
	private String _question;
	private String _reponse;
	private boolean _banned = false;
	private long _banned_time = 0;
	private int _gmLvl = 0;
	private int _subscriber = 0;//Time en secondes
	private boolean _subscriberMessage = true;
	private int _vip = 0;
	private String _curIP = "";
	private String _lastConnectionDate = "";
	private GameThread _gameThread;
	private Client client;
	private Characters _curPerso;
	private long _bankKamas = 0;
	private Map<Integer,Objects> _bank = new TreeMap<Integer,Objects>();
	private ArrayList<Integer> _friendGuids = new ArrayList<Integer>();
	private ArrayList<Integer> _EnemyGuids = new ArrayList<Integer>();
	private long _mute_time = 0;
	private String _mute_raison = "";
	private String _mute_pseudo = "";
	public boolean _isD = false;
	public int _position = -1;//Position du joueur
	private Map<Integer,ArrayList<HdvEntry>> _hdvsItems;// Contient les items des HDV format : <hdvID,<cheapestID>>
	private int _cadeau; //Cadeau à la connexion
	
	private Map<Integer, Characters> _persos = new TreeMap<Integer, Characters>();
	private Map<Integer, Long> WhenHasLeftKolizeum = new HashMap<Integer, Long>();
	private boolean isAFlooder = false;
	private int floodGrade = 0;
	
	byte state;
	
	public boolean isAFlooder() {
		return isAFlooder;
	}

	public void setAFlooder(boolean isAFlooder) {
		this.isAFlooder = isAFlooder;
	}

	public Accounts(int aGUID, String aName, String aPass, String aPseudo, 
			String aQuestion, String aReponse, int aGmLvl, int subscriber, int vip, boolean aBanned, 
			long banned_time, String aLastIp, String aLastConnectionDate, 
			String bank, int bankKamas, String friends, String enemy, 
			int cadeau, long mute_time, String mute_raison, String mute_pseudo) {
		this._GUID 		= aGUID;
		this._name 		= aName;
		this._pass		= aPass;
		this._pseudo 	= aPseudo;
		this._question	= aQuestion;
		this._reponse	= aReponse;
		this._gmLvl		= aGmLvl;
		this._subscriber 			= subscriber;
		this._vip 		= vip;
		this._banned	= aBanned;
		this._banned_time = banned_time;
		this._lastIP	= aLastIp;
		this._lastConnectionDate = aLastConnectionDate;
		this._bankKamas = bankKamas;
		this._hdvsItems = World.getMyItems(_GUID);
		this._mute_time = mute_time;
		this._mute_raison = mute_raison;
		this._mute_pseudo = mute_pseudo;
		//Chargement de la banque
		for(String item : bank.split("\\|"))
		{
			if(item.equals(""))continue;
			String[] infos = item.split(":");
			int guid = Integer.parseInt(infos[0]);

			Objects obj = World.getObjet(guid);
			if( obj == null)continue;
			_bank.put(obj.getGuid(), obj);
		}
		//Chargement de la liste d'amie
		for(String f : friends.split(";"))
		{
			try
			{
				_friendGuids.add(Integer.parseInt(f));
			}catch(Exception E){};
		}
		//Chargement de la liste d'Enemy
		for(String f : enemy.split(";"))
		{
			try
			{
				_EnemyGuids.add(Integer.parseInt(f));
			}catch(Exception E){};
		}
		this._cadeau = cadeau;
	}
	
	public void setBankKamas(long i)
	{
		_bankKamas = i;
		SQLManager.UPDATE_ACCOUNT_DATA(this);
	}
	
	public int getCadeau() {
		return _cadeau;
	}
	
	public void setCadeau() {
		_cadeau = 0;
	}
	
	public void setCadeau(int cadeau) {
		_cadeau = cadeau;
	}

	public String parseBankObjetsToDB()
	{
		StringBuilder str = new StringBuilder();
		if(_bank.isEmpty())return "";
		for(Entry<Integer,Objects> entry : _bank.entrySet())
		{
			Objects obj = entry.getValue();
			str.append(obj.getGuid()).append("|");
		}
		return str.toString();
	}
	
	public Map<Integer, Objects> getBank() {
		return _bank;
	}

	public long getBankKamas()
	{
		return _bankKamas;
	}

	public void setGameThread(GameThread t)
	{
		_gameThread = t;
	}
	
	public void setCurIP(String ip)
	{
		_curIP = ip;
	}
	
	public String getLastConnectionDate() {
		return _lastConnectionDate;
	}
	
	public void setLastIP(String _lastip) {
		_lastIP = _lastip;
	}

	public void setLastConnectionDate(String connectionDate) {
		_lastConnectionDate = connectionDate;
	}

	public GameThread getGameThread()
	{
		return _gameThread;
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public void setClient(Client client)
	{
		this.client = client;
	}
	
	public int get_GUID() {
		return _GUID;
	}
	
	public String get_name() {
		return _name;
	}

	public String get_pass() {
		return _pass;
	}

	public String get_pseudo() {
		return _pseudo;
	}

	
	public void set_pseudo(String pseudo) {
		 _pseudo = pseudo;
	}

	public String get_key() {
		return _key;
	}

	public void setClientKey(String aKey)
	{
		_key = aKey;
	}
	
	public Map<Integer, Characters> get_persos() {
		return _persos;
	}

	public String get_lastIP() {
		return _lastIP;
	}

	public String get_question() {
		return _question;
	}

	public Characters get_curPerso() {
		return _curPerso;
	}

	public String get_reponse() {
		return _reponse;
	}


	public boolean isOnline()
	{
		if(_gameThread != null)return true;
		if(client != null)return true;
		return false;
	}

	public int get_gmLvl() {
		return _gmLvl;
	}

	public String get_curIP() {
		return _curIP;
	}
	
	public boolean isValidPass(String pass,String hash)
	{
		return pass.equals(CryptManager.CryptPassword(hash, _pass));
	}
	
	public int GET_PERSO_NUMBER()
	{
		return _persos.size();
	}
	public static boolean COMPTE_LOGIN(String name, String pass, String key)
	{
		if(World.getCompteByName(name) != null && World.getCompteByName(name).isValidPass(pass,key))
		{
			return true;
		}else
		{
			return false;
		}
	}

	public void addPerso(Characters perso)
	{
		_persos.put(perso.get_GUID(),perso);
	}
	
	public boolean createPerso(String name, int sexe, int classe,int color1, int color2, int color3)
	{
		if (classe > 12 || classe < 1)
			this.ban(-1, false);
		if (sexe < 0 || sexe > 1)
			this.ban(-1, false);
			
		Characters perso = Characters.CREATE_PERSONNAGE(name, sexe, classe, color1, color2, color3, this);
		if(perso==null)
		{
			return false;
		}
		_persos.put(perso.get_GUID(), perso);
		return true;
	}

	public void deletePerso(int guid)
	{
		if(!_persos.containsKey(guid))return;
		World.deletePerso(_persos.get(guid));
		_persos.remove(guid);
	}

	public void setCurPerso(Characters perso)
	{
		_curPerso = perso;
	}

	public void updateInfos(int aGUID,String aName,String aPass, String aPseudo,String aQuestion,String aReponse,int aGmLvl, boolean aBanned)
	{
		this._GUID 		= aGUID;
		this._name 		= aName;
		this._pass		= aPass;
		this._pseudo 	= aPseudo;
		this._question	= aQuestion;
		this._reponse	= aReponse;
		this._gmLvl		= aGmLvl;
		this._banned	= aBanned;
	}

	public void deconnexion(Accounts compte)
	{
		try
		{
		if(_curPerso != null)
		{
			Stalk.notifyToOwner(_curPerso, Stalk.DECONNEXION);
			
			if (_curPerso.getArena() == 0)
				Arena.delTeam(Team.getTeamByID(_curPerso.getTeamID()));
			else if (_curPerso.getKolizeum() == 0)
				Kolizeum.delPlayer(_curPerso);
		}
		_curPerso = null;
		_gameThread = null;
		client = null;
		_curIP = "";
		
		SQLManager.SETOFFLINE(compte.get_GUID());
		resetAllChars(true);
		SQLManager.UPDATE_ACCOUNT_DATA(this);
		for(Characters character : this._persos.values())
			SQLManager.SAVE_PERSONNAGE(character, false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void resetAllChars(boolean save) {
		synchronized(_persos) {
			for(fr.doflegend.legendemu.client.Characters P : _persos.values()) {
				if(P.get_fight() != null) {
					P.set_Online(false);
					if (P.getCurJobAction() != null) P.getCurJobAction().breakFM();
					if(P.get_fight().deconnexion(P, false)) {
						SQLManager.SAVE_PERSONNAGE(P,true);
						continue;
					}
				}
				//Si Echange avec un joueur
				if(P.get_curExchange() != null) P.get_curExchange().cancel();
				//Si en groupe
				if(P.getGroup() != null) P.getGroup().leave(P);
				P.get_curCell().removePlayer(P.get_GUID());
				if(P.get_curCarte() != null && P.isOnline()) 
					SocketManager.GAME_SEND_ERASE_ON_MAP_TO_MAP(P.get_curCarte(), P.get_GUID());
				
				P.set_Online(false);
				//Reset des vars du perso
				/**
				 * TODO : ici ont refais la déco à la masse!
				P.resetVars();
				if(save) SQLManager.SAVE_PERSONNAGE(P,true);
				World.unloadPerso(P.get_GUID());
				
				if (_persos.containsKey(P.get_GUID()))
					_persos.remove(P.get_GUID());**/
			}
		}
	}
	
	public String parseFriendList()
	{
		StringBuilder str = new StringBuilder();
		if(_friendGuids.isEmpty())return "";
		for(int i : _friendGuids)
		{
			Accounts C = World.getCompte(i);
			if(C == null)continue;
			str.append("|").append(C.get_pseudo());
			//on s'arrete la si aucun perso n'est connecté
			if(!C.isOnline())continue;
			Characters P = C.get_curPerso();
			if(P == null)continue;
			str.append(P.parseToFriendList(_GUID));
		}
		return str.toString();
	}
	
	public void SendOnline()
	{
		for (int i : _friendGuids)
		{
			if (this.isFriendWith(i))
			{
				Characters perso = World.getPersonnage(i);
				if (perso != null && perso.is_showFriendConnection() && perso.isOnline())
				SocketManager.GAME_SEND_FRIEND_ONLINE(this._curPerso, perso);
			}
		}
	}

	public void addFriend(int guid)
	{
		if(_GUID == guid)
		{
			SocketManager.GAME_SEND_FA_PACKET(_curPerso,"Ey");
			return;
		}
		if(!_friendGuids.contains(guid))
		{
			_friendGuids.add(guid);
			SocketManager.GAME_SEND_FA_PACKET(_curPerso,"K"+World.getCompte(guid).get_pseudo()+World.getCompte(guid).get_curPerso().parseToFriendList(_GUID));
			SQLManager.UPDATE_ACCOUNT_DATA(this);
		}
		else SocketManager.GAME_SEND_FA_PACKET(_curPerso,"Ea");
	}
	
	public void removeFriend(int guid)
	{
		if(_friendGuids.remove((Object)guid))SQLManager.UPDATE_ACCOUNT_DATA(this);
		SocketManager.GAME_SEND_FD_PACKET(_curPerso,"K");
	}
	
	public boolean isFriendWith(int guid)
	{
		return _friendGuids.contains(guid);
	}
	
	public String parseFriendListToDB()
	{
		String str = "";
		for(int i : _friendGuids)
		{
			if(!str.equalsIgnoreCase(""))str += ";";
			str += i+"";
		}
		return str;
	}
	
	public void addEnemy(String packet, int guid)
	{
		if(_GUID == guid)
		{
			SocketManager.GAME_SEND_FA_PACKET(_curPerso,"Ey");
			return;
		}
		if(!_EnemyGuids.contains(guid))
		{
			_EnemyGuids.add(guid);
			Characters Pr = World.getPersoByName(packet);
			SocketManager.GAME_SEND_ADD_ENEMY(_curPerso, Pr);
			SQLManager.UPDATE_ACCOUNT_DATA(this);
		}
		else SocketManager.GAME_SEND_iAEA_PACKET(_curPerso);
	}
	
	public void removeEnemy(int guid)
	{
		if(_EnemyGuids.remove((Object)guid))SQLManager.UPDATE_ACCOUNT_DATA(this);
		SocketManager.GAME_SEND_iD_COMMANDE(_curPerso,"K");
	}
	
	public boolean isEnemyWith(int guid)
	{
		return _EnemyGuids.contains(guid);
	}
	
	public String parseEnemyListToDB()
	{
		String str = "";
		for(int i : _EnemyGuids)
		{
			if(!str.equalsIgnoreCase(""))str += ";";
			str += i+"";
		}
		return str;
	}
	
	public String parseEnemyList() 
	{
		StringBuilder str = new StringBuilder();
		if(_EnemyGuids.isEmpty())return "";
		for(int i : _EnemyGuids)
		{
			Accounts C = World.getCompte(i);
			if(C == null)continue;
			str.append("|").append(C.get_pseudo());
			//on s'arrete la si aucun perso n'est connecté
			if(!C.isOnline())continue;
			Characters P = C.get_curPerso();
			if(P == null)continue;
			str.append(P.parseToEnemyList(_GUID));
		}
		return str.toString();
	}
	
	public void setGmLvl(int gmLvl)
	{
		_gmLvl = gmLvl;
	}

	public int get_subscriber()
	{
		//Retourne le temps restant
		if(!Config.USE_SUBSCRIBE) return 525600;
		if(_subscriber == 0)
		{
			//Si non abo ou abo dépasser
			return 0;
		}else
		if((System.currentTimeMillis()/1000) > _subscriber)
		{
			//Il faut désabonner le compte
			_subscriber = 0;
			SQLManager.UPDATE_ACCOUNT_SUBSCRIBE(get_GUID(), 0);
			return 0;
		}else
		{
			//Temps restant
			int TimeRemaining = (int) (_subscriber - (System.currentTimeMillis()/1000));
			//Conversion en minute
			int TimeRemMinute = (int) Math.floor(TimeRemaining/60);
			
			return TimeRemMinute;
		}
	}
	
	public void set_subscribe(int subscribe)
	{
		_subscriber = subscribe;
	}
	
	public boolean get_subscriberMessage()
	{
		return _subscriberMessage;
	}
	
	public void set_subscriberMessage(boolean b)
	{
		_subscriberMessage = b;
	}
	
	public int get_vip() {
		return _vip;
	}
	public void set_vip(int b){
		_vip = b;
	}
	
	public boolean recoverItem(int ligneID, int amount)
	{
		if(_curPerso == null)
			return false;
		if(_curPerso.get_isTradingWith() >= 0)
			return false;
		
		int hdvID = Math.abs(_curPerso.get_isTradingWith());//Récupère l'ID de l'HDV
		
		HdvEntry entry = null;
		for(HdvEntry tempEntry : _hdvsItems.get(hdvID))//Boucle dans la liste d'entry de l'HDV pour trouver un entry avec le meme cheapestID que spécifié
		{
			if(tempEntry.getLigneID() == ligneID)//Si la boucle trouve un objet avec le meme cheapestID, arrete la boucle
			{
				entry = tempEntry;
				break;
			}
		}
		if(entry == null)//Si entry == null cela veut dire que la boucle s'est effectué sans trouver d'item avec le meme cheapestID
			return false;
		
		_hdvsItems.get(hdvID).remove(entry);//Retire l'item de la liste des objets a vendre du compte

		Objects obj = entry.getObjet();
		
		boolean OBJ = _curPerso.addObjet(obj,true);//False = Meme item dans l'inventaire donc augmente la qua
		if(!OBJ)
		{
			World.removeItem(obj.getGuid());
		}
		
		World.getHdv(hdvID).delEntry(entry);//Retire l'item de l'HDV
			
		return true;
		//Hdv curHdv = World.getHdv(hdvID);
		
	}
	
	public HdvEntry[] getHdvItems(int hdvID)
	{
		if(_hdvsItems.get(hdvID) == null)
			return new HdvEntry[1];
		
		HdvEntry[] toReturn = new HdvEntry[20];
		for (int i = 0; i < _hdvsItems.get(hdvID).size(); i++)
		{
			toReturn[i] = _hdvsItems.get(hdvID).get(i);
		}
		return toReturn;
	}
	
	public int countHdvItems(int hdvID)
	{
		if(_hdvsItems.get(hdvID) == null)
			return 0;
		
		return _hdvsItems.get(hdvID).size();
	}
	
	//Bannissement temporisé
	public void ban(long time, boolean isEndTime)
	{
		if(time == 0) return;
		if(time != -1)
		{
			if(!isEndTime) time = (long) (System.currentTimeMillis()/1000) + time;
		}
		_banned = true;
		_banned_time = time;
		SQLManager.UPDATE_ACCOUNT_DATA(this);
		state = 3;
	}
	public void unBan()
	{
		_banned = false;
		_banned_time = 0;
		SQLManager.UPDATE_ACCOUNT_DATA(this);
		state = (byte) (this.state==3?(isOnline()?2:1):0);
	}
	public long getBannedTime()
	{
		if(!isBanned()) return 0;
		return _banned_time;
	}

	public boolean isBanned() {
		if(!_banned) return false;
		if(_banned_time == -1) return true;
		if(_banned_time < (long) System.currentTimeMillis()/1000)
		{
			_banned = false;
			_banned_time = 0;
		}
		return _banned;
	}
	
	public void setBanned(boolean banned) {
		_banned = banned;
		this.state = banned?3:this.state;
	}
	public void mute(int nb_minutes, String raison, String pseudo)
	{
		if(nb_minutes <= 0) return;
		_mute_time = (long) System.currentTimeMillis()/1000 + nb_minutes*60;
		_mute_raison = raison;
		_mute_pseudo = pseudo;
		SQLManager.UPDATE_ACCOUNT_DATA(this);
		if(_curPerso != null)
		{
			StringBuilder im_mess = new StringBuilder("117;").append(pseudo).append("~").append(nb_minutes).append("~").append(raison);
			SocketManager.GAME_SEND_Im_PACKET(_curPerso, im_mess.toString());
			if(_curPerso.get_fight() == null)
			{
				_curPerso.teleport(Config.CONFIG_MAP_JAIL, Config.CONFIG_CELL_JAIL);
			}
		}
	}
	public long getMuteTime()
	{
		if(!isMuted()) return 0;
		return _mute_time;
	}
	public String getMuteRaison()
	{
		if(!isMuted()) return "";
		return _mute_raison;
	}
	public String getMutePseudo()
	{
		if(!isMuted()) return "";
		return _mute_pseudo;
	}
	public void unMute()
	{
		if(_mute_time == 0)return;
		_mute_time = 0;
		_mute_raison = "";
		_mute_pseudo = "";
		SQLManager.UPDATE_ACCOUNT_DATA(this);
	}
	public void sendMutedIM()
	{
		if(!isMuted()) return;
		if(_curPerso != null)
		{
			int temps_restant = (int) (_mute_time - System.currentTimeMillis()/1000);
			StringBuilder im_mess = new StringBuilder("1247;").append(_mute_pseudo).append("~").append(temps_restant).append(" secondes~").append(_mute_raison);
			SocketManager.GAME_SEND_Im_PACKET(_curPerso, im_mess.toString());
		}
	}
	public boolean isMuted()
	{
		if(_mute_time == 0) return false;
		if(_mute_time >= (long) System.currentTimeMillis()/1000)return true;
		//On le démute
		_mute_time = 0;
		_mute_raison = "";
		_mute_pseudo = "";
		SQLManager.UPDATE_ACCOUNT_DATA(this);
		return false;
	}

	public void setWhenHasLeftKolizeum(Map<Integer, Long> whenHasLeftKolizeum) {
		WhenHasLeftKolizeum = whenHasLeftKolizeum;
	}

	public Map<Integer, Long> getWhenHasLeftKolizeum() {
		return WhenHasLeftKolizeum;
	}

	public int getFloodGrade() {
		return floodGrade;
	}

	public void setFloodGrade(int floodGrade) {
		this.floodGrade = floodGrade;
	}
	
	public byte getState() {
		return this.state;
	}
	
	public void setState(int state) {
		this.state = (byte) state;
		SQLManager.UPDATE_ACCOUNT_DATA(this);
	}

	public int get_subscriberInt() {
		return _subscriber;
	}
}
