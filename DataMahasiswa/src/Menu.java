import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();
        // atur ukuran window
        window.setSize(480, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    private ArrayList<Mahasiswa> listMahasiswa;
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JComboBox kotaAsalComboBox;
    private JLabel kotaAsal;


    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();
        //buat objek database
        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        String[] kotaAsalData = {
                "Ambon", "Bali", "Balikpapan", "Bandar Lampung", "Bandung", "Banda Aceh", "Banjar", "Banjarmasin", "Bekasi", "Bogor",
                "Cianjur", "Cimahi", "Ciamis", "Cirebon", "Denpasar", "Depok", "Garut", "Gorontalo", "Indramayu", "Jakarta",
                "Jambi", "Jayapura", "Karawang", "Kuningan", "Majalengka", "Makassar", "Malang", "Manado", "Mataram", "Medan",
                "Padang", "Palembang", "Pangandaran", "Pekanbaru", "Pontianak", "Purwakarta", "Semarang", "Subang", "Sukabumi",
                "Surabaya", "Tasikmalaya", "Ternate"
        };
        kotaAsalComboBox.setModel(new DefaultComboBoxModel(kotaAsalData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex == -1){
                    insertData();
                }else{
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0){
                   deleteData();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedKotaAsal = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                kotaAsalComboBox.setSelectedItem(selectedKotaAsal);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);

            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Kota Asal"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);
        try{
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            // isi tabel dengan listMahasiswa
            int i = 0;
            while (resultSet.next()){
                Object[] row = new Object[5];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("asal_kota");

                temp.addRow(row);
                i++;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return temp; // return juga harus diganti
    }
    private boolean isNIMExists(String nim) {
        // melakukan query ke database untuk memeriksa apakah NIM sudah ada
        String query = "SELECT * FROM mahasiswa WHERE nim = '" + nim + "'";
        try {
            ResultSet resultSet = database.selectQuery(query);
            return resultSet.next(); // return true jika NIM sudah ada
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String kotaAsal = kotaAsalComboBox.getSelectedItem().toString();

        if(isNIMExists(nim)){
            JOptionPane.showMessageDialog(null, "NIM nya sudah ada kak!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || kotaAsal.isEmpty()){
            JOptionPane.showMessageDialog(null, "Halo kak! Tolong diisi semua yaa inputannya!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // tambahkan data ke dalam list
        String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "',  '" + jenisKelamin + "', '" + kotaAsal + "');";
        database.insertUpdateDeleteQuery(sql);

        //update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String kotaAsal = kotaAsalComboBox.getSelectedItem().toString();
        if(nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || kotaAsal.isEmpty()){
            JOptionPane.showMessageDialog(null, "Halo kak! Tolong diisi semua yaa inputannya!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // buat query SQL untuk mengupdate data di database
        String sql = "UPDATE mahasiswa SET nim='" + nim + "', nama='" + nama + "', jenis_kelamin='" + jenisKelamin + "', asal_kota='" + kotaAsal + "' WHERE nim='" + nim + "'";

        // jalankan query untuk mengupdate data di database
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
    }


    public void deleteData() {
        int option = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi Penghapusan", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // ambil NIM dari baris yang dipilih
            String nim = nimField.getText();

            // buat query SQL untuk menghapus data dari database
            String sql = "DELETE FROM mahasiswa WHERE nim='" + nim + "'";

            // jalankan query untuk menghapus data dari database
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete Berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;

        // periksa apakah tombol Add sudah ditampilkan
        if (!addUpdateButton.getText().equals("Add")) {
            // jika tidak, ubah menjadi Add
            addUpdateButton.setText("Add");
        }
    }

}
