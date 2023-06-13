package sub1;

public class Purchase {
    private Customer customer; // �� ����
    private Product product; // ��ǰ ����
    private String paymentMethod; // ���� ����

    public Purchase(Customer customer, Product product, String paymentMethod) {
        this.customer = customer;
        this.product = product;
        this.paymentMethod = paymentMethod;
    }

    public boolean processPurchase() {
        // �� ���� Ȯ��
        if (customer == null || !customer.isValid()) {
            return false; // �� ������ ��ȿ���� ����
        }

        // ��ǰ ���뼺 Ȯ��
        if (product == null || !product.isAvailable()) {
            return false; // ��ǰ�� �������� ����
        }

        // ���� ����
        boolean paymentSuccess = performPayment();
        if (!paymentSuccess) {
            return false; // ���� ����
        }

        // ��� ������Ʈ
        updateInventory();

        // ���� ���� ó��
        return true;
    }

    private boolean performPayment() {
        // ���� ���ܿ� ���� ���� ���� ����
        // ���⿡ Ư���� ���� ������ ������ �� ����
        // ���� ���, paymentMethod�� "������ü"�� ��� ���� ���¿��� �ݾ� ����

        if (paymentMethod.equals("������ü")) {
            // ������ü ���� ���� ����
            int customerBalance = customer.getAccountBalance(); // ���� ���� �ܾ�
            double productPrice = product.getPrice(); // ��ǰ ����

            if (customerBalance >= productPrice) {
                // �ܾ��� ����� ���, ���¿��� �ݾ� ����
                customer.setAccountBalance(customerBalance - productPrice);
                return true; // ���� ����
            } else {
                // �ܾ� ����
                return false; // ���� ����
            }
        } else if (paymentMethod.equals("ī�����")) {
            // ī����� ���� ����
            // ���⿡ ī�� ������ ���� ��ü���� ������ �߰��� �� ����
            // �ϴ��� ī�� ������ �׻� �����Ѵٰ� ����
            return true; // ���� ����
        }

        return false; // ��ȿ���� ���� ���� ����
    }

    private void updateInventory() {
        // ��� ������Ʈ ����
        // ���� ���, ������ ��ǰ�� ��� ������
        int currentStock = product.getStock(); // ���� ��� ����
        product.setStock(currentStock - 1); // ��ǰ ��� ����
    }
}
