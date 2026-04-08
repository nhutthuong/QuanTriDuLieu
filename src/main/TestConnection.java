package main;

import dao.SinhVienDAO;
import model.SinhVien;
import java.util.List;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("--- TEST KẾT NỐI VÀ LẤY DỮ LIỆU TỪ MYSQL ---");
        
        // Khởi tạo đối tượng DAO
        SinhVienDAO dao = new SinhVienDAO();
        
        // Gọi hàm lấy danh sách
        List<SinhVien> danhSach = dao.layDanhSachSinhVien();
        
        // Kiểm tra và in kết quả
        if (danhSach.isEmpty()) {
            System.out.println("Không có sinh viên nào trong CSDL, hoặc kết nối thất bại!");
        } else {
            System.out.println("Lấy dữ liệu thành công! Danh sách sinh viên:");
            for (SinhVien sv : danhSach) {
                // In ra nhờ hàm toString() đã viết trong lớp model.SinhVien
                System.out.println(sv.toString() + " - " + sv.getNgaySinh());
            }
        }
    }
}