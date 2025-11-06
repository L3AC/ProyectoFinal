/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardApp extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public DashboardApp() {
        setTitle("Sistema Biblioteca - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Crear pantallas
        JPanel homePanel = createPanel("Inicio", Color.LIGHT_GRAY);
        JPanel usersPanel = createPanel("Usuarios", Color.CYAN);
        JPanel loansPanel = createPanel("Préstamos", Color.YELLOW);

        // Agregar pantallas al contenedor con nombres únicos
        cardPanel.add(homePanel, "home");
        cardPanel.add(usersPanel, "users");
        cardPanel.add(loansPanel, "loans");

        // Panel de navegación
        JPanel navPanel = new JPanel();
        navPanel.add(new JButton(new AbstractAction("Inicio") {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "home");
            }
        }));
        navPanel.add(new JButton(new AbstractAction("Usuarios") {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "users");
            }
        }));
        navPanel.add(new JButton(new AbstractAction("Préstamos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "loans");
            }
        }));

        // Añadir componentes al JFrame
        add(navPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createPanel(String title, Color bg) {
        JPanel panel = new JPanel();
        panel.setBackground(bg);
        panel.add(new JLabel("Pantalla: " + title));
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardApp().setVisible(true));
    }
}