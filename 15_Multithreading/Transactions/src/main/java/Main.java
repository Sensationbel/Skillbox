import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    private volatile static List<Account> accounts = new ArrayList<>();
    private static List<Thread> threads = new ArrayList<>();

    @SneakyThrows
    public static void main(String[] args) {
        Bank bank = new Bank();
        createAccounts(100);
        addAccountsInBank(bank);

        addThreads(bank);

        threads.forEach(thread -> {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        print(bank);
        System.out.println(bank.getSumAllAccounts());
    }

    private static void addThreads(Bank bank) {
        for (int i = 0; i < 100; i++) {
            String fromAccountNum = String.valueOf((int) Math.round(Math.random() * accounts.size()));
            String toAccountNum = String.valueOf((int) Math.round(Math.random() * accounts.size()));
            long amount = Math.round(Math.random() * 100000 + 1);
            threads.add(new Thread(() -> {
                try {
                    bank.checkSynchronised(fromAccountNum, toAccountNum, amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));

        }
    }

    public static void print(Bank bank) {
        Map<String, Account> accountMap = bank.getAccounts();
        int counter = 0;
        for (Map.Entry<String, Account> entry : accountMap.entrySet()) {
            System.out.println("На счету -> "
                    + entry.getValue().getAccNumber()
                    + "  " + entry.getValue().getMoney()
                    + "BYN");
            counter++;
            if(counter == 10){break;}
        }
    }

    public static void createAccounts(int count) {
        int num = 0;
        for (int i = 0; i < count; i++) {
            ++num;
            String accountNum = String.valueOf(num);
            accounts.add(new Account(100000L, accountNum));
        }
    }

    public static void addAccountsInBank(Bank bank) {
        for (int i = 0; i < accounts.size(); i++) {
            bank.addAccounts(accounts.get(i).getAccNumber(), accounts.get(i));
        }
    }
}
