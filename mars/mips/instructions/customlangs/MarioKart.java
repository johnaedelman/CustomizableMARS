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


public class MarioKart extends CustomAssembly{

   // value for $v0 representing no item
   private static int item = -1;

    @Override
    public String getName(){
        return "Mario Kart";
    }

    @Override
    public String getDescription(){
        return "Simulate a fun race in Mario Kart!";
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
                     int value = operands[2] << 16 >> 16; // correct signed immediate value
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
      

       // accelerate
        instructionList.add(
                new BasicInstruction("acc $t0",
            	 "Move up three places: add 3 to $t0 and update standing",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 111111", 
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     // $t0 stores position
                     int[] operands = statement.getOperands();
                     int pos = RegisterFile.getValue(operands[0]);
                     int newPos = pos - 3;

                     if (newPos < 1) {
                        newPos = 1;
                     }

                     RegisterFile.updateRegister(operands[0], newPos);
                     SystemIO.printString("Your place in the race is: " + newPos + "\n");
                  }
            }));

         // brake
         instructionList.add(
                new BasicInstruction("brk $t0",
            	 "Slow down or stop completely: set $t0 to 12 and update standing",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 111110",
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     int[] operands = statement.getOperands();
                     int newPos = 12;
                     
                     RegisterFile.updateRegister(operands[0], newPos);
                     SystemIO.printString("Your place in the race is: " + newPos + "\n");

                  }
            }));

         // drift    
         instructionList.add(
                new BasicInstruction("drf $t0",
            	 "Move up one place: add 1 to $t0 and update standing",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 111000", 
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     // $ t0 stores position
                     int[] operands = statement.getOperands();
                     int pos = RegisterFile.getValue(operands[0]);
                     int newPos = pos - 1;

                     if (newPos < 1) {
                        newPos = 1;
                     }

                     RegisterFile.updateRegister(operands[0], newPos);
                     SystemIO.printString("Your place in the race is: " + newPos + "\n");
                  }
               }));

         // roll for item
         instructionList.add(
                new BasicInstruction("roll",
            	 "Roll for an item in the mystery box: store random result in $v0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 00000 00000 000010", 
                new SimulationCode()
               {

                  public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     // items: red (0), blue shell (1), green shell (2), banana peel (3), bullet bill (4), mushroom (5)

                     // roll for a random number
                     Random random = new Random();
                     int roll = random.nextInt(6);
                     item = roll;
                     // SystemIO.printString("Random roll: " + roll + "\n");

                     RegisterFile.updateRegister(2, roll);

                     // print item received depending on roll
                     switch (RegisterFile.getValue(2)){ // 2 = register $v0 
                        case 0:
                           SystemIO.printString("You got a red shell!\n");
                           break;
                        case 1:
                           SystemIO.printString("You got a blue shell!\n");
                           break;
                        case 2:
                           SystemIO.printString("You got a green shell!\n");
                           break;
                        case 3:
                           SystemIO.printString("You got a banana peel!\n");
                           break;
                        case 4:
                           SystemIO.printString("You got Bullet Bill!\n");
                           break;
                        case 5:
                           SystemIO.printString("You got a mushroom!\n");
                           break;
                        default:
                           SystemIO.printString("You got a coin!\n");
                    }
                }
            }));
         
         // use item
         instructionList.add(
                new BasicInstruction("use",
            	 "Use item: read item from $v0 and display",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 00000 00000 011111", 
                new SimulationCode()
               {

                  public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     if (item == -1) {
                        SystemIO.printString("No item to use!\n");
                        return;
                     }
                     else {
                     // print item received depending on roll
                     switch (RegisterFile.getValue(2)){ // 2 = register $v0 
                        case 0:
                           SystemIO.printString("You used a red shell!\n");
                           break;
                        case 1:
                           SystemIO.printString("You used a blue shell!\n");
                           break;
                        case 2:
                           SystemIO.printString("You used a green shell!\n");
                           break;
                        case 3:
                           SystemIO.printString("You used a banana peel!\n");
                           break;
                        case 4:
                           SystemIO.printString("You used bullet bill!\n");
                           break;
                        case 5:
                           SystemIO.printString("You used a mushroom!\n");
                           break;
                        default:
                        SystemIO.printString("No item to use!\n");
                        break;
                     }
                     
                     item = -1;
                     RegisterFile.updateRegister(2, -1);
                    }
                }
            }));


         } 
} 