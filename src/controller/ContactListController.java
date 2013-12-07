package controller;

import java.io.IOException;

import view.ContactListWindow;
import client.Client;

public class ContactListController {
	
	private ContactListWindow clw;
	private Client client;

	public ContactListController(ContactListWindow clw, Client client) {
		this.clw = clw;
		this.client = client;
		this.client.setContactListController(this);
	}

	public void refresh() {
		try {
			clw.refresh(client.getClientLogins(), client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ContactListWindow getClw() {
		return clw;
	}

	public void setClw(ContactListWindow clw) {
		this.clw = clw;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
