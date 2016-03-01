package monitoring;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import com.freedomotic.plugins.devices.hello.CommandeObj;
import com.freedomotic.plugins.devices.hello.Etat;

public class Interface extends JFrame implements ActionListener,Constante{

	private JPanel contentPane;
	private JTextField textField;
	private JLabel val_temperature_interne;
	private JLabel mode_utilise;
	private JButton sycroniser;
	private JLabel val_temp_externe;
	private JComboBox listeMode;
	private JButton setNewVal;
    private static Map<String,String> listObjetUUID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static{
		listObjetUUID = new HashMap<String, String>();
		listObjetUUID.put(THERMO_EXT, UUID_THERMO_EXT);
		listObjetUUID.put(THERMO_INT, UUID_THERMO_INT);
		listObjetUUID.put(RADIATEUR, UUID_RADIATEUR);
		listObjetUUID.put(PROTE, UUID_PORTE);
	}
	
	/**
	 * Create the frame.
	 */
	public Interface() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{35, 83, 86, 44, 46, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{14, 0, 20, 20, 95, 23, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel liteValeur = new JLabel("List indicateur");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(liteValeur, gbc_lblNewLabel);
		
		JToolBar toolBar = new JToolBar();
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar.gridwidth = 5;
		gbc_toolBar.insets = new Insets(0, 0, 5, 5);
		gbc_toolBar.gridx = 4;
		gbc_toolBar.gridy = 1;
		contentPane.add(toolBar, gbc_toolBar);
		
		JLabel tc = new JLabel("TC");
		GridBagConstraints gbc_tc = new GridBagConstraints();
		gbc_tc.fill = GridBagConstraints.HORIZONTAL;
		gbc_tc.insets = new Insets(0, 0, 5, 5);
		gbc_tc.gridx = 1;
		gbc_tc.gridy = 2;
		contentPane.add(tc, gbc_tc);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel thermo_ext = new JLabel("Température externe");
		GridBagConstraints thermo_ext_gbc_lblNewLabel = new GridBagConstraints();
		thermo_ext_gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		thermo_ext_gbc_lblNewLabel.gridx = 4;
		thermo_ext_gbc_lblNewLabel.gridy = 2;
		contentPane.add(thermo_ext, thermo_ext_gbc_lblNewLabel);
		
		/*valeur externe*/
		val_temp_externe= new JLabel("0");
		GridBagConstraints val_temp_externe_gbc_lblNewLabel = new GridBagConstraints();
		val_temp_externe_gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		val_temp_externe_gbc_lblNewLabel.gridx = 6;
		val_temp_externe_gbc_lblNewLabel.gridy = 2;
		contentPane.add(val_temp_externe, val_temp_externe_gbc_lblNewLabel);
		
		JLabel mode = new JLabel("Mode");
		GridBagConstraints gbc_mode = new GridBagConstraints();
		gbc_mode.fill = GridBagConstraints.HORIZONTAL;
		gbc_mode.insets = new Insets(0, 0, 5, 5);
		gbc_mode.gridx = 1;
		gbc_mode.gridy = 3;
		contentPane.add(mode, gbc_mode);
		
		/*liste mode*/
		listeMode = new JComboBox();
		listeMode.addItem("Eco");
		listeMode.addItem("Confort");
		GridBagConstraints gbc_listeMode = new GridBagConstraints();
		gbc_listeMode.anchor = GridBagConstraints.WEST;
		gbc_listeMode.insets = new Insets(0, 0, 5, 5);
		gbc_listeMode.gridx = 2;
		gbc_listeMode.gridy = 3;
		contentPane.add(listeMode, gbc_listeMode);
		
		JLabel thermo_intern = new JLabel("Température interne");
		GridBagConstraints thermo_intern_gbc_lblNewLabel = new GridBagConstraints();
		thermo_intern_gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		thermo_intern_gbc_lblNewLabel.gridx = 4;
		thermo_intern_gbc_lblNewLabel.gridy = 3;
		contentPane.add(thermo_intern, thermo_intern_gbc_lblNewLabel);
		
		/*valeur thermo interne recupere*/
		val_temperature_interne = new JLabel("0");
		GridBagConstraints val_temperature_interne_gbc_lblNewLabel = new GridBagConstraints();
		val_temperature_interne_gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		val_temperature_interne_gbc_lblNewLabel.gridx = 6;
		val_temperature_interne_gbc_lblNewLabel.gridy = 3;
		contentPane.add(val_temperature_interne, val_temperature_interne_gbc_lblNewLabel);
		
		JLabel mode_radiator = new JLabel("Mode");
		GridBagConstraints mode_radiator_gbc_lblNewLabel = new GridBagConstraints();
		mode_radiator_gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		mode_radiator_gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		mode_radiator_gbc_lblNewLabel.gridx = 4;
		mode_radiator_gbc_lblNewLabel.gridy = 4;
		contentPane.add(mode_radiator, mode_radiator_gbc_lblNewLabel);
		
		/*Mode utilise*/
		mode_utilise = new JLabel("mode");
		GridBagConstraints mode_utilise_gbc_lblNewLabel = new GridBagConstraints();
		mode_utilise_gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		mode_utilise_gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		mode_utilise_gbc_lblNewLabel.gridx = 6;
		mode_utilise_gbc_lblNewLabel.gridy = 4;
		contentPane.add(mode_utilise, mode_utilise_gbc_lblNewLabel);
		
		/*Sycroniser*/
		sycroniser = new JButton("Sycroniser");
		GridBagConstraints gbc_sycroniser = new GridBagConstraints();
		gbc_sycroniser.insets = new Insets(0, 0, 0, 5);
		gbc_sycroniser.gridx = 1;
		gbc_sycroniser.gridy = 5;
		contentPane.add(sycroniser, gbc_sycroniser);
		
		sycroniser.addActionListener(this);
		
		/*update*/
		setNewVal = new JButton("Update");
		GridBagConstraints gbc_setNewVal = new GridBagConstraints();
		gbc_setNewVal.anchor = GridBagConstraints.WEST;
		gbc_setNewVal.insets = new Insets(0, 0, 0, 5);
		gbc_setNewVal.gridx = 2;
		gbc_setNewVal.gridy = 5;
		contentPane.add(setNewVal, gbc_setNewVal);
		setNewVal.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try{
			if(e.getActionCommand().equals("Sycroniser")){
				Etat etat = Services.getEtatPlateforme(Services.getMap(listObjetUUID));
				val_temperature_interne.setText(etat.getValueThermoInt());
				val_temp_externe.setText(etat.getValueThermoExt());
				mode_utilise.setText(etat.getMode());
			}else{
				CommandeObj objC = new CommandeObj();
				objC.setMode(listeMode.getSelectedItem().toString());
				objC.setTc(textField.getText());
				//objC.setTc(textField.@());

				
				ObjectOutputStream oos = null;
				File fichier =  new File("/Users/mac/Desktop/doc/commande.ser") ;
				if(fichier.exists())
				        fichier.delete();
				oos = new ObjectOutputStream(new FileOutputStream(fichier));
				oos.writeObject(objC) ;
				oos.close();
				Services.changeMode(url+UUID_RADIATEUR, "PUT","",textField.getText());
			}
		}catch(Exception exep){
			System.out.println(exep.toString()) ;
		}
		
	}

}
