package ui;

import dao.PrestamoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import modelo.Ejemplar;
import modelo.Prestamo;

public class CrudPrestamos extends javax.swing.JPanel {

    private PrestamoDAO dao = new PrestamoDAO();
    private DefaultTableModel modelo;
    private int fila;

    public CrudPrestamos() {
        initComponents();
        setupDiseño();
        TableColumnModel tcm = tabla.getColumnModel();
        TableColumn columna = tcm.getColumn(0);
        tcm.removeColumn(columna);
        modelo = (DefaultTableModel) tabla.getModel(); // usa el modelo de la tabla
        buscar("");
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        Nombre1 = new javax.swing.JLabel();
        btnDevolver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        Nombre = new javax.swing.JLabel();

        Nombre1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Nombre1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Nombre1.setText("Gestión de préstamos");

        btnDevolver.setText("Devolver");
        btnDevolver.setActionCommand("Devolución");
        btnDevolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolverActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null }
                },
                new String[] {
                        "", "Título", "Código", "Prestamo", "Días Transcurridos", "Mora", "Usuario", "Rol"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tabla.setColumnSelectionAllowed(true);
        tabla.setRowHeight(40);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);
        tabla.getColumnModel().getSelectionModel()
                .setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        Nombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Nombre.setText("Buscar por usuario o título");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 172,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addContainerGap(285, Short.MAX_VALUE)
                                .addComponent(Nombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 224,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(266, 266, 266))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(mainPanelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainPanelLayout.createSequentialGroup()
                                                        .addGap(45, 45, 45)
                                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 775,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))));
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Nombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(62, 62, 62)
                                                .addComponent(Nombre))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(73, 73, 73)
                                                .addComponent(btnDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(311, Short.MAX_VALUE))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(0, 157, Short.MAX_VALUE)
                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 780, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 2, Short.MAX_VALUE)
                                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 3, Short.MAX_VALUE))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 480, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))));
    }// </editor-fold>//GEN-END:initComponents

    private void btnDevolverActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDevolverActionPerformed

        if (fila >= 0) {
            int id = (Integer) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de realizar la devolución?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (dao.devolverPrestamo(id)) {
                        JOptionPane.showMessageDialog(this, "Ejemplar devuelto correctamente.");
                        buscar("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al devolver el ejemplar.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    System.getLogger(CrudPrestamos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un prestamo de la tabla para devolver.");
        }

    }// GEN-LAST:event_btnDevolverActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tablaMouseClicked
        if (evt.getClickCount() == 1) {

            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);

            }
        }
    }// GEN-LAST:event_tablaMouseClicked

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtBuscarKeyTyped

        buscar(txtBuscar.getText());
    }// GEN-LAST:event_txtBuscarKeyTyped

    private void buscar(String texto) {
        try {
            modelo.setRowCount(0); // Limpiar tabla
            List<Prestamo> lista = dao.buscarPrestamos(texto); // Llamada al DAO

            for (Prestamo e : lista) {
                modelo.addRow(new Object[] {
                        e.getIdPrestamo(),
                        e.getIdEjemplar().getCodigoEjemplar(),
                        e.getIdEjemplar().getTitulo(),
                        e.getFechaPrestamo(),
                        e.getDiasTranscurridos(),
                        "$" + e.getTotalMora(),
                        e.getIdUsuario().getCorreo(),
                        e.getIdUsuario().getRol().getNombreRol() });
            }
        } catch (SQLException ex) {
            System.getLogger(CrudPrestamos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void setupDiseño() {
        this.setLayout(new java.awt.BorderLayout());
        this.add(mainPanel, java.awt.BorderLayout.CENTER);
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        mainPanel.setBackground(new java.awt.Color(0, 140, 153));

        java.awt.Font tituloFont = new java.awt.Font("Segoe UI", 1, 24);
        java.awt.Font labelFont = new java.awt.Font("Segoe UI", 0, 14);

        Nombre1.setFont(tituloFont);
        Nombre1.setForeground(java.awt.Color.WHITE);
        Nombre1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainPanel.add(Nombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 300, 40));

        Nombre.setFont(labelFont);
        Nombre.setForeground(java.awt.Color.WHITE);
        mainPanel.add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 300, -1));
        mainPanel.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 135, 300, 35));

        mainPanel.add(btnDevolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 135, 100, 35));

        mainPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 700, 250));
        tabla.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 14));

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel Nombre1;
    private javax.swing.JButton btnDevolver;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
