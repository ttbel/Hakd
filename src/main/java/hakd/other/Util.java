package hakd.other;

import com.badlogic.gdx.Gdx;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.io.File;
import java.util.*;

public final class Util {
    public static final Map<String, Map<String, File>> TEXT_FILES; // this allows text to be mod friendly, because you don't have to change this class
    public static final Map<String, File> PROGRAMS; // this allows text to be mod friendly, because you don't have to change this class

    public static final File RESOURCES;

    private static final int MAX_FILE_NUMBER = 1000;

    static {
        if (new File("src/main/resources/").exists()) {
            RESOURCES = new File("src/main/resources/");
        } else {
            RESOURCES = new File("res/");
        }

        // this may take longer to load if there are a lot of files, but it is worth not having to iterate through a directory more than once
        Map<String, Map<String, File>> textFileMap = new HashMap<String, Map<String, File>>();
        File[] textFileList = new File(RESOURCES.getPath() + "/textfiles/").listFiles();
        if (textFileList != null) {
            for (File d : textFileList) {
                if (d.isDirectory()) {

                    HashMap<String, File> fileHashMap = new HashMap<String, File>();

                    File[] files = d.listFiles();
                    if (files != null) {
                        for (File f : files) {
                            if (!f.isDirectory()) {
                                fileHashMap.put(f.getName(), new File(f.getPath()));
                            }
                        }
                    }

                    textFileMap.put(d.getName(), fileHashMap);
                }
            }
        }
        TEXT_FILES = Collections.unmodifiableMap(textFileMap);

        Map<String, File> programs = new HashMap<String, File>();
        for (File f : listFiles(new File(RESOURCES.getPath() + "/python/programs/"))) {
            if (!f.isDirectory()) {
                programs.put(f.getName(), f);
            }
        }

        PROGRAMS = Collections.unmodifiableMap(programs);
    }


    /**
     * Converts float x and y orthogonal screen coordinates into int x and y
     * isometric.
     */
    public static int[] orthoToIso(float x, float y, int height) {
        // map coordinates
        float a1 = 0.5f * x + y; // checks every frame, so use float not double
        float a2 = -0.5f * x + y;
        float b = (height) / 2 + 0.6f;

        // I have a page full of math written out for this algorithm, front and
        // back, although it is just a system of equations
        float firstIntersectX;
        float firstIntersectY;

        float secondIntersectX;
        float secondIntersectY; // TODO this is a little off, but it works for
        // now

        firstIntersectY = .5f * x + .32f; // I could explain all of these
        // numbers, or you could just trust
        // they work
        firstIntersectX = 2 * (a1 - firstIntersectY);

        secondIntersectY = -y + a2 + b;
        secondIntersectX = 2 * (secondIntersectY - a2);

        int[] iso = new int[2];
        iso[0] = (int) (0.9f * Math.sqrt((firstIntersectX - x) * (firstIntersectX - x) + (firstIntersectY - y) * (firstIntersectY - y)));
        iso[1] = (int) (0.9f * Math.sqrt((secondIntersectX - x) * (secondIntersectX - x) + (secondIntersectY - y) * (secondIntersectY - y)));

        return iso;
    }

    /**
     * Returns float x and y orthogonal screen coordinates at the bottom middle
     * of the isometric tile coordinate.
     */
    public static float[] isoToOrtho(float iX, float iY, float roomHeight) {
        float[] ortho = new float[2];
        ortho[0] = (roomHeight + iX - iY) / 2 - 0.5f;
        ortho[1] = (roomHeight - iX - iY) / 4 - 0.25f;
        return ortho; // adapted from
        // http://www.java-gaming.org/topics/isometric-screen-space/27698/view.html
    }

