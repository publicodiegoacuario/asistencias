
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Ing. Diego Romero
 * @version 1.0
 * @fecha 2015-05-01
 */
public class RegistroAsistencias extends javax.swing.JFrame {

    Connection c;

    /**
     * Creates new form Login
     *
     * @param c
     */
    public RegistroAsistencias(Connection c) {
        this.c = c;
        try {
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {

        }
        initComponents();
        setIconImage(new javax.swing.ImageIcon("src/cel logo.png").getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(38, 169, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cel logo.png"))); // NOI18N
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jTextField1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 204));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setToolTipText("Ingrese su DNI");
        jTextField1.setCaretColor(new java.awt.Color(255, 0, 0));
        jTextField1.setSelectedTextColor(new java.awt.Color(255, 0, 51));
        jTextField1.setSelectionColor(new java.awt.Color(51, 255, 51));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 60, 0, 60);
        jPanel1.add(jTextField1, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jButton1.setText("Reporte diario");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jButton1, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(456, 410));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Funciones.Abrir_URL("http://diegoacuario.com/cel/2015-04-28.html");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }

    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String ced = jTextField1.getText();
        if (evt.getKeyCode() == 27 && ced.isEmpty()) {
            try {
                c.close();
            } catch (SQLException ex1) {
            }
            System.exit(0);
        }

        String tipo = "asistencia";

        if (ced.length() == 9) {
            String cedula = FuncionesCedula.corrigeCedulaNumerica(ced);
            if (cedula != null) {
                String datos[] = Funciones.existeDocente(c, cedula).split(",");
                if (datos.length > 1) {
                    String nomApe = datos[1];
                    Integer id_persona = Integer.parseInt(datos[0]);
                    if (Funciones.registraAsistencia(c, id_persona)) {
                        Funciones.visualizaDialogo(this, "<html><font color='#0000FF' size=7>"
                                + "Estimad@ " + nomApe + "<br>su " + tipo + " fue registrada a las:<br><b><center>"
                                + Funciones.obtieneHora(c) + "</b></center></font></html>", "Registro asistencia", 7000);
                    } else {
                        Funciones.visualizaDialogo(this, "<html><font color='#0000FF' size=7>"
                                + "Estimad@ " + nomApe + "<br>su " + tipo + " no fue registrada a las:<br><b><center>"
                                + Funciones.obtieneHora(c) + "</b></center></font></html>", "Registro asistencia", 7000);

                    }
                } else {
                    Funciones.visualizaDialogo(this, "<html><font color='#FF0000' size=7>"
                            + "Estimad@ verifique su número de cédula " + cedula + " <br>no se encuentra resgistrad@, intento registrar a las:<br><b><center>"
                            + Funciones.obtieneHora(c) + "</b></center></font></html>", "Registro asistencia", 5000);
                }
            } else {
                Funciones.visualizaDialogo(this, "<html><font color='#FF0000' size=7>"
                        + "Estimad@ verifique su número de cédula es incorrecto,<br>intento registrar a las:<br><b><center>"
                        + Funciones.obtieneHora(c) + "</b></center></font></html>", "Registro asistencia", 3000);
            }
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
