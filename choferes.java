import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*; 
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*; 
public class choferes {
   public String nombre; 
   public int numeroUnidad; 
   public double sentido; 

    public choferes() {
       JFrame frame = new JFrame("Agregar Unidades");
        frame.setResizable(false);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(20, 113, 159));
       
        
        Container contenedor = frame.getContentPane();
        contenedor.setLayout(null);
        contenedor.setBackground(new Color(246, 239, 239));

        // Herramientas
        JLabel labelNombre, labelUnidad;
        JButton aceptar;
        JTextField numeroUnidadd, chofer;
        JTextArea explicacion;

        // Etiqueta: Nombre del chofer
        labelNombre = new JLabel("Ingrese el nombre del chofer:");
        labelNombre.setFont(new Font("Times New Roman", Font.BOLD, 14));
        labelNombre.setBounds(20, 20, 200, 20);
        contenedor.add(labelNombre);

        // Campo de texto: Nombre del chofer
        chofer = new JTextField();
        chofer.setBounds(220, 20, 150, 25);
        contenedor.add(chofer);

        // Etiqueta: Número de unidad
        labelUnidad = new JLabel("Ingrese el número de unidad:");
        labelUnidad.setFont(new Font("Times New Roman", Font.BOLD, 14));
        labelUnidad.setBounds(20, 60, 200, 20);
        contenedor.add(labelUnidad);

        // Campo de texto: Número de unidad
        numeroUnidadd = new JTextField();
        numeroUnidadd.setBounds(220, 60, 150, 25);
        contenedor.add(numeroUnidadd);

        // Área de texto: Explicación
        explicacion = new JTextArea(
                "Aclaraciones para el uso durante las carreras:\n"
                + "- Nombre del chofer: Usuario\n"
                + "- Número de unidad: Clave de ingreso"
        );
        explicacion.setBounds(20, 100, 350, 60);
        explicacion.setEditable(false);
        explicacion.setFont(new Font("Arial", Font.PLAIN, 12));
        explicacion.setBackground(new Color(230, 230, 230));
        contenedor.add(explicacion);

        // Botón: Aceptar
        aceptar = new JButton("Aceptar");
        aceptar.setBounds(150, 200, 100, 30);
        aceptar.setBackground(new Color(20, 133, 159));
        aceptar.setForeground(Color.white);
        contenedor.add(aceptar);

        // Acción del botón
        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombreChofer = chofer.getText().trim();
                String numeroUnidadStr = numeroUnidadd.getText().trim();

                String datos[] ={
                    nombreChofer,
                    numeroUnidadStr
                };

                if(esvalido(datos)){
                    frame.dispose();
                }
                
            }
        });
       
             aceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                aceptar.setBackground(new Color(114, 182, 216)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                aceptar.setBackground(new Color(20, 113, 159)); 
            }
        });

         frame.setVisible(true);
       
    }
   
private boolean esvalido(String datos[]) {
    if (datos[0].isEmpty() || datos[1].isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    try {
        int numeroUnidad = Integer.parseInt(datos[1]);
        if (numeroUnidad <= 0) {
            JOptionPane.showMessageDialog(null, "El número de unidad debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "El número de unidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    if (!existencia_Unidad(datos)) {
        BDingreso(datos);  
        return true;
    } else {
        JOptionPane.showMessageDialog(null, "La unidad ya está registrada.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

      
    }
private void BDingreso(String[] datos) {// Método para insertar los datos en la base de datos
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815")) {
        String sql = "INSERT INTO carreras (nombreChofer, idCarreras, pasajeros, Cañas_Liberia_Total, Liberia_Cañas_Total) VALUES (?, ?, 0, 0, 0)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, datos[0]);
        stmt.setString(2, datos[1]);
        stmt.executeUpdate();
        stmt.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al insertar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



private boolean existencia_Unidad(String datos[]) {// Método para verificar si la unidad ya existe en la base de datos
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    boolean existe = false;

    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815");
        String sql = "SELECT * FROM carreras WHERE idCarreras = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, datos[0]);  

        rs = stmt.executeQuery();

        if (rs.next()) {
            existe = true;
        }

        

    } catch (SQLException e) {// Manejo de excepciones para errores de SQL
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al verificar el número de unidad: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException e) {}
        try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
        try { if (conn != null) conn.close(); } catch (SQLException e) {}
    }

    return existe;
}



}