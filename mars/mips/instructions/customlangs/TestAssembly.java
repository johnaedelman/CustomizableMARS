package mars.mips.instructions.customlangs;

import mars.mips.hardware.*;

import mars.*;

import mars.mips.instructions.*;

public class TestAssembly extends CustomAssembly{

@Override

public String getName(){

return "Test Assembly";

}

@Override

public String getDescription(){

return "An example to demonstrate the basics of creating a custom language.";

}
@Override

protected void populate(){

instructionList.add(

new BasicInstruction("add $t1,$t2,$t3",

"This is the description of my add instruction!",

BasicInstructionFormat.R_FORMAT,

"000000 sssss ttttt fffff 00000 100000",

new SimulationCode(){

public void simulate(ProgramStatement statement) throws ProcessingException{

int[] operands = statement.getOperands();

int add1 = RegisterFile.getValue(operands[1]);

int add2 = RegisterFile.getValue(operands[2]);

int sum = add1 + add2;

RegisterFile.updateRegister(operands[0], sum);

}

})

);

}
}