import model.Molecule;
import model.Server;
import util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by max on 24.09.2016.
 */
public class ParseAlgo {
    //TODO
    public static Molecule recognize(File fileEntry) {
        try {
            boolean isStringHasStructure = false;
            Scanner scanner = new Scanner(fileEntry);
            Molecule molecule = new Molecule();
            molecule.setFileName(getFileNameWithoutFormat(fileEntry.getName()));
            Server server = new Server();
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                if (str.contains(" 16                    2.32214   3.60726   1.03062 ")) {
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
                } else if (str.matches("(.*)(Step number)( *)([0-9]*)(.*)")) {
                    Pattern pattern = Pattern.compile("(.*)(Step number)( *)([0-9]*)(.*)");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        if (Integer.parseInt(matcher.group(4)) > molecule.getStepCount()) {
                            molecule.setStepCount(Integer.parseInt(matcher.group(4)));
                        }
                    } else {
                    }
                } else if (str.matches("(.*)(time:)( *)(.*)")) {
                    Pattern pattern = Pattern.compile("(.*)(time:)( *)(.*)(.)");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.matches()) {
                        molecule.increaseTime(StringUtils.stringTimeToSeconds(matcher.group(4)));
                    } else {

                    }
                }
                else if (str.matches("(.*)(#)( )(.*)( )(opt freq)")){
                    Pattern pattern = Pattern.compile("(.*)(#)( )(.*)( )(opt freq)");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        molecule.setMethod(matcher.group(4));
                    } else {

                    }
                }
                else if (str.matches("(.*)(Charge =)( *)([0-9]*)( )(Multiplicity =)( *)([0-9]*)(.*)")) {
                    isStringHasStructure = true;
                } else if (isStringHasStructure && !str.equals("") && str.matches("( ?)([0-9]+)(.*)")) {
                    Pattern pattern = Pattern.compile("( ?)([0-9]*)(.*)");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        molecule.addAtomToStructure(StringUtils.numberToElement(matcher.group(2)));
                    } else {

                    }
                } else if (isStringHasStructure && !str.matches("( ?)(Grad*)(.*)") && !str.equals("") && str.matches("( ?)([A-Z]+)(.*)")) {
                    Pattern pattern = Pattern.compile("( ?)([A-Z]*)(.*)");
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        molecule.addAtomToStructure(matcher.group(2));
                    } else {

                    }
                } else if (isStringHasStructure && str.matches("( ?)(Grad*)(.*)")) {
                    isStringHasStructure = false;
                }

            }
            molecule.setServer(server);
            molecule.fillData();
            return molecule;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFileNameWithoutFormat(String filename) {
        return filename.substring(0, filename.indexOf("."));
    }
}
