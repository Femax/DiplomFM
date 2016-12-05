package model;

import util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 24.09.2016.
 */
public class Molecule {
    private String fileName;
    private Server server;
    private int time;
    private int countOfErrors;
    private int stepCount;
    private int stepTime;
    private String moleculeName;
    private List<String> structure = new ArrayList<>();

    public Molecule() {
    }

    public int getStepTime() {
        return stepTime;
    }

    public void calculateData() {
        if (time == 0) System.out.println("Time is 0");
        else if (stepCount==0) {
            System.out.println("StepCount = 0 ");
        } else {
            this.time = (int)time*server.getProcCount();
            this.stepTime = time/ stepCount;
        }
        if(structure.size()>0){
            StringBuilder sb = new StringBuilder();
            for(String i:structure){
//                if(sb.length()>=1) sb.append("-");
                sb.append(i);

            }
            moleculeName = sb.toString();
        }else moleculeName = "unknown";
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public int getTime() {
        return time;
    }

    public void increaseTime(int timeValue) {
        this.time =time + timeValue;
    }
    public void increaseStepTime(int stepTime){
        this.stepTime += stepTime;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }
    public void increaseStepCount(){
        this.stepCount = stepCount++;
    }

    @Override
    public String toString() {
        return "Molecule{" +
                "fileName='" + fileName + '\'' +
                ", server=" + server +
                ", time=" + StringUtils.secondsToDate(time) +
                ", stepCount=" + stepCount +
                ", stepTime=" + StringUtils.secondsToDate(stepTime) +
                ", structure=" + structure +
                '}';
    }

    public List<String> getStructure() {
        return structure;
    }
    public void addAtomToStructure(String atom){
        structure.add(atom);
    }

    public String getMoleculeName() {
        return moleculeName;
    }

    public void setMoleculeName(String moleculeName) {
        this.moleculeName = moleculeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Molecule molecule = (Molecule) o;

        if (time != molecule.time) return false;
        if (countOfErrors != molecule.countOfErrors) return false;
        if (stepCount != molecule.stepCount) return false;
        if (stepTime != molecule.stepTime) return false;
        if (fileName != null ? !fileName.equals(molecule.fileName) : molecule.fileName != null) return false;
        if (server != null ? !server.equals(molecule.server) : molecule.server != null) return false;
        if (moleculeName != null ? !moleculeName.equals(molecule.moleculeName) : molecule.moleculeName != null)
            return false;
        return structure != null ? structure.equals(molecule.structure) : molecule.structure == null;

    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (server != null ? server.hashCode() : 0);
        result = 31 * result + time;
        result = 31 * result + countOfErrors;
        result = 31 * result + stepCount;
        result = 31 * result + stepTime;
        result = 31 * result + (moleculeName != null ? moleculeName.hashCode() : 0);
        result = 31 * result + (structure != null ? structure.hashCode() : 0);
        return result;
    }
}
