package Test.controllers;

import controllers.InMemoryTaskManager;
import models.*;
import static models.Status.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    InMemoryTaskManager manager; // Объявление менеджера задач

    // Инициализация менеджера задач перед каждым тестом
    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }

    // Проверка, что менеджер истории добавляет разные типы задач и может обнаружить их по id.
    @Test
    void checkInMemoryTaskManager() {
        // Подготовка
        Task task = new Task("Задача", "Описание задачи", NEW);
        Task createdTask = manager.createTask(task); // Создаем задачу с помощью createTask
        int taskId = createdTask.getId(); // Получаем id созданной задачи
        Task retrievedTask = manager.getTask(taskId); // Получаем задачу по ее id
        assertEquals(task, retrievedTask, "Созданная задача и извлеченная по id должны быть одинаковыми.");
    }

    // Проверяет, что задачи с заданным id и генерируемым менеджером id не конфликтуют внутри менеджера.
    @Test
    void checkIdConflict() {
        // Подготовка
        Task manualIdTask = new Task("Заданный ID Задачи", "Description", NEW);
        manualIdTask.setId(100);
        Task manualAddedTask = manager.createTask(manualIdTask); // Добавляем задачу с заданным id
        Task autoIdTask = new Task("Сгенерированный ID задачи", "Описание", NEW);
        Task autoAddedTask = manager.createTask(autoIdTask); // Добавляем задачу с автоматически генерируемым id
        assertNotNull(manager.getTask(manualAddedTask.getId()), "Задача с заданным id должна быть обнаружена.");
        assertNotNull(manager.getTask(autoAddedTask.getId()), "Задача с генерируемым id должна быть обнаружена.");
    }

    // Проверка, что задача остается неизменной после добавления в менеджер.
    @Test
    void checkTaskImmutability() {
        Task task = new Task("Задача", "Описание задачи", NEW);
        // Создаем копию исходной задачи для сравнения
        Task originalTask = new Task("Задача", "Описание задачи", NEW);
        manager.createTask(task); // Добавляем задачу
        assertEquals(originalTask, task,
                "Поля задачи не должны изменяться после добавления задачи в менеджер.");
    }

    @Test
    public void testDeleteAllTasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        for (int i = 0; i < 5; i++) {
            Task task = new Task("Задача " + i, "Описание " + i, Status.NEW);
            taskManager.createTask(task);
        }
        taskManager.deleteAllTasks();
        List<Task> allTasks = taskManager.getAllTasks();
        assertTrue(allTasks.isEmpty());
    }

    @Test
    public void testUpdateTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        Task storedTask = taskManager.createTask(task1);
        storedTask.setTaskName("Имя задачи обновлено");
        taskManager.updateTask(storedTask);
        assertEquals("Имя задачи обновлено", taskManager.getTask(storedTask.getId()).getTaskName());
    }

    @Test
    public void testCreateEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        Epic storedEpic = taskManager.createEpic(epic);
        assertEquals(epic, storedEpic);
    }

    @Test
    public void testUpdateEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Эпик 1", "Описание 1", Status.NEW);
        Epic storedEpic = taskManager.createEpic(epic1);
        storedEpic.setTaskName("Имя эпика обновлено");
        taskManager.updateEpic(storedEpic);
        assertEquals("Имя эпика обновлено", taskManager.getEpic(storedEpic.getId()).getTaskName());
    }

    @Test
    public void testDeleteEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Эпик 1", "Описание 1", Status.NEW);
        Epic storedEpic = taskManager.createEpic(epic1);
        taskManager.deleteEpicById(storedEpic.getId());
        assertNull(taskManager.getEpic(storedEpic.getId()));
    }

    @Test
    public void testGetAllEpics() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        for (int i = 0; i < 5; i++) {
            Epic epic = new Epic("Эпик " + i, "Описание " + i, Status.NEW);
            taskManager.createEpic(epic);
        }
        List<Epic> allEpics = taskManager.getAllEpics();
        assertEquals(5, allEpics.size());
    }

    @Test
    public void testCreateSubtask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача 1", "Описание", Status.NEW, epic.getId());
        Subtask storedSubtask = taskManager.createSubtask(subtask);
        assertEquals(subtask, storedSubtask);
    }

    @Test
    public void testGetAllSubtasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        taskManager.createEpic(epic);
        for (int i = 0; i < 5; i++) {
            Subtask subtask = new Subtask("Подзадача " + i, "Описание " + i, Status.NEW, epic.getId());
            taskManager.createSubtask(subtask);
        }
        List<Subtask> allSubtasks = taskManager.getAllSubtasks();
        assertEquals(5, allSubtasks.size());
    }

    @Test
    public void testDeleteSubtask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Status.NEW, epic.getId());
        Subtask storedSubtask = taskManager.createSubtask(subtask1);
        taskManager.deleteSubtaskById(storedSubtask.getId());
        assertNull(taskManager.getSubtask(storedSubtask.getId()));
    }

    @Test
    public void testUpdateSubtask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Status.NEW, epic.getId());
        Subtask storedSubtask = taskManager.createSubtask(subtask1);
        storedSubtask.setTaskName("Имя подзадачи обновлено");
        taskManager.updateSubtask(storedSubtask);
        assertEquals("Имя подзадачи обновлено", taskManager.getSubtask(storedSubtask.getId()).getTaskName());
    }
}