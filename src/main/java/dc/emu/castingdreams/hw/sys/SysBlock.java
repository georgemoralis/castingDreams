package dc.emu.castingdreams.hw.sys;

import dc.emu.castingdreams.DCemu;
import dc.emu.castingdreams.Debug;
import static dc.emu.castingdreams.hw.sys.SysBlockConstants.SB_ISTNRM;
import dc.emu.castingdreams.util.LogUtil;
import dc.emu.castingdreams.util.UnsignedBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author shadow
 */
public class SysBlock {

    public ByteBuffer sysRegs;

    public SysBlock() {
        sysRegs = ByteBuffer.allocate(0x600000);//todo reduce more
        sysRegs.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static final int getMemoryAddress(int address) {
        return address & 0x00ffffff;
    }

    public long read32(int address) {
        if (Debug.logSys) {
            DCemu.logger.log(LogUtil.AICAREGS, "read32  0x" + Integer.toHexString(address));
        }
        switch (getMemoryAddress(address)) {
            case SB_ISTNRM:
                return UnsignedBuffer.getUnsignedInt(sysRegs, getMemoryAddress(SB_ISTNRM));
        }
        throw new UnsupportedOperationException("sys read32");
    }

    public void write32(int address, long value) {
        if (Debug.logSys) {
            DCemu.logger.log(LogUtil.SYS, "write  0x" + Integer.toHexString(address) + " 0x" + Long.toHexString(value & 0xffffffffL) + " 0x" + Integer.toHexString(getMemoryAddress(address)));
        }
        switch (getMemoryAddress(address)) {
            case SB_ISTNRM://clear interrupts
                UnsignedBuffer.putUnsignedInt(sysRegs, UnsignedBuffer.getUnsignedInt(sysRegs, getMemoryAddress(SB_ISTNRM)) & ~value);
                return;
        }
        throw new UnsupportedOperationException("sys write32");
    }
}