    public static String ganerateName() {
        String[] names = new String[]{"Adara", "Adena", "Adrianne", "Alarice", "Alvita", "Amara", "Ambika", "Antonia", "Araceli", "Balandria", "Basha",
                "Beryl", "Bryn", "Callia", "Caryssa", "Cassandra", "Casondrah", "Chatha", "Ciara", "Cynara", "Cytheria", "Dabria", "Darcei",
                "Deandra", "Deirdre", "Delores", "Desdomna", "Devi", "Dominique", "Drucilla", "Duvessa", "Ebony", "Fantine", "Fuscienne",
                "Gabi", "Gallia", "Hanna", "Hedda", "Jerica", "Jetta", "Joby", "Kacila", "Kagami", "Kala", "Kallie", "Keelia", "Kerry",
                "Kerry-Ann", "Kimberly", "Killian", "Kory", "Lilith", "Lucretia", "Lysha", "Mercedes", "Mia", "Maura", "Perdita", "Quella",
                "Riona", "Ryan", "Safiya", "Salina", "Severin", "Sidonia", "Sirena", "Solita", "Tempest", "Thea", "Treva", "Trista", "Vala", "Winta"};

        PythonInterpreter pi = new PythonInterpreter();
        pi.set("PLACES", names);
        pi.execfile(RESOURCES.getPath() + "/python/NameGenerator.py");
        return pi.get("name").toString();
    }

    public static String ganerateCityName() {
        String[] names = new String[]{"Bangkok", "Beijing", "Buenos Aires", "Cairo", "Delhi", "Dhaka", "Guangzhou", "Hong Kong", "Istanbul", "Jakarta",
                "Karachi", "Kinshasa", "Kolkata", "Lagos", "Lima", "London", "Los Angeles", "Manila", "Mexico City", "Moscow", "Mumbai",
                "New York City", "Osaka", "Rio de Janeiro", "São Paulo", "Seoul", "Shanghai", "Tehran", "Tianjin", "Tokyo"
        };

        PythonInterpreter pi = new PythonInterpreter();
        pi.set("PLACES", names);
        pi.execfile(RESOURCES.getPath() + "/python/NameGenerator.py");
        String name = pi.get("name").toString();
        System.out.println(name);
        return name;
    }

    public static String ganerateCountryName() {
        String[] names = new String[]{};

        PythonInterpreter pi = new PythonInterpreter();
        pi.set("PLACES", names);
        pi.execfile(RESOURCES.getPath() + "/python/NameGenerator.py");
        return pi.get("name").toString();
    }

    public static hakd.other.File getFileData(String directory, String fileName) throws FileNotFoundException {
        String name = "";
        String data = "";
        BufferedReader reader;
        File file = TEXT_FILES.get(directory).get(fileName);

        if (file == null) {
            throw new FileNotFoundException();
        }

        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String s;
        while (true) {
            try {
                s = reader.readLine();
            } catch (IOException e) {
                Gdx.app.error("IO exception", "", e);
                break;
            }

            if (s == null) {
                break;
            } else if (s.startsWith("@name:")) {
                name = s.substring(6);
                int index = name.lastIndexOf('.');
                name = name.substring(0, index) + "_" + (int) (Math.random() * MAX_FILE_NUMBER) + name.substring(index);
            }
            data += s + "\n";
        }

        return new hakd.other.File(name, data);
    }


    public static String getProgramData(String name) {
        List<File> fileList = listFiles(new File(RESOURCES.getPath() + "/python/programs/"));
        File file = null;

        for (File f : fileList) {
            if (f.getName().equals(name)) {
                file = f;
                break;
            }
        }

        if (file == null) {
            return "";
        }

        String data = "";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }

        try {
            while (true) {
                String s = reader.readLine();
                if (s == null) {
                    break;
                }
                data += s + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Recursively list all of the (java.io)files in a directory.
     *
     * @param file is the starting directory
     * @return the list of fies in that directory
     */
    public static List<File> listFiles(File file) {
        List<File> files = new ArrayList<File>();
        File[] fileArray = file.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    files.addAll(listFiles(f));
                } else {
                    files.add(f);
                }
            }
        }

        return files;
    }

    public static String tutorialText(int pos) {
        String text = "";

        String data = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(Util.class.getResourceAsStream("/resources/tutorial" + pos + ".txt")));

        try {
            while (true) {
                String s = reader.readLine();
                if (s == null) {
                    break;
                }
                text += s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}
