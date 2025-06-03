package LoginSystem;

import javax.swing.*;
import java.awt.*;

public class Welcome {
    JFrame frame = new JFrame();  // Main frame for the welcome window
    JLabel welcomeLabel = new JLabel();  // Label to display welcome message

    /* params: username - Username of the logged-in user */
    public Welcome(String username) {
        // Set welcome message with user's name
        welcomeLabel.setText("Hello " + username);
        welcomeLabel.setBounds(150,70,250,35);  // Position and size of the label
        welcomeLabel.setFont(new Font(null, Font.ITALIC, 25));  // Set font style and size

        // Add label to frame
        frame.add(welcomeLabel);

        // Configure frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(173, 216, 230));  // Set a light blue background
        frame.setSize(460, 460);  // Size of the frame
        frame.setLayout(null);  // No layout manager, using absolute positioning
        frame.setVisible(true);  // Make frame visible
        frame.setResizable(false);  // Disable resizing of the frame
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
    }
}
