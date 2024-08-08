import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class preferencias {
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton DESCARGARButton;
    private JLabel texto;
    public JPanel mainPanel;

    public preferencias() {
        DESCARGARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainPanel, "Descarga exitosa!", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public void setVisible(boolean b) {
        mainPanel.setVisible(b);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Preferencias");
        frame.setContentPane(new preferencias().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

