package dc.emu.castingdreams.sh4;

import dc.emu.castingdreams.DCemu;
import dc.emu.castingdreams.Debug;
import dc.emu.castingdreams.util.LogUtil;
import dc.emu.castingdreams.util.UnsignedBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author shadow
 */
/**
 *
 * Memory Mapped I/O hardware regs
 */
public class Sh4Regs {

    private static final int regMapsize = 16 * 1024 * 1024;
    public ByteBuffer regMap;

    public Sh4Regs() {
        regMap = ByteBuffer.allocate(regMapsize);
        regMap.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static final int getMemoryAddress(int address) {
        return address & 0x00ffffff;
    }

    public void hardReset() {
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.EXPEVT), 0);
    }

    public void manualReset() {
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.EXPEVT), 0x20);
    }

    public long read32(int address) {
        if (Debug.logIOREGS) {
            DCemu.logger.log(LogUtil.IOREGS, "read32  " + Sh4RegsNames.getName(address));
        }
        return UnsignedBuffer.getUnsignedInt(regMap, getMemoryAddress(address));
    }
}
