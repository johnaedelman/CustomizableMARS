    package mars.mips.instructions.customlangs;
    import mars.simulator.*;
    import mars.mips.hardware.*;
    import mars.mips.instructions.syscalls.*;
    import mars.*;
    import mars.util.*;
    import java.util.*;
    import java.io.*;
    import mars.mips.instructions.*;

    import mars.mips.instructions.CustomAssembly;
    import java.lang.Math;

public class CatAssembly extends CustomAssembly{
    @Override
    public String getName(){
        return "Cat Assembly";
    }
    
    @Override
    public String getDescription(){
        return "Assembly for cats.";
    }
    @Override
    protected void populate(){
        instructionList.add(
                new BasicInstruction("cat",
                "Prints a cat noise to the console depending on the value of $v0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 00000 00000 101111",
                new SimulationCode()
            {
                public void simulate(ProgramStatement statement) throws ProcessingException
                {
                    switch (RegisterFile.getValue(2)){ // register $v0
                        case 0:
                        SystemIO.printString("Meow!\n");
                        break;
                        case 1:
                        SystemIO.printString("Purr...\n");
                        break;
                        default:
                        SystemIO.printString(getName() + ": " + getDescription() + "\n");
                    }
                }
            }));
    }
}