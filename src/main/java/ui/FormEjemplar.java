/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.sql.Date;
import modelo.Ejemplar;
import modelo.ejemplares.CD;
import modelo.ejemplares.Cassette;
import modelo.ejemplares.DVD;
import modelo.ejemplares.Diccionario;
import modelo.ejemplares.Documento;
import modelo.ejemplares.Libro;
import modelo.ejemplares.Mapa;
import modelo.ejemplares.Periodico;
import modelo.ejemplares.Revista;
import modelo.ejemplares.Tesis;
import modelo.ejemplares.VHS;
import util.RedImg;

/**
 *
 * @author LEAC2
 */
public class FormEjemplar extends javax.swing.JPanel {

    RedImg re = new RedImg();

    private Ejemplar ejemplarActual;

    public FormEjemplar() {

    }

    public FormEjemplar(String accion, Ejemplar ejemplar) {
        initComponents();
        if (accion == "E") {
            cbTD.setEnabled(false);
            this.ejemplarActual = ejemplar;
            cargarDatosEnFormulario(ejemplar);
        } else {
            cbTD.setEnabled(true);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVolver = new javax.swing.JButton();
        lbTitulo = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        txtUbi = new javax.swing.JTextField();
        lb = new javax.swing.JLabel();
        lbC1 = new javax.swing.JLabel();
        txtC1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtC3 = new javax.swing.JTextField();
        lbC2 = new javax.swing.JLabel();
        txtId = new javax.swing.JLabel();
        txtEstado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        cbTD = new javax.swing.JComboBox<>();
        lbC3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JLabel();
        lbE = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        txtFecha = new com.toedter.calendar.JDateChooser();
        lbC4 = new javax.swing.JLabel();
        txtC2 = new javax.swing.JFormattedTextField();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 16, 52, 46));

        lbTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo.setText("Ejemplar");
        add(lbTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 16, 224, 60));

        jLabel9.setText("Título");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 149, -1));
        add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 164, -1));
        add(txtUbi, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 270, -1));

        lb.setText("Ubicación");
        add(lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 149, -1));

        lbC1.setText("Campo 1");
        add(lbC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 149, -1));
        add(txtC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 164, -1));

        jLabel3.setText("Tipo de documento");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 149, 30));
        add(txtC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 164, -1));

        lbC2.setText("Campo 2");
        add(lbC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 149, -1));

        txtId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtId.setText("Id");
        add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 117, -1));

        txtEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtEstado.setText("Estado");
        add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 117, -1));

        jLabel1.setText("Autor");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 149, -1));
        add(txtAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 164, -1));

        cbTD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Libro", "Diccionario", "Mapas", "Tesis", "DVD", "VHS", "Cassettes", "CD", "Documento", "Periodicos", "Revistas" }));
        cbTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTDActionPerformed(evt);
            }
        });
        add(cbTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 110, 30));

        lbC3.setText("Campo 3");
        add(lbC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 149, -1));

        txtCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtCodigo.setText("Código");
        add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 117, -1));

        lbE.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbE.setText("Estado");
        add(lbE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 117, -1));

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 340, 90, 40));
        add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, 160, -1));

        lbC4.setText("Fecha de publicación");
        add(lbC4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, -1, 20));
        add(txtC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 160, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void cargarDatosEnFormulario(Ejemplar e) {
        // Llenar los campos comunes
        txtId.setText(String.valueOf(e.getIdEjemplar()));
        txtCodigo.setText(e.getCodigoEjemplar());
        txtTitulo.setText(e.getTitulo());
        txtAutor.setText(e.getAutor());
        txtUbi.setText(e.getUbicacion());
        cbTD.setSelectedItem(e.getTipoDocumento());
        txtEstado.setText(e.getEstado().toString());

        // Ahora, según el tipo, llenar los campos específicos
        if (e instanceof Libro) {
            Libro l = (Libro) e;
            txtC1.setText(l.getIsbn());
            txtC2.setText(l.getEditorial());
            txtC3.setText(l.getEdicion() != null ? l.getEdicion().toString() : "");
        } else if (e instanceof Diccionario) {
            Diccionario d = (Diccionario) e;
            txtC1.setText(d.getIdioma());
            txtC2.setText(d.getVolumen() != null ? d.getVolumen().toString() : "");
        } else if (e instanceof Mapa) {
            Mapa m = (Mapa) e;
            txtC1.setText(m.getEscala());
            txtC2.setText(m.getTipoMapa());
        } else if (e instanceof Tesis) {
            Tesis t = (Tesis) e;
            txtC1.setText(t.getGradoAcademico());
            txtC2.setText(t.getFacultad());
        } else if (e instanceof DVD) {
            DVD d = (DVD) e;
            txtC1.setText(d.getDuracion() != null ? d.getDuracion().toString() : "");
            txtC2.setText(d.getGenero());
        } else if (e instanceof VHS) {
            VHS v = (VHS) e;
            txtC1.setText(v.getDuracion() != null ? v.getDuracion().toString() : "");
            txtC2.setText(v.getGenero());
        } else if (e instanceof Cassette) {
            Cassette c = (Cassette) e;
            txtC1.setText(c.getDuracion() != null ? c.getDuracion().toString() : "");
            txtC2.setText(c.getTipoCinta());
        } else if (e instanceof CD) {
            CD cd = (CD) e;
            txtC1.setText(cd.getDuracion() != null ? cd.getDuracion().toString() : "");
            txtC2.setText(cd.getGenero());
        } else if (e instanceof Documento) {
            Documento doc = (Documento) e;
            txtC1.setText(doc.getTipoDocumentoDetalle());
        } else if (e instanceof Periodico) {
            Periodico p = (Periodico) e;
            txtFecha.setDate(p.getFechaPublicacion() != null ? Date.valueOf(p.getFechaPublicacion()) : null);
            txtC1.setText(p.getTipoPeriodico());
        } else if (e instanceof Revista) {
            Revista r = (Revista) e;
            txtFecha.setDate(r.getFechaPublicacion() != null ? Date.valueOf(r.getFechaPublicacion()) : null);
            txtC1.setText(r.getTipoRevista());
        }
    }

    private void cbTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTDActionPerformed
        // TODO add your handling code here:
        String seleccion = (String) cbTD.getSelectedItem();

        switch (seleccion) {
            case "Libro":

                break;
            case "Diccionario":

                break;
            case "Mapas":

                break;
            case "Tesis":

                break;
            case "DVD":

                break;
            case "VHS":

                break;
            case "Cassettes":

                break;
            case "CD":

                break;
            case "Documento":

                break;
            case "Periodicos":

                break;
            case "Revistas":

                break;
            default:

                break;
        }
    }//GEN-LAST:event_cbTDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbTD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbC1;
    private javax.swing.JLabel lbC2;
    private javax.swing.JLabel lbC3;
    private javax.swing.JLabel lbC4;
    private javax.swing.JLabel lbE;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtC1;
    private javax.swing.JFormattedTextField txtC2;
    private javax.swing.JTextField txtC3;
    private javax.swing.JLabel txtCodigo;
    private javax.swing.JLabel txtEstado;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JLabel txtId;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtUbi;
    // End of variables declaration//GEN-END:variables
}
