package employees;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import accounts.AccountWindow;
import accounts.Payment;
import connections.sqlConnection;
import login.Animation;
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
	private JTable salaryTable;
	private JPanel panel_2;
	private JTextField permanentAddressField;
	private JTextField presentAddressField;
	private JTextField IDField;
	private final int defaultID=11201901;
	private JLabel imageLabel;
	public FileInputStream stream=null;
	private JTextField amountField;
	private JList<String> list;
	private JTextField quickSearchText;
	private JLabel lblQuickSearch;
	private DefaultListModel<String>  employeeListModel=null;
	private Vector<Employee>employeeList=new Vector<Employee>();
	Thread MrCounter;
	private boolean runMrCounter=true;
	private JLabel timeLabel;
	private JLabel dateField;
	private JLabel label_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeWindow window = new EmployeeWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void set_employeeList()
	{
		try
		{
			Connection temp=sqlConnection.dbConnection();
			ResultSet rs=temp.createStatement().executeQuery("SELECT object FROM employees;");
			employeeListModel.clear();
			while(rs.next())
			{
				byte[] byteStream = (byte[]) rs.getObject(1);
			    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
			    ObjectInputStream ois1 = new ObjectInputStream(baip);
			    Employee dummy=(Employee)ois1.readObject();
			    employeeListModel.addElement(dummy.get_ID()+" "+dummy.get_name());
			    employeeList.addElement(dummy);
			    ois1.close();
			    baip.close();
			}
			rs.close();
			temp.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Create the application.
	 */
	public EmployeeWindow() {
		initialize();
	    set_employeeList();
	    MrCounter.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		employeeListModel = new DefaultListModel<String>();
		centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				runMrCounter=false;
				Menu go=new Menu();
				go.frame.setVisible(true);
			}
		});
		frame.setResizable(false);
		frame.setTitle("Employees");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setBounds(100,100,1192, 662);
		frame.setLocationRelativeTo(frame);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(230, 230, 250));
		panel.setBounds(27, 28, 253, 276);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		imageLabel = new JLabel("Photo");
		imageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to choose a new image?");
				if(dialogResult == JOptionPane.YES_OPTION){
					JFileChooser chooser=new JFileChooser();
					String[] filterKeys={"jpg","gif","png"};
					FileFilter FileFilter=new FileNameExtensionFilter("image.",filterKeys);
					chooser.setFileFilter(FileFilter);
					chooser.showOpenDialog(null);
					File selectedFile=chooser.getSelectedFile();
					Icon image=new ImageIcon(((new ImageIcon(""+selectedFile)).getImage()).getScaledInstance(imageLabel.getWidth(),imageLabel.getHeight(), java.awt.Image.SCALE_SMOOTH));
					imageLabel.setIcon(image);
					try {
						stream=new FileInputStream(selectedFile.getAbsolutePath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				}
			}
		});
		imageLabel.setBounds(0, 0, 260, 276);
		panel.add(imageLabel);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBorder(null);
		imageLabel.setBackground(Color.WHITE);
		
		nameField = new JTextField();
		nameField.setText("name");
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setForeground(Color.GRAY);
		nameField.setFont(new Font("Mistral", Font.PLAIN, 15));
		nameField.setColumns(10);
		nameField.setBounds(300, 162, 499, 30);
		frame.getContentPane().add(nameField);
		
		cellField = new JTextField();
		cellField.setText("cell");
		cellField.setHorizontalAlignment(SwingConstants.CENTER);
		cellField.setForeground(Color.GRAY);
		cellField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cellField.setColumns(10);
		cellField.setBounds(300, 203, 237, 30);
		frame.getContentPane().add(cellField);
		
		emailField = new JTextField();
		emailField.setText("email");
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setForeground(Color.GRAY);
		emailField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		emailField.setColumns(10);
		emailField.setBounds(562, 203, 237, 30);
		frame.getContentPane().add(emailField);
		
		NIDField = new JTextField();
		NIDField.setText("NID");
		NIDField.setHorizontalAlignment(SwingConstants.CENTER);
		NIDField.setForeground(Color.GRAY);
		NIDField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		NIDField.setColumns(10);
		NIDField.setBounds(300, 244, 237, 30);
		frame.getContentPane().add(NIDField);
		
		fatherNameField = new JTextField();
		fatherNameField.setText("Father");
		fatherNameField.setHorizontalAlignment(SwingConstants.CENTER);
		fatherNameField.setForeground(Color.GRAY);
		fatherNameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		fatherNameField.setColumns(10);
		fatherNameField.setBounds(300, 285, 499, 30);
		frame.getContentPane().add(fatherNameField);
		
		designationField = new JTextField();
		designationField.setText("designation");
		designationField.setHorizontalAlignment(SwingConstants.CENTER);
		designationField.setForeground(Color.GRAY);
		designationField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		designationField.setColumns(10);
		designationField.setBounds(300, 490, 237, 30);
		frame.getContentPane().add(designationField);
		
		motherNameField = new JTextField();
		motherNameField.setText("Mother");
		motherNameField.setHorizontalAlignment(SwingConstants.CENTER);
		motherNameField.setForeground(Color.GRAY);
		motherNameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		motherNameField.setColumns(10);
		motherNameField.setBounds(300, 326, 499, 30);
		frame.getContentPane().add(motherNameField);
		
		passportField = new JTextField();
		passportField.setText("passport");
		passportField.setHorizontalAlignment(SwingConstants.CENTER);
		passportField.setForeground(Color.GRAY);
		passportField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		passportField.setColumns(10);
		passportField.setBounds(562, 244, 237, 30);
		frame.getContentPane().add(passportField);
		
		salaryField = new JTextField();
		salaryField.setText("salary per month");
		salaryField.setHorizontalAlignment(SwingConstants.CENTER);
		salaryField.setForeground(Color.GRAY);
		salaryField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		salaryField.setColumns(10);
		salaryField.setBounds(562, 490, 237, 30);
		frame.getContentPane().add(salaryField);
		
		spouseNameField = new JTextField();
		spouseNameField.setText("spouse name");
		spouseNameField.setHorizontalAlignment(SwingConstants.CENTER);
		spouseNameField.setForeground(Color.GRAY);
		spouseNameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		spouseNameField.setColumns(10);
		spouseNameField.setBounds(300, 367, 499, 30);
		frame.getContentPane().add(spouseNameField);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(128, 0, 0));
		separator.setBackground(new Color(128, 0, 0));
		separator.setBounds(828, 28, 2, 543);
		frame.getContentPane().add(separator);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.DARK_GRAY));
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(255, 222, 173));
		panel_1.setBounds(27, 322, 253, 198);
		frame.getContentPane().add(panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 218, 185));
		scrollPane.setBounds(10, 11, 234, 176);
		panel_1.add(scrollPane);
		
		salaryTable = new JTable();
		salaryTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"      Date", " Paid amount"," Paid by"
			}
		));
		salaryTable.getColumnModel().getColumn(0).setResizable(false);
		salaryTable.getColumnModel().getColumn(1).setResizable(false);
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			scrollPane.setViewportView(salaryTable);
		scrollPane.setViewportView(salaryTable);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 51, 102));
		panel_2.setBounds(854, 28, 305, 543);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(255, 255, 51));
		separator_1.setBackground(new Color(255, 255, 102));
		separator_1.setBounds(28, 209, 254, 8);
		panel_2.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(255, 255, 51));
		separator_2.setBackground(new Color(255, 255, 102));
		separator_2.setBounds(28, 317, 254, 2);
		panel_2.add(separator_2);
		
		JLabel lblRemove = new JLabel("remove");
		lblRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(list.getSelectedIndex()!=-1)
				{
					JPanel panel = new JPanel();
					JLabel label = new JLabel("Enter a password:");
					JPasswordField pass = new JPasswordField(10);
					pass.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
					panel.add(pass);
					String[] options = new String[]{"Proceed"};
					int option = JOptionPane.showOptionDialog(null, panel, "Authentication",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null, options,options[0]);
					if(option==0 && checkPassword(new String(pass.getPassword())))
					{
						try
						{
							String toDeleteEmployeeID=list.getSelectedValue().toString().substring(0,9);
							Connection temp=sqlConnection.dbConnection();
							Statement delete=temp.createStatement();
							delete.execute("DELETE FROM employees WHERE employeeID='"+toDeleteEmployeeID+"';");
							ResultSet rs=temp.createStatement().executeQuery("SELECT object FROM employees;");
							employeeList.clear();
							employeeListModel.clear();
							while (rs.next())
							{
								byte[] byteStream = (byte[]) rs.getObject(1);
							    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
							    ObjectInputStream ois1 = new ObjectInputStream(baip);
							    Employee dummy=(Employee)ois1.readObject();
							    employeeList.add(employeeList.size(), dummy);
							    employeeListModel.addElement(dummy.get_ID()+" "+dummy.get_name());
								ois1.close();
								baip.close();
							}
							rs.close();
							delete.close();
							temp.close();
							
						}
						catch(SQLException e)
						{
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		lblRemove.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRemove.setForeground(new Color(250, 128, 114));
		lblRemove.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemove.setBorder(new LineBorder(Color.WHITE, 2));
		lblRemove.setBounds(87, 270, 134, 30);
		panel_2.add(lblRemove);
		
		JLabel lblViewDetails = new JLabel("view details");
		lblViewDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(list.getSelectedIndex()>-1)
				{
					try {
						Connection temp=sqlConnection.dbConnection();
						ResultSet rs=temp.createStatement().executeQuery("SELECT object FROM employees WHERE employeeID='"+list.getSelectedValue().toString().substring(0,9)+"';");
						byte[] byteStream = (byte[]) rs.getObject(1);
					    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
					    ObjectInputStream ois1 = new ObjectInputStream(baip);
					    Employee dummy=(Employee) ois1.readObject();
						nameField.setText(dummy.get_name());
						emailField.setText(dummy.get_email());
						cellField.setText(dummy.get_cellNumber());
						presentAddressField.setText(dummy.get_presentAddress());
						IDField.setText(dummy.get_ID());
						salaryField.setText(Integer.toString(dummy.get_salaryPerMonth()));
						designationField.setText(dummy.get_designation());
						imageLabel.setIcon(dummy.get_picture());
						spouseNameField.setText(dummy.get_spouseName());
						fatherNameField.setText(dummy.get_fatherName());
						motherNameField.setText(dummy.get_motherName());
						permanentAddressField.setText(dummy.get_permanentAddress());
						NIDField.setText(dummy.get_NID());
						DefaultTableModel model = (DefaultTableModel) salaryTable.getModel();
						model.setRowCount(0);
						
						for(int index=0;index<dummy.get_payments().size();index++)
						{
							model.addRow(new Object[]{dummy.get_payments().elementAt(index).get_payment_date(),dummy.get_payments().elementAt(index).get_paid_amount(),dummy.get_payments().elementAt(index).get_paidBy_name()});
						}
						ois1.close();
					    baip.close();
						rs.close();
						temp.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		lblViewDetails.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblViewDetails.setForeground(Color.WHITE);
		lblViewDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewDetails.setBorder(new LineBorder(Color.WHITE, 2));
		lblViewDetails.setBounds(87, 228, 134, 30);
		panel_2.add(lblViewDetails);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.WHITE, 2));
		panel_3.setBounds(28, 342, 254, 173);
		panel_3.setBackground(new Color(0,51,102));
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblPayAmount = new JLabel("pay amount");
		lblPayAmount.setHorizontalAlignment(SwingConstants.LEFT);
		lblPayAmount.setForeground(Color.WHITE);
		lblPayAmount.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPayAmount.setBorder(new LineBorder(new Color(0, 51, 102), 2));
		lblPayAmount.setBounds(89, 17, 81, 20);
		panel_3.add(lblPayAmount);
		
		amountField = new JTextField();
		amountField.setBounds(52, 73, 159, 27);
		panel_3.add(amountField);
		amountField.setColumns(10);
		
		JLabel label = new JLabel("add");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(list.getSelectedIndex()>-1)
				{
					JPanel panel = new JPanel();
					JLabel label = new JLabel("Enter a password:");
					JPasswordField pass = new JPasswordField(10);
					pass.setHorizontalAlignment(SwingConstants.CENTER);
					pass.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent arg0) {
							if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
							{
								try
								{
									Connection temp=sqlConnection.dbConnection();
									PreparedStatement selectStatement=temp.prepareStatement("SELECT object FROM employees WHERE employeeID='"+list.getSelectedValue().toString().substring(0, 9)+"';");
									ResultSet rs=selectStatement.executeQuery();
									byte[] byteStream = (byte[]) rs.getObject(1);
								    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
								    ObjectInputStream ois1 = new ObjectInputStream(baip);
								    Employee dummy=(Employee)ois1.readObject();
								    dummy.add_payment(new Payment(Animation.userName,dateField.getText().toString(),Integer.parseInt(amountField.getText().toString().trim()),IDField.getText().toString()+"  "+nameField.getText().toString(),"official payment"));
								    baip.close();
								    ois1.close();
								    PreparedStatement updateStatement=temp.prepareStatement("UPDATE employees SET employeeID= '"+list.getSelectedValue().toString().substring(0,9)+"',object=?;");
									ByteArrayOutputStream baos = new ByteArrayOutputStream();
								    ObjectOutputStream oos = new ObjectOutputStream(baos);
								    oos.writeObject(dummy);
								    byte[] objectAsBytes = baos.toByteArray();
								    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
								    updateStatement.setBinaryStream(1, bais, objectAsBytes.length);
								    updateStatement.executeUpdate();
								    DefaultTableModel model=(DefaultTableModel) salaryTable.getModel();
								    model.addRow(new String[] {dateField.getText().toString(),amountField.getText().trim().toString(),Animation.userName});
								    baos.close();
								    oos.close();
								    bais.close();
								    
								    updateStatement.close();
								    rs.close();
								    selectStatement.close();
								    temp.close();
								    AccountWindow.add_payment(dateField.getText().toString(), new Payment(Animation.userName,dateField.getText().toString(),Integer.parseInt(amountField.getText().toString().trim()),IDField.getText().toString()+"  "+nameField.getText().toString(),"official payment"));
								}
								catch(SQLException e)
								{
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					});
					panel.add(label);
					panel.add(pass);
					String[] options = new String[]{"Proceed"};
					int option = JOptionPane.showOptionDialog(null, panel, "Authentication",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null, options,options[0]);
					if(option==0 && checkPassword(new String(pass.getPassword())))
					{
						try
						{
							Connection temp=sqlConnection.dbConnection();
							PreparedStatement selectStatement=temp.prepareStatement("SELECT object FROM employees WHERE employeeID='"+list.getSelectedValue().toString().substring(0, 9)+"';");
							ResultSet rs=selectStatement.executeQuery();
							byte[] byteStream = (byte[]) rs.getObject(1);
						    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
						    ObjectInputStream ois1 = new ObjectInputStream(baip);
						    Employee dummy=(Employee)ois1.readObject();
						    dummy.add_payment(new Payment(Animation.userName,dateField.getText().toString(),Integer.parseInt(amountField.getText().toString().trim()),IDField.getText().toString()+"  "+nameField.getText().toString(),"official payment"));
						    baip.close();
						    ois1.close();
						    PreparedStatement updateStatement=temp.prepareStatement("UPDATE employees SET employeeID= '"+list.getSelectedValue().toString().substring(0,9)+"',object=?;");
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
						    ObjectOutputStream oos = new ObjectOutputStream(baos);
						    oos.writeObject(dummy);
						    byte[] objectAsBytes = baos.toByteArray();
						    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
						    updateStatement.setBinaryStream(1, bais, objectAsBytes.length);
						    updateStatement.executeUpdate();
						    DefaultTableModel model=(DefaultTableModel) salaryTable.getModel();
						    model.addRow(new String[] {dateField.getText().toString(),amountField.getText().trim().toString(),Animation.userName});
						    baos.close();
						    oos.close();
						    bais.close();
						    
						    updateStatement.close();
						    rs.close();
						    selectStatement.close();
						    temp.close();
						    AccountWindow.add_payment(dateField.getText().toString(), new Payment(Animation.userName,dateField.getText().toString(),Integer.parseInt(amountField.getText().toString().trim()),IDField.getText().toString()+"  "+nameField.getText().toString(),"official payment"));
						}
						catch(SQLException e)
						{
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}
		});
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBorder(new LineBorder(Color.WHITE, 2));
		label.setBounds(99, 123, 60, 30);
		panel_3.add(label);
		
		dateField = new JLabel("New label");
		dateField.setHorizontalAlignment(SwingConstants.CENTER);
		dateField.setForeground(new Color(255, 255, 255));
		dateField.setBounds(68, 48, 65, 14);
		panel_3.add(dateField);
		
		timeLabel = new JLabel("New label");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setForeground(new Color(255, 255, 255));
		timeLabel.setBounds(129, 48, 70, 14);
		panel_3.add(timeLabel);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(36, 75, 235, 123);
		panel_2.add(panel_4);
		panel_4.setBackground(new Color(0,51,102));
		panel_4.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Employees",TitledBorder.CENTER,TitledBorder.TOP,new Font("times new roman",Font.PLAIN,17), Color.WHITE));
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(10, 21, 225, 91);
		panel_4.add(scrollPane_1);
		scrollPane_1.setBackground(new Color(0,51,102));
		
		list = new JList<String>(employeeListModel);
		list.setForeground(Color.WHITE);
		list.setBackground(new Color(0,51,102));
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane_1.setViewportView(list);
		//scrollPane_1.setForeground(Color.WHITE);
		//scrollPane_1.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Employees",TitledBorder.CENTER,TitledBorder.TOP,new Font("times new roman",Font.PLAIN,15), Color.WHITE));
		
		quickSearchText = new JTextField();
		quickSearchText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		quickSearchText.setHorizontalAlignment(SwingConstants.CENTER);
		quickSearchText.setBounds(39, 44, 134, 20);
		panel_2.add(quickSearchText);
		quickSearchText.setColumns(10);
		
		lblQuickSearch = new JLabel("Quick search");
		lblQuickSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try
				{
					String foundEmployee=new String();
					Connection temp=sqlConnection.dbConnection();
					ResultSet rs=temp.createStatement().executeQuery("SELECT object FROM employees;");
					while(rs.next())
					{
						byte[] byteStream = (byte[]) rs.getObject(1);
					    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
					    ObjectInputStream ois1 = new ObjectInputStream(baip);
					    Employee dummy=(Employee) ois1.readObject();
						if(quickSearchText.getText().trim().toString().equals(dummy.get_NID())||quickSearchText.getText().trim().toString().equals(dummy.get_ID())||quickSearchText.getText().trim().toString().equals(dummy.get_name())||quickSearchText.getText().trim().toString().equals(dummy.get_passportNo()))
						{
							foundEmployee=dummy.get_ID()+" "+dummy.get_name();
							ois1.close();
							baip.close();
							break;
						}
						ois1.close();
						baip.close();
					}
					rs.close();
					temp.close();
					for(int index=0;index<employeeListModel.size();index++)
					{
						if(employeeListModel.getElementAt(index).equalsIgnoreCase(foundEmployee))
						{
							list.setSelectedIndex(index);
						}
					}
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		lblQuickSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuickSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuickSearch.setForeground(new Color(255, 255, 255));
		lblQuickSearch.setBorder(new LineBorder(new Color(255, 255, 255)));
		lblQuickSearch.setBounds(176, 44, 95, 20);
		panel_2.add(lblQuickSearch);
		
		permanentAddressField = new JTextField();
		permanentAddressField.setText("permanent address");
		permanentAddressField.setHorizontalAlignment(SwingConstants.CENTER);
		permanentAddressField.setForeground(Color.GRAY);
		permanentAddressField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		permanentAddressField.setColumns(10);
		permanentAddressField.setBounds(300, 449, 499, 30);
		frame.getContentPane().add(permanentAddressField);
		
		presentAddressField = new JTextField();
		presentAddressField.setText("present address");
		presentAddressField.setHorizontalAlignment(SwingConstants.CENTER);
		presentAddressField.setForeground(Color.GRAY);
		presentAddressField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		presentAddressField.setColumns(10);
		presentAddressField.setBounds(300, 408, 499, 30);
		frame.getContentPane().add(presentAddressField);
		
		IDField = new JTextField();
		IDField.setFont(new Font("Tahoma", Font.BOLD, 17));
		IDField.setHorizontalAlignment(SwingConstants.CENTER);
		IDField.setBounds(432, 97, 223, 38);
		frame.getContentPane().add(IDField);
		IDField.setColumns(10);
		
		JLabel lblAutoGenerate = new JLabel("Auto generate");
		lblAutoGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try
				{
					Connection conn=sqlConnection.dbConnection();
					ResultSet rs=conn.createStatement().executeQuery("SELECT employeeID FROM employees;");
					int index=0;
					while(rs.next())
					{
						index++;
					}
					rs.close();
					conn.close();
					IDField.setText(Integer.toString(defaultID)+Integer.toString(index));
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		});
		lblAutoGenerate.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutoGenerate.setForeground(Color.DARK_GRAY);
		lblAutoGenerate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAutoGenerate.setBorder(new LineBorder(Color.WHITE, 2));
		lblAutoGenerate.setBounds(665, 96, 134, 38);
		frame.getContentPane().add(lblAutoGenerate);
		
		JLabel lblId = new JLabel("ID");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setForeground(Color.DARK_GRAY);
		lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId.setBorder(new LineBorder(Color.WHITE, 2));
		lblId.setBounds(300, 97, 103, 37);
		frame.getContentPane().add(lblId);
		
		JLabel lblEmployees = new JLabel("Employees");
		lblEmployees.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmployees.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmployees.setBounds(343, 28, 433, 38);
		frame.getContentPane().add(lblEmployees);
		
		JLabel lblAddupdate = new JLabel("add/update");
		lblAddupdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Enter a password:");
				JPasswordField pass = new JPasswordField(10);
				pass.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(label);
				panel.add(pass);
				String[] options = new String[]{"Proceed"};
				int option = JOptionPane.showOptionDialog(null, panel, "Authentication",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
				if(option == 0) // pressing OK button
				{
				    if(checkPassword(new String(pass.getPassword())) && !check_availibility(NIDField.getText().trim().toString(),IDField.getText().trim().toString(),cellField.getText().trim().toString(),emailField.getText().trim().toString(),passportField.getText().trim().toString()))
				    {
				    	if(NIDField.getText().trim().toString().length()==11 || NIDField.getText().trim().toString().length()==17)
						{
							char[] NIDInArray=NIDField.getText().trim().toString().toCharArray();
							boolean NIDCheckResult=true;
							for(int index=0;index<NIDInArray.length;index++)
							{
								if((int)NIDInArray[index]<48 || (int)NIDInArray[index]>57)
								{
									NIDCheckResult=false;
									System.out.println("Wrong input");
									break;
								}
							}
							if(NIDCheckResult==true && cellField.getText().trim().toString().length()==11)
							{
								char[] CellInArray=NIDField.getText().trim().toString().toCharArray();
								boolean cellCheckresult=true;
								for(int index=0;index<CellInArray.length;index++)
								{
									if((int)CellInArray[index]<48 || (int)CellInArray[index]>57)
									{
										cellCheckresult=false;
										System.out.println("Wrong input");
										break;
									}
								}
								if(cellCheckresult &&!nameField.getText().equalsIgnoreCase("name")&&!nameField.getText().trim().equals("") && !nameField.getText().trim().toString().equals(null) && !emailField.getText().trim().toString().equals("email") && !emailField.getText().trim().toString().equals(null) && !permanentAddressField.getText().trim().toString().equals(null)&& !permanentAddressField.getText().trim().toString().equalsIgnoreCase("permanent Address"))
								{
									try {
										Connection temp=sqlConnection.dbConnection();
										PreparedStatement pstmt=temp.prepareStatement("INSERT INTO employees(employeeID,object) VALUES (?,?);");
										pstmt.setString(1,IDField.getText().trim().toString());
										
										Employee newEmployee=new Employee(IDField.getText().trim().toString(),imageLabel.getIcon(),nameField.getText().trim().toString(),cellField.getText().trim().toString(),emailField.getText().trim().toString(),NIDField.getText().trim().toString(),passportField.getText().trim().toString(),designationField.getText().trim().toString(),fatherNameField.getText().trim().toString(),motherNameField.getText().trim().toString(),spouseNameField.getText().trim().toString(),presentAddressField.getText().trim().toString(),permanentAddressField.getText().trim().toString(),Integer.parseInt(salaryField.getText().trim().toString()),"");
										ByteArrayOutputStream baos = new ByteArrayOutputStream();
									    ObjectOutputStream oos = new ObjectOutputStream(baos);
									    oos.writeObject(newEmployee);
									    byte[] objectAsBytes = baos.toByteArray();
									    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
									    pstmt.setBinaryStream(2, bais, objectAsBytes.length);
									    
									    pstmt.executeUpdate();
									    pstmt.close();
									    temp.close();
									    employeeListModel.addElement(newEmployee.get_ID()+" "+newEmployee.get_name());
									    list.clearSelection();
									    JOptionPane.showMessageDialog(frame, "New employee has been registered");
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								else
								{
									JOptionPane.showMessageDialog(frame, "Wrong Cell number entry!");
								}
							}
							else
							{
								JOptionPane.showMessageDialog(frame, "Wrong NID entry!");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(frame, "NID must be 11 or 17 digits");
						}
				    }
				}
				else
				{
					System.out.println("cancel pressed");
				}
			}
		});
		lblAddupdate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAddupdate.setForeground(new Color(0, 0, 102));
		lblAddupdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAddupdate.setBorder(new LineBorder(Color.WHITE, 2));
		lblAddupdate.setBounds(432, 554, 105, 30);
		frame.getContentPane().add(lblAddupdate);
		
		JLabel lblClear = new JLabel("clear");
		lblClear.setHorizontalAlignment(SwingConstants.LEFT);
		lblClear.setForeground(new Color(0, 0, 102));
		lblClear.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblClear.setBorder(new LineBorder(Color.WHITE, 2));
		lblClear.setBounds(562, 554, 105, 30);
		frame.getContentPane().add(lblClear);
		
		label_1 = new JLabel("\u00A9LAB Symbiotic");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		label_1.setBounds(940, 582, 148, 14);
		frame.getContentPane().add(label_1);
		
		
		
		MrCounter=new Thread()
		{
			@Override
			public void run()
			{
				
				while(runMrCounter)
				{
					dateField.setText(new SimpleDateFormat("DD-MM-YYYY").format(new Date()));
					timeLabel.setText(new SimpleDateFormat("hh:mm:ss aa").format(new Date()));
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	public boolean checkPassword(String givenPassword)
	{
		boolean result=false;
		try
		{
			Connection temp=sqlConnection.dbConnection();
			ResultSet rs=temp.createStatement().executeQuery("SELECT * FROM users;");
			
			while(rs.next())
			{
				if(Animation.userName.equalsIgnoreCase(rs.getString("name"))&&rs.getString("password").equals(givenPassword))
				{
					result=true;
				}
			}
			rs.close();
			temp.close();
			return result;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return result;
		}
	}
	public boolean check_availibility(String NID, String ID, String cell,String email,String passport)
	{
		for(int index=0;index<employeeList.size();index++)
		{
			if(employeeList.elementAt(index).get_NID().equals(NID)||employeeList.elementAt(index).get_email().equals(email)||employeeList.elementAt(index).get_cellNumber().equals(cell)||employeeList.elementAt(index).get_ID().equals(ID)||employeeList.elementAt(index).get_passportNo().equals(passport))
			{
				return true;
			}
		}
		return false;
	}
}

