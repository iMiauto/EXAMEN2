import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

/**
 * Ventana de registro de usuarios con validaciones básicas
 * y almacenamiento en la base de datos MySQL.
 */
public class Registro {

    JFrame f = new JFrame("Registro de Usuarios");

    public Registro() {
        // Configuración básica del JFrame
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(650, 480);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(246, 239, 239));

        // Icono de ventana
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("Recurso/agregar-usuarioV2.png"));
            f.setIconImage(icono.getImage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "No se pudo cargar el ícono de la aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Título superior
        JLabel lblTitulo = new JLabel("Registro de Usuarios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBounds(0, 0, 650, 50);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(230, 240, 250));
        f.add(lblTitulo);

        // Campos de texto reutilizables con placeholders
        JTextField txtcedula = Campos_Genericos("Cedula");
        txtcedula.setBounds(120, 60, 400, 30);
        f.add(txtcedula);

        JTextField txtNombre = Campos_Genericos("Nombre");
        txtNombre.setBounds(120, 100, 400, 30);
        f.add(txtNombre);

        JTextField txtNombre2 = Campos_Genericos("Segundo Nombre (opcional)");
        txtNombre2.setBounds(120, 140, 400, 30);
        f.add(txtNombre2);

        JTextField txtApellido = Campos_Genericos("Primer Apellido");
        txtApellido.setBounds(120, 180, 400, 30);
        f.add(txtApellido);

        JTextField txtApellido2 = Campos_Genericos("Segundo Apellido");
        txtApellido2.setBounds(120, 220, 400, 30);
        f.add(txtApellido2);

        JTextField txtLogin = Campos_Genericos("Login");
        txtLogin.setBounds(120, 260, 400, 30);
        f.add(txtLogin);

        JTextField txtContrasena = Campos_Genericos("Contraseña");
        txtContrasena.setBounds(120, 300, 400, 30);
        f.add(txtContrasena);

        // Campo de confirmación de contraseña con comportamiento especial
        JPasswordField txtConta_Confirmacion = new JPasswordField("Confirmar Contraseña");
        txtConta_Confirmacion.setBounds(120, 340, 400, 30);
        txtConta_Confirmacion.setForeground(Color.GRAY);
        txtConta_Confirmacion.setEchoChar((char) 0);

        // Placeholder dinámico para contraseña
        txtConta_Confirmacion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtConta_Confirmacion.getPassword()).equals("Confirmar Contraseña")) {
                    txtConta_Confirmacion.setText("");
                    txtConta_Confirmacion.setForeground(Color.BLACK);
                    txtConta_Confirmacion.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtConta_Confirmacion.getPassword()).isEmpty()) {
                    txtConta_Confirmacion.setText("Confirmar Contraseña");
                    txtConta_Confirmacion.setForeground(Color.GRAY);
                    txtConta_Confirmacion.setEchoChar((char) 0);
                }
            }
        });
        f.add(txtConta_Confirmacion);

        // Botón para registrar
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(120, 390, 180, 40);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegistrar.setBackground(new Color(20, 113, 159));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        f.add(btnRegistrar);

        // Acción al presionar "Registrar"
        btnRegistrar.addActionListener(e -> {
            String cedula = txtcedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String segundoNombre = txtNombre2.getText().trim();
            String apellido = txtApellido.getText().trim();
            String segundoApellido = txtApellido2.getText().trim();
            String login = txtLogin.getText().trim();
            String contrasena = txtContrasena.getText().trim();
            String confirmacion = txtConta_Confirmacion.getText().trim();

            String datos[] = { cedula, nombre, segundoNombre, apellido, segundoApellido, login, contrasena, confirmacion };

            try {
                if (esvalido(datos)) {
                    JOptionPane.showMessageDialog(f, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    f.dispose();
                }
            } catch (HeadlessException | SQLException ex1) {
                ex1.printStackTrace();
            }
        });

        // Botón "Volver"
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(340, 390, 180, 40);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnVolver.setBackground(new Color(114, 182, 216));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> f.dispose());
        f.add(btnVolver);

        f.setVisible(true);
    }

    /**
     * Valida los datos ingresados antes del registro.
     */
    private boolean esvalido(String[] datos) throws SQLException {
        for (String dato : datos) {
            if (dato.isEmpty() || dato.equals("Nombre") || dato.equals("Primer Apellido") ||
                dato.equals("Segundo Apellido") || dato.equals("Login") ||
                dato.equals("Contraseña") || dato.equals("Confirmar Contraseña")) {
                JOptionPane.showMessageDialog(f, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (!datos[6].equals(datos[7])) {
            JOptionPane.showMessageDialog(f, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (datos[0].length() != 9) {
            JOptionPane.showMessageDialog(f, "La cédula debe tener exactamente 9 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!existencia_Usuario(datos)) {
            return false;
        }

        DBRegistro(datos);  // Guardar si todo está bien
        return true;
    }

    /**
     * Verifica si ya existe un usuario con la misma cédula o login.
     */
    private boolean existencia_Usuario(String[] datos) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM autentificacion WHERE Cedula = ? OR login = ?")) {
            stmt.setString(1, datos[0]);
            stmt.setString(2, datos[6]);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(f, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(f, "Error al verificar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Registra al usuario en la base de datos.
     */
    private void DBRegistro(String[] datos) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815");
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO autentificacion (Cedula, Nombre1, Nombre2, Apellido1, Apellido2, login, Contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, datos[0]);
            stmt.setString(2, datos[1]);
            stmt.setString(3, datos[2]);
            stmt.setString(4, datos[3]);
            stmt.setString(5, datos[4]);
            stmt.setString(6, datos[5]);
            stmt.setString(7, datos[6]);
            stmt.executeUpdate();
        }
    }

    /**
     * Genera campos de texto con comportamiento de placeholder.
     */
    public JTextField Campos_Genericos(String placeholder) {
        JTextField campo = new JTextField(placeholder);
        campo.setForeground(Color.GRAY);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                }
            }
        });

        return campo;
    }
}
