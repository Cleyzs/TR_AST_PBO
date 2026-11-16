package Model;


public class Layanan {

    private String idLayanan;
    private String namaLayanan;
    private double harga;
    private String jenisSatuan; // "Kg" atau "Pcs"
    private int estimasiWaktu;  // jam

    public Layanan() {
    }

    public Layanan(String idLayanan, String namaLayanan, double harga, String jenisSatuan, int estimasiWaktu) {
        this.idLayanan = idLayanan;
        this.namaLayanan = namaLayanan;
        this.harga = harga;
        this.jenisSatuan = jenisSatuan;
        this.estimasiWaktu = estimasiWaktu;
    }

    public String getIdLayanan() {
        return idLayanan;
    }

    public void setIdLayanan(String idLayanan) {
        this.idLayanan = idLayanan;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getJenisSatuan() {
        return jenisSatuan;
    }

    public void setJenisSatuan(String jenisSatuan) {
        this.jenisSatuan = jenisSatuan;
    }

    public int getEstimasiWaktu() {
        return estimasiWaktu;
    }

    public void setEstimasiWaktu(int estimasiWaktu) {
        this.estimasiWaktu = estimasiWaktu;
    }

    @Override
    public String toString() {
        return namaLayanan; // biar enak dipakai di JComboBox
    }
}