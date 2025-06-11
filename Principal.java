import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Principal extends JFrame implements Serializable{



    private void menuPrincipal() {

        //HERRAMIENTAS GRAFICAS
        // Crear el JFrame principal
        JFrame frame = new JFrame("Reina del Campo S.A.: Menú");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        try {
        ImageIcon icono = new ImageIcon(getClass().getResource("Recurso/autobus.png"));
        frame.setIconImage(icono.getImage());
        } catch (Exception e) {
    
        JOptionPane.showMessageDialog(frame, "No se pudo cargar el ícono de la aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        // Título
        JLabel labelTitulo = new JLabel("<html><center>Reina del Campo S.A.<br>Menú</center></html>", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        labelTitulo.setForeground(new Color(20, 70, 140));
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(labelTitulo, BorderLayout.NORTH);

        // Panel central con dos columnas
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));


        JPanel panelIzquierda = new JPanel(new GridLayout(3, 1, 15, 15));

        // Iconos para los botones
        ImageIcon iconoCarrera = new ImageIcon(Principal.class.getResource("Recurso/Carreras.png"));
        ImageIcon AñadirUnidad = new ImageIcon(Principal.class.getResource("Recurso/add.png"));
        ImageIcon iconoIngresos = new ImageIcon(Principal.class.getResource("Recurso/colon.png"));
        
        panelIzquierda.setBackground(Color.WHITE);
        panelIzquierda.add(botones("Carreras",iconoCarrera));
        panelIzquierda.add(botones("Agregar unidades", AñadirUnidad));
        panelIzquierda.add(botones("Ingresos ", iconoIngresos));

        
        JPanel panelDerecha = new JPanel(new GridLayout(3, 1, 15, 15));

        ImageIcon iconAutoría = new ImageIcon(Principal.class.getResource("Recurso/Autor.png"));
        ImageIcon iconAdmin = new ImageIcon(Principal.class.getResource("Recurso/DB.png"));
        ImageIcon iconUsuarios = new ImageIcon(Principal.class.getResource("Recurso/agregar-usuario.png"));
        
        panelDerecha.setBackground(Color.WHITE);
        panelDerecha.add(botones("Autoría",iconAutoría));
        panelDerecha.add(botones("Administración de DB",iconAdmin));
        panelDerecha.add(botones("Usuarios",iconUsuarios));

    
        panelCentral.add(panelIzquierda);
        panelCentral.add(panelDerecha);

        frame.add(panelCentral, BorderLayout.CENTER);
        frame.getContentPane().setBackground(new Color(240, 245, 252));
        frame.setVisible(true);
    }
    private JButton botones(String texto, ImageIcon icono) {
        JButton btn = new JButton(texto, icono);
        Color colorBase = new Color(20, 113, 159);
        Color colorHover = new Color(30, 140, 190);

        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorBase);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(10);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(colorBase);
            }
        });
        // Acción del botón para cada opción del menú
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (texto) {
                    case "Carreras":
                        carrera();
                        break;
                    case "Agregar unidades":
                    choferes chofer = new choferes();
                        break;
                    case "Ingresos ":
                        ingresosPorUnidad();
                        break;
                    case "Autoría":
                    new Autores().mostrarAutores();
                        break;
                    case "Administración de DB":
                    new DB().mostrarGUI();
                        break;
                    case "Usuarios":
                        Registro registroUsuarios = new Registro();
                        break;
                }
            }
        });

        return btn;
    }






  // Método para consultar ingresos por unidad
    private void ingresosPorUnidad() {
    JFrame frame = new JFrame("Ingresos por Unidad");
    frame.setResizable(false);
    frame.setSize(500, 400);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Container contenedor = frame.getContentPane();
    contenedor.setLayout(null);
    contenedor.setBackground(new Color(246, 239, 239));

    JLabel lblTitulo = new JLabel("Consultar Ingresos por Unidad");
    lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 18));
    lblTitulo.setBounds(120, 20, 300, 30);
    contenedor.add(lblTitulo);

    JLabel lblUnidad = new JLabel("Número de Unidad:");
    lblUnidad.setBounds(50, 80, 150, 20);
    contenedor.add(lblUnidad);

    JTextField txtUnidad = new JTextField();
    txtUnidad.setBounds(200, 80, 150, 25);
    contenedor.add(txtUnidad);

    JTextArea areaResultados = new JTextArea();
    areaResultados.setEditable(false);
    JScrollPane scroll = new JScrollPane(areaResultados);
    scroll.setBounds(50, 150, 400, 150);
    contenedor.add(scroll);

    JButton btnConsultar = new JButton("Consultar");
    btnConsultar.setBounds(180, 320, 120, 30);
    btnConsultar.setBackground(new Color(20, 113, 159));
    btnConsultar.setForeground(Color.WHITE);
    contenedor.add(btnConsultar);

