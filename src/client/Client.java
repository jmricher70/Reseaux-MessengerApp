package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.handling.TCPHandlerClient;
import client.handling.UDPClient;

import common.CommonConstants;
import common.MasterClass;
import common.logging.EventType;
import common.logging.Log;

import controller.ContactListController;
import controller.LoginController;

/**
 * Client master class, contains handler threads, IP and port maps.
 * @author etudiant
 *
 */
public class Client implements MasterClass { 
	
	private static final long DEFAULT_TIMEOUT_TIME = 0;
	private String login;
	private String pass;
	
	private String serverIp;
	private int serverPort;
	private Socket clientSocket;
	
	private boolean running = false;
	private boolean connected = false;
	private boolean tryToConnect = true;
	
	private Map<String, InetAddress> clientIps;
	private Map<String, String> clientPorts;
	private List<String> clientLogins;
	
	private ClientTimeoutHandler timeoutHandler;
	private TCPHandlerClient tcpHandlerClient;
	private UDPClient udpClient;
	
	private long timeout;
	private int mainUDPListeningPort;
	private Log log;
	private InetAddress ipOtherClient;
	private int portOtherClient;
	private boolean isConnectClient;
	private TCPHandlerClient tcp;
	private UDPClient udp;	
	private LoginController loginController;
	private ContactListController contactListController;

	/**
	 * Constructs a Client object.
	 * @param login - Client login.
	 * @param pass - Client password.
	 * @param serverIp - IP address of the Server. 
	 * @param serverPort - port used by the Server (Note: by default, should be 8001).
	 * @throws IOException
	 */
	public Client(String login, String pass, String serverIp, int serverPort) throws IOException {
		super();
		this.login = login;
		this.pass = pass;
		
		//clientSocket = new Socket(serverIp, serverPort);
		clientSocket = new Socket();
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		
		clientIps = new HashMap<String, InetAddress>();
		clientPorts = new HashMap<String, String>();
		setClientLogins(new ArrayList<String>());
		timeoutHandler = new ClientTimeoutHandler(this);
		running = false;
		timeout = DEFAULT_TIMEOUT_TIME;
		log = new Log();
		setConnectClient(false);
	}

	public Client(String login, String serverIp, int port)	{
		super();
		this.login=login;
	}
	
	/**
	 * Prints a summary of the Client's settings.
	 */
	public void printRecap() {
		System.out.println("Starting the client:");
		System.out.println("\tIp:\t" + clientSocket.getInetAddress());
		System.out.println("\tPort:\t" + clientSocket.getLocalPort());
	}
	
	/**
	 * Starts the client: launch the TCP thread and the UDP master class.
	 */
	public void start() {
		running = true;
		
		setupHandlers();/* my stuff
		
		tcpHandlerClient.start();
		log.log(EventType.START, "Starting UDP handler");
		udpClient.start();
		*/
		tcp.run();
		if(connectClient() == true) {
			log.log(EventType.START, "Starting UDP handler");
			udp.start();
		}
	}

	/**
	 * Creates TCP and UDP handlers, without starting them.
	 */
	public void setupHandlers() {
		tcpHandlerClient = new TCPHandlerClient(clientSocket, this);
		udpClient = new UDPClient(this);
		//yassine's
		tcp = new TCPHandlerClient(clientSocket, this);
		udp = new UDPClient(this);
	}

	public static void main(String args[]) {
		String c_login=args[0];
		String c_pass=args[1];
		String ip = args[2];
		try {
			Client client = new Client(c_login, c_pass, ip, CommonConstants.DEFAULT_SERVER_PORT_TCP);
			client.printRecap();
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String ip) {
		this.serverIp = ip;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int port) {
		this.serverPort = port;
	}
	
	public boolean isRunning() {
		return running;
	}

	public ClientTimeoutHandler getTimeoutHandler() {
		return timeoutHandler;
	}
	
	public long getTimeoutTime() {
		return timeout;
	}
	
	public Map<String, InetAddress> getClientIps() {
		return clientIps;
	}
	
	public void setClientIps(Map<String, InetAddress> o)
	{
		this.clientIps=o;
	}
	
	public Map<String, String> getClientPorts() {
		return clientPorts;
	}

	public void setClientPorts(Map<String, String> clientPorts) {
		this.clientPorts = clientPorts;
	}
	
	public int getUDPMainListeningPort() {
		return mainUDPListeningPort;
	}

	public void setUDPMainListeningPort(int listeningPort) {
		this.mainUDPListeningPort = listeningPort;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public InetAddress getIpotherclient() {
		return ipOtherClient;
	}

	public void setIpotherclient(InetAddress ipotherclient) {
		this.ipOtherClient = ipotherclient;
	}

	public int getPortotherclient() {
		return portOtherClient;
	}

	public void setPortotherclient(int portotherclient) {
		this.portOtherClient = portotherclient;
	}
	
	private String loginotherclient;
	
	public String getLoginotherclient() {
		return loginotherclient;
	}

	public void setLoginotherclient(String loginotherclient) {
		this.loginotherclient = loginotherclient;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public List<String> getClientLogins() {
		return clientLogins;
	}

	public void setClientLogins(List<String> clientLogins) {
		this.clientLogins = clientLogins;
	}

	public ContactListController getContactListController() {
		return contactListController;
	}
	
	public void setContactListController(ContactListController clc) {
		this.contactListController = clc;
	}

	public TCPHandlerClient getTcpHandlerClient() {
		return tcpHandlerClient;
	}

	public UDPClient getUdpClient() {
		return udpClient;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	@Override
	public void run() {
		log.log(EventType.START, "Starting Client as Thread");
		start();
	}

	/**
	 * Server connection behavior.
	 * @return True if the Client attempts to connect to the Server when disconnected.
	 */
	public boolean tryToConnect() {
		return tryToConnect;
	}
	
	/**
	 * Sets the Server connection behavior. 
	 * @param tryToConnect - Should the Client attempt to connect with the Server?
	 */
	public void tryToConnect(boolean tryToConnect) {
		this.tryToConnect = tryToConnect;
	}

	/**
	 * Connection status.
	 * @return True if the Client is connected to a Server.
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Sets the connection status. Must be called whenever the client
	 * acquires or loses connection to the server.
	 * @param isConnected - New connection status.
	 */
	public void setConnectionStatus(boolean isConnected) {
		this.connected = isConnected;
	}

//yassine's stuff
	public boolean connectClient() {
		return isConnectClient;
	}

	public void setConnectClient(boolean isConnectClient) {
		this.isConnectClient = isConnectClient;
	}
	
	public UDPClient getUdp() {
		return udp;
	}

	public void setUdp(UDPClient udp) {
		this.udp = udp;
	}
//end of yassine's stuff
}
