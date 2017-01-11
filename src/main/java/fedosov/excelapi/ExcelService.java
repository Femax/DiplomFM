package fedosov.excelapi;

import fedosov.model.JsonCollection;
import fedosov.model.Molecule;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by femax on 05.12.2016.
 */
public class ExcelService {

    private static Workbook book = new HSSFWorkbook();
    private static Sheet sheet = book.createSheet("Data");

    //Выводит данные из обработанных файлов
    public static void writeIntoExcelFile(Map<String, TreeMap<String, Molecule>> data, File file) {
        int i = 0;
        Row row = sheet.createRow(0);
        for (Map.Entry<String, TreeMap<String, Molecule>> task : data.entrySet()) {


            Cell name = row.createCell(i);
            name.setCellValue(task.getValue().firstEntry().getValue().getMoleculeName());
            int j = 1;
            for (Map.Entry<String, Molecule> molecule : task.getValue().entrySet()) {
                if (molecule.getValue().getTime() != 0) {
                    if (sheet.getPhysicalNumberOfRows() - 1 < j) {
                        Row rowTime = sheet.createRow(j);
                        Cell time = rowTime.createCell(i);
                        time.setCellValue(molecule.getValue().getTime());
                    } else {
                        Row rowTime = sheet.getRow(j);
                        Cell time = rowTime.createCell(i);
                        time.setCellValue(molecule.getValue().getTime());
                    }
                    j++;
                }
            }
            j = 1;
            i++;
        }
        try {
            book.write(new FileOutputStream(file));
            book.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Выводит данные из Json по маске
    public static void writeIntoExcelFileFromJsonByCountS(HashMap<String, JsonCollection> data1_S, File file) {
        int i = 0;
        Row row = sheet.createRow(0);
        for (Map.Entry<String, JsonCollection> task : data1_S.entrySet()) {
            if (task.getKey().contains("1_S")) i = 0;
            if (task.getKey().contains("2_S")) i = 1;
            if (task.getKey().contains("3_S")) i = 2;
            if (task.getKey().contains("4_S")) i = 3;
            int j = 1;
            for (Molecule molecule : task.getValue().getMolecules()) {
                if (molecule.getTime() != 0) {
                    if (sheet.getPhysicalNumberOfRows() - 1 < j) {
                        Row rowTime = sheet.createRow(j);
                        Cell time = rowTime.createCell(i);
                        time.setCellValue(molecule.getTime());
                    } else {
                        Row rowTime = sheet.getRow(j);
                        Cell time = rowTime.createCell(i);
                        time.setCellValue(molecule.getTime());
                    }
                    j++;
                }
            }
            j = 1;


            try {
                book.write(new FileOutputStream(file));
                book.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        book = new HSSFWorkbook();
        sheet = book.createSheet("Data");
    }

    // Выводит данные по методам
    public static void writeIntoExcelFileFromJsonByMethod(HashMap<String, JsonCollection> datapw61, HashMap<String, JsonCollection> datapm6, File file) {
        int i = 1;
        Row row0 = sheet.createRow(0);


        for (Map.Entry<String, JsonCollection> task : datapw61.entrySet()) {
            if (datapm6.containsKey(getSameNameWithOtherMethod(task.getKey(), "pm6"))) {

                Cell name = row0.createCell(1);
                name.setCellValue(task.getKey());

                name = row0.createCell(2);
                name.setCellValue(getSameNameWithOtherMethod(task.getKey(), "pm6"));

                Row dataRow = sheet.createRow(i);
                Cell fileName = dataRow.createCell(0);
                fileName.setCellValue(task.getKey());


                Cell time = dataRow.createCell(1);
                time.setCellValue(task.getValue().getSSTime());


                JsonCollection molecule = datapm6.get(getSameNameWithOtherMethod(task.getKey(), "pm6"));
                time = dataRow.createCell(2);
                time.setCellValue(molecule.getSSTime());
                i++;
            }
            try {
                book.write(new FileOutputStream(file));
                book.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private static String getSameNameWithOtherMethod(String key, String method) {
        return key.replace("MName_pw91pw91", "MName_pm6");
    }
}
