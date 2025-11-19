package ui;

import dao.RolDAO;
import dao.UsuarioDAO;
import java.awt.BorderLayout;
import java.net.URL;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Rol;
import modelo.Usuario;
import util.Validacion;
import javax.swing.ImageIcon;
import java.awt.Image;

public class FormUsuario extends javax.swing.JPanel {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private RolDAO rolDAO = new RolDAO();

    private String accion;
    private Usuario usuarioActual;
    private CrudUsuarios panelCrudUsuarios; // Referencia al panel anterior

    /**
     * Creates new form FormUsuario
     * 
     * @param accion            "A" para Agregar, "E" para Editar
     * @param usuario           El usuario a editar (null si es Agregar)
     * @param panelCrudUsuarios El panel CRUD para regresar
     */
    public FormUsuario(String accion, Usuario usuario, CrudUsuarios panelCrudUsuarios) {
        initComponents();
        this.accion = accion;
        this.usuarioActual = usuario;
        this.panelCrudUsuarios = panelCrudUsuarios;

        try {
            URL urlFlecha = getClass().getResource("/img/flecha.png");
            if (urlFlecha != null) {
                ImageIcon rawIcon = new ImageIcon(urlFlecha);
                Image esc = rawIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                btnVolver.setIcon(new ImageIcon(esc));
                btnVolver.setText("");
                btnVolver.setBorderPainted(false);
                btnVolver.setContentAreaFilled(false);
                btnVolver.setFocusPainted(false);
                btnVolver.setToolTipText("Regresar");
            } else {
                System.err.println("No se encontró la imagen /img/flecha.png en el classpath.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cargarRolesEnComboBox();

        // Aplicar validaciones
        Validacion.permitirSolo(txtNombre, "[A-Za-zÁÉÍÓÚáéíóúÑñ ]*", 50);
        Validacion.permitirSolo(txtApellido, "[A-Za-zÁÉÍÓÚáéíóúÑñ ]*", 50);
        Validacion.permitirSolo(txtCorreo, "[A-Za-z0-9@._+\\-]*", 100);
        // Validacion para contraseña (en tu clase Validacion parece tener un bug con
        // '^' y '$')
        // Usaremos una validación simple de longitud
        Validacion.permitirSolo(txtContra, ".{0,30}", 30);

        if ("E".equals(accion)) {
            lbTitulo.setText("Editar Usuario");
            btnConfirmar.setText("Actualizar");

            // Llenar datos
            txtId.setText(String.valueOf(usuario.getIdUsuario()));
            txtNombre.setText(usuario.getNombre());
            txtApellido.setText(usuario.getApellido());
            txtCorreo.setText(usuario.getCorreo());
            seleccionarRolEnComboBox(usuario.getRol().getIdRol());
            txtContra.setText("");
            lbRol.setVisible(false); // Opcional: podrías permitirlo si modificas el DAO
            cbRol.setVisible(false); // Opcional:

        } else {
            // Modo AGREGAR
            lbTitulo.setText("Agregar Usuario");
            btnConfirmar.setText("Guardar");

            // Ocultar ID
            lbId.setVisible(false);
            txtId.setVisible(false);
        }
    }

    /**
     * Carga los roles desde la BD al JComboBox.
     */
    private void cargarRolesEnComboBox() {
        DefaultComboBoxModel<Rol> model = new DefaultComboBoxModel<>();
        List<Rol> roles = rolDAO.listarRoles();

        for (Rol rol : roles) {
            model.addElement(rol);
        }
        cbRol.setModel(model);

        // Hacer que el ComboBox muestre el nombre del rol
        cbRol.setRenderer(new javax.swing.ListCellRenderer<Rol>() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<? extends Rol> list, Rol value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                javax.swing.JLabel label = new javax.swing.JLabel(value == null ? "" : value.getNombreRol());
                if (isSelected) {
                    label.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                    label.setOpaque(true);
                }
                return label;
            }
        });
    }

    /**
     * Selecciona un ítem en el ComboBox basado en el idRol.
     */
    private void seleccionarRolEnComboBox(int idRol) {
        for (int i = 0; i < cbRol.getItemCount(); i++) {
            if (cbRol.getItemAt(i).getIdRol() == idRol) {
                cbRol.setSelectedIndex(i);
                return;
            }
        }
    }

    /**
     * Carga un nuevo JPanel en el contenedor principal.
     */
    private void cargarPanel(JPanel panel) {
        JPanel contenedor = (JPanel) this.getParent();
        contenedor.removeAll();
        contenedor.setLayout(new BorderLayout());
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
    }

    /**
     * Valida los campos del formulario.
     */
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El apellido es obligatorio.", "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtCorreo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El correo es obligatorio.", "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Validar correo (simple)
        if (!txtCorreo.getText().trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "El formato del correo no es válido.", "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if ("E".equals(accion)) {
            String pass = new String(txtContra.getPassword()).trim();
            if (!pass.isEmpty() && pass.length() < 4) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.",
                        "Error de validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        // Validación solo para modo AGREGAR
        if ("A".equals(accion)) {
            String pass = new String(txtContra.getPassword());
            if (pass.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.", "Error de validación",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (pass.trim().length() < 4) { // Regla de negocio simple
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.",
                        "Error de validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (cbRol.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un rol.", "Error de validación",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // Estilos
        java.awt.Color colorFondo = new java.awt.Color(0, 140, 153);
        java.awt.Font fuenteLabel = new java.awt.Font("Segoe UI", 1, 14);
        java.awt.Font fuenteInput = new java.awt.Font("Segoe UI", 0, 14);

        mainPanel = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        lbTitulo = new javax.swing.JLabel();

        // Labels
        lbId = new javax.swing.JLabel();
        txtId = new javax.swing.JLabel(); // Label para mostrar ID
        jLabel9 = new javax.swing.JLabel(); // Nombre
        jLabel1 = new javax.swing.JLabel(); // Apellido
        lb = new javax.swing.JLabel(); // Correo
        lbContra = new javax.swing.JLabel(); // Contraseña
        lbRol = new javax.swing.JLabel(); // Rol

        // Inputs
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtContra = new javax.swing.JPasswordField();
        cbRol = new javax.swing.JComboBox<>();
        btnConfirmar = new javax.swing.JButton();

        this.setLayout(new java.awt.BorderLayout());
        mainPanel.setBackground(colorFondo);
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // --- Botón Volver (Flecha) ---
        // Asumiendo que ya cargas el ícono en el constructor como tenías antes
        mainPanel.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 40));

        // --- Título ---
        lbTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lbTitulo.setForeground(java.awt.Color.WHITE);
        lbTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo.setText("Gestión de Usuario");
        mainPanel.add(lbTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 300, 40));

        // --- ID (Oculto o visible según lógica, lo ponemos discreto arriba a la
        // derecha) ---
        lbId.setFont(fuenteLabel);
        lbId.setForeground(java.awt.Color.WHITE);
        lbId.setText("ID:");
        mainPanel.add(lbId, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, -1, -1));
        txtId.setFont(fuenteLabel);
        txtId.setForeground(java.awt.Color.WHITE);
        txtId.setText("0");
        mainPanel.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 50, -1));

        // --- COLUMNA 1 (Izquierda - X=150) ---

        // Nombre
        jLabel9.setFont(fuenteLabel);
        jLabel9.setForeground(java.awt.Color.WHITE);
        jLabel9.setText("Nombre");
        mainPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 200, -1));
        txtNombre.setFont(fuenteInput);
        mainPanel.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 125, 220, 35));

        // Correo
        lb.setFont(fuenteLabel);
        lb.setForeground(java.awt.Color.WHITE);
        lb.setText("Correo Electrónico");
        mainPanel.add(lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 200, -1));
        txtCorreo.setFont(fuenteInput);
        mainPanel.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 205, 220, 35));

        // Contraseña
        lbContra.setFont(fuenteLabel);
        lbContra.setForeground(java.awt.Color.WHITE);
        lbContra.setText("Contraseña");
        mainPanel.add(lbContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 200, -1));
        txtContra.setFont(fuenteInput);
        mainPanel.add(txtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 285, 220, 35));

        // --- COLUMNA 2 (Derecha - X=410) ---

        // Apellido
        jLabel1.setFont(fuenteLabel);
        jLabel1.setForeground(java.awt.Color.WHITE);
        jLabel1.setText("Apellido");
        mainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 200, -1));
        txtApellido.setFont(fuenteInput);
        mainPanel.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 125, 220, 35));

        // Rol
        lbRol.setFont(fuenteLabel);
        lbRol.setForeground(java.awt.Color.WHITE);
        lbRol.setText("Rol");
        mainPanel.add(lbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, 200, -1));
        cbRol.setFont(fuenteInput);
        mainPanel.add(cbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 205, 220, 35));

        // --- Botón Confirmar (Centrado abajo) ---
        btnConfirmar.setText("Guardar Cambios");
        btnConfirmar.setFont(new java.awt.Font("Segoe UI", 1, 16));
        btnConfirmar.setBackground(new java.awt.Color(255, 255, 255));
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        // Centrado: (780 - 180) / 2 = 300
        mainPanel.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, 180, 45));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        this.add(mainPanel, java.awt.BorderLayout.CENTER);
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnVolverActionPerformed
        // Vuelve al panel CRUD principal
        cargarPanel(this.panelCrudUsuarios);
        this.panelCrudUsuarios.cargarDatosCompletos();
        this.panelCrudUsuarios.filtrarDatos();
    }// GEN-LAST:event_btnVolverActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnConfirmarActionPerformed
        if (validarCampos()) {

            if ("E".equals(accion)) {
                // --- MODO ACTUALIZAR ---
                // Tu DAO (UsuarioDAO.actualizarUsuario) solo actualiza nombre, apellido y
                // correo.
                // Si quisieras actualizar rol o contraseña, necesitarías modificar el DAO.

                usuarioActual.setNombre(txtNombre.getText().trim());
                usuarioActual.setApellido(txtApellido.getText().trim());
                usuarioActual.setCorreo(txtCorreo.getText().trim());
                String nuevaContra = new String(txtContra.getPassword()).trim();
                if (nuevaContra.isEmpty()) {
                    nuevaContra = null;
                }
                boolean actualizado = usuarioDAO.actualizarUsuario(usuarioActual, nuevaContra);

                if (actualizado) {
                    JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
                    // Volver al panel CRUD
                    btnVolverActionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al actualizar el usuario. Verifique que el correo no esté duplicado.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else {
                // --- MODO AGREGAR ---
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre(txtNombre.getText().trim());
                nuevoUsuario.setApellido(txtApellido.getText().trim());
                nuevoUsuario.setCorreo(txtCorreo.getText().trim());
                nuevoUsuario.setContrasena(new String(txtContra.getPassword())); // El DAO se encarga de encriptar
                nuevoUsuario.setRol((Rol) cbRol.getSelectedItem());

                boolean registrado = usuarioDAO.registrarUsuario(nuevoUsuario);

                if (registrado) {
                    JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                    // Volver al panel CRUD
                    btnVolverActionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar el usuario. El correo ya existe.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }// GEN-LAST:event_btnConfirmarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<Rol> cbRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbContra;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbRol;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JLabel txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}