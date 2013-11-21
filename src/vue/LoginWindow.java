package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginWindow extends JPanel implements ActionListener {
	
	public JLabel logLabel = new JLabel("Log   : "); 
	public JLabel passLabel = new JLabel("Pass : "); 
	public JLabel ipServerLabel = new JLabel("Ip Server : "); 
	public JButton connectButton = new JButton("CONNECT");
	public JTextField logField = new JTextField(15);
	public JTextField passField = new JTextField(15);
	public JTextField ipServerField = new JTextField(15);
	public JFrame cadre = new javax.swing.JFrame("Chat-Expert");
	
	public LoginWindow(){}
	
	public void lancerAffichage() throws IOException
	{
		connectButton.addActionListener(this);
		
		logField.setSize(30, 5);
		passField.setSize(30, 5);
		
		JPanel panneauLog = new JPanel();
		panneauLog.setLayout(new BorderLayout());
		panneauLog.add(logLabel, BorderLayout.WEST);
		panneauLog.add(logField, BorderLayout.CENTER);
		panneauLog.setBorder(new EmptyBorder(0,0,10,0));
		
		
		JPanel panneauPass = new JPanel();
		panneauPass.setLayout(new BorderLayout());
		panneauPass.add(passLabel, BorderLayout.WEST);
		panneauPass.add(passField, BorderLayout.CENTER);
		panneauPass.setBorder(new EmptyBorder(0,0,10,0));
		
		JPanel panneauIpServer = new JPanel();
		panneauIpServer.setLayout(new BorderLayout());
		panneauIpServer.add(ipServerLabel, BorderLayout.WEST);
		panneauIpServer.add(ipServerField, BorderLayout.CENTER);
		panneauIpServer.setBorder(new EmptyBorder(0,0,10,0));
		
		JPanel panneauLogPassIpServer = new JPanel();
		panneauLogPassIpServer.setLayout(new BorderLayout());
		panneauLogPassIpServer.add(panneauLog, BorderLayout.NORTH);
		panneauLogPassIpServer.add(panneauPass, BorderLayout.CENTER);
		panneauLogPassIpServer.add(panneauIpServer, BorderLayout.SOUTH);
		panneauLogPassIpServer.setBorder(new EmptyBorder(5,0,10,0));
		
		JPanel panneauConnect = new JPanel();
		panneauConnect.setLayout(new BorderLayout());
		panneauConnect.add(panneauLogPassIpServer, BorderLayout.NORTH);
		panneauConnect.add(connectButton, BorderLayout.CENTER);
		panneauConnect.setBorder(new EmptyBorder(5,5,5,5));
		
		cadre.setContentPane(panneauConnect);
		cadre.setLocation(600, 250);
		cadre.pack();
		cadre.setResizable(false);
		cadre.setVisible(true);
		cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connectButton)
		{
			cadre.setVisible(false);
			ContactListWindow clw = new ContactListWindow();
			try {
				clw.lancerAffichage();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}