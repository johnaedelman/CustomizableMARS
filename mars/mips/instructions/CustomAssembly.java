   package mars.mips.instructions;
   import mars.simulator.*;
   import mars.mips.hardware.*;
   import mars.mips.instructions.syscalls.*;
   import mars.*;
   import mars.util.*;
   import java.util.*;
   import java.io.*;

public abstract class CustomAssembly{

   public abstract String getName();
   public abstract String getDescription();
   
   protected ArrayList instructionList = new ArrayList<BasicInstruction>();

   public void addCustomInstructions(ArrayList instrList){
      this.populate();
      instrList.addAll(instructionList);
   }

   // Add custom instructions here
   protected abstract void populate();
}