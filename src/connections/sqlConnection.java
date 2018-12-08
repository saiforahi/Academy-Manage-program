package connections;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class sqlConnection {
		Connection conn=null;
		
		public static Connection dbConnection(){
			try{
				Class.forName("org.sqlite.JDBC");
				Connection conn=DriverManager.getConnection("jdbc:sqlite:D:\\Workshop\\CILL - Manager\\Data\\CILLDataBase.db");
				//Connection conn=DriverManager.getConnection("jdbc:sqlite:Data\\CILLDataBase.db");
				return conn;
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null,"database problem  "+e.getMessage());
				return null;
			}
		}
		public static Connection answerDBConnection(){
			try{
				Class.forName("org.sqlite.JDBC");
				Connection conn=DriverManager.getConnection("jdbc:sqlite:Answer\\Answers.db");
				//Connection conn=DriverManager.getConnection("jdbc:sqlite::resource:BirdsEyeDatabase.db");
				//JOptionPane.showMessageDialog(null,"database Connected");
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
		 
		 
		 public  void readPicture(int materialId, String filename) {
		        // update sql
		        String selectSQL = "SELECT describeImage FROM test1question WHERE rowid=?";
		        ResultSet rs = null;
		        FileOutputStream fos = null;
		        Connection conn = null;
		        PreparedStatement pstmt = null;
		 
		        try {
		            conn = dbConnection();
		            pstmt = conn.prepareStatement(selectSQL);
		            pstmt.setInt(1, materialId);
		            rs = pstmt.executeQuery();
		 
		            // write binary stream into file
		            File file = new File(filename);
		            fos = new FileOutputStream(file);
		 
		            System.out.println("Writing BLOB to file " + file.getAbsolutePath());
		            while (rs.next()) {
		                InputStream input = rs.getBinaryStream("describeImage");
		               
		                byte[] buffer = new byte[1024];
		                while (input.read(buffer) > 0) {
		                    fos.write(buffer);
		                }
		            }
		        } catch (SQLException | IOException e) {
		            System.out.println(e.getMessage());
		        } finally {
		            try {
		                if (rs != null) {
		                    rs.close();
		                }
		                if (pstmt != null) {
		                    pstmt.close();
		                }
		 
		                if (conn != null) {
		                    conn.close();
		                }
		                if (fos != null) {
		                    fos.close();
		                }
		 
		            } catch (SQLException | IOException e) {
		                System.out.println(e.getMessage());
		            }
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
			 	sqlConnection app = new sqlConnection();
			    app.readPicture(1,"shortQ1.jpg");
		        //app.updatePicture(1, "D://Workshop//SWP//The Eagles - Hotel California Live At the Capital Center (1977).mp3");
		    }
}
