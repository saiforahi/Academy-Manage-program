package registration;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import accounts.AccountWindow;
import accounts.Collection;
import connections.sqlConnection;
import login.Animation;
import menu.Menu;
import javax.swing.border.MatteBorder;

public class Registration  {

	public JFrame frame;
	private JTextField nameField;
	private JTextField cellField;
	private JTextField emailField;
	private JTextField addressField;
	private JTextField programField;
	private JPanel panel_3;
	private JLabel imageLabel;
	private JTable table;
	private JList<String> list;
	private DefaultListModel<String>  learnerNameList=null;
	DefaultTableCellRenderer centerRenderer;
	private JTextField skimField;
	private JTextField downPaymentField;
	private JLabel lblNewLabel_2;
	private Vector<Learner> learnerList=new Vector<Learner>();
	private Vector<String> collectionList=new Vector<String>();
	FileInputStream stream=null;  
	private JTextField textField;
	private JTextField searchField;
	private JLabel searchButton;
	private JComboBox<String> comboBox;
	private JScrollPane scrollPane;
	private JPanel panel_1;
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
		set_learnerList();
		initialize();
		sqlConnection.setPicture(searchButton, "search_icon");
	}
	
	public void set_learnerList() 
	{
		
		try {
			learnerNameList = new DefaultListModel<String>();
			Connection temp=sqlConnection.dbConnection();
			ResultSet rs=temp.createStatement().executeQuery("SELECT objects FROM learners;");
			learnerList.clear();
			while(rs.next())
			{
				byte[] byteStream = (byte[]) rs.getObject(1);
			    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
			    ObjectInputStream ois1 = new ObjectInputStream(baip);
			    Learner dummy=(Learner)ois1.readObject();
				learnerList.add(learnerList.size(), dummy);
				learnerNameList.addElement(dummy.get_email());
			}
			rs.close();
			ResultSet rs1=temp.createStatement().executeQuery("SELECT collectionNo FROM collections;");
			while(rs.next())
			{
				collectionList.add(rs.getString(1));
			}
			rs1.close();
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
	
	public void update_learner(String emailAddress,int amount)
	{
		try {
			Connection temp=sqlConnection.dbConnection();
			PreparedStatement pstmt = temp.prepareStatement("SELECT objects FROM learners WHERE email='"+emailAddress+"';");
			ResultSet rs=pstmt.executeQuery();
			byte[] byteStream = (byte[]) rs.getObject(1);
		    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
		    ObjectInputStream ois1 = new ObjectInputStream(baip);
		    Learner dummy=(Learner)ois1.readObject();
		    ois1.close();
		    baip.close();
			rs.close();
			pstmt.close();
			temp.close();
			delete_learner(dummy);
			String date=ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME).toString();
			dummy.add_installment(new Installment(date,amount));
			dummy.set_due(dummy.get_due()-amount);
			add_learner(dummy);
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
	
	public void add_learner(Learner newLearner) throws SQLException, IOException
	{
		Connection conn=sqlConnection.dbConnection();
		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO learners(email, name,objects) VALUES (?,?,?)");
		pstmt.setString(1, emailField.getText());
		pstmt.setString(2,nameField.getText());
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(baos);
	    oos.writeObject(newLearner);
	    byte[] objectAsBytes = baos.toByteArray();
	    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
	    pstmt.setBinaryStream(3, bais, objectAsBytes.length);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		learnerList.add(learnerList.size(),newLearner);
		learnerNameList.addElement(newLearner.get_email());
		
	}
	public void delete_learner(Learner toDeleteLearner) throws SQLException
	{
		Connection conn=sqlConnection.dbConnection();
		Statement delete=conn.createStatement();
		delete.execute("DELETE FROM Learners WHERE email='"+toDeleteLearner.get_email()+"';");
		delete.close();
		conn.close();
		learnerNameList.removeElement(toDeleteLearner.get_email());
		learnerList.remove(toDeleteLearner);
	}
	public String generate_collectionNo()
	{
		String date=new SimpleDateFormat("DD").format(new Date())+new SimpleDateFormat("MM").format(new Date())+new SimpleDateFormat("YYYY").format(new Date());
		int collectionSerialNo=1;
		for(int index=0;index<collectionList.size();index++)
		{
			if(date.equals(collectionList.elementAt(index).substring(0,8)))
			{
				collectionSerialNo++;
			}
		}
		return date+collectionSerialNo;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		centerRenderer = new DefaultTableCellRenderer();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setSize(new Dimension(screenSize.width, screenSize.height));
		frame.getContentPane().setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		frame.setBackground(Color.BLACK);
		frame.setLocationRelativeTo(null);
		frame.setLocation(0,0);
		frame.getContentPane().setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(248, 248, 255), 2));
		panel.setBackground(new Color(0, 0, 0,0));
		panel.setOpaque(false);
		panel.setBounds(878, 21, 467, 716);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		comboBox = new JComboBox();
		comboBox.setBackground(new Color(0, 0, 0,0));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Email", "Name"}));
		comboBox.setBounds(314, 100, 60, 30);
		panel.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Report");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(22, 542, 48, 20);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html><p align='CENTER'>Null</p></center></html>");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBackground(new Color(255, 255, 255));
		
		lblNewLabel_1.setBounds(22, 573, 422, 120);
		lblNewLabel_1.setBorder(new LineBorder(new Color(255, 255, 255)));
		panel.add(lblNewLabel_1);
		
		JLabel lblUpdate = new JLabel("update");
		lblUpdate.setForeground(Color.WHITE);
		lblUpdate.setBounds(314, 210, 60, 30);
		panel.add(lblUpdate);
		lblUpdate.setBackground(Color.BLACK);
		lblUpdate.setFont(new Font("Consolas", Font.BOLD, 14));
		lblUpdate.setOpaque(true);
		lblUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		lblUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdate.setBorder(new LineBorder(new Color(102, 205, 170), 2));
		
		JLabel label_2 = new JLabel("delete");
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(384, 210, 60, 30);
		panel.add(label_2);
		label_2.setBackground(Color.BLACK);
		label_2.setFont(new Font("Consolas", Font.BOLD, 14));
		label_2.setOpaque(true);
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(list.getSelectedIndex()!=-1)
				{
					int dialogResult = JOptionPane.showConfirmDialog (frame, "Do you want to completely delete this learner's information?");
					if(dialogResult == JOptionPane.YES_OPTION){
						try {
							String toDeleteLearnerEmail=list.getSelectedValue().toString();
							Connection temp=sqlConnection.dbConnection();
							Statement delete=temp.createStatement();
							delete.execute("DELETE FROM learners WHERE email='"+toDeleteLearnerEmail+"';");
							delete.close();
							temp.close();
							
							Connection temp2=sqlConnection.dbConnection();
							PreparedStatement pstmt = temp2.prepareStatement("SELECT objects FROM learners;");
							ResultSet rs=pstmt.executeQuery();
							learnerList.clear();
							learnerNameList.clear();
							while(rs.next())
							{
								byte[] byteStream = (byte[]) rs.getObject(1);
							    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
							    ObjectInputStream ois1 = new ObjectInputStream(baip);
							    Learner dummy=(Learner)ois1.readObject();
								learnerList.add(learnerList.size(), dummy);
								learnerNameList.addElement(dummy.get_email());
								ois1.close();
								baip.close();
							}
							
							rs.close();
							pstmt.close();
							temp2.close();
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
				else
					JOptionPane.showMessageDialog(frame, "No learner has been selected");
			}
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBorder(new LineBorder(new Color(139, 0, 0), 2));
		
		JLabel lblViewDetails = new JLabel("view details");
		lblViewDetails.setForeground(new Color(255, 255, 255));
		lblViewDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(list.getSelectedIndex()>-1)
				{
					try {
						Connection temp=sqlConnection.dbConnection();
						PreparedStatement pstmt = temp.prepareStatement("SELECT objects FROM learners WHERE email=?;");
						pstmt.setString(1,list.getSelectedValue().toString());
						ResultSet rs=pstmt.executeQuery();
						byte[] byteStream = (byte[]) rs.getObject(1);
					    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
					    ObjectInputStream ois1 = new ObjectInputStream(baip);
					    Learner dummy=(Learner) ois1.readObject();
					    ois1.close();
					    baip.close();
						rs.close();
						pstmt.close();
						temp.close();
						nameField.setText(dummy.get_name());
						emailField.setText(dummy.get_email());
						cellField.setText(dummy.get_cell());
						addressField.setText(dummy.get_address());
						programField.setText(dummy.get_courseName());
						skimField.setText(Integer.toString(dummy.get_skimTotal()));
						downPaymentField.setText(Integer.toString(dummy.get_downPayment()));
						imageLabel.setIcon(dummy.get_picture());
						imageLabel.setText(null);
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.setRowCount(0);
						int due=dummy.get_skimTotal();
						for(int index=0;index<dummy.get_Installments().size();index++)
						{
							due=due-dummy.get_Installments().elementAt(index).get_amount();
							model.addRow(new Object[]{dummy.get_Installments().elementAt(index).get_date(),dummy.get_Installments().elementAt(index).get_amount(),Integer.toString(due)});
						}
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
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		lblViewDetails.setOpaque(true);
		lblViewDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewDetails.setFont(new Font("Consolas", Font.BOLD, 14));
		lblViewDetails.setBorder(new LineBorder(new Color(255, 255, 0), 2));
		lblViewDetails.setBackground(new Color(0, 0, 0));
		lblViewDetails.setBounds(314, 173, 129, 26);
		panel.add(lblViewDetails);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(22, 141, 282, 121);
		scrollPane_1.setBackground(new Color(0, 0, 0));
		scrollPane_1.setOpaque(true);
		scrollPane_1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(scrollPane_1);
		
		list = new JList(learnerNameList);
		list.setForeground(Color.WHITE);
		list.setBorder(new MatteBorder(10, 10, 0, 10, (Color) new Color(0, 0, 0)));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
				{
					if(list.getSelectedIndex()>-1)
					{
						try {
							Connection temp=sqlConnection.dbConnection();
							PreparedStatement pstmt = temp.prepareStatement("SELECT objects FROM learners WHERE email=?;");
							pstmt.setString(1,list.getSelectedValue().toString());
							ResultSet rs=pstmt.executeQuery();
							byte[] byteStream = (byte[]) rs.getObject(1);
						    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
						    ObjectInputStream ois1 = new ObjectInputStream(baip);
						    Learner dummy=(Learner) ois1.readObject();
						    ois1.close();
						    baip.close();
							rs.close();
							pstmt.close();
							temp.close();
							nameField.setText(dummy.get_name());
							emailField.setText(dummy.get_email());
							emailField.setEditable(false);
							cellField.setText(dummy.get_cell());
							addressField.setText(dummy.get_address());
							programField.setText(dummy.get_courseName());
							skimField.setText(Integer.toString(dummy.get_skimTotal()));
							downPaymentField.setText(Integer.toString(dummy.get_downPayment()));
							imageLabel.setIcon(dummy.get_picture());
							imageLabel.setText(null);
							DefaultTableModel model = (DefaultTableModel) table.getModel();
							model.setRowCount(0);
							int due=dummy.get_skimTotal();
							for(int index=0;index<dummy.get_Installments().size();index++)
							{
								due=due-dummy.get_Installments().elementAt(index).get_amount();
								model.addRow(new Object[]{dummy.get_Installments().elementAt(index).get_date(),dummy.get_Installments().elementAt(index).get_amount(),Integer.toString(due)});
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (ClassNotFoundException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.setBackground(Color.BLACK);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(list);
		
		JLabel lblLearners = new JLabel("Learners");
		lblLearners.setForeground(new Color(255, 255, 255));
		lblLearners.setBackground(new Color(0, 0, 0));
		lblLearners.setOpaque(true);
		lblLearners.setFont(new Font("Consolas", Font.BOLD, 16));
		lblLearners.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblLearners);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 255, 255));
		panel_5.setBorder(new LineBorder(new Color(128, 0, 0), 2));
		panel_5.setBounds(22, 469, 422, 62);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblAddPayment = new JLabel("Add payment");
		lblAddPayment.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAddPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddPayment.setBounds(10, 11, 106, 38);
		panel_5.add(lblAddPayment);
		
		textField = new JTextField();
		textField.setBackground(new Color(255, 255, 255));
		textField.setBorder(new LineBorder(new Color(128, 0, 0), 2));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(121, 11, 178, 40);
		panel_5.add(textField);
		textField.setColumns(10);
		
		JLabel payButton = new JLabel("Pay!");
		payButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(list.getSelectedIndex()>=-1)
				{
					JPanel panel = new JPanel();
					JLabel label = new JLabel("Enter a password:");
					JPasswordField pass = new JPasswordField(10);
					pass.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
					panel.add(pass);
					String[] options = new String[]{"Proceed"};
					int option = JOptionPane.showOptionDialog(null, panel, "Authentication",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
					if(option == 0 && checkPassword(new String(pass.getPassword()))) // pressing OK button
					{
						for(int index=0;index<learnerList.size();index++)
						{
							if (list.getSelectedValue().toString().equals(learnerList.elementAt(index).get_email()) && nameField.getText().toString().equals(learnerList.elementAt(index).get_name()) && cellField.getText().toString().equals(learnerList.elementAt(index).get_cell()) && programField.getText().toString().equals(learnerList.elementAt(index).get_courseName()))
							{
								update_learner(emailField.getText().toString(),Integer.parseInt(textField.getText().toString().trim()));
								DefaultTableModel model = (DefaultTableModel) table.getModel();
								if(model.getRowCount()<=0)
								{
									model.addRow(new Object[]{ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME).toString(),textField.getText(),Integer.toString(Integer.parseInt(skimField.getText().trim().toString())-Integer.parseInt(textField.getText().trim().toString()))});
								}
								else
									model.addRow(new Object[]{ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME).toString(),textField.getText(),Integer.toString(Integer.parseInt(table.getValueAt(table.getRowCount()-1, 2).toString())-Integer.parseInt(textField.getText().trim().toString()))});
								AccountWindow.add_collection(new Collection(generate_collectionNo(),Animation.userName,new SimpleDateFormat("DD-MM-YYYY").format(new Date()),new SimpleDateFormat("hh:mm:ss aa").format(new Date()),"Payment from "+nameField.getText().trim().toString(),Integer.parseInt(textField.getText().toString().trim())));
								collectionList.add(generate_collectionNo());
								textField.setText(null);
								JOptionPane.showMessageDialog(frame, "Learner's information updated!");
								break;
							}
						}
						
					}
					else
						JOptionPane.showMessageDialog(frame, "Wrong password entry");
					
				}
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		payButton.setFont(new Font("Estrangelo Edessa", Font.BOLD, 18));
		payButton.setHorizontalAlignment(SwingConstants.CENTER);
		payButton.setBorder(new LineBorder(new Color(139, 0, 0), 2));
		payButton.setBounds(309, 15, 93, 31);
		panel_5.add(payButton);
		
		searchField = new JTextField();
		searchField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		searchField.setHorizontalAlignment(SwingConstants.CENTER);
		searchField.setBounds(22, 100, 282, 30);
		panel.add(searchField);
		searchField.setColumns(10);
		
		searchButton = new JLabel("New label");
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!searchField.getText().equals(null)&& !searchField.getText().equals(""))
				{
					if(comboBox.getSelectedItem().equals("Email"))
					{
						for(int index=0;index<learnerNameList.size();index++)
						{
							if(learnerNameList.getElementAt(index).toString().equalsIgnoreCase(searchField.getText()))
							{
								list.setSelectedIndex(index);
							}
						}
					}
					else if(comboBox.getSelectedItem().equals("Name"))
					{
						for(int index=0;index<learnerList.size();index++)
						{
							if(learnerList.elementAt(index).get_name().equalsIgnoreCase(searchField.getText()))
							{
								list.setSelectedIndex(index);
							}
						}
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		searchButton.setBounds(384, 101, 62, 29);
		searchButton.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(searchButton);
		
		JLabel lblInvetory = new JLabel("CILL's Inventory");
		lblInvetory.setForeground(new Color(255, 255, 255));
		lblInvetory.setFont(new Font("Consolas", Font.BOLD, 26));
		lblInvetory.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvetory.setBounds(99, 21, 275, 62);
		panel.add(lblInvetory);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Installments",TitledBorder.CENTER,TitledBorder.TOP,new Font("times new roman",Font.PLAIN,15), Color.black));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(22, 273, 422, 160);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(255, 255, 255));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"      Date", " Installment", "    Due"
			}
		) {
			/**
			 * 
			 */
			public static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		scrollPane.setViewportView(table);
		
		
		panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				panel_1.requestFocus();
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		panel_1.setBorder(new LineBorder(SystemColor.windowBorder, 1, true));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setLayout(null);
		panel_1.setBounds(21, 21, 843, 529);
		frame.getContentPane().add(panel_1);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(244, 164, 96));
		panel_3.setBounds(68, 104, 179, 194);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
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
					imageLabel.setText(null);
					try {
						stream=new FileInputStream(selectedFile.getAbsolutePath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		imageLabel.setBounds(0, 0, 187, 194);
		panel_3.add(imageLabel);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBorder(null);
		imageLabel.setBackground(Color.WHITE);
		
		nameField = new JTextField();
		nameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(nameField.getText().trim().toString().equals("name"))
				{
					nameField.setText(null);
					nameField.requestFocus();
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(nameField.getText().trim().toString().equals("name") ||  nameField.getText().trim().toString().equals("")||nameField.getText().trim().toString().equals(null))
				{
					nameField.setText("name");
				}
			}
		});
		nameField.setText("name");
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setForeground(Color.GRAY);
		nameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameField.setColumns(10);
		nameField.setBounds(283, 104, 499, 30);
		panel_1.add(nameField);
		
		cellField = new JTextField();
		cellField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(cellField.getText().trim().toString().equalsIgnoreCase("cell"))
				{
					cellField.setText(null);
					cellField.requestFocus();
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(cellField.getText().trim().toString().equals("cell") ||  cellField.getText().trim().toString().equals("")||cellField.getText().trim().toString().equals(null))
				{
					cellField.setText("cell");
				}
			}
		});
		cellField.setText("cell");
		cellField.setHorizontalAlignment(SwingConstants.CENTER);
		cellField.setForeground(Color.GRAY);
		cellField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cellField.setColumns(10);
		cellField.setBounds(283, 145, 237, 30);
		panel_1.add(cellField);
		
		emailField = new JTextField();
		emailField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(emailField.getText().trim().toString().equalsIgnoreCase("email"))
				{
					emailField.setText(null);
					emailField.requestFocus();;
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(emailField.getText().trim().toString().equalsIgnoreCase("email") ||  emailField.getText().trim().toString().equals("")||emailField.getText().trim().toString().equals(null))
				{
					emailField.setText("email");
					
				}
			}
		});
		emailField.setText("email");
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setForeground(Color.GRAY);
		emailField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		emailField.setColumns(10);
		emailField.setBounds(545, 145, 237, 30);
		panel_1.add(emailField);
		
		addressField = new JTextField();
		addressField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(addressField.getText().trim().toString().equalsIgnoreCase("address"))
				{
					addressField.setText(null);
					addressField.requestFocus();;
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(addressField.getText().trim().toString().equalsIgnoreCase("address") ||  addressField.getText().trim().toString().equals("")||addressField.getText().trim().toString().equals(null))
				{
					addressField.setText("address");
					
				}
			}
		});
		addressField.setText("address");
		addressField.setHorizontalAlignment(SwingConstants.CENTER);
		addressField.setForeground(Color.GRAY);
		addressField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		addressField.setColumns(10);
		addressField.setBounds(283, 186, 499, 30);
		panel_1.add(addressField);
		
		programField = new JTextField();
		programField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(programField.getText().trim().toString().equalsIgnoreCase("program details"))
				{
					programField.setText(null);
					programField.requestFocus();;
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(programField.getText().trim().toString().equalsIgnoreCase("program details") ||  programField.getText().trim().toString().equals("")||programField.getText().trim().toString().equals(null))
				{
					programField.setText("program details");
					
				}
			}
		});
		programField.setText("program details");
		programField.setHorizontalAlignment(SwingConstants.CENTER);
		programField.setForeground(Color.GRAY);
		programField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		programField.setColumns(10);
		programField.setBounds(283, 227, 499, 30);
		panel_1.add(programField);
		
		JLabel lblAdd = new JLabel("add/update");
		lblAdd.setBackground(new Color(224, 255, 255));
		lblAdd.setFont(new Font("Consolas", Font.PLAIN, 15));
		lblAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(nameField.getText()!=null && cellField.getText()!=null && emailField.getText()!=null && addressField.getText()!=null && programField.getText()!=null && skimField.getText()!=null && downPaymentField.getText()!=null && imageLabel.getIcon()!=null)
				{
					
					try {
						boolean found=false;
						int index=0;
						for(;index<learnerList.size();index++)
						{
							if(learnerList.get(index).get_email().equals(emailField.getText()))
							{
								found=true;
								break;
							}
						}
						if(found==false)
						{
							JPanel panel = new JPanel();
							JLabel label = new JLabel("Enter password to add new learner:");
							JPasswordField pass = new JPasswordField(10);
							pass.setHorizontalAlignment(SwingConstants.CENTER);
							panel.add(label);
							panel.add(pass);
							String[] options = new String[]{"Proceed"};
							int dialogResult = JOptionPane.showOptionDialog(null, panel, "Authentication",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
							if(dialogResult ==0 && checkPassword(new String(pass.getPassword()))){
								Learner newLearner=new Learner(imageLabel.getIcon(),nameField.getText(),cellField.getText(),emailField.getText(),addressField.getText(),programField.getText(),Integer.parseInt(skimField.getText()),Integer.parseInt(downPaymentField.getText()),null);
								Connection conn=sqlConnection.dbConnection();
								PreparedStatement pstmt = conn.prepareStatement("INSERT INTO learners(email, name,objects) VALUES (?,?,?);");
								pstmt.setString(1, emailField.getText());
								pstmt.setString(2,nameField.getText());
								
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
							    ObjectOutputStream oos = new ObjectOutputStream(baos);
							    oos.writeObject(newLearner);
							    byte[] objectAsBytes = baos.toByteArray();
							    
							    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
							    pstmt.setBinaryStream(3, bais, objectAsBytes.length);
							    
								//pstmt.setBinaryStream(4, stream,stream.available());
								pstmt.executeUpdate();
								pstmt.close();
								conn.close();
								learnerList.add(learnerList.size(),newLearner);
								learnerNameList.addElement(newLearner.get_email());
								JOptionPane.showMessageDialog(frame, "Learner's information saved!");
							}
							
						}
						else
						{
							JOptionPane.showMessageDialog(frame, "An entry already exists with this email, do you want to update it with new information?..");
							JPanel panel = new JPanel();
							JLabel label = new JLabel("Enter password to update new learner:");
							JPasswordField pass = new JPasswordField(10);
							pass.setHorizontalAlignment(SwingConstants.CENTER);
							panel.add(label);
							panel.add(pass);
							String[] options = new String[]{"Proceed"};
							int dialogResult = JOptionPane.showOptionDialog(null, panel, "Authentication",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
							if(dialogResult ==0 && checkPassword(new String(pass.getPassword()))){
								learnerList.elementAt(index).set_name(nameField.getText().trim().toString());
								learnerList.elementAt(index).set_cell(cellField.getText().trim().toString());
								learnerList.elementAt(index).set_address(addressField.getText().trim().toString());
								learnerList.elementAt(index).set_courseName(programField.getText().trim().toString());
								learnerList.elementAt(index).set_skimTotal(Integer.parseInt(skimField.getText().trim().toString()));
								learnerList.elementAt(index).set_downPayment(Integer.parseInt(downPaymentField.getText().trim().toString()));
								learnerList.elementAt(index).erase_installments();
								learnerList.elementAt(index).set_picture(imageLabel.getIcon());
								Connection temp=sqlConnection.dbConnection();
								PreparedStatement updateStatement=temp.prepareStatement("UPDATE learners SET email= '"+emailField.getText().trim().toString()+"',name='"+nameField.getText().trim().toString()+"',objects=? WHERE email='"+emailField.getText().trim().toString()+"';");
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
							    ObjectOutputStream oos = new ObjectOutputStream(baos);
							    oos.writeObject(learnerList.elementAt(index));
							    byte[] objectAsBytes = baos.toByteArray();
							    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
							    updateStatement.setBinaryStream(1, bais, objectAsBytes.length);
							    updateStatement.executeUpdate();
							    oos.close();
							    bais.close();
							    baos.close();
							    updateStatement.close();
							    temp.close();
							    JOptionPane.showMessageDialog(frame, "Learner's information updated successfully!");
							}
						}
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
					JOptionPane.showMessageDialog(frame, "Enter informations correctly");
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		lblAdd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdd.setBorder(BorderFactory.createLineBorder(Color.black));
		lblAdd.setOpaque(true);
		lblAdd.setBounds(420, 337, 109, 37);
		panel_1.add(lblAdd);
		
		JLabel lblClear = new JLabel("clear");
		lblClear.setBackground(new Color(255, 250, 250));
		lblClear.setFont(new Font("Consolas", Font.PLAIN, 15));
		lblClear.setOpaque(true);
		lblClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				searchField.setText("search");
				emailField.setEditable(true);
				imageLabel.setIcon(null);
				nameField.setText("name");
				emailField.setText("email");
				cellField.setText("cell");
				addressField.setText("address");
				programField.setText("program details");
				skimField.setText("skim total");
				downPaymentField.setText("down Payment");
				DefaultTableModel model=(DefaultTableModel) table.getModel();
				model.setRowCount(0);
				list.clearSelection();
				textField.setText(null);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		lblClear.setHorizontalAlignment(SwingConstants.CENTER);
		lblClear.setBorder(BorderFactory.createLineBorder(Color.black));
		lblClear.setBounds(549, 337, 64, 37);
		panel_1.add(lblClear);
		
		JLabel lblNewLabel_3 = new JLabel("Edition");
		lblNewLabel_3.setFont(new Font("Consolas", Font.BOLD, 18));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(367, 31, 157, 44);
		panel_1.add(lblNewLabel_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(68, 321, 179, 153);
		panel_1.add(panel_2);
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(null);
		
		skimField = new JTextField();
		skimField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(skimField.getText().trim().toString().equalsIgnoreCase("skim total"))
				{
					skimField.setText(null);
					skimField.requestFocus();;
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(skimField.getText().trim().toString().equalsIgnoreCase("skim total") ||  skimField.getText().trim().toString().equals("")||skimField.getText().trim().toString().equals(null))
				{
					skimField.setText("skim total");
					
				}
			}
		});
		skimField.setText("skim total");
		skimField.setHorizontalAlignment(SwingConstants.CENTER);
		skimField.setForeground(Color.GRAY);
		skimField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		skimField.setColumns(10);
		skimField.setBounds(30, 62, 94, 30);
		panel_2.add(skimField);
		
		downPaymentField = new JTextField();
		downPaymentField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(downPaymentField.getText().trim().toString().equalsIgnoreCase("down payment"))
				{
					downPaymentField.setText(null);
					downPaymentField.requestFocus();;
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(downPaymentField.getText().trim().toString().equalsIgnoreCase("down payment") ||  downPaymentField.getText().trim().toString().equals("")||downPaymentField.getText().trim().toString().equals(null))
				{
					downPaymentField.setText("down payment");
					
				}
			}
		});
		downPaymentField.setText("down payment");
		downPaymentField.setHorizontalAlignment(SwingConstants.CENTER);
		downPaymentField.setForeground(Color.GRAY);
		downPaymentField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		downPaymentField.setColumns(10);
		downPaymentField.setBounds(30, 103, 94, 30);
		panel_2.add(downPaymentField);
		
		lblNewLabel_2 = new JLabel("Payments");
		lblNewLabel_2.setForeground(new Color(70, 130, 180));
		lblNewLabel_2.setFont(new Font("Consolas", Font.BOLD, 22));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(20, 21, 137, 30);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("tk");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(134, 62, 22, 30);
		panel_2.add(lblNewLabel_4);
		
		JLabel label = new JLabel("tk");
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label.setBounds(134, 103, 22, 30);
		panel_2.add(label);
		
		JLabel lblLabSymbiotic = new JLabel("\u00A9LAB Symbiotic");
		lblLabSymbiotic.setForeground(new Color(255, 255, 0));
		lblLabSymbiotic.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblLabSymbiotic.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabSymbiotic.setBounds(468, 723, 148, 14);
		frame.getContentPane().add(lblLabSymbiotic);
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
	
	public JPanel my_optionPanel()
	{
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 255, 255));
		panel_4.setBounds(330, 389, 212, 118);
		panel_4.setLayout(null);
		
		JLabel lblEnterPassword = new JLabel("Enter password");
		lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEnterPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterPassword.setBounds(22, 11, 173, 22);
		panel_4.add(lblEnterPassword);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(47, 43, 120, 27);
		panel_4.add(passwordField);
		
		JLabel lblProceed = new JLabel("proceed");
		lblProceed.setHorizontalAlignment(SwingConstants.CENTER);
		lblProceed.setBorder(BorderFactory.createLineBorder(Color.black));
		lblProceed.setOpaque(true);
		lblProceed.setBounds(78, 81, 61, 24);
		panel_4.add(lblProceed);
		return panel_4;
	}
}

