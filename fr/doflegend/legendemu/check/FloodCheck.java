package fr.doflegend.legendemu.check;

import java.util.Map.Entry;

import fr.doflegend.legendemu.client.Accounts;
import fr.doflegend.legendemu.client.Characters;
import fr.doflegend.legendemu.common.SocketManager;
import fr.doflegend.legendemu.kernel.Config;

public class FloodCheck {

	public static void updateFloodInfos(Characters _perso, String msg)
	{
		if (_perso.getLastMessSent().size() > 50)
			_perso.getLastMessSent().clear();
		
		_perso.setLastMess(System.currentTimeMillis());
		_perso.getLastMessSent().put(msg, System.currentTimeMillis());
		_perso.setContent(msg);
	}
	
	public static boolean isBypass(Characters perso, String mess)
	{
		String last = perso.getContent();
		try {
			if ((mess.length() > 1 && mess.substring(0,mess.length()-2).equals(last.substring(0,mess.length()-2))) ||  (mess.length() > 2 && mess.substring(0,mess.length()-3).equals(last.substring(0,mess.length()-3)))
																												   ||  (mess.length() > 3 &&  mess.substring(0,mess.length()-4).equals(last.substring(0,mess.length()-4))))
			{
				return true;
			}
		}catch(Exception e){}
		return false;
	}
	
	public static boolean isFlooding(Characters perso, String mess)
	{
		if (perso.get_compte().get_gmLvl() > 0)
			return false;
		
		long actualTime = System.currentTimeMillis();
		Accounts acc = perso.get_compte();
		
		if (acc.getFloodGrade() >= 40)
		{
			SocketManager.GAME_SEND_MESSAGE(perso, "<b>AntiFlood:</b> Attention ! Vous êtes à "+acc.getFloodGrade()+"/50 floods possibles ! Restez sans parler quelques temps pour baisser votre quotas.", Config.CONFIG_MOTD_COLOR);
		}
		if (actualTime - perso.getLastMess() > 60000*5)
		{
			if (acc.isAFlooder() != false)
				acc.setAFlooder(false);
			acc.setFloodGrade(0);
		}
		if (actualTime - perso.getLastMess() > 10000 && acc.isAFlooder() == false)
		{
			if (acc.getFloodGrade() > 9 && acc.getFloodGrade() < 50)
			{
				acc.setFloodGrade(acc.getFloodGrade() - 10);
			}
		}
		if (acc.getFloodGrade() >= 50 && acc.isAFlooder() != true)
		{
			acc.setAFlooder(true);
			SocketManager.GAME_SEND_MESSAGE_TO_ALL("<b>AntiFlood:</b> Le joueur <b>"+perso.get_name()+"</b> est désormais soumis à l'antiflood du serveur ! Applaudissements !", Config.CONFIG_MOTD_COLOR);
			SocketManager.GAME_SEND_MESSAGE(perso, "<b>AntiFlood:</b> Restez muet pendant 5 minutes pour désactiver l'antiflood", Config.CONFIG_MOTD_COLOR);
	    }
		if (actualTime - perso.getLastMess() < 1000)
		{
			if (perso.get_compte().isAFlooder())
			{
				SocketManager.GAME_SEND_MESSAGE(perso, "<b>AntiFlood:</b> Veuillez laisser 1 seconde d'intervale entre chaque message !", Config.CONFIG_MOTD_COLOR);
				perso.setLastMess(System.currentTimeMillis());
				return true;
			}
			else
			{
				acc.setFloodGrade(acc.getFloodGrade() + 1);
				return false;
			}
		}
		else if (perso.getContent().equals(mess) || isBypass(perso, mess))
		{
			if (perso.get_compte().isAFlooder())
			{
				SocketManager.GAME_SEND_MESSAGE(perso, "<b>AntiFlood:</b> Le message est semblable au précédent !", Config.CONFIG_MOTD_COLOR);
				updateFloodInfos(perso, mess);
				return true;
			}
			else
			{
				acc.setFloodGrade(acc.getFloodGrade() + 1);
				return false;
			}
		}
		
		int nbrPer15s = 0;
		
		for (Entry<String, Long> lastMess: perso.getLastMessSent().entrySet())
		{
			if (nbrPer15s >= 12)
			{
				if (perso.getAverto() != 2)
				{
					SocketManager.GAME_SEND_MESSAGE(perso, "<b>AntiFlood:</b> Vous avez envoyé plus de 12 messages en moins de 15 secondes ! Encore 1 tentative avant votre perte de parole temporaire...", Config.CONFIG_MOTD_COLOR);
					perso.setAverto(2);
					updateFloodInfos(perso, mess);
					return true;
				}
				else
				{
					perso.get_compte().mute(10, "Restez calme prochainement...", "l'Antiflood");
					SocketManager.GAME_SEND_MESSAGE_TO_ALL("<b>AntiFlood:</b> Le joueur "+perso.get_name()+" a perdu la parole pour les prochaines 10 minutes !", Config.CONFIG_MOTD_COLOR);
					return true;
				}
			}
			if (actualTime - lastMess.getValue() < 15000){
				nbrPer15s++;
			}
			if (lastMess.getKey().equals(mess))
			{
				if (actualTime - lastMess.getValue() < 10000)
				{
					if (perso.getLastFloodTime() < 15)
					{
						if (perso.get_compte().isAFlooder())
						{
							SocketManager.GAME_SEND_MESSAGE(perso, "<b>AntiFlood:</b> Votre message a été détecté comme flood ! Il vous reste <b>"+(14-perso.getLastFloodTime())+" tentatives</b> possibles avant votre bannissement temporaire...", Config.CONFIG_MOTD_COLOR);
							perso.setLastFloodTime(perso.getLastFloodTime()+1);
							updateFloodInfos(perso, mess);
							return true;
						}
						else
						{
							acc.setFloodGrade(acc.getFloodGrade() + 1);
							return false;
						}
					}
					else
					{
						perso.get_compte().ban(3600, false);
						if (perso.get_compte().getGameThread() != null) 
							perso.get_compte().getGameThread().kick();
						SocketManager.GAME_SEND_MESSAGE_TO_ALL("Le joueur "+perso.get_name()+" a été banni <b>1 heure</b> par <b>l'AntiFlood</b> automatique du serveur !", Config.CONFIG_MOTD_COLOR);
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
