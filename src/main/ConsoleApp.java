package main;

import ui.MenuPhanHeA;
import ui.MenuPhanHeB;
import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuPhanHeA menuA = new MenuPhanHeA();
        MenuPhanHeB menuB = new MenuPhanHeB();

        while (true) {
            System.out.println("=======================================================");
            System.out.println("   HỆ THỐNG QUẢN LÝ SINH VIÊN - ĐỒ ÁN QUẢN TRỊ DỮ LIỆU ");
            System.out.println("=======================================================");
            System.out.println("1. Phân hệ A: Quản lý Hồ sơ Sinh viên & Danh mục Khoa");
            System.out.println("2. Phân hệ B: Quản lý Học phần, Đăng ký & Điểm số");
            System.out.println("3. Thoát chương trình");
            System.out.print("--> Nhập lựa chọn của bạn (1-3): ");

            String luaChon = scanner.nextLine();

            switch (luaChon) {
                case "1":
                    // Chuyển hướng sang menu của Phân hệ A
                    menuA.hienThiMenu(scanner); 
                    break;
                case "2":
                    // Chuyển hướng sang menu của Phân hệ B
                    menuB.hienThiMenu(scanner); 
                    break;
                case "3":
                    System.out.println("Đã thoát hệ thống!");
                    scanner.close();
                    return; // Kết thúc toàn bộ chương trình
                default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ. Vui lòng nhập lại!");
            }
        }
    }
}