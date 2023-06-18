package sub1;

public class CardPay {
	private String number;
    private String clc;
    private String date;
    private String password;
    private double balance;

    public void Card(String number, String clc, String date, String password) {
        this.number = number;
        this.clc = clc;
        this.date = date;
        this.password = password;
        this.balance = 0.0;
    }

    public void addBalance(double amount) {
        balance += amount;
    }
}

