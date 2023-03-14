package utilities;

import java.io.File;
import java.util.Vector;

public class Package {
    //
    Vector<String> allPackage = new Vector<String>();

    public Vector<String> getAllPackageIn(String folder, String racine) {
        //
        File directoryPath = null;
        Vector<String> result = new Vector<String>();
        if (!folder.equals("./")) {
            folder = folder.replace(".", "/");
        }

        boolean same = folder.equals(racine);
        if (racine == null || same) {
            directoryPath = new File(folder);
        } else {
            directoryPath = new File(racine);
        }
        //
        String[] folders = directoryPath.list();
        //
        if (folders != null) {
            for (int i = 0; i < folders.length; i++) {
                if (racine == null) {
                    if (getFolder(folders[i])) {
                        this.allPackage.add(replaceToPackage(folders[i]));
                        getAllPackageIn(folders[i], folders[i]);
                    }
                } else {
                    if (same) {
                        if (getFolder(racine + "/" + folders[i])) {
                            this.allPackage.add(replaceToPackage(racine + "." + folders[i]));
                            getAllPackageIn(folders[i], racine + "/" + folders[i]);
                        }
                    } else {
                        if (getFolder(racine + "/" + folders[i])) {
                            this.allPackage.add(replaceToPackage(racine + "." + folders[i]));
                            getAllPackageIn(folders[i], folder);
                        }
                    }
                }

            }
        }
        return result;
    }

    public String replaceToPackage(String text) {
        String result = "";
        result = text.replace("/", ".");
        return result;
    }

    public void showPackage() {
        for (String pack : this.allPackage) {
            System.out.println(pack);
        }
    }

    public static boolean getFolder(String folder) {
        File file = new File(folder);
        if (file.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    public Vector<String> getAllPackage() {
        return allPackage;
    }

    public void setAllPackage(Vector<String> allPackage) {
        this.allPackage = allPackage;
    }

}
