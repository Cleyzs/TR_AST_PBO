package Controller;

import DAO.LayananDAO;
import DAO.PelangganDAO;
import DAO.PesananDAO;
import Model.Layanan;
import Model.Pelanggan;
import Model.Pesanan;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class LaporanController {

    private static final String TIDAK_TERSEDIA = "Tidak tersedia";

    private final PesananDAO pesananDAO = new PesananDAO();
    private final PelangganDAO pelangganDAO = new PelangganDAO();
    private final LayananDAO layananDAO = new LayananDAO();

    public Object[] dataNota(String idPesanan) {
        if (idPesanan == null || idPesanan.trim().isEmpty()) {
            info("ID pesanan wajib diisi.");
            return null;
        }

        try {
            Pesanan pesanan = pesananDAO.getById(idPesanan.trim());
            if (pesanan == null) {
                info("Pesanan tidak ditemukan.");
                return null;
            }

            Pelanggan pelanggan = pelangganDAO.getById(pesanan.getIdPelanggan());
            if (pelanggan == null) {
                error("Data pelanggan untuk pesanan ini tidak ditemukan.");
                return null;
            }

            Layanan layanan = layananDAO.getById(pesanan.getIdLayanan());
            if (layanan == null) {
                error("Data layanan untuk pesanan ini tidak ditemukan.");
                return null;
            }

            return new Object[]{
                pesanan.getIdPesanan(),
                pelanggan.getNama(),
                formatKontak(pelanggan),
                layanan.getNamaLayanan(),
                formatJumlah(pesanan.getBerat(), layanan.getJenisSatuan()),
                pesanan.getTotalBiaya(),
                TIDAK_TERSEDIA,
                nilaiAtauPlaceholder(pesanan.getTglMasuk()),
                nilaiAtauPlaceholder(pesanan.getEstimasiSelesai()),
                nilaiAtauPlaceholder(pesanan.getStatusPembayaran()),
                TIDAK_TERSEDIA,
                TIDAK_TERSEDIA
            };
        } catch (SQLException e) {
            error("Gagal memuat data nota: " + e.getMessage());
            return null;
        }
    }

    public Object[] riwayatPelanggan(int idPelanggan) {
        if (idPelanggan <= 0) {
            info("ID pelanggan tidak valid.");
            return null;
        }

        try {
            Pelanggan pelanggan = pelangganDAO.getById(idPelanggan);
            if (pelanggan == null) {
                info("Pelanggan tidak ditemukan.");
                return null;
            }

            List<Pesanan> pesananList = pesananDAO.getByPelanggan(idPelanggan);
            Object[][] barisRiwayat = new Object[pesananList.size()][6];
            Map<String, String> jenisSatuanLayanan = new HashMap<>();
            Map<String, String> namaLayanan = new HashMap<>();

            for (int i = 0; i < pesananList.size(); i++) {
                Pesanan pesanan = pesananList.get(i);
                String nama = namaLayanan.get(pesanan.getIdLayanan());
                if (nama == null) {
                    Layanan layanan = layananDAO.getById(pesanan.getIdLayanan());
                    if (layanan == null) {
                        throw new SQLException("Layanan untuk pesanan " + pesanan.getIdPesanan() + " tidak ditemukan.");
                    }
                    nama = layanan.getNamaLayanan();
                    namaLayanan.put(pesanan.getIdLayanan(), nama);
                    jenisSatuanLayanan.put(pesanan.getIdLayanan(), layanan.getJenisSatuan());
                }

                barisRiwayat[i] = new Object[]{
                    pesanan.getIdPesanan(),
                    nilaiAtauPlaceholder(pesanan.getTglMasuk()),
                    nama,
                    formatJumlah(pesanan.getBerat(), jenisSatuanLayanan.get(pesanan.getIdLayanan())),
                    nilaiAtauPlaceholder(pesanan.getStatusPesanan()),
                    pesanan.getTotalBiaya()
                };
            }

            return new Object[]{
                pelanggan.getNama(),
                nilaiAtauPlaceholder(pelanggan.getNoHp()),
                nilaiAtauPlaceholder(pelanggan.getAlamat()),
                pesananList.size(),
                barisRiwayat
            };
        } catch (SQLException e) {
            error("Gagal memuat riwayat pelanggan: " + e.getMessage());
            return null;
        }
    }

    public Object[] pendapatan(java.sql.Date tglAwal, java.sql.Date tglAkhir) {
        if (tglAwal == null || tglAkhir == null) {
            info("Tanggal awal dan tanggal akhir wajib diisi.");
            return null;
        }

        LocalDate awal = tglAwal.toLocalDate();
        LocalDate akhir = tglAkhir.toLocalDate();
        if (akhir.isBefore(awal)) {
            info("Tanggal akhir tidak boleh sebelum tanggal awal.");
            return null;
        }

        Timestamp awalHari = Timestamp.valueOf(awal.atStartOfDay());
        Timestamp akhirEksklusif = Timestamp.valueOf(akhir.plusDays(1).atStartOfDay());

        try {
            List<Pesanan> pesananLunas = pesananDAO.getPesananLunasByDate(awalHari, akhirEksklusif);
            double totalPendapatan = pesananDAO.getPendapatanByDate(awalHari, akhirEksklusif);
            Object[][] barisPendapatan = new Object[pesananLunas.size()][6];
            Map<Integer, String> namaPelanggan = new HashMap<>();
            Map<String, String> namaLayanan = new HashMap<>();

            for (int i = 0; i < pesananLunas.size(); i++) {
                Pesanan pesanan = pesananLunas.get(i);
                String nama = namaPelanggan.get(pesanan.getIdPelanggan());
                if (nama == null) {
                    Pelanggan pelanggan = pelangganDAO.getById(pesanan.getIdPelanggan());
                    if (pelanggan == null) {
                        throw new SQLException("Pelanggan untuk pesanan " + pesanan.getIdPesanan() + " tidak ditemukan.");
                    }
                    nama = pelanggan.getNama();
                    namaPelanggan.put(pesanan.getIdPelanggan(), nama);
                }

                String layananNama = namaLayanan.get(pesanan.getIdLayanan());
                if (layananNama == null) {
                    Layanan layanan = layananDAO.getById(pesanan.getIdLayanan());
                    if (layanan == null) {
                        throw new SQLException("Layanan untuk pesanan " + pesanan.getIdPesanan() + " tidak ditemukan.");
                    }
                    layananNama = layanan.getNamaLayanan();
                    namaLayanan.put(pesanan.getIdLayanan(), layananNama);
                }

                barisPendapatan[i] = new Object[]{
                    pesanan.getIdPelanggan(),
                    nilaiAtauPlaceholder(pesanan.getTglMasuk()),
                    nama,
                    layananNama,
                    nilaiAtauPlaceholder(pesanan.getStatusPembayaran()),
                    pesanan.getTotalBiaya()
                };
            }

            return new Object[]{totalPendapatan, barisPendapatan};
        } catch (SQLException e) {
            error("Gagal memuat laporan pendapatan: " + e.getMessage());
            return null;
        }
    }

    private String formatKontak(Pelanggan pelanggan) {
        String noHp = nilaiAtauPlaceholder(pelanggan.getNoHp());
        String alamat = pelanggan.getAlamat();
        if (alamat == null || alamat.trim().isEmpty()) {
            return noHp;
        }
        return noHp + " / " + alamat.trim();
    }

    private String formatJumlah(double jumlah, String jenisSatuan) {
        return jumlah + " " + nilaiAtauPlaceholder(jenisSatuan);
    }

    private String nilaiAtauPlaceholder(Object nilai) {
        if (nilai == null || nilai.toString().trim().isEmpty()) {
            return TIDAK_TERSEDIA;
        }
        return nilai.toString();
    }

    private void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
