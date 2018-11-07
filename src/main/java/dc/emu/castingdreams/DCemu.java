package dc.emu.castingdreams;

import dc.emu.castingdreams.hw.aica.AicaRegs;
import dc.emu.castingdreams.memory.Memory;
import dc.emu.castingdreams.memory.MemoryMap;
import dc.emu.castingdreams.sh4.Sh4Int;
import dc.emu.castingdreams.sh4.Sh4Regs;
import dc.emu.castingdreams.util.Config;
import dc.emu.castingdreams.util.LogUtil;

/**
 *
 * static init class
 */
public class DCemu {

    public static LogUtil logger;
    public static Loader loader;
    public static Memory memory;
    public static Config config;
    public static Sh4Int sh4cpu;
    public static Sh4Regs sh4regs;
    public static MemoryMap memmap;
    public static AicaRegs aicaregs;

    static {
        logger = new LogUtil();
        loader = new Loader();
        memory = new Memory();
        memmap = new MemoryMap();
        config = new Config();
        sh4regs = new Sh4Regs();
        sh4cpu = new Sh4Int();
        aicaregs = new AicaRegs();
    }
}
