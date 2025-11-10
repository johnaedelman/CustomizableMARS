
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.*;

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

    private static void runJavaCommand(String[] cmd) throws IOException, InterruptedException{
        ProcessBuilder pb = new ProcessBuilder(cmd);
        String outputLine;
        pb.redirectErrorStream(true);
        Process currentProcess = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(currentProcess.getInputStream()));
        while ((outputLine = reader.readLine()) != null)
            System.out.println(outputLine);
        currentProcess.waitFor();
        if (currentProcess.exitValue() != 0)
            System.exit(-1);
        return;
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
        String[] ensure_mars_compliance_command = {"javac", "Mars.java"};
        String[] compile_command = {"javac", "-d", OUT_PATH, FOLDER_PATH + langFile + ".java"};
        String[] build_jar_command = {"jar", "cf", langFile + ".jar", "-C", OUT_PATH, "."};

        runJavaCommand(ensure_mars_compliance_command);
        System.out.println("[PROCESS] MARS executable is up to date.");
        runJavaCommand(compile_command);
        System.out.println("[PROCESS] " + langFile + ".java file compiled successfully.");
        runJavaCommand(build_jar_command);
        System.out.println("[PROCESS] " + langFile + ".jar file built successfully.");
        File outDir = new File(OUT_PATH);
        deleteDirectory(outDir);
        System.out.println("[PROCESS] Cleanup successful.");
        Files.move(Paths.get(langFile + ".jar"), Paths.get(FOLDER_PATH + langFile + ".jar"), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("[PROCESS] JAR file successfully moved to " + FOLDER_PATH + langFile + ".jar.");
        System.out.println("[SUCCESS] JAR built successfully! Open MARS LE and check it out.");
    }
}