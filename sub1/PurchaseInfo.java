package sub1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class PurchaseInfo extends JFrame {
    private JComboBox<String> customerComboBox;
    private JComboBox<Product> productComboBox;
    private JTextArea cartTextArea;
    private JPanel loginPanel;
    private JPanel adminPanel;
    private JPanel customerPanel;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton signupButton;
    private boolean isLoggedIn;
    private boolean isAdmin;
    private Map<String, String> userMap;
    private List<Product> productList;
    private JLabel totalPriceLabel; // Added totalPriceLabel

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PurchaseInfo frame = new PurchaseInfo();
                frame.setTitle("Purchase Info");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public PurchaseInfo() {
        loginPanel = createLoginPanel();
        productList = new ArrayList<>();
        initializeProducts(); // Initialize products
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(loginPanel, BorderLayout.CENTER);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isLoggedIn = false;
                isAdmin = false;
                getContentPane().removeAll();
                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(createLoginPanel(), BorderLayout.CENTER);
                getContentPane().revalidate();
                getContentPane().repaint();
            }
        });


        isLoggedIn = false;
        isAdmin = false;
        userMap = new HashMap<>();
        userMap.put("root", "1234");
        userMap.put("cust1", "1234");
    }
    
    private void initializeProducts() {
        productList.add(new Product("Keyboard", 1000));
        productList.add(new Product("Mouse", 50));
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel idLabel = new JLabel("ID:");
        JTextField idTextField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        panel.add(idLabel);
        panel.add(idTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                String password = new String(passwordField.getPassword());

                if (isLoggedIn) {
                    JOptionPane.showMessageDialog(null, "Already logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (authenticateUser(id, password)) {
                    if (isAdminUser(id)) {
                        loginAsAdmin();
                    } else {
                        loginAsCustomer();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid ID or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSignupPage();
            }
        });

        panel.add(loginButton);
        panel.add(signupButton);

        return panel;
    }

    private boolean authenticateUser(String id, String password) {
        return userMap.containsKey(id) && userMap.get(id).equals(password);
    }

    private boolean isAdminUser(String id) {
        return id.equals("root");
    }

    private void loginAsAdmin() {
        isLoggedIn = true;
        isAdmin = true;

        adminPanel = createAdminPanel();

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(adminPanel, BorderLayout.CENTER);
        getContentPane().add(logoutButton, BorderLayout.SOUTH);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void loginAsCustomer() {
        isLoggedIn = true;

        customerPanel = createCustomerPanel();

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(customerPanel, BorderLayout.CENTER);
        getContentPane().add(logoutButton, BorderLayout.SOUTH);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void showSignupPage() {
        JPanel signupPanel = new JPanel(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneTextField = new JTextField();
        JLabel idLabel = new JLabel("ID:");
        JTextField idTextField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        signupPanel.add(nameLabel);
        signupPanel.add(nameTextField);
        signupPanel.add(phoneLabel);
        signupPanel.add(phoneTextField);
        signupPanel.add(idLabel);
        signupPanel.add(idTextField);
        signupPanel.add(passwordLabel);
        signupPanel.add(passwordField);

        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String phone = phoneTextField.getText();
                String id = idTextField.getText();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || phone.isEmpty() || id.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (userMap.containsKey(id)) {
                    JOptionPane.showMessageDialog(null, "ID already exists. Please choose a different ID.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    userMap.put(id, password);
                    JOptionPane.showMessageDialog(null, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    getContentPane().removeAll();
                    getContentPane().setLayout(new BorderLayout());
                    getContentPane().add(loginPanel, BorderLayout.CENTER);
                    getContentPane().revalidate();
                    getContentPane().repaint();
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(loginPanel, BorderLayout.CENTER);
                getContentPane().revalidate();
                getContentPane().repaint();
            }
        });

        signupPanel.add(signupButton);
        signupPanel.add(backButton);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(signupPanel, BorderLayout.CENTER);
        getContentPane().revalidate();
        getContentPane().repaint();
    }


    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Admin Panel");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel productPanel = createProductPanel();
        panel.add(productPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel productLabel = new JLabel("Products:");
        panel.add(productLabel, BorderLayout.NORTH);

        productComboBox = new JComboBox<>(new Vector<>(productList));
        panel.add(productComboBox, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddProductDialog();
            }
        });
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSelectedProduct();
            }
        });
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void showAddProductDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceTextField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(priceLabel);
        panel.add(priceTextField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());

            Product product = new Product(name, price);
            productList.add(product);
            productComboBox.addItem(product);
        }
    }

    private void removeSelectedProduct() {
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        if (selectedProduct != null) {
            productList.remove(selectedProduct);
            productComboBox.removeItem(selectedProduct);
        }
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Customer Panel");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel productListPanel = createProductListPanel();
        panel.add(productListPanel, BorderLayout.WEST);

        JPanel cartPanel = createCartPanel();
        panel.add(cartPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel productLabel = new JLabel("Product List:");
        panel.add(productLabel, BorderLayout.NORTH);

        JList<Product> productList = new JList<>(new Vector<>(this.productList));
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(productList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSelectedProductToCart(productList.getSelectedValue());
            }
        });
        panel.add(addToCartButton, BorderLayout.SOUTH);

        return panel;
    }

    private void addSelectedProductToCart(Product product) {
        if (product != null) {
            cartTextArea.append(product.getName() + " - $" + String.format("%.2f", product.getPrice()) + "\n");
            updateTotalPriceLabel();
        }
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel cartLabel = new JLabel("Cart:");
        panel.add(cartLabel, BorderLayout.NORTH);

        cartTextArea = new JTextArea(10, 30);
        cartTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(cartTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
        buttonPanel.add(checkoutButton);

        totalPriceLabel = new JLabel("Total Price: $0.00");
        buttonPanel.add(totalPriceLabel);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void checkout() {
        cartTextArea.setText("");
        updateTotalPriceLabel();
        JOptionPane.showMessageDialog(null, "Checkout successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTotalPriceLabel() {
        double total = 0.0;

        String[] cartItems = cartTextArea.getText().split("\n");
        for (String item : cartItems) {
            String[] itemDetails = item.split(" - ");
            if (itemDetails.length == 2) {
                double price = Double.parseDouble(itemDetails[1].substring(1));
                total += price;
            }
        }

        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", total));
    }
}
