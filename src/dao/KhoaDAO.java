package dao;

import model.Khoa;
import util.MySQLConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhoaDAO {
    
    // Hàm lấy toàn bộ danh sách khoa
    public List<Khoa> layDanhSachKhoa() {
        List<Khoa> dsKhoa = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnect.getConnection();
            String sql = "SELECT * FROM KHOA";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                Khoa khoa = new Khoa();
                khoa.setMaKhoa(rs.getString("MaKhoa"));
                khoa.setTenKhoa(rs.getString("TenKhoa"));
                dsKhoa.add(khoa);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi khi lấy danh sách Khoa: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsKhoa;
    }
}