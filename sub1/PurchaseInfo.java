package sub1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.lang.Thread;
import java.util.stream.Collectors;

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
    private List<Vector<String>> cardList; // Added cardList
    private List<Vector<String>> accList;
    private String id;
    private JTextField idTextField;
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
        cardList = new ArrayList<>();
        accList = new ArrayList<>();
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
        
        Vector<String> card = new Vector<>();
        card.add("1234 - 1234 - 1234 - 1234");
        card.add("123");
        card.add("12/34");
        card.add("1234");
        card.add("100000");
        card.add("cust1");
        cardList.add(card);

        Vector<String> acc = new Vector<>();
        acc.add("11112345678");
        acc.add("홍길동");
        acc.add("농협");
        acc.add("1234");
        acc.add("100000");
        acc.add("cust1");
        accList.add(acc);
        
    }
    
    private void initializeProducts() {
        productList.add(new Product("Corsair K100 MX Speed", 235));
        productList.add(new Product("Corsair K70 Lux", 133));
        productList.add(new Product("Corsair K70 MK2",235));
        productList.add(new Product("Logitech G502 Hero",63));
        productList.add(new Product("Logitech PRO X SuperLight", 157));
        productList.add(new Product("AMD Ryzen9-5 7950X 3D", 807));
        productList.add(new Product("AMD Ryzen9-5 7950X", 606));
        productList.add(new Product("AMD Ryzen9-4 5950X",543));
        productList.add(new Product("MSI Geforce RTX 4090 Suprim X D6X 24GB TriFrozer3S", 2192));
        productList.add(new Product("ASUS ROG STRIX Geforce RTX 4090 O24G GAMING OC D6X 24GB",2388));
        
    }
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel idLabel = new JLabel("ID:");
        idTextField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        panel.add(idLabel);
        panel.add(idTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                id = idTextField.getText();
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
                    System.out.println(userMap);
                    idTextField.setText("");
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
    
    private void showCardPanel() {
        JPanel cardPanel = new JPanel(new GridLayout(5, 2));

        JLabel cardNum = new JLabel("CardNum:");
        JTextField cardNumT = new JTextField();
        JLabel cardCLC = new JLabel("CLC:");
        JTextField clcTextField = new JTextField();
        JLabel CardRate = new JLabel("Card Date (mm/yy):");
        JTextField dateTextField = new JTextField();
        JLabel cpasswordLabel = new JLabel("Password:");
        JPasswordField cpasswordField = new JPasswordField();
        cardPanel.add(cardNum);
        cardPanel.add(cardNumT);
        cardPanel.add(cardCLC);
        cardPanel.add(clcTextField);
        cardPanel.add(CardRate);
        cardPanel.add(dateTextField);
        cardPanel.add(cpasswordLabel);
        cardPanel.add(cpasswordField);

        JButton cardBtn = new JButton("Add");
        cardBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String number = cardNumT.getText();
                String clc = clcTextField.getText();
                String da = dateTextField.getText();
                String pw = new String(cpasswordField.getPassword());
                String money = "100000";
                if (number.isEmpty() || clc.isEmpty() || da.isEmpty() || pw.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the card number already exists
                for (Vector<String> card : cardList) {
                    if (card.get(0).equals(number)) {
                        JOptionPane.showMessageDialog(null, "Card number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                Vector<String> card = new Vector<>();
                card.add(number.substring(0,4)+" - "+number.substring(4,8)+" - "+number.substring(8,12)+" - "+number.substring(12));
                card.add(clc);
                card.add(da);
                card.add(pw);
                card.add(money);
                card.add(id);
                
                cardList.add(card);
                System.out.println("---System input Card info---");
                System.out.println("Card Number :" + number.substring(0,4)+" - "+number.substring(4,8)+" - "+number.substring(8,12)+" - "+number.substring(12));
                System.out.println("CLC Number : " + clc);
                System.out.println("Card Date : " + da);
                System.out.println("Card Password : " + pw);
                System.out.println("Card Money : "+money);
                System.out.println("Name : "+id);
                JOptionPane.showMessageDialog(null, "Card added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(createCustomerPanel(), BorderLayout.CENTER);
                getContentPane().add(logoutButton, BorderLayout.SOUTH);

                // Enable Add Card button when returning to the customer panel
                cardBtn.setEnabled(true);

                getContentPane().revalidate();
                getContentPane().repaint();
            }
        });

        cardPanel.add(cardBtn);
        cardPanel.add(backButton);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(cardPanel, BorderLayout.CENTER);
        getContentPane().add(backButton, BorderLayout.SOUTH);
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    private void showAccPanel() {
        JPanel accPanel = new JPanel(new GridLayout(5, 2));

        JLabel accNum = new JLabel("Account Num:");
        JTextField accNumT = new JTextField();
        JLabel accName = new JLabel("Name:");
        JTextField aTextField = new JTextField();
        JLabel accBank = new JLabel("Bank");
        JComboBox<String> bankComboBox = new JComboBox<>(new String[]{"농협", "신한", "기업", "국민", "카카오", "토스", "우리", "케이뱅크"});
        JLabel apasswordLabel = new JLabel("Password:");
        JPasswordField apasswordField = new JPasswordField();
        accPanel.add(accNum);
        accPanel.add(accNumT);
        accPanel.add(accName);
        accPanel.add(aTextField);
        accPanel.add(accBank);
        accPanel.add(bankComboBox);
        accPanel.add(apasswordLabel);
        accPanel.add(apasswordField);

        JButton bankBtn = new JButton("Add");
        bankBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String number = accNumT.getText();
                String name = aTextField.getText();
                String bank = (String) bankComboBox.getSelectedItem();
                String password = new String(apasswordField.getPassword());
                String money = "100000";
                
                if (number.isEmpty() || name.isEmpty() || bank.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the Acc number already exists
                for (Vector<String> acc : accList) {
                    if (acc.get(0).equals(number)) {
                        JOptionPane.showMessageDialog(null, "Account number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }


                Vector<String> acc = new Vector<>();
                acc.add(number);
                acc.add(name);
                acc.add(bank);
                acc.add(password);
                acc.add(money);
                acc.add(id);
                accList.add(acc);
                System.out.println("---System input Card info---");
                System.out.println("Account Number :" + number);
                System.out.println("Account Holder : " + name);
                System.out.println("Bank : " + bank);
                System.out.println("Account Password : " + password);
                System.out.println("Account Money : "+money);
                System.out.println("Name : "+id);
                JOptionPane.showMessageDialog(null, "Account added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(createCustomerPanel(), BorderLayout.CENTER);
                getContentPane().add(logoutButton, BorderLayout.SOUTH);

                // Enable Add Card button when returning to the customer panel
                bankBtn.setEnabled(true);

                getContentPane().revalidate();
                getContentPane().repaint();
            }
        });

        accPanel.add(bankBtn);
        accPanel.add(backButton);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(accPanel, BorderLayout.CENTER);
        getContentPane().add(backButton, BorderLayout.SOUTH);
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

        JButton clearButton = new JButton("Clear");
        
        JButton AddCard = new JButton("Add Card");
        AddCard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showCardPanel();
            }
        });
        buttonPanel.add(AddCard);
        
        JButton AddAcc = new JButton("Add Acc");
        AddAcc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		showAccPanel();
        	}
        });
        buttonPanel.add(AddAcc);
        
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearCart();
            }
        });
        buttonPanel.add(clearButton);

        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                purchase();
            }
        });
        buttonPanel.add(purchaseButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Added totalPriceLabel
        totalPriceLabel = new JLabel("Total Price: $0.00");
        panel.add(totalPriceLabel, BorderLayout.EAST);

        return panel;
    }

    private void clearCart() {
        cartTextArea.setText("");
        updateTotalPriceLabel();
    }

    private void updateTotalPriceLabel() {
        double total = 0;
        String[] items = cartTextArea.getText().split("\n");
        for (String item : items) {
            String[] parts = item.split(" - \\$");
            if (parts.length == 2) {
                double price = Double.parseDouble(parts[1]);
                total += price;
            }
        }
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", total));
    }

    private void purchase() {
        String cartContents = cartTextArea.getText();
        if (cartContents.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cart is empty.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] paymentOptions = {"Card", "Account"};
            Object selectedPaymentOption = JOptionPane.showInputDialog(
                    null,
                    "Select a payment method:",
                    "Payment Method",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    paymentOptions,
                    paymentOptions[0]
            );

            if (selectedPaymentOption == null) {
                JOptionPane.showMessageDialog(null, "Payment canceled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedPaymentOption.equals("Card")) {
                // Select a card for payment
                Vector<String> selectedCard = selectCardForPayment();
                if (selectedCard == null) {
                    JOptionPane.showMessageDialog(null, "No card selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double totalPrice = calculateTotalPrice(cartContents);
                boolean deductionSuccessful = deductAmountFromCard(selectedCard, totalPrice);

                if (deductionSuccessful) {
                    clearCart();
                    double remainingBalance = getCardBalance(selectedCard);
                    System.out.println("------------------------------------------");
                    System.out.println("Card Number : " + getCardNumber(selectedCard));
                    System.out.println("Previous balance: $" + (remainingBalance + totalPrice));
                    System.out.println("Deducted amount: $" + totalPrice);
                    System.out.println("------------------------------------------");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("System Error!");
                    }
                    System.out.println("Remaining balance: $" + remainingBalance);
                    JOptionPane.showMessageDialog(null, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("System Error!");
                    }
                    JOptionPane.showMessageDialog(null, "Insufficient balance on the card.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (selectedPaymentOption.equals("Account")) {
                // Select an account for payment
                Vector<String> selectedAccount = selectAccountForPayment();
                if (selectedAccount == null) {
                    JOptionPane.showMessageDialog(null, "No account selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double totalPrice = calculateTotalPrice(cartContents);
                boolean deductionSuccessful = deductAmountFromAccount(selectedAccount, totalPrice);

                if (deductionSuccessful) {
                    clearCart();
                    double remainingBalance = getAccountBalance(selectedAccount);
                    System.out.println("------------------------------------------");
                    System.out.println("Account Number : " + getAccountNumber(selectedAccount));
                    System.out.println("Previous balance: $" + (remainingBalance + totalPrice));
                    System.out.println("Deducted amount: $" + totalPrice);
                    System.out.println("------------------------------------------");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("System Error!");
                    }
                    System.out.println("Remaining balance: $" + remainingBalance);
                    JOptionPane.showMessageDialog(null, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("System Error!");
                    }
                    JOptionPane.showMessageDialog(null, "Insufficient balance in the account.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private double calculateTotalPrice(String cartContents) {
        String[] items = cartContents.split("\n");
        double totalPrice = 0.0;
        for (String item : items) {
            String[] parts = item.split(" - ");
            double price = Double.parseDouble(parts[1].replace("$", ""));
            totalPrice += price;
        }
        return totalPrice;
    }

    private Vector<String> selectCardForPayment() {
        String loggedInUsername = id; // 현재 로그인된 사용자 이름 가져오기
        List<Vector<String>> filteredCards = cardList.stream()
                .filter(card -> card.get(5).equals(loggedInUsername)) // 이름이 일치하는 카드만 필터링
                .collect(Collectors.toList());

        if (filteredCards.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No cards available.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Object[] cardArray = filteredCards.stream()
                .map(card -> card.get(0))
                .toArray();

        if (cardArray.length == 0) {
            JOptionPane.showMessageDialog(null, "No cards available.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Object selectedCard = JOptionPane.showInputDialog(
                null,
                "Select a card for payment:",
                "Card Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                cardArray,
                cardArray[0]
        );

        if (selectedCard != null) {
            int selectedIndex = Arrays.asList(cardArray).indexOf(selectedCard);
            return filteredCards.get(selectedIndex);
        } else {
            return null;
        }
    }

    private boolean deductAmountFromCard(Vector<String> card, double amount) {
        String balanceStr = card.get(4);
        double balance = Double.parseDouble(balanceStr);
        if (balance >= amount) {
            balance -= amount;
            card.set(4, String.valueOf(balance));
            return true;
        }
        return false;
    }

    private double getCardBalance(Vector<String> card) {
        String balanceStr = card.get(4);
        return Double.parseDouble(balanceStr);
    }

    private String getCardNumber(Vector<String> card) {
        return card.get(0);
    }

    private Vector<String> selectAccountForPayment() {
        String loggedInUsername = id; // 현재 로그인된 사용자 이름 가져오기
        List<Vector<String>> filteredAccounts = accList.stream()
                .filter(acc -> acc.get(5).equals(loggedInUsername)) // 이름이 일치하는 계좌만 필터링
                .collect(Collectors.toList());

        if (filteredAccounts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No accounts available.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Object[] accountArray = filteredAccounts.stream()
                .map(account -> account.get(0))
                .toArray();

        if (accountArray.length == 0) {
            JOptionPane.showMessageDialog(null, "No accounts available.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Object selectedAccount = JOptionPane.showInputDialog(
                null,
                "Select an account for payment:",
                "Account Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                accountArray,
                accountArray[0]
        );

        if (selectedAccount != null) {
            int selectedIndex = Arrays.asList(accountArray).indexOf(selectedAccount);
            return filteredAccounts.get(selectedIndex);
        } else {
            return null;
        }
    }

    private boolean deductAmountFromAccount(Vector<String> account, double amount) {
        String balanceStr = account.get(4);
        double balance = Double.parseDouble(balanceStr);
        if (balance >= amount) {
            balance -= amount;
            account.set(4, String.valueOf(balance));
            return true;
        }
        return false;
    }

    private double getAccountBalance(Vector<String> account) {
        String balanceStr = account.get(4);
        return Double.parseDouble(balanceStr);
    }

    private String getAccountNumber(Vector<String> account) {
        return account.get(0);
    }
}
