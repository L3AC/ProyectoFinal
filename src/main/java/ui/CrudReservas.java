package ui;

import dao.PrestamoDAO;
import dao.ReservaDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import modelo.Prestamo;
import modelo.Reserva;
import modelo.Ejemplar; 

public class CrudReservas extends javax.swing.JPanel {

    private ReservaDAO reservaDAO = new ReservaDAO();
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private DefaultTableModel modelo;

    private List<Reserva> listaReservasActual;

    public CrudReservas() {
        initComponents();
        configurarTabla();
        buscar("");
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Título", "Código", "Usuario", "Rol", "Tipo Doc.", "Fecha Reserva"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tabla.setModel(modelo);

        TableColumnModel tcm = tabla.getColumnModel();
        TableColumn columna = tcm.getColumn(0);
        tcm.removeColumn(columna); 
    }

    private void buscar(String texto) {
        try {
            modelo.setRowCount(0); 
            listaReservasActual = reservaDAO.buscarReservas(texto);

            for (Reserva r : listaReservasActual) {
                modelo.addRow(new Object[] {
                        r.getIdReserva(),
                        r.getEjemplar().getTitulo(),
                        r.getEjemplar().getCodigoEjemplar(),
                        r.getUsuario().getCorreo(),
                        r.getUsuario().getRol().getNombreRol(),
                        r.getEjemplar().getTipoDocumento(),
                        r.getFechaReserva()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar reservas: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // --- ACCIÓN BOTÓN ACEPTAR ---
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para aceptar.");
            return;
        }

        Reserva reservaSeleccionada = listaReservasActual.get(fila);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Desea aceptar la reserva y crear el préstamo para: " + reservaSeleccionada.getEjemplar().getTitulo()
                        + "?",
                "Confirmar Préstamo", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (!reservaDAO.puedeCrearPrestamo(reservaSeleccionada.getUsuario())) {
                    JOptionPane.showMessageDialog(this,
                            "El usuario tiene mora pendiente o excedió el límite de préstamos.", "No permitido",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Prestamo nuevoPrestamo = new Prestamo();
                nuevoPrestamo.setIdUsuario(reservaSeleccionada.getUsuario());
                nuevoPrestamo.setIdEjemplar(reservaSeleccionada.getEjemplar());
                nuevoPrestamo.setFechaPrestamo(Date.valueOf(LocalDate.now())); // Fecha actual
                nuevoPrestamo.setEstado("Activo");

                prestamoDAO.crearPrestamo(nuevoPrestamo);

                reservaDAO.eliminarReserva(reservaSeleccionada.getIdReserva());

                JOptionPane.showMessageDialog(this, "Préstamo creado exitosamente.");
                buscar(txtBuscar.getText()); // Refrescar tabla

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar el préstamo: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    // --- ACCIÓN BOTÓN RECHAZAR/ELIMINAR ---
    private void btnRechazarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para rechazar.");
            return;
        }

        Reserva reservaSeleccionada = listaReservasActual.get(fila);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de rechazar/eliminar esta reserva?",
                "Eliminar Reserva", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (reservaDAO.eliminarReserva(reservaSeleccionada.getIdReserva())) {
                    JOptionPane.showMessageDialog(this, "Reserva eliminada.");
                    buscar(txtBuscar.getText());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la reserva.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error SQL: " + ex.getMessage());
            }
        }
    }

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {

        javax.swing.SwingUtilities.invokeLater(() -> buscar(txtBuscar.getText()));
    }

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        Nombre1 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton(); 
        btnRechazar = new javax.swing.JButton(); 
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        Nombre = new javax.swing.JLabel();
        Nombre2 = new javax.swing.JLabel();

        Nombre1.setFont(new java.awt.Font("Segoe UI", 0, 18));
        Nombre1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Nombre1.setText("Gestión de Reservas"); 

        btnAceptar.setText("Aceptar Reserva");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnRechazar.setText("Rechazar");
        btnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazarActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {} 
        ));
        tabla.setRowHeight(40);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        Nombre.setFont(new java.awt.Font("Segoe UI", 0, 14));
        Nombre.setText("Buscar por usuario, título o tipo");

        Nombre2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        Nombre2.setText("Seleccione una reserva para gestionar");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 250,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150,
                                        Short.MAX_VALUE)
                                .addGroup(mainPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(Nombre2)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnRechazar, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(20, 20, 20))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(mainPanelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainPanelLayout.createSequentialGroup()
                                                        .addGap(307, 307, 307)
                                                        .addComponent(Nombre1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                224, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addGap(128, 128, 128)
                                .addGroup(
                                        mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(Nombre)
                                                .addComponent(Nombre2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnRechazar, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(295, Short.MAX_VALUE))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(Nombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(94, 94, 94)
                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))));

        this.add(mainPanel);
    }// </editor-fold>

    // Variables declaration - do not modify
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel Nombre1;
    private javax.swing.JLabel Nombre2;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnRechazar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration
}