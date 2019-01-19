package connections;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class sqlConnection {
		Connection conn=null;
		
		public static Connection dbConnection(){
			try{
				Class.forName("org.sqlite.JDBC");
				Connection conn=DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir") + "\\Data\\CILLDataBase.db");
				return conn;
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null,"database problem  "+e.getMessage());
				return null;
			}
		}
		
		 private byte[] readFile(String file) {
		        ByteArrayOutputStream bos = null;
		        try {
		            File f = new File(file);
		            @SuppressWarnings("resource")
					FileInputStream fis = new FileInputStream(f);
		            byte[] buffer = new byte[1024];
		            bos = new ByteArrayOutputStream();
		            for (int len; (len = fis.read(buffer)) != -1;) {
		                bos.write(buffer, 0, len);
		            }
		        } catch (FileNotFoundException e) {
		            System.err.println(e.getMessage());
		        } catch (IOException e2) {
		            System.err.println(e2.getMessage());
		        }
		        return bos != null ? bos.toByteArray() : null;
		    }
	
		 public void updatePicture(int materialId, String filename) {
		        // update query
		        String updateSQL = "UPDATE test "
		                + "SET audio = ? "
		                + "WHERE num=?";
		 
		        try (Connection conn = dbConnection();
		                PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
		 
		            // set parameters
		            pstmt.setBytes(1, readFile(filename));
		            pstmt.setInt(2, materialId);
		 
		            pstmt.executeUpdate();
		            System.out.println("Stored the file in the BLOB column.");
		 
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		 
		 
		 public  static void setPicture( JLabel label,String name) {
			 try {
					Connection con=sqlConnection.dbConnection();
					ResultSet rs=con.createStatement().executeQuery("SELECT file FROM image_lib WHERE rowid=2;");
					
					if (rs.next())
					{
						byte[] blob=rs.getBytes("file");
						ImageIcon image= new ImageIcon(blob);
						Image im=image.getImage();
						Image myimg=im.getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon icon= new ImageIcon(myimg);
						label.setIcon(icon);
						
					}
					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			
		   }
		 public static Image setIcon(){
				try {
					Connection con=sqlConnection.dbConnection();
					//PreparedStatement pstmt= con.prepareStatement("SELECT describeImage FROM test1question WHERE rowid=?;");
					//pstmt.setInt(1, 1);
					ResultSet rs=con.createStatement().executeQuery("SELECT image FROM image_table WHERE rowid=5;");
					if (rs.next())
					{
						byte[] blob=rs.getBytes("image");
						ImageIcon image= new ImageIcon(blob);
						Image im=image.getImage();
						return im;
					}
					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
				
			}
		 public static Image setIcon2(){
				try {
					Connection con=sqlConnection.dbConnection();
					//PreparedStatement pstmt= con.prepareStatement("SELECT describeImage FROM test1question WHERE rowid=?;");
					//pstmt.setInt(1, 1);
					ResultSet rs=con.createStatement().executeQuery("SELECT image FROM image_table WHERE rowid=6;");
					if (rs.next())
					{
						byte[] blob=rs.getBytes("image");
						ImageIcon image= new ImageIcon(blob);
						Image im=image.getImage();
						return im;
					}
					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
				
			}
		 public static void main(String[] args) {
			 //readPicture(1,"shortQ1.mp3");
		        //app.updatePicture(1, "D://Workshop//SWP//The Eagles - Hotel California Live At the Capital Center (1977).mp3");
		    }
}
