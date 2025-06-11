import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DB {


    public void mostrarGUI() {
        // COMPONENTES DE LA INTERFAZ
        JFrame frame = new JFrame("Administración de Base de Datos");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(180, 60, 60));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.setPreferredSize(new Dimension(120, 35));
        btnRegresar.addActionListener(e -> frame.dispose());

        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInferior.add(btnRegresar);
        frame.add(panelInferior, BorderLayout.SOUTH);

        JTabbedPane pestañas = new JTabbedPane();

        
        JPanel panelCarreras = new JPanel(new BorderLayout(10, 10));
        panelCarreras.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel controlesCarreras = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField txtIdCarrera = new JTextField();
        JTextField txtChofer = new JTextField();
        JTextField txtPasajeros = new JTextField();
        JTextField txtCL = new JTextField();
        JTextField txtLC = new JTextField();

        controlesCarreras.add(new JLabel("ID Carrera"));
        controlesCarreras.add(txtIdCarrera);
        controlesCarreras.add(new JLabel("Nombre Chofer"));
        controlesCarreras.add(txtChofer);
        controlesCarreras.add(new JLabel("Pasajeros"));
        controlesCarreras.add(txtPasajeros);
        controlesCarreras.add(new JLabel("Cañas → Liberia"));
        controlesCarreras.add(txtCL);
        controlesCarreras.add(new JLabel("Liberia → Cañas"));
        controlesCarreras.add(txtLC);

        JPanel botonesCarreras = new JPanel();
        JButton btnInsertarCarrera = crearBoton("Insertar");
        JButton btnActualizarCarrera = crearBoton("Actualizar");
        JButton btnEliminarCarrera = crearBoton("Eliminar");
        JButton btnVerCarrera = crearBoton("Ver Todos");
        botonesCarreras.add(btnInsertarCarrera);
        botonesCarreras.add(btnActualizarCarrera);
        botonesCarreras.add(btnEliminarCarrera);
        botonesCarreras.add(btnVerCarrera);
        
        JTable tablaCarreras = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Chofer", "Pasajeros", "Cañas_Lib", "Liberia_Cañas"}
        ));
        JScrollPane scrollCarreras = new JScrollPane(tablaCarreras);

        JPanel centroCarreras = new JPanel(new BorderLayout(5, 5));
        centroCarreras.add(controlesCarreras, BorderLayout.NORTH);
        centroCarreras.add(botonesCarreras, BorderLayout.CENTER);

        panelCarreras.add(new JLabel("Gestión de Carreras", SwingConstants.CENTER), BorderLayout.NORTH);
        panelCarreras.add(centroCarreras, BorderLayout.WEST);
        panelCarreras.add(scrollCarreras, BorderLayout.CENTER);

        // COMPONENTES USUARIOS
        JPanel panelUsuarios = new JPanel(new BorderLayout(10, 10));
        panelUsuarios.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel controlesUsuarios = new JPanel(new GridLayout(7, 2, 10, 10));
        JTextField txtCedula = new JTextField();
        JTextField txtNombre1 = new JTextField();
        JTextField txtNombre2 = new JTextField();
        JTextField txtApellido1 = new JTextField();
        JTextField txtApellido2 = new JTextField();
        JTextField txtLogin = new JTextField();
        JTextField txtPassword = new JTextField();

        controlesUsuarios.add(new JLabel("Cédula"));
        controlesUsuarios.add(txtCedula);
        controlesUsuarios.add(new JLabel("Nombre 1"));
        controlesUsuarios.add(txtNombre1);
        controlesUsuarios.add(new JLabel("Nombre 2"));
        controlesUsuarios.add(txtNombre2);
        controlesUsuarios.add(new JLabel("Apellido 1"));
        controlesUsuarios.add(txtApellido1);
        controlesUsuarios.add(new JLabel("Apellido 2"));
        controlesUsuarios.add(txtApellido2);
        controlesUsuarios.add(new JLabel("Login"));
        controlesUsuarios.add(txtLogin);
        controlesUsuarios.add(new JLabel("Contraseña"));
        controlesUsuarios.add(txtPassword);

        JPanel botonesUsuarios = new JPanel();
        JButton btnInsertarUsuario = crearBoton("Insertar");
        JButton btnActualizarUsuario = crearBoton("Actualizar");
        JButton btnEliminarUsuario = crearBoton("Eliminar");
        JButton btnVerUsuario = crearBoton("Ver Todos");
        botonesUsuarios.add(btnInsertarUsuario);
        botonesUsuarios.add(btnActualizarUsuario);
        botonesUsuarios.add(btnEliminarUsuario);
        botonesUsuarios.add(btnVerUsuario);

        JTable tablaUsuarios = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Cédula", "Nombre1", "Nombre2", "Apellido1", "Apellido2", "Login", "Contraseña"}
        ));
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);

        JPanel centroUsuarios = new JPanel(new BorderLayout(5, 5));
        centroUsuarios.add(controlesUsuarios, BorderLayout.NORTH);
        centroUsuarios.add(botonesUsuarios, BorderLayout.CENTER);

        panelUsuarios.add(new JLabel("Gestión de Usuarios", SwingConstants.CENTER), BorderLayout.NORTH);
        panelUsuarios.add(centroUsuarios, BorderLayout.WEST);
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);

        pestañas.add("Carreras", panelCarreras);
        pestañas.add("Autentificación", panelUsuarios);
        frame.add(pestañas);
        frame.setVisible(true);

      
  // Acción al hacer clic en el botón "Insertar Carrera"
