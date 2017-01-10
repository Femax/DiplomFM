package model;

/**
 * Created by max on 24.09.2016.
 */
public class Server {
    private String name;
    private Byte procCount;
    private String date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Server server = (Server) o;

        if (memory != server.memory) return false;
        if (name != null ? !name.equals(server.name) : server.name != null) return false;
        return procCount != null ? procCount.equals(server.procCount) : server.procCount == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (procCount != null ? procCount.hashCode() : 0);
        result = 31 * result + memory;
        return result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
