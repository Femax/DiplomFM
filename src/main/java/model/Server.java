package model;

/**
 * Created by max on 24.09.2016.
 */
public class Server {
    private String name;
    private Byte procCount;
    private int memory;

    public Server(String name, byte procCount, int memory) {
        this.name = name;
        this.procCount = procCount;
        this.memory = memory;
    }

    public Server() {
    }

    public String getName() {
        return name;
    }

    public Byte getProcCount() {
        return procCount;
    }

    public int getMemory() {
        return memory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProcCount(byte procCount) {
        this.procCount = procCount;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", procCount=" + procCount +
                ", memory=" + memory +
                '}';
    }
}
