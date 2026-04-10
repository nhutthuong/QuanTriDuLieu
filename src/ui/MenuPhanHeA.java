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
                	System.out.print("Nhập Mã Khoa cần thống kê (VD: attt): ");
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
                    xuLySuaSinhVien(scanner);
                    break;
                case "5":
                    xuLyXoaSinhVien(scanner);
                    break;
                case "6":
                    xuLyThemSinhVien(scanner); 
                    break;
                case "0":
                    return; 
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
    private void xuLySuaSinhVien(Scanner scanner) {
        System.out.println("\n--- CHỨC NĂNG SỬA THÔNG TIN SINH VIÊN ---");
        System.out.print("Nhập MSSV cần sửa: ");
        String mssv = scanner.nextLine();

        List<SinhVien> ds = sinhVienDAO.timKiemSinhVien(mssv);
        if (ds.isEmpty()) {
            System.out.println(">> Lỗi: Không tìm thấy sinh viên mã " + mssv);
            return;
        }

        SinhVien sv = ds.get(0);
        System.out.println("Đang sửa SV: " + sv.getHoTen());

        try {
            System.out.print("Họ tên mới: "); sv.setHoTen(scanner.nextLine());
            System.out.print("Phái mới (M/F): "); sv.setGioiTinh(scanner.nextLine());
            System.out.print("Ngày sinh mới (yyyy-mm-dd): "); 
            sv.setNgaySinh(java.sql.Date.valueOf(scanner.nextLine()));
            System.out.print("Nơi sinh mới: "); sv.setNoiSinh(scanner.nextLine());
            System.out.print("Địa chỉ mới: "); sv.setDiaChi(scanner.nextLine());
            System.out.print("Mã khoa mới: "); sv.setMaKhoa(scanner.nextLine());

            if (sinhVienDAO.capNhatSinhVien(sv)) {
                System.out.println(">> THÀNH CÔNG: Đã cập nhật thông tin mới.");
            } else {
                System.out.println(">> THẤT BẠI: Cập nhật không thành công.");
            }
        } catch (Exception e) {
            System.out.println(">> LỖI: Định dạng dữ liệu không hợp lệ (VD: Ngày sinh phải là yyyy-mm-dd)");
        }
    }
    // --- HÀM XỬ LÝ XÓA & LƯU TRỮ SINH VIÊN (Chức năng 5) ---
    private void xuLyXoaSinhVien(Scanner scanner) {
        System.out.println("\n--- CHỨC NĂNG XÓA & LƯU TRỮ SINH VIÊN (TRANSACTION) ---");
        

        System.out.print("Nhập MSSV muốn xóa: ");
        String mssv = scanner.nextLine().trim();

        if (mssv.isEmpty()) {
            System.out.println(">> Lỗi: MSSV không được để trống!");
            return;
        }

      
        System.out.print("Hệ thống sẽ chuyển SV này sang bảng LƯU TRỮ và xóa khỏi danh sách chính. Xác nhận? (y/n): ");
        String xacNhan = scanner.nextLine();

        if (xacNhan.equalsIgnoreCase("y")) {

            int ketQua = sinhVienDAO.xoaSinhVienTransaction(mssv);

            if (ketQua == 1) {
                System.out.println("=======================================================");
                System.out.println(">> THÀNH CÔNG: Sinh viên " + mssv + " đã được chuyển vào kho lưu trữ.");
                System.out.println("=======================================================");
            } else if (ketQua == 0) {
                System.out.println(">> THẤT BẠI: Không tìm thấy sinh viên mã " + mssv + " trong hệ thống.");
            } else {
                System.out.println(">> LỖI: Có lỗi Transaction xảy ra. Dữ liệu vẫn được giữ an toàn (Rollback).");
            }
        } else {
            System.out.println(">> Đã hủy thao tác xóa.");
        }
    }

        private void xuLyThemSinhVien(Scanner scanner) {
        System.out.println("\n--- CHỨC NĂNG THÊM SINH VIÊN (HỌ TÊN TỰ ĐỘNG VIẾT HOA) ---");
        try {
            SinhVien sv = new SinhVien();
            System.out.print("Nhập MSSV: "); sv.setMssv(scanner.nextLine().trim());
            System.out.print("Nhập Họ tên (ví dụ: nguyen minh thai): "); sv.setHoTen(scanner.nextLine().trim());
            System.out.print("Phái (M/F): "); sv.setGioiTinh(scanner.nextLine().trim().toUpperCase());
            System.out.print("Ngày sinh (yyyy-mm-dd): "); 
            sv.setNgaySinh(java.sql.Date.valueOf(scanner.nextLine().trim()));
            System.out.print("Nơi sinh: "); sv.setNoiSinh(scanner.nextLine().trim());
            System.out.print("Địa chỉ: "); sv.setDiaChi(scanner.nextLine().trim());
            System.out.print("Mã khoa: "); sv.setMaKhoa(scanner.nextLine().trim().toUpperCase());

            int kq = sinhVienDAO.themSinhVien(sv);

            if (kq == 1) {
                System.out.println(">> THÀNH CÔNG: Sinh viên đã được thêm. Hãy dùng chức năng 1 để kiểm tra Họ tên.");
            } else if (kq == 0) {
                System.out.println(">> THẤT BẠI: Mã số sinh viên này đã tồn tại!");
            } else {
                System.out.println(">> LỖI: Không thể thêm sinh viên.");
            }
        } catch (Exception e) {
            System.out.println(">> LỖI: Dữ liệu nhập không đúng định dạng (Ngày sinh phải là yyyy-mm-dd).");
        }
    }
}