package accounts;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import connections.sqlConnection;
import login.Animation;
import menu.Menu;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.DropMode;

public class AccountWindow {

	public JFrame frame;
	private JTextField txtAmount;
	private JLabel lblNewLabel_1;
	private JLabel dateLabel;
	Thread MrCounter;
	private boolean runMrCounter=true;
	private JTextPane purposeField;
	private JTextField payAmountField;
	private JTextField payToField;
	private JList<String> list;
	private DefaultListModel<String>  collectionList=null;
	private DefaultListModel<String>  expenseList=null;
	private JLabel timeLabel;
	private static JLabel dateField;
	private JLabel expensePaidByLabel;
	private JLabel expenseDateLabel;
	private JLabel expenseAmountLabel;
	private JList<String> list_1;
	private JTextPane purposeTextPane;
	private JLabel expenseTimeLabel;
	JPasswordField passwordField;
	JLabel lblProceed;
	JLabel lblEnterPassword ;
	private JTextPane paymentPurposeField;
	private JLabel collectionByLabel;
	private JLabel collectionDateLabel;
	private JLabel label3;
	private JLabel label4;
	private JTextPane collectionSourcePan;
	private JPanel collectionPanelView;
	private JLabel collectionTimeLabel;
	private JLabel dateLabelCollection;
	private JLabel collectionAmountLabel;
	private JLabel collectedByLabel;
	private JLabel label_1;
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
	
