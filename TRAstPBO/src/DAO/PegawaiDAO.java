package DAO;

import Model.Pegawai;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PegawaiDAO extends BaseDAO {

    private Pegawai map(ResultSet rs) throws SQLException {
        return new Pegawai(
                rs.getString("id_pegawai"),
                rs.getString("nama_pegawai"),
                rs.getString("jabatan"),
                rs.getString("password"));
    }

    public void insert(Pegawai p) throws SQLException {
        String sql = "INSERT INTO tb_admin (id_pegawai, nama_pegawai, jabatan, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, p.getIdPegawai());
            ps.setString(2, p.getNamaPegawai());
            ps.setString(3, p.getJabatan());
            ps.setString(4, p.getPassword());
            ps.executeUpdate();
        }
    }

    public List<Pegawai> getAll() throws SQLException {
        List<Pegawai> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_admin ORDER BY id_pegawai";
        try (PreparedStatement ps = getCon().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void update(Pegawai p) throws SQLException {
        String sql = "UPDATE tb_admin SET nama_pegawai = ?, jabatan = ?, password = ? WHERE id_pegawai = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, p.getNamaPegawai());
            ps.setString(2, p.getJabatan());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getIdPegawai());
            ps.executeUpdate();
        }
    }

    public void delete(String idPegawai) throws SQLException {
        String sql = "DELETE FROM tb_admin WHERE id_pegawai = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, idPegawai);
            ps.executeUpdate();
        }
    }

   
    public Pegawai login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM tb_admin WHERE (id_pegawai = ? OR nama_pegawai = ?) AND password = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }
}