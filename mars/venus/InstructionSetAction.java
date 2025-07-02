   package mars.venus;
   import mars.simulator.*;
import mars.venus.GuiAction;
import mars.venus.VenusUI;
import mars.*;
   import java.util.*;
   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.*;
   import java.io.*;
   import mars.mips.instructions.CustomAssembly;
   import mars.mips.instructions.InstructionSet;
   import mars.venus.editors.jeditsyntax.tokenmarker.MIPSTokenMarker;
	
/**
 * Action class for the Instruction Set dropdown to toggle instruction sets on and off.
 */
public class InstructionSetAction extends GuiAction{
   private CustomAssembly assembly;
   private ArrayList<CustomAssembly> assemblyList;
   private JMenu dropdown;
    public InstructionSetAction(String name, Icon icon, String descrip,
                             Integer mnemonic, KeyStroke accel, VenusUI gui, CustomAssembly asm, ArrayList<CustomAssembly> asmList, JMenu dd) {
         super(name, icon, descrip, mnemonic, accel, gui);
         assembly = asm;
         assemblyList = asmList;
         dropdown = dd;
      }
   		 
       public void actionPerformed(ActionEvent e) {
		   for (CustomAssembly c : assemblyList){
            if (c.enabled == true){
               c.enabled = false;
            }
         }
         for (Component c : dropdown.getMenuComponents()){
            ((JMenuItem) c).setBackground(Color.WHITE);
         }
         ((JMenuItem) e.getSource()).setBackground(new Color(200, 221, 242));
         assembly.enabled = true; // Enables the instruction set for the .populate() call
         Globals.instructionSet.populate();
      }
}