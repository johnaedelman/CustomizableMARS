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


public class McDonalds extends CustomAssembly{

    @Override
    public String getName(){
        return "McDonald's";
    }

    @Override
    public String getDescription(){
        return "Simulate a dining experience at McDonald's!";
    }

    @Override
    protected void populate(){

      SystemIO.printString("Welcome: Our standard meal contains: burger ($5), fries ($3), happy meal ($3), drink ($3)\n");
      
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
      
       // tax (random)
        instructionList.add(
                new BasicInstruction("tax $t0",
            	 "Add random tax to order: add 1-10 to $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 101010", 
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     // $t0 stores price of order
                     int[] operands = statement.getOperands();

                     Random random = new Random();
                     int tax = random.nextInt(10) + 1;

                     RegisterFile.updateRegister(operands[0], tax);
                     SystemIO.printString("Your tax for your meal is: $" + tax + "\n");
                  }
            }));

            // checkout
            instructionList.add(
                new BasicInstruction("co $t0",
            	 "Display order total with tax: print value in $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 111011", 
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     // $t0 stores price of order
                     int total = RegisterFile.getValue(8);
                     SystemIO.printString("Your total is: $" + total + "\n");
                  }
            }));


            // yell at cashier
            instructionList.add(
                new BasicInstruction("yell",
            	 "Be rude: add 4 to $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 00000 00000 110001", 
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     // $t0 stores price of order
                     int price = RegisterFile.getValue(8);
                     int extra = price + 4;
                     SystemIO.printString("You yelled at the cashier! They increased your order total to: $" + extra + "\n");
                    RegisterFile.updateRegister(8, extra);

                  }
            }));

            // open happy meal
            // list = hot wheels, action figure, barbie, keychain, stuffed animal, yoyo, ball (7)
            instructionList.add(
                new BasicInstruction("hm",
            	 "Open a Happy Meal: store random result in $v0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 00000 00000 000010", 
                new SimulationCode()
               {

                  public void simulate(ProgramStatement statement) throws ProcessingException
                  {

                     // roll for a random toy
                     Random random = new Random();
                     int roll = random.nextInt(6);
                     // SystemIO.printString("Random roll: " + roll + "\n");

                     RegisterFile.updateRegister(2, roll);

                     // print item received depending on roll
                     switch (RegisterFile.getValue(2)){ // 2 = register $v0 
                        case 0:
                           SystemIO.printString("You got hot wheels!\n");
                           break;
                        case 1:
                           SystemIO.printString("You got an action figure!\n");
                           break;
                        case 2:
                           SystemIO.printString("You got a barbie doll!\n");
                           break;
                        case 3:
                           SystemIO.printString("You got a keychain!\n");
                           break;
                        case 4:
                           SystemIO.printString("You got a stuffed animal!\n");
                           break;
                        case 5:
                           SystemIO.printString("You got a yoyo!\n");
                           break;
                        case 6:
                           SystemIO.printString("You got a ball!\n");
                           break;
                        default:
                            SystemIO.printString("Uh oh. No toy was included!\n");

                    }
                }
            }));

            // buy meal
            instructionList.add(
                new BasicInstruction("buy $t0",
            	 "Buy a meal: add 14 to $t0",
                BasicInstructionFormat.R_FORMAT,
                "000000 00000 00000 fffff 00000 000001", 
                new SimulationCode()
               {
                   public void simulate(ProgramStatement statement) throws ProcessingException
                  {
                     // $t0 stores price of order
                     int[] operands = statement.getOperands();

                     int current = RegisterFile.getValue(8);
                     int price = 14 + current;

                     RegisterFile.updateRegister(operands[0], price);
                     SystemIO.printString("You spent $14 on a meal!\n");
                  }
            }));
            
         } 
} 