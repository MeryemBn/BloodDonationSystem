package ProjectFrames;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class login extends JFrame implements ActionListener , MouseListener{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JRadioButton donorRadioButton;
    private JRadioButton adminRadioButton;
    private JButton showPasswordButton; 
    private JButton closeButton; // Added close button
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/blooddonation";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public login() {
        setSize(766, 468);
        setUndecorated(true); // Remove frame title bar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel for the main content
        JPanel mainPanel = new JPanel() {
        };
        mainPanel.setLayout(null);

        // Close button at the top-right corner
        closeButton = new JButton("X");
        closeButton.setBounds(707, 0, 59, 54);
        closeButton.setFont(new Font("Arial", Font.BOLD, 16)); // Adjust position and size
        closeButton.addActionListener(this);
        closeButton.setFocusPainted(false); // Remove focus indication
        closeButton.setContentAreaFilled(false); // Make the button transparent
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(closeButton);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        usernameLabel.setBounds(372, 146, 148, 30);
        mainPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(516, 152, 200, 25);
        mainPanel.add(usernameField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        passwordLabel.setBounds(374, 205, 100, 25);
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(516, 205, 200, 25);
        mainPanel.add(passwordField);
        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(449, 353, 200, 40); // Set size and position
        loginButton.setBackground(Color.WHITE); // Set background color
        loginButton.setForeground(Color.RED); // Set text color
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size
        loginButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Set border
        loginButton.setFocusPainted(false); // Remove focus indication
        loginButton.addActionListener(this); // Add action listener
        loginButton.addMouseListener(this); // Add mouse listener
        mainPanel.add(loginButton);

        // Radio buttons for selecting login type
        ButtonGroup buttonGroup = new ButtonGroup();

        donorRadioButton = new JRadioButton("Donor Login");
        donorRadioButton.setForeground(Color.WHITE);
        donorRadioButton.setFont(new Font("Arial", Font.ITALIC, 14)); 
        donorRadioButton.setOpaque(false);
        donorRadioButton.setBounds(391, 277, 120, 25);
        buttonGroup.add(donorRadioButton);
        mainPanel.add(donorRadioButton);

        adminRadioButton = new JRadioButton("Admin Login");
        adminRadioButton.setForeground(Color.WHITE);
        adminRadioButton.setFont(new Font("Arial", Font.ITALIC, 14)); 
        adminRadioButton.setOpaque(false);
        adminRadioButton.setBounds(541, 277, 120, 25);
        buttonGroup.add(adminRadioButton);
        mainPanel.add(adminRadioButton);

        getContentPane().add(mainPanel);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon("D:\\Downloads\\BBMS_assets\\background.jpg"));
        lblNewLabel.setBounds(-125, 0, 891, 468);
        mainPanel.add(lblNewLabel);
        setVisible(true);
    }
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == loginButton) {
            // Change appearance when mouse enters the button
            loginButton.setBackground(Color.RED.darker());
            loginButton.setForeground(Color.WHITE);
            loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == loginButton) {
            // Change appearance when mouse exits the button
            loginButton.setBackground(UIManager.getColor("Button.background"));
            loginButton.setForeground(UIManager.getColor("Button.foreground"));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Implement mouseClicked method if needed
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Implement mousePressed method if needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Implement mouseReleased method if needed
    }
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found!");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Code for database connection and credential verification
            try {
                Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                String query = "";
                if (donorRadioButton.isSelected()) {
                    query = "SELECT * FROM donneur WHERE username = ? AND password = ?";
                } else if (adminRadioButton.isSelected()) {
                    query = "SELECT * FROM admin WHERE username = ? AND password = ?";
                }

                // Prepare the query
                if (!query.isEmpty()) {
                if (!username.equals("")||!password.equals("")) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, username);
                    statement.setString(2, password);

                    // Execute the query
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            JOptionPane.showMessageDialog(this, "Login successful!");
                            setVisible(false);
                            
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
                        }
                    }
                }
                }else {
                    JOptionPane.showMessageDialog(this, "Please fill the login fields.");

                }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a login user.");
                }

                // Remaining code for database interaction...
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database connection error!");
            }
        } else if (e.getSource() == closeButton) {
            // Close button action
            dispose(); // Close the JFrame
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new login();
            }
        });
    }
}
