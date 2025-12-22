import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager(5); // Максимум 5 задач
    }

    // Тест 1: Проверка добавления и удаления задач
    @Test
    @DisplayName("Добавление и удаление задач")
    void testAddAndRemoveTasks() {
        // Проверяем начальное состояние
        assertTrue(taskManager.isEmpty());
        assertEquals(0, taskManager.getTaskCount());

        // Добавляем задачи
        assertTrue(taskManager.addTask("Задача 1"));
        assertTrue(taskManager.addTask("Задача 2"));
        assertEquals(2, taskManager.getTaskCount());
        assertFalse(taskManager.isEmpty());

        // Проверяем получение задач
        assertEquals("Задача 1", taskManager.getTask(0));
        assertEquals("Задача 2", taskManager.getTask(1));

        // Удаляем задачу
        assertTrue(taskManager.removeTask(0));
        assertEquals(1, taskManager.getTaskCount());
        assertEquals("Задача 2", taskManager.getTask(0));

        // Пытаемся удалить несуществующую задачу
        assertFalse(taskManager.removeTask(10));
        assertFalse(taskManager.removeTask(-1));
    }

    // Тест 2: Проверка лимита задач и обработки ошибок
    @Test
    @DisplayName("Проверка лимита задач и исключений")
    void testTaskLimitsAndExceptions() {
        // Добавляем максимальное количество задач
        for (int i = 1; i <= 5; i++) {
            assertTrue(taskManager.addTask("Задача " + i));
        }

        assertEquals(5, taskManager.getTaskCount());

        // Пытаемся добавить сверх лимита
        assertFalse(taskManager.addTask("Лишняя задача"));
        assertEquals(5, taskManager.getTaskCount()); // Количество не изменилось

        // Проверяем исключения при невалидных данных
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> taskManager.addTask(null));
        assertEquals("Task cannot be null or empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> taskManager.addTask("   "));
        assertEquals("Task cannot be null or empty", exception.getMessage());

        // Проверяем исключение при неверном индексе
        exception = assertThrows(IndexOutOfBoundsException.class,
                () -> taskManager.getTask(10));
        assertTrue(exception.getMessage().contains("Invalid task index"));
    }

    // Тест 3: Проверка отметки задач как выполненных и поиска
    @Test
    @DisplayName("Отметка задач выполненными и поиск")
    void testMarkCompletedAndSearch() {
        // Добавляем тестовые задачи
        taskManager.addTask("Купить продукты");
        taskManager.addTask("Сделать уборку");
        taskManager.addTask("Позвонить врачу");
        taskManager.addTask("Купить лекарства");

        // Проверяем начальное количество невыполненных задач
        assertEquals(4, taskManager.getPendingTaskCount());

        // Отмечаем две задачи как выполненные
        assertTrue(taskManager.markAsCompleted(0)); // Купить продукты
        assertTrue(taskManager.markAsCompleted(2)); // Позвонить врачу

        // Проверяем обновленное состояние
        assertEquals(2, taskManager.getPendingTaskCount());

        // Проверяем, что задачи помечены правильно
        assertTrue(taskManager.getTask(0).startsWith("[✓] "));
        assertTrue(taskManager.getTask(2).startsWith("[✓] "));
        assertFalse(taskManager.getTask(1).startsWith("[✓] "));

        // Проверяем поиск
        List<String> searchResults = taskManager.searchTasks("купить");
        assertEquals(2, searchResults.size());
        assertTrue(searchResults.get(0).toLowerCase().contains("купить"));
        assertTrue(searchResults.get(1).toLowerCase().contains("купить"));

        // Проверяем поиск по несуществующему слову
        searchResults = taskManager.searchTasks("несуществующее");
        assertTrue(searchResults.isEmpty());

        // Проверяем поиск с пустым запросом
        searchResults = taskManager.searchTasks("");
        assertTrue(searchResults.isEmpty());

        // Проверяем отметку несуществующей задачи
        assertFalse(taskManager.markAsCompleted(10));
    }

    // Дополнительный тест: Проверка конструктора
    @Test
    @DisplayName("Проверка конструкторов")
    void testConstructors() {
        // Тестируем конструктор с лимитом
        TaskManager customManager = new TaskManager(3);
        for (int i = 0; i < 3; i++) {
            customManager.addTask("Задача " + i);
        }
        assertFalse(customManager.addTask("Лишняя"));

        // Тестируем конструктор по умолчанию
        TaskManager defaultManager = new TaskManager();
        // Должен создаться с лимитом 10 задач
        for (int i = 0; i < 10; i++) {
            assertTrue(defaultManager.addTask("Задача " + i));
        }
        assertFalse(defaultManager.addTask("11-я задача"));

        // Проверяем исключение при невалидном лимите
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new TaskManager(0));
        assertEquals("Max tasks must be positive", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> new TaskManager(-5));
        assertEquals("Max tasks must be positive", exception.getMessage());
    }
}