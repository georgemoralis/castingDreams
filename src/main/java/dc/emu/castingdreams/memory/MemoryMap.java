package dc.emu.castingdreams.memory;

import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_RAM_FIRST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_RAM_LAST;
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
    public int mem_read8(int address) {
        throw new UnsupportedOperationException("mem_read8");
    }
    public int mem_read16(int address) {
        System.out.println("read16 " + Integer.toHexString(address));       
        int addr= address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            return UnsignedBuffer.getUnsignedShort(ram, address & 0x00ffffff);
        }
        throw new UnsupportedOperationException("mem_read16");
    }

    public long mem_read32(int address) {
        System.out.println("read32 " + Integer.toHexString(address));
        int addr= address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            return UnsignedBuffer.getUnsignedInt(ram, address & 0x00ffffff);
        }
        System.out.println(Integer.toHexString(address));
        throw new UnsupportedOperationException("mem_read32");
    }

    public void mem_write8(int address, int value) {
        System.out.println("write8 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
        int addr= address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            UnsignedBuffer.putUnsignedByte(ram, address & 0x00ffffff, value);
            return;
        }
        System.out.println("UNSSSSSS write32 " + Integer.toHexString(addr) + " " + Integer.toHexString(address) + " " + Integer.toHexString(value));
    }

    public void mem_write16(int address, int value) {
        
        System.out.println("write16 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
        int addr= address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            UnsignedBuffer.putUnsignedShort(ram, address & 0x00ffffff, value);
            return;
        }
        System.out.println("UNSSSSSS write16 " + Integer.toHexString(addr) + " " + Integer.toHexString(address) + " " + Integer.toHexString(value));
    }

    public void mem_write32(int address, long value) {
        System.out.println("write32 " + Integer.toHexString(address) + " " + Long.toHexString(value& 0xffffffffL));
        int addr= address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            UnsignedBuffer.putUnsignedInt(ram, address & 0x00ffffff, value);
            return;
        }
        System.out.println("UNSSSSSS write32 " + Integer.toHexString(addr) + " " + Integer.toHexString(address) + " " + Long.toHexString(value& 0xffffffffL));
    }
}
