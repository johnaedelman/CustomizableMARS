    package mars.mips.instructions.customlangs;
    import mars.mips.hardware.*;
    import mars.*;
    import mars.util.*;
    import mars.mips.instructions.*;

public class DragonBallAssembly extends CustomAssembly{
    @Override
    public String getName(){
        return "Dragon Ball Assembly";
    }

    @Override
    public String getDescription(){
        return "Assembly language to let your computer control Ki";
    }

    @Override
    protected void populate(){
        instructionList.add(
                new BasicInstruction("pow $t1,-100",
            	 "Power up : Change the level of ($t1) by an immediate value",
                BasicInstructionFormat.I_FORMAT,
                "001000 fffff 00000 ssssssssssssssss",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int add1 = RegisterFile.getValue(operands[0]);
                     int add2 = operands[1] << 16 >> 16;
                     int sum = add1 + add2;
                     RegisterFile.updateRegister(operands[0], sum);
                  }
               }));
        instructionList.add(
                new BasicInstruction("kai $t1,$t2",
            	 "Kaioken : Apply a multiplier of ($t2) onto ($t1)",
                BasicInstructionFormat.R_FORMAT,
                "000000 fffff sssss 00000 00000 100000",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int base = RegisterFile.getValue(operands[0]);
                     int multiplier = RegisterFile.getValue(operands[1]);
                     int result = base * multiplier;
                     RegisterFile.updateRegister(operands[0], result);
                  }
               }));
        instructionList.add(
                new BasicInstruction("it $t1, label",
                "Instant Transmission : Jump to statement at label's address if ($t1) is high enough to be sensed (5 or higher)",
            	 BasicInstructionFormat.I_BRANCH_FORMAT,
                "000100 fffff 00000 ssssssssssssssss",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                  
                     if (RegisterFile.getValue(operands[0]) >= 5)
                     {
                        Globals.instructionSet.processBranch(operands[1]);
                     }
                  }
               }));
        instructionList.add(
                new BasicInstruction("tp label",
            	 "Telepathy : Mentally transmit a message string stored at the label",
                BasicInstructionFormat.I_BRANCH_FORMAT,
                "110000 00000 00000 ffffffffffffffff",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {        
                     char ch = 0;
                     // Get the name of the label from the token list
                     String label = statement.getOriginalTokenList().get(1).getValue();
                     // Look up the label in the program symbol table to get its address
                     int byteAddress = Globals.program.getLocalSymbolTable().getAddressLocalOrGlobal(label);

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
                new BasicInstruction("ssj $t1",
                "Super Saiyan : Boost the level of ($t1) by a factor of 50",
            	 BasicInstructionFormat.R_FORMAT,
                "000000 fffff 00000 00000 00000 010011",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int operand = RegisterFile.getValue(operands[0]);
                     RegisterFile.updateRegister(operands[0], operand * 50);
                  }
               }));
        
        instructionList.add(
                new BasicInstruction("sb $t1",
                "Spirit Bomb : Take a bit of energy from every other $t register to increase the power of ($t1)",
            	 BasicInstructionFormat.R_FORMAT,
                "000100 fffff 00000 ssssssssssssssss",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int targetReg = operands[0];
                     int newVal = RegisterFile.getValue(targetReg);
                     // Iterate through every $t register
                     for (int i = 8; i < 16; i++){
                        if (i == targetReg)
                            continue;
                        int val = RegisterFile.getValue(i);
                        if (val >= 10){
                            newVal += 10;
                            RegisterFile.updateRegister(i, val - 10);
                        } else if (val > 0){
                            newVal += val;
                            RegisterFile.updateRegister(i, 0);
                        }
                     }
                     RegisterFile.updateRegister(targetReg, newVal);
                  }
               }));
    }
}