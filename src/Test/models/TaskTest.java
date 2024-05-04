package Test.models;

import models.*;
import static models.Status.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    // Проверка равенства двух экземпляра класса Task с одинаковым id.
    @Test
    void taskEqualityById() {
        // Подготовка к тесту
        Task task1 = new Task("Task 1", "Описание Задачи 1", NEW);
        Task task2 = new Task("Task 2", "Описание Задачи 2", IN_PROGRESS);
        // Устанавливаем одинаковый id для обеих задач
        task2.setId(task1.getId());
        // Проверка
        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны.");
    }

}