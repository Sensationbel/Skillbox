import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

  public static final String PATH_MOVEMENTS_CSV = "src/test/resources/movementList.csv";

  public static void main(String[] args) {

    Movements movements = new Movements(PATH_MOVEMENTS_CSV);

    System.out.println("Amounts of expenses: " + movements.getExpenseSum() + " RUR");
    System.out.println("Amount of incomes: " + movements.getIncomeSum() + " RUR\n");
    Main.calculationOfExpenses(movements.getData());


  }

  public static void calculationOfExpenses(List<Movements> data) {
    String name = "";
    Map<Double, String> expenses = new TreeMap<>(Collections.reverseOrder());

    for (var str : data) {
      if (str.getNameCompany().equals(name) && str.getExpenseSumLine() == 0.0) {
        continue;
      }
      name = str.getNameCompany();
      String finalName = name;
      double count = data.stream()
          .filter(movements -> movements.getNameCompany().equals(finalName))
          .mapToDouble(Movements::getExpenseSumLine)
          .sum();
      expenses.put(count, finalName);
    }
    Main.print(expenses);
  }

  private static void print(Map<Double, String> expenses) {
    StringBuilder builder = new StringBuilder();
    builder.append("Amounts of expenses by organizations: \n\n");
    for (Map.Entry entry : expenses.entrySet()) {
      builder.append(entry.getValue()).append(":\t")
          .append(entry.getKey()).append(" RUR").append("\n");
    }
    System.out.println(builder);
  }
}
