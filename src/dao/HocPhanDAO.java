package dao;

import model.HocPhan;
import util.MySQLConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HocPhanDAO {

    public boolean themHocPhan(HocPhan hp) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        boolean isSuccess = false;

        try {
            conn = MySQLConnect.getConnection();
            String sql = "INSERT INTO HOCPHAN (MaHP, TenHP, SoTinChi, SoTietLT, SoTietTH) VALUES (?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, hp.getMaHP());
            pStmt.setString(2, hp.getTenHP());
            pStmt.setInt(3, hp.getSoTinChi());
            pStmt.setInt(4, hp.getSoTietLT());
            pStmt.setInt(5, hp.getSoTietTH());

            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }

        } catch (SQLException ex) {
            // Bắt lỗi trùng mã học phần (Primary Key)
            if (ex.getErrorCode() == 1062) {
                System.out.println("LỖI CSDL: Mã học phần '" + hp.getMaHP() + "' đã tồn tại!");
            } else {
                System.out.println("LỖI CSDL: " + ex.getMessage());
            }
        } finally {
            try {
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }
}