package Test.controllers;

import controllers.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    // Проверка, что класс утилит всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров.
    @Test
    void checkManagerInitialization() {
        assertNotNull(Managers.getDefault(), "Менеджер задач должен быть проинициализирован");
        assertNotNull(Managers.getDefaultHistory(), "Менеджер истории должен быть проинициализирован");
    }
}