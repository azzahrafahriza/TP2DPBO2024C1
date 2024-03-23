public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String kotaAsal;


    public Mahasiswa(String nim, String nama, String jenisKelamin, String kotaAsal) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.kotaAsal = kotaAsal;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    public void setKotaAsal(String kotaAsal) { this.kotaAsal = kotaAsal; }

    public String getNim() { return this.nim;}

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public String getKotaAsal() {
        return this.kotaAsal;
    }
}
