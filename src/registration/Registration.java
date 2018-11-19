package registration;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import menu.Menu;
import javax.swing.JComboBox;

public class Registration {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPanel panel_1;
	private JTextField txtProgramDetails;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registration window = new Registration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Registration() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Menu go=new Menu();
				go.frame.setVisible(true);
			}
		});
		frame.setResizable(false);
		frame.setTitle("Academi");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLocationRelativeTo(null);
		frame.setBounds(100, 100, 894, 490);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 868, 440);
		tabbedPane.setBorder(null);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		tabbedPane.addTab("Registration", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 187, 843, 214);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label = new JLabel("Photo");
		label.setBounds(10, 11, 147, 153);
		panel_2.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(new LineBorder(SystemColor.inactiveCaptionText));
		
		textField = new JTextField();
		textField.setBounds(167, 11, 499, 30);
		panel_2.add(textField);
		textField.setText("name");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.GRAY);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(167, 52, 237, 30);
		panel_2.add(textField_1);
		textField_1.setText("cell");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setForeground(Color.GRAY);
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(429, 52, 237, 30);
		panel_2.add(textField_2);
		textField_2.setText("email");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setForeground(Color.GRAY);
		textField_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(167, 93, 499, 30);
		panel_2.add(textField_3);
		textField_3.setText("address");
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setForeground(Color.GRAY);
		textField_3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_3.setColumns(10);
		
		txtProgramDetails = new JTextField();
		txtProgramDetails.setBounds(167, 134, 499, 30);
		panel_2.add(txtProgramDetails);
		txtProgramDetails.setText("program details");
		txtProgramDetails.setHorizontalAlignment(SwingConstants.CENTER);
		txtProgramDetails.setForeground(Color.GRAY);
		txtProgramDetails.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtProgramDetails.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.text);
		tabbedPane.addTab("Database", null, panel_1, null);
		panel_1.setLayout(null);
	}
}
