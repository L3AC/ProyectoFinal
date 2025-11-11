package util;

import com.toedter.calendar.JYearChooser;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Validacion {

    public static void permitirSolo(JTextField campo, String regexPermitido, int limite) {
    campo.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();

            // Permitir retroceso, tabulación y control
            if (c == '\b' || c == '\t' || c == KeyEvent.CHAR_UNDEFINED) {
                return;
            }

            String textoActual = campo.getText();

            // Verificar longitud
            if (textoActual.length() >= limite) {
                e.consume();
                return;
            }

            // Validar con regex progresivo (full match con * o +)
            String textoFinal = textoActual + c;
            if (!textoFinal.matches(regexPermitido)) {
                e.consume();
            }
        }
    });
}


    public static void permitirSoloFecha(JYearChooser campo, String regexPermitido) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (c == '\b' || c == '\t' || c == KeyEvent.CHAR_UNDEFINED) {
                    return;
                }

                String textoActual = String.valueOf(campo.getYear());
                String textoFinal = textoActual + c;

                if (!textoFinal.matches(regexPermitido)) {
                    e.consume();
                }
            }
        });
    }
}
