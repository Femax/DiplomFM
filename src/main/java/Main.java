import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import excelapi.ExcelService;
import model.JsonCollection;
import model.Molecule;
import util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * Created by max on 24.09.2016.
 */
public class Main {

    public static void main(String args[]) throws IOException {
        Map<String, TreeMap<String, Molecule>> data = new HashMap<>();
        HashMap<String, JsonCollection> data2 = new HashMap<>();
        StringUtils.secondsToDate(123444);
        HashMap<String, JsonCollection> datapw61 = new HashMap<>();
        HashMap<String, JsonCollection> datapm6 = new HashMap<>();
        HashMap<String, JsonCollection> data1_S = new HashMap<>();
        HashMap<String, JsonCollection> data2_S = new HashMap<>();
        HashMap<String, JsonCollection> data3_S = new HashMap<>();
        HashMap<String, JsonCollection> data4_S = new HashMap<>();
        readFromFileForMethods(datapw61, datapm6);
        readFromFileForS(data1_S);
        System.out.println(data2);
//     There ParseALgo
//      saveFileInJson(data);

        File file = new File("resultS.xls");


        //  File file5 = new File("resultpm6pw61.xls");
        ExcelService.writeIntoExcelFileFromJsonByCountS(data1_S, file);

        //   ExcelService.writeIntoExcelFileFromJsonByCountS(datapw61, datapm6, file3);
    }

    private static void saveFileInJson(Map<String, TreeMap<String, Molecule>> data) {
        Gson gson = new Gson();
        for (Map.Entry<String, TreeMap<String, Molecule>> task : data.entrySet()) {

            String moleculename = task.getValue().firstEntry().getValue().getMoleculeName();
            ArrayList<Molecule> list = new ArrayList<>(task.getValue().values());
            JsonCollection jsonCollection = new JsonCollection();
            jsonCollection.setMolecules(list);

            String json = gson.toJson((jsonCollection));
            try {
                FileWriter fw = new FileWriter("./output/" + moleculename + ".txt");
                BufferedWriter out = new BufferedWriter(fw);
                out.write(json);
                out.close();

            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    }

    private static void readFromFileForMethods(HashMap<String, JsonCollection> datapw91, HashMap<String, JsonCollection> datapm6) throws FileNotFoundException {
        final File folder = new File("./output");
        HashMap<String, JsonCollection> data = new HashMap<>();
        for (final File fileEntry : folder.listFiles()) {
//            final Type REVIEW_TYPE = new TypeToken<ArrayList<Molecule>>() {
//            }.getType();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileEntry));
            reader.setLenient(true);
            System.out.println(fileEntry.getName());
            if (fileEntry.getName().contains("pw91"))
                datapw91.put(fileEntry.getName(), gson.fromJson(reader, JsonCollection.class));
            else
                datapm6.put(fileEntry.getName(), gson.fromJson(reader, JsonCollection.class));// contains the whole reviews list
        }

    }

    private static void readFromFileForS(HashMap<String, JsonCollection> data1_S) throws FileNotFoundException {
        final File folder = new File("./output");
        for (final File fileEntry : folder.listFiles()) {
//            final Type REVIEW_TYPE = new TypeToken<ArrayList<Molecule>>() {
//            }.getType();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileEntry));
            reader.setLenient(true);
            System.out.println(fileEntry.getName());
            data1_S.put(fileEntry.getName(), gson.fromJson(reader, JsonCollection.class));


        }

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
        ExcelService.writeIntoExcelFile(data, file);

    }


}
