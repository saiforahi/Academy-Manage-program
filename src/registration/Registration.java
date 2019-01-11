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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import connections.sqlConnection;
import menu.Menu;

public class Registration {

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
	FileInputStream stream=null;  
	private JTextField textField;
	private JTextField searchField;
	private JLabel searchButton;
	private JComboBox<String> comboBox;
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
			PreparedStatement pstmt = temp.prepareStatement("SELECT objects FROM learners;");
			ResultSet rs=pstmt.executeQuery();
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
			pstmt.close();
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
			System.out.println(date+" "+amount);
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
		list.getSelectionModel().clearSelection();
		learnerNameList.removeElement(toDeleteLearner.get_email());
		learnerList.remove(toDeleteLearner);
		
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
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setSize(new Dimension(screenSize.width, screenSize.height));
		frame.getContentPane().setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		frame.setBackground(SystemColor.menu);
		frame.setLocationRelativeTo(null);
		frame.setLocation(0,0);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(SystemColor.inactiveCaptionText, 2));
		panel.setBackground(SystemColor.info);
		panel.setBounds(878, 21, 467, 716);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(22, 244, 422, 182);
		panel.add(panel_4);
		panel_4.setLayout(null);
		panel_4.setBackground(SystemColor.activeCaptionBorder);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(0, 0, 0, 65));
		scrollPane.setBounds(10, 11, 402, 160);
		panel_4.add(scrollPane);
		
		table = new JTable();
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
		
		JLabel lblNewLabel = new JLabel("Report");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(22, 510, 48, 20);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Null");
		lblNewLabel_1.setBounds(22, 534, 422, 159);
		lblNewLabel_1.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(lblNewLabel_1);
		
		JLabel lblUpdate = new JLabel("update");
		lblUpdate.setBounds(297, 158, 60, 30);
		panel.add(lblUpdate);
		lblUpdate.setBackground(new Color(240, 230, 140));
		lblUpdate.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
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
		lblUpdate.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel label_2 = new JLabel("delete");
		label_2.setBounds(367, 158, 60, 30);
		panel.add(label_2);
		label_2.setBackground(new Color(255, 160, 122));
		label_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
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
		label_2.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel lblViewDetails = new JLabel("view details");
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
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						int due=dummy.get_skimTotal();
						for(int index=0;index<dummy.get_Installments().size();index++)
						{
							model.addRow(new Object[]{dummy.get_Installments().elementAt(index).get_date(),dummy.get_Installments().elementAt(index).get_amount(),Integer.toString(due-dummy.get_Installments().elementAt(index).get_amount())});
							due=due-dummy.get_Installments().elementAt(index).get_amount();
						}
						
						//centerRenderer = new DefaultTableCellRenderer();
						//table.getColumn("Date").setCellRenderer( centerRenderer );
						//table.getColumn("Installment").setCellRenderer( centerRenderer );
						//table.getColumn("Due").setCellRenderer( centerRenderer );
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
			public void mouseEntered(MouseEvent arg0) {
			}
		});
		lblViewDetails.setOpaque(true);
		lblViewDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewDetails.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblViewDetails.setBorder(BorderFactory.createLineBorder(Color.black));
		lblViewDetails.setBackground(new Color(240, 230, 140));
		lblViewDetails.setBounds(297, 121, 129, 26);
		panel.add(lblViewDetails);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(22, 80, 265, 153);
		panel.add(scrollPane_1);
		
		list = new JList(learnerNameList);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.setBackground(new Color(240, 248, 255));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(list);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_5.setBounds(22, 437, 422, 62);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblAddPayment = new JLabel("Add payment");
		lblAddPayment.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAddPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddPayment.setBounds(10, 11, 106, 38);
		panel_5.add(lblAddPayment);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(121, 11, 178, 38);
		panel_5.add(textField);
		textField.setColumns(10);
		
		JLabel payButton = new JLabel("Pay!");
		payButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(emailField.getText().equals(list.getSelectedValue()))
				{
					update_learner(emailField.getText().toString(),Integer.parseInt(textField.getText().toString().trim()));
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.addRow(new Object[]{ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME).toString(),textField.getText(),Integer.toString(Integer.parseInt(table.getValueAt(table.getRowCount()-1, 2).toString())-Integer.parseInt(textField.getText().trim().toString()))});
					JOptionPane.showMessageDialog(frame, "Learner's information updated!");
				}
			}
		});
		payButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		payButton.setHorizontalAlignment(SwingConstants.CENTER);
		payButton.setBorder(BorderFactory.createLineBorder(Color.black));
		payButton.setBounds(309, 15, 93, 31);
		panel_5.add(payButton);
		
		searchField = new JTextField();
		searchField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		searchField.setHorizontalAlignment(SwingConstants.CENTER);
		searchField.setBounds(22, 39, 265, 30);
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
		});
		searchButton.setBounds(365, 40, 62, 29);
		searchButton.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(searchButton);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Email", "Name"}));
		comboBox.setBounds(295, 39, 60, 30);
		panel.add(comboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(SystemColor.windowBorder, 1, true));
		panel_1.setBackground(SystemColor.info);
		panel_1.setLayout(null);
		panel_1.setBounds(21, 21, 843, 268);
		frame.getContentPane().add(panel_1);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(244, 164, 96));
		panel_3.setBounds(10, 63, 147, 153);
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
					Icon image=new ImageIcon(((new ImageIcon(""+selectedFile)).getImage()).getScaledInstance(163,153, java.awt.Image.SCALE_SMOOTH));
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
		imageLabel.setBounds(0, 0, 147, 153);
		panel_3.add(imageLabel);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBorder(new LineBorder(SystemColor.inactiveCaptionText));
		imageLabel.setBackground(Color.WHITE);
		
		nameField = new JTextField();
		nameField.setText("name");
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setForeground(Color.GRAY);
		nameField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameField.setColumns(10);
		nameField.setBounds(167, 63, 499, 30);
		panel_1.add(nameField);
		
		cellField = new JTextField();
		cellField.setText("cell");
		cellField.setHorizontalAlignment(SwingConstants.CENTER);
		cellField.setForeground(Color.GRAY);
		cellField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cellField.setColumns(10);
		cellField.setBounds(167, 104, 237, 30);
		panel_1.add(cellField);
		
		emailField = new JTextField();
		emailField.setText("email");
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setForeground(Color.GRAY);
		emailField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		emailField.setColumns(10);
		emailField.setBounds(429, 104, 237, 30);
		panel_1.add(emailField);
		
		addressField = new JTextField();
		addressField.setText("address");
		addressField.setHorizontalAlignment(SwingConstants.CENTER);
		addressField.setForeground(Color.GRAY);
		addressField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		addressField.setColumns(10);
		addressField.setBounds(167, 145, 499, 30);
		panel_1.add(addressField);
		
		programField = new JTextField();
		programField.setText("program details");
		programField.setHorizontalAlignment(SwingConstants.CENTER);
		programField.setForeground(Color.GRAY);
		programField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		programField.setColumns(10);
		programField.setBounds(167, 186, 499, 30);
		panel_1.add(programField);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(676, 63, 157, 153);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		skimField = new JTextField();
		skimField.setText("skim total");
		skimField.setHorizontalAlignment(SwingConstants.CENTER);
		skimField.setForeground(Color.GRAY);
		skimField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		skimField.setColumns(10);
		skimField.setBounds(20, 62, 116, 30);
		panel_2.add(skimField);
		
		downPaymentField = new JTextField();
		downPaymentField.setText("down payment");
		downPaymentField.setHorizontalAlignment(SwingConstants.CENTER);
		downPaymentField.setForeground(Color.GRAY);
		downPaymentField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		downPaymentField.setColumns(10);
		downPaymentField.setBounds(20, 103, 116, 30);
		panel_2.add(downPaymentField);
		
		lblNewLabel_2 = new JLabel("Payments");
		lblNewLabel_2.setForeground(new Color(70, 130, 180));
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(20, 11, 116, 40);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblAdd = new JLabel("add/update");
		lblAdd.setBackground(new Color(144, 238, 144));
		lblAdd.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(stream!=null && nameField.getText()!=null && cellField.getText()!=null && emailField.getText()!=null && addressField.getText()!=null && programField.getText()!=null && skimField.getText()!=null && downPaymentField.getText()!=null && imageLabel.getIcon()!=null)
				{
					
					try {
						boolean found=false;
						for(int index=0;index<learnerList.size();index++)
						{
							if(learnerList.get(index).toString().equals(emailField.getText()))
							{
								found=true;
								break;
							}
						}
						if(found==false)
						{
							Learner newLearner=new Learner(imageLabel.getIcon(),nameField.getText(),cellField.getText(),emailField.getText(),addressField.getText(),programField.getText(),Integer.parseInt(skimField.getText()),Integer.parseInt(downPaymentField.getText()));
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
						    
							//pstmt.setBinaryStream(4, stream,stream.available());
							pstmt.executeUpdate();
							pstmt.close();
							conn.close();
							learnerList.add(learnerList.size(),newLearner);
							learnerNameList.addElement(newLearner.get_email());
							JOptionPane.showMessageDialog(frame, "Learner's information saved!");
						}
						else
						{
							JOptionPane.showMessageDialog(frame, "An entry already exists with this email, enter another email..");
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
		lblAdd.setBounds(298, 227, 104, 30);
		panel_1.add(lblAdd);
		
		JLabel lblClear = new JLabel("clear");
		lblClear.setBackground(new Color(255, 250, 250));
		lblClear.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblClear.setOpaque(true);
		lblClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				imageLabel.setIcon(null);
				nameField.setText(null);
				emailField.setText(null);
				cellField.setText(null);
				addressField.setText(null);
				programField.setText(null);
				skimField.setText(null);
				downPaymentField.setText(null);
				DefaultTableModel model=(DefaultTableModel) table.getModel();
				model.setRowCount(0);
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
		lblClear.setBounds(427, 227, 60, 30);
		panel_1.add(lblClear);
		
		JLabel lblNewLabel_3 = new JLabel("Edition");
		lblNewLabel_3.setFont(new Font("Consolas", Font.BOLD, 16));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(342, 11, 157, 44);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblLabSymbiotic = new JLabel("\u00A9LAB Symbiotic");
		lblLabSymbiotic.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblLabSymbiotic.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabSymbiotic.setBounds(365, 560, 148, 14);
		frame.getContentPane().add(lblLabSymbiotic);
	}
}
