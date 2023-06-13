package sub1;

public class Customer {
    private int customerNum;
    private String customerName;
    private int customerId;
    private String customerPhone;
    private double accountBalance;

    public Customer(int customerNum, String customerName, int customerId, String customerPhone) {
        this.customerNum = customerNum;
        this.customerName = customerName;
        this.customerId = customerId;
        this.customerPhone = customerPhone;
    }

    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public boolean isValid() {
        // Add implementation to check if the customer information is valid
        // For example, you can perform validation based on certain conditions or rules
        if (customerName == null || customerName.isEmpty()) {
            return false; // Customer name is required
        }
        if (customerId <= 0) {
            return false; // Invalid customer ID
        }
        if (customerPhone == null || customerPhone.isEmpty()) {
            return false; // Customer phone number is required
        }
        return true; // Customer information is valid
    }

    public int getAccountBalance() {
        return (int) accountBalance;
    }

    public void setAccountBalance(double d) {
        this.accountBalance = d;
    }

    public void newCustomer(int customerNum, String customerName, int customerId, String customerPhone) {
        this.customerNum = customerNum;
        this.customerName = customerName;
        this.customerId = customerId;
        this.customerPhone = customerPhone;
    }

    public void reloadCustomer(int customerNum, String customerName, int customerId, String customerPhone) {
        this.customerNum = customerNum;
        this.customerName = customerName;
        this.customerId = customerId;
        this.customerPhone = customerPhone;
    }

    public void removeCustomer() {
        this.customerNum = 0;
        this.customerName = "";
        this.customerId = 0;
        this.customerPhone = "";
    }

    public static void main(String[] args) {
        Customer customer = new Customer(1, "±è¿ìÇõ", 12345, "010-6264-9549");
        System.out.println("Customer Name: " + customer.getCustomerName());
        System.out.println("Customer Phone: " + customer.getCustomerPhone());

        // Updating customer information
        customer.setCustomerName("È«±æµ¿");
        customer.setCustomerPhone("010-1234-5678");
        System.out.println("Updated Customer Name: " + customer.getCustomerName());
        System.out.println("Updated Customer Phone: " + customer.getCustomerPhone());
    }
}
