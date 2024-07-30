import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("SISTEMA DE BIBLIOTECA");
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new admin().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}