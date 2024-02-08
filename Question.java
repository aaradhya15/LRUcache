import java.util.*;

public class Question {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the maxSize of cache.");
        int maxSize = sc.nextInt();
        Cache lrucache = new Cache(maxSize);
        while (true) {
            int key = sc.nextInt();
            int value = sc.nextInt();
            lrucache.putItem(new Node(key, value));
        }

    }

}

class Node {
    Node prevNode;
    Node nextNode;

    int key, value;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class Cache {
    Node firstNode;
    Node lastNode;
    int maxSize;
    Map<Integer, Node> nodeMap;

    public Cache(int maxSize) {
        this.firstNode = new Node(0, 0);
        this.lastNode = new Node(0, 0);
        this.firstNode.nextNode = lastNode;
        this.lastNode.prevNode = firstNode;
        this.maxSize = maxSize;
        this.nodeMap = new HashMap<>();

        System.out.println("LRU cache created of max size " + maxSize);
    }

    public void putItem(Node node) {
        checkItem(node.key);
        if (nodeMap.size() >= maxSize) {
            System.out.println("Cache size full. Erasing item.");
            eraseItem(null);
        }

        Node currentTopNode = this.firstNode.nextNode;
        this.firstNode.nextNode = node;
        node.prevNode = this.firstNode;
        node.nextNode = currentTopNode;
        currentTopNode.prevNode = node;
        nodeMap.put(node.key, node);
    }

    public void eraseItem(Node node) {
        System.out.println("Evicting node...");
        if (node == null) {
            Node leastRecentlyUsedNode = this.lastNode.prevNode;
            leastRecentlyUsedNode.prevNode.nextNode = this.lastNode;
            this.lastNode.prevNode = leastRecentlyUsedNode.prevNode;
            printNode(leastRecentlyUsedNode);
            nodeMap.remove(leastRecentlyUsedNode.key);
        }

        else {
            node.nextNode.prevNode = node.prevNode;
            node.prevNode.nextNode = node.nextNode;
            printNode(node);
            nodeMap.remove(node.key);
        }

    }

    public void printNode(Node node) {
        System.out.println("Node is : node key - " + node.key + " node value - " + node.value);
    }

    public Node getItem(int key) {
        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            eraseItem(node);
            putItem(node);
            printNode(node);
            return node;
        }
        return null;
    }

    public void checkItem(int key) {
        if (nodeMap.containsKey(key)) {
            System.out.println("Node already exists");
            eraseItem(nodeMap.get(key));
        }
    }
}
