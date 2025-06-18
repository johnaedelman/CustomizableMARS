   package mars.mips.instructions;
   import mars.simulator.*;
   import mars.mips.hardware.*;
   import mars.mips.instructions.syscalls.*;
   import mars.*;
   import mars.util.*;
   import java.util.*;
   import java.io.*;

public class CustomInstructions{
   private static ArrayList customInstructionList = new ArrayList<BasicInstruction>();

   public static void addCustomInstructions(ArrayList instrList){
      populate();
      instrList.addAll(customInstructionList);
   }

   // Add custom instructions here
   private static void populate(){
      customInstructionList.add(
                new BasicInstruction("pow $t1,$t2,$t3",
            	 "Integer exponentiation with overflow : set $t1 to ($t2 ^ $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000000 sssss ttttt fffff 00000 110111",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int base = RegisterFile.getValue(operands[1]);
                     int power = RegisterFile.getValue(operands[2]);
                     int result = (int)Math.pow(base, power);
                  // overflow on A+B detected when A and B have same sign and A+B has other sign.
                     if ((base >= 0 && power >= 0 && result < 0)
                        || (base < 0 && power < 0 && result >= 0))
                     {
                        throw new ProcessingException(statement,
                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                     }
                     RegisterFile.updateRegister(operands[0], result);
                  }
               }));

         customInstructionList.add(
                new BasicInstruction("noise",
            	 "Prints an animal noise to the console depending on the value of $v0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 00000 00000 111011",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     switch (RegisterFile.getValue(2)){ // register $v0
                        case 0:
                           SystemIO.printString("Woof!\n");
                           break;
                        case 1:
                           SystemIO.printString("Meow!\n");
                           break;
                        case 2:
                           SystemIO.printString("Moo!\n");
                           break;
                        default:
                           SystemIO.printString("ough\n");
                     }
                  }
               }));
   }
}