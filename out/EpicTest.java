package test.models;

import models.Epic;
import models.Subtask;

import static models.Status.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;


class EpicTest {

    // Проверка равенства двух экземпляра класса Epic с одинаковым id.
    @Test
    void epicEqualityById() {
        // Подготовка к тесту
        Epic epic1 = new Epic("Epic 1", "Описание эпика 1", Status.NEW);
        Epic epic2 = new Epic("Epic 2", "Описание эпика 2", Status.IN_PROGRESS);
        // Устанавливаем одинаковый id для обоих эпиков
        epic2.setId(epic1.getId());
        // Проверка
        Assertions.assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны.");
    }

    // Проверка, что экземпляр класса Epic не может быть добавлен в себя в качестве подзадачи.
    @Test
    void cannotAddSelfAsSubtask() {
        Epic epic = new Epic("Эпик", "Описание эпика", Status.NEW);
        Subtask subtask = new Subtask("Эпик подзадача", "Описание подзадачи", Status.NEW, epic.getId());  // передаем parentId при создании
        subtask.setId(epic.getId());   // Устанавливаем идентификатор подзадачи таким же, как и у эпика

        Assertions.assertThrows(IllegalArgumentException.class, () -> epic.addSubtask(subtask),
                "Эпик не может добавляться в себя в качестве подзадачи");
    }
}