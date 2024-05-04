package controllers;

import models.*;

import java.util.LinkedList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_RECENT_TASKS = 10;
    private final LinkedList<Task> recentViews = new LinkedList<>();

    public void add(Task task) {
        if (recentViews.size() == MAX_RECENT_TASKS) {
            recentViews.removeFirst();
        }
        recentViews.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return recentViews;
    }
}