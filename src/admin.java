import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class admin {
    private JTextField insertarlib;
    private JTextField textField1;
    private JTextField textField2;// Campo para nombre del libro
    private JComboBox<String> comboBox1;
    private JLabel texto;
    private JButton insertar;
    public JPanel mainPanel;

    public admin() {
        // Inicialización de JComboBox con opciones
        comboBox1.setModel(new DefaultComboBoxModel<>(new String[]{"Terror", "Fantasía", "Romance", "Aventura"}));

        // Acción para insertar el libro en la base de datos
        insertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) comboBox1.getSelectedItem();
                String bookName = insertarlib.getText().trim();

                // Validación de la entrada
                if (selectedCategory == null || selectedCategory.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una categoría.");
                    return;
                }
                if (bookName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre del libro.");
                    return;
                }

                // Conexión y ejecución SQL
                Connection conn = null;
                PreparedStatement pstmt = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
                    String sql = "INSERT INTO terror (nombre) VALUES (?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, bookName);

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Libro registrado correctamente.");
                    insertarlib.setText(""); // Limpiar el campo de texto
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al insertar libro: " + ex.getMessage());
                } finally {
                    // Cerrar recursos en finally para garantizar el cierre
                    try {
                        if (pstmt != null) pstmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
                    }
                }
            }
        });

        // Acción para actualizar el JLabel con la información de la base de datos según la categoría seleccionada
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) comboBox1.getSelectedItem();

                // Validación de la selección
                if (selectedCategory == null || selectedCategory.isEmpty()) {
                    texto.setText("Por favor, seleccione una categoría.");
                    return;
                }

                // Conexión y ejecución SQL
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
                    String sql = "SELECT nombre FROM terror WHERE nombre = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, selectedCategory);
                    rs = pstmt.executeQuery();

                    // Construir el texto para el JLabel
                    StringBuilder result = new StringBuilder("Libros en la categoría '" + selectedCategory + "':\n");
                    while (rs.next()) {
                        result.append(rs.getString("nombre")).append("\n");
                    }
                    texto.setText(result.toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al consultar la base de datos: " + ex.getMessage());
                } finally {
                    // Cerrar recursos en finally para garantizar el cierre
                    try {
                        if (rs != null) rs.close();
                        if (pstmt != null) pstmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
                    }
                }
            }
        });
    }
}
