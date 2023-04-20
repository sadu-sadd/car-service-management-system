package View;

import java.awt.EventQueue;
import Controller.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textToInsert;
	private Management controller = new Management();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the frame.
	public Main() {
		setResizable(false);
		setForeground(SystemColor.activeCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/Car-Repair-Blue-2-icon.png")));
		setFont(new Font("Tahoma", Font.BOLD, 18));
		setTitle("Car Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 623, 287);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setForeground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JLabel lblCarManagement = new JLabel("Welcome To Car Management System");
		lblCarManagement.setBackground(UIManager.getColor("Button.background"));
		lblCarManagement.setBounds(0, 11, 599, 50);
		lblCarManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarManagement.setForeground(Color.BLACK);
		lblCarManagement.setFont(new Font("Arial Black", Font.BOLD, 24));
		JLabel lblUserName = new JLabel("Who are you?:");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBackground(UIManager.getColor("Button.background"));
		lblUserName.setForeground(Color.BLACK);
		lblUserName.setBounds(121, 79, 149, 42);
		lblUserName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		JLabel lblToInsert = new JLabel("ToInsert:");
		lblToInsert.setHorizontalAlignment(SwingConstants.CENTER);
		lblToInsert.setBackground(UIManager.getColor("Button.background"));
		lblToInsert.setForeground(Color.BLACK);
		lblToInsert.setBounds(121, 132, 212, 29);
		lblToInsert.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		JRadioButton rdbtnClient = new JRadioButton("Client");
		rdbtnClient.setBackground(UIManager.getColor("Button.background"));
		rdbtnClient.setOpaque(false);
		rdbtnClient.setBounds(287, 86, 85, 29);
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(244, 186, 121, 42);
		btnSubmit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		rdbtnClient.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		JRadioButton rdbtnTeam = new JRadioButton("Manager");
		rdbtnTeam.setOpaque(false);
		rdbtnTeam.setBounds(382, 86, 120, 29);
		rdbtnTeam.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		textToInsert = new JTextField();
		textToInsert.setBounds(343, 132, 114, 27);
		textToInsert.setColumns(10);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) 
			{
				lblToInsert.setVisible(false);
				textToInsert.setVisible(false);
				controller.initializeShRoomDictionaries();
			}
		});
		
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(rdbtnClient.isSelected() && !textToInsert.getText().isEmpty()) 
				{
					//dispose();
					ClientForm clientF = new ClientForm(controller.getClientFullDetails(textToInsert.getText()), "Client Car Details");
					clientF.setTitle("Client Car Details");
					clientF.setVisible(true);
				}
				else if(rdbtnTeam.isSelected() && textToInsert.getText().contentEquals("admin")) 
				{
					//dispose();
					ShRoomForm shRoomForm = new ShRoomForm();
					shRoomForm.setVisible(true);
				}
			}
		});
		
		textToInsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textToInsert.setText("");
			}
		});

		rdbtnTeam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				rdbtnTeam.setSelected(true);
				rdbtnClient.setSelected(false);
				lblToInsert.setText("Enter Password:");
				lblToInsert.setVisible(true);
				textToInsert.setVisible(true);
			}
		});
		rdbtnClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				rdbtnClient.setSelected(true);
				rdbtnTeam.setSelected(false);
				lblToInsert.setText("Insert Plate Number:");
				lblToInsert.setVisible(true);
				textToInsert.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(lblCarManagement);
		contentPane.add(lblUserName);
		contentPane.add(lblToInsert);
		contentPane.add(rdbtnClient);
		contentPane.add(rdbtnTeam);
		contentPane.add(textToInsert);
		contentPane.add(btnSubmit);
	}
}
