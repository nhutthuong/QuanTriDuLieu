package dao;

import util.MySQLConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class DangKyDAO {

    // Hàm gọi Procedure Nhập điểm
    public int nhapDiemSinhVien(String mssv, String maHP, String maHK, float diem) {
        int ketQua = -1; // -1 là lỗi hệ thống, 0 là chưa đăng ký, 1 là thành công
        Connection conn = null;
        CallableStatement cStmt = null;

        try {
            conn = MySQLConnect.getConnection();
            
            // Cú pháp gọi Procedure có tham số OUT: {call ten_thu_tuc(?, ?, ?, ?, ?)}
            String sql = "{call NhapDiemThi(?, ?, ?, ?, ?)}";
            cStmt = conn.prepareCall(sql);

            // Truyền các tham số IN
            cStmt.setString(1, mssv);
            cStmt.setString(2, maHP);
            cStmt.setString(3, maHK);
            cStmt.setFloat(4, diem);

            // Đăng ký tham số OUT (vị trí số 5, kiểu INT) để nhận kết quả trả về
            cStmt.registerOutParameter(5, Types.INTEGER);

            // Thực thi Procedure
            cStmt.execute();

            // Lấy giá trị từ tham số OUT
            ketQua = cStmt.getInt(5);

        } catch (SQLException ex) {
            System.out.println("Lỗi khi gọi Procedure nhập điểm: " + ex.getMessage());
        } finally {
            try {
                if (cStmt != null) cStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ketQua;
    }
    
    //Hàm đăng ký nhiều môn học cho một học kỳ, niên khóa cụ thể
    public boolean dangKyNhieuMon(String mssv, String maHK_NK, List<String> danhSachMaHP) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        boolean isSuccess = false;

        try {
            conn = MySQLConnect.getConnection();
            
            // Tắt tự động lưu (Bắt đầu Giao dịch)
            conn.setAutoCommit(false); 

            // Chuẩn bị lệnh (Cột Diem tự động NULL do chưa thi)
            String sql = "INSERT INTO DANGKY (MSSV, MaHP, MaHK_NK, NgayDangKy) VALUES (?, ?, ?, CURDATE())";
            pStmt = conn.prepareStatement(sql);

            // Đưa từng môn học vào "Lô" (Batch)
            for (String maHP : danhSachMaHP) {
                pStmt.setString(1, mssv.trim());
                pStmt.setString(2, maHP.trim()); 
                pStmt.setString(3, maHK_NK.trim());
                
                pStmt.addBatch(); // Thêm vào Lô
            }

            // Chạy toàn bộ Lô lệnh
            pStmt.executeBatch(); 

            // Thành công -> Xác nhận lưu (Commit)
            conn.commit();
            isSuccess = true;

        } catch (SQLException ex) {
            System.out.println("\n[LỖI CSDL] Quá trình đăng ký thất bại do: " + ex.getMessage());
            // Có lỗi (VD: sai mã môn) -> Hoàn tác toàn bộ (Rollback)
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("HỆ THỐNG ĐÃ ROLLBACK: Hủy đăng ký toàn bộ các môn để bảo vệ dữ liệu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            //Giải phóng tài nguyên
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }
    
    //Xóa học phần đã đăng ký
    public int xoaDangKyHocPhan(String mssv, String maHP, String maHK) {
        int ketQua = -1; // Mặc định là lỗi hệ thống
        Connection conn = null;
        CallableStatement cStmt = null;

        try {
            conn = util.MySQLConnect.getConnection();
            
            // Gọi Stored Procedure (3 tham số IN, 1 tham số OUT)
            String sql = "{call sp_XoaDangKyHocPhan(?, ?, ?, ?)}";
            cStmt = conn.prepareCall(sql);

            // Truyền tham số
            cStmt.setString(1, mssv);
            cStmt.setString(2, maHP);
            cStmt.setString(3, maHK);
            
            // Đăng ký tham số nhận kết quả trả về
            cStmt.registerOutParameter(4, java.sql.Types.INTEGER);

            // Thực thi
            cStmt.execute();

            // Lấy mã kết quả từ tham số OUT thứ 4
            ketQua = cStmt.getInt(4);

        } catch (SQLException ex) {
            System.out.println("Lỗi gọi thủ tục xóa học phần: " + ex.getMessage());
        } finally {
            try {
                if (cStmt != null) cStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return ketQua;
    }
}