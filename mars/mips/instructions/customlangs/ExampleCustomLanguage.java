    package mars.mips.instructions.customlangs;
    import mars.simulator.*;
    import mars.mips.hardware.*;
    import mars.*;
    import mars.util.*;
    import mars.mips.instructions.*;
/**
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
 * @see CustomAssembly
 */
public class ExampleCustomLanguage extends CustomAssembly{
    @Override
    public String getName(){
        return "Example Custom Language";
    }

    @Override
    public String getDescription(){
        return "A language to demonstrate the basics of the CustomAssembly abstract class";
    }

    @Override
    protected void populate(){
        instructionList.add(
                new BasicInstruction("add $t1,$t2,$t3",
            	 "Addition with overflow : set $t1 to ($t2 plus $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000000 sssss ttttt fffff 00000 100000",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int add1 = RegisterFile.getValue(operands[1]);
                     int add2 = RegisterFile.getValue(operands[2]);
                     int sum = add1 + add2;
                  // overflow on A+B detected when A and B have same sign and A+B has other sign.
                     if ((add1 >= 0 && add2 >= 0 && sum < 0)
                        || (add1 < 0 && add2 < 0 && sum >= 0))
                     {
                        throw new ProcessingException(statement,
                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                     }
                     RegisterFile.updateRegister(operands[0], sum);
                  }
               }));
         instructionList.add(
                new BasicInstruction("addi $t1,$t2,-100",
            	 "Addition immediate with overflow : set $t1 to ($t2 plus signed 16-bit immediate)",
                BasicInstructionFormat.I_FORMAT,
                "001000 sssss fffff tttttttttttttttt",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int add1 = RegisterFile.getValue(operands[1]);
                     int add2 = operands[2] << 16 >> 16;
                     int sum = add1 + add2;
                  // overflow on A+B detected when A and B have same sign and A+B has other sign.
                     if ((add1 >= 0 && add2 >= 0 && sum < 0)
                        || (add1 < 0 && add2 < 0 && sum >= 0))
                     {
                        throw new ProcessingException(statement,
                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                     }
                     RegisterFile.updateRegister(operands[0], sum);
                  }
               }));
        instructionList.add(
                new BasicInstruction("jump target", 
            	 "Jump unconditionally : Jump to statement at target address",
            	 BasicInstructionFormat.J_FORMAT,
                "000010 ffffffffffffffffffffffffff",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     RegisterFile.updateRegister("$t1", operands[0]);
                     Globals.instructionSet.processJump(
                        ((RegisterFile.getProgramCounter() & 0xF0000000)
                                | (operands[0] << 2)));            
                  }
               }));
      instructionList.add(
                new BasicInstruction("print $t1, label",
            	 "example",
                BasicInstructionFormat.I_BRANCH_FORMAT,
                "110000 fffff 00000 ssssssssssssssss",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();             
                     char ch = 0;
                     // Get the name of the label from the token list
                     String label = statement.getOriginalTokenList().get(2).getValue();
                     // Look up the label in the program symbol table to get its address
                     int byteAddress = Globals.program.getLocalSymbolTable().getAddressLocalOrGlobal(label);
                     RegisterFile.updateRegister(operands[0], byteAddress);

                     try
                        {
                           ch = (char) Globals.memory.getByte(byteAddress);
                                             // won't stop until NULL byte reached!
                           while (ch != 0)
                           {
                              SystemIO.printString(new Character(ch).toString());
                              byteAddress++;
                              ch = (char) Globals.memory.getByte(byteAddress);
                           }
                        } 
                           catch (AddressErrorException e)
                           {
                              throw new ProcessingException(statement, e);
                           }
                     
                  }
                           
               }));
         instructionList.add(
                new BasicInstruction("bne $t1,$t2,label",
                "Branch if not equal : Branch to statement at label's address if $t1 and $t2 are not equal",
            	 BasicInstructionFormat.I_BRANCH_FORMAT,
                "000100 fffff sssss tttttttttttttttt",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                  
                     if (RegisterFile.getValue(operands[0])
                        != RegisterFile.getValue(operands[1]))
                     {
                        Globals.instructionSet.processBranch(operands[2]);
                     }
                  }
               }));
    }
}