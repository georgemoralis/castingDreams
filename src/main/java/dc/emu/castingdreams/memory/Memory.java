package dc.emu.castingdreams.memory;

import dc.emu.castingdreams.DCemu;
import dc.emu.castingdreams.util.UnsignedBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author shadow
 */
public class Memory {
    /**
     * memory class atm is a big hack need a lot more work
     */
    public ByteBuffer ram;
    public ByteBuffer bios;

    public Memory() {
        ram = ByteBuffer.allocate(MemoryConstants.ramSize);
        ram.order(ByteOrder.LITTLE_ENDIAN);

        bios = ByteBuffer.allocate(MemoryConstants.biosSize);
        bios.order(ByteOrder.LITTLE_ENDIAN);
    }

    public int read16(int address) {
        //we have only loaded bios so return only from there atm
        return UnsignedBuffer.getUnsignedShort(bios, address & 0x1fffffff);
    }

    public long read32(int address) {
        if(address >= 0xe0000000 && address<= 0xffffffff)//map SH4 memory mapped registers
        {
            return DCemu.sh4regs.read32(address);
        }
        //we have only loaded bios so return only from there atm
        return UnsignedBuffer.getUnsignedInt(bios, address & 0x1fffffff);
    }
}
