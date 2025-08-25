    package mars.mips.instructions.customlangs;
    import mars.simulator.*;
    import mars.mips.hardware.*;
    import mars.mips.instructions.syscalls.*;
    import mars.*;
    import mars.util.*;
    import java.util.*;
    import java.io.*;
    import mars.mips.instructions.*;

/*
 * To create a custom language, you must extend the CustomAssembly abstract class and override its three methods.
 * It must also be part of the mars.mips.instructions.customlangs package.
 * 
 * The populate() method is where the magic happens - you must specify your instructions to be added here.
 * For more examples regarding the instruction format, you can view the implementation of the MIPS instructions in mars/mips/instructions/MipsAssembly.java.
 * 
 * Instructions to get your custom language into MARS:
 * Navigate to the MARS folder in your command terminal and build a JAR file from your custom assembly file.
 * Ensure that the internal folder structure is correct, or the JAR will be broken and MARS won't recognize it.
 * To do this on Windows, input the following commands after navigating to .../MARS/:
 * 
 * javac -d out mars/mips/instructions/customlangs/{NAME OF YOUR LANGUAGE}.java
 * jar cf {NAME OF YOUR LANGUAGE}.jar -C out .
 * rmdir /S /Q out
 * 
 * This will leave you with a working JAR file in the MARS directory containing your custom language. 
 * Drop it into the customlangs folder and it will appear under the Language Switcher tool.
 * 
 */
public class MIPSPlusPlus extends CustomAssembly{
    @Override
    public String getName(){
        return "MIPS Plus Plus";
    }

    @Override
    public String getDescription(){
        return "An extended version of MIPS with more functionality";
    }

    @Override
    protected void populate(){

        // absolute value
        instructionList.add(
                new BasicInstruction("abs $t0, $t1",
            	 "Take the absolute value of an integer: abs $t1 and store in $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 sssss fffff 00000 111101",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int input = RegisterFile.getValue(operands[1]);
                     int abs = Math.abs(input);

                     RegisterFile.updateRegister(operands[0], abs);
                     SystemIO.printString("The absolute value of " + input + " is " + abs);
                  } 
               }));
        
        // square
            instructionList.add(
                new BasicInstruction("sqr $t0, $t1",
            	 "Take the square of an integer: multiply $t1 by itself and store in $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 sssss fffff 00000 100001",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int input = RegisterFile.getValue(operands[1]);

                     int squared = Math.abs(input);
                     if (squared > Integer.MAX_VALUE) {
                        throw new ProcessingException(statement,"Number is too large to square!");
                     }

                     RegisterFile.updateRegister(operands[0], squared);
                     SystemIO.printString("The square of " + input + " is " + squared);
                  } 
               }));
            
        // ceiling
        instructionList.add(
                new BasicInstruction("cel $f0",
            	 "Take the ceiling of a floating point: round up to the next integer and store in $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 101111",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     double num = RegisterFile.getValue(operands[0]);
                     int ceiling = (int) Math.ceil(num);

                     RegisterFile.updateRegister(operands[0], ceiling);
                     SystemIO.printString("The ceiling of " + num + " is " + ceiling);
                  } 
               }));

        // modulo       
        instructionList.add(
                new BasicInstruction("mod $t0, $t1, $t2",
            	 "Take the modulus an integer: mod $t1 by $t2 and store result in $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 sssss ttttt fffff 00000 100001",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int result = RegisterFile.getValue(operands[0]);
                     int dividend = RegisterFile.getValue(operands[1]);
                     int modulus = RegisterFile.getValue(operands[2]);

                     // RegisterFile.updateRegister(operands[0], result);
                     // SystemIO.printString("The modulus of " + input + " is " + squared);
                  } 
               }));  
        
    }
}