btnConsultar.addActionListener(e -> {
    // Obtener el número de unidad ingresado por el usuario
    String unidad = txtUnidad.getText().trim();

    // Validar que el campo no esté vacío
    if (unidad.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Ingrese un número de unidad.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Conexión a la base de datos y consulta de los datos de la unidad
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815")) {

        // Consulta SQL para obtener los datos de la carrera correspondiente al número de unidad
        String sql = "SELECT Pasajeros, Cañas_Liberia_Total, Liberia_Cañas_Total FROM carreras WHERE idCarreras = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, unidad); // Establecer el parámetro de búsqueda (número de unidad)

        ResultSet rs = stmt.executeQuery(); // Ejecutar la consulta

        // Verificar si se encontró un resultado
        if (rs.next()) {
            // Extraer los valores de la base de datos
            int pasajeros = rs.getInt("Pasajeros");
            int totalCL = rs.getInt("Cañas_Liberia_Total");
            int totalLC = rs.getInt("Liberia_Cañas_Total");

            // Mostrar los resultados en el área de texto
            areaResultados.setText(
                "Unidad: " + unidad + "\n" +
                "Pasajeros: " + pasajeros + "\n" +
                "Total Cañas → Liberia: " + totalCL + "\n" +
                "Total Liberia → Cañas: " + totalLC
            );
        } else {
            // Si no se encontraron resultados para la unidad ingresada
            areaResultados.setText("No se encontraron datos para la unidad " + unidad + ".");
        }

        // Cierre de recursos
        rs.close();
        stmt.close();

    } catch (SQLException ex) {
        // En caso de error en la conexión o consulta, mostrar un mensaje de error
        ex.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Error al consultar la base de datos:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
});
    // Mostrar el JFrame
    frame.setVisible(true);
}

    // Método para iniciar la carrera

 // Este método crea una ventana para iniciar una carrera, donde el usuario puede ingresar su nombre, el código de la unidad y seleccionar el sentido de la carrera.
