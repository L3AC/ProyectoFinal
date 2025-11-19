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
                new Object[][] {},
                new String[] { "ID", "Título", "Código", "Usuario", "Rol", "Tipo Doc.", "Fecha Reserva" }) {
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
        // Estilos
        java.awt.Color colorFondo = new java.awt.Color(0, 140, 153);
        java.awt.Font fuenteTitulo = new java.awt.Font("Segoe UI", 1, 24);
        java.awt.Font fuenteLabel = new java.awt.Font("Segoe UI", 0, 14);

        mainPanel = new javax.swing.JPanel();
        Nombre1 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnRechazar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        Nombre = new javax.swing.JLabel();
        Nombre2 = new javax.swing.JLabel();

        // Configuración del Panel
        this.setLayout(new java.awt.BorderLayout()); // Usamos BorderLayout para el contenedor principal
        mainPanel.setBackground(colorFondo);
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // --- Título ---
        Nombre1.setFont(fuenteTitulo);
        Nombre1.setForeground(java.awt.Color.WHITE);
        Nombre1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Nombre1.setText("Gestión de Reservas");
        mainPanel.add(Nombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 300, 40));

        // --- Buscador ---
        Nombre.setFont(fuenteLabel);
        Nombre.setForeground(java.awt.Color.WHITE);
        Nombre.setText("Buscar reserva:");
        mainPanel.add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 250, -1));

        txtBuscar.setFont(fuenteLabel);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        mainPanel.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 135, 300, 35));

        // --- Botones ---
        Nombre2.setFont(new java.awt.Font("Segoe UI", 2, 12));
        Nombre2.setForeground(java.awt.Color.WHITE);
        Nombre2.setText("Gestionar selección:");
        mainPanel.add(Nombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, -1, -1));

        btnAceptar.setText("Aceptar");
        btnAceptar.setBackground(new java.awt.Color(144, 238, 144)); // Un verde suave
        btnAceptar.setFont(fuenteLabel);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        mainPanel.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 135, 110, 35));

        btnRechazar.setText("Rechazar");
        btnRechazar.setBackground(new java.awt.Color(255, 102, 102)); // Un rojo suave
        btnRechazar.setFont(fuenteLabel);
        btnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazarActionPerformed(evt);
            }
        });
        mainPanel.add(btnRechazar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 135, 110, 35));

        // --- Tabla ---
        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {}));
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 14));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);
        mainPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 700, 250));

        this.add(mainPanel, java.awt.BorderLayout.CENTER);
    }

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