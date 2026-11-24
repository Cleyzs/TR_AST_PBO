package DAO;

import Model.Pelanggan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PelangganDAO extends BaseDAO {

    private Pelanggan map(ResultSet rs) throws SQLException {
        return new Pelanggan(
                rs.getInt("id_pelanggan"),
                rs.getString("nama"),
                rs.getString("no_hp"),
                rs.getString("alamat"));
    }

    public void insert(Pelanggan p) throws SQLException {
        String sql = "INSERT INTO tb_user (nama, no_hp, alamat) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoHp());
            ps.setString(3, p.getAlamat());
            ps.executeUpdate();
        }
    }

    public List<Pelanggan> getAll() throws SQLException {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_user ORDER BY id_pelanggan";
        try (PreparedStatement ps = getCon().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public Pelanggan getById(int idPelanggan) throws SQLException {
        String sql = "SELECT * FROM tb_user WHERE id_pelanggan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, idPelanggan);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /** Cari pelanggan berdasarkan potongan nama (buat fitur search kasir). */
    public List<Pelanggan> cariByNama(String keyword) throws SQLException {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_user WHERE nama LIKE ? ORDER BY nama";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    public void update(Pelanggan p) throws SQLException {
        String sql = "UPDATE tb_user SET nama = ?, no_hp = ?, alamat = ? WHERE id_pelanggan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoHp());
            ps.setString(3, p.getAlamat());
            ps.setInt(4, p.getIdPelanggan());
            ps.executeUpdate();
        }
    }

    public void delete(int idPelanggan) throws SQLException {
        String sql = "DELETE FROM tb_user WHERE id_pelanggan = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, idPelanggan);
            ps.executeUpdate();
        }
    }
}