   package mars.venus;
   import mars.*;
   import java.util.*;
   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.*;
   import java.io.*;
   import mars.mips.instructions.CustomAssembly;
	
/**
 * Action class for the Instruction Set dropdown to toggle instruction sets on and off.
 */
public class LanguageAction extends GuiAction{
   private CustomAssembly assembly;
   private ArrayList<CustomAssembly> assemblyList;
   private JMenu dropdown;
   private EditTabbedPane editPane;
   private VenusUI g;
   private JFrame window;
    public LanguageAction(String name, Icon icon, String descrip,
                             Integer mnemonic, KeyStroke accel, VenusUI gui, CustomAssembly asm, ArrayList<CustomAssembly> asmList, JMenu dd, EditTabbedPane ep, JFrame win) {
         super(name, icon, descrip, mnemonic, accel, gui);
         g = gui;
         assembly = asm;
         assemblyList = asmList;
         dropdown = dd;
         editPane = ep;
         window = win;
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
         // Reopens the file to fix syntax highlighting
         String currentFilename = editPane.getCurrentEditTab().getFilename();
         editPane.closeCurrentFile();
         editPane.openFile(new File(currentFilename));
         window.setVisible(false);
      }
}