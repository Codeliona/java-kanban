import models.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TaskTest {

    // Проверка равенства двух экземпляра класса Task с одинаковым id.
    @Test
    void taskEqualityById() {
        // Подготовка к тесту
        Task task1 = new Task("Task 1", "Описание Задачи 1", Status.NEW);
        Task task2 = new Task("Task 2", "Описание Задачи 2", Status.IN_PROGRESS);
        // Устанавливаем одинаковый id для обеих задач
        task2.setId(task1.getId());
        // Проверка
        Assertions.assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны.");
    }

}