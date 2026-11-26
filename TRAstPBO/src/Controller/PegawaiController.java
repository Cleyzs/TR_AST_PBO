/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.PegawaiDAO;
import Model.Pegawai;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controller Pegawai: penghubung View (Login, FormPegawai) dengan PegawaiDAO
 *
 * Urutan Object[] dari FormPegawai.getDataForm():
 *   [0] nama, [1] username (= id_pegawai), [2] password, [3] telepon, [4] jabatan
 * Catatan: telepon tidak punya kolom di tb_admin, jadi tidak disimpan.
 */
public class PegawaiController {

    private final PegawaiDAO dao = new PegawaiDAO();

    /** Login: kembalikan Pegawai bila cocok, null bila gagal. */
    public Model.Pegawai login(String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return null;
        }
        try {
            return dao.login(username.trim(), password);
        } catch (SQLException e) {
            error("Gagal login: " + e.getMessage());
            return null;
        }
    }

    public void tambah(Object[] data) {
        try {
            Pegawai p = toPegawai(data);
            if (p.getIdPegawai().isEmpty() || p.getNamaPegawai().isEmpty()
                    || p.getJabatan().isEmpty() || p.getPassword().isEmpty()) {
                info("Nama, username, password, dan jabatan wajib diisi.");
                return;
            }
            if (cariById(p.getIdPegawai()) != null) {
                info("Username '" + p.getIdPegawai() + "' sudah dipakai.");
                return;
            }
            dao.insert(p);
            info("Data pegawai berhasil disimpan.");
        } catch (SQLException e) {
            error("Gagal menyimpan: " + e.getMessage());
        }
    }

    public void ubah(Object[] data) {
        try {
            Pegawai p = toPegawai(data);
            Pegawai lama = cariById(p.getIdPegawai());
            if (lama == null) {
                info("Pegawai dengan username '" + p.getIdPegawai() + "' tidak ditemukan.");
                return;
            }
            // Field password di form dikosongkan saat baris dipilih -> kosong = tidak diganti
            if (p.getPassword().isEmpty()) {
                p.setPassword(lama.getPassword());
            }
            if (p.getNamaPegawai().isEmpty() || p.getJabatan().isEmpty()) {
                info("Nama dan jabatan wajib diisi.");
                return;
            }
            dao.update(p);
            info("Data pegawai berhasil diubah.");
        } catch (SQLException e) {
            error("Gagal mengubah: " + e.getMessage());
        }
    }

    public void hapus(String id) {
        if (id == null || id.trim().isEmpty()) {
            info("Pilih pegawai yang akan dihapus.");
            return;
        }
        try {
            dao.delete(id.trim());
            info("Data pegawai berhasil dihapus.");
        } catch (SQLException e) {
            error("Gagal menghapus: " + e.getMessage());
        }
    }

    public java.util.List<Model.Pegawai> semua() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            error("Gagal memuat data: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ---------- helper ----------

    private Pegawai cariById(String id) throws SQLException {
        for (Pegawai p : dao.getAll()) {
            if (p.getIdPegawai().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    private Pegawai toPegawai(Object[] d) {
        return new Pegawai(str(d, 1), str(d, 0), str(d, 4), str(d, 2));
    }

    private String str(Object[] d, int i) {
        return (d != null && i < d.length && d[i] != null) ? d[i].toString().trim() : "";
    }

    private void info(String m) {
        JOptionPane.showMessageDialog(null, m);
    }

    private void error(String m) {
        JOptionPane.showMessageDialog(null, m, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
