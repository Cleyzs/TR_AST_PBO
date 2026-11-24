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

    public void insert(Layanan l) throws SQLException {
        String sql = "INSERT INTO tb_layanan (id_layanan, nama_layanan, harga, jenis_satuan, estimasi_waktu) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, l.getIdLayanan());
            ps.setString(2, l.getNamaLayanan());
            ps.setDouble(3, l.getHarga());
            ps.setString(4, l.getJenisSatuan());
            ps.setInt(5, l.getEstimasiWaktu());
            ps.executeUpdate();
        }
    }

    public List<Layanan> getAll() throws SQLException {
        List<Layanan> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_layanan ORDER BY id_layanan";
        try (PreparedStatement ps = getCon().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public Layanan getById(String idLayanan) throws SQLException {
        String sql = "SELECT * FROM tb_layanan WHERE id_layanan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, idLayanan);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public void update(Layanan l) throws SQLException {
        String sql = "UPDATE tb_layanan SET nama_layanan = ?, harga = ?, jenis_satuan = ?, estimasi_waktu = ? WHERE id_layanan = ?";
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
            ps.executeUpdate();
        }
    }
}