btnInsertarCarrera.addActionListener(e -> {
    try (Connection conn = conectarDB()) {
        // Consulta SQL para insertar una nueva carrera en la tabla 'carreras'
        String sql = "INSERT INTO carreras (idCarreras, nombreChofer, pasajeros, Cañas_Liberia_Total, Liberia_Cañas_Total) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Establecer los valores desde los campos de texto
        stmt.setInt(1, Integer.parseInt(txtIdCarrera.getText().trim()));     // ID de la carrera
        stmt.setString(2, txtChofer.getText().trim());                       // Nombre del chofer
        stmt.setString(3, txtPasajeros.getText().trim());                    // Cantidad de pasajeros
        stmt.setString(4, txtCL.getText().trim());                           // Total Cañas → Liberia
        stmt.setString(5, txtLC.getText().trim());                           // Total Liberia → Cañas

        // Ejecutar la consulta
        stmt.executeUpdate();

       
        JOptionPane.showMessageDialog(null, "Carrera registrada correctamente.");
    } catch (Exception ex) {
        // Mostrar mensaje de error en caso de excepción
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }

    // Limpiar campos después de insertar
    txtIdCarrera.setText("");
    txtChofer.setText("");
    txtPasajeros.setText("");
    txtCL.setText("");
    txtLC.setText("");
});


// Acción al hacer clic en el botón "Actualizar Carrera"
btnActualizarCarrera.addActionListener(e -> {
    try (Connection conn = conectarDB()) {
        // Consulta SQL para actualizar una carrera existente en la tabla 'carreras'
        String sql = "UPDATE carreras SET nombreChofer=?, pasajeros=?, Cañas_Liberia_Total=?, Liberia_Cañas_Total=? WHERE idCarreras=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Establecer los nuevos valores desde los campos de texto
        stmt.setString(1, txtChofer.getText().trim());                       // Nuevo nombre del chofer
        stmt.setString(2, txtPasajeros.getText().trim());                    // Nueva cantidad de pasajeros
        stmt.setString(3, txtCL.getText().trim());                           // Nuevo total Cañas → Liberia
        stmt.setString(4, txtLC.getText().trim());                           // Nuevo total Liberia → Cañas
        stmt.setInt(5, Integer.parseInt(txtIdCarrera.getText().trim()));     // ID de la carrera a actualizar

        // Ejecutar la actualización
        stmt.executeUpdate();

        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(null, "Carrera actualizada correctamente.");
    } catch (Exception ex) {
        // Mostrar mensaje de error si ocurre una excepción
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }

    // Limpiar los campos de entrada después de actualizar
    txtIdCarrera.setText("");
    txtChofer.setText("");
    txtPasajeros.setText("");
    txtCL.setText("");
    txtLC.setText("");
});

 // Acción al hacer clic en el botón "Eliminar Carrera"
