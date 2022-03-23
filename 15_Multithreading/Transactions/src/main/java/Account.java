import java.util.concurrent.atomic.AtomicLong;

public class Account {

    private AtomicLong money = new AtomicLong();
    private String accNumber;

    public Account(long money, String accNumber) {
        this.money.set(money);
        this.accNumber = accNumber;
    }

    public long getMoney() {
        return money.get();
    }

    public void setMoney(long money) {
        this.money.set(money);
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void debit(long amount){
        setMoney(getMoney() - amount);
    }

    public void credit(long amount){
        setMoney(getMoney() + amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "money=" + money +
                ", accNumber='" + accNumber + '\'' +
                '}';
    }
}
