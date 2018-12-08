package employees;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import menu.Menu;

public class EmployeeWindow {

	public JFrame frame;
	private JTextField nameField;
	private JTextField cellField;
	private JTextField emailField;
	private JTextField NIDField;
	private JTextField fatherNameField;
	private JTextField designationField;
	private JTextField motherNameField;
	private JTextField passportField;
	private JTextField salaryField;
	private JTextField spouseNameField;
	DefaultTableCellRenderer centerRenderer;
	private JTable table;
	private JPanel panel_2;
	private JTextField permanentAddressField;
	private JTextField presentAddressField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeWindow window = new EmployeeWindow();
					window.frame.setVisible(true);
					boolean result=false;
					if(result==true)
					{
						System.out.println("results is right");
					}
					else
					{
						System.out.println("results is not right");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		centerRenderer = new DefaultTableCellRenderer();
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
		frame.setTitle("Employees");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(230, 230, 250));
		panel.setBounds(27, 28, 253, 276);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Photo");
		label.setBounds(0, 0, 253, 276);
		panel.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(new LineBorder(SystemColor.inactiveCaptionText));
		label.setBackground(Color.WHITE);
		
		nameField = new JTextField();
		nameField.setText("name");
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setForeground(Color.GRAY);
		nameField.setFont(new Font("Mistral", Font.PLAIN, 15));
		nameField.setColumns(10);
		nameField.setBounds(300, 28, 499, 30);
		frame.getContentPane().add(nameField);
		
		cellField = new JTextField();
		cellField.setText("cell");
		cellField.setHorizontalAlignment(SwingConstants.CENTER);
		cellField.setForeground(Color.GRAY);
		cellField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cellField.setColumns(10);
		cellField.setBounds(300, 69, 237, 30);
		frame.getContentPane().add(cellField);
		
		emailField = new JTextField();
		emailField.setText("email");
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setForeground(Color.GRAY);
		emailField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		emailField.setColumns(10);
		emailField.setBounds(562, 69, 237, 30);
		frame.getContentPane().add(emailField);
		
		NIDField = new JTextField();
		NIDField.setText("NID");
		NIDField.setHorizontalAlignment(SwingConstants.CENTER);
		NIDField.setForeground(Color.GRAY);
		NIDField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		NIDField.setColumns(10);
		NIDField.setBounds(300, 110, 237, 30);
		frame.getContentPane().add(NIDField);
		
		fatherNameField = new JTextField();
		fatherNameField.setText("Father");
		fatherNameField.setHorizontalAlignment(SwingConstants.CENTER);
		fatherNameField.setForeground(Color.GRAY);
		fatherNameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		fatherNameField.setColumns(10);
		fatherNameField.setBounds(300, 151, 499, 30);
		frame.getContentPane().add(fatherNameField);
		
		designationField = new JTextField();
		designationField.setText("designation");
		designationField.setHorizontalAlignment(SwingConstants.CENTER);
		designationField.setForeground(Color.GRAY);
		designationField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		designationField.setColumns(10);
		designationField.setBounds(300, 356, 237, 30);
		frame.getContentPane().add(designationField);
		
		motherNameField = new JTextField();
		motherNameField.setText("Mother");
		motherNameField.setHorizontalAlignment(SwingConstants.CENTER);
		motherNameField.setForeground(Color.GRAY);
		motherNameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		motherNameField.setColumns(10);
		motherNameField.setBounds(300, 192, 499, 30);
		frame.getContentPane().add(motherNameField);
		
		passportField = new JTextField();
		passportField.setText("passport");
		passportField.setHorizontalAlignment(SwingConstants.CENTER);
		passportField.setForeground(Color.GRAY);
		passportField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		passportField.setColumns(10);
		passportField.setBounds(562, 110, 237, 30);
		frame.getContentPane().add(passportField);
		
		salaryField = new JTextField();
		salaryField.setText("salary per month");
		salaryField.setHorizontalAlignment(SwingConstants.CENTER);
		salaryField.setForeground(Color.GRAY);
		salaryField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		salaryField.setColumns(10);
		salaryField.setBounds(562, 356, 237, 30);
		frame.getContentPane().add(salaryField);
		
		spouseNameField = new JTextField();
		spouseNameField.setText("spouse name");
		spouseNameField.setHorizontalAlignment(SwingConstants.CENTER);
		spouseNameField.setForeground(Color.GRAY);
		spouseNameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		spouseNameField.setColumns(10);
		spouseNameField.setBounds(300, 233, 499, 30);
		frame.getContentPane().add(spouseNameField);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(128, 0, 0));
		separator.setBackground(new Color(128, 0, 0));
		separator.setBounds(828, 28, 2, 457);
		frame.getContentPane().add(separator);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.DARK_GRAY));
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(255, 222, 173));
		panel_1.setBounds(27, 322, 253, 163);
		frame.getContentPane().add(panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 218, 185));
		scrollPane.setBounds(10, 11, 234, 141);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"      Date", " Paid amount"
			}
		));
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			scrollPane.setViewportView(table);
		scrollPane.setViewportView(table);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 51, 102));
		panel_2.setBounds(854, 28, 279, 457);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(255, 255, 51));
		separator_1.setBackground(new Color(255, 255, 102));
		separator_1.setBounds(26, 159, 227, 2);
		panel_2.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(255, 255, 51));
		separator_2.setBackground(new Color(255, 255, 102));
		separator_2.setBounds(26, 297, 227, 2);
		panel_2.add(separator_2);
		
		JLabel label_1 = new JLabel("add");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setForeground(Color.WHITE);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBorder(new LineBorder(Color.WHITE, 2));
		label_1.setBounds(36, 172, 60, 30);
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("delete");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setForeground(Color.WHITE);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBorder(new LineBorder(Color.WHITE, 2));
		label_2.setBounds(106, 172, 60, 30);
		panel_2.add(label_2);
		
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setBounds(36, 38, 207, 26);
		panel_2.add(comboBox);
		
		JLabel label_3 = new JLabel("add");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_3.setBorder(new LineBorder(Color.WHITE, 2));
		label_3.setBounds(176, 172, 60, 30);
		panel_2.add(label_3);
		
		permanentAddressField = new JTextField();
		permanentAddressField.setText("permanent address");
		permanentAddressField.setHorizontalAlignment(SwingConstants.CENTER);
		permanentAddressField.setForeground(Color.GRAY);
		permanentAddressField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		permanentAddressField.setColumns(10);
		permanentAddressField.setBounds(300, 315, 499, 30);
		frame.getContentPane().add(permanentAddressField);
		
		presentAddressField = new JTextField();
		presentAddressField.setText("present address");
		presentAddressField.setHorizontalAlignment(SwingConstants.CENTER);
		presentAddressField.setForeground(Color.GRAY);
		presentAddressField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		presentAddressField.setColumns(10);
		presentAddressField.setBounds(300, 274, 499, 30);
		frame.getContentPane().add(presentAddressField);
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setBounds(100, 100, 1166, 537);
	}
}


