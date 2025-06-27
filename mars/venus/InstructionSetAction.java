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
    public InstructionSetAction(String name, Icon icon, String descrip,
                             Integer mnemonic, KeyStroke accel, VenusUI gui, CustomAssembly asm) {
         super(name, icon, descrip, mnemonic, accel, gui);
         assembly = asm;
      }
   		 
       public void actionPerformed(ActionEvent e) {
		   boolean visibility = ((JCheckBoxMenuItem) e.getSource()).isSelected();
         assembly.enabled = visibility; // Enables the instruction set for the .populate() call based on whether the box is checked or unchecked
         Globals.instructionSet.populate();
      }
}