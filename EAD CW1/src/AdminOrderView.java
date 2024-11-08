import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminOrderView extends JFrame {

    private static final String URL = "jdbc:mysql://localhost:3308/iHungryBurger";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public AdminOrderView() {
        setTitle("I HUNGRY BURGER");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout()) {
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


        panel.setPreferredSize(new Dimension(800, 600)); // Adjust width and height as needed

        JLabel headerLabel = new JLabel("I HUNGRY BURGER");
        headerLabel.setForeground(Color.ORANGE);
        headerLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 46));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headerLabel, BorderLayout.NORTH);


        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //Close current page
                new Login().setVisible(true); // Redirect to Login
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Order ID", "Item Name", "Quantity", "Status", "Action"}, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        table.setRowHeight(35);


        TableColumn itemNameColumn = table.getColumnModel().getColumn(1);
        TableColumn orderIDColumn = table.getColumnModel().getColumn(0);
        TableColumn quantityColumn = table.getColumnModel().getColumn(2);
        TableColumn actionColumn = table.getColumnModel().getColumn(4);

        itemNameColumn.setPreferredWidth(300);
        orderIDColumn.setPreferredWidth(50);
        quantityColumn.setPreferredWidth(50);
        actionColumn.setPreferredWidth(150);

        // Add buttons to the "Action" column
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox(), tableModel));

        // Set the preferred size of the table for the scroll pane
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));


        JScrollPane scrollPane = new JScrollPane(table);


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setPreferredSize(new Dimension(500, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add the tablePanel to the main panel
        panel.add(tablePanel, BorderLayout.CENTER);

        // Fetch data from database and populate table
        loadData(tableModel);

        // Add the main panel to the frame
        add(panel);
    }

    private void loadData(DefaultTableModel tableModel) {
        String query = "SELECT * FROM order_details";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Clear existing data
            tableModel.setRowCount(0);

            // Populate table with data from result set
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String itemName = rs.getString("item_name");
                String quantity = rs.getString("quantity");
                String status = rs.getString("status");


                tableModel.addRow(new Object[]{orderId, itemName, quantity, status, "Update/Delete"});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminOrderView app = new AdminOrderView();
            app.setVisible(true);
        });
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton updateButton = new JButton("Update");
        private final JButton deleteButton = new JButton("Delete");

        public ButtonRenderer() {
            setLayout(new FlowLayout());
            add(updateButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }


    class ButtonEditor extends DefaultCellEditor {
        private final JButton updateButton = new JButton("Update");
        private final JButton deleteButton = new JButton("Delete");
        private final JPanel panel = new JPanel();
        private DefaultTableModel tableModel;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel) {
            super(checkBox);
            this.tableModel = tableModel;
            panel.setLayout(new FlowLayout());
            panel.add(updateButton);
            panel.add(deleteButton);


            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    updateStatus();
                }
            });


            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    deleteRow();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.selectedRow = row;
            return panel;
        }


        private void updateStatus() {
            int orderId = (int) tableModel.getValueAt(selectedRow, 0);
            String status = JOptionPane.showInputDialog(null, "Enter new status:");

            if (status != null && !status.trim().isEmpty()) {
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement("UPDATE order_details SET status = ? WHERE order_id = ?")) {

                    stmt.setString(1, status);
                    stmt.setInt(2, orderId);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        tableModel.setValueAt(status, selectedRow, 3);
                        JOptionPane.showMessageDialog(null, "Status updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update status.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Status cannot be empty.");
            }
        }


        private void deleteRow() {
            int orderId = (int) tableModel.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this order?", "Delete Order", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement("DELETE FROM order_details WHERE order_id = ?")) {

                    stmt.setInt(1, orderId);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "Order deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete order.");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

//        @Override
//        public Object getCellEditorValue() {
//            return "Update/Delete";
//        }
    }
}