package model;

public class HocPhan {
    private String maHP;
    private String tenHP;
    private int soTinChi;
    private int soTietLT;
    private int soTietTH;

    public HocPhan() {}

    public HocPhan(String maHP, String tenHP, int soTinChi, int soTietLT, int soTietTH) {
        this.maHP = maHP;
        this.tenHP = tenHP;
        this.soTinChi = soTinChi;
        this.soTietLT = soTietLT;
        this.soTietTH = soTietTH;
    }

    public String getMaHP() { return maHP; }
    public void setMaHP(String maHP) { this.maHP = maHP; }

    public String getTenHP() { return tenHP; }
    public void setTenHP(String tenHP) { this.tenHP = tenHP; }

    public int getSoTinChi() { return soTinChi; }
    public void setSoTinChi(int soTinChi) { this.soTinChi = soTinChi; }

    public int getSoTietLT() { return soTietLT; }
    public void setSoTietLT(int soTietLT) { this.soTietLT = soTietLT; }

    public int getSoTietTH() { return soTietTH; }
    public void setSoTietTH(int soTietTH) { this.soTietTH = soTietTH; }
}