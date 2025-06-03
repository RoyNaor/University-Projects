package gym.customers;

public class Balance {
    private int amount;

    public Balance(int initialAmount) {
        this.amount = initialAmount;
    }

    public  int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

}
