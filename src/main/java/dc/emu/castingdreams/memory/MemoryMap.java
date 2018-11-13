package dc.emu.castingdreams.memory;

import dc.emu.castingdreams.DCemu;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_AICA_FIRST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_AICA_LAST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_BIOS_LAST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_PVR2_CORE_FIRST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_PVR2_CORE_LAST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_RAM_FIRST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_RAM_LAST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_SYS_FIRST;
import static dc.emu.castingdreams.memory.MemoryMapConstants.ADDR_SYS_LAST;
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
        int addr = address & 0x1fffffff;
        System.out.println("read8 " + Integer.toHexString(address));
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            return UnsignedBuffer.getUnsignedByte(ram, address & 0x00ffffff);
        }
        throw new UnsupportedOperationException("mem_read8");
    }

    public int mem_read16(int address) {
        System.out.println("read16 " + Integer.toHexString(address));
        int addr = address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            return UnsignedBuffer.getUnsignedShort(ram, address & 0x00ffffff);
        }
        throw new UnsupportedOperationException("mem_read16");
    }

    public long mem_read32(int address) {
        System.out.println("read32 " + Integer.toHexString(address));
        int addr = address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            return UnsignedBuffer.getUnsignedInt(ram, address & 0x00ffffff);
        } else if (addr >= ADDR_AICA_FIRST && addr <= ADDR_AICA_LAST) {
            return DCemu.aicaregs.read32(addr);
        } else if (addr >= ADDR_PVR2_CORE_FIRST && addr <= ADDR_PVR2_CORE_LAST) {
            return DCemu.pvr2cregs.read32(addr);
        }
        else if (addr >= ADDR_SYS_FIRST && addr <= ADDR_SYS_LAST) {
            return DCemu.sysblock.read32(address);
        }
        System.out.println("read32 " + Integer.toHexString(addr));
        throw new UnsupportedOperationException("mem_read32");
    }

    public void mem_write8(int address, int value) {
        System.out.println("write8 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
        int addr = address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            UnsignedBuffer.putUnsignedByte(ram, address & 0x00ffffff, value);
            return;
        }
        throw new UnsupportedOperationException("mem_write8");
    }

    public void mem_write16(int address, int value) {

        System.out.println("write16 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
        int addr = address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            UnsignedBuffer.putUnsignedShort(ram, address & 0x00ffffff, value);
            return;
        }
        throw new UnsupportedOperationException("mem_write16");
    }

    public void mem_write32(int address, long value) {
        System.out.println("write32 " + Integer.toHexString(address) + " " + Long.toHexString(value & 0xffffffffL));
        int addr = address & 0x1fffffff;
        if (addr >= ADDR_RAM_FIRST && addr <= ADDR_RAM_LAST) {
            UnsignedBuffer.putUnsignedInt(ram, address & 0x00ffffff, value);
            return;
        } else if (addr >= ADDR_AICA_FIRST && addr <= ADDR_AICA_LAST) {
            DCemu.aicaregs.write32(addr, value);
            return;
        } else if (addr >= ADDR_PVR2_CORE_FIRST && addr <= ADDR_PVR2_CORE_LAST) {
            DCemu.pvr2cregs.write32(addr, value);
            return;
        }
        else if (addr >= ADDR_SYS_FIRST && addr <= ADDR_SYS_LAST) {
            DCemu.sysblock.write32(address, value);
            return;
        }

        throw new UnsupportedOperationException("mem_write32");
    }
}