btnEliminarCarrera.addActionListener(e -> {
    try (Connection conn = conectarDB()) {
        // Consulta SQL para eliminar una carrera específica según su ID
        String sql = "DELETE FROM carreras WHERE idCarreras=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Establecer el ID de la carrera a eliminar desde el campo de texto
        stmt.setInt(1, Integer.parseInt(txtIdCarrera.getText().trim()));

        // Ejecutar la eliminación
        stmt.executeUpdate();

        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(null, "Carrera eliminada correctamente.");
    } catch (Exception ex) {
        // Mostrar mensaje de error en caso de excepción
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }

    // Limpiar los campos de texto tras la operación
    txtIdCarrera.setText("");
    txtChofer.setText("");
    txtPasajeros.setText("");
    txtCL.setText("");
    txtLC.setText("");
});


  // Acción al hacer clic en el botón "Insertar Usuario"
btnInsertarUsuario.addActionListener(e -> {
    try (Connection conn = conectarDB()) {
        // Consulta SQL para insertar un nuevo registro en la tabla 'autentificacion'
        String sql = "INSERT INTO autentificacion (Cedula, Nombre1, Nombre2, Apellido1, Apellido2, Login, Contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Establecer los valores capturados desde los campos de texto
        stmt.setString(1, txtCedula.getText().trim());
        stmt.setString(2, txtNombre1.getText().trim());
        stmt.setString(3, txtNombre2.getText().trim());
        stmt.setString(4, txtApellido1.getText().trim());
        stmt.setString(5, txtApellido2.getText().trim());
        stmt.setString(6, txtLogin.getText().trim());
        stmt.setString(7, txtPassword.getText().trim());

        // Ejecutar la inserción del nuevo usuario
        stmt.executeUpdate();

        
        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
    } catch (Exception ex) {
        // Mostrar mensaje de error en caso de excepción
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }

    // Limpiar 
    txtCedula.setText("");
    txtNombre1.setText("");
    txtNombre2.setText("");
    txtApellido1.setText("");
    txtApellido2.setText("");
    txtLogin.setText("");
    txtPassword.setText("");
});


// Acción al hacer clic en el botón "Actualizar Usuario"
btnActualizarUsuario.addActionListener(e -> {
    try (Connection conn = conectarDB()) {
        // Consulta SQL para actualizar un usuario existente en la tabla 'autentificacion'
        String sql = "UPDATE autentificacion SET Nombre1=?, Nombre2=?, Apellido1=?, Apellido2=?, Login=?, Contraseña=? WHERE Cedula=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Asignar los valores actualizados desde los campos de texto
        stmt.setString(1, txtNombre1.getText().trim());     // Primer nombre
        stmt.setString(2, txtNombre2.getText().trim());     // Segundo nombre
        stmt.setString(3, txtApellido1.getText().trim());   // Primer apellido
        stmt.setString(4, txtApellido2.getText().trim());   // Segundo apellido
        stmt.setString(5, txtLogin.getText().trim());       // Nombre de usuario
        stmt.setString(6, txtPassword.getText().trim());    // Contraseña
        stmt.setString(7, txtCedula.getText().trim());      // Cédula (clave primaria para identificar al usuario)

        // Ejecutar la actualización en la base de datos
        stmt.executeUpdate();

        
        JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
    } catch (Exception ex) {
        // Mostrar mensaje de error en caso de fallo
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }

    // Limpiar los campos del formulario tras la actualización
    txtCedula.setText("");
    txtNombre1.setText("");
    txtNombre2.setText("");
    txtApellido1.setText("");
    txtApellido2.setText("");
    txtLogin.setText("");
    txtPassword.setText("");
});


 // Acción al hacer clic en el botón "Eliminar Usuario"
