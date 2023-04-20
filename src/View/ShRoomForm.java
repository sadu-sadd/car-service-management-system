package View;

import Controller.*;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.SystemColor;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ShRoomForm extends JFrame {

	private static String newColor;
	private enum CurrentState
	{
		None,
		AddCar,
		UpdateCar,
		MakeAction,
		PrintByService,
		ViewDetails,
	};
	
	private JPanel contentPane;
	private Management controller = new Management();
	private CurrentState currentState = CurrentState.None;
	
	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShRoomForm frame = new ShRoomForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static <T> T as(Object o, Class<T> tClass) 
	{
	    //return tClass.isInstance(o) ? (T) o : null;
		if (tClass.isInstance(o)) {
			return tClass.cast(o);
		} else {
			return null;
		}
	}
	
	private boolean checkIfFilled(JPanel panel) 
	{
		Component[] c = panel.getComponents();
		for(Component com : c) 
		{
			if(com instanceof JTextField) 
			{
				if(((JTextField) com).getText().isEmpty() || ((JTextField) com).getText().equals("Type Here"))
				{
					return false;
				}
			}
			else if(com instanceof JComboBox<?>)
			{
				if(((JComboBox<?>) com).getSelectedItem().equals("Select"))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isCarExists(String i_PlateNumber) 
	{
		try
		{
			FileReader reader = new FileReader("Cars.json");
			BufferedReader buffer = new BufferedReader(reader);
			JSONParser parser = new JSONParser();
			String currentJSONString  = "";

			while((currentJSONString = buffer.readLine()) != null ) 
			{
				JSONObject car = (JSONObject)parser.parse(currentJSONString);
				if(car.get("Plate Number").equals(i_PlateNumber))
			    {	
					return true;
			    }
			}
			
		}
		catch (FileNotFoundException e1) 
		{
            e1.printStackTrace();
        }
		catch (IOException e1) 
		{
            e1.printStackTrace();
        }
		catch (org.json.simple.parser.ParseException e1)
		{
			e1.printStackTrace();
		}
		return false;
	}

	private String getNewColor(String i_PlateNumber) 
	{
		try
		{
			FileReader reader = new FileReader("Cars.json");
			BufferedReader buffer = new BufferedReader(reader);
			JSONParser parser = new JSONParser();
			String currentJSONString  = "";

			while((currentJSONString = buffer.readLine()) != null ) 
			{
				JSONObject car = (JSONObject)parser.parse(currentJSONString);
				if(car.get("Plate Number").equals(i_PlateNumber))
			    {	
					return (String)car.get("New Color");
			    }
			}
			
		}
		catch (FileNotFoundException e1) 
		{
            e1.printStackTrace();
        }
		catch (IOException e1) 
		{
            e1.printStackTrace();
        }
		catch (org.json.simple.parser.ParseException e1)
		{
			e1.printStackTrace();
		}
		return "None";
	}
	
	public ShRoomForm() 
	{
		setResizable(false);
		setBackground(SystemColor.activeCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShRoomForm.class.getResource("/resources/Car-Repair-Blue-2-icon.png")));
		
		// Components Initialization
		setTitle("Car Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 627);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JPanel panelFillForm = new JPanel();
		panelFillForm.setBounds(25, 185, 892, 384);
		JButton btnPrintByService = new JButton("Print By Car Service");
		btnPrintByService.setHorizontalAlignment(SwingConstants.LEFT);
		btnPrintByService.setIcon(new ImageIcon(ShRoomForm.class.getResource("/resources/Actions-view-list-details-icon.png")));
		btnPrintByService.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnPrintByService.setBounds(118, 127, 231, 47);
		JButton btnUpdateCar = new JButton("Update Car Details");
		btnUpdateCar.setHorizontalAlignment(SwingConstants.LEFT);
		btnUpdateCar.setIcon(new ImageIcon(ShRoomForm.class.getResource("/resources/edit-file-icon.png")));
		btnUpdateCar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnUpdateCar.setBounds(364, 69, 231, 47);
		JButton btnViewFullDetails = new JButton("View Full Car Details");
		btnViewFullDetails.setHorizontalAlignment(SwingConstants.LEFT);
		btnViewFullDetails.setIcon(new ImageIcon(ShRoomForm.class.getResource("/resources/Apps-preferences-contact-list-icon.png")));
		btnViewFullDetails.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnViewFullDetails.setBounds(364, 127, 231, 47);
		JButton btnLogout = new JButton("Logout");
		btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
		btnLogout.setIcon(new ImageIcon(ShRoomForm.class.getResource("/resources/logout.png")));
		btnLogout.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnLogout.setBounds(608, 127, 231, 47);
		JLabel lblCarManagement = new JLabel("Car Management System Menu");
		lblCarManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarManagement.setBounds(142, 0, 620, 66);
		lblCarManagement.setFont(new Font("Arial Black", Font.BOLD, 29));	
		JButton btnAddCar = new JButton("Add Car");
		btnAddCar.setHorizontalAlignment(SwingConstants.LEFT);
		btnAddCar.setIcon(new ImageIcon(ShRoomForm.class.getResource("/resources/car-add-icon.png")));
		btnAddCar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnAddCar.setBounds(118, 69, 231, 47);
		JLabel lblModel = new JLabel("Model:");
		lblModel.setBounds(65, 95, 61, 22);
		lblModel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JLabel lblPlateNumber = new JLabel("Plate Number:");
		lblPlateNumber.setBounds(65, 25, 131, 22);
		lblPlateNumber.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(65, 60, 50, 22);
		lblType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxCarType = new JComboBox<String>();
		comboBoxCarType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxCarType.setBounds(258, 60, 149, 20);
		comboBoxCarType.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "ClassA", "ClassB", "ClassC"}));
		JFormattedTextField textPlateNumber = new JFormattedTextField();
		textPlateNumber.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textPlateNumber.setBounds(258, 25, 149, 20);
		textPlateNumber.setText("Type here");
		JTextField textFieldModel = new JTextField();
		textFieldModel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textFieldModel.setBounds(258, 95, 149, 20);
		textFieldModel.setText("Type here");
		textFieldModel.setColumns(10);
		JFormattedTextField textEngineCap = new JFormattedTextField();
		textEngineCap.setText("Type here");
		textEngineCap.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textEngineCap.setBounds(257, 199, 150, 20);
		JLabel lblNumberOfWheels = new JLabel("Number of wheels:");
		lblNumberOfWheels.setBounds(65, 235, 172, 22);
		lblNumberOfWheels.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxNumOfWheels = new JComboBox<String>();
		comboBoxNumOfWheels.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxNumOfWheels.setBounds(258, 235, 149, 20);
		comboBoxNumOfWheels.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "4", "6"}));
		JLabel lblEngineType = new JLabel("Engine Type:");
		lblEngineType.setBounds(65, 165, 117, 22);
		lblEngineType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxEngineType = new JComboBox<String>();
		
		comboBoxEngineType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxEngineType.setBounds(258, 165, 149, 20);
		comboBoxEngineType.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "Fuel", "Electric"}));
		JLabel lblFuelType = new JLabel("Fuel Type:");
		lblFuelType.setBounds(530, 165, 107, 22);
		lblFuelType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxFuelType = new JComboBox<String>();
		comboBoxFuelType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxFuelType.setBounds(650, 165, 140, 20);
		comboBoxFuelType.setModel(new DefaultComboBoxModel<String>(new String[] {"Select","Li-ion Battery", "Petrol", "Diesel"}));
		JLabel lblWheelsManufacturer = new JLabel("Wheels' Manufacturer:");
		lblWheelsManufacturer.setBounds(65, 270, 209, 22);
		lblWheelsManufacturer.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JFormattedTextField textWheelManufacturer = new JFormattedTextField();
		textWheelManufacturer.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textWheelManufacturer.setBounds(257, 270, 150, 20);
		textWheelManufacturer.setText("Type here");
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(528, 60, 71, 22);
		lblColor.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxColor = new JComboBox<String>();
		comboBoxColor.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxColor.setBounds(650, 60, 140, 20);
		comboBoxColor.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "Red", "Green", "Blue", "White", "Black", "Yellow", "Violet", "Pink"}));
		JLabel lblOption = new JLabel("New Color:");
		lblOption.setBounds(528, 130, 120, 22);
		lblOption.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxOption = new JComboBox<String>();
		comboBoxOption.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxOption.setBounds(650, 130, 140, 20);
		comboBoxOption.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "None", "Red", "Green", "Blue", "White", "Black", "Yellow", "Violet", "Pink"}));
		JLabel lblNumberOfDoors = new JLabel("Number Of Doors:");
		lblNumberOfDoors.setBounds(65, 340, 164, 22);
		lblNumberOfDoors.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxNumOfDoors = new JComboBox<String>();
		comboBoxNumOfDoors.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxNumOfDoors.setBounds(258, 340, 149, 20);
		comboBoxNumOfDoors.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "2", "3", "4", "5"}));
		JLabel lblService = new JLabel("Service:");
		lblService.setBounds(528, 95, 85, 22);
		lblService.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxCarService = new JComboBox<String>();
		comboBoxCarService.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxCarService.setBounds(650, 95, 140, 20);
		comboBoxCarService.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "Color Service", "Wash Service", "Fill Air"}));
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(587, 291, 122, 49);
		btnSubmit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		JLabel lblClientName = new JLabel("Client Name:");
		lblClientName.setBounds(528, 200, 122, 22);
		lblClientName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JFormattedTextField textClientName = new JFormattedTextField();
		textClientName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textClientName.setBounds(650, 200, 140, 20);
		textClientName.setText("Type here");
		JLabel lblClientPhone = new JLabel("Client Phone:");
		lblClientPhone.setBounds(528, 235, 131, 22);
		lblClientPhone.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JFormattedTextField textClientPhoneNumber = new JFormattedTextField();
		textClientPhoneNumber.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textClientPhoneNumber.setBounds(650, 235, 140, 20);
		textClientPhoneNumber.setText("Type here");
		JLabel lblEngineCapacity = new JLabel("Engine Capacity:");
		lblEngineCapacity.setBounds(65, 200, 201, 22);
		lblEngineCapacity.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JLabel lblWheelsAirPressure = new JLabel("Wheels' Air Pressure:");
		lblWheelsAirPressure.setBounds(65, 305, 200, 22);
		lblWheelsAirPressure.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JLabel lblLicenseType = new JLabel("License Type:");
		lblLicenseType.setBounds(65, 130, 200, 22);
		lblLicenseType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxLicenseType = new JComboBox<String>();
		comboBoxLicenseType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxLicenseType.setBounds(258, 130, 149, 20);
		comboBoxLicenseType.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "A", "B", "C"}));
		JFormattedTextField textWheelAirPressure = new JFormattedTextField();
		textWheelAirPressure.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textWheelAirPressure.setBounds(258, 305, 149, 20);
		textWheelAirPressure.setText("Type here");
		JButton btnOK= new JButton("OK");
		btnOK.setBounds(417, 17, 55, 35);
		btnOK.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		JButton btnMakeAction = new JButton("Make Action");
		btnMakeAction.setHorizontalAlignment(SwingConstants.LEFT);
		btnMakeAction.setIcon(new ImageIcon(ShRoomForm.class.getResource("/resources/Actions-configure-icon.png")));
		btnMakeAction.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnMakeAction.setBounds(608, 69, 231, 47);
		
		
		// Events
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) 
			{
				panelFillForm.setVisible(false);
				contentPane.setFocusable(true);
				controller.intializeJSONArray();
				controller.initializeShRoomDictionaries();
			}
		});
		///////////////////////////////////////////////////////////
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				contentPane.setFocusable(true);
				panelFillForm.setFocusable(false);
			}
			@Override
			public void mouseExited(MouseEvent e)
			{
				contentPane.setFocusable(false);
				panelFillForm.setFocusable(true);
			}
		});
		///////////////////////////////////////////////////////////
		textPlateNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) 
			{
				if((isCarExists(textPlateNumber.getText())) && (currentState.equals(CurrentState.AddCar))) 
				{
					JOptionPane.showMessageDialog(panelFillForm, "The car already exists." + System.lineSeparator() + "Changing service of car number:" + textPlateNumber.getText() + " to Available.");
					controller.changeCarService(textPlateNumber.getText(),"Available");
					textPlateNumber.setText("");
				}
			}
		});
		
		textEngineCap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textEngineCap.setText("");
			}
		});
		
		
		textWheelAirPressure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textWheelAirPressure.setText("");
			}
		});
		
		textPlateNumber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textPlateNumber.setText("");
			}
		});
		
		textFieldModel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textFieldModel.setText("");
			}
		});
		
		textWheelManufacturer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textWheelManufacturer.setText("");
			}
		});
		
		textClientName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textClientName.setText("");
			}
		});
		
		textClientName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) 
			{
				for(char c : textClientName.getText().toCharArray())
				{
					if(Character.isDigit(c)) 
					{
						JOptionPane.showMessageDialog(panelFillForm, "Client name can't include digits!");
						textClientName.setText("");
						break;
					}
				}
			}
		});
		
		textClientPhoneNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) 
			{
				for(char c : textClientPhoneNumber.getText().toCharArray())
				{
					if(Character.isLetter(c)) 
					{
						JOptionPane.showMessageDialog(panelFillForm, "Client phone number can't include letters!");
						textClientPhoneNumber.setText("");
						break;
					}
				}
			}
		});

		
		textWheelAirPressure.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) 
			{
				for(char c : textWheelAirPressure.getText().toCharArray())
				{
					if(Character.isLetter(c)) 
					{
						JOptionPane.showMessageDialog(panelFillForm, "Wheels Air Pressure can't include letters!");
						textWheelAirPressure.setText("");
						break;
					}
				}
			}
		});
		
		textClientPhoneNumber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textClientPhoneNumber.setText("");
			}
		});
		
		comboBoxEngineType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxEngineType.getSelectedItem().equals("Electric"))
				{
						comboBoxFuelType.setSelectedItem("Li-ion Battery");
						textEngineCap.setText("Enter in hours");
				}
				if (comboBoxEngineType.getSelectedItem().equals("Fuel"))
				{
					textEngineCap.setText("Enter in liters");
					if (comboBoxCarType.getSelectedItem().equals("ClassB"))
					{
						comboBoxFuelType.setSelectedItem("Diesel");
					}
					if (comboBoxCarType.getSelectedItem().equals("ClassA"))
					{
						comboBoxFuelType.setSelectedItem("Petrol");
					}
					
				}
				comboBoxFuelType.setEnabled(false);
				
			}
		});

		comboBoxCarService.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				if (comboBoxCarService.getSelectedItem().equals("Color Service")) {
					comboBoxOption.setSelectedItem("Select");
					comboBoxOption.setEnabled(true);
				}
				if (comboBoxCarService.getSelectedItem().equals("Wash Service")) {
					comboBoxOption.setSelectedItem("None");
					comboBoxOption.setEnabled(false);
				}
				if (comboBoxCarService.getSelectedItem().equals("Fill Air")) {
					comboBoxOption.setSelectedItem("None");
					comboBoxOption.setEnabled(false);
				}
				//
			}       
		});

		
		comboBoxCarType.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (comboBoxCarType.getSelectedItem().equals("ClassB"))
				{
						comboBoxLicenseType.setSelectedItem("B");
						comboBoxNumOfWheels.setSelectedItem("4");
						comboBoxNumOfWheels.setEnabled(true);
						comboBoxLicenseType.setEnabled(false);
						comboBoxNumOfDoors.setEnabled(true);
						
				}
				if (comboBoxCarType.getSelectedItem().equals("ClassA"))
				{
					comboBoxLicenseType.setSelectedItem("A");
					comboBoxNumOfWheels.setSelectedItem("4");
					comboBoxNumOfWheels.setEnabled(true);
					comboBoxLicenseType.setEnabled(false);
					comboBoxNumOfDoors.setEnabled(true);
				}
				
				comboBoxEngineType.setSelectedIndex(0);
				comboBoxEngineType.setEnabled(true);
				comboBoxFuelType.setSelectedIndex(0);
				comboBoxFuelType.setEnabled(true);
				
				if (comboBoxCarType.getSelectedItem().equals("ClassC"))
				{
					comboBoxLicenseType.setSelectedItem("C");
					comboBoxNumOfWheels.setSelectedItem("4");
					comboBoxEngineType.setSelectedItem("Electric");
					comboBoxFuelType.setSelectedItem("Li-ion Battery");
					comboBoxFuelType.setEnabled(false);
					comboBoxEngineType.setEnabled(false);
					comboBoxNumOfWheels.setEnabled(false);
					comboBoxLicenseType.setEnabled(false);
					comboBoxNumOfDoors.setEnabled(true);
				}	
				
			}
				
			
		});
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});

		btnOK.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if((currentState.equals(CurrentState.UpdateCar)) || (currentState.equals(CurrentState.MakeAction))|| (currentState.equals(CurrentState.ViewDetails)) )
				{
					boolean carFound = false;
					try
					{
						FileReader reader = new FileReader("Cars.json");
						BufferedReader buffer = new BufferedReader(reader);
						JSONParser parser = new JSONParser();
						String currentJSONString  = "";
	
						while((currentJSONString = buffer.readLine()) != null ) 
						{
							JSONObject car = (JSONObject)parser.parse(currentJSONString);
							if(car.get("Plate Number").equals(textPlateNumber.getText()))
						    {
								textPlateNumber.setText(car.get("Plate Number").toString());
								textWheelManufacturer.setText(car.get("Wheel Manufacturer").toString());
								textFieldModel.setText(car.get("Model").toString());
								textEngineCap.setText(car.get("Engine Capacity").toString());
								textWheelAirPressure.setText(car.get("Wheel Air Pressure").toString());
								comboBoxColor.setSelectedItem(car.get("Color"));
								comboBoxEngineType.setSelectedItem(car.get("Engine Type"));
								comboBoxFuelType.setSelectedItem(car.get("Fuel Type"));
								comboBoxNumOfDoors.setSelectedItem(car.get("NumOfDoors"));
								comboBoxNumOfWheels.setSelectedItem(car.get("NumOfWheels"));
								comboBoxCarType.setSelectedItem(car.get("Type"));
								comboBoxLicenseType.setSelectedItem(car.get("License Type"));
								carFound = true;
						    	break;
						    }
						}
						reader = new FileReader("Clients.json");
						buffer = new BufferedReader(reader);
						currentJSONString  = "";
	
						while((currentJSONString = buffer.readLine()) != null ) 
						{
							JSONObject client = (JSONObject)parser.parse(currentJSONString);
							if(client.get("Car#").equals(textPlateNumber.getText())) 
							{
								textClientName.setText(client.get("Name").toString());
								textClientPhoneNumber.setText(client.get("Phone").toString());
								comboBoxCarService.setSelectedItem(client.get("Service")); 
								break;
							}
						}
						buffer.close();
					}
					catch (FileNotFoundException e1) 
					{
			            e1.printStackTrace();
			        }
					catch (IOException e1) 
					{
			            e1.printStackTrace();
			        }
					catch (org.json.simple.parser.ParseException e1)
					{
						e1.printStackTrace();
					}
					
					comboBoxCarService.setEnabled(true);
					if(carFound)
					{
						if(currentState.equals(currentState.ViewDetails))
						{
							ClientForm clientF = new ClientForm(controller.getCarFullDetails(textPlateNumber.getText()), "Full Car Details");
							clientF.setTitle("Full Car Details");
							clientF.setVisible(true);
						}
						
						if(currentState.equals(CurrentState.UpdateCar))
						{
							JOptionPane.showMessageDialog(panelFillForm, "Details are shown." + System.lineSeparator()+ "Service can be updated.");
						}
						
						if(currentState.equals(CurrentState.MakeAction))
						{
							Object[] possibilities = {"Select","Paint New Color","Wash Car","Inflate air pressure to maximum", "Add energy to car"};
							String s = (String)JOptionPane.showInputDialog(
						                    contentPane,
						                    "Please select Action:\n"
									                    ,
									                    "Make Action",
									                    JOptionPane.PLAIN_MESSAGE,
									                    null,
									                    possibilities,
									                    "Select");
									
							if (s.equals("Inflate air pressure to maximum"))
							{
								controller.inflateWheelsToMax((String)textPlateNumber.getText());
								controller.changeCarAirPressure((String)textPlateNumber.getText());
								JOptionPane.showMessageDialog(panelFillForm, "Wheels Air pressure fully filled!");
								
							}

							else if(s.equals("Paint New Color"))
							{
								String ncolor = getNewColor((String)textPlateNumber.getText());
								controller.changeCarColor((String)textPlateNumber.getText(), ncolor);
								JOptionPane.showMessageDialog(panelFillForm, "Car Painted with Color: " + ncolor);
							}

							else if(s.equals("Wash Car"))
							{
								controller.washCar((String)textPlateNumber.getText());
								JOptionPane.showMessageDialog(panelFillForm, "Car Washed");
							}
							
							else if (s.equals("Add energy to car"))
							{
								String energyType = controller.getEnergyType((String)textPlateNumber.getText());
								
								String FuelOrElectric = "";
								if(energyType.equals("Fuel")) 
								{
									FuelOrElectric = "in liters";
								}
								else
								{
									FuelOrElectric = "in minutes";
								}
								
								String s2 = (String)JOptionPane.showInputDialog(
							                    panelFillForm,
							                    "Please enter amount of energy to add " + FuelOrElectric
										                    ,
										                    "Add Energy",
										                    JOptionPane.PLAIN_MESSAGE,
										                    null,
										                    null,
										                    "Type here");
	
								if (energyType.equals("Fuel"))
								{
									
									if(controller.refuelCar((String)textPlateNumber.getText(),s2))
									{
										JOptionPane.showMessageDialog(panelFillForm, "Energy succesfully filled!");
										controller.changeCarEnergy((String)textPlateNumber.getText());
										
									}
									else
									{
										JOptionPane.showMessageDialog(panelFillForm, "Max energy can't be exceeded");
									}
									
								}
								
								if (energyType.equals("Electric"))
								{
									
									if(controller.chargeCar((String)textPlateNumber.getText(),s2))
									{
										JOptionPane.showMessageDialog(panelFillForm, "Energy succesfully filled!");
										controller.changeCarEnergy((String)textPlateNumber.getText());
									}
									else
									{
										JOptionPane.showMessageDialog(panelFillForm, "Max energy can't be exceeded");
									}
									
								}
								
								
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog(panelFillForm, "Car not found!" + System.lineSeparator() + "Please make sure the plate number is correct.");
					}
				}
			}
		});
		
		
		
		btnMakeAction.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				lblPlateNumber.setVisible(true);
				textPlateNumber.setVisible(true);
				panelFillForm.setVisible(true);
				currentState = CurrentState.MakeAction;
				panelFillForm.setVisible(true);
				btnSubmit.setVisible(false);
				textClientName.setVisible(false);
				textClientPhoneNumber.setVisible(false);
				textWheelManufacturer.setVisible(false);
				textFieldModel.setVisible(false);
				textEngineCap.setVisible(false);
				textWheelAirPressure.setVisible(false);
				comboBoxColor.setVisible(false);
				comboBoxEngineType.setVisible(false);
				comboBoxFuelType.setVisible(false);
				comboBoxNumOfDoors.setVisible(false);
				comboBoxNumOfWheels.setVisible(false);
				comboBoxCarService.setVisible(false);
				comboBoxCarType.setVisible(false);
				comboBoxLicenseType.setVisible(false);
				comboBoxOption.setVisible(false);
				btnOK.setVisible(true);
				lblClientName.setVisible(false);
				lblClientPhone.setVisible(false);
				lblColor.setVisible(false);
				lblOption.setVisible(false);
				lblEngineCapacity.setVisible(false);
				lblEngineType.setVisible(false);
				lblFuelType.setVisible(false);
				lblLicenseType.setVisible(false);
				lblModel.setVisible(false);
				lblNumberOfDoors.setVisible(false);
				lblNumberOfWheels.setVisible(false);
				lblType.setVisible(false);
				lblWheelsAirPressure.setVisible(false);
				lblWheelsManufacturer.setVisible(false);
				lblService.setVisible(false);
			}	
		});
		
		btnAddCar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				panelFillForm.setVisible(true);
				currentState = CurrentState.AddCar;
				panelFillForm.setVisible(true);
				btnSubmit.setVisible(true);
				textClientName.setEnabled(true);
				textClientPhoneNumber.setEnabled(true);
				textWheelManufacturer.setEnabled(true);
				textFieldModel.setEnabled(true);
				textEngineCap.setEnabled(true);
				textWheelAirPressure.setEnabled(true);
				comboBoxColor.setEnabled(true);
				comboBoxEngineType.setEnabled(true);
				comboBoxFuelType.setEnabled(true);
				comboBoxNumOfDoors.setEnabled(true);
				comboBoxNumOfWheels.setEnabled(true);
				comboBoxCarService.setEnabled(true);
				comboBoxCarType.setEnabled(true);
				comboBoxLicenseType.setEnabled(true);
				comboBoxOption.setEnabled(true);
				textClientName.setVisible(true);
				textClientPhoneNumber.setVisible(true);
				textWheelManufacturer.setVisible(true);
				textFieldModel.setVisible(true);
				textEngineCap.setVisible(true);
				textWheelAirPressure.setVisible(true);
				comboBoxColor.setVisible(true);
				comboBoxEngineType.setVisible(true);
				comboBoxFuelType.setVisible(true);
				comboBoxNumOfDoors.setVisible(true);
				comboBoxNumOfWheels.setVisible(true);
				comboBoxCarService.setVisible(true);
				comboBoxCarType.setVisible(true);
				comboBoxLicenseType.setVisible(true);
				comboBoxOption.setVisible(true);
				btnOK.setVisible(false);
				lblClientName.setVisible(true);
				lblClientPhone.setVisible(true);
				lblColor.setVisible(true);
				lblOption.setVisible(true);
				lblEngineCapacity.setVisible(true);
				lblEngineType.setVisible(true);
				lblFuelType.setVisible(true);
				lblLicenseType.setVisible(true);
				lblModel.setVisible(true);
				lblNumberOfDoors.setVisible(true);
				lblNumberOfWheels.setVisible(true);
				lblType.setVisible(true);
				lblWheelsAirPressure.setVisible(true);
				lblWheelsManufacturer.setVisible(true);
				lblService.setVisible(true);
			}
		});
		
		btnUpdateCar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				panelFillForm.setVisible(true);
				currentState = CurrentState.UpdateCar;
				btnSubmit.setVisible(true);
				panelFillForm.setVisible(true);
				textClientName.setEnabled(false);
				textClientPhoneNumber.setEnabled(false);
				textWheelManufacturer.setEnabled(false);
				textFieldModel.setEnabled(false);
				textEngineCap.setEnabled(false);
				textWheelAirPressure.setEnabled(false);
				comboBoxColor.setEnabled(false);
				comboBoxOption.setEnabled(false);
				comboBoxEngineType.setEnabled(false);
				comboBoxFuelType.setEnabled(false);
				comboBoxNumOfDoors.setEnabled(false);
				comboBoxNumOfWheels.setEnabled(false);
				comboBoxCarService.setEnabled(false);
				comboBoxCarType.setEnabled(false);
				comboBoxLicenseType.setEnabled(false);
				textClientName.setVisible(true);
				textClientPhoneNumber.setVisible(true);
				textWheelManufacturer.setVisible(true);
				textFieldModel.setVisible(true);
				textEngineCap.setVisible(true);
				textWheelAirPressure.setVisible(true);
				comboBoxColor.setVisible(true);
				comboBoxOption.setVisible(true);
				comboBoxEngineType.setVisible(true);
				comboBoxFuelType.setVisible(true);
				comboBoxNumOfDoors.setVisible(true);
				comboBoxNumOfWheels.setVisible(true);
				comboBoxCarService.setVisible(true);
				comboBoxCarType.setVisible(true);
				comboBoxLicenseType.setVisible(true);
				btnOK.setVisible(true);
				lblClientName.setVisible(true);
				lblClientPhone.setVisible(true);
				lblColor.setVisible(true);
				lblOption.setVisible(true);
				lblEngineCapacity.setVisible(true);
				lblEngineType.setVisible(true);
				lblFuelType.setVisible(true);
				lblLicenseType.setVisible(true);
				lblModel.setVisible(true);
				lblNumberOfDoors.setVisible(true);
				lblNumberOfWheels.setVisible(true);
				lblType.setVisible(true);
				lblWheelsAirPressure.setVisible(true);
				lblWheelsManufacturer.setVisible(true);
				lblService.setVisible(true);
			}
		});
		
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(currentState == CurrentState.AddCar)
				{	
					if(checkIfFilled(panelFillForm))
					{
						String resultAirPressure =controller.CheckAirPressureValidation((String)comboBoxCarType.getSelectedItem(),(String)textWheelAirPressure.getText());
						String resultEnergy = controller.CheckEnergyValidation((String)comboBoxCarType.getSelectedItem(),(String)comboBoxEngineType.getSelectedItem(),textEngineCap.getText());
						
						String minOrLtr = "";
						if (((String)comboBoxEngineType.getSelectedItem()).equals("Fuel"))
						{
							minOrLtr = "liters";
						}
						
						else
						{
							minOrLtr = "hours";
						}
						
						if(!resultAirPressure.equals("") || !resultEnergy.equals(""))
						{
							if(!resultAirPressure.equals(""))
							{
								JOptionPane.showMessageDialog(panelFillForm, "Air Pressure too high!" + System.lineSeparator() +"The max is:" + resultAirPressure);
							}
							
							if(!resultEnergy.equals(""))
							{
								JOptionPane.showMessageDialog(panelFillForm, "Engine Capacity too high!" + System.lineSeparator() +"The max is:" + resultEnergy+ " " + minOrLtr);
							}
							
							
						}
						else
						{
							newColor = comboBoxOption.getSelectedItem().toString();
							JSONObject car = new JSONObject();
							JSONObject client = new JSONObject();
							
							// Creating Car 
							car.put("Plate Number", textPlateNumber.getText());
							car.put("Type", comboBoxCarType.getSelectedItem());
							car.put("License Type", comboBoxLicenseType.getSelectedItem());
							car.put("Model", textFieldModel.getText());
							car.put("Engine Type", comboBoxEngineType.getSelectedItem());
							car.put("Engine Capacity", textEngineCap.getText());
							car.put("Fuel Type", comboBoxFuelType.getSelectedItem());
							car.put("NumOfWheels", comboBoxNumOfWheels.getSelectedItem());
							car.put("Wheel Manufacturer", textWheelManufacturer.getText());
							car.put("Wheel Air Pressure", textWheelAirPressure.getText());
							car.put("New Color", comboBoxOption.getSelectedItem());
							car.put("Color", comboBoxColor.getSelectedItem());
							car.put("NumOfDoors", comboBoxNumOfDoors.getSelectedItem());
							car.put("Washed","No");
							controller.AddCarToFile(car); //Write to file
							
							// Creating Client JSON
							client.put("Name", textClientName.getText());
							client.put("Phone", textClientPhoneNumber.getText());
							client.put("Service", comboBoxCarService.getSelectedItem());
							client.put("Car#", textPlateNumber.getText());
							controller.AddClientToFile(client); //Write to file
							
							// Creating Car Object
							controller.addCar(car, client);

							textPlateNumber.setText("");
							textClientName.setText("");
							textClientPhoneNumber.setText("");
							textWheelManufacturer.setText("");
							textFieldModel.setText("");
							textWheelAirPressure.setText("");
							textEngineCap.setText("");
							comboBoxColor.setSelectedIndex(0);
							comboBoxEngineType.setSelectedIndex(0);
							comboBoxEngineType.setSelectedIndex(0);
							comboBoxFuelType.setSelectedIndex(0);
							comboBoxNumOfDoors.setSelectedIndex(0);
							comboBoxNumOfWheels.setSelectedIndex(0);
							comboBoxCarService.setSelectedIndex(0);
							comboBoxCarType.setSelectedIndex(0);
							JOptionPane.showMessageDialog(panelFillForm, "Car added succesfully!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(panelFillForm, "Form must be fully filled!");
					}
				}
				else if(currentState == CurrentState.UpdateCar) 
				{
					controller.changeCarService(textPlateNumber.getText(),(String)comboBoxCarService.getSelectedItem());
					JOptionPane.showMessageDialog(panelFillForm, "Service of Car number: " + textPlateNumber.getText() + " updated succesfully!");
					textPlateNumber.setText("");
				}
			}
		});

		btnPrintByService.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				comboBoxOption.setVisible(false);
				lblPlateNumber.setVisible(false);
				textPlateNumber.setVisible(false);
				btnOK.setVisible(false);
				Object[] possibilities = {"Select", "Color Service", "Wash Service", "Fill Air"};
				String s = (String)JOptionPane.showInputDialog(
				                    contentPane,
				                    "Please select service:\n"
				                    ,
				                    "Print By Car Service",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    possibilities,
				                    "Select");
				
				Report reportForm = new Report(controller.GenerateStringReport(s));
				reportForm.setVisible(true);
			}
		});
		
		btnViewFullDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				comboBoxOption.setVisible(false);
				lblPlateNumber.setVisible(true);
				textPlateNumber.setVisible(true);
				panelFillForm.setVisible(true);
				currentState = CurrentState.ViewDetails;
				panelFillForm.setVisible(true);
				textClientName.setVisible(false);
				textClientPhoneNumber.setVisible(false);
				textWheelManufacturer.setVisible(false);
				textFieldModel.setVisible(false);
				textEngineCap.setVisible(false);
				textWheelAirPressure.setVisible(false);
				comboBoxColor.setVisible(false);
				comboBoxColor.setVisible(false);
				comboBoxEngineType.setVisible(false);
				comboBoxFuelType.setVisible(false);
				comboBoxNumOfDoors.setVisible(false);
				comboBoxNumOfWheels.setVisible(false);
				comboBoxCarService.setVisible(false);
				comboBoxCarType.setVisible(false);
				comboBoxLicenseType.setVisible(false);
				btnOK.setVisible(true);
				btnSubmit.setVisible(false);
				lblClientName.setVisible(false);
				lblClientPhone.setVisible(false);
				lblColor.setVisible(false);
				lblOption.setVisible(false);
				lblEngineCapacity.setVisible(false);
				lblEngineType.setVisible(false);
				lblFuelType.setVisible(false);
				lblLicenseType.setVisible(false);
				lblModel.setVisible(false);
				lblNumberOfDoors.setVisible(false);
				lblNumberOfWheels.setVisible(false);
				lblType.setVisible(false);
				lblWheelsAirPressure.setVisible(false);
				lblWheelsManufacturer.setVisible(false);
				lblService.setVisible(false);
			}
		});
		
		
		contentPane.setLayout(null);
		
		// Panel and ContentPane Layout
		panelFillForm.setBorder(null);
		panelFillForm.setLayout(null);
		panelFillForm.add(lblService);
		panelFillForm.add(lblType);
		panelFillForm.add(lblPlateNumber);
		panelFillForm.add(lblEngineType);
		panelFillForm.add(lblModel);
		panelFillForm.add(lblNumberOfWheels);
		panelFillForm.add(lblOption);
		panelFillForm.add(comboBoxOption);
		panelFillForm.add(comboBoxNumOfWheels);
		panelFillForm.add(comboBoxCarType);
		panelFillForm.add(comboBoxEngineType);
		panelFillForm.add(textFieldModel);
		panelFillForm.add(textPlateNumber);
		panelFillForm.add(lblClientName);
		panelFillForm.add(lblClientPhone);
		panelFillForm.add(lblFuelType);
		panelFillForm.add(comboBoxFuelType);
		panelFillForm.add(textClientPhoneNumber);
		panelFillForm.add(textClientName);
		panelFillForm.add(btnSubmit);
		panelFillForm.add(lblWheelsManufacturer);
		panelFillForm.add(lblColor);
		panelFillForm.add(lblNumberOfDoors);
		panelFillForm.add(comboBoxCarService);
		panelFillForm.add(comboBoxNumOfDoors);
		panelFillForm.add(comboBoxColor);
		panelFillForm.add(textWheelManufacturer);
		panelFillForm.add(lblEngineCapacity);
		panelFillForm.add(lblWheelsAirPressure);
		panelFillForm.add(lblLicenseType);
		panelFillForm.add(comboBoxLicenseType);
		panelFillForm.add(textWheelAirPressure);
		panelFillForm.add(btnOK);
		panelFillForm.add(textEngineCap);
		contentPane.add(panelFillForm);
		contentPane.add(lblCarManagement);
		contentPane.add(btnAddCar);
		contentPane.add(btnUpdateCar);
		contentPane.add(btnPrintByService);
		contentPane.add(btnViewFullDetails);
		contentPane.add(btnMakeAction);
		contentPane.add(btnLogout);
	}
}
