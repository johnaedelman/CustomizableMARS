    package mars.mips.instructions.customlangs;
    import mars.simulator.*;
    import mars.mips.hardware.*;
    import mars.mips.instructions.syscalls.*;
    import mars.*;
    import mars.util.*;
    import java.util.*;
    import java.io.*;
    import mars.mips.instructions.*;
    import java.util.Random;


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

        // put (same as addi)
      instructionList.add(
                new BasicInstruction("put $t0,$t1,12",
            	 "Assign value to register: set $t0 to ($t1 plus signed 16-bit immediate)",
                BasicInstructionFormat.I_FORMAT,
                "111111 sssss fffff tttttttttttttttt",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int placeholder = RegisterFile.getValue(operands[1]);
                     int value = operands[2] << 16 >> 16;
                     int result = placeholder + value;
                  // overflow on A+B detected when A and B have same sign and A+B has other sign.
                     if ((placeholder >= 0 && value >= 0 && result < 0)
                        || (placeholder < 0 && value < 0 && result >= 0))
                     {
                        throw new ProcessingException(statement,
                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                     }
                     RegisterFile.updateRegister(operands[0], result);
                     
                  }
               }));

        
        // square
            instructionList.add(
                new BasicInstruction("sqr $t0",
            	 "Take the square of an integer: multiply $t0 by itself and store result",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 100001",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                        int[] operands = statement.getOperands();
                        int input = RegisterFile.getValue(operands[0]);

                        int squared = input * input;
                        SystemIO.printString("The square of " + input + " is: " + squared + "\n");
                        RegisterFile.updateRegister(operands[0], squared);
                  } 
               }));

        // modulo     
        instructionList.add(
                new BasicInstruction("mod $t0, $t1, $t2",
            	 "Take the modulo an integer: mod $t1 by $t2 and store result in $t0",
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
                     
                     result = dividend % modulus;
                     SystemIO.printString(dividend + " % " + modulus + " is " + result + "\n");
                     RegisterFile.updateRegister(operands[0], result);
                  } 
               }));  
         
            
         // min
         instructionList.add(
                new BasicInstruction("min $t0, $t1, $t2",
            	 "Take the minimum: compare $t1 with $t2 and store smaller value in $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 sssss ttttt fffff 00000 100111",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int min = RegisterFile.getValue(operands[0]);
                     int num1 = RegisterFile.getValue(operands[1]); 
                     int num2 = RegisterFile.getValue(operands[2]); 
                     
                     min = Math.min(num1, num2);
                     SystemIO.printString("The minimum of " + num1 + " and " + num2 + " is " + min + "\n");
                     RegisterFile.updateRegister(operands[0], min);
                  } 
               })); 

         // random
         instructionList.add(
                new BasicInstruction("rand $t0, $t1, $t2",
            	 "Generate a random number: lower bound is $t1, upper bound is $t2, store result in $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 ttttt sssss fffff 00000 101001",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();

                     int result = RegisterFile.getValue(operands[0]);
                     int lower = RegisterFile.getValue(operands[1]);
                     int upper = RegisterFile.getValue(operands[2]);

                     Random random = new Random();
                     int roll = random.nextInt(upper - (lower + 1)) + lower;
                     RegisterFile.updateRegister(operands[0], result);

                     SystemIO.printString("Generating random number...");
                     SystemIO.printString(" " + roll + "\n");
                  }
               }));

          // summation 
         instructionList.add(
                new BasicInstruction("sum $t0",
            	 "Perform summation: add from 1 to $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 101101", 
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int bound = RegisterFile.getValue(operands[0]);
                     int sum = 0;

                     for (int i = 1; i <= bound; i++) {
                        sum += i;
                     }

                     RegisterFile.updateRegister(operands[0], sum);
                     SystemIO.printString("The sum from 1 to " + bound + " is " + sum + "\n");
                  }
               }));
    }
}