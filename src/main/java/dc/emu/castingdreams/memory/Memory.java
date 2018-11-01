package dc.emu.castingdreams.memory;

import dc.emu.castingdreams.DCemu;
import static dc.emu.castingdreams.memory.MemoryConstants.*;
import static dc.emu.castingdreams.memory.VirtMemArea.*;
import static dc.emu.castingdreams.sh4.Sh4Constants.*;
import static dc.emu.castingdreams.sh4.Sh4RegsConstants.CCR;
import static dc.emu.castingdreams.sh4.Sh4RegsConstants.MMUCR;
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
    

    public Memory() {
        
    }

    public int read16(int address) {
        //we have only loaded bios so return only from there atm
        return DCemu.memmap.mem_read16(address);
    }

    public long read32(int address) {
        if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
        {
            return DCemu.sh4regs.read32(address);
        }
        //we have only loaded bios so return only from there atm
        return DCemu.memmap.mem_read32(address);
    }

    public void write8(int address, int value) {
        //if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
        //{
        //    DCemu.sh4regs.write8(address, value);
        //    return;
        // }
        DCemu.memmap.mem_write8(address, value);
    }

    public void write16(int address, int value) {
        if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
        {
            DCemu.sh4regs.write16(address, value);
            return;
        }
        DCemu.memmap.mem_write16(address, value);
    }

    public void write32(int address, long value) {
        VirtMemArea virt_area = sh4GetMemoryArea(address);
        switch (virt_area) {
            case SH4_AREA_P0:
            case SH4_AREA_P3:
                if ((DCemu.sh4regs.read32(MMUCR) & SH4_MMUCR_AT_MASK) != 0) {
                    throw new UnsupportedOperationException("Unsupported MMU");
                } else {
                    if (((DCemu.sh4regs.read32(CCR) & SH4_CCR_OCE_MASK) != 0) && ((DCemu.sh4regs.read32(CCR) & SH4_CCR_ORA_MASK) != 0) && sh4_ocache_in_ram_area(address)) {
                        DCemu.sh4cpu.dumpRegisters();
                        System.out.println("Unsupported ORA CACHE");
                    }
                }
                DCemu.sh4cpu.dumpRegisters();
                System.out.println("P0-P3 write");
                break;
            case SH4_AREA_P1:
                System.out.println("P1 write");
                break;
            case SH4_AREA_P2:
                System.out.println("P2 write");
            case SH4_AREA_P4:
                if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
                {
                    DCemu.sh4regs.write32(address, value);
                    return;
                }
                break;
            default:
                break;
        }

        DCemu.memmap.mem_write32(address, value);
    }
}
