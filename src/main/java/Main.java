import model.Molecule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by max on 24.09.2016.
 */
public class Main {

    public static void main(String args[]) {
        Map<String, List<Molecule>> data = new HashMap<>();
        String pathToFolder = args[0];
        final File folder = new File(pathToFolder);
        // StringUtils.secondsToDate(123444);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
            } else {
                System.out.println(fileEntry.getName());
                try {
                    Molecule molecule = ParseAlgo.recognize(fileEntry);
                    if (data.get(molecule.getMoleculeName()) != null) {
                        data.get(molecule.getMoleculeName()).add(molecule);
                    } else {
                        ArrayList<Molecule> moleculesList = new ArrayList<>();
                        moleculesList.add(molecule);
                        data.put(molecule.getMoleculeName(), moleculesList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        outPutToOneFile(data);
    }

    public static void outPutToManyFiles(Map<String, List<Molecule>> data) {
        System.out.print(data);
        for (Map.Entry<String, List<Molecule>> i : data.entrySet()) {
            try (FileWriter writer = new FileWriter(i.getValue().get(0).getMoleculeName() + ".txt", false)) {
                int j = 0;
                for (Molecule molecule : i.getValue()) {
                    writer.write("" + j + " " + molecule.getTime());
                    writer.append('\n');
                }

                writer.flush();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }


        }
    }

    public static void outPutToOneFile(Map<String, List<Molecule>> data) {
        System.out.print(data);

        try (FileWriter writer = new FileWriter("result.txt", false)) {
            int j = 0;
            for (Map.Entry<String, List<Molecule>> i : data.entrySet()) {
                writer.write(i.getValue().get(0).getMoleculeName());
                writer.append('\n');
                for (Molecule molecule : i.getValue()) {
                    writer.write("" + j + " " + molecule.getTime());
                    writer.append('\n');
                }
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }
}
