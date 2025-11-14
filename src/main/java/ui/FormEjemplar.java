package ui;

import dao.EjemplarDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.lang.Integer.parseInt;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
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
import util.Validacion;

public class FormEjemplar extends javax.swing.JPanel {

    RedImg re = new RedImg();
    private EjemplarDAO dao = new EjemplarDAO();

    public FormEjemplar() {
        initComponents();
    }

    private Ejemplar ejemplarActual;
    private Ejemplar ejemplarRegistro;
    private String accion = "";

    public FormEjemplar(String accion, Ejemplar ejemplar) {
        initComponents();
        this.accion = accion;
        if ("E".equals(accion)) {
            lbTitulo.setText("Editar ejemplar");
            cbTD.setEnabled(false);
            this.ejemplarActual = ejemplar;
            cargarDatosEnFormulario(ejemplar);
        } else {
            lbTitulo.setText("Agregar ejemplar");
            cbTD.setEnabled(true);
            txtCodigo.setVisible(false);
            lbE.setVisible(false);
            txtEstado.setVisible(false);
        }
        txtFecha.setDateFormatString("dd/MM/yyyy");

        //DESABILITAR EDICION EN EL JDateChooser
        JTextField dateField = (JTextField) txtFecha.getDateEditor().getUiComponent();
        dateField.setEditable(false);
        dateField.setBackground(Color.WHITE);
        dateField.setForeground(Color.BLACK);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        txtTitulo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lbTitulo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbTD = new javax.swing.JComboBox<>();
        txtId = new javax.swing.JLabel();
        txtUbi = new javax.swing.JTextField();
        lb = new javax.swing.JLabel();
        lbC4 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        btnConfirmar = new javax.swing.JButton();
        txtC2 = new javax.swing.JFormattedTextField();
        lbC2 = new javax.swing.JLabel();
        lbC1 = new javax.swing.JLabel();
        txtC1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JLabel();
        lbE = new javax.swing.JLabel();
        txtEstado = new javax.swing.JLabel();
        txtC3 = new javax.swing.JTextField();
        lbC3 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        mainPanel.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 16, 52, 46));
        mainPanel.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 164, -1));

        jLabel9.setText("Título");
        mainPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 149, -1));

        lbTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo.setText("Ejemplar");
        mainPanel.add(lbTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 16, 290, 60));

        jLabel3.setText("Tipo de documento");
        mainPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 149, 30));

        cbTD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Libro", "Diccionario", "Mapas", "Tesis", "DVD", "VHS", "Cassettes", "CD", "Documento", "Periodicos", "Revistas" }));
        cbTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTDActionPerformed(evt);
            }
        });
        mainPanel.add(cbTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 110, 30));

        txtId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtId.setText("Id");
        mainPanel.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 117, -1));
        mainPanel.add(txtUbi, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 270, -1));

        lb.setText("Ubicación");
        mainPanel.add(lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 149, -1));

        lbC4.setText("Fecha de publicación");
        mainPanel.add(lbC4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, -1, 20));
        mainPanel.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, 160, -1));

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        mainPanel.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 340, 90, 40));
        mainPanel.add(txtC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 160, -1));

        lbC2.setText("Campo 2");
        mainPanel.add(lbC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 149, -1));

        lbC1.setText("Campo 1");
        mainPanel.add(lbC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 149, -1));
        mainPanel.add(txtC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 164, -1));

        jLabel1.setText("Autor");
        mainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 149, -1));
        mainPanel.add(txtAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 164, -1));

        txtCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtCodigo.setText("Código");
        mainPanel.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 117, -1));

        lbE.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbE.setText("Estado");
        mainPanel.add(lbE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 117, -1));

        txtEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtEstado.setText("Estado");
        mainPanel.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 117, -1));
        mainPanel.add(txtC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 164, -1));

        lbC3.setText("Campo 3");
        mainPanel.add(lbC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 149, -1));

        add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 480));
    }// </editor-fold>//GEN-END:initComponents

    private void cbTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTDActionPerformed

        try {
            //listas para cambiar la visibilidad de elementos
            List<JComponent> listaUno = Arrays.asList(lbC3, txtC3, lbC4, txtFecha);
            List<JComponent> listaDos = Arrays.asList(lbC4, txtFecha);
            List<JComponent> listaTres = Arrays.asList(lbC2, txtC2, txtC3, lbC3, txtC3, lbC4, txtFecha);
            List<JComponent> listaCuatro = Arrays.asList(lbC2, txtC2, lbC3, txtC3);
            List<JComponent> listaCinco = Arrays.asList(lbC1, txtC1, lbC2, txtC2, lbC3, txtC3, lbC4, txtFecha);
            MaskFormatter timeMask = new MaskFormatter("##:##:##");
            timeMask.setPlaceholderCharacter('0');
            txtC2.setFormatterFactory(null);
            // TODO add your handling code here:
            String seleccion = (String) cbTD.getSelectedItem();
            setVisible(true, listaCinco);
            switch (seleccion) {
                case "Libro":
                    lbC1.setText("ISBN");
                    lbC2.setText("Editorial");
                    lbC3.setText("Edicion");

                    Validacion.permitirSolo(txtC1, "^[0-9-]*$", 17);
                    Validacion.permitirSolo(txtC2, "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9.,:;()&'’\"°\\-/ ]*$", 100);
                    Validacion.permitirSolo(txtC3, "^[0-9]*$", 3);

                    setVisible(false, listaDos);//LLAMA AL METODO PARA OCULTAR ELEMENTOS
                    break;
                case "Diccionario":
                    lbC1.setText("Idioma");
                    lbC2.setText("Volumen");

                    Validacion.permitirSolo(txtC1, "^[0-9-]*$", 17);
                    Validacion.permitirSolo(txtC2, "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9.,:;()&'’\"°\\-/ ]*$", 100);
                    setVisible(false, listaUno);
                    break;
                case "Mapas":
                    lbC1.setText("Escala");
                    lbC2.setText("Tipo mapa");

                    Validacion.permitirSolo(txtC1, "^[0-9-]*$", 17);
                    Validacion.permitirSolo(txtC2, "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9.,:;()&'’\"°\\-/ ]*$", 100);
                    setVisible(false, listaUno);
                    break;
                case "Tesis":
                    lbC1.setText("Grado Académico");
                    lbC2.setText("Facultad");

                    Validacion.permitirSolo(txtC1, "^[0-9-]*$", 17);
                    Validacion.permitirSolo(txtC2, "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9.,:;()&'’\"°\\-/ ]*$", 100);
                    setVisible(false, listaUno);
                    break;
                case "DVD":
                    lbC1.setText("Género");
                    lbC2.setText("Duración");

                    Validacion.permitirSolo(txtC1, "^[A-Za-zÁÉÍÓÚáéíóúÑñ, ]*$", 100);
                    //PONER MASCARA AL JFormmated

                    txtC2.setFormatterFactory(new DefaultFormatterFactory(timeMask));
                    Validacion.permitirSolo(txtC2, "^[0-9:]*$", 8);
                    setVisible(false, listaUno);
                    break;
                case "VHS":
                    lbC1.setText("Género");
                    lbC2.setText("Duración");

                    Validacion.permitirSolo(txtC1, "^[A-Za-zÁÉÍÓÚáéíóúÑñ, ]*$", 100);
                    //PONER MASCARA AL JFormmated
                    txtC2.setFormatterFactory(new DefaultFormatterFactory(timeMask));
                    Validacion.permitirSolo(txtC2, "^[0-9:]*$", 8);
                    setVisible(false, listaUno);
                    break;
                case "Cassettes":
                    lbC1.setText("Tipo cinta");
                    lbC2.setText("Duración");

                    Validacion.permitirSolo(txtC1, "^[A-Za-zÁÉÍÓÚáéíóúÑñ, ]*$", 100);
                    //PONER MASCARA AL JFormmated
                    txtC2.setFormatterFactory(new DefaultFormatterFactory(timeMask));
                    Validacion.permitirSolo(txtC2, "^[0-9:]*$", 8);
                    setVisible(false, listaUno);
                    break;
                case "CD":
                    lbC1.setText("Tipo cinta");
                    lbC2.setText("Duración");

                    Validacion.permitirSolo(txtC1, "^[A-Za-zÁÉÍÓÚáéíóúÑñ, ]*$", 100);
                    //PONER MASCARA AL JFormmated
                    txtC2.setFormatterFactory(new DefaultFormatterFactory(timeMask));
                    Validacion.permitirSolo(txtC2, "^[0-9:]*$", 8);
                    setVisible(false, listaUno);
                    break;
                case "Documento":
                    lbC1.setText("Tipo de documento");

                    Validacion.permitirSolo(txtC1, "^[A-Za-zÁÉÍÓÚáéíóúÑñ, ]*$", 100);
                    setVisible(false, listaTres);
                    break;
                case "Periodicos":
                    lbC1.setText("Tipo periodico");

                    Validacion.permitirSolo(txtC1, "^[A-Za-zÁÉÍÓÚáéíóúÑñ, ]*$", 100);
                    setVisible(false, listaCuatro);
                    break;
                case "Revistas":
                    lbC1.setText("Tipo revista");
                    Validacion.permitirSolo(txtC1, "^[A-Za-zÁÉÍÓÚáéíóúÑñ, ]*$", 100);
                    setVisible(false, listaCuatro);
                    break;
                default:

                    break;
            }
        } catch (ParseException ex) {
            System.getLogger(FormEjemplar.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_cbTDActionPerformed
    public static void setVisible(boolean visible, List<JComponent> componentes) {
        for (JComponent c : componentes) {
            c.setVisible(visible);
        }
    }

    private void cargarPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout()); // Asegura layout adecuado
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
        if (validarCampos()) {

            try {
                String codigo = txtCodigo.getText().trim();
                String titulo = txtTitulo.getText().trim();
                String autor = txtAutor.getText().trim();
                String ubicacion = txtUbi.getText().trim();
                String tipoSeleccionado = (String) cbTD.getSelectedItem();
                Ejemplar.Estado estado = Ejemplar.Estado.valueOf("Disponible");
                Ejemplar.TipoDocumento tipoDoc = Ejemplar.TipoDocumento.valueOf(tipoSeleccionado);

                if (accion.equals("E")) {
                    // EDITAR: Actualiza el objeto existente (ejemplarActual)

                    // Actualiza los campos generales
                    ejemplarActual.setCodigoEjemplar(codigo);
                    ejemplarActual.setTitulo(titulo);
                    ejemplarActual.setAutor(autor);
                    ejemplarActual.setUbicacion(ubicacion);
                    ejemplarActual.setEstado(estado);
                    // El tipo de documento probablemente no se debería cambiar, pero si es necesario:
                    ejemplarActual.setTipoDocumento(tipoDoc); // Usar con precaución

                    // Actualiza los campos específicos según el tipo de documento
                    switch (tipoDoc) {
                        case Libro:
                            if (ejemplarActual instanceof Libro) {
                                Libro libro = (Libro) ejemplarActual;
                                libro.setIsbn(txtC1.getText().trim());
                                libro.setEditorial(txtC2.getText().trim());
                                libro.setEdicion(Integer.parseInt(txtC3.getText().toString()));
                            } else {
                                // Manejar inconsistencia: el tipo seleccionado no coincide con el tipo del objeto original
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case DVD:
                            if (ejemplarActual instanceof DVD) {
                                DVD dvd = (DVD) ejemplarActual;
                                dvd.setDuracion(LocalTime.parse(txtC2.getText().trim()));
                                dvd.setGenero(txtC1.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case CD:
                            if (ejemplarActual instanceof CD) {
                                CD cd = (CD) ejemplarActual;
                                cd.setDuracion(LocalTime.parse(txtC2.getText().trim()));
                                cd.setGenero(txtC1.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case VHS:
                            if (ejemplarActual instanceof VHS) {
                                VHS vhs = (VHS) ejemplarActual;
                                vhs.setDuracion(LocalTime.parse(txtC2.getText().trim()));
                                vhs.setGenero(txtC1.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case Diccionario:
                            if (ejemplarActual instanceof Diccionario) {
                                Diccionario dic = (Diccionario) ejemplarActual;
                                dic.setIdioma(txtC1.getText().trim());
                                dic.setVolumen(Integer.parseInt(txtC2.getText().trim()));

                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case Mapas:
                            if (ejemplarActual instanceof Mapa) {
                                Mapa mapa = (Mapa) ejemplarActual;
                                mapa.setEscala(txtC1.getText().trim());
                                mapa.setTipoMapa(txtC2.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case Tesis:
                            if (ejemplarActual instanceof Tesis) {
                                Tesis tesis = (Tesis) ejemplarActual;
                                tesis.setGradoAcademico(txtC1.getText().trim());
                                tesis.setFacultad(txtC2.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case Documento:
                            if (ejemplarActual instanceof Documento) {
                                Documento doc = (Documento) ejemplarActual;
                                doc.setTipoDocumentoDetalle(txtC1.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case Periodicos:
                            if (ejemplarActual instanceof Periodico) {
                                Periodico periodico = (Periodico) ejemplarActual;
                                periodico.setFechaPublicacion(LocalDate.parse(txtFecha.getDate().toString()));
                                periodico.setTipoPeriodico(txtC1.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case Revistas:
                            if (ejemplarActual instanceof Revista) {
                                Revista revista = (Revista) ejemplarActual;
                                revista.setFechaPublicacion(LocalDate.parse(txtFecha.getDate().toString()));
                                revista.setTipoRevista(txtC1.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        case Cassettes:
                            if (ejemplarActual instanceof Cassette) {
                                Cassette cassette = (Cassette) ejemplarActual;
                                cassette.setDuracion(LocalTime.parse(txtC2.getText().trim()));
                                cassette.setTipoCinta(txtC1.getText().trim());
                            } else {
                                JOptionPane.showMessageDialog(this, "Error: El tipo de documento no coincide con el registro original.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;

                        default:
                            JOptionPane.showMessageDialog(this, "Tipo de documento no soportado: " + tipoSeleccionado, "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Salir si el tipo no es válido
                    }

                    // Llama al DAO para actualizar la base de datos con los datos modificados de ejemplarActual
                    boolean actualizado = dao.actualizarEjemplar(ejemplarActual);
                    if (actualizado) {
                        JOptionPane.showMessageDialog(this, "Ejemplar actualizado exitosamente.");
                        // Opcional: limpiar formulario, refrescar tabla, etc.
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al actualizar el ejemplar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    // AGREGAR: Crea un nuevo objeto desde cero

                    Ejemplar nuevoEjemplar = null; // Variable para el nuevo objeto

                    switch (tipoDoc) {
                        case Libro:
                            String isbn = txtC1.getText().trim();
                            String editorial = txtC2.getText().trim();
                            Integer edicion = Integer.parseInt(txtC3.getText().trim());
                            // Crea un nuevo objeto Libro
                            nuevoEjemplar = new Libro(null, codigo, titulo, autor, ubicacion, estado, isbn, editorial, edicion);
                            break;

                        case DVD:
                            LocalTime duracionDVD = null;
                            duracionDVD = LocalTime.parse(txtC2.getText().trim());
                            String generoDVD = txtC1.getText().trim();
                            nuevoEjemplar = new DVD(null, codigo, titulo, autor, ubicacion, estado, duracionDVD, generoDVD);
                            break;

                        case CD:
                            LocalTime duracionCD = null;
                            duracionCD = LocalTime.parse(txtC2.getText().trim());
                            String generoCD = txtC1.getText().trim();
                            nuevoEjemplar = new CD(null, codigo, titulo, autor, ubicacion, estado, duracionCD, generoCD);
                            break;

                        case VHS:
                            LocalTime duracionVHS = null;
                            duracionVHS = LocalTime.parse(txtC1.getText().trim());
                            String generoVHS = txtC2.getText().trim();
                            nuevoEjemplar = new VHS(null, codigo, titulo, autor, ubicacion, estado, duracionVHS, generoVHS);
                            break;

                        case Diccionario:
                            String idioma = txtC1.getText().trim();
                            Integer volumen = null;
                            volumen = Integer.parseInt(txtC2.getText().trim());
                            nuevoEjemplar = new Diccionario(null, codigo, titulo, autor, ubicacion, estado, idioma, volumen);
                            break;

                        case Mapas:
                            String escala = txtC1.getText().trim();
                            String tipoMapa = txtC2.getText().trim();
                            nuevoEjemplar = new Mapa(null, codigo, titulo, autor, ubicacion, estado, escala, tipoMapa);
                            break;

                        case Tesis:
                            String grado = txtC1.getText().trim();
                            String facultad = txtC2.getText().trim();
                            nuevoEjemplar = new Tesis(null, codigo, titulo, autor, ubicacion, estado, grado, facultad);
                            break;

                        case Documento:
                            String tipoDetalle = txtC1.getText().trim();
                            nuevoEjemplar = new Documento(null, codigo, titulo, autor, ubicacion, estado, tipoDetalle);
                            break;

                        case Periodicos:
                            LocalDate fechaPubPeriodico = null;
                            fechaPubPeriodico = LocalDate.parse(txtFecha.getDate().toString());
                            String tipoPeriodico = txtC1.getText().trim();
                            nuevoEjemplar = new Periodico(null, codigo, titulo, autor, ubicacion, estado, fechaPubPeriodico, tipoPeriodico);
                            break;

                        case Revistas:
                            LocalDate fechaPubRevista = null;
                            fechaPubRevista = LocalDate.parse(txtFecha.getDate().toString());
                            String tipoRevista = txtC1.getText().trim();
                            nuevoEjemplar = new Revista(null, codigo, titulo, autor, ubicacion, estado, fechaPubRevista, tipoRevista);
                            break;

                        case Cassettes:
                            LocalTime duracionCassette = null;
                            duracionCassette = LocalTime.parse(txtFecha.getDate().toString());
                            String tipoCinta = txtC1.getText().trim();
                            nuevoEjemplar = new Cassette(null, codigo, titulo, autor, ubicacion, estado, duracionCassette, tipoCinta);
                            break;

                        default:
                            JOptionPane.showMessageDialog(this, "Tipo de documento no soportado: " + tipoSeleccionado, "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                    }

                    // Llama al DAO para insertar el nuevo objeto
                    boolean insertado = dao.agregarEjemplar(nuevoEjemplar);
                    if (insertado) {
                        JOptionPane.showMessageDialog(this, "Ejemplar agregado exitosamente.");
                        // Opcional: limpiar formulario, refrescar tabla, etc.
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al agregar el ejemplar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (Exception e) {
                // Manejo de excepciones más específico si es necesario
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); // Para depuración
            }
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título es obligatorio.");
            return false;
        }
        if (txtAutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El autor es obligatorio.");
            return false;
        }
        if (txtUbi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El editorial es obligatorio.");
            return false;
        }
        String seleccion = (String) cbTD.getSelectedItem();
        switch (seleccion) {
            case "Libro":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El ISBN es obligatorio.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La editorial es obligatoria.");
                    return false;
                }
                if (txtC3.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La edicion es obligatoria.");
                    return false;
                }
                break;
            case "Diccionario":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El idioma es obligatorio.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La volumen es obligatorio.");
                    return false;
                }
                break;
            case "Mapas":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La escala es obligatoria.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El tipo mapa es obligatorio.");
                    return false;
                }
                break;
            case "Tesis":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El Grado academico es obligatorio.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La facultad es obligatorio.");
                    return false;
                }
                break;
            case "DVD":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El genero es obligatorio.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La duracion es obligatorio.");
                    return false;
                }
                break;
            case "VHS":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El genero es obligatorio.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La duracion es obligatorio.");
                    return false;
                }
                break;
            case "Cassettes":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El genero es obligatorio.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La duracion es obligatorio.");
                    return false;
                }
                break;
            case "CD":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El genero es obligatorio.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La duracion es obligatorio.");
                    return false;
                }
                break;
            case "Documento":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El idioma es obligatorio.");
                    return false;
                }
                if (txtC2.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La volumen es obligatorio.");
                    return false;
                }
                break;
            case "Periodicos":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El tipo periodico es obligatorio.");
                    return false;
                }
                break;
            case "Revistas":
                if (txtC1.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El tipo revista es obligatorio.");
                    return false;
                }
                break;
            default:

                break;
        }
        return true;
    }
    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        cargarPanel(new CrudEjemplares());
    }//GEN-LAST:event_btnVolverActionPerformed

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
    private javax.swing.JPanel mainPanel;
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
