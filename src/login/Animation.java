package login;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import connections.sqlConnection;
import menu.Menu;

public class Animation {

	public JFrame frame;
	private JPanel panel1;
	private JPanel panel8;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel6;
	private JPanel panel7;
	private JLabel label7;
	private JLabel label6;
	private JLabel label5;
	private JLabel label2;
	private JLabel label3;
	private JLabel label1;
	private JLabel label4;
	private JLabel lblManager5;
	private JLabel lblManager4;
	private JLabel lblManager3;
	private JLabel lblManager2;
	private JLabel lblManager1;
	private JPasswordField passwordField;
	private JPanel panel;
	private JComboBox<String> comboBox;
	public static String userName="admin";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File f=new File("License.ser");
					Date startDay=new SimpleDateFormat("DD-MM-YYYY").parse("17-01-2019");
					Date expireDate=new SimpleDateFormat("DD-MM-YYYY").parse("31-01-2019");
					if(startDay.before(new Date()) && expireDate.before(new Date())&& f.exists())
					{
						Animation window = new Animation();
						window.frame.setVisible(true);
						window.titleAnimation();
					}
					else
						JOptionPane.showMessageDialog(null,"Demo version's license has been expired!..");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Animation() {
		initialize();
		userSetter();
		
	}
	
	public void userSetter()
	{
		comboBox.removeAllItems();
		try {
			Connection conn=sqlConnection.dbConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT name FROM users;");
			while(rs.next())
			{
				comboBox.insertItemAt(rs.getString("name"),comboBox.getModel().getSize());
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		frame.getContentPane().setBackground(SystemColor.textHighlightText);
		frame.setResizable(false);
		frame.setBounds(100, 100, 638, 356);
		frame.setLocationRelativeTo(null);
		//frame.setIconImage(Functions.setIcon());
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel1 = new JPanel();
		panel1.setBackground(new Color(205, 92, 92));
		panel1.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		panel1.setBounds(0, 0, 79, 176);
		frame.getContentPane().add(panel1);
		panel1.setLayout(null);
		
		label1 = new JLabel("");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(new Font("Comic Sans MS", Font.BOLD, 87));
		label1.setBounds(10, 30, 59, 118);
		panel1.add(label1);
		
		panel2 = new JPanel();
		panel2.setBackground(new Color(255, 160, 122));
		panel2.setLayout(null);
		panel2.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		panel2.setBounds(80, 0, 79, 176);
		frame.getContentPane().add(panel2);
		
		label2 = new JLabel("");
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setFont(new Font("Comic Sans MS", Font.BOLD, 57));
		label2.setBounds(10, 30, 59, 118);
		panel2.add(label2);
		
		panel3 = new JPanel();
		panel3.setBackground(new Color(255, 222, 173));
		panel3.setLayout(null);
		panel3.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		panel3.setBounds(160, 0, 79, 176);
		frame.getContentPane().add(panel3);
		
		label3 = new JLabel("");
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setFont(new Font("Comic Sans MS", Font.BOLD, 43));
		label3.setBounds(10, 30, 59, 118);
		panel3.add(label3);
		
		panel4 = new JPanel();
		panel4.setBackground(new Color(250, 250, 210));
		panel4.setLayout(null);
		panel4.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		panel4.setBounds(240, 0, 158, 176);
		frame.getContentPane().add(panel4);
		
		label4 = new JLabel("");
		label4.setHorizontalAlignment(SwingConstants.CENTER);
		label4.setFont(new Font("Comic Sans MS", Font.BOLD, 43));
		label4.setBounds(10, 0, 138, 176);
		panel4.add(label4);
		
		panel8 = new JPanel();
		panel8.setBackground(new Color(205, 92, 92));
		panel8.setLayout(null);
		panel8.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		panel8.setBounds(559, 0, 79, 176);
		frame.getContentPane().add(panel8);
		
		label7 = new JLabel("");
		label7.setHorizontalAlignment(SwingConstants.CENTER);
		label7.setFont(new Font("Comic Sans MS", Font.BOLD, 87));
		label7.setBounds(10, 30, 59, 118);
		panel8.add(label7);
		
		panel7 = new JPanel();
		panel7.setBackground(new Color(255, 160, 122));
		panel7.setLayout(null);
		panel7.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		panel7.setBounds(479, 0, 79, 176);
		frame.getContentPane().add(panel7);
		
		label6 = new JLabel("");
		label6.setHorizontalAlignment(SwingConstants.CENTER);
		label6.setFont(new Font("Comic Sans MS", Font.BOLD, 77));
		label6.setBounds(10, 30, 59, 118);
		panel7.add(label6);
		
		panel6 = new JPanel();
		panel6.setBackground(new Color(255, 222, 173));
		panel6.setLayout(null);
		panel6.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		panel6.setBounds(399, 0, 79, 176);
		frame.getContentPane().add(panel6);
		
		label5 = new JLabel("");
		label5.setHorizontalAlignment(SwingConstants.CENTER);
		label5.setFont(new Font("Comic Sans MS", Font.BOLD, 43));
		label5.setBounds(10, 30, 59, 118);
		panel6.add(label5);
		
		lblManager5 = new JLabel("");
		lblManager5.setBackground(SystemColor.textHighlightText);
		lblManager5.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
		lblManager5.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager5.setOpaque(true);
		lblManager5.setBounds(240, 179, 158, 32);
		frame.getContentPane().add(lblManager5);
		
		lblManager4 = new JLabel("");
		lblManager4.setBackground(SystemColor.textHighlightText);
		lblManager4.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
		lblManager4.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager4.setOpaque(true);
		lblManager4.setBounds(240, 211, 158, 32);
		frame.getContentPane().add(lblManager4);
		
		lblManager3 = new JLabel("");
		lblManager3.setBackground(SystemColor.textHighlightText);
		lblManager3.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
		lblManager3.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager3.setOpaque(true);
		lblManager3.setBounds(240, 242, 158, 32);
		frame.getContentPane().add(lblManager3);
		
		lblManager2 = new JLabel("");
		lblManager2.setBackground(SystemColor.textHighlightText);
		lblManager2.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
		lblManager2.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager2.setBounds(240, 274, 158, 32);
		lblManager2.setOpaque(true);
		frame.getContentPane().add(lblManager2);
		
		lblManager1 = new JLabel("");
		lblManager1.setBackground(SystemColor.textHighlightText);
		lblManager1.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
		lblManager1.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager1.setOpaque(true);
		lblManager1.setBounds(240, 307, 158, 32);
		frame.getContentPane().add(lblManager1);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 0, 0), 2, true));
		panel.setBackground(SystemColor.info);
		panel.setBounds(433, 199, 179, 132);
		panel.setLayout(null);
		panel.setVisible(false);
		frame.getContentPane().add(panel);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"User 1", "User 2", "User 3", "Admin"}));
		comboBox.setBounds(20, 32, 138, 25);
		panel.add(comboBox);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
				{
					
					File f=new File("License.ser");
					if(f.exists())
					{
						
						if(comboBox.getSelectedItem().toString().equalsIgnoreCase("admin")||comboBox.getSelectedItem().toString().equalsIgnoreCase("desk"))
						{
							Connection newConnection=sqlConnection.dbConnection();
							try {
								ResultSet rs = newConnection.createStatement().executeQuery("SELECT * FROM users;");
								boolean matched=false;
								while(rs.next())
								{
										//rs.getString("password").equalsIgnoreCase(new String(passwordField.getPassword()))
										if(new String(passwordField.getPassword()).length()!=0)
										{
											matched=true;
											userName=comboBox.getSelectedItem().toString();
											frame.dispose();
											Menu go=new Menu();
											go.frame.setVisible(true);
										}
								}
								rs.close();
								newConnection.close();
								if(!matched)
								{
									JOptionPane.showMessageDialog(frame,"Wrong password");
								}
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null,"Database error!");
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
		passwordField.setBounds(20, 76, 138, 25);
		panel.add(passwordField);
		
	}
	
	//************************************************************** Method of animation in starting*********************************************************
	
			public void titleAnimation(){
				Runnable run=new Runnable(){
					public void run(){
						
						frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						for (int index=0;index<5;index++)
						{
							if(index==0)
							{
								label1.setText("C");
								label7.setText("L");
								label2.setText("I");
								label6.setText("L");
							}
							else if(index==1)
							{
								label1.setText("");
								label2.setFont(new Font("Comic Sans MS", Font.BOLD, 57));
								label2.setText("C");
								label7.setText("");
								label6.setFont(new Font("Comic Sans MS", Font.BOLD, 57));
								label6.setText("L");
								label3.setText("I");
								label5.setText("L");
							}
							else if(index==2)
							{
								label1.setText("");
								label2.setText("");
								label3.setText("");
								label5.setText("");
								label6.setText("");
								label7.setText("");
								label4.setText("CILL");
								break;
							}
							try {
								TimeUnit.MILLISECONDS.sleep(400);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						titleAnimation2();
						
					}
				};
				Thread tr=new Thread(run);
				tr.start();
			}
			
			//************************************************************** Method of animation in starting*********************************************************
			
			public void titleAnimation2(){
				Runnable run=new Runnable(){
					public void run(){
						
						frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						for (int i=0;i<6;i++)
						{
							if(i==0)
							{
								lblManager1.setText("Manager");
							}
							else if(i==1)
							{
								lblManager1.setText("");
								lblManager3.setText("Manager");
							}
							else if(i==2)
							{
								lblManager3.setText("");
								lblManager2.setText("Manager");
							}
							else if(i==3)
							{
								lblManager2.setText("");
								lblManager4.setText("Manager");
							}
							else if(i==4)
							{
								lblManager4.setText("");
								lblManager3.setText("Manager");
							}
							else if(i==5)
							{
								lblManager3.setText("");
								lblManager5.setText("Manager");
								label4.setVerticalAlignment(SwingConstants.BOTTOM);
							}
							try {
								TimeUnit.MILLISECONDS.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						try {
							TimeUnit.MILLISECONDS.sleep(500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						panel.setVisible(true);
						lblManager1.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
						lblManager1.setText("Powered by Lab Symbiotic");
					}
				};
				Thread tr=new Thread(run);
				tr.start();
			}
}
