package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import view.ChatMain;
import view.LoginWindow;
import client.Client;

public class LoginController {
	
	private LoginWindow loginWindow;
	private Client client;
	
	public LoginController(LoginWindow window) {
		this.loginWindow = window;
		loginWindow.setController(this);
	}
	
	public void processLogin(String login, String pass, String ipServer) {
		if (login.isEmpty() || pass.isEmpty() || ipServer.isEmpty()) {
			fireErrorMessage("Veuillez remplir tous les champs.");
		} else {
			try {
				client = new Client(login, pass, ipServer, 8001);
				client.setLoginController(this);
				
				ContactListController clc = new ContactListController(ChatMain.clw, client);
				ChatMain.clw.setController(clc);
				
				client.start();

				loginWindow.createContactList(client.getClientLogins());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void fireErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.INFORMATION_MESSAGE);
	}

	public Client getClient() {
		return client;
	}

}
