package DAO;

import Controller.Koneksi;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {

    protected Connection getCon() throws SQLException {
        if (Koneksi.con == null || Koneksi.con.isClosed()) {
            new Koneksi().config();
        }
        if (Koneksi.con == null) {
            throw new SQLException("Koneksi ke database gagal, cek MySQL dan Koneksi.java");
        }
        return Koneksi.con;
    }
}