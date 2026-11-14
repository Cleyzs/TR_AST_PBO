package Model;

public class Pegawai {

    private String idPegawai;
    private String namaPegawai;
    private String jabatan;
    private String password;

    public Pegawai() {
    }

    public Pegawai(String idPegawai, String namaPegawai, String jabatan, String password) {
        this.idPegawai = idPegawai;
        this.namaPegawai = namaPegawai;
        this.jabatan = jabatan;
        this.password = password;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return namaPegawai;
    }
}