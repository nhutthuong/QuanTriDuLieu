package ui;

import dao.DangKyDAO;
import dao.HocPhanDAO;
import model.HocPhan;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class MenuPhanHeB {
	private DangKyDAO dangKyDAO = new DangKyDAO();
	private HocPhanDAO hocPhanDAO = new HocPhanDAO();
    
    public void hienThiMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- PHÂN HỆ B: HỌC PHẦN, ĐĂNG KÝ & ĐIỂM SỐ ---");
            System.out.println("1. Thêm học phần vào danh mục Học phần");
            System.out.println("2. Sửa thông tin học phần");
            System.out.println("3. Xóa học phần ra khỏi danh mục học phần");
            System.out.println("4. Đăng ký học phần");
            System.out.println("5. Xóa học phần đã đăng ký");
            System.out.println("6. Nhập / cập nhật điểm thi");
            System.out.println("7. Xem bảng điểm chi tiết");
            System.out.println("8. Tính Điểm trung bình - GPA");
            System.out.println("9. Lọc sinh viên Xuất sắc / Cảnh báo");
            System.out.println("0. Quay lại Menu Chính");
            System.out.print("--> Chọn chức năng (0-9): ");

            String luaChon = scanner.nextLine();

            switch (luaChon) {
            	case "1":
            		xuLyThemHocPhan(scanner);
            		break;
                case "2":
                	xuLySuaHocPhan(scanner);
                    break;
                case "3":
                	xuLyXoaHocPhan(scanner);
                    break;
                case "4":
                	xuLyDangKyHocPhan(scanner);
                	break;
                case "5":
                	xuLyXoaDangKy(scanner);
                	break;
                case "6":
                	xuLyNhapDiem(scanner);
                	break;
                case "7":
                    xuLyXemBangDiem(scanner);
                	break;
                case "8":
                    xuLyTinhGPA(scanner);
                	break;
                case "9":
                     xuLyLocHocLuc(scanner);
                	break;
                case "0":
                    return; // Về menu chính
                default:
                	System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    
    //Hàm xử lý nhập điểm thi (Sử dụng Procedure)
    private void xuLyNhapDiem(Scanner scanner) {
        System.out.println("\n--- CHỨC NĂNG NHẬP / CẬP NHẬT ĐIỂM THI ---");
        System.out.print("Nhập MSSV: ");
        String mssv = scanner.nextLine();
        
        System.out.print("Nhập Mã Học Phần (VD: CT174): ");
        String maHP = scanner.nextLine();
        
        System.out.print("Nhập Mã Học Kỳ (VD: HK1_2526): ");
        String maHK = scanner.nextLine();
        
        System.out.print("Nhập Điểm số (0 - 10): ");
        float diem = -1;
        
        try {
            diem = Float.parseFloat(scanner.nextLine());
            if (diem < 0 || diem > 10) {
                System.out.println("Lỗi: Điểm phải nằm trong khoảng từ 0 đến 10!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Điểm phải là một con số hợp lệ!");
            return; 
        }

        // Gọi DAO xử lý
        int ketQua = dangKyDAO.nhapDiemSinhVien(mssv, maHP, maHK, diem);
        
        if (ketQua == 1) {
            System.out.println("THÀNH CÔNG: Đã cập nhật điểm (" + diem + ") cho sinh viên " + mssv + " ở môn " + maHP);
        } else if (ketQua == 0) {
            System.out.println("THẤT BẠI: Sinh viên " + mssv + " CHƯA ĐĂNG KÝ môn " + maHP + " trong học kỳ " + maHK + "!");
        } else {
            System.out.println("Lỗi hệ thống khi cập nhật điểm.");
        }
    }
    
    // Hàm xử lý đăng ký học phần
    private void xuLyDangKyHocPhan(Scanner scanner) {
        System.out.println("\n=== CHỨC NĂNG ĐĂNG KÝ HỌC PHẦN ===");
        
        System.out.print("- Nhập MSSV: ");
        String mssv = scanner.nextLine();
        
        System.out.print("- Nhập Mã Học Kỳ (VD: HK1_2526): ");
        String maHK = scanner.nextLine();
        
        System.out.print("- Nhập Mã các Học phần (cách nhau bằng dấu phẩy, VD: CT174,CT176): ");
        String inputMHP = scanner.nextLine();

        // Kiểm tra dữ liệu rỗng
        if (mssv.isEmpty() || maHK.isEmpty() || inputMHP.isEmpty()) {
            System.out.println("CẢNH BÁO: Không được để trống thông tin!");
            return;
        }

        // Tách chuỗi thành một List []
        List<String> danhSachHP = Arrays.asList(inputMHP.split(","));

        System.out.println("Đang tiến hành đăng ký " + danhSachHP.size() + " môn học...");

        // Gọi DAO thực thi
        boolean ketQua = dangKyDAO.dangKyNhieuMon(mssv, maHK, danhSachHP);

        // In thông báo kết quả
        if (ketQua) {
            System.out.println("THÀNH CÔNG: Sinh viên " + mssv + " đã đăng ký xong các môn: " + inputMHP);
        } else {
            System.out.println("THẤT BẠI: Vui lòng kiểm tra lại (Có thể do sai MSSV, sai Mã môn hoặc trùng môn).");
        }
    }
    
    //Hàm xử lý xóa học phần đã đăng ký
    private void xuLyXoaDangKy(Scanner scanner) {
        System.out.println("\n--- CHỨC NĂNG XÓA ĐĂNG KÝ HỌC PHẦN ---");
        System.out.print("Nhập MSSV: ");
        String mssv = scanner.nextLine();
        System.out.print("Nhập Mã Học Phần: ");
        String maHP = scanner.nextLine();
        System.out.print("Nhập Mã Học Kỳ: ");
        String maHK = scanner.nextLine();

        // Xác nhận lại trước khi xóa
        System.out.print("Bạn có chắc chắn muốn hủy đăng ký này? (y/n): ");
        if (!scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println(">> Đã hủy thao tác xóa.");
            return;
        }

        int ketQua = dangKyDAO.xoaDangKyHocPhan(mssv, maHP, maHK);

        switch (ketQua) {
            case 1:
                System.out.println("THÀNH CÔNG: Đã xóa đăng ký môn " + maHP + " cho sinh viên " + mssv);
                break;
            case 0:
                System.out.println("THẤT BẠI: Không tìm thấy dữ liệu đăng ký phù hợp.");
                break;
            case -2:
                System.out.println("CẢNH BÁO: Không thể xóa vì sinh viên đã có điểm môn này!");
                break;
            default:
                System.out.println("LỖI: Có lỗi xảy ra trong quá trình kết nối CSDL.");
                break;
        }
    }
    
    //Hàm xử lý thêm học phần mới
    private void xuLyThemHocPhan(Scanner scanner) {
        System.out.println("\n--- CHỨC NĂNG THÊM HỌC PHẦN MỚI ---");
        
        try {
            System.out.print("Nhập Mã HP (Tối đa 6 ký tự, VD: CT174): ");
            String maHP = scanner.nextLine().trim();
            if (maHP.isEmpty() || maHP.length() > 6) {
                System.out.println("LỖI: Mã HP không được để trống và không quá 6 ký tự.");
                return;
            }

            System.out.print("Nhập Tên HP: ");
            String tenHP = scanner.nextLine().trim();
            if (tenHP.isEmpty()) {
                System.out.println("LỖI: Tên học phần không được để trống.");
                return;
            }

            System.out.print("Nhập Số tín chỉ (> 0): ");
            int soTinChi = Integer.parseInt(scanner.nextLine());
            if (soTinChi <= 0) {
                System.out.println("LỖI: Số tín chỉ phải lớn hơn 0.");
                return;
            }

            System.out.print("Nhập Số tiết Lý thuyết (>= 0): ");
            int soTietLT = Integer.parseInt(scanner.nextLine());
            if (soTietLT < 0) {
                System.out.println("LỖI: Số tiết lý thuyết không được âm.");
                return;
            }

            System.out.print("Nhập Số tiết Thực hành (>= 0): ");
            int soTietTH = Integer.parseInt(scanner.nextLine());
            if (soTietTH < 0) {
                System.out.println("LỖI: Số tiết thực hành không được âm.");
                return;
            }

            // Gói dữ liệu vào đối tượng Model
            HocPhan hp = new HocPhan(maHP, tenHP, soTinChi, soTietLT, soTietTH);

            // Gọi DAO xử lý
            if (hocPhanDAO.themHocPhan(hp)) {
                System.out.println("THÀNH CÔNG: Đã thêm học phần '" + tenHP + "' vào hệ thống!");
            }

        } catch (NumberFormatException ex) {
            System.out.println("LỖI: Dữ liệu nhập vào không hợp lệ. Số tín chỉ và số tiết phải là số nguyên!");
        }
    }
    // ham xu ly sua hoc phan
    private void xuLySuaHocPhan(Scanner scanner) {
        System.out.println("\n--- CHỨC NĂNG SỬA THÔNG TIN HỌC PHẦN ---");
        System.out.print("Nhập Mã Học Phần cần sửa: ");
        String maHP = scanner.nextLine().trim();

        try {
      
            System.out.print("Tên Học Phần mới: ");
            String tenHP = scanner.nextLine().trim();
            System.out.print("Số tín chỉ mới: ");
            int stc = Integer.parseInt(scanner.nextLine());
            System.out.print("Số tiết LT mới: ");
            int lt = Integer.parseInt(scanner.nextLine());
            System.out.print("Số tiết TH mới: ");
            int th = Integer.parseInt(scanner.nextLine());

     
            HocPhan hp = new HocPhan(maHP, tenHP, stc, lt, th);

    
            if (hocPhanDAO.capNhatHocPhan(hp)) {
                System.out.println(">> THÀNH CÔNG: Đã cập nhật thông tin học phần " + maHP);
            } else {
                System.out.println(">> THẤT BẠI: Không tìm thấy Mã Học Phần này trong hệ thống.");
            }
        } catch (NumberFormatException e) {
            System.out.println(">> LỖI: Số tín chỉ và số tiết phải là con số!");
        }
    }
    // ham xu ly xoa hoc phan
    private void xuLyXoaHocPhan(Scanner scanner) {
        System.out.println("\n--- CHỨC NĂNG XÓA HỌC PHẦN ---");
        System.out.print("Nhập Mã Học Phần muốn xóa: ");
        String maHP = scanner.nextLine().trim().toUpperCase();

        if (maHP.isEmpty()) {
            System.out.println(">> Lỗi: Mã học phần không được để trống!");
            return;
        }

        System.out.print("Bạn có chắc chắn muốn xóa học phần " + maHP + "? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            // Gọi DAO thực thi
            int kq = hocPhanDAO.xoaHocPhan(maHP);

            switch (kq) {
                case 1:
                    System.out.println(">> THÀNH CÔNG: Đã xóa học phần khỏi danh mục.");
                    break;
                case 0:
                    System.out.println(">> THẤT BẠI: Không tìm thấy học phần có mã " + maHP);
                    break;
                case -2:
                    System.out.println(">> CẢNH BÁO: Không thể xóa! Học phần này đã có sinh viên đăng ký học.");
                    break;
                default:
                    System.out.println(">> LỖI: Có lỗi hệ thống xảy ra.");
                    break;
            }
        } else {
            System.out.println(">> Đã hủy thao tác xóa.");
        }
    }
    // ham xu ly them bang diem
    private void xuLyXemBangDiem(Scanner scanner) {
        System.out.println("\n--- XEM BẢNG ĐIỂM CHI TIẾT ---");
        System.out.print("Nhập MSSV cần xem: ");
        String mssv = scanner.nextLine().trim();

        List<String[]> bangDiem = dangKyDAO.layBangDiemChiTiet(mssv);

        if (bangDiem.isEmpty()) {
            System.out.println(">> Thông báo: Sinh viên này chưa đăng ký học phần nào hoặc không tồn tại.");
        } else {
        	// 1. Sửa lại tiêu đề bảng cho khớp (Bỏ cột Học kỳ)
        	System.out.println("\nBẢNG ĐIỂM CỦA SINH VIÊN: " + mssv);
        	System.out.println("----------------------------------------------------------------------");
        	System.out.println(String.format("%-10s | %-30s | %-10s | %-5s", 
        	    "Mã HP", "Tên Học Phần", "Tín chỉ", "Điểm"));
        	System.out.println("----------------------------------------------------------------------");

        	// 2. Sửa vòng lặp in dữ liệu
        	for (String[] row : bangDiem) {
        	    // Chỉ in 4 cột: row[0], row[1], row[2], row[3]
        	    // KHÔNG ĐƯỢC để row[4] ở đây vì mảng bây giờ chỉ dài 4
        	    System.out.println(String.format("%-10s | %-30s | %-10s | %-5s", 
        	        row[0], row[1], row[2], row[3]));
        	}
        	System.out.println("----------------------------------------------------------------------");
        }
    }
    // ham xu ly xu ly tinh GPA
    private void xuLyTinhGPA(Scanner scanner) {
        System.out.println("\n--- TÍNH ĐIỂM TRUNG BÌNH TÍCH LŨY (GPA) ---");
        System.out.print("Nhập MSSV cần tính GPA: ");
        String mssv = scanner.nextLine().trim();

        float gpa = dangKyDAO.tinhGPASinhVien(mssv);

        System.out.println("==========================================");
        System.out.println(">> SINH VIÊN: " + mssv);
        System.out.println(String.format(">> ĐIỂM GPA HỆ 10: %.2f", gpa));
        
        // Xếp loại nhanh dựa trên GPA
        if (gpa >= 9.0) System.out.println(">> XẾP LOẠI: Xuất sắc");
        else if (gpa >= 8.0) System.out.println(">> XẾP LOẠI: Giỏi");
        else if (gpa >= 7.0) System.out.println(">> XẾP LOẠI: Khá");
        else if (gpa >= 5.0) System.out.println(">> XẾP LOẠI: Trung bình");
        else if (gpa > 0) System.out.println(">> XẾP LOẠI: Yếu/Kém");
        else System.out.println(">> CHƯA CÓ DỮ LIỆU ĐIỂM");
        System.out.println("==========================================");
    }
    // ham xu ly hoc luc
    private void xuLyLocHocLuc(Scanner scanner) {
        System.out.println("\n--- LỌC SINH VIÊN THEO HỌC LỰC ---");
        System.out.println("1. Danh sách KHEN THƯỞNG (GPA >= 8.0)");
        System.out.println("2. Danh sách CẢNH BÁO (GPA < 4.0)");
        System.out.print("Chọn loại danh sách (1-2): ");
        String chon = scanner.nextLine();
        
        String loai = chon.equals("1") ? "KHENTHUONG" : "CANHBAO";
        String tieuDe = chon.equals("1") ? "DANH SÁCH KHEN THƯỞNG" : "DANH SÁCH CẢNH BÁO HỌC VỤ";

        List<String[]> ds = dangKyDAO.locSinhVienTheoHocLuc(loai);

        if (ds.isEmpty()) {
            System.out.println(">> Hiện tại không có sinh viên nào thuộc diện này.");
        } else {
            System.out.println("\n" + tieuDe);
            System.out.println("------------------------------------------------------------");
            System.out.println(String.format("%-10s | %-20s | %-10s | %-5s", "MSSV", "Họ Tên", "Mã Khoa", "GPA"));
            System.out.println("------------------------------------------------------------");
            for (String[] row : ds) {
                System.out.println(String.format("%-10s | %-20s | %-10s | %-5s", row[0], row[1], row[2], row[3]));
            }
            System.out.println("------------------------------------------------------------");
            System.out.println("Tổng cộng: " + ds.size() + " sinh viên.");
        }
    }
}