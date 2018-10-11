package dc.emu.castingdreams.memory;

import dc.emu.castingdreams.util.UnsignedBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author shadow
 */
public class Memory {

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
        return UnsignedBuffer.getUnsignedShort(bios, address& 0x1fffffff);
    }
}
