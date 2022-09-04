package ATM;

public class Account {
    private String name;
    private String password;
    private String cardId;
    private double money;

    public void Account(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }

    public Account(String name, String password, String cardId, double quotaMoney) {
        this.name = name;
        this.password = password;
        this.cardId = cardId;

        this.quotaMoney = quotaMoney;
    }

    private double quotaMoney;
}
