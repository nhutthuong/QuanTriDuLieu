package ui;

import dao.SinhVienDAO;
import dao.KhoaDAO;
import model.SinhVien;
import model.Khoa;

import java.util.List;
import java.util.Scanner;

public class MenuPhanHeA {
    // Khởi tạo DAO để tương tác với Database
    private SinhVienDAO sinhVienDAO = new SinhVienDAO();
    private KhoaDAO khoaDAO = new KhoaDAO();

    // Nhận Scanner từ ConsoleApp truyền qua 
    public void hienThiMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- PHÂN HỆ A: HỒ SƠ SINH VIÊN & KHOA ---");
            System.out.println("1. Hiển thị & Tìm kiếm sinh viên");
            System.out.println("2. Hiển thị danh sách Khoa");
            System.out.println("3. Thống kê số lượng Sinh viên theo mã Khoa");
            System.out.println("4. Sửa thông tin sinh viên");
            System.out.println("5. Xóa và lưu trữ sinh viên");
            System.out.println("6. Thêm sinh viên");
            System.out.println("0. Quay lại Menu Chính");
            System.out.print("--> Chọn chức năng (0-6): ");

            String luaChon = scanner.nextLine();

            switch (luaChon) {
                case "1":
                    System.out.print("Nhập từ khóa tìm kiếm (Enter để xem tất cả): ");
                    String tuKhoa = scanner.nextLine();
                    List<SinhVien> ds = sinhVienDAO.timKiemSinhVien(tuKhoa);
                    inBangSinhVien(ds);
                    break;
                case "2":
                    List<Khoa> dsKhoa = khoaDAO.layDanhSachKhoa();
                    inBangKhoa(dsKhoa);
                    break;
                case "3":
                	System.out.print("Nhập Mã Khoa cần thống kê (VD: cntt&tt): ");
                    String maKhoa = scanner.nextLine();
                    
                    // Gọi Function để lấy con số tổng
                    int soLuong = sinhVienDAO.demSoLuongSinhVienTheoKhoa(maKhoa);
                    
                    System.out.println("\n=======================================================");
                    System.out.println(">> TỔNG SỐ SINH VIÊN CỦA KHOA '" + maKhoa.toUpperCase() + "': " + soLuong + " sinh viên.");
                    System.out.println("=======================================================");
                    
                    // Nếu có sinh viên thì in luôn danh sách ra bảng
                    if (soLuong > 0) {
                        System.out.println("Danh sách chi tiết:");
                        List<SinhVien> dsTheoKhoa = sinhVienDAO.layDanhSachSinhVienTheoKhoa(maKhoa);
                        inBangSinhVien(dsTheoKhoa); // Gọi lại hàm in bảng đã viết
                    }
                    break;
                case "4":
                    System.out.println("Đang gọi chức năng sửa thông tin...");
                    break;
                case "5":
                	System.out.println("Đang gọi chức năng xóa và lưu trữ sinh viên...");
                    break;
                case "6":
                    System.out.println("Đang gọi Stored Procedure thêm sinh viên...");
                    break;
                case "0":
                    return; // Thoát khỏi vòng lặp này, quay về ConsoleApp
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // Hàm in bảng danh sách sinh viên
    private void inBangSinhVien(List<SinhVien> list) {
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy dữ liệu!");
            return;
        }
        System.out.println(String.format("\n%-10s | %-25s | %-5s | %-12s | %-12s | %-30s | %-10s", 
            "MSSV", "Họ Tên", "Phái", "Ngày Sinh", "Nơi sinh", "Địa chỉ", "Mã Khoa"));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        for (SinhVien sv : list) {
            System.out.println(String.format("%-10s | %-25s | %-5s | %-12s | %-12s | %-30s | %-10s",
                sv.getMssv(), sv.getHoTen(), sv.getGioiTinh(), sv.getNgaySinh(), sv.getNoiSinh(), sv.getDiaChi(), sv.getMaKhoa()));
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
    }
    
    //Hàm in danh sách Khoa
    private void inBangKhoa(List<Khoa> list) {
        if (list.isEmpty()) {
            System.out.println(">> Không có dữ liệu Khoa trong CSDL!");
            return;
        }
        System.out.println("\n--- DANH SÁCH CÁC KHOA ---");
        System.out.println("----------------------------------------------------------");
        System.out.println(String.format("%-15s | %-40s", "Mã Khoa", "Tên Khoa"));
        System.out.println("----------------------------------------------------------");
        for (Khoa k : list) {
            System.out.println(String.format("%-15s | %-40s", k.getMaKhoa(), k.getTenKhoa()));
        }
        System.out.println("----------------------------------------------------------");
        System.out.println(">> Tổng cộng: " + list.size() + " khoa.");
    }
}