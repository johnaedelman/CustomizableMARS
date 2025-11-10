package CustomLangJarBuilder;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BuildCustomLang{
    private final static String FOLDER_PATH = "mars/mips/instructions/customlangs/";
    private final static String OUT_PATH = "last_customlang_out";

    public static boolean findJavaFileInDir(File dir, String filename){
        for (File fileEntry : dir.listFiles()){
            if (fileEntry.getName().equalsIgnoreCase(filename + ".java")){
                return true;
            }
        }
        return false;
    }
    public static void deleteDirectory(File folder){
        for (File f : folder.listFiles()){
            if (f.isDirectory()){
                deleteDirectory(f);
            } else{
                f.delete();
            }
        }
        folder.delete();
    }
    public static void main(String[] args) throws IOException, InterruptedException{
        String langFile = "";
        if (args.length == 1){
            langFile = args[0].split("\\.")[0];
        } else{
            System.out.println("Improper input provided! Give only one argument, the name of your custom language file, i.e. \"ExampleCustomAssembly.java\".");
            System.exit(-1);
        }
        File folder = new File(FOLDER_PATH);
        
        if (!findJavaFileInDir(folder, langFile)){
            System.out.println("File not found in " + FOLDER_PATH + "! Input only the name of your custom language file, i.e. \"ExampleCustomAssembly.java\".");
            System.exit(-1);
        }
        String[] compile_command = {"javac", "-d", OUT_PATH, FOLDER_PATH + langFile + ".java"};
        String[] build_jar_command = {"jar", "cf", langFile + ".jar", "-C", OUT_PATH, "."};
        
        ProcessBuilder pb = new ProcessBuilder(compile_command);
        Process currentProcess = pb.start();
        currentProcess.waitFor();
        pb = new ProcessBuilder(build_jar_command);
        currentProcess = pb.start();
        currentProcess.waitFor();
        File outDir = new File(OUT_PATH);
        deleteDirectory(outDir);
        File jarFile = new File(langFile + ".jar");
        File jarFilePath = new File(FOLDER_PATH + langFile + ".jar");
        if (jarFilePath.isFile()){
            jarFilePath.delete();
        }
        jarFile.renameTo(new File(FOLDER_PATH + langFile + ".jar"));
        System.out.println("JAR built successfully! Open MARS LE and check it out.");
    }
}