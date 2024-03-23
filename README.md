# TP2DPBO2024C1
Saya Azzahra Fahriza Fitriani dengan NIM 2102296 mengerjakan soal TP 2 dalam mata kuliah Struktur Data untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

## Data Mahasiswa App
Aplikasi ini adalah aplikasi sederhana untuk manajemen data mahasiswa. Aplikasi ini dibangun menggunakan Java dengan antarmuka grafis menggunakan Java Swing dan menggunakan MySQL sebagai database.

## Struktur File
- Database.java: Kelas untuk mengelola koneksi dan query ke database MySQL.
- Mahasiswa.java: Kelas model untuk objek Mahasiswa.
- Menu.java: Kelas utama yang menangani antarmuka grafis dan logika aplikasi.

### Menu.Java
- main(String[] args) -> Metode main yang merupakan titik masuk utama untuk menjalankan
  aplikasi. Ini membuat objek Menu, mengatur properti tampilan, dan menampilkannya.
- Menu() -> Konstruktor kelas Menu yang menginisialisasi komponen GUI, mengatur koneksi database, dan menetapkan action listener untuk tombol dan tabel.
- setTable() -> Metode untuk mengatur data dalam tabel. Ini melakukan query ke database untuk mengambil data mahasiswa dan memasukkannya ke dalam model tabel.
- isNIMExists(String nim) -> Metode untuk memeriksa apakah NIM yang dimasukkan sudah ada dalam database. Ini digunakan untuk validasi saat menambahkan data mahasiswa baru.
- insertData() -> Metode untuk menambahkan data mahasiswa baru ke database. Ini memeriksa validitas input dan kemudian mengeksekusi query INSERT untuk menambahkan data ke dalam database.
- updateData() -> Metode untuk memperbarui data mahasiswa yang ada di database. Ini memperbarui data mahasiswa yang dipilih di tabel dengan data yang dimasukkan oleh pengguna.
- deleteData() -> Metode untuk menghapus data mahasiswa dari database. Ini menghapus data mahasiswa yang dipilih dari database setelah pengguna mengonfirmasi penghapusan.
- clearForm() -> Metode untuk mengosongkan semua field input setelah operasi tambah, ubah, atau hapus selesai dieksekusi. Ini juga mengatur kembali GUI ke keadaan awal.
- ActionListener -> Beberapa action listener yang ditetapkan untuk tombol-tombol yang ada di antarmuka pengguna, seperti tombol "Add", "Update", "Delete", dan "Cancel".
- MouseListener -> Mouse listener yang digunakan untuk mendeteksi klik pada tabel untuk memilih baris yang akan diubah atau dihapus.

### Database.Java
- Konstruktor Database() -> Fungsi ini merupakan konstruktor kelas Database yang bertanggung jawab untuk menginisialisasi koneksi ke database MySQL.
- selectQuery(String sql)-> Fungsi ini digunakan untuk melakukan query SELECT ke database. Parameter sql adalah pernyataan SQL yang ingin dieksekusi. Fungsi ini mengembalikan hasil dari query dalam bentuk ResultSet.
- insertUpdateDeleteQuery(String sql)-> Fungsi ini digunakan untuk menjalankan pernyataan SQL yang mengubah data di database, seperti INSERT, UPDATE, atau DELETE. Parameter sql adalah pernyataan SQL yang ingin dieksekusi. Fungsi ini mengembalikan jumlah baris yang terpengaruh oleh operasi tersebut.
- getStatement()-> Fungsi ini mengembalikan objek Statement yang digunakan untuk mengeksekusi pernyataan SQL. Ini dapat berguna jika Anda perlu menggunakan objek Statement untuk operasi SQL kustom lainnya.


## Catatan
Aplikasi ini hampir sama dengan yang ada pada repositori LP5DPBPO2024C1 hanya dilakukan modifikasi untuk data yang ada ditambahkan ke database, serta dapat menampilkan popup error ketika ingin menginsert / mengupdate data tetapi ada yang tidak diisi, serta ketika ingin menambah data tidak boleh ada NIM yang sama.
