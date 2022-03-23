import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class Movements {

    private List<Movements> data;
    private String nameCompany;
    private double expenseSumLine;
    private double incomeSumLine;

    public Movements(String pathMovementsCsv) {
        loadDataFromFilesCsv(pathMovementsCsv);
    }

    private Movements(String nameCompany, double income, double expense) {
        this.nameCompany = nameCompany;
        this.expenseSumLine = expense;
        this.incomeSumLine = income;
    }

    public void loadDataFromFilesCsv(String pathMovementsCsv) {
        data = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathMovementsCsv));
            for (String line : lines) {
                String[] fragments = line.split(",");
                if (fragments[0].equals("Тип счёта")) {
                    continue;
                }
                if (fragments.length == 9 && fragments[6].equals("0")) {
                    fragments[7] += "." + fragments[8];
                } else if (fragments.length == 9 && fragments[8].equals("0")) {
                    fragments[6] += "." + fragments[7];
                    data.add(new Movements(transformCellRecipient(fragments[5])
                            , removingQuotesCell(fragments[6])
                            , removingQuotesCell(fragments[8])));
                    continue;
                }
                data.add(new Movements(transformCellRecipient(fragments[5])
                        , removingQuotesCell(fragments[6])
                        , removingQuotesCell(fragments[7])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double removingQuotesCell(String argument) {
        return Double.parseDouble(argument.replaceAll("\"", ""));
    }

    private String transformCellRecipient(String argument) {
        String temp = argument.replace('\\', '/');
        int start = temp.indexOf("/");
        int end = temp.indexOf("  ", start);
        String result = temp.substring(start + 1, end);
        return result.replaceAll("/", "");
    }

    public List<Movements> getData() {
        data.sort(comparing(Movements::getNameCompany));
        return data;
    }

    public double getExpenseSum() {
        return data.stream()
                .mapToDouble(Movements::getExpenseSumLine).sum();
    }

    public double getIncomeSum() {
        return data.stream()
                .mapToDouble(Movements::getIncomeSumLine).sum();
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public double getExpenseSumLine() {
        return expenseSumLine;
    }

    public double getIncomeSumLine() {
        return incomeSumLine;
    }

    @Override
    public String toString() {
        return "Movements{" +
                "nameCompany='" + nameCompany + '\'' +
                ", expenseSumLine=" + expenseSumLine +
                ", incomeSumLine=" + incomeSumLine +
                '}';
    }
}
