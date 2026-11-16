package Model;

import java.sql.Timestamp;


public class Pesanan {

    private String idPesanan;
    private int idPelanggan;
    private String idLayanan;
    private double berat;
    private double biayaTambahan;
    private double totalBiaya;
    private String statusPesanan;
    private String statusPembayaran;
    private Timestamp tglMasuk;
    private Timestamp estimasiSelesai;

    public Pesanan() {
    }

    public Pesanan(String idPesanan, int idPelanggan, String idLayanan, double berat,
            double biayaTambahan, double totalBiaya, String statusPesanan,
            String statusPembayaran, Timestamp tglMasuk, Timestamp estimasiSelesai) {
        this.idPesanan = idPesanan;
        this.idPelanggan = idPelanggan;
        this.idLayanan = idLayanan;
        this.berat = berat;
        this.biayaTambahan = biayaTambahan;
        this.totalBiaya = totalBiaya;
        this.statusPesanan = statusPesanan;
        this.statusPembayaran = statusPembayaran;
        this.tglMasuk = tglMasuk;
        this.estimasiSelesai = estimasiSelesai;
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
    }

    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdLayanan() {
        return idLayanan;
    }

    public void setIdLayanan(String idLayanan) {
        this.idLayanan = idLayanan;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public double getBiayaTambahan() {
        return biayaTambahan;
    }

    public void setBiayaTambahan(double biayaTambahan) {
        this.biayaTambahan = biayaTambahan;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(double totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public String getStatusPesanan() {
        return statusPesanan;
    }

    public void setStatusPesanan(String statusPesanan) {
        this.statusPesanan = statusPesanan;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public Timestamp getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(Timestamp tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public Timestamp getEstimasiSelesai() {
        return estimasiSelesai;
    }

    public void setEstimasiSelesai(Timestamp estimasiSelesai) {
        this.estimasiSelesai = estimasiSelesai;
    }
}