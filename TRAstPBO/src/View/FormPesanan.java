/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

/**
 *
 * @author kaner
 */
public class FormPesanan extends javax.swing.JPanel {

    private final Controller.PesananController pesananController = new Controller.PesananController();
    private final Controller.PelangganController pelangganController = new Controller.PelangganController();
    private final Controller.LayananController layananController = new Controller.LayananController();
    private boolean memuatCombo;

    /**
     * Creates new form FormPesanan
     */
   public FormPesanan() {
    initComponents();
    perbaikiLayout();   // <- baris baru
    fieldEstimasi.setEditable(false);
    checkExpress.setSelected(false);
    checkExpress.setEnabled(false);
    checkExpress.setText("Express (belum tersedia)");
    checkExpress.setToolTipText("Tarif dan rumus Express belum ditetapkan.");
    aturRendererCombo();
    addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentShown(java.awt.event.ComponentEvent evt) {
            muatCombo();
        }
    });
}
    private void perbaikiLayout() {
        // ===== Layout dalam jPanel1 (Data Pesanan) =====
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(judulForm)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(comboPelanggan, 0, 545, Short.MAX_VALUE)
                    .addComponent(tombolTambahPelanggan)
                    .addComponent(jLabel4)
                    .addComponent(comboLayanan, 0, 545, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(fieldBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(fieldEstimasi, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                    .addComponent(checkExpress, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(judulForm)
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tombolTambahPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldEstimasi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(checkExpress, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        // ===== Layout dalam kartuKanan (Ringkasan Nota) =====
        javax.swing.GroupLayout kartuKananLayout = new javax.swing.GroupLayout(kartuKanan);
        kartuKanan.setLayout(kartuKananLayout);
        kartuKananLayout.setHorizontalGroup(
            kartuKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kartuKananLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(kartuKananLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(areaRincian, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(kartuKananLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(kartuKananLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tombolSimpan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kartuKananLayout.setVerticalGroup(
            kartuKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kartuKananLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel7)
                .addGap(33, 33, 33)
                .addComponent(areaRincian, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kartuKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(tombolSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        // ===== Layout utama =====
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30)
                        .addComponent(kartuKanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 600)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kartuKanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        judulForm = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboPelanggan = new javax.swing.JComboBox<>();
        tombolTambahPelanggan = new javax.swing.JButton();
        comboLayanan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fieldEstimasi = new javax.swing.JTextField();
        checkExpress = new javax.swing.JCheckBox();
        fieldBerat = new javax.swing.JTextField();
        kartuKanan = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        areaRincian = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tombolSimpan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 242));

        jPanel1.setBackground(new java.awt.Color(160, 216, 239));

        judulForm.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        judulForm.setText("Data Pesanan");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Pelanggan");

        comboPelanggan.setModel(new javax.swing.DefaultComboBoxModel<Model.Pelanggan>());
        comboPelanggan.addActionListener(this::comboPelangganActionPerformed);

        tombolTambahPelanggan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tombolTambahPelanggan.setText("+ Tambah Pelanggan Baru");
        tombolTambahPelanggan.addActionListener(this::tombolTambahPelangganActionPerformed);

        comboLayanan.setModel(new javax.swing.DefaultComboBoxModel<Model.Layanan>());
        comboLayanan.addActionListener(this::comboLayananActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Layanan");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Berat (kg)");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Estimasi selesai");

        fieldEstimasi.setText("-");

        checkExpress.setText("Layanan Express");
        checkExpress.addActionListener(this::checkExpressActionPerformed);

        fieldBerat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldBeratKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(judulForm)
                .addGap(237, 237, 237))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(221, 221, 221))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(comboPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tombolTambahPelanggan)
                                .addComponent(jLabel4)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(fieldBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(142, 142, 142)
                                    .addComponent(fieldEstimasi)))
                            .addComponent(checkExpress, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(19, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(judulForm)
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tombolTambahPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldEstimasi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(checkExpress, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        kartuKanan.setBackground(new java.awt.Color(160, 216, 239));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel7.setText("Ringkasan Nota");

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Pilih pelanggan dan layanan");
        areaRincian.setViewportView(jTextArea1);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Total");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Rp.0");

        tombolSimpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tombolSimpan.setText("Simpan & Cetak Nota");
        tombolSimpan.addActionListener(this::tombolSimpanActionPerformed);

        javax.swing.GroupLayout kartuKananLayout = new javax.swing.GroupLayout(kartuKanan);
        kartuKanan.setLayout(kartuKananLayout);
        kartuKananLayout.setHorizontalGroup(
            kartuKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kartuKananLayout.createSequentialGroup()
                .addGroup(kartuKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kartuKananLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kartuKananLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(areaRincian)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kartuKananLayout.createSequentialGroup()
                .addContainerGap(115, Short.MAX_VALUE)
                .addGroup(kartuKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kartuKananLayout.createSequentialGroup()
                        .addComponent(tombolSimpan)
                        .addGap(93, 93, 93))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kartuKananLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(126, 126, 126))))
        );
        kartuKananLayout.setVerticalGroup(
            kartuKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kartuKananLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel7)
                .addGap(33, 33, 33)
                .addComponent(areaRincian, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(kartuKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(tombolSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Form Pesanan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(kartuKanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kartuKanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void tombolTambahPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolTambahPelangganActionPerformed
        java.awt.Window w = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (w instanceof MenuUtama) {
            ((MenuUtama) w).tampilkanKartu("formPelanggan");
        }
    }//GEN-LAST:event_tombolTambahPelangganActionPerformed

    private void tombolSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolSimpanActionPerformed
       simpanPesananTerhubung();
    }//GEN-LAST:event_tombolSimpanActionPerformed

    private void fieldBeratKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldBeratKeyReleased
        updatePreview();
    }//GEN-LAST:event_fieldBeratKeyReleased

    private void comboPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPelangganActionPerformed
        updatePreview();
    }//GEN-LAST:event_comboPelangganActionPerformed

    private void comboLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLayananActionPerformed
        updatePreview();
    }//GEN-LAST:event_comboLayananActionPerformed

    private void checkExpressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkExpressActionPerformed
        updatePreview();
    }//GEN-LAST:event_checkExpressActionPerformed

    // CATATAN CONTROLLER: tambahkan field PesananController di sini setelah tersedia.
    // private final Controller.PesananController controller =
    //         new Controller.PesananController();

    /**
     * Satuan laundry selalu KG — tidak ada pilihan pcs.
     */
    private static final String SATUAN = "kg";

    // CATATAN CONTROLLER: muatCombo() harus mengisi combo dari Controller:
    //   controller.semuaNamaPelanggan() -> comboPelanggan
    //   controller.semuaNamaLayanan()   -> comboLayanan
    private void aturRendererCombo() {
        comboPelanggan.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Model.Pelanggan) {
                    Model.Pelanggan pelanggan = (Model.Pelanggan) value;
                    setText(pelanggan.getNama() + " (ID " + pelanggan.getIdPelanggan() + ")");
                }
                return this;
            }
        });
        comboLayanan.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Model.Layanan) {
                    Model.Layanan layanan = (Model.Layanan) value;
                    String harga = java.text.NumberFormat.getNumberInstance(
                            new java.util.Locale("id", "ID")).format(layanan.getHarga());
                    setText(layanan.getNamaLayanan() + " | Rp " + harga + "/" + layanan.getJenisSatuan()
                            + " | " + layanan.getEstimasiWaktu() + " jam");
                }
                return this;
            }
        });
    }

    private void muatCombo() {
        memuatCombo = true;
        try {
            comboPelanggan.removeAllItems();
            for (Model.Pelanggan pelanggan : pelangganController.semua()) {
                comboPelanggan.addItem(pelanggan);
            }
            comboLayanan.removeAllItems();
            for (Model.Layanan layanan : layananController.semua()) {
                comboLayanan.addItem(layanan);
            }
        } finally {
            memuatCombo = false;
        }
        updatePreview();
    }
    
        private String formatRupiah(double nilai) {
            return "Rp " + java.text.NumberFormat.getNumberInstance(
                    new java.util.Locale("id", "ID")).format(nilai);
        }

        private void updatePreview() {
            if (memuatCombo) {
                return;
            }
            Model.Pelanggan pelanggan = (Model.Pelanggan) comboPelanggan.getSelectedItem();
            Model.Layanan layanan = (Model.Layanan) comboLayanan.getSelectedItem();
            if (layanan == null) {
                jLabel5.setText("Berat / jumlah");
                fieldEstimasi.setText("-");
                jLabel9.setText("Rp 0");
                jTextArea1.setText("Pilih layanan yang tersedia.");
                return;
            }

            String satuan = layanan.getJenisSatuan();
            jLabel5.setText("Jumlah (" + satuan + ")");
            fieldEstimasi.setText(java.time.LocalDateTime.now()
                    .plusHours(layanan.getEstimasiWaktu())
                    .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

            String jumlahTeks = fieldBerat.getText().trim().replace(',', '.');
            if (jumlahTeks.isEmpty()) {
                jLabel9.setText("Rp 0");
                jTextArea1.setText("Pilih pelanggan dan masukkan berat/jumlah.");
                return;
            }

            try {
                double jumlah = Double.parseDouble(jumlahTeks);
                if (!Double.isFinite(jumlah) || jumlah <= 0) {
                    throw new IllegalArgumentException("Jumlah harus lebih dari nol.");
                }
                if ("Pcs".equalsIgnoreCase(satuan) && jumlah != Math.rint(jumlah)) {
                    throw new IllegalArgumentException("Jumlah layanan Pcs harus bilangan bulat.");
                }
                double total = pesananController.hitungPratinjau(
                        jumlah, layanan, checkExpress.isSelected());
                jLabel9.setText(formatRupiah(total));
                jTextArea1.setText("Pelanggan : " + (pelanggan == null ? "-" : pelanggan.getNama())
                        + "\nLayanan   : " + layanan.getNamaLayanan()
                        + "\nJumlah    : " + jumlahTeks + " " + satuan
                        + "\nHarga     : " + formatRupiah(layanan.getHarga()) + "/" + satuan
                        + "\nEstimasi  : " + fieldEstimasi.getText());
            } catch (UnsupportedOperationException | IllegalArgumentException e) {
                jLabel9.setText("Rp 0");
                jTextArea1.setText(e.getMessage());
            } catch (IllegalStateException e) {
                jLabel9.setText("Rp 0");
                jTextArea1.setText(e.getMessage());
            }
        }

        private void hitungUlang() {
            String beratTeks = fieldBerat.getText().trim();
            Object layanan = comboLayanan.getSelectedItem();

            if (beratTeks.isEmpty()) {
                jLabel9.setText("Rp.0");
                jTextArea1.setText("Masukkan berat dulu.");
                return;
            }
            if (layanan == null) {
                jLabel9.setText("Rp.0");
                jTextArea1.setText("Pilih layanan dulu.");
                return;
            }

            // CATATAN CONTROLLER: total harus dihitung controller.hitungPratinjau(...).
            // double total = controller.hitungPratinjau(
            //         beratTeks, layanan.toString(), checkExpress.isSelected());
            jTextArea1.setText(
                    "Berat      : " + beratTeks + " " + SATUAN
                    + "\nLayanan    : " + layanan.toString()
                    + "\nExpress    : " + (checkExpress.isSelected() ? "Ya" : "Tidak")
                    + "\nEstimasi   : " + fieldEstimasi.getText());
        }

        private void simpanPesananTerhubung() {
            Model.Pelanggan pelanggan = (Model.Pelanggan) comboPelanggan.getSelectedItem();
            Model.Layanan layanan = (Model.Layanan) comboLayanan.getSelectedItem();
            if (pelanggan == null || layanan == null) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Pilih pelanggan dan layanan dari data yang tersedia.");
                return;
            }
            try {
                double jumlah = Double.parseDouble(fieldBerat.getText().trim().replace(',', '.'));
                if (!Double.isFinite(jumlah) || jumlah <= 0) {
                    throw new IllegalArgumentException("Berat atau jumlah harus lebih dari nol.");
                }
                if ("Pcs".equalsIgnoreCase(layanan.getJenisSatuan())
                        && jumlah != Math.rint(jumlah)) {
                    throw new IllegalArgumentException("Jumlah layanan Pcs harus bilangan bulat.");
                }
                Model.Pesanan pesanan = new Model.Pesanan(null, pelanggan.getIdPelanggan(),
                        layanan.getIdLayanan(), jumlah, 0, 0, null, null, null, null);
                String idPesanan = pesananController.simpan(pesanan);
                if (idPesanan != null) {
                    fieldBerat.setText("");
                    updatePreview();
                    Nota nota = new Nota(idPesanan);
                    nota.setVisible(true);
                }
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Masukkan berat/jumlah dalam format angka yang valid.",
                        "Input tidak valid", javax.swing.JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException e) {
                javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Input tidak valid", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        }

        private void simpanPesanan() {
            String berat = fieldBerat.getText().trim();
            Object pelanggan = comboPelanggan.getSelectedItem();
            Object layanan = comboLayanan.getSelectedItem();

            if (berat.isEmpty() || pelanggan == null || layanan == null) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Lengkapi berat, pelanggan, dan layanan.");
                return;
            }

            // CATATAN CONTROLLER: simpan pesanan lewat controller.simpan(...),
            // lalu tampilkan nota: new Nota(id).setVisible(true);
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Tombol Simpan ditekan. Controller belum tersedia.");
        }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane areaRincian;
    private javax.swing.JCheckBox checkExpress;
    private javax.swing.JComboBox<Model.Layanan> comboLayanan;
    private javax.swing.JComboBox<Model.Pelanggan> comboPelanggan;
    private javax.swing.JTextField fieldBerat;
    private javax.swing.JTextField fieldEstimasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel judulForm;
    private javax.swing.JPanel kartuKanan;
    private javax.swing.JButton tombolSimpan;
    private javax.swing.JButton tombolTambahPelanggan;
    // End of variables declaration//GEN-END:variables
}