btnEliminarUsuario.addActionListener(e -> {
    try (Connection conn = conectarDB()) {
        // Consulta SQL para eliminar un usuario basado en su cédula
        String sql = "DELETE FROM autentificacion WHERE Cedula=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Se obtiene la cédula del campo de texto, que es la clave para eliminar al usuario
        stmt.setString(1, txtCedula.getText().trim());

        // Ejecutar la eliminación en la base de datos
        stmt.executeUpdate();

        // Mostrar mensaje de confirmación al usuario
        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
    } catch (Exception ex) {
        // Mostrar mensaje de error si algo falla
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }

    // Limpiar todos los campos del formulario tras la eliminación
    txtCedula.setText("");
    txtNombre1.setText("");
    txtNombre2.setText("");
    txtApellido1.setText("");
    txtApellido2.setText("");
    txtLogin.setText("");
    txtPassword.setText("");
});



btnVerCarrera.addActionListener(e -> {
    // Obtener el modelo de la tabla para poder modificar su contenido
    DefaultTableModel model = (DefaultTableModel) tablaCarreras.getModel();

    // Limpiar las filas actuales de la tabla para mostrar datos frescos
    model.setRowCount(0);

    try (Connection conn = conectarDB()) {
        // Consulta SQL que selecciona todos los registros de la tabla 'carreras'
        String sql = "SELECT * FROM carreras";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // Iterar 
        while (rs.next()) {
            // Agregar una nueva fila al modelo de la tabla con los datos obtenidos
            model.addRow(new Object[]{
                rs.getInt("idCarreras"),                // ID de la carrera
                rs.getString("nombreChofer"),           // Nombre del chofer
                rs.getString("pasajeros"),              // Cantidad de pasajeros
                rs.getString("Cañas_Liberia_Total"),    // Total de viajes Cañas → Liberia
                rs.getString("Liberia_Cañas_Total")     // Total de viajes Liberia → Cañas
            });
        }

        // Cierre

    } catch (Exception ex) {
        // Mostrar mensaje de error si ocurre una excepción 
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }
});



btnVerUsuario.addActionListener(e -> {
    // Obtener el modelo actual de la tabla de usuarios
    DefaultTableModel model = (DefaultTableModel) tablaUsuarios.getModel();

    // Limpiar todas las filas actuales de la tabla para evitar duplicados
    model.setRowCount(0);

    try (Connection conn = conectarDB()) {
        // Consulta SQL para obtener todos los registros de la tabla 'autentificacion'
        String sql = "SELECT * FROM autentificacion";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // Iterar sobre los resultados de la consulta
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("Cedula"),       // Cédula del usuario
                rs.getString("Nombre1"),      // Primer nombre
                rs.getString("Nombre2"),      // Segundo nombre
                rs.getString("Apellido1"),    // Primer apellido
                rs.getString("Apellido2"),    // Segundo apellido
                rs.getString("Login"),        // Nombre de usuario
                rs.getString("Contraseña")    // Contraseña
            });
        }

        // El uso de try-with cierra automáticamente stmt y rs

    } catch (Exception ex) {
        // Mostrar mensaje de error si ocurre un problema en la consulta
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }
});
    }


    private Connection conectarDB() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815");
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        Color base = new Color(20, 113, 159);
        Color hover = new Color(114, 182, 216);
        btn.setBackground(base);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            @Override
            public void mouseExited(MouseEvent e) { btn.setBackground(base); }
        });
        return btn;
    }
}