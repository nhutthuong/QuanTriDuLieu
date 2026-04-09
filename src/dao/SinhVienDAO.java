package dao;

import model.SinhVien;
import util.MySQLConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO {

    // Hàm lấy toàn bộ danh sách sinh viên từ CSDL
    public List<SinhVien> layDanhSachSinhVien() {
        // Khởi tạo một danh sách rỗng để chứa dữ liệu
        List<SinhVien> dsSinhVien = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            // Lấy kết nối từ class MySQLConnect
            conn = MySQLConnect.getConnection();

            // Viết câu truy vấn SQL
            String sql = "SELECT * FROM SINHVIEN";

            // Chuẩn bị câu lệnh
            pStmt = conn.prepareStatement(sql);

            // Thực thi và nhận kết quả trả về
            rs = pStmt.executeQuery();

            // Duyệt qua từng dòng dữ liệu trong ResultSet 
            while (rs.next()) {
                SinhVien sv = new SinhVien();
                // Ánh xạ từng cột trong CSDL vào thuộc tính của Object SinhVien
                sv.setMssv(rs.getString("MSSV"));
                sv.setHoTen(rs.getString("HoTen"));
                sv.setGioiTinh(rs.getString("GioiTinh"));
                sv.setNgaySinh(rs.getDate("NgaySinh"));
                sv.setNoiSinh(rs.getString("NoiSinh"));
                sv.setDiaChi(rs.getString("DiaChi"));
                sv.setMaKhoa(rs.getString("MaKhoa"));

                // Thêm đối tượng sinh viên vừa tạo vào danh sách
                dsSinhVien.add(sv);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi khi lấy danh sách sinh viên: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // giải phóng tài nguyên khi không sử dụng nữa
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return dsSinhVien;
    }
    
    // Hàm tìm kiếm sinh viên theo MSSV hoặc Tên (nếu tuKhoa rỗng, sẽ lấy tất cả)
    public List<SinhVien> timKiemSinhVien(String tuKhoa) {
        List<SinhVien> dsSinhVien = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnect.getConnection();
            // Câu lệnh SQL tìm kiếm gần đúng bằng LIKE trên cả MSSV và HoTen
            String sql = "SELECT * FROM SINHVIEN WHERE MSSV LIKE ? OR HoTen LIKE ?";
            pStmt = conn.prepareStatement(sql);
            
            // Gắn chuỗi tìm kiếm 
            String searchPattern = "%" + tuKhoa + "%";
            pStmt.setString(1, searchPattern);
            pStmt.setString(2, searchPattern);

            rs = pStmt.executeQuery();

            while (rs.next()) {
                SinhVien sv = new SinhVien();
                sv.setMssv(rs.getString("MSSV"));
                sv.setHoTen(rs.getString("HoTen"));
                sv.setGioiTinh(rs.getString("GioiTinh"));
                sv.setNgaySinh(rs.getDate("NgaySinh"));
                sv.setNoiSinh(rs.getString("NoiSinh"));
                sv.setDiaChi(rs.getString("DiaChi"));
                sv.setMaKhoa(rs.getString("MaKhoa"));
                dsSinhVien.add(sv);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi truy vấn tìm kiếm: " + ex.getMessage());
        } finally {
            // Giải phóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsSinhVien;
    }
    
    // Hàm gọi Function MySQL để đếm số lượng sinh viên
    public int demSoLuongSinhVienTheoKhoa(String maKhoa) {
        int soLuong = 0;
        Connection conn = null;
        CallableStatement cStmt = null; // Dùng CallableStatement để gọi Function/Procedure

        try {
            conn = util.MySQLConnect.getConnection();
            
            // Gọi Function trong JDBC: {? = call ten_ham(?)}
            // Dấu ? đầu tiên là giá trị trả về của hàm (RETURN)
            // Dấu ? thứ hai là tham số đầu vào (p_MaKhoa)
            String sql = "{? = call ThongKeSoLuongSVKhoa(?)}";
            cStmt = conn.prepareCall(sql);
            
            // Đăng ký kiểu dữ liệu cho kết quả trả về (vị trí số 1)
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            
            // Truyền tham số đầu vào (vị trí số 2)
            cStmt.setString(2, maKhoa);
            
            // Thực thi
            cStmt.execute();
            
            // Nhận kết quả từ vị trí số 1
            soLuong = cStmt.getInt(1);

        } catch (SQLException ex) {
            System.out.println("Lỗi khi gọi Function thống kê: " + ex.getMessage());
        } finally {
            try {
                if (cStmt != null) cStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return soLuong;
    }

    // Hàm lấy danh sách sinh viên lọc theo mã khoa
    public List<SinhVien> layDanhSachSinhVienTheoKhoa(String maKhoa) {
        List<SinhVien> ds = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            conn = util.MySQLConnect.getConnection();
            String sql = "SELECT * FROM SINHVIEN WHERE MaKhoa = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, maKhoa);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                SinhVien sv = new SinhVien();
                sv.setMssv(rs.getString("MSSV"));
                sv.setHoTen(rs.getString("HoTen"));
                sv.setGioiTinh(rs.getString("GioiTinh"));
                sv.setNgaySinh(rs.getDate("NgaySinh"));
                sv.setNoiSinh(rs.getString("NoiSinh"));
                sv.setDiaChi(rs.getString("DiaChi"));
                sv.setMaKhoa(rs.getString("MaKhoa"));
                ds.add(sv);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ds;
    }
    public boolean capNhatSinhVien(SinhVien sv) {
    String sql = "{call sp_CapNhatSinhVien(?,?,?,?,?,?,?,?)}";
    try (Connection conn = MySQLConnect.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {
        
        cstmt.setString(1, sv.getMssv());
        cstmt.setString(2, sv.getHoTen());
        cstmt.setString(3, sv.getGioiTinh());
        cstmt.setDate(4, new java.sql.Date(sv.getNgaySinh().getTime()));
        cstmt.setString(5, sv.getNoiSinh());
        cstmt.setString(6, sv.getDiaChi());
        cstmt.setString(7, sv.getMaKhoa());
        
        cstmt.registerOutParameter(8, java.sql.Types.INTEGER);
        
        cstmt.execute();
        return cstmt.getInt(8) == 1;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public int xoaSinhVienTransaction(String mssv) {
    Connection conn = null;
    CallableStatement cStmt = null;
    int ketQua = 0;

    try {
        conn = util.MySQLConnect.getConnection();
        String sql = "{call sp_XoaSinhVienTransaction(?, ?)}";
        cStmt = conn.prepareCall(sql);

        cStmt.setString(1, mssv);
        cStmt.registerOutParameter(2, java.sql.Types.INTEGER);

        cStmt.execute();
        ketQua = cStmt.getInt(2);

    } catch (SQLException ex) {
        System.out.println("Lỗi JDBC Xóa: " + ex.getMessage());
        ketQua = -1;
    } finally {
        try {
            if (cStmt != null) cStmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    return ketQua;
}   
    public int themSinhVien(SinhVien sv) {
    Connection conn = null;
    CallableStatement cStmt = null;
    int ketQua = 0;

    try {
        conn = util.MySQLConnect.getConnection();
        String sql = "{call sp_ThemSinhVienHoaChuDau(?,?,?,?,?,?,?,?)}";
        cStmt = conn.prepareCall(sql);

        cStmt.setString(1, sv.getMssv());
        cStmt.setString(2, sv.getHoTen());
        cStmt.setString(3, sv.getGioiTinh());
        cStmt.setDate(4, new java.sql.Date(sv.getNgaySinh().getTime()));
        cStmt.setString(5, sv.getNoiSinh());
        cStmt.setString(6, sv.getDiaChi());
        cStmt.setString(7, sv.getMaKhoa());
        
        cStmt.registerOutParameter(8, java.sql.Types.INTEGER);

        cStmt.execute();
        ketQua = cStmt.getInt(8);

    } catch (SQLException ex) {
        System.out.println("Lỗi JDBC Thêm: " + ex.getMessage());
        ketQua = -1;
    } finally {
        try {
            if (cStmt != null) cStmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    return ketQua;
}
}