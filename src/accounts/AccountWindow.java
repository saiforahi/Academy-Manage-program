package accounts;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import connections.sqlConnection;
import menu.Menu;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import javax.swing.ScrollPaneConstants;

public class AccountWindow {

	public JFrame frame;
	private JTextField txtAmount;
	private JLabel lblNewLabel_1;
	private JLabel dateLabel;
	Thread MrCounter;
	private boolean runMrCounter=true;
	private JTextPane purposeField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JCheckBox chckbxUnofficial;
	private JList<String> list;
	private DefaultListModel<String>  collectionList=null;
	private DefaultListModel<String>  expenseList=null;
	private JLabel timeLabel;
	private static JLabel dateField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountWindow window = new AccountWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void add_payment(String date,Payment newPayment)
	{
		try
		{
			Connection temp=sqlConnection.dbConnection();
			PreparedStatement insertStatement=temp.prepareStatement("INSERT INTO expenses(date,object) VALUES (?,?);");
		    insertStatement.setString(1,date);
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream(baos);
		    oos.writeObject(newPayment);
		    byte[] objectAsBytes = baos.toByteArray();
		    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
		    insertStatement.setBinaryStream(2, bais, objectAsBytes.length);
		    insertStatement.executeUpdate();
		    bais.close();
		    oos.close();
		    baos.close();
		    insertStatement.close();
		    temp.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void set_CollectionTable()
	{
		collectionList.clear();
		try
		{
			Connection conn=sqlConnection.dbConnection();
			ResultSet rs=conn.createStatement().executeQuery("SELECT objects FROM collections;");
			while(rs.next())
			{
				byte[] byteStream = (byte[]) rs.getObject(1);
			    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
			    ObjectInputStream ois1 = new ObjectInputStream(baip);
			    Collection dummy=(Collection)ois1.readObject();
			    collectionList.addElement(dummy.get_collectionDate()+"          "+dummy.get_collectionAmount()+" BDT");
			   
			}
			rs.close();
			conn.close();
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
	
	public void set_expensesList()
	{
		expenseList.clear();
		try
		{
			Connection conn=sqlConnection.dbConnection();
			ResultSet rs=conn.createStatement().executeQuery("SELECT object FROM expenses;");
			while(rs.next())
			{
				byte[] byteStream = (byte[]) rs.getObject(1);
			    ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
			    ObjectInputStream ois1 = new ObjectInputStream(baip);
			    Payment dummy=(Payment)ois1.readObject();
			    expenseList.addElement(dummy.get_payment_date()+" "+dummy.get_paid_amount()+" BDT");
			    ois1.close();
			    baip.close();
			}
			rs.close();
			conn.close();
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
	public AccountWindow() {
		initialize();
		set_CollectionTable();
		set_expensesList();
		MrCounter.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		collectionList=new DefaultListModel<String>();
		expenseList=new DefaultListModel<String>();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				runMrCounter=false;
				Menu go=new Menu();
				go.frame.setVisible(true);
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setUndecorated(false);
		frame.setResizable(false);
		frame.setSize(new Dimension(screenSize.width, screenSize.height));
		frame.getContentPane().setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		frame.setBackground(SystemColor.menu);
		frame.setLocationRelativeTo(null);
		frame.setLocation(0,0);
		frame.setTitle("Accounts");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(35, 35, 787, 412);
		frame.getContentPane().add(panel);
		panel.setBackground(SystemColor.text);
		panel.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(455, 11, 323, 390);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add a collection");
		lblNewLabel.setForeground(new Color(255, 127, 80));
		lblNewLabel.setFont(new Font("Garamond", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 303, 54);
		panel_3.add(lblNewLabel);
		
		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtAmount.setHorizontalAlignment(SwingConstants.CENTER);
		txtAmount.setText("amount");
		txtAmount.setBounds(44, 76, 240, 34);
		panel_3.add(txtAmount);
		txtAmount.setColumns(10);
		
		purposeField = new JTextPane();
		purposeField.setText("write down the source of collection here");
		purposeField.setBounds(44, 131, 240, 140);
		panel_3.add(purposeField);
		
		lblNewLabel_1 = new JLabel("Save!");
		lblNewLabel_1.setBackground(new Color(210, 180, 140));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(Integer.parseInt(txtAmount.getText().toString())>0 && !purposeField.getText().equals(null) && !purposeField.getText().equals(""))
				{
					int dialogResult = JOptionPane.showConfirmDialog (frame, "This is dialougue box for Confirmation to proceed");
					if(dialogResult == JOptionPane.YES_OPTION){
						Collection newCollection=new Collection("name",dateField.getText().toString(),Integer.toString(Calendar.getInstance().get(Calendar.HOUR))+":"+Integer.toString(Calendar.getInstance().get(Calendar.MINUTE))+":"+Integer.toString(Calendar.getInstance().get(Calendar.SECOND)),purposeField.getText().trim().toString(),Integer.parseInt(txtAmount.getText().trim().toString()));
						try {
							Connection conn=sqlConnection.dbConnection();
							PreparedStatement pstmt = conn.prepareStatement("INSERT INTO collections(collector,objects) VALUES (?,?)");
							pstmt.setString(1, "name");
							
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
						    ObjectOutputStream oos = new ObjectOutputStream(baos);
						    oos.writeObject(newCollection);
						    byte[] objectAsBytes = baos.toByteArray();
						    
						    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
						    pstmt.setBinaryStream(2, bais, objectAsBytes.length);
						    pstmt.executeUpdate();
							pstmt.close();
							conn.close();
							collectionList.addElement(newCollection.get_collectionDate()+" "+newCollection.get_collectionAmount()+" BDT");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else
				{
					System.out.println("else esecuting");
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
		lblNewLabel_1.setFont(new Font("Estrangelo Edessa", Font.BOLD, 17));
		lblNewLabel_1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(36, 320, 248, 35);
		panel_3.add(lblNewLabel_1);
		
		dateLabel = new JLabel("date & time");
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dateLabel.setForeground(Color.DARK_GRAY);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setBounds(36, 282, 70, 27);
		panel_3.add(dateLabel);
		
		dateField = new JLabel("");
		dateField.setHorizontalAlignment(SwingConstants.CENTER);
		dateField.setForeground(new Color(255, 99, 71));
		dateField.setBounds(121, 282, 85, 27);
		panel_3.add(dateField);
		
		timeLabel = new JLabel("");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setForeground(new Color(255, 99, 71));
		timeLabel.setBounds(204, 282, 80, 27);
		panel_3.add(timeLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 99, 71));
		separator.setForeground(new Color(255, 69, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(433, 11, 4, 390);
		panel.add(separator);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setViewportBorder(new TitledBorder(null, "Collections", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		scrollPane_2.setOpaque(true);
		//scrollPane_2.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Collections",TitledBorder.CENTER,TitledBorder.TOP,new Font("times new roman",Font.PLAIN,15), Color.black));
		scrollPane_2.setBackground(Color.WHITE);
		scrollPane_2.setBounds(36, 90, 247, 136);
		panel.add(scrollPane_2);
		
		list = new JList<String>(collectionList);
		scrollPane_2.setViewportView(list);
		
		JLabel label = new JLabel("view details");
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		label.setBackground(new Color(240, 230, 140));
		label.setBounds(302, 146, 109, 29);
		panel.add(label);
		
		JLabel lblPaidBy = new JLabel("Collected by :");
		lblPaidBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPaidBy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaidBy.setBounds(36, 251, 84, 22);
		panel.add(lblPaidBy);
		
		JLabel lblDate = new JLabel("Date :");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(36, 275, 84, 22);
		panel.add(lblDate);
		
		JLabel lblAmount_1 = new JLabel("Amount :");
		lblAmount_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAmount_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount_1.setBounds(36, 297, 84, 22);
		panel.add(lblAmount_1);
		
		JLabel lblSource = new JLabel("Source :");
		lblSource.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSource.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSource.setBounds(36, 319, 84, 22);
		panel.add(lblSource);
		
		JLabel lblSearchBy = new JLabel("Search by");
		lblSearchBy.setBounds(36, 57, 56, 22);
		panel.add(lblSearchBy);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(130, 321, 284, 80);
		panel.add(textPane_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_2.setBounds(876, 35, 452, 653);
		frame.getContentPane().add(panel_2);
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportBorder(new TitledBorder(null, "Expenses", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		scrollPane.setOpaque(true);
		//scrollPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Expenses",TitledBorder.CENTER,TitledBorder.TOP,new Font("times new roman",Font.PLAIN,15), Color.black));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(40, 83, 268, 296);
		panel_2.add(scrollPane);
		
		JList<String> list_1 = new JList<String>((expenseList));
		scrollPane.setViewportView(list_1);
		
		JLabel label_1 = new JLabel("Collected by :");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(40, 390, 84, 22);
		panel_2.add(label_1);
		
		JLabel label_3 = new JLabel("Date :");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_3.setBounds(40, 414, 84, 22);
		panel_2.add(label_3);
		
		JLabel label_6 = new JLabel("Amount :");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_6.setBounds(40, 436, 84, 22);
		panel_2.add(label_6);
		
		JLabel label_7 = new JLabel("Source :");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_7.setBounds(40, 458, 84, 22);
		panel_2.add(label_7);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(134, 458, 276, 163);
		panel_2.add(textPane);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(new Color(255, 69, 0));
		separator_1.setBackground(new Color(255, 99, 71));
		separator_1.setBounds(851, 35, 15, 645);
		frame.getContentPane().add(separator_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(349, 458, 473, 215);
		frame.getContentPane().add(panel_4);
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBackground(Color.WHITE);
		panel_4.setLayout(null);
		
		JLabel lblMakeAPayment = new JLabel("Make a Payment");
		lblMakeAPayment.setForeground(new Color(139, 69, 19));
		lblMakeAPayment.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMakeAPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakeAPayment.setBounds(176, 11, 122, 19);
		panel_4.add(lblMakeAPayment);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_5.setBackground(new Color(255, 255, 255));
		panel_5.setBounds(25, 41, 224, 152);
		panel_4.add(panel_5);
		panel_5.setLayout(null);
		
		JTextPane txtpnWriteDownPurpose = new JTextPane();
		txtpnWriteDownPurpose.setBounds(10, 11, 204, 130);
		panel_5.add(txtpnWriteDownPurpose);
		txtpnWriteDownPurpose.setBackground(new Color(255, 255, 255));
		txtpnWriteDownPurpose.setText("write down purpose of payment");
		txtpnWriteDownPurpose.setBorder(new LineBorder(new Color(255, 255, 255)));
		
		chckbxUnofficial = new JCheckBox("Unofficial");
		chckbxUnofficial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxUnofficial.isSelected())
				{
					
				}
			}
		});
		chckbxUnofficial.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxUnofficial.setBackground(Color.WHITE);
		chckbxUnofficial.setBounds(359, 44, 89, 23);
		panel_4.add(chckbxUnofficial);
		
		JLabel lblPayTo = new JLabel("Pay to");
		lblPayTo.setHorizontalAlignment(SwingConstants.LEFT);
		lblPayTo.setBounds(389, 84, 59, 19);
		panel_4.add(lblPayTo);
		
		JLabel label_2 = new JLabel("Amount");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setBounds(389, 114, 59, 19);
		panel_4.add(label_2);
		
		JLabel label_4 = new JLabel("Password");
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setBounds(389, 144, 59, 19);
		panel_4.add(label_4);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(277, 113, 102, 20);
		panel_4.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(277, 84, 102, 20);
		panel_4.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(277, 143, 102, 20);
		panel_4.add(textField_4);
		
		JLabel label_5 = new JLabel("Pay!");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_5.setBounds(318, 176, 89, 28);
		panel_4.add(label_5);
		frame.setLocationRelativeTo(null);
		
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
}
