package login;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Animation2 {

	public JFrame frame;
	private JLabel lblCill;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Animation2 window = new Animation2();
					window.frame.setVisible(true);
					window.titleAnimation();
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
		//userSetter();
	}
	
	/*public void userSetter()
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
	}*/

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
		
		lblCill = new JLabel("CILL");
		lblCill.setFont(new Font("Viner Hand ITC", Font.BOLD, 81));
		lblCill.setHorizontalAlignment(SwingConstants.CENTER);
		lblCill.setForeground(new Color(255, 255, 255));
		lblCill.setBounds(0, 0, 638, 198);
		frame.getContentPane().add(lblCill);
		
	}
	
	//************************************************************** Method of animation in starting*********************************************************
	
			public void titleAnimation(){
				Runnable run=new Runnable(){
					public void run(){
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
						
						
					}
				};
				Thread tr=new Thread(run);
				tr.start();
			}
			
			//************************************************************** Method of animation in starting*********************************************************
			
			
}
