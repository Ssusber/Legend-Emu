package fr.doflegend.legendemu.kernel;

import fr.doflegend.legendemu.common.SQLManager;
import fr.doflegend.legendemu.common.World;

public class Reboot {
	
	public static final int TIME = 24*3; //hours
	
	public static void start() {
		if(!Config.isSaving) 
			World.saveAll(null);
		
		LegendEmu.isRunning = false;
		
		try { LegendEmu.gameServer.kickAll(); } catch(Exception e) {}
		LegendEmu.gameServer.stop();
		
		try { LegendEmu.loginServer.stop(); } catch(Exception e) {}
		
		LegendEmu.loginServer = null;
		LegendEmu.gameServer = null;
		
		try { World.clearAllVar(); } catch(Exception e) {}	
		try { LegendEmu.listThreads(true); } catch(Exception e) {}
		
		SQLManager.closeCons();
		System.gc();
		
		LegendEmu.main(null);
	}
}
