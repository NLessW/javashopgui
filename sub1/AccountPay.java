package sub1;
public class AccountPay {
    private int accNum; // ���¹�ȣ
    private int accPassword; // ���� ��й�ȣ
    private int accMoney; // ���� �ܾ�

    // ������
    public AccountPay(int accNum, int accPassword, int accMoney) {
        this.accNum = accNum;
        this.accPassword = accPassword;
        this.accMoney = accMoney;
    }

    // ���Ϳ� ���� �޼���
    public int getAccNum() {
        return accNum;
    }

    public void setAccNum(int accNum) {
        this.accNum = accNum;
    }

    public int getAccPassword() {
        return accPassword;
    }

    public void setAccPassword(int accPassword) {
        this.accPassword = accPassword;
    }

    public int getAccMoney() {
        return accMoney;
    }

    public void setAccMoney(int accMoney) {
        this.accMoney = accMoney;
    }
}
