package main;

import ui.CrudUsuarios;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Home extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Home.class.getName());
    private ImageIcon imagen;
    private ImageIcon icono;

    public Home() {
        initComponents();
        pintarImagen(lbIcono, "/com/mycompany/proyectofinal/img/udb.png");
    }

    private void pintarImagen(JLabel lbl, String ruta) {
        this.imagen = new ImageIcon(ruta);
        this.icono = new ImageIcon(
                this.imagen.getImage().getScaledInstance(
                        lbl.getWidth(),
                        lbl.getHeight(),
                        Image.SCALE_DEFAULT
                )
        );
        lbl.setIcon(this.icono);
        this.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu3 = new javax.swing.JMenu();
        jPanel3 = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        lbOpcion1 = new javax.swing.JLabel();
        lbOpcion2 = new javax.swing.JLabel();
        lbOpcion3 = new javax.swing.JLabel();
        lbOpcion4 = new javax.swing.JLabel();
        lbOpcion5 = new javax.swing.JLabel();
        lbTitulo1 = new javax.swing.JLabel();
        lbIcono = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();

        jMenuItem1.setText("jMenuItem1");

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuPanel.setBackground(new java.awt.Color(153, 102, 0));

        lbOpcion1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion1.setText("Usuarios");
        lbOpcion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion1MouseClicked(evt);
            }
        });

        lbOpcion2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion2.setText("Ejemplares");
        lbOpcion2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbOpcion2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion2MouseClicked(evt);
            }
        });

        lbOpcion3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion3.setText("Prestamos");
        lbOpcion3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion3MouseClicked(evt);
            }
        });

        lbOpcion4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion4.setText("Configuración");
        lbOpcion4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion4MouseClicked(evt);
            }
        });

        lbOpcion5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion5.setText("Cerrar sesión");
        lbOpcion5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion5MouseClicked(evt);
            }
        });

        lbTitulo1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo1.setText("Encargados");
        lbTitulo1.setToolTipText("");

        lbIcono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbOpcion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbOpcion2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbOpcion3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbOpcion4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbOpcion5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbTitulo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lbIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addComponent(lbIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbOpcion1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbOpcion2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(lbOpcion3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lbOpcion4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lbOpcion5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jPanel3.add(menuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 480));

        mainPanel.setBackground(new java.awt.Color(0, 102, 102));
        mainPanel.setLayout(new java.awt.CardLayout());
        jPanel3.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 680, 480));

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbOpcion1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion1MouseClicked
        cargarPanel(new CrudUsuarios());
    }//GEN-LAST:event_lbOpcion1MouseClicked

    private void lbOpcion2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbOpcion2MouseClicked

    private void lbOpcion3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbOpcion3MouseClicked

    private void lbOpcion4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbOpcion4MouseClicked

    private void lbOpcion5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbOpcion5MouseClicked

    private void cargarPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Home().setVisible(true));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel lbIcono;
    private javax.swing.JLabel lbOpcion1;
    private javax.swing.JLabel lbOpcion2;
    private javax.swing.JLabel lbOpcion3;
    private javax.swing.JLabel lbOpcion4;
    private javax.swing.JLabel lbOpcion5;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    // End of variables declaration//GEN-END:variables
}
