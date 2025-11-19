/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import dao.RolDAO;
import java.util.List;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import modelo.Rol;

public class CrudConfiguracion extends javax.swing.JPanel {

    private RolDAO dao = new RolDAO();
    private DefaultTableModel modelo;
    private int fila;

    public CrudConfiguracion() {
        initComponents();
        TableColumnModel tcm = tabla.getColumnModel();
        TableColumn columna = tcm.getColumn(0);
        tcm.removeColumn(columna);
        modelo = (DefaultTableModel) tabla.getModel(); // usa el modelo de la tabla
        listar();

        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);
        lbId.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbRol = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMora = new javax.swing.JTextField();
        txtDias = new javax.swing.JTextField();
        lb = new javax.swing.JLabel();
        txtEjemplares = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        lbId = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbRol.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbRol.setText("Rol");
        lbRol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, -1, 40));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Mora diaria");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 149, -1));
        jPanel1.add(txtMora, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 164, -1));

        txtDias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiasActionPerformed(evt);
            }
        });
        jPanel1.add(txtDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 164, -1));

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Máximo días prestamo");
        jPanel1.add(lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, 149, -1));
        jPanel1.add(txtEjemplares, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 170, -1));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Máximo ejemplares a prestar");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 170, -1));

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 230, 73, 34));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "", "Nombre", "Ejemplares Máx.", "Días Máx.", "Mora diaria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 297, 840, 190));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("CONFIGURACIÓN");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, 40));

        lbId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbId.setText("LB ID");
        lbId.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lbId, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Tipo rol: ");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            int id = Integer.parseInt(lbId.getText().trim());
            if (validarCampos()) {
                // Crear objeto Rol con los datos nuevos
                Rol rolEditado = new Rol();
                rolEditado.setIdRol(id);
                rolEditado.setCantMaxPrestamo(Integer.parseInt(txtEjemplares.getText().trim()));
                rolEditado.setDiasPrestamo(Integer.parseInt(txtDias.getText().trim()));
                rolEditado.setMoraDiaria(Double.parseDouble(txtMora.getText().trim()));

                if (dao.editarRol(rolEditado)) {
                    JOptionPane.showMessageDialog(this, "Rol modificado correctamente.");
                    listar(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Error al modificar el rol.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un rol de la tabla y asegúrese que los campos numéricos sean válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void listar() {
        modelo.setRowCount(0); // Limpiar tabla
        List<Rol> lista = dao.listarRoles();
        for (Rol m : lista) {
            Rol l = (Rol) m;

            modelo.addRow(new Object[]{
                l.getIdRol(),
                l.getNombreRol(),
                l.getCantMaxPrestamo(),
                l.getDiasPrestamo(),
                l.getMoraDiaria(),
            });

        }
    }

    private boolean validarCampos() {
        if (txtEjemplares.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los ejemplares es obligatorio.");
            return false;
        }

        if (txtDias.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los dias son obligatorio.");
            return false;
        }
        if (txtMora.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La mora es obligatorio.");
            return false;
        }
        return true;
    }
    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        if (evt.getClickCount() == 1) {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                // Asumiendo que la tabla modelo es el correcto con las columnas de Rol
                lbId.setText(modelo.getValueAt(fila, 0).toString()); // id_rol
                lbRol.setText(modelo.getValueAt(fila, 1).toString());
                // No parece haber un campo para 'nombre_rol' en la interfaz gráfica
                txtEjemplares.setText(modelo.getValueAt(fila, 2).toString()); // cant_max_prestamo
                txtDias.setText(modelo.getValueAt(fila, 3).toString());       // dias_prestamo
                txtMora.setText(modelo.getValueAt(fila, 4).toString());       // mora_diaria
            }
        }
    }//GEN-LAST:event_tablaMouseClicked

    private void txtDiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbRol;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtDias;
    private javax.swing.JTextField txtEjemplares;
    private javax.swing.JTextField txtMora;
    // End of variables declaration//GEN-END:variables
}
