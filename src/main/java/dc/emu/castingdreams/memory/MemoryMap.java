package dc.emu.castingdreams.memory;

import dc.emu.castingdreams.util.UnsignedBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author shadow
 */
public class MemoryMap {

    public ByteBuffer ram;
    public ByteBuffer bios;

    public MemoryMap() {
        ram = ByteBuffer.allocate(MemoryConstants.ramSize);
        ram.order(ByteOrder.LITTLE_ENDIAN);

        bios = ByteBuffer.allocate(MemoryConstants.biosSize);
        bios.order(ByteOrder.LITTLE_ENDIAN);
    }

    public int mem_read16(int address) {
        System.out.println("read16 " + Integer.toHexString(address));
        return UnsignedBuffer.getUnsignedShort(ram, address & 0x00ffffff);
    }

    public long mem_read32(int address) {
        System.out.println("read32 " + Integer.toHexString(address));
        return UnsignedBuffer.getUnsignedInt(ram, address & 0x00ffffff);
    }

    public void mem_write8(int address, int value) {
        UnsignedBuffer.putUnsignedByte(ram, address & 0x00ffffff, value);
        System.out.println("write8 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
    }

    public void mem_write16(int address, int value) {
        UnsignedBuffer.putUnsignedShort(ram, address & 0x00ffffff, value);
        System.out.println("write16 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
    }

    public void mem_write32(int address, int value) {
        UnsignedBuffer.putUnsignedInt(ram, address & 0x00ffffff, value);
        System.out.println("write32 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
    }
}
