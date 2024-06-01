package test.controllers;

import controllers.InMemoryHistoryManager;
import controllers.InMemoryTaskManager;
import models.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    // Проверка, что после добавления задачи, задача появляется в истории.
    @Test
    void addTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Задача 1", "Описание", Status.NEW);

        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(1, history.size());
        Assertions.assertEquals(task, history.get(0));
    }

    // Проверка, что что InMemoryHistoryManager сохраняет только заданное количество последних задач
    // и корректно удаляет более старые задачи.
    @Test
    void maxRecentTasksLimit() {
        int historyLimit = 10;
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        for (int i = 1; i <= historyLimit + 5; i++) {
            Task task = new Task("Задача " + i, "Описание", Status.NEW);
            historyManager.add(task);
        }

        List<Task> history = historyManager.getHistory();
        Assertions.assertEquals(historyLimit, history.size());
        Assertions.assertEquals("Задача 6", history.get(0).getTaskName());
    }

    @Test
    public void testHistoryManager() {
        InMemoryHistoryManager manager = new InMemoryHistoryManager();

        // Задаем максимальное количество задач
        int maxRecentTasks = 10;

        // создаем максимальное количество задач
        List<Task> createdTasks = new ArrayList<>();
        for (int i = 0; i < maxRecentTasks; i++) {
            Task task = new Task("Задача " + (i + 1), "Описание", Status.NEW);
            manager.add(task);
            createdTasks.add(task);
        }

        // Проверяем, что задачи добавлены в историю
        List<Task> history = manager.getHistory();
        Assertions.assertEquals(maxRecentTasks, history.size());
        for (Task task : createdTasks) {
            Assertions.assertTrue(history.contains(task));
        }

        // Добавляем еще одну задачу, выходящую за пределы maxRecentTasks
        Task extraTask = new Task("Extra task", "Описание", Status.NEW);
        manager.add(extraTask);

        // Убеждаемся, что старейшая задача была удалена из истории
        history = manager.getHistory();
        Assertions.assertEquals(maxRecentTasks, history.size());
        Assertions.assertFalse(history.contains(createdTasks.get(0)));
    }


    // Проверка корректного хранения версий задач при использовании нового алгоритма.
    @Test
    public void whenTaskIsModified_ItsOldVersionIsStoredInHistory() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());
        Task task = new Task("Задача", "Описание задачи", Status.NEW);

        Task createdTask = manager.createTask(task);
        createdTask.setTaskName("Измененная задача");
        manager.updateTask(createdTask);

        List<Task> taskHistory = manager.getHistory();
        long modifiedCount = taskHistory.stream().filter(t -> "Измененная задача".equals(t.getTaskName())).count();

        Assertions.assertEquals(1, modifiedCount, "Modified task not found in history");
    }
}
