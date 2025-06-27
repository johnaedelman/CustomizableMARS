    package mars.mips.instructions;
    import mars.simulator.*;
    import mars.mips.hardware.*;
    import mars.mips.instructions.syscalls.*;
    import mars.*;
    import mars.util.*;
    import java.util.*;
    import java.io.*;
    import mars.mips.instructions.CustomAssembly;

public class MipsAssembly extends CustomAssembly{
    @Override
    public String getName(){
        return "MIPS Assembly";
    }

    @Override
    public String getDescription(){
        return "The basic MIPS instruction set.";
    }

    @Override
    protected void populate(){
        {
        }
}
}