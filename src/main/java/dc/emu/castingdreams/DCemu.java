package dc.emu.castingdreams;

import dc.emu.castingdreams.memory.Memory;
import dc.emu.castingdreams.util.LogUtil;

/**
 *
 * static init class
 */
public class DCemu {

    public static LogUtil logger;
    public static Loader  loader;
    public static Memory  memory;

    static {
        logger = new LogUtil();
        loader = new Loader();
        memory = new Memory();
    }
}
