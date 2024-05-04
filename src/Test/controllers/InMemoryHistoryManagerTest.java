package Test.controllers;

import models.*;
import controllers.*;
import org.junit.jupiter.api.Test;

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
        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
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
        assertEquals(historyLimit, history.size());
        assertEquals("Задача 6", history.get(0).getTaskName());
    }
}
