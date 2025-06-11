import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Autores {
    // Clase para mostrar los autores del proyecto
    public void mostrarAutores() {
        JFrame f = new JFrame("Autores");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.getContentPane().setBackground(Color.WHITE);
        f.setLayout(new BorderLayout());

        try {
        ImageIcon icono = new ImageIcon(getClass().getResource("Recurso/AutorV2.png"));
        f.setIconImage(icono.getImage());
        } catch (Exception e) {
    
        JOptionPane.showMessageDialog(f, "No se pudo cargar el ícono de la aplicación.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        
        JLabel encabezado = new JLabel("Autores del Proyecto", SwingConstants.CENTER);
        encabezado.setFont(new Font("Segoe UI", Font.BOLD, 24));
        encabezado.setForeground(new Color(20, 70, 140));
        encabezado.setOpaque(true);
        encabezado.setBackground(new Color(239, 246, 252));
        encabezado.setPreferredSize(new Dimension(800, 60));
        f.add(encabezado, BorderLayout.NORTH);

        
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(20, 113, 159));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setFocusPainted(false);
        btnVolver.setPreferredSize(new Dimension(100, 35));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener((ActionEvent e) -> f.dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(Color.WHITE);
        panelBoton.add(btnVolver);
        f.add(panelBoton, BorderLayout.SOUTH);

            // Crear los paneles para los autores
        JPanel panelIzquierdo = crearPanelAutores("Roberth Ponce Juárez", "C4I72", "Recurso/Roberh.jpg");
        JPanel panelDerecho = crearPanelAutores("Benjamín Pizarro Chavez", "C4I460", "Recurso/Benjamin.jpeg");

    
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(2);
        splitPane.setEnabled(false);
        f.add(splitPane, BorderLayout.CENTER);

        f.setVisible(true);
    }

    private JPanel crearPanelAutores(String nombre, String carne, String ruta) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        ImageIcon icono = escalarImagenOvalada(ruta, 300, 300);
        JLabel lblImagen = new JLabel(icono);
        lblImagen.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JLabel lblCarne = new JLabel(carne);
        lblCarne.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCarne.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        panel.add(lblImagen);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblNombre);
        panel.add(lblCarne);

        return panel;
    }

    // Método para escalar una imagen a un tamaño ovalado
    private ImageIcon escalarImagenOvalada(String ruta, int ancho, int alto) {
        try {
            BufferedImage imagenOriginal = ImageIO.read(new File(ruta));
            Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

            BufferedImage imagenCircular = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imagenCircular.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new Ellipse2D.Float(0, 0, ancho, alto));
            g2.drawImage(imagenEscalada, 0, 0, null);
            g2.dispose();

            return new ImageIcon(imagenCircular);
        } catch (IOException e) {
            System.err.println("Error al cargar imagen: " + ruta);
            return new ImageIcon();
        }
    }






}
