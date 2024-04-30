import controllers.Manager;
import models.*;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = manager.createTask(new Task("Задача #1", "Описание первой задачи",
                Status.IN_PROGRESS));
        Task task2 = manager.createTask(new Task("Задача #2", "Описание второй задачи",
                Status.IN_PROGRESS));

        Epic epic1 = manager.createEpic(new Epic("Эпик #1", "Описание первого эпика",
                Status.NEW));
        Epic epic2 = manager.createEpic(new Epic("Эпик #2", "Описание второго эпика",
                Status.NEW));

        Subtask subtask1 = manager.createSubtask(new Subtask("Подзадача #1",
                "Описание первой подзадачи", Status.NEW, epic1.getId()));
        Subtask subtask2 = manager.createSubtask(new Subtask("Подзадача #2",
                "Описание второй подзадачи", Status.NEW, epic2.getId()));

        Subtask subtask3 = new Subtask("Подзадача #3", "Описание третьей подзадачи",
                Status.DONE, epic2.getId());
        manager.createSubtask(subtask3);

        // Печать текущего состояния
        printCurrentState(manager);

        // Изменение статусов подзадач и вывод состояния эпиков
        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);
        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);
        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask3);

        System.out.println("Status эпиков после завершения всех подзадач: " + manager.getAllEpics());

        // Изменение статуса задач
        task1.setStatus(Status.DONE);
        manager.updateTask(task1);
        task2.setStatus(Status.DONE);
        manager.updateTask(task2);

        // Распечатка отредактированного состояния
        printCurrentState(manager);

        // Удаление задачи и эпика
        manager.deleteTaskById(task1.getId());
        manager.deleteEpicById(epic1.getId());

        // Распечатка конечного состояния
        printCurrentState(manager);
    }

    private static void printCurrentState(Manager manager) {
        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());
    }
}