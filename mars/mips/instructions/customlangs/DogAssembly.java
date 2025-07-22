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

public class DogAssembly extends CustomAssembly{
    @Override
    public String getName(){
        return "Dog Assembly";
    }

    @Override
    public String getDescription(){
        return "Assembly for dogs.";
    }

    @Override
    protected void populate(){
        instructionList.add(
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

        instructionList.add(
                new BasicInstruction("dog",
                "Prints a dog noise to the console depending on the value of $v0",
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
                        SystemIO.printString("Bark!\n");
                        break;
                        case 2:
                        SystemIO.printString("Ruff!\n");
                        break;
                        default:
                        SystemIO.printString(getName() + ": " + getDescription() + enabled + "\n");
                    }
                }
            }));
    }
}