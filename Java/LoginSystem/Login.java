package LoginSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Login implements ActionListener {
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JLabel userLabel = new JLabel("Username:");
    JLabel userPassLabel = new JLabel("Password:");
    JLabel message = new JLabel("");  // Label for displaying login messages

    HashMap<String, String> copyInfo = new HashMap<String, String>();  // Stores username and password

    /* params: info - HashMap storing user credentials */
    public Login(HashMap<String, String > info) {
        copyInfo = info; // Initialize with user provided credentials

        // Set component bounds and styles
        userLabel.setBounds(50,100,75,25);
        userPassLabel.setBounds(50,160,75,25);
        message.setBounds(125,270,250,35);
        message.setFont(new Font(null, Font.ITALIC, 25));
        userField.setBounds(125,100,250,30);
        passwordField.setBounds(125,160,250,30);
        loginButton.setBounds(125,220,125,30);
        resetButton.setBounds(250,220,125,30);

        // Make buttons unfocusable and add action listeners
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        // Add components to JFrame
        frame.add(userLabel);
        frame.add(userPassLabel);
        frame.add(userField);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(message);

        // Configure the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(173, 216, 230));  // Set a light blue background
        frame.setSize(460, 460);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    /* params: e - ActionEvent to determine which component triggered the event */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Reset button logic
        if (e.getSource() == resetButton) {
            userField.setText("");  // Clear username field
            passwordField.setText("");  // Clear password field
            message.setText("");  // Clear message label
        }

        // Login button logic
        if (e.getSource() == loginButton) {
            String username = userField.getText();
            String password = String.valueOf(passwordField.getPassword());

            // Validate username and password
            if (copyInfo.containsKey(username)) {
                if (copyInfo.get(username).equals(password)) {
                    message.setForeground(Color.green);
                    message.setText("Login successful!");
                    frame.dispose();  // Close the login window
                    new Welcome(username);  // Open welcome window
                }
                else {
                    message.setForeground(Color.red);
                    message.setText("Wrong password!");  // Incorrect password
                }
            }
            else {
                message.setForeground(Color.red);
                message.setText("User not found!");  // Username not found
            }
        }
    }
}
