package sub1;

public class CardPay {
    private int cardNum; // ī���ȣ
    private int cardPassword; // ī�� ��й�ȣ
    private int maxPay; // ���� �ѵ�
    private int numCLC; // CLC ��ȣ
    private int maxDate; // ��ȿ �Ⱓ

    // ������
    public CardPay(int cardNum, int cardPassword, int maxPay, int numCLC, int maxDate) {
        this.cardNum = cardNum;
        this.cardPassword = cardPassword;
        this.maxPay = maxPay;
        this.numCLC = numCLC;
        this.maxDate = maxDate;
    }

    // ���Ϳ� ���� �޼���
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