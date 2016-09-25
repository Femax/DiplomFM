import model.Molecule;
import model.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by max on 24.09.2016.
 */
public class ParseAlgo {
    public static void recognize(File fileEntry) {
        try {
            Scanner scanner = new Scanner(fileEntry);
            Molecule molecule = new Molecule();
            molecule.setMoleculeName(fileEntry.getName());
            Server server = new Server();
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                if (str.matches("^[1-9]")) continue;
                if (str.contains("steps")) {
                    System.out.println(str);
                }
                if (str.contains("%mem")) {
                    Pattern pattern = Pattern.compile(" (%mem=)([0-9])([0-9])([0-9])(MW)");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.matches()) {
                        server.setMemory(Integer.parseInt(matcher.group(2) + matcher.group(3) + matcher.group(4)));
                    } else {
                    }
                } else if (str.matches(" (%nproc=)([1-9)])")) {
                    Pattern pattern = Pattern.compile(" (%nproc=)([1-9)])");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.matches()) {
                        server.setProcCount(Byte.parseByte(matcher.group(2)));
                    } else {
                    }
                } else if (molecule.getStepCount()==0&&str.matches("(.*)(Number of steps in this run=)( *)([0-9]*)(.*)")) {
                    Pattern pattern = Pattern.compile("(.*)(Number of steps in this run=)( *)([0-9]*)(.*)");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.matches()) {
                        molecule.setStepCount(Integer.parseInt(matcher.group(4)));
                    } else {
                    }
                } else if (str.matches("(.*)(time:)( *)(.*)")) {
                    Pattern pattern = Pattern.compile("(.*)(time:)( *)(.*)(.)");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.matches()) {
                        molecule.setTime(matcher.group(4));
                    } else {
                    }
                }

            }
            molecule.setServer(server);
            molecule.calculateStepTime();
            System.out.println(molecule);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
