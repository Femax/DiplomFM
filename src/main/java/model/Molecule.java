package model;

import util.StringUtils;

/**
 * Created by max on 24.09.2016.
 */
public class Molecule {
    private String moleculeName;
    private Server server;
    private String time;
    private int stepCount;
    private String stepTime;

    public Molecule() {
    }
    public Molecule(String moleculeName, Server server, String time, int stepCount) {
        this.moleculeName = moleculeName;
        this.server = server;
        this.time = time;
        this.stepCount = stepCount;
    }
    public String getStepTime() {
        return stepTime;
    }

    public void calculateStepTime() {
        if(time==null) System.out.println("Time is null");
        else this.stepTime = StringUtils.secondsToDate(StringUtils.stringTineToSeconds(time)/4);
    }


    public String getMoleculeName() {
        return moleculeName;
    }

    public void setMoleculeName(String moleculeName) {
        this.moleculeName = moleculeName;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    @Override
    public String toString() {
        return "Molecule{" +
                "moleculeName='" + moleculeName + '\'' +
                ", server=" + server +
                ", time='" + time + '\'' +
                ", stepCount=" + stepCount +
                ", stepTime='" + stepTime + '\'' +
                '}';
    }
}
