package accounts;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import menu.Menu;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccountWindow {

	public JFrame frame;
	private JTable table;
	private JTextField txtAmount;
	private JLabel lblNewLabel_1;
	private JTable table_1;

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

	/**
	 * Create the application.
	 */
	public AccountWindow() {
		initialize();
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
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Menu go=new Menu();
				go.frame.setVisible(true);
			}
		});
		frame.setResizable(false);
		frame.setTitle("Accounts");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 868, 440);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		tabbedPane.addTab("Collection", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 478, 390);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"      Date", " Amount","Source info"
				}
			));
		scrollPane.setViewportView(table);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(530, 11, 323, 390);
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
		
		JTextPane txtpnWriteDownThe = new JTextPane();
		txtpnWriteDownThe.setText("write down the source of collection here");
		txtpnWriteDownThe.setBounds(44, 131, 240, 178);
		panel_3.add(txtpnWriteDownThe);
		
		lblNewLabel_1 = new JLabel("Save!");
		lblNewLabel_1.setBackground(new Color(210, 180, 140));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
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
		lblNewLabel_1.setFont(new Font("Estrangelo Edessa", Font.BOLD, 17));
		lblNewLabel_1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(44, 320, 240, 35);
		panel_3.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 99, 71));
		separator.setForeground(new Color(255, 69, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(508, 11, 4, 390);
		panel.add(separator);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Academic fees", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 250, 250));
		tabbedPane.addTab("Revenue & Expenses", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 271, 390);
		panel_2.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"      Date", " Amount"
				}
			));
		scrollPane_1.setViewportView(table_1);
		frame.setLocationRelativeTo(null);
		frame.setBounds(100, 100, 1075, 490);
	}
}
