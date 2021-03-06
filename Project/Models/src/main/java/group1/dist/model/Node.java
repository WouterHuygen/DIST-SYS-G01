package group1.dist.model;

public class Node {
    private int id;
    private String name;
    private String ip;

    public Node() {
    }

    public Node(String name, String ip) {
        this.name = name;
        this.id = calculateHash(name);
        this.ip = ip;
    }

    public static int calculateHash(String name) {
        int hash = (name.hashCode() & (32768 - 1));
        return ((hash * hash) & (32768 - 1));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public void setName(String name) {
        this.name = name;
        this.id = calculateHash(name);
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Node: " + name + "(" + id + ")" + "\n\tip: " + ip;
    }
}
