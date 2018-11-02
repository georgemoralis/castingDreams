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
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.MMUCR), 0);
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.CCR), 0);
        UnsignedBuffer.putUnsignedShort(regMap, getMemoryAddress(Sh4RegsConstants.BCR2), 0x3ffc);
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.WCR1), 0x77777777);
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.WCR2), 0xfffeefff);
        UnsignedBuffer.putUnsignedByte(regMap, getMemoryAddress(Sh4RegsConstants.TOCR), 0);
    }

    public void manualReset() {
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.EXPEVT), 0x20);
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.MMUCR), 0);
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.CCR), 0);
        UnsignedBuffer.putUnsignedShort(regMap, getMemoryAddress(Sh4RegsConstants.BCR2), 0);
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.WCR1), 0);
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(Sh4RegsConstants.WCR2), 0);
        UnsignedBuffer.putUnsignedByte(regMap, getMemoryAddress(Sh4RegsConstants.TOCR), 0);
    }

    public int read16(int address) {
        if (Debug.logIOREGS) {
            DCemu.logger.log(LogUtil.IOREGS, "read16  " + Sh4RegsNames.getName(address));
        }
        throw new UnsupportedOperationException("Unimplemented");
    }

    public long read32(int address) {
        if (Debug.logIOREGS) {
            DCemu.logger.log(LogUtil.IOREGS, "read32  " + Sh4RegsNames.getName(address));
        }
        return UnsignedBuffer.getUnsignedInt(regMap, getMemoryAddress(address));
    }

    public void write8(int address, int value) {
        UnsignedBuffer.putUnsignedByte(regMap, getMemoryAddress(address), value);
        if (Debug.logIOREGS) {
            DCemu.logger.log(LogUtil.IOREGS, "write8 " + Sh4RegsNames.getName(address) + " value = 0x" + Integer.toHexString((int) (value)));
        }
    }

    public void write16(int address, int value) {
        UnsignedBuffer.putUnsignedShort(regMap, getMemoryAddress(address), value);
        if (Debug.logIOREGS) {
            DCemu.logger.log(LogUtil.IOREGS, "write16 " + Sh4RegsNames.getName(address) + " value = 0x" + Integer.toHexString((int) (value)));
        }
    }

    public void write32(int address, long value) {
        UnsignedBuffer.putUnsignedInt(regMap, getMemoryAddress(address), value & 0xffffffffL);
        if (Debug.logIOREGS) {
            DCemu.logger.log(LogUtil.IOREGS, "write32 " + Sh4RegsNames.getName(address) + " value = 0x" + Long.toHexString((value & 0xffffffffL)));
        }
    }
}
