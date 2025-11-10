   package mars.tools;
   import mars.*;
   import mars.venus.*;
   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.*;
   import mars.mips.instructions.*;

public class LanguageSwitcher implements MarsTool{
    public String getName(){
        return "Language Switcher";
    }
    
    public void action(){
        JFrame frame = new JFrame("Language Switcher");
        JMenu menu = new JMenu("Select Language");
        JMenuBar test = new JMenuBar();
        for (CustomAssembly c : LanguageLoader.assemblyList){
              JMenuItem assemblyAction = new JMenuItem(new LanguageAction(c.getName(),
                                            null,
               									  c.getDescription(),
               									  null,null,
               									  Globals.getGui(), c, LanguageLoader.assemblyList, menu, (EditTabbedPane) Globals.getGui().getMainPane().getEditTabbedPane(), frame));
            if (c.enabled){
               assemblyAction.setBackground(new Color(200, 221, 242));
            }
            menu.add(assemblyAction);
         }
        JPanel buttonPanel = new JPanel();
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                    frame.setVisible(false);
                    
                    }
                
                });
        buttonPanel.add(closeButton);
        test.add(menu);
        frame.setJMenuBar(test);
        frame.pack();
        frame.setVisible(true);
        frame.setTitle(" Language Switcher");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // changed 12/12/09 DPS (was EXIT)
        frame.setVisible(true); // show();
    }
}