package login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import connections.sqlConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class Login {

	public JFrame frame;
	private JDesktopPane desktopPane;
	JMenuBar jmb;
	JMenu logins;
	JLabel label;
	JMenuItem menuItems;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
		backAnimation();
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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		
		frame.setTitle("Login");
		frame.getContentPane().setBackground(new Color(0, 153, 153));
		frame.setBackground(Color.WHITE);
		frame.setSize(new Dimension(screenSize.width,screenSize.height));
		frame.getContentPane().setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		jmb=new JMenuBar();
		frame.setJMenuBar(jmb);
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(245, 245, 220));
		desktopPane.setBounds(0, 0, 1366, 747);
		
		frame.getContentPane().add(desktopPane);
		
		JInternalFrame internalFrame = new JInternalFrame("Registration");
		internalFrame.setForeground(Color.DARK_GRAY);
		internalFrame.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		internalFrame.setBounds(48, 44, 735, 388);
		desktopPane.add(internalFrame);
		internalFrame.getContentPane().setLayout(null);
		
		label = new JLabel("Photo");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		label.setBounds(25, 81, 147, 158);
		internalFrame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setText("name");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.GRAY);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField.setColumns(10);
		textField.setBounds(194, 127, 499, 30);
		internalFrame.getContentPane().add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText("cell");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setForeground(Color.GRAY);
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_1.setColumns(10);
		textField_1.setBounds(194, 168, 237, 30);
		internalFrame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setText("address");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setForeground(Color.GRAY);
		textField_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_2.setColumns(10);
		textField_2.setBounds(194, 209, 499, 30);
		internalFrame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("email");
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setForeground(Color.GRAY);
		textField_3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_3.setColumns(10);
		textField_3.setBounds(456, 168, 237, 30);
		internalFrame.getContentPane().add(textField_3);
		internalFrame.setVisible(true);
		logins=new JMenu("Logins");
		menuItems=new JMenuItem("PTExaminer");
		menuItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "working");
			}
		});
		logins.add(menuItems);
		jmb.add(logins);
		
		JMenuItem mntmExit = new JMenuItem("Exit!");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		logins.add(mntmExit);
	}
	
	public void backAnimation(){
		Runnable run=new Runnable(){
			public void run(){
				for (int i=0;i<i+1;i++)
				{
					if(i==1)
					{
						label.setIcon((Icon) sqlConnection.setIcon());
					}
					else if(i==2)
					{
						label.setIcon( (Icon) sqlConnection.setIcon2());
						i=0;
					}
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
		};
		Thread design=new Thread(run);
		design.start();
	}
}
