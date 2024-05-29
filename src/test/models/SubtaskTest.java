package test.models;

import static models.Status.*;

import models.Subtask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    // Проверка равенства двух экземпляра класса Subtask с одинаковым id.
    @Test
    void subtaskEqualityById() {
        // Подготовка к тесту
        int parentEpicId = 1;
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", NEW, parentEpicId);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", IN_PROGRESS,
                parentEpicId);
        // Установим id вручную
        subtask1.setId(1);
        subtask2.setId(1);
        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым id должны быть равны.");
    }

    // Проверка, что экземпляр класса Subtask не может установить свой собственный id как id родительского Epic.
    @Test
    void cannotSetSelfAsParent() {
        // Подготовка к тесту
        int parentEpicId = 1; // произвольное значение для parentEpicId
        Subtask subtask = new Subtask("Подзадача", "Описание подзадачи", NEW, parentEpicId);
        // Проверка
        assertThrows(IllegalArgumentException.class, () -> subtask.setParentEpicId(subtask.getId()),
                "Подзадача не может устанавливать себя в качестве родительского эпоса");
    }
}