package DAO;

import Model.Pesanan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PesananDAO extends BaseDAO {

    private Pesanan map(ResultSet rs) throws SQLException {
        return new Pesanan(
                rs.getString("id_pesanan"),
                rs.getInt("id_pelanggan"),
                rs.getString("id_layanan"),
                rs.getDouble("berat"),
                rs.getDouble("biaya_tambahan"),
                rs.getDouble("total_biaya"),
                rs.getString("status_pesanan"),
                rs.getString("status_pembayaran"),
                rs.getTimestamp("tgl_masuk"),
                rs.getTimestamp("estimasi_selesai"));
    }

    /** Bikin ID pesanan berikutnya, format LP001, LP002, dst. */
    public String generateIdPesanan() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_pesanan, 3) AS UNSIGNED)) AS maks FROM tb_pesanan";
        int next = 1;
        try (PreparedStatement ps = getCon().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                next = rs.getInt("maks") + 1;
            }
        }
        return String.format("LP%03d", next);
    }

    public void buatPesanan(Pesanan p) throws SQLException {
        String sql = "INSERT INTO tb_pesanan (id_pesanan, id_pelanggan, id_layanan, berat, biaya_tambahan, "
                + "total_biaya, status_pesanan, status_pembayaran, tgl_masuk, estimasi_selesai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, p.getIdPesanan());
            ps.setInt(2, p.getIdPelanggan());
            ps.setString(3, p.getIdLayanan());
            ps.setDouble(4, p.getBerat());
            ps.setDouble(5, p.getBiayaTambahan());
            ps.setDouble(6, p.getTotalBiaya());
            ps.setString(7, p.getStatusPesanan());
            ps.setString(8, p.getStatusPembayaran());
            ps.setTimestamp(9, p.getTglMasuk());
            ps.setTimestamp(10, p.getEstimasiSelesai());
            ps.executeUpdate();
        }
    }

    public List<Pesanan> getSemuaPesanan() throws SQLException {
        List<Pesanan> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_pesanan ORDER BY tgl_masuk DESC";
        try (PreparedStatement ps = getCon().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public Pesanan getById(String idPesanan) throws SQLException {
        String sql = "SELECT * FROM tb_pesanan WHERE id_pesanan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, idPesanan);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /** Riwayat semua pesanan satu pelanggan (buat RiwayatPelangganView). */
    public List<Pesanan> getByPelanggan(int idPelanggan) throws SQLException {
        List<Pesanan> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_pesanan WHERE id_pelanggan = ? ORDER BY tgl_masuk DESC";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, idPelanggan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    /** statusBaru: Diterima, Dicuci, Disetrika, Siap Diambil, atau Sudah Diambil. */
    public void updateStatusPesanan(String idPesanan, String statusBaru) throws SQLException {
        String sql = "UPDATE tb_pesanan SET status_pesanan = ? WHERE id_pesanan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, statusBaru);
            ps.setString(2, idPesanan);
            ps.executeUpdate();
        }
    }

    /** statusBaru: Belum Dibayar, Dibayar Sebagian, atau Lunas. */
    public void updateStatusPembayaran(String idPesanan, String statusBaru) throws SQLException {
        String sql = "UPDATE tb_pesanan SET status_pembayaran = ? WHERE id_pesanan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, statusBaru);
            ps.setString(2, idPesanan);
            ps.executeUpdate();
        }
    }

    /**
     * Batalin pesanan = hapus barisnya dari tabel.
     * (Dipilih hapus karena belum tentu enum status_pesanan punya nilai "Dibatalkan".
     * Kalau ternyata ada, ganti jadi UPDATE status_pesanan.)
     */
    public void batalkanPesanan(String idPesanan) throws SQLException {
        String sql = "DELETE FROM tb_pesanan WHERE id_pesanan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, idPesanan);
            ps.executeUpdate();
        }
    }

    /**
     * Total pendapatan pesanan yang statusnya Lunas, berdasarkan tgl_masuk
     * antara awal dan akhir (keduanya ikut dihitung).
     * Tips: akhir sebaiknya jam 23:59:59 biar sehari penuh ikut.
     */
    public double getPendapatanByDate(Timestamp awal, Timestamp akhir) throws SQLException {
        String sql = "SELECT COALESCE(SUM(total_biaya), 0) AS total FROM tb_pesanan "
                + "WHERE status_pembayaran = 'Lunas' AND tgl_masuk BETWEEN ? AND ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setTimestamp(1, awal);
            ps.setTimestamp(2, akhir);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble("total") : 0;
            }
        }
    }

  
    public List<Pesanan> getPesananLunasByDate(Timestamp awal, Timestamp akhir) throws SQLException {
        List<Pesanan> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_pesanan WHERE status_pembayaran = 'Lunas' "
                + "AND tgl_masuk BETWEEN ? AND ? ORDER BY tgl_masuk";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setTimestamp(1, awal);
            ps.setTimestamp(2, akhir);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }
}