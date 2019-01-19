package login;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import javax.swing.border.MatteBorder;

public class Animation2 {

	public JFrame frame;
	private JLabel lblCill;
	private JPasswordField passwordField;
	private JComboBox<String> comboBox;
	public static String userName="admin";
	private JPanel panel;
	private JLabel label;
	private boolean animationRunning=false;
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
						Animation2 window = new Animation2();
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
	public Animation2() {
		initialize();
		userSetter();
	}
	
	public void userSetter()
	{
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
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setResizable(false);
		frame.setBounds(100, 100, 638, 402);
		frame.setLocationRelativeTo(null);
		//frame.setIconImage(Functions.setIcon());
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new MatteBorder(3, 0, 3, 0, (Color) new Color(0, 0, 0)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(417, 230, 179, 132);
		frame.getContentPane().add(panel);
		panel.setVisible(false);
		panel.setLayout(null);
		
		lblCill = new JLabel("CILL");
		lblCill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CillAnimation();
			}
		});
		lblCill.setFont(new Font("Viner Hand ITC", Font.BOLD, 81));
		lblCill.setHorizontalAlignment(SwingConstants.CENTER);
		lblCill.setForeground(new Color(255, 255, 255));
		lblCill.setBounds(0, 36, 638, 152);
		frame.getContentPane().add(lblCill);
		
		comboBox = new JComboBox<String>();
		comboBox.setForeground(new Color(0, 0, 0));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(20, 30, 138, 25);
		panel.add(comboBox);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(20, 76, 138, 28);
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
		panel.add(passwordField);
		
		label = new JLabel("\u00A9LAB Symbiotic");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(0, 0, 0));
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		label.setBounds(222, 345, 148, 17);
		label.setVisible(false);
		frame.getContentPane().add(label);
		
	}
	
	//************************************************************** Method of animation in starting*********************************************************
	
			public void titleAnimation(){
				Runnable run=new Runnable(){
					public void run(){
						if(animationRunning==false)
						{
							animationRunning=true;
							int a=255;
							int b=255;
							int c=255;
							try
							{
								frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								TimeUnit.MILLISECONDS.sleep(100);
								for (int index=0;index<20;index++)
								{
									lblCill.setForeground(new Color(a, b, c));
									TimeUnit.MILLISECONDS.sleep(200);
									a=a-13;
									b=b-13;
									c=c-13;
								}
								TimeUnit.MILLISECONDS.sleep(100);
								panel.setVisible(true);
								TimeUnit.MILLISECONDS.sleep(200);
								label.setVisible(true);
							}
							 catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
							}
							animationRunning=false;
						}
					}
				};
				Thread tr=new Thread(run);
				tr.start();
			}
			
			public void CillAnimation(){
				Runnable run=new Runnable(){
					public void run(){
						if(animationRunning==false)
						{
							animationRunning=true;
							int a=255;
							int b=255;
							int c=255;
							try
							{
								frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								TimeUnit.MILLISECONDS.sleep(100);
								for (int index=0;index<20;index++)
								{
									lblCill.setForeground(new Color(a, b, c));
									TimeUnit.MILLISECONDS.sleep(200);
									a=a-13;
									b=b-13;
									c=c-13;
								}
								
							}
							 catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
							}
							animationRunning=false;
						}
						
					}
				};
				Thread tr=new Thread(run);
				tr.start();
			}
}
