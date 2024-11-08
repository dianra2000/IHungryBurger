import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    public Login() {

        setTitle("i HUNGRY BURGER");


        setSize(810, 640);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon backgroundIcon = new ImageIcon("burgerBackground.jpg");
                Image backgroundImage = backgroundIcon.getImage();

                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.err.println("Image not found: burgerBackground.jpg");
                }
            }
        };
        panel.setLayout(null);

        JLabel headerLabel = new JLabel("I HUNGRY BURGER");
        headerLabel.setForeground(Color.ORANGE);
        headerLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 46));
        headerLabel.setBounds(170, 20, 500, 50);
        panel.add(headerLabel);


        JLabel loginLabel = new JLabel("Login");
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setBounds(150, 130, 100, 30);
        panel.add(loginLabel);

        JLabel loginUsernameLabel = new JLabel("User Name");
        loginUsernameLabel.setForeground(Color.ORANGE);
        loginUsernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginUsernameLabel.setBounds(50, 180, 150, 30);
        panel.add(loginUsernameLabel);


        JTextField loginUsername = new JTextField();
        loginUsername.setName("loginusername");
        loginUsername.setBounds(150, 180, 200, 30);
        panel.add(loginUsername);


        JLabel loginPasswordLabel = new JLabel("Password");
        loginPasswordLabel.setForeground(Color.ORANGE);
        loginPasswordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginPasswordLabel.setBounds(50, 230, 100, 30);
        panel.add(loginPasswordLabel);


        JPasswordField loginPassword = new JPasswordField();
        loginPassword.setName("loginpassword");
        loginPassword.setBounds(150, 230, 200, 30);
        panel.add(loginPassword);

        // Add the login button below the login password field
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 280, 100, 30);
        panel.add(loginButton);


        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 20));
        signUpLabel.setBounds(150, 330, 100, 30);
        panel.add(signUpLabel);

        JLabel signUpUsernameLabel = new JLabel("User Name");
        signUpUsernameLabel.setForeground(Color.ORANGE);
        signUpUsernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        signUpUsernameLabel.setBounds(50, 380, 120, 30);
        panel.add(signUpUsernameLabel);


        JTextField signUpUsername = new JTextField();
        signUpUsername.setName("signupusername");
        signUpUsername.setBounds(150, 380, 200, 30);
        panel.add(signUpUsername);


        JLabel signUpPasswordLabel = new JLabel("Password");
        signUpPasswordLabel.setForeground(Color.ORANGE);
        signUpPasswordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        signUpPasswordLabel.setBounds(50, 430, 100, 30);
        panel.add(signUpPasswordLabel);


        JPasswordField signUpPassword = new JPasswordField();
        signUpPassword.setName("signuppassword");
        signUpPassword.setBounds(150, 430, 200, 30);
        panel.add(signUpPassword);


        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(150, 480, 100, 30);
        panel.add(signUpButton);

        // Add action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUsername.getText();
                String password = new String(loginPassword.getPassword());

                // Check if it's the admin login
                if (username.equals("Ihungry") && password.equals("h1828")) {
                    AdminOrderView adminOrderView = new AdminOrderView();
                    adminOrderView.setVisible(true);
                    dispose();
                } else {
                    // Handle regular user login
                    if (DatabaseHandler.checkUserCredentials(username, password)) {
                        // Assuming there's a Menu class for users
                        BurgerSelect1 menu = new BurgerSelect1(username);
                        menu.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add action listener for signup button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = signUpUsername.getText();
                String password = new String(signUpPassword.getPassword());

                // Add user to the database
                DatabaseHandler.addUser(username, password);

                // Show success message
                JOptionPane.showMessageDialog(Login.this, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        JPanel footerPanel = createFooterPanel();
        footerPanel.setBounds(0, 525, 800, 75);
        panel.add(footerPanel);

        add(panel);
    }


    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new GridLayout(1, 3));
        footerPanel.setBackground(Color.LIGHT_GRAY);
        footerPanel.setPreferredSize(new Dimension(800, 75));

        footerPanel.add(createFooterSection("              Address", "No 190/A, Matara RD, Galle"));
        footerPanel.add(createFooterSection("                Contact Us", "               Ihungry Burger\n     Telephone: 0703060263\nE-mail: ihungryburger@gmail.com"));
        footerPanel.add(createFooterSection("           OPENING HOURS", "               Monday - Sunday \n             5.00pm - 11.00pm"));

        return footerPanel;
    }


    private JPanel createFooterSection(String title, String content) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setEditable(false);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setBackground(Color.LIGHT_GRAY);
        sectionPanel.add(contentArea, BorderLayout.CENTER);

        return sectionPanel;
    }

    public static void main(String[] args) {
        // Create and display the application window
        SwingUtilities.invokeLater(() -> {
            Login app = new Login();
            app.setVisible(true);
        });
    }
}