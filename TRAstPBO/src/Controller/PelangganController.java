/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.PelangganDAO;
import Model.Pelanggan;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controller Pelanggan: penghubung View (FormPelanggan, RiwayatPelanggan,
 * FormPesanan) dengan PelangganDAO.
 *
 * Urutan Object[] dari FormPelanggan.getDataForm():
 *   [0] nama, [1] no HP, [2] alamat   (opsional [3] id_pelanggan)
 * Form tidak punya field id, jadi ubah/hapus mengenali pelanggan lewat nama.
 */
public class PelangganController {

    private final PelangganDAO dao = new PelangganDAO();

    public void tambah(Object[] data) {
        try {
            String nama = str(data, 0), hp = str(data, 1), alamat = str(data, 2);
            if (nama.isEmpty() || hp.isEmpty()) {
                info("Nama dan nomor HP wajib diisi.");
                return;
            }
            dao.insert(new Pelanggan(0, nama, hp, alamat));
            info("Data pelanggan berhasil disimpan.");
        } catch (SQLException e) {
            error("Gagal menyimpan: " + e.getMessage());
        }
    }

    public void ubah(Object[] data) {
        try {
            String nama = str(data, 0), hp = str(data, 1), alamat = str(data, 2);
            if (nama.isEmpty() || hp.isEmpty()) {
                info("Nama dan nomor HP wajib diisi.");
                return;
            }
            Pelanggan lama = null;
            if (data != null && data.length > 3 && data[3] != null) {
                lama = dao.getById(Integer.parseInt(data[3].toString()));
            } else {
                lama = cariByNama(nama);
            }
            if (lama == null) {
                info("Pelanggan '" + nama + "' tidak ditemukan.");
                return;
            }
            lama.setNama(nama);
            lama.setNoHp(hp);
            lama.setAlamat(alamat);
            dao.update(lama);
            info("Data pelanggan berhasil diubah.");
        } catch (NumberFormatException e) {
            error("ID pelanggan tidak valid.");
        } catch (SQLException e) {
            error("Gagal mengubah: " + e.getMessage());
        }
    }

    public void hapus(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            info("Pilih pelanggan yang akan dihapus.");
            return;
        }
        try {
            Pelanggan p = cariByNama(nama);
            if (p == null) {
                info("Pelanggan '" + nama + "' tidak ditemukan.");
                return;
            }
            dao.delete(p.getIdPelanggan());
            info("Data pelanggan berhasil dihapus.");
        } catch (SQLException e) {
            // biasanya karena pelanggan masih punya pesanan (foreign key)
            error("Gagal menghapus: " + e.getMessage());
        }
    }

    public java.util.List<Model.Pelanggan> semua() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            error("Gagal memuat data: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /** Pencarian pakai kata kunci (potongan nama). Kata kunci kosong = semua. */
    public java.util.List<Model.Pelanggan> cari(String kataKunci) {
        if (kataKunci == null || kataKunci.trim().isEmpty()) {
            return semua();
        }
        try {
            return dao.cariByNama(kataKunci.trim());
        } catch (SQLException e) {
            error("Gagal mencari: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /** Satu pelanggan (dipakai RiwayatPelanggan). Prioritas nama persis, null bila tidak ada. */
    public Model.Pelanggan cariByNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            return null;
        }
        try {
            List<Pelanggan> hasil = dao.cariByNama(nama.trim());
            for (Pelanggan p : hasil) {
                if (p.getNama().equalsIgnoreCase(nama.trim())) {
                    return p;
                }
            }
            return hasil.isEmpty() ? null : hasil.get(0);
        } catch (SQLException e) {
            error("Gagal mencari: " + e.getMessage());
            return null;
        }
    }

    // ---------- helper ----------

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
