/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RedImg {
    public void setImageOnButton(JButton boton, String rutaImagen) {
    // Cargar la imagen desde el recurso (carpeta src)
    ImageIcon icon = new ImageIcon(getClass().getResource(rutaImagen));
    
    // Escalar la imagen al tamaño del botón
    Image img = icon.getImage().getScaledInstance(
            boton.getWidth(), 
            boton.getHeight(), 
            Image.SCALE_SMOOTH
    );
    
    // Asignar el icono redimensionado al botón
    boton.setIcon(new ImageIcon(img));
    
    // Opcional: quitar texto y bordes para que solo se vea la imagen
    boton.setText("");
    boton.setBorderPainted(false);
    boton.setContentAreaFilled(false);
}
}
