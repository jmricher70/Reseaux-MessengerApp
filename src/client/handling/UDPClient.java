package client.handling;

import java.net.SocketException;

import common.logging.EventType;
import common.logging.Log;

import client.Client;

/**
 * Client side UDP handling master class. Launches threads dedicated to client
 * to server operations and client to client operations.
 * @author etudiant
 * @see Client
 * @see UDPHandlerClientListening
 * @see UDPHandlerClientSending
 * @see UDPHandlerClientDiscuss
 */
public class UDPClient extends Thread {
	
	private Client client;
	private UDPHandlerClientListening listeningHandler;
	private UDPHandlerClientSending sendingHandler;
	private UDPHandlerClientDiscuss discussHandler;
	private Log log;
	
	/**
	 * Create an UDPClient object for the specified Client. Note that the thread needs
	 * to be started by the Client.
	 * @param client
	 */
	public UDPClient(Client client) {
		this.client = client;
		log = client.getLog();
		try {
			listeningHandler = new UDPHandlerClientListening(this.client);
			sendingHandler = new UDPHandlerClientSending(this.client);
			discussHandler = new UDPHandlerClientDiscuss(this.client);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		log.log(EventType.START, "Starting UDP listening handler");
		listeningHandler.start();
		
		log.log(EventType.START, "Starting UDP sending handler");
		sendingHandler.start();
	}
	
	/**
	 * Discussion handler getter.
	 * @return UDPHandlerClientDiscuss handler.
	 */
	public UDPHandlerClientDiscuss getSending_client() {
		return discussHandler;
	}

	/**
	 * Discuss handler setter.
	 * @param sending_client - new UDPHandlerClientDiscuss.
	 */
	public void setSending_client(UDPHandlerClientDiscuss sending_client) {
		this.discussHandler = sending_client;
	}

	public Client getClient() {
		return client;
	}

	public UDPHandlerClientListening getListen() {
		return listeningHandler;
	}

	public void setListen(UDPHandlerClientListening listen) {
		this.listeningHandler = listen;
	}

	public UDPHandlerClientSending getSend() {
		return sendingHandler;
	}

	public void setSend(UDPHandlerClientSending send) {
		this.sendingHandler = send;
	}

}
