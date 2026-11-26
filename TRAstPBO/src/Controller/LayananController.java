package Controller;

import DAO.LayananDAO;
import Model.Layanan;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LayananController {

    private final LayananDAO dao = new LayananDAO();

    public void tambah(Layanan layanan) {
        try {
            Layanan data = validasiDanNormalisasi(layanan, false);
            String id;
            if (data.getIdLayanan().isEmpty()) {
                id = dao.insertDenganIdOtomatis(data);
            } else {
                dao.insert(data);
                id = data.getIdLayanan();
            }
            info("Data layanan berhasil disimpan dengan ID " + id + ".");
        } catch (IllegalArgumentException e) {
            info(e.getMessage());
        } catch (SQLException e) {
            error("Gagal menyimpan layanan: " + e.getMessage());
        }
    }

    public void ubah(Layanan layanan) {
        try {
            dao.update(validasiDanNormalisasi(layanan, true));
            info("Data layanan berhasil diubah.");
        } catch (IllegalArgumentException e) {
            info(e.getMessage());
        } catch (SQLException e) {
            error("Gagal mengubah layanan: " + e.getMessage());
        }
    }

    public void hapus(String idLayanan) {
        if (idLayanan == null || idLayanan.trim().isEmpty()) {
            info("Pilih layanan yang akan dihapus.");
            return;
        }

        String id = idLayanan.trim();
        try {
            if (dao.getById(id) == null) {
                info("Layanan dengan ID '" + id + "' tidak ditemukan.");
                return;
            }
            if (dao.isUsedByPesanan(id)) {
                info("Layanan tidak dapat dihapus karena sudah digunakan oleh pesanan.");
                return;
            }
            dao.delete(id);
            info("Data layanan berhasil dihapus.");
        } catch (SQLException e) {
            if (isConstraintViolation(e)) {
                error("Layanan tidak dapat dihapus karena sudah digunakan oleh pesanan.");
            } else {
                error("Gagal menghapus layanan: " + e.getMessage());
            }
        }
    }

    public List<Layanan> semua() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            error("Gagal memuat data layanan: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private Layanan validasiDanNormalisasi(Layanan layanan, boolean wajibId) {
        if (layanan == null) {
            throw new IllegalArgumentException("Data layanan wajib diisi.");
        }

        String id = layanan.getIdLayanan() == null ? "" : layanan.getIdLayanan().trim();
        String nama = layanan.getNamaLayanan() == null ? "" : layanan.getNamaLayanan().trim();
        String satuan = layanan.getJenisSatuan() == null ? "" : layanan.getJenisSatuan().trim();

        if (wajibId && id.isEmpty()) {
            throw new IllegalArgumentException("ID layanan wajib diisi.");
        }
        if (id.length() > 10) {
            throw new IllegalArgumentException("ID layanan maksimal 10 karakter.");
        }
        if (nama.isEmpty()) {
            throw new IllegalArgumentException("Nama layanan wajib diisi.");
        }
        if (!Double.isFinite(layanan.getHarga()) || layanan.getHarga() <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari nol.");
        }
        if (layanan.getEstimasiWaktu() <= 0) {
            throw new IllegalArgumentException("Estimasi waktu harus lebih dari nol jam.");
        }

        if (satuan.equalsIgnoreCase("kg")) {
            satuan = "Kg";
        } else if (satuan.equalsIgnoreCase("pcs")) {
            satuan = "Pcs";
        } else {
            throw new IllegalArgumentException("Jenis satuan harus Kg atau Pcs.");
        }

        return new Layanan(id, nama, layanan.getHarga(), satuan, layanan.getEstimasiWaktu());
    }

    private boolean isConstraintViolation(SQLException e) {
        String sqlState = e.getSQLState();
        return sqlState != null && sqlState.startsWith("23");
    }

    private void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
