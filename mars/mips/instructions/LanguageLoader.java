    package mars.mips.instructions;
    import mars.simulator.*;
    import mars.mips.hardware.*;
    import mars.mips.instructions.syscalls.*;
    import mars.*;
    import mars.util.*;
    import java.util.*;
    import java.io.*;
    import java.lang.reflect.Constructor;
    import mars.mips.instructions.MipsAssembly;
    import mars.mips.instructions.CustomAssembly;
    
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;



/**
 * Handles all user-defined instruction sets. Add CustomAssembly objects to the assemblyList and they will become accessible in the Instruction Sets dropdown.
 * @see CustomAssembly
 * @see assemblyList
 */
public class LanguageLoader{
    private static final String CUSTOM_LANG_DIRECTORY = "mars/mips/instructions/customlangs";
    private static final String CLASS_EXTENSION = "class";
    private static final String CLASS_PREFIX = "mars.mips.instructions.customlangs.";

    private static ArrayList finalInstructionList = new ArrayList<BasicInstruction>();
    public static ArrayList<CustomAssembly> assemblyList = new ArrayList<CustomAssembly>(){{
        MipsAssembly m = new MipsAssembly();
        add(m);
        ArrayList<String> langCandidates = FilenameFinder.getFilenameList(m.getClass().getClassLoader(), CUSTOM_LANG_DIRECTORY, CLASS_EXTENSION);
        
        HashSet<String> languages = new HashSet();
        for (String file : langCandidates){
            // Ensure duplicates are not loaded
            if (languages.contains(file)){
                continue;
            } else{
                languages.add(file);
            }
            // Add an instance of the class to assemblyList if it extends CustomAssembly
            try {
                String className = CLASS_PREFIX + file.substring(0, file.indexOf(CLASS_EXTENSION) - 1);
                Class langClass = Class.forName(className);
                // Do nothing if the class doesn't implement CustomAssembly
                if (!CustomAssembly.class.isAssignableFrom(langClass)){
                    continue;
                }
                Constructor<CustomAssembly> c = langClass.getConstructor();
                add(c.newInstance());
            } catch(Exception e){
                System.out.println("Error instantiating CustomAssembly from file " + file + ": "+e);
            }
        }
    }};

    /**
    * Merges all enabled custom instruction sets into the main instruction set that the simulator reads from.
    * @param instrList The global instruction list.
    */
    public static void mergeCustomInstructions(ArrayList instrList){
        boolean pseudo = false;
        finalInstructionList.clear();
        for (CustomAssembly c : assemblyList){
            if (c.enabled){
                c.addCustomInstructions(finalInstructionList);
                if (c instanceof MipsAssembly){
                    pseudo = true;
                }
            }
        }

        instrList.addAll(finalInstructionList);
        if (pseudo == true){
            Globals.instructionSet.addPseudoInstructions();
        }
    }
}