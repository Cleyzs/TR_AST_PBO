package Controller;

import DAO.LayananDAO;
import DAO.PelangganDAO;
import DAO.PesananDAO;
import Model.Layanan;
import Model.Pesanan;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PesananController {

    private static final String STATUS_DITERIMA = "Diterima";
    private static final String STATUS_DICUCI = "Dicuci";
    private static final String STATUS_DISETRIKA = "Disetrika";
    private static final String STATUS_SIAP_DIAMBIL = "Siap Diambil";
    private static final String STATUS_SUDAH_DIAMBIL = "Sudah Diambil";
    private static final String STATUS_DIBATALKAN = "Dibatalkan";

    private static final String BAYAR_BELUM = "Belum Dibayar";
    private static final String BAYAR_SEBAGIAN = "Dibayar Sebagian";
    private static final String BAYAR_LUNAS = "Lunas";

    private final PesananDAO pesananDAO = new PesananDAO();
    private final PelangganDAO pelangganDAO = new PelangganDAO();
    private final LayananDAO layananDAO = new LayananDAO();

    public double hitungPratinjau(double jumlah, Layanan layanan, boolean express) {
        if (express) {
            throw new UnsupportedOperationException("Perhitungan Express belum tersedia.");
        }

        validasiJumlah(jumlah);
        if (layanan == null || layanan.getIdLayanan() == null || layanan.getIdLayanan().trim().isEmpty()) {
            throw new IllegalArgumentException("Pilih layanan yang valid.");
        }

        try {
            Layanan layananTersimpan = layananDAO.getById(layanan.getIdLayanan().trim());
            if (layananTersimpan == null) {
                throw new IllegalArgumentException("Layanan tidak ditemukan.");
            }
            return hitungTotalDasar(jumlah, layananTersimpan);
        } catch (SQLException e) {
            throw new IllegalStateException("Gagal memuat data layanan: " + e.getMessage(), e);
        }
    }

    public String simpan(Pesanan pesanan) {
        try {
            validasiPesanan(pesanan);
            if (pelangganDAO.getById(pesanan.getIdPelanggan()) == null) {
                throw new IllegalArgumentException("Pelanggan tidak ditemukan.");
            }

            Layanan layanan = layananDAO.getById(pesanan.getIdLayanan().trim());
            if (layanan == null) {
                throw new IllegalArgumentException("Layanan tidak ditemukan.");
            }

            double totalBiaya = hitungTotalDasar(pesanan.getBerat(), layanan) + pesanan.getBiayaTambahan();
            if (!Double.isFinite(totalBiaya)) {
                throw new IllegalArgumentException("Total biaya tidak valid.");
            }

            if (layanan.getEstimasiWaktu() <= 0) {
                throw new IllegalArgumentException("Estimasi waktu layanan tidak valid.");
            }

            LocalDateTime waktuMasuk = LocalDateTime.now();
            pesanan.setIdPesanan(pesananDAO.generateIdPesanan());
            pesanan.setIdLayanan(layanan.getIdLayanan());

            pesanan.setTotalBiaya(totalBiaya);
            pesanan.setStatusPesanan(STATUS_DITERIMA);
            pesanan.setStatusPembayaran(BAYAR_BELUM);
            pesanan.setTglMasuk(Timestamp.valueOf(waktuMasuk));
            pesanan.setEstimasiSelesai(Timestamp.valueOf(waktuMasuk.plusHours(layanan.getEstimasiWaktu())));

            pesananDAO.buatPesanan(pesanan);
            info("Pesanan berhasil disimpan dengan ID " + pesanan.getIdPesanan() + ".");
            return pesanan.getIdPesanan();
        } catch (IllegalArgumentException e) {
            info(e.getMessage());
        } catch (SQLException e) {
            error("Gagal menyimpan pesanan: " + e.getMessage());
        }
        return null;
    }

    public void ubahStatus(String idPesanan, String status) {
        if (idPesanan == null || idPesanan.trim().isEmpty()) {
            info("ID pesanan wajib diisi.");
            return;
        }
        if (!statusPesananValid(status)) {
            info("Status pesanan tidak valid.");
            return;
        }

        String id = idPesanan.trim();
        String statusBaru = status.trim();
        try {
            Pesanan pesanan = pesananDAO.getById(id);
            if (pesanan == null) {
                info("Pesanan tidak ditemukan.");
                return;
            }

            String statusSaatIni = pesanan.getStatusPesanan();
            if (!statusPesananValid(statusSaatIni)) {
                info("Status pesanan saat ini tidak valid.");
                return;
            }
            if (statusSaatIni.equals(statusBaru)) {
                return;
            }
            if (STATUS_DIBATALKAN.equals(statusSaatIni)) {
                info("Pesanan yang sudah dibatalkan tidak dapat diubah statusnya.");
                return;
            }

            if (STATUS_DIBATALKAN.equals(statusBaru)) {
                if (STATUS_SUDAH_DIAMBIL.equals(statusSaatIni)) {
                    info("Pesanan yang sudah diambil tidak dapat dibatalkan.");
                    return;
                }
                pesananDAO.batalkanPesanan(id);
            } else if (!statusBerikutnya(statusSaatIni).equals(statusBaru)) {
                info("Status hanya dapat maju satu tahap sesuai alur pesanan.");
                return;
            } else {
                pesananDAO.updateStatusPesanan(id, statusBaru);
            }

            info("Status pesanan berhasil diperbarui.");
        } catch (SQLException e) {
            error("Gagal memperbarui status pesanan: " + e.getMessage());
        }
    }

    public void ubahPembayaran(String idPesanan, String status, double jumlah) {
        if (idPesanan == null || idPesanan.trim().isEmpty()) {
            info("ID pesanan wajib diisi.");
            return;
        }
        if (!Double.isFinite(jumlah) || jumlah < 0) {
            info("Jumlah pembayaran tidak valid.");
            return;
        }
        if (!statusPembayaranValid(status)) {
            info("Status pembayaran tidak valid.");
            return;
        }

        String id = idPesanan.trim();
        String statusBaru = status.trim();
        try {
            Pesanan pesanan = pesananDAO.getById(id);
            if (pesanan == null) {
                info("Pesanan tidak ditemukan.");
                return;
            }
            if (!statusBaru.equals(pesanan.getStatusPembayaran())) {
                pesananDAO.updateStatusPembayaran(id, statusBaru);
            }
            info("Status pembayaran diperbarui. Nominal pembayaran tidak disimpan karena kolomnya belum tersedia di database.");
        } catch (SQLException e) {
            error("Gagal memperbarui status pembayaran: " + e.getMessage());
        }
    }

    public List<Pesanan> semua() {
        try {
            return pesananDAO.getSemuaPesanan();
        } catch (SQLException e) {
            error("Gagal memuat data pesanan: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Object[][] dataTabel() {
        try {
            List<Object[]> hasil = pesananDAO.getDataTabelPesanan();
            Object[][] data = new Object[hasil.size()][7];
            for (int i = 0; i < hasil.size(); i++) {
                Object[] baris = hasil.get(i);
                double jumlah = ((Number) baris[3]).doubleValue();
                String nilaiJumlah = java.math.BigDecimal.valueOf(jumlah)
                        .stripTrailingZeros().toPlainString();
                data[i] = new Object[]{
                    baris[0], baris[1], baris[2], nilaiJumlah + " " + baris[4],
                    baris[5], baris[6], baris[7]
                };
            }
            return data;
        } catch (SQLException e) {
            error("Gagal memuat tabel pesanan: " + e.getMessage());
            return new Object[0][7];
        }
    }

    private void validasiPesanan(Pesanan pesanan) {
        if (pesanan == null) {
            throw new IllegalArgumentException("Data pesanan wajib diisi.");
        }
        if (pesanan.getIdPelanggan() <= 0) {
            throw new IllegalArgumentException("Pilih pelanggan yang valid.");
        }
        if (pesanan.getIdLayanan() == null || pesanan.getIdLayanan().trim().isEmpty()) {
            throw new IllegalArgumentException("Pilih layanan yang valid.");
        }
        validasiJumlah(pesanan.getBerat());
        if (!Double.isFinite(pesanan.getBiayaTambahan()) || pesanan.getBiayaTambahan() < 0) {
            throw new IllegalArgumentException("Biaya tambahan tidak valid.");
        }
    }

    private void validasiJumlah(double jumlah) {
        if (!Double.isFinite(jumlah) || jumlah <= 0) {
            throw new IllegalArgumentException("Berat atau jumlah satuan harus lebih dari nol.");
        }
    }

    private double hitungTotalDasar(double jumlah, Layanan layanan) {
        validasiJumlah(jumlah);
        if (layanan.getHarga() <= 0 || !Double.isFinite(layanan.getHarga())) {
            throw new IllegalArgumentException("Harga layanan tidak valid.");
        }
        String satuan = layanan.getJenisSatuan();
        if ("Pcs".equalsIgnoreCase(satuan)) {
            if (jumlah != Math.rint(jumlah)) {
                throw new IllegalArgumentException("Jumlah layanan Pcs harus berupa bilangan bulat.");
            }
        } else if (!"Kg".equalsIgnoreCase(satuan)) {
            throw new IllegalArgumentException("Jenis satuan layanan tidak valid.");
        }

        double total = layanan.getHarga() * jumlah;
        if (!Double.isFinite(total)) {
            throw new IllegalArgumentException("Total biaya tidak valid.");
        }
        return total;
    }

    private boolean statusPesananValid(String status) {
        if (status == null) {
            return false;
        }
        switch (status.trim()) {
            case STATUS_DITERIMA:
            case STATUS_DICUCI:
            case STATUS_DISETRIKA:
            case STATUS_SIAP_DIAMBIL:
            case STATUS_SUDAH_DIAMBIL:
            case STATUS_DIBATALKAN:
                return true;
            default:
                return false;
        }
    }

    private String statusBerikutnya(String statusSaatIni) {
        switch (statusSaatIni) {
            case STATUS_DITERIMA:
                return STATUS_DICUCI;
            case STATUS_DICUCI:
                return STATUS_DISETRIKA;
            case STATUS_DISETRIKA:
                return STATUS_SIAP_DIAMBIL;
            case STATUS_SIAP_DIAMBIL:
                return STATUS_SUDAH_DIAMBIL;
            default:
                return "";
        }
    }

    private boolean statusPembayaranValid(String status) {
        if (status == null) {
            return false;
        }
        switch (status.trim()) {
            case BAYAR_BELUM:
            case BAYAR_SEBAGIAN:
            case BAYAR_LUNAS:
                return true;
            default:
                return false;
        }
    }

    private void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
