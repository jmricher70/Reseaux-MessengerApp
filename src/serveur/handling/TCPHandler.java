package serveur.handling;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.Socket;

import serveur.ServerMessageManager;
import serveur.Serveur;

import commun.Message;

public class TCPHandler extends Thread implements Handler {
	
	private Socket socket;
	private Serveur serveur;
	private ServerMessageManager messageManager;
	
	public TCPHandler(Socket clientSocket, Serveur serveur) {
		socket = clientSocket;
		this.serveur = serveur;
		messageManager = new ServerMessageManager(serveur, this);
	}
	
	public void handleDialog() throws IOException, ClassNotFoundException, HandlingException {
		Message message = getClientMessage(socket);
		messageManager.handleMessage(message, socket);
		socket.close();
	}

	
	public void sendMessage(Message message, Socket socket) throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
		os.writeObject(message);
		os.flush();
	}

	public Message getClientMessage(Socket socket) throws IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
		Message message = (Message) is.readObject();
		return message;
	}

	@Override
	public void run() {
		try {
			handleDialog();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (HandlingException e) {
			e.printStackTrace();
		}
	}

	public Serveur getServeur() {
		return serveur;
	}

	@Override
	public void sendMessage(Message message, DatagramSocket socket) throws HandlingException {
		throw new HandlingException("Can't handle a DatagramSocket.");
	}

}