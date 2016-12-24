package model;

import util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by max on 24.09.2016.
 */
public class Molecule {
    private String fileName;
    private Server server;
    private String method;
    private int time;
    private boolean error;
    private int stepCount;
    private int stepTime;
    private String moleculeName;
    private List<String> structure = new ArrayList<>();
    private String formFactor;

    public Molecule() {
    }

    public int getStepTime() {
        return stepTime;
    }

    public void fillData() {
        if (time == 0) System.out.println("Time is 0");
        else if (stepCount == 0) {
            System.out.println("StepCount = 0 ");
        } else {
            this.time = (int) time * server.getProcCount();
            this.stepTime = time / stepCount;
        }
        if (fileName.contains("--")) {
            moleculeName = fileName.substring(0, fileName.indexOf("--"));
            formFactor = "--np" + this.server.getProcCount()+"--ME"+this.server.getMemory()+"--MName_"+this.method;
            moleculeName= moleculeName +  formFactor;
        } else {
            Pattern pattern = Pattern.compile("(.*)(-)([A-Z]*)");
            Matcher matcher = pattern.matcher(fileName);
            if(matcher.find()){
                moleculeName = matcher.group(1);
            }
            else {
                System.out.println("Problems with molecule name at file"+fileName);

            }
            moleculeName= moleculeName + this.server.getProcCount();
        }
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
        this.time = time + timeValue;
    }

    public void increaseStepTime(int stepTime) {
        this.stepTime += stepTime;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public void increaseStepCount() {
        this.stepCount = stepCount++;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
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

    public void addAtomToStructure(String atom) {
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
        if (error != molecule.error) return false;
        if (stepCount != molecule.stepCount) return false;
        if (stepTime != molecule.stepTime) return false;
        if (fileName != null ? !fileName.equals(molecule.fileName) : molecule.fileName != null) return false;
        if (server != null ? !server.equals(molecule.server) : molecule.server != null) return false;
        if (method != null ? !method.equals(molecule.method) : molecule.method != null) return false;
        if (moleculeName != null ? !moleculeName.equals(molecule.moleculeName) : molecule.moleculeName != null)
            return false;
        return structure != null ? structure.equals(molecule.structure) : molecule.structure == null;

    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (server != null ? server.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + time;
        result = 31 * result + (error ? 1 : 0);
        result = 31 * result + stepCount;
        result = 31 * result + stepTime;
        result = 31 * result + (moleculeName != null ? moleculeName.hashCode() : 0);
        result = 31 * result + (structure != null ? structure.hashCode() : 0);
        return result;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }
}
