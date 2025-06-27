    package mars.mips.instructions;
    import mars.simulator.*;
    import mars.mips.hardware.*;
    import mars.mips.instructions.syscalls.*;
    import mars.*;
    import mars.util.*;
    import java.util.*;
    import java.io.*;
    import mars.mips.instructions.DogAssembly;
    import mars.mips.instructions.CatAssembly;

/**
 * Handles all user-defined instruction sets. Add CustomAssembly objects to the assemblyList and they will become accessible in the Instruction Sets dropdown.
 * @see CustomAssembly
 * @see assemblyList
 */
public class LanguageLoader{
    private static ArrayList finalInstructionList = new ArrayList<BasicInstruction>();
    public static ArrayList<CustomAssembly> assemblyList = new ArrayList<CustomAssembly>(){{
        // Add your instruction sets here
        add(new DogAssembly());
        add(new CatAssembly());
    }};

    /**
    * Merges all enabled custom instruction sets into the main instruction set that the simulator reads from.
    * @param instrList The global instruction list.
    */
    public static void mergeCustomInstructions(ArrayList<BasicInstruction> instrList){
        finalInstructionList.clear();
        for (CustomAssembly c : assemblyList){
            if (c.enabled){
                c.addCustomInstructions(finalInstructionList);
            }
        }

        instrList.addAll(finalInstructionList);
    }
}