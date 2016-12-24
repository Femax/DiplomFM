import com.google.gson.Gson;
import excelapi.ExcelService;
import model.Molecule;
import util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by max on 24.09.2016.
 */
public class Main {

    public static void main(String args[]) {
        Map<String, TreeMap<String, Molecule>> data = new HashMap<>();
        String pathToFolder = args[0];
        final File folder = new File(pathToFolder);
        // StringUtils.secondsToDate(123444);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
            } else {
                System.out.println(fileEntry.getName());

                try {

                    Molecule molecule = ParseAlgo.recognize(fileEntry);
                    String formFactor = molecule.getMoleculeName();
                    if (fileEntry.getName().contains(".cut")) {
                        if (data.get(formFactor) != null) {
                            if (data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(fileEntry.getName())) != null) {
                                data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(fileEntry.getName())).increaseTime(molecule.getTime());
                                data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(fileEntry.getName())).increaseStepTime(molecule.getStepTime());
                            } else {
                                data.get(formFactor).put(ParseAlgo.getFileNameWithoutFormat(fileEntry.getName()), molecule);
                            }
                        } else {
                            TreeMap<String, Molecule> moleculesList = new TreeMap<>();
                            moleculesList.put(ParseAlgo.getFileNameWithoutFormat(fileEntry.getName()), molecule);
                            data.put(formFactor, moleculesList);
                        }
                    } else if (data.get(formFactor) != null) {
                        data.get(formFactor).put(ParseAlgo.getFileNameWithoutFormat(fileEntry.getName()), molecule);
                    } else {
                        TreeMap<String, Molecule> moleculesList = new TreeMap<>();
                        moleculesList.put(ParseAlgo.getFileNameWithoutFormat(fileEntry.getName()), molecule);
                        data.put(formFactor, moleculesList);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        try {
            saveFileInJson(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPutToExcelFile(data);
    }

    private static void saveFileInJson( Map<String, TreeMap<String, Molecule>> data) throws IOException {
        Gson gson = new Gson();


        gson.toJson(data, new FileWriter("./data.json"));

        String jsonInString = gson.toJson(data);
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

    public static void outPutToOneFile(Map<String, TreeMap<String, Molecule>> data) {
        System.out.print(data);

        try (FileWriter writer = new FileWriter("result.txt", false)) {

            for (Map.Entry<String, TreeMap<String, Molecule>> i : data.entrySet()) {
                writer.write(i.getValue().firstEntry().getValue().getMoleculeName());
                writer.append('\n');
                int j = 0;
                for (Map.Entry<String, Molecule> molecule : i.getValue().entrySet()) {
                    if (molecule.getValue().getTime() != 0) {
                        writer.write("" + j + " " + molecule.getValue().getTime());
                        writer.append('\n');
                        j++;
                    }
                }
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }

    public static void outPutToExcelFile(Map<String, TreeMap<String, Molecule>> data) {
        System.out.print(data);

        File file = new File("result.xls");

        int i = 0;
        ExcelService.writeIntoExcelFile(data,file);

    }
}
