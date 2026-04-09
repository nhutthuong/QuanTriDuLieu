package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnect {
    // Thông tin kết nối CSDL
	private static final String DB_URL = "jdbc:mysql://localhost:3306/qlsinhvien";
    private static final String USER_NAME = "root"; 
    private static final String PASSWORD = ""; 

    // Hàm thực hiện nối kết đến MySQL
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Đăng ký driver
        	DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
            // Tạo chuỗi nối kết và khởi tạo đối tượng Connection
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            
            //System.out.println("Nối kết thành công!");
        } catch (Exception ex) {
            System.out.println("Nối kết không thành công!");
            ex.printStackTrace();
        }
        return conn;
    }
}
