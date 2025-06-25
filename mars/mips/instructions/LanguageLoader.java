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
public class LanguageLoader{
    private static ArrayList finalInstructionList = new ArrayList<BasicInstruction>();

    public static void populate(ArrayList<BasicInstruction> instrList){
        // Populate your instructions using the format below
        new DogAssembly().addCustomInstructions(finalInstructionList);
        new CatAssembly().addCustomInstructions(finalInstructionList);

        instrList.addAll(finalInstructionList);
    }
}