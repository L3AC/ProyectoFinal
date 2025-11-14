package ui;

import dao.RolDAO;
import dao.UsuarioDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Rol;
import modelo.Usuario;
import util.Validacion;

public class FormUsuario extends javax.swing.JPanel {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private RolDAO rolDAO = new RolDAO();

    private String accion;
    private Usuario usuarioActual;
    private CrudUsuarios panelCrudUsuarios; // Referencia al panel anterior

    /**
     * Creates new form FormUsuario
     * @param accion "A" para Agregar, "E" para Editar
     * @param usuario El usuario a editar (null si es Agregar)
     * @param panelCrudUsuarios El panel CRUD para regresar
     */
    public FormUsuario(String accion, Usuario usuario, CrudUsuarios panelCrudUsuarios) {
        initComponents();
        this.accion = accion;
        this.usuarioActual = usuario;
        this.panelCrudUsuarios = panelCrudUsuarios;

        cargarRolesEnComboBox();

        // Aplicar validaciones
        Validacion.permitirSolo(txtNombre, "[A-Za-zÁÉÍÓÚáéíóúÑñ ]*", 50);
        Validacion.permitirSolo(txtApellido, "[A-Za-zÁÉÍÓÚáéíóúÑñ ]*", 50);
        Validacion.permitirSolo(txtCorreo, "[A-Za-z0-9@._+\\-]*", 100);
        // Validacion para contraseña (en tu clase Validacion parece tener un bug con '^' y '$')
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

            // Seleccionar el Rol en el ComboBox
            seleccionarRolEnComboBox(usuario.getRol().getIdRol());

            // En modo EDICIÓN, no permitimos cambiar rol ni contraseña (basado en UsuarioDAO.actualizarUsuario)
            lbContra.setVisible(false);
            txtContra.setVisible(false);
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
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<? extends Rol> list, Rol value, int index, boolean isSelected, boolean cellHasFocus) {
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
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El apellido es obligatorio.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtCorreo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El correo es obligatorio.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // Validar correo (simple)
        if (!txtCorreo.getText().trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "El formato del correo no es válido.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validación solo para modo AGREGAR
        if ("A".equals(accion)) {
            String pass = new String(txtContra.getPassword());
            if (pass.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (pass.trim().length() < 4) { // Regla de negocio simple
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (cbRol.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un rol.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        lbTitulo = new javax.swing.JLabel();
        lbId = new javax.swing.JLabel();
        txtId = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        lb = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lbContra = new javax.swing.JLabel();
        txtContra = new javax.swing.JPasswordField();
        lbRol = new javax.swing.JLabel();
        cbRol = new javax.swing.JComboBox<>();
        btnConfirmar = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        mainPanel.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 16, 80, 40));

        lbTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo.setText("Gestión de Usuario");
        mainPanel.add(lbTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 16, 290, 60));

        lbId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbId.setText("ID:");
        mainPanel.add(lbId, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, -1, -1));

        txtId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtId.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtId.setText("0");
        mainPanel.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 80, -1));

        jLabel9.setText("Nombre");
        mainPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 149, -1));
        mainPanel.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 180, -1));

        jLabel1.setText("Apellido");
        mainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 149, -1));
        mainPanel.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 180, -1));

        lb.setText("Correo Electrónico");
        mainPanel.add(lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 149, -1));
        mainPanel.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 400, -1));

        lbContra.setText("Contraseña");
        mainPanel.add(lbContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 149, -1));
        mainPanel.add(txtContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 180, -1));

        lbRol.setText("Rol de Usuario");
        mainPanel.add(lbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 250, 149, -1));

        mainPanel.add(cbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, 180, -1));

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        mainPanel.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 360, 120, 40));

        add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 480));
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // Vuelve al panel CRUD principal
        cargarPanel(this.panelCrudUsuarios);
        this.panelCrudUsuarios.cargarDatosCompletos();
        this.panelCrudUsuarios.filtrarDatos();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        if (validarCampos()) {

            if ("E".equals(accion)) {
                // --- MODO ACTUALIZAR ---
                // Tu DAO (UsuarioDAO.actualizarUsuario) solo actualiza nombre, apellido y correo.
                // Si quisieras actualizar rol o contraseña, necesitarías modificar el DAO.

                usuarioActual.setNombre(txtNombre.getText().trim());
                usuarioActual.setApellido(txtApellido.getText().trim());
                usuarioActual.setCorreo(txtCorreo.getText().trim());

                boolean actualizado = usuarioDAO.actualizarUsuario(usuarioActual);

                if (actualizado) {
                    JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
                    // Volver al panel CRUD
                    btnVolverActionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el usuario. Verifique que el correo no esté duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(this, "Error al registrar el usuario. El correo ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed


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