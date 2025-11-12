package ui;

import dao.EjemplarDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Ejemplar;

public class CrudEjemplares extends javax.swing.JPanel {

    private EjemplarDAO dao = new EjemplarDAO();
    private DefaultTableModel modelo;

    public CrudEjemplares() {

        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        Nombre = new javax.swing.JLabel();
        Nombre1 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Código", "Título", "Autor", "Ubicacion", "Tipo", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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
        tabla.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 205, 775, 275));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 160, 239, -1));

        Nombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Nombre.setText("Buscar por título, autor o tipo ejemplar");
        add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 133, -1, -1));

        Nombre1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Nombre1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Nombre1.setText("Gestión de ejemplares");
        add(Nombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 6, 224, 60));

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(458, 157, -1, 30));

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(665, 157, -1, 30));

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(567, 157, -1, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        if (evt.getClickCount() == 2) {

            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                /*txtId.setText(modelo.getValueAt(fila, 0).toString());
                txtCodigo.setText(modelo.getValueAt(fila, 1).toString());
                txtTitulo.setText(modelo.getValueAt(fila, 2).toString());
                txtUD.setText(modelo.getValueAt(fila, 3).toString());
                txtAutor.setText(modelo.getValueAt(fila, 4).toString());
                txtNP.setText(modelo.getValueAt(fila, 5).toString());
                txtEditorial.setText(modelo.getValueAt(fila, 6).toString());
                txtISBN.setText(modelo.getValueAt(fila, 7).toString());
                txtFecha.setYear(Integer.parseInt(modelo.getValueAt(fila, 8).toString()));*/
            }
        }
    }//GEN-LAST:event_tablaMouseClicked

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        // TODO add your handling code here:
        buscar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed
    private void buscar(String texto) {
        modelo.setRowCount(0); // Limpiar tabla
        List<Ejemplar> lista = dao.buscarEjemplaresPorTitulo(texto); // Llamada al DAO

        for (Ejemplar e : lista) {
            modelo.addRow(new Object[]{
                e.getIdEjemplar(),
                e.getCodigoEjemplar(),
                e.getTitulo(),
                e.getAutor(),
                e.getUbicacion(),
                e.getTipoDocumento().name(),
                e.getEstado().name()
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel Nombre1;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
