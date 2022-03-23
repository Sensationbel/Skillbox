import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank {
    private volatile Map<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();
    private volatile boolean valueIsFound;
    private static Object tieLock = new Object();

    public Bank() {
        this.valueIsFound = false;
    }

    public synchronized boolean isFraud()//String fromAccountNum, String toAccountNum, long amount
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(final Account accountFrom, final Account accountTo, long amount) throws InterruptedException {
        if (accountFrom.getMoney() >= amount) {
            accountFrom.debit(amount);
            accountTo.credit(amount);
            if (amount > 50000) {
                valueIsFound = isFraud();
                while (valueIsFound) {
                    System.out.println("Запрос на транзакцию со щета: " + accountFrom.getAccNumber()
                            + " сумма транзакции: " + amount);
                    Thread.sleep(1000);
                    valueIsFound = false;
                    System.out.println(accountFrom.getAccNumber() + " транзаккция одобрена.");
                }
            }
        } else {
            System.out.println("На счету: " + accountFrom.getAccNumber() + " не достаточно средств!!!");
            Thread.interrupted();
        }
    }

    public void checkSynchronised(final String fromAccountNum, final String toAccountNum, long amount) throws InterruptedException {
        final Account accountFrom = accounts.get(fromAccountNum);
        final Account accountTo = accounts.get(toAccountNum);
        int fromHash = System.identityHashCode(fromAccountNum);
        int toHash = System.identityHashCode(toAccountNum);
        if (accountFrom == null || accountTo == null || fromAccountNum.equals(toAccountNum)) {
            System.out.println("null accFrom -> " + fromAccountNum
                    + " null accTo -> " + toAccountNum);
        } else if (fromHash < toHash) {
            synchronized (accountFrom) {
                synchronized (accountTo) {
                    transfer(accountFrom, accountTo, amount);
                }
            }
        } else if (fromHash > toHash) {
            synchronized (accountTo) {
                synchronized (accountFrom) {
                    transfer(accountFrom, accountTo, amount);
                }
            }
        } else synchronized (tieLock) {
            synchronized (accountFrom) {
                synchronized (accountTo) {
                    transfer(accountFrom, accountTo, amount);
                }
            }
        }
    }

    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public long getSumAllAccounts() {
        long sumALLAccounts = 0;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            sumALLAccounts += account.getMoney();
        }
        return sumALLAccounts;
    }

    public void addAccounts(String accountNum, Account account) {
        accounts.put(accountNum, account);
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }
}
