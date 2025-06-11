
        import java.awt.*;
        import java.io.Serializable;
        import java.sql.*;
        import javax.swing.*;

        public class CarreraUnificada extends JFrame implements Serializable {

            private int pasajeros, totalPasajeros, unidad, sentido;
            private double totalCarrera, totalTiket;
            private static double totalGanancias;
            private String nombreChofer;
            private Connection conn;
            //CLASE CREADA PARA UNIFICAR LAS CARRERAS DE LIBERIA A CAÑAS Y VICEVERSA
            public CarreraUnificada(int unidad, String nombreChofer, int sentido, Connection conn) {
                this.unidad = unidad;
                this.nombreChofer = nombreChofer;
                this.sentido = sentido;
                this.conn = conn;

                if (sentido == 1 || sentido == 2) {
                    inicioCarrera();
                }
            }

            public void inicioCarrera() {
                String[] paradas;
                double[] tarifas;
                String sentidoTexto;
                // Definir paradas y tarifas según el sentido
                if (sentido == 2) { 
                    sentidoTexto = "Cañas-Liberia";
                    paradas = new String[]{"Los Pumas (500 colones)", "Montenegro (800 colones)", "Bagaces (800 colones)",
                            "Pijije (1200 colones)", "Llanos del Cortéz (1200 colones)", "El Salto (1600 colones)", "Liberia (1800 colones)"};
                    tarifas = new double[]{500, 800, 800, 1200, 1200, 1600, 1800};
                } else { 
                    sentidoTexto = "Liberia-Cañas";
                    paradas = new String[]{"El salto (400 colones)", "Pijije (550 colones)", "Bagaces (800 colones)",
                            "Montenegro (1200 colones)", "Entrada Upala (1200 colones)", "Los Pumas (1600 colones)", "Cañas (1800 colones)"};
                    tarifas = new double[]{400, 550, 800, 1200, 1200, 1600, 1800};
                }

                setPasajeros(0);
                setTotalCarrera(0);
                setTotalTiket(0);

                JFrame frame = new JFrame("Iniciar Carrera");
                frame.setSize(400, 300);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                Container contenedor = frame.getContentPane();
                contenedor.setLayout(null);
                contenedor.setBackground(new Color(246, 239, 239));

                JLabel labelDestino = new JLabel("Destino:");
                labelDestino.setFont(new Font("Times New Roman", Font.BOLD, 14));
                labelDestino.setBounds(20, 20, 100, 30);
                contenedor.add(labelDestino);

                JComboBox<String> comboDestino = new JComboBox<>(paradas);
                comboDestino.setBounds(130, 20, 220, 30);
                contenedor.add(comboDestino);

                JLabel labelCantidad = new JLabel("Cantidad de pasajeros:");
                labelCantidad.setFont(new Font("Times New Roman", Font.BOLD, 14));
                labelCantidad.setBounds(20, 70, 200, 30);
                contenedor.add(labelCantidad);

                JTextField fieldCantidad = new JTextField();
                fieldCantidad.setBounds(200, 70, 150, 30);
                contenedor.add(fieldCantidad);

                JButton pagar = new JButton("Pagar");
                pagar.setBounds(50, 130, 120, 40);
                pagar.setBackground(new Color(20, 113, 159));
                pagar.setForeground(Color.WHITE);
                contenedor.add(pagar);

                JButton terminar = new JButton("Terminar");
                terminar.setBounds(200, 130, 120, 40);
                terminar.setBackground(new Color(20, 113, 159));
                terminar.setForeground(Color.WHITE);
                contenedor.add(terminar);

                // Mostrar sentido de carrera y calcular total de pasajeros y monto de acuerdo al sentido
                pagar.addActionListener(e -> {
                    boolean validacion = true;
                    int indice = comboDestino.getSelectedIndex();

                    try {
                        pasajeros = Integer.parseInt(fieldCantidad.getText().trim());
                        if (pasajeros <= 0) throw new NumberFormatException();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Ingrese una cantidad válida de pasajeros", "Error", JOptionPane.ERROR_MESSAGE);
                        validacion = false;
                    }

                    if (validacion) {
                        totalTiket = tarifas[indice] * pasajeros;
                        totalPasajeros += pasajeros;
                        mostrarFactura(paradas[indice], tarifas[indice], totalTiket, sentidoTexto);
                        calcularPorSentido(totalTiket);
                    }
                });

            terminar.addActionListener(e -> {
            calcularGanancias(totalTiket);
            
            frame.dispose();
        });
                

                frame.setVisible(true);
            }

            private void mostrarFactura(String parada, double tarifa, double totalPagar, String sentidoTexto) {
                StringBuilder factura = new StringBuilder();
                factura.append("**** FACTURA ****\n");
                factura.append("Sentido de Carrera: ").append(sentidoTexto).append("\n");
                factura.append("Parada Final: ").append(parada).append("\n");
                factura.append("Tarifa por pasajero: ").append(tarifa).append(" colones\n");
                factura.append("Número de Pasajeros: ").append(pasajeros).append("\n");
                factura.append("Monto Total: ").append(totalPagar).append(" colones\n");

                JOptionPane.showMessageDialog(null, factura.toString(), "Factura", JOptionPane.INFORMATION_MESSAGE);
            }


            //ACÁ SE ENCUENTRA LA LOGICA DE LA CONEXION A LA BASE DE DATOS PARA ACTUALIZAR LAS GANANCIAS Y PASAJEROS
private void calcularPorSentido(double totalTiket) {
    
    String columna = (sentido == 2) ? "Cañas_Liberia_Total" : "Liberia_Cañas_Total";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "Tree23815")) {
        String sql = "UPDATE carreras SET " +
                    columna + " = " + columna + " + ?, " +
                    "Pasajeros = Pasajeros + ? " +
                    "WHERE idCarreras = ? AND nombreChofer = ?";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, totalTiket);   // ganancia a sumar
        ps.setInt(2, pasajeros);       // pasajeros a sumar
        ps.setInt(3, unidad);          // ID de la unidad
        ps.setString(4, nombreChofer); // nombre del chofer
        ps.executeUpdate();
        ps.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar la carrera: " + e.getMessage());
    }
}



            // Método para calcular las ganancias totales
            // Este método es estático para que pueda ser llamado sin necesidad de instanciar la clase
            public static void calcularGanancias(double totalTiket) {
                totalGanancias += totalTiket;
            }

            public void setPasajeros(int pasajeros) { this.pasajeros = pasajeros; }
            public void setTotalCarrera(double totalCarrera) { this.totalCarrera = totalCarrera; }
            public void setTotalTiket(double totalTiket) { this.totalTiket = totalTiket; }
            public static double getTotalGanancias() { return totalGanancias; }
            public int getUnidad() { return unidad; }

            @Override
            public String toString() {
                return "Unidad: " + unidad +
                        "\nChofer: " + nombreChofer +
                        "\nPasajeros: " + pasajeros +
                        "\nTotal Carrera: " + totalCarrera +
                        "\nTotal Tiket: " + totalTiket +
                        "\nSentido: " + (sentido == 1 ? "Liberia-Cañas" : "Cañas-Liberia");
            }



        }