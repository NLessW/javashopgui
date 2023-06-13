package sub1;
public class AccountPay {
    private int accNum; // 계좌번호
    private int accPassword; // 계좌 비밀번호
    private int accMoney; // 계좌 잔액

    // 생성자
    public AccountPay(int accNum, int accPassword, int accMoney) {
        this.accNum = accNum;
        this.accPassword = accPassword;
        this.accMoney = accMoney;
    }

    // 게터와 세터 메서드
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
