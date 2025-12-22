public class Main {
    public static void main(String[] args) {
        System.out.println("=== Task Manager Demo ===\n");

        TaskManager taskManager = new TaskManager(5);

        // Добавляем задачи
        taskManager.addTask("Купить молоко");
        taskManager.addTask("Сделать домашнее задание");
        taskManager.addTask("Позвонить маме");

        System.out.println("Всего задач: " + taskManager.getTaskCount());
        System.out.println("Невыполненных задач: " + taskManager.getPendingTaskCount());

        // Показываем все задачи
        System.out.println("\nСписок задач:");
        for (int i = 0; i < taskManager.getTaskCount(); i++) {
            System.out.println((i + 1) + ". " + taskManager.getTask(i));
        }

        // Отмечаем задачу как выполненную
        taskManager.markAsCompleted(0);
        System.out.println("\nПосле выполнения первой задачи:");
        System.out.println("Невыполненных задач: " + taskManager.getPendingTaskCount());

        // Поиск задач
        System.out.println("\nПоиск задач с 'домашнее':");
        for (String task : taskManager.searchTasks("домашнее")) {
            System.out.println("- " + task);
        }

        // Пытаемся добавить больше лимита
        System.out.println("\nПытаемся добавить больше лимита:");
        boolean added = taskManager.addTask("Новая задача 4");
        System.out.println("Добавлена 4-я задача: " + added);
        added = taskManager.addTask("Новая задача 5");
        System.out.println("Добавлена 5-я задача: " + added);
        added = taskManager.addTask("Новая задача 6");
        System.out.println("Добавлена 6-я задача: " + added + " (лимит достигнут)");
    }
}