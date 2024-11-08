import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BurgerSelect2 extends JFrame {
    private Map<String, Integer> selectedBurgers;
    private JCheckBox doubleCheeseCheckBox;
    private JCheckBox crispyChickenCheckBox;
    private JCheckBox burgerTowerCheckBox;
    private JSpinner doubleCheeseQuantity;
    private JSpinner crispyChickenQuantity;
    private JSpinner burgerTowerQuantity;
    private static String username;

    public BurgerSelect2(Map<String, Integer> selectedBurgers,String username) {
        this.username = username;
        this.selectedBurgers = new HashMap<>(selectedBurgers); // Initialize with selected burgers from BurgerSelect1

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

        //Logged username
        JLabel userNameLabel = new JLabel(username);
        userNameLabel.setForeground(new Color(153, 76, 0));
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userNameLabel.setBounds(280, 100, 100, 30);
        panel.add(userNameLabel);

        //Adding img , Checkbox, QtyBox
        doubleCheeseCheckBox = addBurgerOption(panel, "Double Cheese", "rs. 500.00", "doubleCheese.png", 50, 200);
        doubleCheeseQuantity = addQuantitySelector(panel, 50, 360);

        crispyChickenCheckBox = addBurgerOption(panel, "Crispy Chicken", "rs. 550.00", "crispyChicken.png", 300, 200);
        crispyChickenQuantity = addQuantitySelector(panel, 300, 360);

        burgerTowerCheckBox = addBurgerOption(panel, "Burger Tower", "rs. 600.00", "burgerTower.png", 550, 100, 250, 250);
        burgerTowerQuantity = addQuantitySelector(panel, 550, 360);


        // Populate quantities if already selected in BurgerSelect1
        if (selectedBurgers.containsKey("Double Cheese")) {
            doubleCheeseCheckBox.setSelected(true);
            doubleCheeseQuantity.setValue(selectedBurgers.get("Double Cheese"));
        }
        if (selectedBurgers.containsKey("Crispy Chicken")) {
            crispyChickenCheckBox.setSelected(true);
            crispyChickenQuantity.setValue(selectedBurgers.get("Crispy Chicken"));
        }
        if (selectedBurgers.containsKey("Burger Tower")) {
            burgerTowerCheckBox.setSelected(true);
            burgerTowerQuantity.setValue(selectedBurgers.get("Burger Tower"));
        }


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


        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.setFont(new Font("Arial", Font.BOLD, 20));
        placeOrderButton.setBackground(new Color(221, 204, 188));
        placeOrderButton.setBounds(300, 480, 200, 40);
        panel.add(placeOrderButton);

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (doubleCheeseCheckBox.isSelected()) {
                    selectedBurgers.put("Double Cheese", (Integer) doubleCheeseQuantity.getValue());
                }
                if (crispyChickenCheckBox.isSelected()) {
                    selectedBurgers.put("Crispy Chicken", (Integer) crispyChickenQuantity.getValue());
                }
                if (burgerTowerCheckBox.isSelected()) {
                    selectedBurgers.put("Burger Tower", (Integer) burgerTowerQuantity.getValue());
                }

                PlaceOrder placeOrderPage = new PlaceOrder(selectedBurgers);
                placeOrderPage.setVisible(true);
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
        return addBurgerOption(panel, name, price, imageName, x, y, 200, 150);
    }

    private JCheckBox addBurgerOption(JPanel panel, String name, String price, String imageName, int x, int y, int width, int height) {
        ImageIcon burgerIcon = new ImageIcon(imageName);
        JLabel burgerLabel = new JLabel(burgerIcon);
        burgerLabel.setBounds(x, y, width, height);
        panel.add(burgerLabel);

        JLabel burgerNameLabel = new JLabel(name);
        burgerNameLabel.setForeground(Color.WHITE);
        burgerNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        burgerNameLabel.setBounds(x + 50, y + height + 10, 150, 30);
        panel.add(burgerNameLabel);


        JLabel burgerPriceLabel = new JLabel(price);
        burgerPriceLabel.setForeground(Color.ORANGE);
        burgerPriceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        burgerPriceLabel.setBounds(x + 50, y + height + 40, 150, 30);
        panel.add(burgerPriceLabel);


        JCheckBox burgerCheckBox = new JCheckBox();
        burgerCheckBox.setBounds(x + 75, y+20 + height + 80, 20, 20);
        burgerCheckBox.setBackground(new Color(0, 0, 0, 0));
        panel.add(burgerCheckBox);

        return burgerCheckBox;
    }

    //Qty selector
    private JSpinner addQuantitySelector(JPanel panel, int x, int y) {
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        quantitySpinner.setBounds(x + 75, y+60, 50, 30);
        panel.add(quantitySpinner);
        return quantitySpinner;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, Integer> selectedBurgers = new HashMap<>(); // No pre-selected burgers
            BurgerSelect2 app = new BurgerSelect2(selectedBurgers,username);
            app.setVisible(true);
        });
    }
}