	public static void add_collection(Collection newCollection)
	{
		try {
			Connection conn=sqlConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO collections(collectionNo,collector,objects) VALUES (?,?,?)");
			pstmt.setString(1,newCollection.get_collectionNo());
			pstmt.setString(2, newCollection.get_collectorName());
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream(baos);
		    oos.writeObject(newCollection);
		    byte[] objectAsBytes = baos.toByteArray();
		    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
		    pstmt.setBinaryStream(3, bais, objectAsBytes.length);
		    pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void add_payment(String date,String time,Payment newPayment)
	{
		try
		{
			Connection temp=sqlConnection.dbConnection();
			PreparedStatement insertStatement=temp.prepareStatement("INSERT INTO expenses(date,time,object) VALUES (?,?,?);");
		    insertStatement.setString(1,date);
		    insertStatement.setString(2,time);
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream(baos);
		    oos.writeObject(newPayment);
		    byte[] objectAsBytes = baos.toByteArray();
		    ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
		    insertStatement.setBinaryStream(3, bais, objectAsBytes.length);
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
	public void set_CollectionList()
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
			    collectionList.addElement(dummy.get_collectionNo()+"     "+dummy.get_collectorName());
			   
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
			    expenseList.addElement(dummy.get_payment_date()+"     "+dummy.get_payment_time());
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
	/**
	 * Create the application.
	 */
	public AccountWindow() {
		initialize();
		set_CollectionList();
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
		frame.setUndecorated(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				runMrCounter=false;
				Menu go=new Menu();
				go.frame.setVisible(true);
			}
		});
		frame.setForeground(Color.BLACK);
		frame.getContentPane().setForeground(new Color(0,0,0,0));
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setResizable(false);
		frame.setSize(new Dimension(screenSize.width, screenSize.height));
		frame.getContentPane().setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		frame.setBackground(Color.BLACK);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Accounts");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(0, 0, 1366, 768);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		collectionPanelView = new JPanel();
		collectionPanelView.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		collectionPanelView.setBounds(31, 37, 468, 412);
		panel_1.add(collectionPanelView);
		collectionPanelView.setBackground(Color.WHITE);
		collectionPanelView.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane_2.setViewportBorder(new TitledBorder(null, "Collections", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		scrollPane_2.setOpaque(true);
		//scrollPane_2.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Collections",TitledBorder.CENTER,TitledBorder.TOP,new Font("times new roman",Font.PLAIN,15), Color.black));
		scrollPane_2.setBackground(Color.WHITE);
		scrollPane_2.setBounds(29, 90, 199, 295);
		collectionPanelView.add(scrollPane_2);
		
		list = new JList<String>(collectionList);
		list.setBorder(new LineBorder(Color.WHITE, 6));
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount()==2 && list.getSelectedIndex()>-1)
				{
					arg0.consume();
					try
					{
						Connection temp=sqlConnection.dbConnection();
						char[] stringToCharArray=list.getSelectedValue().toString().toCharArray();
						int index=10;
						for(;index<stringToCharArray.length;index++)
						{
							if((int)stringToCharArray[index]==32)
							{
								break;
							}
						}
					    ResultSet rs=temp.createStatement().executeQuery("SELECT * FROM collections WHERE collectionNo='"+list.getSelectedValue().toString().substring(0,index).trim()+"';");
					    Collection dummy=(Collection) new ObjectInputStream(new ByteArrayInputStream((byte[]) rs.getObject(3))).readObject();
					    collectedByLabel.setText(dummy.get_collectorName());
					    dateLabelCollection.setText(dummy.get_collectionDate());
					    collectionTimeLabel.setText(dummy.get_collectionTime());
					    collectionAmountLabel.setText(Integer.toString(dummy.get_collectionAmount()));
					    collectionSourcePan.setText(dummy.get_collectionSource());
						rs.close();
						temp.close();
						
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
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
		scrollPane_2.setViewportView(list);
		
		JLabel lblCollections = new JLabel("Collections");
		lblCollections.setOpaque(true);
		lblCollections.setBackground(Color.WHITE);
		lblCollections.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		lblCollections.setFont(new Font("Estrangelo Edessa", Font.BOLD, 16));
		lblCollections.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_2.setColumnHeaderView(lblCollections);
		
		JLabel label_4 = new JLabel("");
		scrollPane_2.setRowHeaderView(label_4);
		
		collectionByLabel = new JLabel("Collected by :");
		collectionByLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		collectionByLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		collectionByLabel.setBounds(238, 90, 84, 22);
		collectionPanelView.add(collectionByLabel);
		
		collectionDateLabel = new JLabel("Date :");
		collectionDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		collectionDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		collectionDateLabel.setBounds(238, 114, 84, 22);
		collectionPanelView.add(collectionDateLabel);
		
		label3 = new JLabel("Time :");
		label3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setBounds(238, 136, 84, 22);
		collectionPanelView.add(label3);
		
		JLabel lblSource = new JLabel("Source :");
		lblSource.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSource.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSource.setBounds(248, 190, 74, 22);
		collectionPanelView.add(lblSource);
		
		JLabel lblSearchBy = new JLabel("Search by");
		lblSearchBy.setBounds(36, 57, 56, 22);
		collectionPanelView.add(lblSearchBy);
		
		collectionSourcePan = new JTextPane();
		collectionSourcePan.setFont(new Font("Tahoma", Font.PLAIN, 13));
		collectionSourcePan.setText("Es triste saber que con semejante talento tiene tan pocos suscriptores y youtubers basura tienen 10 m como Kimberly Loaiza y Juan de Dios Pantoja\uFEFF");
		collectionSourcePan.setEditable(false);
		collectionSourcePan.setBorder(new LineBorder(new Color(255, 255, 255), 10));
		collectionSourcePan.setBounds(258, 212, 184, 173);
		collectionPanelView.add(collectionSourcePan);
		
		collectedByLabel = new JLabel("null");
		collectedByLabel.setHorizontalAlignment(SwingConstants.LEFT);
		collectedByLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		collectedByLabel.setBounds(337, 90, 75, 22);
		collectionPanelView.add(collectedByLabel);
		
		dateLabelCollection = new JLabel("null");
		dateLabelCollection.setHorizontalAlignment(SwingConstants.LEFT);
		dateLabelCollection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dateLabelCollection.setBounds(337, 114, 75, 22);
		collectionPanelView.add(dateLabelCollection);
		
		collectionTimeLabel = new JLabel("null");
		collectionTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		collectionTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		collectionTimeLabel.setBounds(337, 137, 91, 22);
		collectionPanelView.add(collectionTimeLabel);
		
		label4 = new JLabel("Amount :");
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label4.setBounds(238, 159, 84, 22);
		collectionPanelView.add(label4);
		
		collectionAmountLabel = new JLabel("null");
		collectionAmountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		collectionAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		collectionAmountLabel.setBounds(337, 159, 75, 22);
		collectionPanelView.add(collectionAmountLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(961, 37, 366, 476);
		panel_1.add(panel_2);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrollPane.setOpaque(true);
		//scrollPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Expenses",TitledBorder.CENTER,TitledBorder.TOP,new Font("times new roman",Font.PLAIN,15), Color.black));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(34, 35, 297, 210);
		panel_2.add(scrollPane);
		
		list_1 = new JList<String>((expenseList));
		list_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list_1.setBorder(new LineBorder(new Color(255, 255, 255), 7));
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2&& !arg0.isConsumed() ) {
					arg0.consume();
					if(list_1.getSelectedIndex()>-1)
					{
						try
						{
							
							Connection temp=sqlConnection.dbConnection();
							String searchDate=list_1.getSelectedValue().toString().substring(0,10);
							System.out.println(searchDate);
							//ResultSet rs=temp.createStatement().executeQuery("SELCET * FROM expenses WHERE date='"+list_1.getSelectedValue().toString().substring(0,10)+"';");
						    ResultSet rs=temp.createStatement().executeQuery("SELECT * FROM expenses WHERE date='"+searchDate+"' AND time='"+list_1.getSelectedValue().toString().substring(15,26)+"';");
						    Payment dummy=(Payment) new ObjectInputStream(new ByteArrayInputStream((byte[]) rs.getObject(3))).readObject();
						    expensePaidByLabel.setText(dummy.get_paidTo_name());
							expenseDateLabel.setText(dummy.get_payment_date());
							expenseAmountLabel.setText(Integer.toString(dummy.get_paid_amount()));
							purposeTextPane.setText(dummy.get_purpose());
							expenseTimeLabel.setText(dummy.get_payment_time());
							rs.close();
							temp.close();
							
						}
						catch(SQLException e)
						{
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
		scrollPane.setViewportView(list_1);
		
		JLabel lblNewLabel_2 = new JLabel("Expenses");
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setFont(new Font("Estrangelo Edessa", Font.BOLD, 18));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel_2);
		
		JLabel label_14 = new JLabel("");
		scrollPane.setRowHeaderView(label_14);
		
		JLabel lblPaidBy_1 = new JLabel("Paid to :");
		lblPaidBy_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaidBy_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPaidBy_1.setBounds(20, 267, 84, 22);
		panel_2.add(lblPaidBy_1);
		
		JLabel label_3 = new JLabel("Date :");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_3.setBounds(20, 291, 84, 22);
		panel_2.add(label_3);
		
		JLabel label_6 = new JLabel("Amount :");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_6.setBounds(20, 313, 84, 22);
		panel_2.add(label_6);
		
		JLabel lblPurpose = new JLabel("Purpose :");
		lblPurpose.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPurpose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPurpose.setBounds(20, 354, 84, 22);
		panel_2.add(lblPurpose);
		
		purposeTextPane = new JTextPane();
		purposeTextPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		purposeTextPane.setEditable(false);
		purposeTextPane.setText("Es triste saber que con semejante talento tiene tan pocos suscriptores y youtubers basura tienen 10 m como Kimberly Loaiza y Juan de Dios Pantoja\uFEFF");
		purposeTextPane.setBounds(124, 347, 224, 105);
		purposeTextPane.setBorder(new LineBorder(new Color(255, 255, 255), 10));
		panel_2.add(purposeTextPane);
		
		expensePaidByLabel = new JLabel("null");
		expensePaidByLabel.setHorizontalAlignment(SwingConstants.LEFT);
		expensePaidByLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		expensePaidByLabel.setBounds(126, 267, 163, 22);
		panel_2.add(expensePaidByLabel);
		
		expenseDateLabel = new JLabel("null");
		expenseDateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		expenseDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		expenseDateLabel.setBounds(126, 291, 86, 22);
		panel_2.add(expenseDateLabel);
		
		expenseAmountLabel = new JLabel("null");
		expenseAmountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		expenseAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		expenseAmountLabel.setBounds(126, 313, 86, 22);
		panel_2.add(expenseAmountLabel);
		
		JLabel lblTime = new JLabel("Time :");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTime.setBounds(208, 291, 46, 22);
		panel_2.add(lblTime);
		
		expenseTimeLabel = new JLabel("null");
		expenseTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		expenseTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		expenseTimeLabel.setBounds(259, 291, 87, 22);
		panel_2.add(expenseTimeLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(924, 37, 9, 680);
		panel_1.add(separator_1);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(new Color(255, 69, 0));
		separator_1.setBackground(new Color(255, 99, 71));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(566, 413, 323, 304);
		panel_1.add(panel_4);
		panel_4.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255), 2), new LineBorder(new Color(128, 0, 0), 3)));
		panel_4.setBackground(Color.WHITE);
		panel_4.setOpaque(false);
		panel_4.setLayout(null);
		
		JLabel lblMakeAPayment = new JLabel("Make a Payment");
		lblMakeAPayment.setForeground(new Color(220, 20, 60));
		lblMakeAPayment.setFont(new Font("Estrangelo Edessa", Font.BOLD, 20));
		lblMakeAPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakeAPayment.setBounds(80, 23, 178, 19);
		panel_4.add(lblMakeAPayment);
		
		JLabel lblPayTo = new JLabel("Pay to");
		lblPayTo.setForeground(Color.WHITE);
		lblPayTo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPayTo.setHorizontalAlignment(SwingConstants.LEFT);
		lblPayTo.setBounds(65, 68, 59, 25);
		panel_4.add(lblPayTo);
		
		JLabel label_2 = new JLabel("Amount");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setBounds(65, 108, 59, 25);
		panel_4.add(label_2);
		
		payAmountField = new JTextField();
		payAmountField.setForeground(new Color(255, 255, 255));
		payAmountField.setBackground(new Color(0, 0, 0));
		payAmountField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		payAmountField.setHorizontalAlignment(SwingConstants.CENTER);
		payAmountField.setColumns(10);
		payAmountField.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		payAmountField.setOpaque(true);
		payAmountField.setBounds(142, 105, 116, 30);
		panel_4.add(payAmountField);
		
		payToField = new JTextField();
		payToField.setForeground(new Color(255, 255, 255));
		payToField.setBackground(new Color(0, 0, 0));
		payToField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		payToField.setHorizontalAlignment(SwingConstants.CENTER);
		payToField.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		payToField.setOpaque(true);
		payToField.setColumns(10);
		payToField.setBounds(142, 65, 116, 30);
		panel_4.add(payToField);
		
		JLabel label_5 = new JLabel("Pay!");
		label_5.setForeground(new Color(255, 255, 0));
		label_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Enter password to proceed:");
				JPasswordField pass = new JPasswordField(10);
				pass.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(label);
				panel.add(pass);
				String[] options = new String[]{"Proceed"};
				int dialogResult = JOptionPane.showOptionDialog(null, panel, "Authentication",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
				if(dialogResult == 0 && checkPassword(new String(pass.getPassword())))
				{
					add_payment(dateField.getText().toString(),timeLabel.getText().toString(),new Payment(Animation.userName,dateField.getText().toString(),timeLabel.getText().toString(),Integer.parseInt(payAmountField.getText().toString()),payToField.getText().toString(),paymentPurposeField.getText().toString()));
					expenseList.addElement(dateField.getText().toString()+"     "+timeLabel.getText().toString());
				}
				
			}
		});
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Estrangelo Edessa", Font.BOLD, 19));
		label_5.setBorder(new LineBorder(new Color(255, 255, 0), 2));
		label_5.setBounds(115, 255, 89, 28);
		panel_4.add(label_5);
		
		paymentPurposeField = new JTextPane();
		paymentPurposeField.setForeground(new Color(255, 255, 255));
		paymentPurposeField.setBounds(55, 151, 213, 93);
		panel_4.add(paymentPurposeField);
		paymentPurposeField.setBackground(new Color(0, 0, 0));
		paymentPurposeField.setBorder(new LineBorder(new Color(255, 255, 0), 2));
		paymentPurposeField.setOpaque(true);
		paymentPurposeField.setText("write down purpose of payment");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_3.setBounds(566, 37, 323, 313);
		panel_1.add(panel_3);
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add a collection");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Estrangelo Edessa", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 303, 54);
		panel_3.add(lblNewLabel);
		
		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtAmount.setHorizontalAlignment(SwingConstants.CENTER);
		txtAmount.setText("amount");
		txtAmount.setBounds(44, 76, 240, 34);
		panel_3.add(txtAmount);
		txtAmount.setColumns(10);
		
		purposeField = new JTextPane();
		purposeField.setText("write down the source of collection here");
		purposeField.setBounds(44, 131, 240, 105);
		panel_3.add(purposeField);
		
		lblNewLabel_1 = new JLabel("Add!");
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(Integer.parseInt(txtAmount.getText().toString())>0 && !purposeField.getText().equals(null) && !purposeField.getText().equals(""))
				{
					JPanel panel = new JPanel();
					JLabel label = new JLabel("Enter a password:");
					JPasswordField pass = new JPasswordField(10);
					pass.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
					panel.add(pass);
					String[] options = new String[]{"Proceed"};
					int dialogResult = JOptionPane.showOptionDialog(null, panel, "Authentication",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
					if(dialogResult == 0&& checkPassword(new String(pass.getPassword()))){
						Collection newCollection=new Collection(generate_collectionNo(),Animation.userName,dateField.getText().toString(),Integer.toString(Calendar.getInstance().get(Calendar.HOUR))+":"+Integer.toString(Calendar.getInstance().get(Calendar.MINUTE))+":"+Integer.toString(Calendar.getInstance().get(Calendar.SECOND)),purposeField.getText().trim().toString(),Integer.parseInt(txtAmount.getText().trim().toString()));
						add_collection(newCollection);
						collectionList.addElement(newCollection.get_collectionNo()+"     "+newCollection.get_collectorName());
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
		lblNewLabel_1.setBounds(104, 247, 117, 35);
		panel_3.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(531, 37, 2, 412);
		panel_1.add(separator);
		separator.setBackground(new Color(255, 99, 71));
		separator.setForeground(new Color(255, 69, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		
		dateLabel = new JLabel("date & time");
		dateLabel.setBounds(605, 371, 70, 27);
		panel_1.add(dateLabel);
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dateLabel.setForeground(Color.WHITE);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		dateField = new JLabel("");
		dateField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dateField.setBounds(690, 371, 85, 27);
		panel_1.add(dateField);
		dateField.setHorizontalAlignment(SwingConstants.CENTER);
		dateField.setForeground(Color.YELLOW);
		
		timeLabel = new JLabel("");
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		timeLabel.setBounds(773, 371, 80, 27);
		panel_1.add(timeLabel);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setForeground(Color.YELLOW);
		
		label_1 = new JLabel("\u00A9LAB Symbiotic");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.YELLOW);
		label_1.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		label_1.setBounds(603, 728, 148, 17);
		panel_1.add(label_1);
		
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
	
	public static boolean checkPassword(String givenPassword)
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
		panel_4.setSize(400,400);
		panel_4.setLayout(null);
		
		passwordField = new JPasswordField(10);
		passwordField.setBounds(47, 83, 120, 27);         //initializing password field for customized JOptionPane
		
		lblProceed = new JLabel("proceed");
		lblProceed.setHorizontalAlignment(SwingConstants.CENTER);
		lblProceed.setBorder(BorderFactory.createLineBorder(Color.black));   //initializing proceed button for customized optionpane
		lblProceed.setOpaque(true);
		lblProceed.setBounds(78, 81, 61, 24);
		
		lblEnterPassword = new JLabel("Enter password");
		lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEnterPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterPassword.setBounds(22, 11, 173, 22);
		
		panel_4.add(lblEnterPassword);
		
		panel_4.add(passwordField);
		
		panel_4.add(lblProceed);
		return panel_4;
	}
}
