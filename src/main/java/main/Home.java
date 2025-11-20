package main;

import ui.CrudUsuarios;
import java.awt.Image;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelo.Usuario;
import ui.CrudConfiguracion;
import ui.CrudEjemplares;
import ui.CrudPrestamos;
import ui.CrudReservas;
import java.awt.Color;

public class Home extends javax.swing.JFrame {

    private final Color colorNormal = new Color(153, 102, 0);
    private final Color colorHover = new  Color(183, 132, 30);
    private final Color colorActivo = new Color(123, 72, 0);
    private JLabel labelActual;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Home.class.getName());
    private ImageIcon imagen;
    private ImageIcon icono;

    public Home() {

    }

    public Home(Usuario usuario) {
        initComponents();
        initMenuStyles();
        setLocationRelativeTo(null);
        pintarImagen(lbIcono, "/img/udb.png");
    }

    private void pintarImagen(JLabel lbl, String ruta) {
        try {
            // Usamos getResource para obtener la URL de la imagen dentro del classpath
            java.net.URL url = getClass().getResource(ruta);
            if (url != null) {
                this.imagen = new ImageIcon(url);
                this.icono = new ImageIcon(
                        this.imagen.getImage().getScaledInstance(
                                lbl.getWidth(),
                                lbl.getHeight(),
                                Image.SCALE_DEFAULT));
                lbl.setIcon(this.icono);
                this.repaint();
            } else {
                System.err.println("No se encontró la imagen en la ruta: " + ruta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        lbOpcion8 = new javax.swing.JLabel();
        lbTitulo1 = new javax.swing.JLabel();
        lbIcono = new javax.swing.JLabel();
        lbOpcion6 = new javax.swing.JLabel();
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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbOpcion1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbOpcion1MouseExited(evt);
            }
        });

        lbOpcion2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion2.setText("Ejemplares");
        lbOpcion2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbOpcion2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbOpcion2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbOpcion2MouseExited(evt);
            }
        });

        lbOpcion3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion3.setText("Prestamos");
        lbOpcion3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbOpcion3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbOpcion3MouseExited(evt);
            }
        });

        lbOpcion4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion4.setText("Configuración");
        lbOpcion4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbOpcion4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbOpcion4MouseExited(evt);
            }
        });

        lbOpcion8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion8.setText("Cerrar sesión");
        lbOpcion8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion8MouseClicked(evt);
            }
        });

        lbTitulo1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo1.setText("Encargados");
        lbTitulo1.setToolTipText("");

        lbIcono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbOpcion6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbOpcion6.setText("Reservas");
        lbOpcion6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpcion6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbOpcion6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbOpcion6MouseExited(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbOpcion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbOpcion2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbOpcion3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbOpcion4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbTitulo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lbIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbOpcion8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lbOpcion6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addComponent(lbIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lbOpcion1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbOpcion2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lbOpcion6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbOpcion3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbOpcion4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(lbOpcion8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel3.add(menuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 490));

        mainPanel.setBackground(new java.awt.Color(0, 102, 102));
        mainPanel.setLayout(new java.awt.CardLayout());
        jPanel3.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 780, 490));

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbOpcion1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion1MouseClicked
        if (labelActual != null) {
            labelActual.setBackground(colorNormal);
        }
        labelActual = lbOpcion1;
        lbOpcion1.setBackground(colorActivo);
        cargarPanel(new CrudUsuarios());
    }//GEN-LAST:event_lbOpcion1MouseClicked

    private void lbOpcion2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion2MouseClicked
        if (labelActual != null) {
            labelActual.setBackground(colorNormal);
        }
        labelActual = lbOpcion2;
        lbOpcion2.setBackground(colorActivo);
        cargarPanel(new CrudEjemplares());
    }//GEN-LAST:event_lbOpcion2MouseClicked

    private void lbOpcion3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion3MouseClicked

        // TODO add your handling code here:
        if (labelActual != null) {
            labelActual.setBackground(colorNormal);
        }
        labelActual = lbOpcion3;
        lbOpcion3.setBackground(colorActivo);
        cargarPanel(new CrudPrestamos());
    }//GEN-LAST:event_lbOpcion3MouseClicked

    private void lbOpcion4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion4MouseClicked
        if (labelActual != null) {
            labelActual.setBackground(colorNormal);
        }
        labelActual = lbOpcion4;
        lbOpcion4.setBackground(colorActivo);
        cargarPanel(new CrudConfiguracion());
    }//GEN-LAST:event_lbOpcion4MouseClicked

    private void lbOpcion8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion8MouseClicked
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_lbOpcion8MouseClicked

    private void lbOpcion6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion6MouseClicked
        // TODO add your handling code here:
        if (labelActual != null) {
            labelActual.setBackground(colorNormal);
        }
        labelActual = lbOpcion6;
        lbOpcion6.setBackground(colorActivo);
        cargarPanel(new CrudReservas());
    }//GEN-LAST:event_lbOpcion6MouseClicked

    private void lbOpcion1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion1MouseEntered
        // TODO add your handling code here:
        if (lbOpcion1 != labelActual) {
            lbOpcion1.setBackground(colorHover);
        }
    }//GEN-LAST:event_lbOpcion1MouseEntered

    private void lbOpcion1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion1MouseExited
        // TODO add your handling code here:
        if (lbOpcion1 != labelActual) {
            lbOpcion1.setBackground(colorNormal);
        }
    }//GEN-LAST:event_lbOpcion1MouseExited

    private void lbOpcion2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion2MouseEntered
        // TODO add your handling code here:
        if (lbOpcion2 != labelActual) {
            lbOpcion2.setBackground(colorHover);
        }
    }//GEN-LAST:event_lbOpcion2MouseEntered

    private void lbOpcion2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion2MouseExited
        // TODO add your handling code here:
        if (lbOpcion2 != labelActual) {
            lbOpcion2.setBackground(colorNormal);
        }
    }//GEN-LAST:event_lbOpcion2MouseExited

    private void lbOpcion6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion6MouseEntered
        // TODO add your handling code here:
        if (lbOpcion6 != labelActual) {
            lbOpcion6.setBackground(colorHover);
        }
    }//GEN-LAST:event_lbOpcion6MouseEntered

    private void lbOpcion6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion6MouseExited
        // TODO add your handling code here:
        if (lbOpcion6 != labelActual) {
            lbOpcion6.setBackground(colorNormal);
        }
    }//GEN-LAST:event_lbOpcion6MouseExited

    private void lbOpcion3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion3MouseEntered
        // TODO add your handling code here:
        if (lbOpcion3 != labelActual) {
            lbOpcion3.setBackground(colorHover);
        }
    }//GEN-LAST:event_lbOpcion3MouseEntered

    private void lbOpcion3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion3MouseExited
        // TODO add your handling code here:
        if (lbOpcion3 != labelActual) {
            lbOpcion3.setBackground(colorNormal);
        }
    }//GEN-LAST:event_lbOpcion3MouseExited

    private void lbOpcion4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion4MouseEntered
        // TODO add your handling code here:
        if (lbOpcion4 != labelActual) {
            lbOpcion4.setBackground(colorHover);
        }
    }//GEN-LAST:event_lbOpcion4MouseEntered

    private void lbOpcion4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpcion4MouseExited
        // TODO add your handling code here:
        if (lbOpcion4 != labelActual) {
            lbOpcion4.setBackground(colorNormal);
        }
    }//GEN-LAST:event_lbOpcion4MouseExited

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

    private void initMenuStyles() {
        lbOpcion1.setOpaque(true);
        lbOpcion2.setOpaque(true);
        lbOpcion3.setOpaque(true);
        lbOpcion4.setOpaque(true);
        lbOpcion6.setOpaque(true);

        lbOpcion1.setBackground(colorNormal);
        lbOpcion2.setBackground(colorNormal);
        lbOpcion3.setBackground(colorNormal);
        lbOpcion4.setBackground(colorNormal);
        lbOpcion6.setBackground(colorNormal);

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
    private javax.swing.JLabel lbOpcion6;
    private javax.swing.JLabel lbOpcion8;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    // End of variables declaration//GEN-END:variables
}
