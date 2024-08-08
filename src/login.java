import clases.ConexionBDD;
import clases.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends JFrame {
    public JPanel mainPanel;
    private JPasswordField contrasenia;
    private JTextField usuario;
    private JButton loginButton;
    private JLabel img;
    private JLabel img1;
    private JLabel mensaje;
    private JComboBox<String> comboBox1;

    public login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("SISTEMA DE BIBLIOTECA");
                JFrame frame = new JFrame("Login");
                frame.setContentPane(new preferencias().mainPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(500, 500);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                comboBox1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = usuario.getText();
                        String password = new String(contrasenia.getPassword());
                        String role = (String) comboBox1.getSelectedItem();

                        Usuario user = authenticate(username, password, role);
                        if (user != null) {
                            if (user.getRole().equals("Usuario")) {
                                new preferencias().setVisible(true);
                            } else if (user.getRole().equals("Admin")) {
                                new admin().setVisible(true);
                            }
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(login.this, "Credenciales incorrectas.");
                        }

                    }
                });

            }
        });
    }
    private Usuario authenticate(String username, String password, String role) {
        String query = "SELECT * FROM usuarios WHERE username = ? AND password = ? AND role = ?";
        try (Connection conn = ConexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(rs.getString("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new login().setVisible(true));
    }
}

