package sub1;

public class CardPay {
    private int cardNum; // 카드번호
    private int cardPassword; // 카드 비밀번호
    private int maxPay; // 결제 한도
    private int numCLC; // CLC 번호
    private int maxDate; // 유효 기간

    // 생성자
    public CardPay(int cardNum, int cardPassword, int maxPay, int numCLC, int maxDate) {
        this.cardNum = cardNum;
        this.cardPassword = cardPassword;
        this.maxPay = maxPay;
        this.numCLC = numCLC;
        this.maxDate = maxDate;
    }

    // 게터와 세터 메서드
    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public int getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(int cardPassword) {
        this.cardPassword = cardPassword;
    }

    public int getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(int maxPay) {
        this.maxPay = maxPay;
    }

    public int getNumCLC() {
        return numCLC;
    }

    public void setNumCLC(int numCLC) {
        this.numCLC = numCLC;
    }

    public int getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(int maxDate) {
        this.maxDate = maxDate;
    }
}