package model;

import java.sql.Date;

public class SinhVien {
	private String mssv;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private String noiSinh;
    private String diaChi;
    private String maKhoa;
    
    // Hàm xây dựng không tham số
    public SinhVien() {
		super();
	}

    // Hàm xây dựng đầy đủ tham số
    public SinhVien(String mssv, String hoTen, String gioiTinh, Date ngaySinh, String noiSinh, String diaChi, String maKhoa) {
		super();
		this.mssv = mssv;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.noiSinh = noiSinh;
		this.diaChi = diaChi;
		this.maKhoa = maKhoa;
	}

    // Các hàm Getter và Setter
    public String getMssv() {
        return mssv;
    }

	public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    @Override
    public String toString() {
        return this.mssv + " - " + this.hoTen;
    }
}
