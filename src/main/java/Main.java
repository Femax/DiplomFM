import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by max on 24.09.2016.
 */
public class Main {

    public static void main (String args[]){
        String pathToFolder = "c:\\dev\\Parse\\src\\main\\resources\\";
        final File folder = new File(pathToFolder);

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
            } else {
                   System.out.println(fileEntry.getName());
                try {
                    ParseAlgo.recognize(fileEntry);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
