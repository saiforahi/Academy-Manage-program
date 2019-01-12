package menu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import accounts.AccountWindow;
import employees.EmployeeWindow;
import registration.Registration;

public class Menu {

	public JFrame frame;
	private JLabel lblAdmission;
	private JLabel lblAccounts;
	private JLabel lblEmployees;
	private JLabel lblCiller;
	public static String userName=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
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
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		userName=new String();
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.textHighlightText);
		frame.setResizable(false);
		frame.setBounds(100, 100, 532, 441);
		frame.setLocationRelativeTo(null);
		//frame.setIconImage(Functions.setIcon());
		
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(25, 25, 112));
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(95, 158, 160), new Color(192, 192, 192)));
		panel.setBounds(0, 0, 264, 217);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblAdmission = new JLabel("Academi");
		lblAdmission.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				Registration go=new Registration();
				go.frame.setVisible(true);
				go.frame.setLocationRelativeTo(null);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblAdmission.setFont(new Font("Nyala", Font.BOLD, 34));
				lblAccounts.setFont(new Font("Nyala", Font.BOLD, 24));
				lblCiller.setFont(new Font("Nyala", Font.BOLD, 24));
				lblEmployees.setFont(new Font("Nyala", Font.BOLD, 24));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				lblAdmission.setFont(new Font("Nyala", Font.BOLD, 24));
			}
		});
		lblAdmission.setOpaque(true);
		lblAdmission.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdmission.setForeground(Color.WHITE);
		lblAdmission.setFont(new Font("Nyala", Font.BOLD, 24));
		lblAdmission.setBorder(new LineBorder(new Color(255, 255, 255), 4));
		lblAdmission.setBackground(new Color(70, 130, 180));
		lblAdmission.setBounds(40, 35, 186, 147);
		panel.add(lblAdmission);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(25, 25, 112));
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(95, 158, 160), new Color(192, 192, 192)));
		panel_1.setBounds(268, 0, 264, 217);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblAccounts = new JLabel("Accounts");
		lblAccounts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				AccountWindow go=new AccountWindow();
				go.frame.setVisible(true);
				go.frame.setLocationRelativeTo(null);;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				lblAdmission.setFont(new Font("Nyala", Font.BOLD, 24));
				lblAccounts.setFont(new Font("Nyala", Font.BOLD, 24));
				lblCiller.setFont(new Font("Nyala", Font.BOLD, 24));
				lblEmployees.setFont(new Font("Nyala", Font.BOLD, 24));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblAdmission.setFont(new Font("Nyala", Font.BOLD, 24));
				lblAccounts.setFont(new Font("Nyala", Font.BOLD, 34));
				lblCiller.setFont(new Font("Nyala", Font.BOLD, 24));
				lblEmployees.setFont(new Font("Nyala", Font.BOLD, 24));
			}
		});
		lblAccounts.setOpaque(true);
		lblAccounts.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccounts.setForeground(Color.WHITE);
		lblAccounts.setFont(new Font("Nyala", Font.BOLD, 24));
		lblAccounts.setBorder(new LineBorder(new Color(255, 255, 255), 4));
		lblAccounts.setBackground(new Color(70, 130, 180));
		lblAccounts.setBounds(39, 36, 187, 147);
		panel_1.add(lblAccounts);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(25, 25, 112));
		panel_3.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(95, 158, 160), new Color(192, 192, 192)));
		panel_3.setBounds(0, 223, 264, 218);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		lblEmployees = new JLabel("Employees");
		lblEmployees.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				EmployeeWindow go=new EmployeeWindow();
				go.frame.setVisible(true);
				go.frame.setLocationRelativeTo(null);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				lblAdmission.setFont(new Font("Nyala", Font.BOLD, 24));
				lblAccounts.setFont(new Font("Nyala", Font.BOLD, 24));
				lblCiller.setFont(new Font("Nyala", Font.BOLD, 24));
				lblEmployees.setFont(new Font("Nyala", Font.BOLD, 24));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblAdmission.setFont(new Font("Nyala", Font.BOLD, 24));
				lblAccounts.setFont(new Font("Nyala", Font.BOLD, 24));
				lblCiller.setFont(new Font("Nyala", Font.BOLD, 24));
				lblEmployees.setFont(new Font("Nyala", Font.BOLD, 34));
			}
		});
		lblEmployees.setBackground(new Color(70, 130, 180));
		lblEmployees.setBounds(39, 38, 186, 147);
		panel_3.add(lblEmployees);
		lblEmployees.setOpaque(true);
		lblEmployees.setBorder(new LineBorder(new Color(255, 255, 255), 4));
		lblEmployees.setForeground(Color.WHITE);
		lblEmployees.setFont(new Font("Nyala", Font.BOLD, 24));
		lblEmployees.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(25, 25, 112));
		panel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(95, 158, 160), new Color(192, 192, 192)));
		panel_2.setBounds(268, 223, 264, 218);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		lblCiller = new JLabel("CILLer");
		lblCiller.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Under Development");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				lblAdmission.setFont(new Font("Nyala", Font.BOLD, 24));
				lblAccounts.setFont(new Font("Nyala", Font.BOLD, 24));
				lblCiller.setFont(new Font("Nyala", Font.BOLD, 24));
				lblEmployees.setFont(new Font("Nyala", Font.BOLD, 24));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblAdmission.setFont(new Font("Nyala", Font.BOLD, 24));
				lblAccounts.setFont(new Font("Nyala", Font.BOLD, 24));
				lblCiller.setFont(new Font("Nyala", Font.BOLD, 34));
				lblEmployees.setFont(new Font("Nyala", Font.BOLD, 24));
			}
		});
		lblCiller.setOpaque(true);
		lblCiller.setHorizontalAlignment(SwingConstants.CENTER);
		lblCiller.setForeground(Color.WHITE);
		lblCiller.setFont(new Font("Nyala", Font.BOLD, 24));
		lblCiller.setBorder(new LineBorder(new Color(255, 255, 255), 4));
		lblCiller.setBackground(new Color(70, 130, 180));
		lblCiller.setBounds(40, 39, 187, 147);
		panel_2.add(lblCiller);
	}
}
