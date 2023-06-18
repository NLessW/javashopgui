package sub1;

public class Purchase {
    private Customer customer; // 고객 정보
    private Product product; // 상품 정보
    private String paymentMethod; // 결제 수단

    public Purchase(Customer customer, Product product, String paymentMethod) {
        this.customer = customer;
        this.product = product;
        this.paymentMethod = paymentMethod;
    }

    public boolean processPurchase() {
        // 고객 정보 확인
        if (customer == null || !customer.isValid()) {
            return false; // 고객 정보가 유효하지 않음
        }

        // 상품 가용성 확인
        if (product == null || !product.isAvailable()) {
            return false; // 상품이 가용하지 않음
        }

        // 결제 수행
        boolean paymentSuccess = performPayment();
        if (!paymentSuccess) {
            return false; // 결제 실패
        }

        // 재고 업데이트
        updateInventory();

        // 구매 성공 처리
        return true;
    }

    private boolean performPayment() {
        // 결제 수단에 따른 결제 로직 수행
        // 여기에 특정한 결제 로직을 구현할 수 있음
        // 예를 들어, paymentMethod가 "계좌이체"인 경우 고객의 계좌에서 금액 차감

        if (paymentMethod.equals("계좌이체")) {
            // 계좌이체 결제 로직 수행
            int customerBalance = customer.getAccountBalance(); // 고객의 계좌 잔액
            double productPrice = product.getPrice(); // 상품 가격

            if (customerBalance >= productPrice) {
                // 잔액이 충분한 경우, 계좌에서 금액 차감
                customer.setAccountBalance(customerBalance - productPrice);
                return true; // 결제 성공
            } else {
                // 잔액 부족
                return false; // 결제 실패
            }
        } else if (paymentMethod.equals("카드결제")) {
            // 카드결제 로직 수행
            // 여기에 카드 결제에 대한 구체적인 구현을 추가할 수 있음
            // 일단은 카드 결제가 항상 성공한다고 가정
            return true; // 결제 성공
        }

        return false; // 유효하지 않은 결제 수단
    }

    private void updateInventory() {
        // 재고 업데이트 로직
        // 예를 들어, 구매한 상품의 재고를 차감함
        int currentStock = product.getStock(); // 현재 재고 수량
        product.setStock(currentStock - 1); // 상품 재고 차감
    }
}
