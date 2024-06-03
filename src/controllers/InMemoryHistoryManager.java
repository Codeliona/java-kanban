package controllers;

import models.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAXRECENTTASKS = 10;

    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private final Node head = new Node();
    private final Node tail = new Node();

    public InMemoryHistoryManager() {
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        Node node = nodeMap.get(task.getId());
        if (node != null) {
            removeNode(node);
        }

        node = new Node();
        node.task = task;
        nodeMap.put(task.getId(), node);

        addNode(node);

        if (nodeMap.size() > MAXRECENTTASKS) {
            Node first = head.next;
            removeNode(first);
            nodeMap.remove(first.task.getId());
        }
    }

    private void addNode(Node nodeToAdd) {
        Node last = tail.prev;
        last.next = nodeToAdd;
        nodeToAdd.prev = last;
        nodeToAdd.next = tail;
        tail.prev = nodeToAdd;
    }

    private void removeNode(Node nodeToRemove) {
        Node nextNode = nodeToRemove.next;
        Node prevNode = nodeToRemove.prev;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    @Override
    public void remove(int id) {
        Node node = nodeMap.get(id);
        if (node != null) {
            removeNode(node);
            nodeMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        Node cur = head.next;
        while (cur != tail) {
            list.add(cur.task);
            cur = cur.next;
        }
        return list;
    }

    private static class Node {
        Task task;
        Node prev;
        Node next;
    }
}