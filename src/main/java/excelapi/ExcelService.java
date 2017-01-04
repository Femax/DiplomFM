package excelapi;

import model.JsonCollection;
import model.Molecule;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by femax on 05.12.2016.
 */
public class ExcelService {

    private static Workbook book = new HSSFWorkbook();
    private static Sheet sheet = book.createSheet("Data");




    public static void writeIntoExcelFile(Map<String, TreeMap<String, Molecule>> data, File file) {
        int i = 0;
        Row row = sheet.createRow(0);
        for (Map.Entry<String, TreeMap<String, Molecule>> task : data.entrySet()) {


            Cell name = row.createCell(i);
            name.setCellValue(task.getValue().firstEntry().getValue().getMoleculeName());
            int j = 1;
            for (Map.Entry<String, Molecule> molecule : task.getValue().entrySet()) {
                if (molecule.getValue().getTime() != 0) {
                    if(sheet.getPhysicalNumberOfRows()-1<j){
                        Row rowTime = sheet.createRow(j);
                        Cell time = rowTime.createCell(i);
                        time.setCellValue(molecule.getValue().getTime());
                    } else  {
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

    public static void writeIntoExcelFileFromJson(HashMap<String, JsonCollection> data, File file) {
        int i = 0;
        Row row = sheet.createRow(0);
        for (Map.Entry<String, JsonCollection> task : data.entrySet()) {


            Cell name = row.createCell(i);
            name.setCellValue(task.getKey());
            int j = 1;
            for (Molecule molecule:task.getValue().getMolecules()) {
                if (molecule.getTime() != 0) {
                    if(sheet.getPhysicalNumberOfRows()-1<j){
                        Row rowTime = sheet.createRow(j);
                        Cell time = rowTime.createCell(i);
                        time.setCellValue(molecule.getTime());
                    } else  {
                        Row rowTime = sheet.getRow(j);
                        Cell time = rowTime.createCell(i);
                        time.setCellValue(molecule.getTime());
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
}
