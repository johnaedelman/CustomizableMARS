    package mars.mips.instructions.customlangs;
    import mars.mips.hardware.*;
    import mars.*;
    import mars.util.*;
    import mars.mips.instructions.*;

/*
 * To create a custom language, you must extend the CustomAssembly abstract class and override its three methods.
 * It must also be part of the mars.mips.instructions.customlangs package, and should be placed in the mars/mips/instructions/customlangs folder.
 * 
 * The populate() method is where the magic happens - you must specify your instructions to be added here.
 * For more examples regarding the instruction format, you can view the implementation of the MIPS instructions in mars/mips/instructions/MipsAssembly.java.
 * 
 * Instructions to get your custom language into MARS:
 * Navigate to the CustomizabLeMARS folder in either File Explorer or your command terminal and run the BuildCustomLangJar.bat file.
 * It will prompt you for the filename of your CustomAssembly file. If you created a file called SampleCustomLang.java, then you would
 * run the .bat file and type in "SampleCustomLang" or "SampleCustomLang.java". The file will then automatically build a JAR from your .java file
 * and place it into the /customlangs folder. You will be notified of any errors or compilation failures in the output of the .bat file.
 * 
 * You should now be able to relaunch MARS and view your custom language.
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