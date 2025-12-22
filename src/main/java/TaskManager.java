import java.util.ArrayList;
import java.util.List;

/**
 * Простой менеджер задач (To-Do List)
 */
public class TaskManager {
    private final List<String> tasks;
    private final int maxTasks;

    public TaskManager(int maxTasks) {
        if (maxTasks <= 0) {
            throw new IllegalArgumentException("Max tasks must be positive");
        }
        this.tasks = new ArrayList<>();
        this.maxTasks = maxTasks;
    }

    public TaskManager() {
        this(10); // По умолчанию максимум 10 задач
    }

    /**
     * Добавить новую задачу
     */
    public boolean addTask(String task) {
        if (task == null || task.trim().isEmpty()) {
            throw new IllegalArgumentException("Task cannot be null or empty");
        }

        if (tasks.size() >= maxTasks) {
            return false; // Достигнут лимит задач
        }

        tasks.add(task.trim());
        return true;
    }

    /**
     * Удалить задачу по индексу
     */
    public boolean removeTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.remove(index);
        return true;
    }

    /**
     * Получить задачу по индексу
     */
    public String getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return tasks.get(index);
    }

    /**
     * Отметить задачу как выполненную
     */
    public boolean markAsCompleted(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }

        String task = tasks.get(index);
        if (!task.startsWith("[✓] ")) {
            tasks.set(index, "[✓] " + task);
        }
        return true;
    }

    /**
     * Получить количество задач
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Получить количество невыполненных задач
     */
    public int getPendingTaskCount() {
        int count = 0;
        for (String task : tasks) {
            if (!task.startsWith("[✓] ")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Проверить, пуст ли список задач
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Очистить все задачи
     */
    public void clearAllTasks() {
        tasks.clear();
    }

    /**
     * Поиск задач по ключевому слову
     */
    public List<String> searchTasks(String keyword) {
        List<String> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return result;
        }

        String searchTerm = keyword.toLowerCase().trim();
        for (String task : tasks) {
            if (task.toLowerCase().contains(searchTerm)) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Получить все задачи
     */
    public List<String> getAllTasks() {
        return new ArrayList<>(tasks); // Возвращаем копию
    }
}