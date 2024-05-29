package Test.controllers;

import controllers.HistoryManager;
import controllers.InMemoryHistoryManager;
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
        HistoryManager historyManager = new InMemoryHistoryManager();
        manager = new InMemoryTaskManager(historyManager);
    }

    // Проверка, что менеджер истории добавляет разные типы задач и может обнаружить их по id.
    @Test
    void checkInMemoryTaskManager() {
        // Подготовка
        Task task = new Task("Задача", "Описание задачи", NEW);
        Task createdTask = manager.createTask(task); // Создаем задачу с помощью createTask
        int taskId = createdTask.getId(); // Получаем id созданной задачи
        task.setId(taskId); // Устанавливаем id для исходной задачи, чтобы совпадать с createdTask
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
        Task originalTask = new Task(task.getTaskName(), task.getDescription(), task.getStatus());
        originalTask.setId(task.getId());
        manager.createTask(task);
        assertEquals(originalTask, task,
                "Поля задачи не должны изменяться после добавления задачи в менеджер.");
    }

    @Test
    public void testDeleteAllTasks() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
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
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        Task storedTask = taskManager.createTask(task1);
        storedTask.setTaskName("Имя задачи обновлено");
        taskManager.updateTask(storedTask);
        assertEquals("Имя задачи обновлено", taskManager.getTask(storedTask.getId()).getTaskName());
    }

    @Test
    public void testCreateEpic() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        Epic storedEpic = taskManager.createEpic(epic);
        assertEquals(epic, storedEpic);
    }

    @Test
    public void testUpdateEpic() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        Epic epic1 = new Epic("Эпик 1", "Описание 1", Status.NEW);
        Epic storedEpic = taskManager.createEpic(epic1);
        storedEpic.setTaskName("Имя эпика обновлено");
        taskManager.updateEpic(storedEpic);
        assertEquals("Имя эпика обновлено", taskManager.getEpic(storedEpic.getId()).getTaskName());
    }

    @Test
    public void testDeleteEpic() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        Epic epic1 = new Epic("Эпик 1", "Описание 1", Status.NEW);
        Epic storedEpic = taskManager.createEpic(epic1);
        taskManager.deleteEpicById(storedEpic.getId());
        assertNull(taskManager.getEpic(storedEpic.getId()));
    }

    @Test
    public void testGetAllEpics() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        for (int i = 0; i < 5; i++) {
            Epic epic = new Epic("Эпик " + i, "Описание " + i, Status.NEW);
            taskManager.createEpic(epic);
        }
        List<Epic> allEpics = taskManager.getAllEpics();
        assertEquals(5, allEpics.size());
    }

    @Test
    public void testCreateSubtask() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача 1", "Описание", Status.NEW, epic.getId());
        Subtask storedSubtask = taskManager.createSubtask(subtask);
        assertEquals(subtask, storedSubtask);
    }

    @Test
    public void testGetAllSubtasks() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
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
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Status.NEW, epic.getId());
        Subtask storedSubtask = taskManager.createSubtask(subtask1);
        taskManager.deleteSubtaskById(storedSubtask.getId());
        assertNull(taskManager.getSubtask(storedSubtask.getId()));
    }

    @Test
    public void testUpdateSubtask() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        Epic epic = new Epic("Эпик 1", "Описание 1", Status.NEW);
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Status.NEW, epic.getId());
        Subtask storedSubtask = taskManager.createSubtask(subtask1);
        storedSubtask.setTaskName("Имя подзадачи обновлено");
        taskManager.updateSubtask(storedSubtask);
        assertEquals("Имя подзадачи обновлено", taskManager.getSubtask(storedSubtask.getId()).getTaskName());
    }

    // Используя сеттеры, мы меняем поля экземпляров задач, после чего убеждаемся,
    // что изменения отражены в данных менеджера.
    @Test
    public void whenTaskFieldsAreChanged_TheseChangesAreReflectedInManager() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());
        Task task = new Task("Задача", "Описание задачи", Status.NEW);

        Task createdTask = manager.createTask(task);
        createdTask.setTaskName("Измененная задача");

        Task retrievedTask = manager.getTask(createdTask.getId());
        assertEquals("Измененная задача", retrievedTask.getTaskName());
    }

    // Проверить, что при удалении задачи, главной задачи или подзадачи, история корректно обновляется.
    @Test
    public void whenTaskIsDeleted_ItsRemovedFromHistory() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());
        Task task = new Task("Задача", "Описание задачи", Status.NEW);

        Task createdTask = manager.createTask(task);
        manager.deleteTaskById(createdTask.getId());

        List<Task> taskHistory = manager.getHistory();
        assertTrue(taskHistory.stream().noneMatch(t -> "Задача".equals(t.getTaskName())));
    }

    // Проверка, что при добавлении задачи или изменении статуса задачи, эти изменения корректно отражаются в истории.
    @Test
    public void whenTaskIsAddedOrStatusChanged_ItsReflectedInHistory() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());
        Task task = new Task("Задача", "Описание задачи", Status.NEW);

        Task createdTask = manager.createTask(task);
        createdTask.setStatus(Status.IN_PROGRESS);
        manager.updateTask(createdTask);

        List<Task> taskHistory = manager.getHistory();
        assertTrue(taskHistory.stream().anyMatch(t -> "Задача".equals(t.getTaskName())));
        assertTrue(taskHistory.stream().anyMatch(t -> t.getStatus() == Status.IN_PROGRESS));
    }

    // Проверка того, что внутри эпиков не остается неактуальных идентификаторов подзадач.
    @Test
    public void whenSubtaskIsDeleted_ItsIdIsRemovedFromParentEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());
        Epic epic = new Epic("Эпик", "Описание эпика", Status.NEW);
        Subtask subTask = new Subtask("Подзадача", "Описание подзадачи", Status.NEW, epic.getId());

        Epic createdEpic = manager.createEpic(epic);
        Subtask createdSubtask = manager.createSubtask(subTask);
        manager.deleteSubtaskById(createdSubtask.getId());

        assertFalse(createdEpic.getSubTaskIds().contains(createdSubtask.getId()));
    }
}