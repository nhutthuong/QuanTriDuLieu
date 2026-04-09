package dao;

import model.HocPhan;
import util.MySQLConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.cj.jdbc.CallableStatement;

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
    public boolean capNhatHocPhan(HocPhan hp) {
    Connection conn = null;
    CallableStatement cStmt = null;
    boolean thanhCong = false;

    try {
        conn = util.MySQLConnect.getConnection();
        String sql = "{call sp_CapNhatHocPhan(?,?,?,?,?,?)}";
        cStmt = (CallableStatement) conn.prepareCall(sql);

        cStmt.setString(1, hp.getMaHP());
        cStmt.setString(2, hp.getTenHP());
        cStmt.setInt(3, hp.getSoTinChi());
        cStmt.setInt(4, hp.getSoTietLT());
        cStmt.setInt(5, hp.getSoTietTH());
        cStmt.registerOutParameter(6, java.sql.Types.INTEGER);

        cStmt.execute();
        if (cStmt.getInt(6) == 1) {
            thanhCong = true;
        }
    } catch (SQLException ex) {
        System.out.println("Lỗi JDBC Cập nhật học phần: " + ex.getMessage());
    } finally {
        try {
            if (cStmt != null) cStmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    return thanhCong;
}
    public int xoaHocPhan(String maHP) {
    Connection conn = null;
    CallableStatement cStmt = null;
    int ketQua = 0;

    try {
        conn = util.MySQLConnect.getConnection();
        String sql = "{call sp_XoaHocPhan(?, ?)}";
        cStmt = (CallableStatement) conn.prepareCall(sql);

        cStmt.setString(1, maHP);
        cStmt.registerOutParameter(2, java.sql.Types.INTEGER);

        cStmt.execute();
        ketQua = cStmt.getInt(2);

    } catch (SQLException ex) {
        System.out.println("Lỗi JDBC Xóa học phần: " + ex.getMessage());
        ketQua = -1; // Lỗi hệ thống
    } finally {
        try {
            if (cStmt != null) cStmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    return ketQua;
}

}