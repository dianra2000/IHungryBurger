import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PlaceOrder extends JFrame {
    private String username;

    public PlaceOrder(Map<String, Integer> selectedBurgers) {
        this.username = username;

        setTitle("Place Your Order");
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
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        JLabel headerLabel = new JLabel("Your Order");
        headerLabel.setForeground(Color.ORANGE);
        headerLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 46));
        headerLabel.setBounds(250, 20, 300, 50);
        panel.add(headerLabel);

        // Display chosen burgers and quantities
        int y = 100;
        for (Map.Entry<String, Integer> entry : selectedBurgers.entrySet()) {
            JLabel burgerLabel = new JLabel(entry.getKey() + " x " + entry.getValue());
            burgerLabel.setForeground(Color.WHITE);
            burgerLabel.setFont(new Font("Arial", Font.BOLD, 20));
            burgerLabel.setBounds(50, y, 700, 30);
            panel.add(burgerLabel);
            y += 40;
        }

        JLabel instructionLabel = new JLabel("If you want to Place Order:");
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLabel.setBounds(50, 450, 300, 30);
        panel.add(instructionLabel);

        JButton placeOrderButton = new JButton("Click Here");
        placeOrderButton.setFont(new Font("Arial", Font.BOLD, 20));
        placeOrderButton.setForeground(Color.BLUE);
        placeOrderButton.setBackground(null);
        placeOrderButton.setBorderPainted(false);
        placeOrderButton.setContentAreaFilled(false);
        placeOrderButton.setFocusPainted(false);
        placeOrderButton.setBounds(255, 447, 200, 40);
        panel.add(placeOrderButton);

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert the order into the database and transfer details
                int orderId = DatabaseHandler.insertOrderAndTransferDetails(selectedBurgers);

                if (orderId != -1)
                {
                    JOptionPane.showMessageDialog(panel, "Order Confirmed!");
                } else {

                    JOptionPane.showMessageDialog(panel, "Failed to place order. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton previousButton = new JButton("<");
        previousButton.setFont(new Font("Arial", Font.BOLD, 10));
        previousButton.setBackground(new Color(221, 204, 188));
        previousButton.setBounds(50, 480, 40, 40);
        panel.add(previousButton);

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BurgerSelect1 previousPage = new BurgerSelect1(username);
                previousPage.setVisible(true);
                dispose();
            }
        });


        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 15));
        logoutButton.setForeground(Color.RED);
        logoutButton.setBackground(null);
        logoutButton.setBorderPainted(false);    // Remove the border
        logoutButton.setContentAreaFilled(false); // Remove the content area
        logoutButton.setFocusPainted(false);     // Remove focus border
        logoutButton.setBounds(650, 20, 100, 40);
        panel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to Login.java
                Login loginPage = new Login();
                loginPage.setVisible(true);
                dispose();
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
        SwingUtilities.invokeLater(() -> {
            // Example of selected burgers; this should be replaced with real data
            Map<String, Integer> selectedBurgers = Map.of(
                    "Normal Burger", 2,
                    "Medium Burger", 1,
                    "Double Cheese", 3,
                    "Crispy Chicken", 2
            );
            PlaceOrder app = new PlaceOrder(selectedBurgers);
            app.setVisible(true);
        });
    }
}