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
    private JComboBox<Product> productComboBox;	//제품 선택 콤보박스
    private JTextArea cartTextArea;	//장바구니 텍스트 영역
    private JPanel loginPanel;	//로그인 패널
    private JPanel adminPanel;	//관리자패널
    private JPanel customerPanel;	//고객 패널
    private JButton loginButton;	//로그인 버튼
    private JButton logoutButton;	//로그아웃 버튼
    private JButton signupButton;	//회원가입 버튼
    private boolean isLoggedIn;	//로그인 상태 여부 확인
    private boolean isAdmin;	//관리자여부 확인
    private Map<String, String> userMap;	//사용자 정보를 담은 맵
    private List<Product> productList;	//상품 리스트(장바구니)
    private JLabel totalPriceLabel; // 총 가격을 표시하는 텍스트 레이블
    private List<Vector<String>> cardList; // 카드 정보를 담은 리스트
    private List<Vector<String>> accList;	//계좌 정보를 담은 리스트
    private String id;	//로그인한 사용자 아이디
    private JTextField idTextField;	//아이디 입력필드
    private boolean isFirstLogin = true;	//첫번째 로그인여부 확인
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PurchaseInfo frame = new PurchaseInfo();
                frame.setTitle("Purchase Info");	//프레임의 제목 설정
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//프레임 닫기 버튼
                frame.setSize(1100,400);	//프레임 크기설정
                
                frame.setVisible(true);	//프레임 보이도록 설정
            }
        });
    }

    public PurchaseInfo() {
        loginPanel = createLoginPanel();	//로그인 패널 생성
        productList = new ArrayList<>();	//제품 리스트 초기화
        cardList = new ArrayList<>();	//카드 정보 리스트 초기화
        accList = new ArrayList<>();	//계좌 정보 리스트 초기화
        initializeProducts(); // 상품 초기화
        
        getContentPane().setLayout(new BorderLayout());	//프레임 패널 레이아웃 설정
        getContentPane().add(loginPanel, BorderLayout.CENTER);	//패널 위치 설정

        logoutButton = new JButton("Logout");	//로그아웃 버튼 생성
        logoutButton.addActionListener(new ActionListener() {	//이벤트 선언
            public void actionPerformed(ActionEvent e) {	//메서드 구현
                isLoggedIn = false;	//로그인 상태 해제
                isAdmin = false;	//관리자 상태 해제
                getContentPane().removeAll();	//모든 텍스트 제거
                getContentPane().setLayout(new BorderLayout());	//레이아웃 변경
                getContentPane().add(createLoginPanel(), BorderLayout.CENTER);	//중앙에 위치
                getContentPane().revalidate();	//다시 gui 표시요청
                getContentPane().repaint();	// gui 재표시
            }
        });

        isLoggedIn = false;	//초기에 로그인 상태 false 설정
        isAdmin = false;	//초기에 관리자 상태 false 설정
        userMap = new HashMap<>();	//사용자 정보를 담을 맵 생성
        userMap.put("root", "1234");	//관리자 계정
        userMap.put("cust1", "1234");	//미리 생성된 고객 계정
        userMap.put("cust2", "1234");
        userMap.put("cust3", "1234");
        userMap.put("cust4", "1234");
        userMap.put("cust5", "1234");

        
        Vector<String> card = new Vector<>();	//카드 정보 생성
        card.add("1234 - 1234 - 1234 - 1234");	//카드번호
        card.add("123");	//cvc
        card.add("12/34");	//유효기간
        card.add("1234");	//비밀번호
        card.add("100000");	//금액
        card.add("cust1");//소유주 계정
        cardList.add(card);	//카드 정보 리스트에 추가
  
        Vector<String> acc = new Vector<>();	//계좌 정보생성
        acc.add("11112345678");	//계좌 번호
        acc.add("홍길동");	//예금주
        acc.add("농협");	//은행
        acc.add("1234");	//비밀번호
        acc.add("100000");	//잔액
        acc.add("cust1");	//소유주 계정
        accList.add(acc);	//계좌 정보 리스트에 추가
        
    }
    
    //상품 목록 초기화
    private void initializeProducts() {
        productList.add(new Product("Corsair K100 MX Speed", 235));	//"상품 이름", 가격으로 미리 상품 추가
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
    // 로그인 패널 생성
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2)); //3행 2열의 그리드 레이아웃 패널 생성

        JLabel idLabel = new JLabel("ID:");	//ID 레이블 생성
        idTextField = new JTextField();	//ID를 입력받을 텍스트 필드 생성
        
        JLabel passwordLabel = new JLabel("Password:");	//Password 레이블 생성
        JPasswordField passwordField = new JPasswordField();	//Password를 입력받을 텍스트 필드 생성 
        if (isFirstLogin) {
            idTextField.setText("cust1"); // 첫 번째 로그인 시에만 자동 입력
            passwordField.setText("1234"); // 첫 번째 로그인 시에만 자동 입력
            isFirstLogin = false; // 이후에는 실행하지 않도록 설정

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    loginButton.doClick(); // 처음 로그인 시에만 클릭
                }
            });
        }
        
        panel.add(idLabel);	//패널에 ID 레이블 추가
        panel.add(idTextField);	//ID텍스트 필드 추가
        panel.add(passwordLabel);	//패널에 비밀번호 레이블 추가
        panel.add(passwordField);	//비밀번호 패스워드 필드 추가
        loginButton = new JButton("Login");	//Login 버튼 생성
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                id = idTextField.getText().toString();	//입력된 ID 가져옴
                String password = new String(passwordField.getPassword());	//입력된 비밀번호 가져옴
                if (isLoggedIn) {
                    JOptionPane.showMessageDialog(null, "Already logged in.", "Error", JOptionPane.ERROR_MESSAGE);//에러메세지 창
                    return;
                }

                if (authenticateUser(id, password)) {//id와 비밀번호가 인증되었을 때
                    if (isAdminUser(id)) {	//관리자 사용자 아이디인 경우
                        loginAsAdmin();	//관리자 로그인
                    } else {	//일반 사용자일 경우
                        loginAsCustomer();	//고객으로 로그인
                    }
                } else {	//인증에 실패한 경우
                    JOptionPane.showMessageDialog(null, "Invalid ID or password.", "Error", JOptionPane.ERROR_MESSAGE);	//에러 메세지 창
                }

            }
        });
        signupButton = new JButton("Signup");	//Signup 버튼 생성
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSignupPage();	//회원가입 페이지를 보여주는 메서드 호출
            }
        });
        panel.add(loginButton);	//패널에 로그인 버튼 추가
        panel.add(signupButton);	//패널에 회원가입 버튼 추기
        
        

        return panel; //생성된 로그인 패널 반환
    }

    private boolean authenticateUser(String id, String password) {	//사용자 맵에서 주어진 id,비밀번호가 일치한지 확인하고 인증
        return userMap.containsKey(id) && userMap.get(id).equals(password);	//일치하는지 확인
    }

    private boolean isAdminUser(String id) {
        return id.equals("root");	//입력받은 계정이 root이면 관리자 권한 부여
    }

    private void loginAsAdmin() {
        isLoggedIn = true;	//로그인 상태로 변경
        isAdmin = true;	//관리자 모드 설정

        adminPanel = createAdminPanel();	//관리자 패널 생성

        getContentPane().removeAll();	//기존 구성요소 삭제
        getContentPane().setLayout(new BorderLayout());	//레이아웃 변경
        getContentPane().add(adminPanel, BorderLayout.CENTER);	//중앙 위치
        getContentPane().add(logoutButton, BorderLayout.SOUTH);	// 로그아웃 버튼을 밑에 추가
        getContentPane().revalidate();	//gui 재요청
        getContentPane().repaint();	//gui 표시
    }
    
    private void loginAsCustomer() {
        isLoggedIn = true;	//로그인 상태 변경
        customerPanel = createCustomerPanel();//고객 패널 생성
        getContentPane().removeAll();	//기존 구성요소 삭제
        getContentPane().setLayout(new BorderLayout());	//레이아웃 변경
        getContentPane().add(customerPanel, BorderLayout.CENTER);	//중앙위치
        getContentPane().add(logoutButton, BorderLayout.SOUTH);	//로그아웃 버튼 맨 밑 추가
        getContentPane().revalidate();	// gui 재요청
        getContentPane().repaint();	//gui표시
    }
    private void showSignupPage() {
        JPanel signupPanel = new JPanel(new GridLayout(5, 2));	//5행 2열의 그리드 레이아웃 생성

        JLabel nameLabel = new JLabel("Name:"); //이름 레이블
        JTextField nameTextField = new JTextField();	//이름 입력 필드
        JLabel phoneLabel = new JLabel("Phone:");	//전화번호 레이블
        JTextField phoneTextField = new JTextField();	//전화번호 입력 필드
        JLabel idLabel = new JLabel("ID:");	//id 레이블
        JTextField idTextField = new JTextField();	//id입력 필드
        JLabel passwordLabel = new JLabel("Password:");	//비밀번호 레이블
        JPasswordField passwordField = new JPasswordField();	//비밀번호 입력 필드

        //패널에 생성된 필드와 레이블들 추가
        signupPanel.add(nameLabel);	
        signupPanel.add(nameTextField);
        signupPanel.add(phoneLabel);
        signupPanel.add(phoneTextField);
        signupPanel.add(idLabel);
        signupPanel.add(idTextField);
        signupPanel.add(passwordLabel);
        signupPanel.add(passwordField);

        
        JButton signupButton = new JButton("Signup");	//회원가입 버튼
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();	//입력받은 이름 가져옴
                String phone = phoneTextField.getText();	//입력받은 전화번호 가져옴
                String id = idTextField.getText();	//입력받은 id 가져옴
                String password = new String(passwordField.getPassword());	//입력받은 비밀번호 가져옴

                if (name.isEmpty() || phone.isEmpty() || id.isEmpty() || password.isEmpty()) {//모든 필드가 채워지지 않았을 때 표시
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (userMap.containsKey(id)) {	//이미 존재하는 id일 경우 표시
                    JOptionPane.showMessageDialog(null, "ID already exists. Please choose a different ID.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    userMap.put(id, password);	//회원가입 성공시
                    JOptionPane.showMessageDialog(null, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    getContentPane().removeAll();
                    getContentPane().setLayout(new BorderLayout());
                    getContentPane().add(loginPanel, BorderLayout.CENTER);
                    getContentPane().revalidate();
                    getContentPane().repaint();
                    idTextField.setText(""); //id 입력필드 초기화
                }
            }
        });
 
        JButton backButton = new JButton("Back");	//뒤로 가기 버튼
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(loginPanel, BorderLayout.CENTER);
                getContentPane().revalidate();
                getContentPane().repaint();
            }
        });
        //생성된 버튼 추가
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
                //이미 존재하는 카드 번호일 경우 표시
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
    
    
    private JPanel createAdminPanel() {	//관리자 패널 생성
        JPanel panel = new JPanel(new BorderLayout());	//관리자 패널 레이아웃

        JLabel titleLabel = new JLabel("Admin Panel");	//관리자 패널 제목
        titleLabel.setHorizontalAlignment(JLabel.CENTER);	//제목 가운데 맞춤
        panel.add(titleLabel, BorderLayout.NORTH);	//제목 레이블 위치 위쪽 설정

        JPanel productPanel = createProductPanel();	//제품 패널 생성
        panel.add(productPanel, BorderLayout.CENTER);	//제품 패널 중앙 추가

        return panel;	//패널 반환
    }

    private JPanel createProductPanel() {	//상품 콤보박스 패널
        JPanel panel = new JPanel(new BorderLayout());

        JLabel productLabel = new JLabel("Products:");
        panel.add(productLabel, BorderLayout.NORTH);

        productComboBox = new JComboBox<>(new Vector<>(productList));	//상품 목록 호출
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
                removeSelectedProduct();	//상품 삭제
            }
        });
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    //상품 추가 메서드
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

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);	//패널 보여줌
        if (result == JOptionPane.OK_OPTION) {	//ok버튼 누르면
            String name = nameTextField.getText();	//입력된 이름 가져옴
            double price = Double.parseDouble(priceTextField.getText());	//입력된 가격 가져옴

            Product product = new Product(name, price);	//새로운 상품 생성
            productList.add(product);	//리스트에 추가
            productComboBox.addItem(product);	//콤보박스에 추가
        }
    }
    //상품 삭제 메서드
    private void removeSelectedProduct() {
        Product selectedProduct = (Product) productComboBox.getSelectedItem();	//제품 선택
        if (selectedProduct != null) {	//선택된 제품 유무 확인
            productList.remove(selectedProduct);	//리스트에서 삭제
            productComboBox.removeItem(selectedProduct);	//콤보박스에서 제거
        }
    }
    //고객 패널 생성
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Customer Panel");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel productListPanel = createProductListPanel();	//상품 목록 패널 생성
        panel.add(productListPanel, BorderLayout.WEST);	//왼쪽에 생성

        JPanel cartPanel = createCartPanel();	//장바구니 생성
        panel.add(cartPanel, BorderLayout.CENTER);	//중앙 위치

        return panel;
    }
    
    //상품 리스트 패널
    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel productLabel = new JLabel("Product :");
        panel.add(productLabel, BorderLayout.NORTH);

        JList<Product> productList = new JList<>(new Vector<>(this.productList)); //리스트에서 상품 목록을 불러옴
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//단일 선택

        JScrollPane scrollPane = new JScrollPane(productList);	//스크롤 가능한 패널 생성
        panel.add(scrollPane, BorderLayout.CENTER);	//가운데 추가

        JButton addToCartButton = new JButton("Add to Cart");	//Add to Cart 버튼 생성
        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSelectedProductToCart(productList.getSelectedValue());	//선택된 상품을 추가하는 메서드
            }
        });
        panel.add(addToCartButton, BorderLayout.SOUTH);	//버튼을 패널의 하단에 추가

        return panel;
    }

    private void addSelectedProductToCart(Product product) {	//장바구니 추가 메서드
        if (product != null) {
            cartTextArea.append(product.getName() + " - $" + String.format("%.2f", product.getPrice()) + "\n");	//선택한 상품의 이름과 가격을 장바구니에 추가
            updateTotalPriceLabel();	//총 가격을 업데이트
        }
    }

    private JPanel createCartPanel() {	//장바구니 패널 생성
        JPanel panel = new JPanel(new BorderLayout());

        JLabel cartLabel = new JLabel("My Product List(Cart):");	//장바구니 레이블
        panel.add(cartLabel, BorderLayout.NORTH);

        cartTextArea = new JTextArea(10, 30);	//장바구니 텍스트 영역 생성
        cartTextArea.setEditable(false);	//수정 불가능
        JScrollPane scrollPane = new JScrollPane(cartTextArea);	//스크롤 가능
        panel.add(scrollPane, BorderLayout.CENTER);	//중앙 위치
        
        JPanel buttonPanel = new JPanel();	//버튼 패널 생성

        JButton clearButton = new JButton("Clear");	//Clear 버튼 생성
        
        JButton AddCard = new JButton("Add Card");	//Add Card 버튼 생성
        AddCard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showCardPanel();	//버튼 클릭시 카드 추가 패널 호출
            }
        });
        buttonPanel.add(AddCard);	//버튼 추가
        
        JButton AddAcc = new JButton("Add Acc");	//Add Acc 버튼 생성
        AddAcc.addActionListener(new ActionListener() {	
        	public void actionPerformed(ActionEvent e) {
        		showAccPanel();	//버튼 클릭시 계좌 추가 패널 호출
        	}
        });
        buttonPanel.add(AddAcc);	//버튼 추가
        
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearCart();	//Clear버튼 클릭시 장바구니 비우는 메서드 호출
            }
        });
        buttonPanel.add(clearButton);

        JButton purchaseButton = new JButton("Purchase");	//Purchase 버튼 생성
        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                purchase();	//구매페이지 메서드 호출
            }
        });
        buttonPanel.add(purchaseButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        totalPriceLabel = new JLabel("Total Price: $0.00"); //총 가격 표시할 레이블 생성
        panel.add(totalPriceLabel, BorderLayout.EAST);	//레이블을 오른쪽 추가

        return panel;	//패널 반환
    }

    private void clearCart() {	//Clear 버튼 작동 메서드
        cartTextArea.setText("");	//텍스트 칸을 비움
        updateTotalPriceLabel();	//총 가격 표시 최신화
    }

    private void updateTotalPriceLabel() {	//총 가격 표시 최신화 코드
        double total = 0;	//시작 0
        String[] items = cartTextArea.getText().split("\n");	//상품의 가격을 가져옴
        for (String item : items) {
            String[] parts = item.split(" - \\$");	//상품 정보를 나눠서 가져옴
            if (parts.length == 2) {
                double price = Double.parseDouble(parts[1]);	//상품 가격 파싱
                total += price;	//가격 합산
            }
        }
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", total));	//계산된 가격 표시
    }

    private void purchase() {	//구매 수행 메서드
        String cartContents = cartTextArea.getText();	//장바구니 가져옴
        if (cartContents.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cart is empty.", "Error", JOptionPane.ERROR_MESSAGE); //장바구니가 비었을 때.
        } else {
            String[] paymentOptions = {"Card", "Account"};	//카드 계산, 계좌 계산 선택
            Object selectedPaymentOption = JOptionPane.showInputDialog(	//계좌 방식 선택
                    null,	//대화상자 부모 컴포넌트, 특정하지 않기 위해 Null(parentComponent) 
                    "Select a payment method:",	//대화상자에 표시할 메세지(message)
                    "Payment Method",	//대화상자 제목 (title)
                    JOptionPane.QUESTION_MESSAGE, //대화상자 메세지 타입(messageType)
                    null,	//대화상자에 표시할 아이콘 (icon)
                    paymentOptions,	//선택 가능한 옵션을 나타내는 객체 배열 (selectionValues)
                    paymentOptions[0]	//처음에 선택되어 있는 옵션(initialSelectionValue)
            );

            if (selectedPaymentOption == null) {	//결제 방법이 선택되지 않았을 때 표시
                JOptionPane.showMessageDialog(null, "Payment canceled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;	//실행 중단
            }

            if (selectedPaymentOption.equals("Card")) {	//card를 선택한 경우
                Vector<String> selectedCard = selectCardForPayment();	//카드 선택 메서드 호출 후 사용자 아이디에 맞는 카드 정보 가져옴
                if (selectedCard == null) {	//선택한 카드가 없을 경우
                    JOptionPane.showMessageDialog(null, "No card selected.", "Error", JOptionPane.ERROR_MESSAGE);	//선택한 카드 없다 표시
                    return;	//실행 중단
                }

                double totalPrice = calculateTotalPrice(cartContents);	//장바구니 내 가격을 합쳐서 변수에 저장
                boolean deductionSuccessful = deductAmountFromCard(selectedCard, totalPrice);	//선택한 카드로 상품 가격만큼 차감하는 메서드

                if (deductionSuccessful) {	//차감하는 메서드가 true일 경우
                    clearCart();	//장바구니 초기화
                    double remainingBalance = getCardBalance(selectedCard);	//카드의 잔액을 가져와 저장
                    //시스템 부분
                    System.out.println("------------------------------------------");	
                    System.out.println("Card Number : " + getCardNumber(selectedCard));	//선택한 카드의 카드번호 출력
                    System.out.println("Previous balance: $" + (remainingBalance + totalPrice));	//원래 있던 잔액
                    System.out.println("Deducted amount: $" + totalPrice);	//차감된 금액 출력
                    System.out.println("------------------------------------------");
                    try {
                        Thread.sleep(1000);	//1초 대기
                    } catch (InterruptedException e) {
                        System.out.println("System Error!");
                    }
                    System.out.println("Remaining balance: $" + remainingBalance);	//결제 후 남은 금액 표시
                    JOptionPane.showMessageDialog(null, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);	//결제 성공 알림
                } else {	//결제 실패시
                    try {
                        Thread.sleep(1000);	//1초 대기
                    } catch (InterruptedException e) {
                        System.out.println("System Error!");
                    }
                    JOptionPane.showMessageDialog(null, "Insufficient balance on the card.", "Error", JOptionPane.ERROR_MESSAGE);	//잔액 부족 알림
                }
            } else if (selectedPaymentOption.equals("Account")) {	//계좌 선택시

                Vector<String> selectedAccount = selectAccountForPayment();	//결제에 사용할 계좌 선택
                if (selectedAccount == null) {	//선택되지 않았을 떄
                    JOptionPane.showMessageDialog(null, "No account selected.", "Error", JOptionPane.ERROR_MESSAGE);	//선택되지않음을 알림
                    return;
                }

                double totalPrice = calculateTotalPrice(cartContents);	//금액을 가져와서 저장
                boolean deductionSuccessful = deductAmountFromAccount(selectedAccount, totalPrice);	//선택한 계좌로 상품 가격만큼 차감하는 메서드
                //Card 랑 동일
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
    //총 가격 계산 메서드
    private double calculateTotalPrice(String cartContents) {
        String[] items = cartContents.split("\n");	//장바구니 내용을 줄바꿈으로 배열에 저장
        double totalPrice = 0.0;	//가격 초기화
        for (String item : items) {
            String[] parts = item.split(" - ");	//상품 정보를 분할하여 저장
            double price = Double.parseDouble(parts[1].replace("$", ""));	//가격 정보에서 $ 제거 후 double타입으로 저장
            totalPrice += price;	//총 가격에 현재 상품의 가격을 추가
        }
        return totalPrice;	//최종 계산 반환
    }
    //결제에 사용할 카드 선택
    private Vector<String> selectCardForPayment() {
        String loggedInUsername = id; // 현재 로그인된 사용자 이름 가져오기
        List<Vector<String>> filteredCards = cardList.stream()	//스트림으로 변환
                .filter(card -> card.get(5).equals(loggedInUsername)) // 이름이 일치하는 카드만 필터링
                .collect(Collectors.toList()); //스트림으로 변환된걸 리스트로 수집

        if (filteredCards.isEmpty()) {	//필터링 된 카드가 없는경우
            JOptionPane.showMessageDialog(null, "No cards available.", "Error", JOptionPane.ERROR_MESSAGE);	//없다고 알림
            return null;	//null반환
        }

        Object[] cardArray = filteredCards.stream()	//스트림을 생성
                .map(card -> card.get(0))	//카드리스트에서 번호 추출
                .toArray();	//배열로 변환

        if (cardArray.length == 0) {	//카드가 없는 경우
            JOptionPane.showMessageDialog(null, "No cards available.", "Error", JOptionPane.ERROR_MESSAGE);	//없다고 알림
            return null;	//null 반환
        }

        Object selectedCard = JOptionPane.showInputDialog(	//대화상자 표시
                null,	//대화상자 부모컴포넌트, 특정하지 않기 위해 Null (parentComponent)
                "Select a card for payment:",	//대화상제 메세지(message)
                "Card Selection",	//대화상자 제목(title)
                JOptionPane.QUESTION_MESSAGE,	//메시지 타입(messageType)
                null,	//아이콘 (icon)
                cardArray,	//선택 가능한 옵션을 나타냄 (selectionValues)
                cardArray[0]	//처음에 선택되어 있는 옵션(initialSelectionValue)
        );

        if (selectedCard != null) {	//카드를 선택한 경우
            int selectedIndex = Arrays.asList(cardArray).indexOf(selectedCard);	//배열에서 카드를 찾고 해당하는 카드를
            return filteredCards.get(selectedIndex);	//반환한다.
        } else {
            return null;	//없으면 Null로 반환
        }
    }

    
   
    private boolean deductAmountFromCard(Vector<String> card, double amount) {	//카드에서 지정된 금액을 차감하는 메서드
        String balanceStr = card.get(4);	//카드 잔액 정보 가져오기
        double balance = Double.parseDouble(balanceStr);	//잔액 변환
        if (balance >= amount) {	//잔액이 금액보다 많은지 확인
            balance -= amount;	//잔액에서 금액 차감
            card.set(4, String.valueOf(balance));	//카드 정보 최신화
            return true;	//차감 성공
        }
        return false;	//잔액 부족
    }

    private double getCardBalance(Vector<String> card) {	//카드 잔액 가져오는 메서드
        String balanceStr = card.get(4);	//카드 잔액 가져오기
        return Double.parseDouble(balanceStr);	//잔액 변환한 후 반환
    }

    private String getCardNumber(Vector<String> card) {	//카드 번호 가져오는 메서드
        return card.get(0);	//카드 번호 반환
    }

    private Vector<String> selectAccountForPayment() {	//계좌 선택 메서드
        String loggedInUsername = id; // 현재 로그인된 사용자 이름 가져오기
        List<Vector<String>> filteredAccounts = accList.stream()//스트림으로 변환
                .filter(acc -> acc.get(5).equals(loggedInUsername)) // 이름이 일치하는 계좌만 필터링
                .collect(Collectors.toList());	//변환된걸 리스트로 저장

        if (filteredAccounts.isEmpty()) {	//필터링 된 계좌가 없는 경우
            JOptionPane.showMessageDialog(null, "No accounts available.", "Error", JOptionPane.ERROR_MESSAGE);	//계좌가 없다 표시
            return null;	//null 반환
        }

        Object[] accountArray = filteredAccounts.stream()	//스트림으로 변환
                .map(account -> account.get(2)+"-"+account.get(0))	//은행과 계좌번호를 가져와서
                .toArray();	//저장

        if (accountArray.length == 0) {	//계좌가 없을 떄
            JOptionPane.showMessageDialog(null, "No accounts available.", "Error", JOptionPane.ERROR_MESSAGE);	//없다 대화 상자
            return null;	//null 반환
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

        if (selectedAccount != null) {	//선택환 계좌 반환 메서드
            int selectedIndex = Arrays.asList(accountArray).indexOf(selectedAccount);	//선택한 계좌 가져오기
            return filteredAccounts.get(selectedIndex);	//선택된 계좌 반환
        } else {
            return null;	//선택한 계좌가 없을 경우 null
        }
    }

    private boolean deductAmountFromAccount(Vector<String> account, double amount) {	//계좌에서 지정된 금액을 차감하는 메서드
        String balanceStr = account.get(4);	//잔액 가져오기
        double balance = Double.parseDouble(balanceStr);	//잔액 자료형 변환
        if (balance >= amount) {	//잔액이 금액보다 많은지 확인
            balance -= amount;	//금액 차감
            account.set(4, String.valueOf(balance));	//잔액 업데이트
            return true;	//차감 성공
        }
        return false;	//잔액 부족
    }

    private double getAccountBalance(Vector<String> account) {	//계좌 잔액 메서드
        String balanceStr = account.get(4);	//계좌 잔액 가져오기
        return Double.parseDouble(balanceStr);	//잔액 자료형 변환
    }

    private String getAccountNumber(Vector<String> account) {	//계좌번호 메서드
        return account.get(0);	//계좌번호 반환
    }
}
