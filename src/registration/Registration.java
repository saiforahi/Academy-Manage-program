package registration;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
	DefaultTableCellRenderer centerRenderer;
	private JComboBox<?> comboBox;
	private JTextField skimField;
	private JTextField downPaymentField;
	private JLabel lblNewLabel_2;
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
		initialize();
	}
	
	public boolean find_learner(String emailAddress)
	{
		
		return false;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		centerRenderer = new DefaultTableCellRenderer();
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
		frame.setLocationRelativeTo(null);
		frame.setBounds(100, 100, 894, 589);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(SystemColor.inactiveCaptionText, 2));
		panel.setBackground(SystemColor.info);
		panel.setBounds(23, 24, 843, 185);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox<Object>();
		comboBox.setBounds(22, 23, 477, 26);
		AutoCompleteFind.enable(comboBox);
		panel.add(comboBox);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(522, 11, 311, 163);
		panel.add(panel_4);
		panel_4.setLayout(null);
		panel_4.setBackground(SystemColor.activeCaptionBorder);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(0, 0, 0, 65));
		scrollPane.setBounds(10, 11, 291, 141);
		panel_4.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"      Date", " Installment", "    Balance"
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
		lblNewLabel.setBounds(22, 60, 48, 20);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Null");
		lblNewLabel_1.setBounds(22, 84, 477, 90);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(SystemColor.windowBorder, 1, true));
		panel_1.setBackground(SystemColor.info);
		panel_1.setLayout(null);
		panel_1.setBounds(23, 220, 843, 314);
		frame.getContentPane().add(panel_1);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(244, 164, 96));
		panel_3.setBounds(10, 11, 147, 153);
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
		nameField.setBounds(167, 11, 499, 30);
		panel_1.add(nameField);
		
		cellField = new JTextField();
		cellField.setText("cell");
		cellField.setHorizontalAlignment(SwingConstants.CENTER);
		cellField.setForeground(Color.GRAY);
		cellField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cellField.setColumns(10);
		cellField.setBounds(167, 52, 237, 30);
		panel_1.add(cellField);
		
		emailField = new JTextField();
		emailField.setText("email");
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setForeground(Color.GRAY);
		emailField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		emailField.setColumns(10);
		emailField.setBounds(429, 52, 237, 30);
		panel_1.add(emailField);
		
		addressField = new JTextField();
		addressField.setText("address");
		addressField.setHorizontalAlignment(SwingConstants.CENTER);
		addressField.setForeground(Color.GRAY);
		addressField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		addressField.setColumns(10);
		addressField.setBounds(167, 93, 499, 30);
		panel_1.add(addressField);
		
		programField = new JTextField();
		programField.setText("program details");
		programField.setHorizontalAlignment(SwingConstants.CENTER);
		programField.setForeground(Color.GRAY);
		programField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		programField.setColumns(10);
		programField.setBounds(167, 134, 499, 30);
		panel_1.add(programField);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(676, 11, 157, 153);
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
		
		JLabel lblAdd = new JLabel("add");
		lblAdd.setBackground(new Color(144, 238, 144));
		lblAdd.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(nameField.getText()!=null && cellField.getText()!=null && emailField.getText()!=null && addressField.getText()!=null && programField.getText()!=null && skimField.getText()!=null && downPaymentField.getText()!=null && imageLabel.getIcon()!=null)
				{
					
					try {
						Learner newLearner=new Learner(imageLabel.getIcon(),nameField.getText(),cellField.getText(),emailField.getText(),addressField.getText(),programField.getText());
						Connection conn=sqlConnection.dbConnection();
						PreparedStatement pstmt = conn.prepareStatement("INSERT INTO learners(email, name,objects) VALUES (?, ?,?)");
						pstmt.setString(1, emailField.getText());
						pstmt.setString(2,nameField.getText());
						pstmt.setObject(3,newLearner);
						pstmt.executeUpdate();
						pstmt.close();
						conn.close();
						JOptionPane.showMessageDialog(frame, "Learner's information saved!");
					} catch (SQLException e) {
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
		lblAdd.setBounds(287, 240, 60, 30);
		panel_1.add(lblAdd);
		
		JLabel label_2 = new JLabel("delete");
		label_2.setBackground(new Color(255, 160, 122));
		label_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		label_2.setOpaque(true);
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
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
		label_2.setBounds(357, 240, 60, 30);
		panel_1.add(label_2);
		
		JLabel lblUpdate = new JLabel("update");
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
		lblUpdate.setBounds(427, 240, 60, 30);
		panel_1.add(lblUpdate);
		
		JLabel lblClear = new JLabel("clear");
		lblClear.setBackground(new Color(255, 250, 250));
		lblClear.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblClear.setOpaque(true);
		lblClear.addMouseListener(new MouseAdapter() {
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
		lblClear.setHorizontalAlignment(SwingConstants.CENTER);
		lblClear.setBorder(BorderFactory.createLineBorder(Color.black));
		lblClear.setBounds(497, 240, 60, 30);
		panel_1.add(lblClear);
	}
}
