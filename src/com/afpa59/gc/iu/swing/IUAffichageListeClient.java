package com.afpa59.gc.iu.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.afpa59.gc.donnees.Client;
import com.afpa59.gc.donnees.Entite;
import com.afpa59.gc.services.fichier.ObjetInexistantException;
import com.afpa59.gc.services.fichier.ServiceClient;

public class IUAffichageListeClient extends JFrame{

	private List<Entite> entites;
	private Mode mode;
	
	private JTable table;
	private JButton bouton;
	
	public IUAffichageListeClient(String titre, int w, int h, Mode mode, List<Entite> clients){
		super(titre);
		
		this.entites = clients;
		this.mode = mode;
		
		this.setSize(w, h);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(400, 200));

		this.setLayout(new BorderLayout());
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);
		getContentPane().add(getButtonsPanel(), BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	
	public JScrollPane getMainPanel(){
		String[] entetes = {"id", "Nom", "Prénom", "Adresse"};
		Object[][] datas = new Object[entites.size()][entetes.length];
		for(int i = 0; i<entites.size(); i++){
			Client client = (Client)entites.get(i);
			datas[i][0]= client.getId();
			datas[i][1] = client.getNom();
			datas[i][2] = client.getPrenom();
			datas[i][3] = client.getAdresse();
		}
		
		table = new JTable(datas, entetes);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new EmptyBorder(10,10,10,10));
		return scrollPane;
	}
	
	public JPanel getButtonsPanel(){
		JPanel panButton = new JPanel();
		
		switch(mode){
			case VISUALISATION:
				bouton = new JButton("Visualiser");
				
				break;
			case MODIFICATION:
				bouton = new JButton("Modifier");
				break;
			case SUPPRESSION:
				bouton = new JButton("Supprimer");
				break;
			default: 
				bouton = null;
				break;
		}
		
		bouton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rowSelected();
			}
		});
		
		if(bouton!=null){
			panButton.add(bouton);
		}
		return panButton;
	}
	
	public void rowSelected(){
		int index = table.getSelectedRow();
		int id =  (int) table.getModel().getValueAt(index, 0);
		try {
			Client client = (Client) ServiceClient.getInstance().rechercherParId(id);
			new IUAffichageClient("Affichage client", 500, 500, this.mode, client);
		} catch (ObjetInexistantException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
