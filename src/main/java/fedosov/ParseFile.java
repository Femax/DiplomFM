package fedosov;

import com.google.gson.Gson;
import fedosov.model.JsonCollection;
import fedosov.model.Molecule;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by fedosovmax on 10.01.17.
 */
public class ParseFile {
    public static void main(String args[]) {
        //fedosov.ParseAlgo
        String pathToFolder = args[0];

        final File folder = new File(pathToFolder);


        Map<String, TreeMap<String, Molecule>> data = new HashMap<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
            } else {
                System.out.println(fileEntry.getName());
                parseFileFromZip(fileEntry, data);

            }
        }
        saveFileInJson(data);
    }

    private static void parseFileFromTar(File file, Map<String, TreeMap<String, Molecule>> data) {
        try {
            TarArchiveInputStream tarInput = new TarArchiveInputStream(new BZip2CompressorInputStream(new FileInputStream(file)));

            TarArchiveEntry currentEntry = (TarArchiveEntry) tarInput.getNextTarEntry();
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            while (currentEntry != null) {
                // Read directly from tarInput


                try {
                    if (currentEntry.getName().contains(".out")) {
                        System.out.println(currentEntry.getName());
                        Molecule molecule = ParseAlgo.recognizeTar(currentEntry, file, tarInput);
                        String formFactor = molecule.getMoleculeName();
                        if (currentEntry.getName().contains(".cut")) {
                            if (data.get(formFactor) != null) {
                                if (data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName())) != null) {
                                    data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName())).increaseTime(molecule.getTime());
                                    data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName())).increaseStepTime(molecule.getStepTime());
                                } else {
                                    data.get(formFactor).put(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName()), molecule);
                                }
                            } else {
                                TreeMap<String, Molecule> moleculesList = new TreeMap<>();
                                moleculesList.put(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName()), molecule);
                                data.put(formFactor, moleculesList);
                            }
                        } else if (data.get(formFactor) != null) {
                            data.get(formFactor).put(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName()), molecule);
                        } else {
                            TreeMap<String, Molecule> moleculesList = new TreeMap<>();
                            moleculesList.put(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName()), molecule);
                            data.put(formFactor, moleculesList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                currentEntry = tarInput.getNextTarEntry(); // You forgot to iterate to the next file

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseFileFromZip(File file, Map<String, TreeMap<String, Molecule>> data) {

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);


            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry currentEntry = entries.nextElement();

                try {
                    if (currentEntry.getName().contains(".out")) {
                        System.out.println(currentEntry.getName());
                        Molecule molecule = ParseAlgo.recognizeZip(currentEntry, file, zipFile);
                        String formFactor = molecule.getMoleculeName();
                        if (currentEntry.getName().contains(".cut")) {
                            if (data.get(formFactor) != null) {
                                if (data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName())) != null) {
                                    data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName())).increaseTime(molecule.getTime());
                                    data.get(formFactor).get(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName())).increaseStepTime(molecule.getStepTime());
                                } else {
                                    data.get(formFactor).put(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName()), molecule);
                                }
                            } else {
                                TreeMap<String, Molecule> moleculesList = new TreeMap<>();
                                moleculesList.put(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName()), molecule);
                                data.put(formFactor, moleculesList);
                            }
                        } else if (data.get(formFactor) != null) {
                            data.get(formFactor).put(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName()), molecule);
                        } else {
                            TreeMap<String, Molecule> moleculesList = new TreeMap<>();
                            moleculesList.put(ParseAlgo.getFileNameWithoutFormat(currentEntry.getName()), molecule);
                            data.put(formFactor, moleculesList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
