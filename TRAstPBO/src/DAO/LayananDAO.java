package DAO;

import Model.Layanan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LayananDAO extends BaseDAO {

    private Layanan map(ResultSet rs) throws SQLException {
        return new Layanan(
                rs.getString("id_layanan"),
                rs.getString("nama_layanan"),
                rs.getDouble("harga"),
                rs.getString("jenis_satuan"),
                rs.getInt("estimasi_waktu"));
    }

    private static final Object ID_LOCK = new Object();

    public void insert(Layanan l) throws SQLException {
        String sql = "INSERT INTO tb_layanan "
                + "(id_layanan, nama_layanan, harga, jenis_satuan, estimasi_waktu) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, l.getIdLayanan());
            ps.setString(2, l.getNamaLayanan());
            ps.setDouble(3, l.getHarga());
            ps.setString(4, l.getJenisSatuan());
            ps.setInt(5, l.getEstimasiWaktu());
            ps.executeUpdate();
        }
    }

    public String insertDenganIdOtomatis(Layanan l) throws SQLException {
        synchronized (ID_LOCK) {
            for (int percobaan = 0; percobaan < 3; percobaan++) {
                String id = generateIdLayanan();
                l.setIdLayanan(id);
                try {
                    insert(l);
                    return id;
                } catch (SQLException e) {
                    if (e.getErrorCode() != 1062 || percobaan == 2) {
                        throw e;
                    }
                }
            }
        }
        throw new SQLException("Gagal membuat ID layanan yang unik.");
    }

    private String generateIdLayanan() throws SQLException {
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(id_layanan, 4) AS UNSIGNED)), 0) AS maks "
                + "FROM tb_layanan WHERE id_layanan REGEXP '^LYN[0-9]+$'";
        try (PreparedStatement ps = getCon().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            long next = rs.next() ? rs.getLong("maks") + 1 : 1;
            return String.format("LYN%03d", next);
        }
    }

    public List<Layanan> getAll() throws SQLException {
        List<Layanan> list = new ArrayList<>();
        String sql = "SELECT id_layanan, nama_layanan, harga, jenis_satuan, estimasi_waktu "
                + "FROM tb_layanan ORDER BY id_layanan";
        try (PreparedStatement ps = getCon().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public Layanan getById(String idLayanan) throws SQLException {
        String sql = "SELECT id_layanan, nama_layanan, harga, jenis_satuan, estimasi_waktu "
                + "FROM tb_layanan WHERE id_layanan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, idLayanan);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public boolean isUsedByPesanan(String idLayanan) throws SQLException {
        String sql = "SELECT 1 FROM tb_pesanan WHERE id_layanan = ? LIMIT 1";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, idLayanan);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void update(Layanan l) throws SQLException {
        if (getById(l.getIdLayanan()) == null) {
            throw new SQLException("Layanan dengan ID '" + l.getIdLayanan() + "' tidak ditemukan.");
        }

        String sql = "UPDATE tb_layanan SET nama_layanan = ?, harga = ?, jenis_satuan = ?, "
                + "estimasi_waktu = ? WHERE id_layanan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, l.getNamaLayanan());
            ps.setDouble(2, l.getHarga());
            ps.setString(3, l.getJenisSatuan());
            ps.setInt(4, l.getEstimasiWaktu());
            ps.setString(5, l.getIdLayanan());
            ps.executeUpdate();
        }
    }

    public void delete(String idLayanan) throws SQLException {
        String sql = "DELETE FROM tb_layanan WHERE id_layanan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, idLayanan);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Layanan dengan ID '" + idLayanan + "' tidak ditemukan.");
            }
        }
    }
}
