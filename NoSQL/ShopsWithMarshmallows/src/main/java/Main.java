public class Main {

    public static void main(String[] args) {
        ProcessingForRequests processing = new ProcessingForRequests();

        processing.parsRequest("Добавить_магазин Девяточка");
        processing.parsRequest("Добавить_магазин Радуга");
        processing.parsRequest("Добавить_магазин Лерой");

        processing.parsRequest("Добавить_товар Вафли 54");
        processing.parsRequest("Добавить_товар Хлеб 15");
        processing.parsRequest("Добавить_товар Молоко 30");
        processing.parsRequest("Добавить_товар Масло 70");
        processing.parsRequest("Добавить_товар Сыр 87");
        processing.parsRequest("Добавить_товар Молоток 76");
        processing.parsRequest("Добавить_товар Дрель 300");

        processing.parsRequest("Выставить_товар Вафли Девяточка");
        processing.parsRequest("Выставить_товар Вафли Девяточка");
        processing.parsRequest("Выставить_товар Хлеб Девяточка");
        processing.parsRequest("Выставить_товар Молоко Девяточка");
        processing.parsRequest("Выставить_товар Сыр Радуга");
        processing.parsRequest("Выставить_товар Вафли Радуга");
        processing.parsRequest("Выставить_товар Масло Радуга");
        processing.parsRequest("Выставить_товар Молоток Лерой");
        processing.parsRequest("Выставить_товар Дрель Лерой");

        processing.parsRequest("Статистика_товаров");

    }
}
