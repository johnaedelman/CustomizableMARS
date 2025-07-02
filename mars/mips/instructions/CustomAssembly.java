   package mars.mips.instructions;
   import mars.simulator.*;
   import mars.mips.hardware.*;
   import mars.mips.instructions.syscalls.*;
   import mars.*;
   import mars.util.*;
   import java.util.*;
   import java.io.*;

/**
 * Abstract class defining the template for a custom instruction set which works with LanguageLoader.
 * All three abstract methods within the class must be overridden in order to create a valid CustomAssembly.
 * @see getName
 * @see getDescription
 * @see populate
 */
public abstract class CustomAssembly{
   /**
    * @return The instruction set name which will be listed on the GUI dropdown.
    */
   public abstract String getName();

   /**
    * @return The instruction set description which will be listed on the GUI dropdown.
    */
   public abstract String getDescription();

   public boolean enabled = false;
   protected ArrayList<BasicInstruction> instructionList = new ArrayList<BasicInstruction>();
   
   /**
    * Merges the custom instructions defined in populate() into a larger ArrayList, and ultimately into the global instruction list.
    * @param instrList The larger ArrayList. Should be the finalInstructionList of LanguageLoader.
    * @see populate
    * @see LanguageLoader
    */
   public void addCustomInstructions(ArrayList instrList){
      instructionList.clear();
      this.populate();
      instrList.addAll(instructionList);
   }

   /**
    * Adds all custom-defined BasicInstructions to the instructionList ArrayList.
    * @see instructionList
    */
   protected abstract void populate();
}