public void carrera() {
    JFrame frame = new JFrame("Validación");
    frame.setResizable(false);
    frame.setSize(400, 400);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Container contenedor = frame.getContentPane();
    contenedor.setLayout(null);
    contenedor.setBackground(new Color(246, 239, 239));

    JLabel labelTitulo = new JLabel("Validación de Carrera");
    labelTitulo.setFont(new Font("Times New Roman", Font.BOLD, 18));
    labelTitulo.setBounds(100, 10, 200, 30);
    labelTitulo.setForeground(new Color(20, 113, 159));
    contenedor.add(labelTitulo);

    JLabel labelNombre = new JLabel("Ingrese usuario:");
    labelNombre.setFont(new Font("Times New Roman", Font.BOLD, 14));
    labelNombre.setBounds(20, 50, 200, 20);
    contenedor.add(labelNombre);

    JTextField Chofer = new JTextField();
    Chofer.setBounds(200, 50, 150, 25);
    contenedor.add(Chofer);

    JLabel labelUnidad = new JLabel("Ingrese Código:");
    labelUnidad.setFont(new Font("Times New Roman", Font.BOLD, 14));
    labelUnidad.setBounds(20, 90, 200, 20);
    contenedor.add(labelUnidad);

    JTextField numeroUnidad = new JTextField();
    numeroUnidad.setBounds(200, 90, 150, 25);
    contenedor.add(numeroUnidad);

    JLabel inicioCarrera = new JLabel("Seleccione el sentido de Carrera:");
    inicioCarrera.setFont(new Font("Times New Roman", Font.BOLD, 14));
    inicioCarrera.setBounds(20, 130, 250, 20);
    contenedor.add(inicioCarrera);

    JCheckBox bCañas_Liberia = new JCheckBox("Cañas - Liberia");
    bCañas_Liberia.setBounds(50, 160, 150, 20);
    bCañas_Liberia.setBackground(new Color(246, 239, 239));
    contenedor.add(bCañas_Liberia);

    JCheckBox bLiberia_Cañas = new JCheckBox("Liberia - Cañas");
    bLiberia_Cañas.setBounds(50, 190, 150, 20);
    bLiberia_Cañas.setBackground(new Color(246, 239, 239));
    contenedor.add(bLiberia_Cañas);

    ButtonGroup selecion = new ButtonGroup();
    selecion.add(bCañas_Liberia);
    selecion.add(bLiberia_Cañas);

    JTextArea explicacion = new JTextArea(
        "Aclaraciones para el uso durante las carreras:\n"
        + "- Nombre del chofer: Usuario\n"
        + "- Número de unidad: Clave de ingreso"
    );
    explicacion.setBounds(20, 220, 350, 60);
    explicacion.setEditable(false);
    explicacion.setFont(new Font("Arial", Font.PLAIN, 12));
    explicacion.setBackground(new Color(230, 230, 230));
    contenedor.add(explicacion);

    JButton aceptar = new JButton("Aceptar");
    aceptar.setBounds(140, 300, 120, 30);
    aceptar.setBackground(new Color(20, 133, 159));
    aceptar.setForeground(Color.white);
    contenedor.add(aceptar);

    
    aceptar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener y limpiar los valores ingresados por el usuario
        String nombreChofer = Chofer.getText().trim();
        String codigoTexto = numeroUnidad.getText().trim();
        boolean caniasLiberia = bCañas_Liberia.isSelected(); // Opción Cañas → Liberia
        boolean liberiaCanias = bLiberia_Cañas.isSelected(); // Opción Liberia → Cañas

        // Validar que todos los campos estén llenos y que se haya seleccionado un sentido
        if (nombreChofer.isEmpty() || codigoTexto.isEmpty() || (!caniasLiberia && !liberiaCanias)) {
            JOptionPane.showMessageDialog(frame, "Complete todos los campos y seleccione un sentido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idCarrera;
        try {
            // Convertir el texto del código a número
            idCarrera = Integer.parseInt(codigoTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "El código de unidad debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Determinar el sentido de la carrera: 2 = Cañas-Liberia, 1 = Liberia-Cañas
        int sentido = caniasLiberia ? 2 : 1;

        try {
            // Establecer conexión con la base de datos
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815");

            // Consulta SQL para validar que el chofer y la unidad existan en la tabla carreras
            String sql = "SELECT * FROM carreras WHERE nombreChofer = ? AND idCarreras = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombreChofer);
            ps.setInt(2, idCarrera);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Si existe, abrir la ventana de carrera con los datos
                new CarreraUnificada(idCarrera, nombreChofer, sentido, conn);
                frame.dispose(); // Cerrar la ventana actual
            } else {
                // Si no se encuentra coincidencia, mostrar error
                JOptionPane.showMessageDialog(frame, "El usuario o número de unidad son incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }

            ps.close();
            conn.close(); // Cerrar conexión

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

    frame.setVisible(true);
}

private void validacion (){

    int sentido;
}
// Este método crea una ventana para validar el usuario y la contraseña, y si son correctos, abre el menú principal.
private void ingreso_Usuario() {
    JFrame f = new JFrame("Validación de usuario");
    f.setTitle("Reina del Campo S.A. - Bienvenido");
    f.setSize(400, 300);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLocationRelativeTo(null);
    f.setResizable(false);
    f.setLayout(null);

    Container c = f.getContentPane();
    c.setBackground(new Color(239, 246, 252));

    JLabel labelTitulo = new JLabel("<html><center>Reina del Campo S.A<br>Bienvenido</center></html>", SwingConstants.CENTER);
    labelTitulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
    labelTitulo.setBounds(100, 10, 200, 50);
    labelTitulo.setForeground(new Color(0, 70, 140));
    c.add(labelTitulo);

    
  // Icono de la aplicación
    try {
        ImageIcon icono = new ImageIcon(getClass().getResource("/Recurso/Icono_bus.jpg"));
        f.setIconImage(icono.getImage());
    } catch (Exception e) {
    
        JOptionPane.showMessageDialog(f, "No se pudo cargar el ícono de la aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    JTextField txtUsuario = new JTextField("Usuario");
    txtUsuario.setBounds(100, 80, 200, 30);
    txtUsuario.setForeground(Color.GRAY);
    txtUsuario.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (txtUsuario.getText().equals("Usuario")) {
                txtUsuario.setText("");
                txtUsuario.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (txtUsuario.getText().isEmpty()) {
                txtUsuario.setForeground(Color.GRAY);
                txtUsuario.setText("Usuario");
            }
        }
    });
    c.add(txtUsuario);
 // Campo de contraseña no muestra el texto por defecto
    JPasswordField txtContrasena = new JPasswordField("Contraseña");
    txtContrasena.setBounds(100, 120, 200, 30);
    txtContrasena.setForeground(Color.GRAY);
    txtContrasena.setEchoChar((char) 0);

    txtContrasena.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (String.valueOf(txtContrasena.getPassword()).equals("Contraseña")) {
                txtContrasena.setText("");
                txtContrasena.setForeground(Color.BLACK);
                txtContrasena.setEchoChar('•');
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (String.valueOf(txtContrasena.getPassword()).isEmpty()) {
                txtContrasena.setForeground(Color.GRAY);
                txtContrasena.setText("Contraseña");
                txtContrasena.setEchoChar((char) 0);
            }
        }
    });
    c.add(txtContrasena);

    // Botón Consultar
    JButton btnIngresar = new JButton("Consultar");
    btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
    btnIngresar.setForeground(Color.WHITE);
    btnIngresar.setBackground(new Color(20, 113, 159));
    btnIngresar.setBounds(140, 180, 120, 40);
    btnIngresar.setFocusPainted(false);
    c.add(btnIngresar);

    // Hover efecto
    btnIngresar.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            btnIngresar.setBackground(new Color(114, 182, 216));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnIngresar.setBackground(new Color(20, 113, 159));
        }
    });

    // Acción del botón
    btnIngresar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String usuario = txtUsuario.getText().trim();
            String contrasena = String.valueOf(txtContrasena.getPassword()).trim();

            if (usuario.equals("Usuario") || contrasena.equals("Contraseña") ||
                usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(f, "Por favor, complete los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (validarUsuario(usuario, contrasena)) {
                JOptionPane.showMessageDialog(f, "Ingreso exitoso. Bienvenido " + usuario + "!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                f.dispose();
                menuPrincipal();
            } else {
            
            }
        }
    });

    f.setVisible(true);
}

private boolean validarUsuario(String usuario, String contrasena) {
    Connection conn = null;              // Conexión a la base de datos
    PreparedStatement stmt = null;      // Sentencia preparada para la consulta SQL
    ResultSet rs = null;                // Resultado de la consulta
    boolean isValid = false;            // Resultado de la validación

    try {
        // Establecer conexión con la base de datos 'proyecto1'
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815");

        // Consulta SQL que verifica si existe un registro con el login y la contraseña proporcionados
        String sql = "SELECT * FROM autentificacion WHERE login = ? AND Contraseña = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario);       // Reemplazar el primer '?' por el nombre de usuario
        stmt.setString(2, contrasena);    // Reemplazar el segundo '?' por la contraseña

        // Ejecutar la consulta y obtener los resultados
        rs = stmt.executeQuery();

        if (rs.next()) {
            // Si hay un resultado, las credenciales son válidas
            isValid = true;
        } else {
            // Si no hay resultados, mostrar mensaje de error
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        // Imprimir errores
        e.printStackTrace();
    } finally {
        
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    return isValid;
}


    public static   void main(String[] args) {
    new Principal().ingreso_Usuario();
    
    }
}