package fr.doflegend.legendemu.login.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Calendar;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import fr.doflegend.legendemu.kernel.Config;
import fr.doflegend.legendemu.kernel.Console;
import fr.doflegend.legendemu.kernel.Console.Color;

public class LoginServer {
	public static String ip, universalPassword = "5sdfh4d6f4g534fd655644wfd64qsd53g56s1d5s1sdgf5444", version = "1.29.1";
	private static NioSocketAcceptor acceptor;
	
	public LoginServer() {//establish variables
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName("UTF8"), LineDelimiter.NUL, new LineDelimiter("\n\0"))));
		acceptor.setHandler(new ClientHandler());
	}
	
	public void start() {//start server
		if(acceptor.isActive()) return;//if acceptor's already launched
		
		try { acceptor.bind(new InetSocketAddress(Config.CONFIG_REALM_PORT));//enable connection
		} catch (IOException e) {
			addToLog(e.toString());
			addToLog("Fail to bind acceptor : " + e);
		} finally {
			Console.println("Login server started on port " + Config.CONFIG_REALM_PORT, Color.GREEN); 
		}
	}
	
	 public void stop() throws Exception {//stop server
		 if(!acceptor.isActive()) return;//if acceptor's not launched
		 
		 acceptor.unbind();//disable connection
		 
		 for (IoSession session : acceptor.getManagedSessions().values())//kick all clients
			 if (session.isConnected() || !session.isClosing()) 
				 session.close(true);
	     
	     acceptor.dispose();//closing

	     addToLog("login server stoped");
	}
	 
	 public synchronized static void addToLog(String str) {
		 if(Config.debug)
		 Console.println(str, Color.YELLOW);
		 if(Config.canLog) {
			 try {
				 String date = Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE+":"+Calendar.SECOND;
				 Config.Log_Realm.write(date+": "+str);
				 Config.Log_Realm.newLine();
				 Config.Log_Realm.flush();
			 } catch (IOException e) {}//ne devrait pas avoir lieu
		 }
	 }
	 
	 public synchronized static void addToSockLog(String str)
	 {
		 if(Config.debug)
		 Console.println(str, Color.YELLOW);
		 if(Config.canLog)
		 {
			 try {
				 String date = Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE+":"+Calendar.SECOND;
				 Config.Log_RealmSock.write(date+": "+str);
				 Config.Log_RealmSock.newLine();
				 Config.Log_RealmSock.flush();
			 } catch (IOException e) {}//ne devrait pas avoir lieu
		 }
	 }
}
