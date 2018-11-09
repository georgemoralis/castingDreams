package dc.emu.castingdreams.hw.aica;

import dc.emu.castingdreams.DCemu;
import dc.emu.castingdreams.Debug;
import static dc.emu.castingdreams.hw.aica.AicaRegsConstants.AVCTL;
import dc.emu.castingdreams.util.LogUtil;
import dc.emu.castingdreams.util.UnsignedBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author shadow
 */
public class AicaRegs {
    public ByteBuffer aicaMap;

    public AicaRegs() {
        aicaMap = ByteBuffer.allocate(0x8000000);//todo reduce size
        aicaMap.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static final int getMemoryAddress(int address) {
        return address & 0x00ffffff;
    }
    public long read32(int address) {
        if (Debug.logAICAREGS) {
            DCemu.logger.log(LogUtil.AICAREGS, "read32  0x" + Integer.toHexString(address));
        }
        switch(address)
        {
            case AVCTL://has to do with type of video cable
             return UnsignedBuffer.getUnsignedInt(aicaMap, getMemoryAddress(address)) | 0x00000000; //vga cable
        }
        throw new UnsupportedOperationException("aica read32");
    }
    public void write32(int address, long value) {
        if (Debug.logAICAREGS) {
            DCemu.logger.log(LogUtil.AICAREGS, "write  0x" + Integer.toHexString(address)+ " 0x" + Long.toHexString(value & 0xffffffffL));
        }
        UnsignedBuffer.putUnsignedInt(aicaMap, value);
    }
}
