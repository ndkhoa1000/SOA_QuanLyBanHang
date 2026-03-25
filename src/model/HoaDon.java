package model;

import java.util.Date;
import java.util.List;

public class HoaDon {

    private String mso;
    private Date tde;
    private String nvien;

    private String idkhachHang;
    private List<ChiTiet> chiTietList;

    public HoaDon() {}

    public HoaDon(String mso, Date tde, String nvien) {
        this.mso = mso;
        this.tde = tde;
        this.nvien = nvien;
    }

    public String getMso() {
        return mso;
    }

    public void setMso(String mso) {
        this.mso = mso;
    }

    public Date getTde() {
        return tde;
    }


	public void setTde(Date tde) {
        this.tde = tde;
    }

    public String getNvien() {
        return nvien;
    }

    public String getKhachHang() {
		return idkhachHang;
	}

	public void setKhachHang(String idkhachHang) {
		this.idkhachHang = idkhachHang;
	}

	public List<ChiTiet> getChiTietList() {
		return chiTietList;
	}

	public void setChiTietList(List<ChiTiet> chiTietList) {
		this.chiTietList = chiTietList;
	}

	public void setNvien(String nvien) {
        this.nvien = nvien;
    }

    public double tong() {
        double sum = 0;
        if (chiTietList != null) {
            for (ChiTiet ct : chiTietList) {
                sum += ct.getSluong() * ct.getDgia();
            }
        }
        return sum;
    }

    public void nhap() {}

    public void in() {}
}