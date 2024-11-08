import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BurgerSelect1 extends JFrame {
    private JCheckBox normalCheckBox;
    private JCheckBox mediumCheckBox;
    private JCheckBox largeCheckBox;
    private JSpinner normalQuantity;
    private JSpinner mediumQuantity;
    private JSpinner largeQuantity;
    private static String username;

    public BurgerSelect1(String username) {
        this.username = username;

        setTitle("I HUNGRY BURGER");
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


        JLabel headerLabel = new JLabel("I HUNGRY BURGER");
        headerLabel.setForeground(Color.ORANGE);
        headerLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 46));
        headerLabel.setBounds(170, 20, 500, 50);
        panel.add(headerLabel);


        JLabel helloLabel = new JLabel("Hello : ");
        helloLabel.setForeground(Color.ORANGE);
        helloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        helloLabel.setBounds(200, 100, 80, 30);
        panel.add(helloLabel);

        JLabel userNameLabel = new JLabel(username);
        userNameLabel.setForeground(new Color(153, 76, 0));
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userNameLabel.setBounds(280, 100, 100, 30);
        panel.add(userNameLabel);

        //Adding img , Checkbox, QtyBox
        normalCheckBox = addBurgerOption(panel, "Normal", "rs. 450.00", "normal.png", 50, 200);
        normalQuantity = addQuantitySelector(panel, 50, 360);

        mediumCheckBox = addBurgerOption(panel, "Medium", "rs. 750.00", "medium.png", 300, 200);
        mediumQuantity = addQuantitySelector(panel, 300, 360);

        largeCheckBox = addBurgerOption(panel, "Large", "rs. 950.00", "large.png", 550, 200);
        largeQuantity = addQuantitySelector(panel, 550, 360);


        JButton nextButton = new JButton(">");
        nextButton.setFont(new Font("Arial", Font.BOLD, 10));
        nextButton.setBackground(new Color(221, 204, 188));
        nextButton.setBounds(700, 480, 40, 40);
        panel.add(nextButton);

        //memory what are selected
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Integer> selectedBurgers = new HashMap<>();
                if (normalCheckBox.isSelected()) {
                    selectedBurgers.put("Normal Burger", (Integer) normalQuantity.getValue());
                }
                if (mediumCheckBox.isSelected()) {
                    selectedBurgers.put("Medium Burger", (Integer) mediumQuantity.getValue());
                }
                if (largeCheckBox.isSelected()) {
                    selectedBurgers.put("Large Burger", (Integer) largeQuantity.getValue());
                }

                // Navigate to the next page and pass the selected burgers
                BurgerSelect2 nextPage = new BurgerSelect2(selectedBurgers,username);
                nextPage.setVisible(true);
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


    private JCheckBox addBurgerOption(JPanel panel, String name, String price, String imageName, int x, int y) {
        ImageIcon burgerIcon = new ImageIcon(imageName);
        JLabel burgerLabel = new JLabel(burgerIcon);
        burgerLabel.setBounds(x, y, 200, 150);
        panel.add(burgerLabel);


        JLabel burgerNameLabel = new JLabel(name);
        burgerNameLabel.setForeground(Color.WHITE);
        burgerNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        burgerNameLabel.setBounds(x + 50, y + 160, 150, 30);
        panel.add(burgerNameLabel);


        JLabel burgerPriceLabel = new JLabel(price);
        burgerPriceLabel.setForeground(Color.ORANGE);
        burgerPriceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        burgerPriceLabel.setBounds(x + 50, y + 190, 150, 30);
        panel.add(burgerPriceLabel);


        JCheckBox burgerCheckBox = new JCheckBox();
        burgerCheckBox.setBounds(x + 75, y + 270, 20, 20);
        burgerCheckBox.setBackground(new Color(0, 0, 0, 0));
        panel.add(burgerCheckBox);

        return burgerCheckBox;
    }


    private JSpinner addQuantitySelector(JPanel panel, int x, int y) {
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        quantitySpinner.setBounds(x + 75, y+70, 50, 30);
        panel.add(quantitySpinner);
        return quantitySpinner;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BurgerSelect1 app = new BurgerSelect1(username);
            app.setVisible(true);
        });
    }
}