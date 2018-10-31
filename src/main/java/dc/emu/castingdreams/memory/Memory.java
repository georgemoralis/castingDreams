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
        System.out.println("read16 " + Integer.toHexString(address));
        return UnsignedBuffer.getUnsignedShort(ram, address & 0x00ffffff);
    }

    public long read32(int address) {
        if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
        {
            return DCemu.sh4regs.read32(address);
        }
        //we have only loaded bios so return only from there atm
        System.out.println("read32 " + Integer.toHexString(address));
        return UnsignedBuffer.getUnsignedInt(ram, address & 0x00ffffff);
    }

    public void write8(int address, int value) {
        //if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
        //{
        //    DCemu.sh4regs.write8(address, value);
        //    return;
       // }
        UnsignedBuffer.putUnsignedByte(ram, address & 0x00ffffff, value);
        System.out.println("write8 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
    }

    public void write16(int address, int value) {
        if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
        {
            DCemu.sh4regs.write16(address, value);
            return;
        }
        UnsignedBuffer.putUnsignedShort(ram, address & 0x00ffffff, value);
        System.out.println("write16 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
    }

    public void write32(int address, int value) {
        if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
        {
            DCemu.sh4regs.write32(address, value);
            return;
        }
        UnsignedBuffer.putUnsignedInt(ram, address & 0x00ffffff, value);
        System.out.println("write32 " + Integer.toHexString(address) + " " + Integer.toHexString(value));
    }
}
