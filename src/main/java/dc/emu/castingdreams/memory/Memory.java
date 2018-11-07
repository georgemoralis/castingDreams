package dc.emu.castingdreams.memory;

import dc.emu.castingdreams.DCemu;
import dc.emu.castingdreams.Debug;
import static dc.emu.castingdreams.memory.MemoryConstants.*;
import static dc.emu.castingdreams.memory.VirtMemArea.*;
import static dc.emu.castingdreams.sh4.Sh4Constants.*;
import static dc.emu.castingdreams.sh4.Sh4RegsConstants.CCR;
import static dc.emu.castingdreams.sh4.Sh4RegsConstants.MMUCR;
import dc.emu.castingdreams.util.LogUtil;
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
    ByteBuffer CacheArea;

    public Memory() {
        CacheArea = ByteBuffer.allocate(32 * 1024);
    }

    public int read8(int address) {
        VirtMemArea virt_area = sh4GetMemoryArea(address);
        switch (virt_area) {
            case SH4_AREA_P0:
            case SH4_AREA_P3:
                long mmucr = DCemu.sh4regs.read32(MMUCR);
                if ((mmucr & SH4_MMUCR_AT_MASK) != 0) {
                    throw new UnsupportedOperationException("Unsupported MMU");
                } else {
                    long ccr = DCemu.sh4regs.read32(CCR);
                    if (((ccr & SH4_CCR_OCE_MASK) != 0) && ((ccr & SH4_CCR_ORA_MASK) != 0) && sh4_ocache_in_ram_area(address)) {
                        // DCemu.sh4cpu.dumpRegisters();
                        System.out.println("Unsupported ORA CACHE");
                    }
                }
                return DCemu.memmap.mem_read8(address);
            case SH4_AREA_P1:
                return DCemu.memmap.mem_read8(address);
            case SH4_AREA_P2:
                return DCemu.memmap.mem_read8(address);
            case SH4_AREA_P4:
                if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
                {
                    return DCemu.sh4regs.read8(address);
                }
                break;
            default:
                throw new UnsupportedOperationException("Error");
        }
        throw new UnsupportedOperationException("Error");
    }

    public int read16(int address) {
        VirtMemArea virt_area = sh4GetMemoryArea(address);
        switch (virt_area) {
            case SH4_AREA_P0:
            case SH4_AREA_P3:
                long mmucr = DCemu.sh4regs.read32(MMUCR);
                if ((mmucr & SH4_MMUCR_AT_MASK) != 0) {
                    throw new UnsupportedOperationException("Unsupported MMU");
                } else {
                    long ccr = DCemu.sh4regs.read32(CCR);
                    if (((ccr & SH4_CCR_OCE_MASK) != 0) && ((ccr & SH4_CCR_ORA_MASK) != 0) && sh4_ocache_in_ram_area(address)) {
                        if ((ccr & 0x80) != 0) {
                            return UnsignedBuffer.getUnsignedShort(CacheArea, (((address >> 13) & 0x1000) + (address & 0x0fff)));
                        } else {
                            return UnsignedBuffer.getUnsignedShort(CacheArea, (((address >> 1) & 0x1000) + (address & 0x0fff)));
                        }
                    }
                }
                return DCemu.memmap.mem_read16(address);
            case SH4_AREA_P1:
                return DCemu.memmap.mem_read16(address);
            case SH4_AREA_P2:
                return DCemu.memmap.mem_read16(address);
            case SH4_AREA_P4:
                if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
                {
                    return DCemu.sh4regs.read16(address);
                }
                break;
            default:
                throw new UnsupportedOperationException("Error");
        }
        throw new UnsupportedOperationException("Error");
    }

    public long read32(int address) {
        VirtMemArea virt_area = sh4GetMemoryArea(address);
        switch (virt_area) {
            case SH4_AREA_P0:
            case SH4_AREA_P3:
                long mmucr = DCemu.sh4regs.read32(MMUCR);
                if ((mmucr & SH4_MMUCR_AT_MASK) != 0) {
                    throw new UnsupportedOperationException("Unsupported MMU");
                } else {
                    long ccr = DCemu.sh4regs.read32(CCR);
                    if (((ccr & SH4_CCR_OCE_MASK) != 0) && ((ccr & SH4_CCR_ORA_MASK) != 0) && sh4_ocache_in_ram_area(address)) {
                        System.out.println("cache read32 0x" + Integer.toHexString(address));
                        long read;
                        if ((ccr & 0x80) != 0) {
                            read = UnsignedBuffer.getUnsignedInt(CacheArea, (((address >> 13) & 0x1000) + (address & 0x0fff)));
                            if (Debug.logCache) {
                                DCemu.logger.log(LogUtil.CACHE, "1cache read32 from address 0x" + Integer.toHexString(address) + " 0x" + Long.toHexString(read));
                            }
                            System.out.println("1 " + Long.toHexString(read));
                            return read;
                        } else {
                            read = UnsignedBuffer.getUnsignedInt(CacheArea, (((address >> 1) & 0x1000) + (address & 0x0fff)));
                            if (Debug.logCache) {
                                DCemu.logger.log(LogUtil.CACHE, "2cache read32 from address 0x" + Integer.toHexString(address) + " 0x" + Long.toHexString(read));
                            }
                            System.out.println("2 " + Long.toHexString(read));
                            return read;
                        }
                    }
                }
                return DCemu.memmap.mem_read32(address);
            case SH4_AREA_P1:
                return DCemu.memmap.mem_read32(address);
            case SH4_AREA_P2:
                return DCemu.memmap.mem_read32(address);
            case SH4_AREA_P4:
                if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
                {
                    return DCemu.sh4regs.read32(address);
                }
                break;
            default:
                throw new UnsupportedOperationException("Error");
        }
        throw new UnsupportedOperationException("Error");
    }

    public void write8(int address, int value) {
        VirtMemArea virt_area = sh4GetMemoryArea(address);
        switch (virt_area) {
            case SH4_AREA_P0:
            case SH4_AREA_P3:
                long mmucr = DCemu.sh4regs.read32(MMUCR);
                if ((mmucr & SH4_MMUCR_AT_MASK) != 0) {
                    throw new UnsupportedOperationException("Unsupported MMU");
                } else {
                    long ccr = DCemu.sh4regs.read32(CCR);
                    System.out.println("cache write8 0x" + Integer.toHexString(address));
                    if (((ccr & SH4_CCR_OCE_MASK) != 0) && ((ccr & SH4_CCR_ORA_MASK) != 0) && sh4_ocache_in_ram_area(address)) {
                        if ((ccr & 0x80) != 0) {
                            UnsignedBuffer.putUnsignedByte(CacheArea, ((address >> 13) & 0x1000) + (address & 0x0fff), value);
                            if (Debug.logCache) {
                                DCemu.logger.log(LogUtil.CACHE, "1cache write8 0x" + Integer.toHexString(address) + " value = " + Long.toHexString(value & 0xffffffffL));
                            }
                            return;
                        } else {
                            UnsignedBuffer.putUnsignedByte(CacheArea, ((address >> 1) & 0x1000) + (address & 0x0fff), value);
                            if (Debug.logCache) {
                                DCemu.logger.log(LogUtil.CACHE, "2cache write8 0x" + Integer.toHexString(address) + " value = " + Long.toHexString(value & 0xffffffffL));
                            }
                            return;
                        }
                    }
                }
                DCemu.memmap.mem_write8(address, value);
                break;
            case SH4_AREA_P1:
                DCemu.memmap.mem_write8(address, value);
                break;
            case SH4_AREA_P2:
                DCemu.memmap.mem_write8(address, value);
                break;
            case SH4_AREA_P4:
                if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
                {
                    DCemu.sh4regs.write8(address, value);
                    return;
                }
                break;
            default:
                throw new UnsupportedOperationException("Unimplemented");
        }
    }

    public void write16(int address, int value) {
        VirtMemArea virt_area = sh4GetMemoryArea(address);
        switch (virt_area) {
            case SH4_AREA_P0:
            case SH4_AREA_P3:
                long mmucr = DCemu.sh4regs.read32(MMUCR);
                if ((mmucr & SH4_MMUCR_AT_MASK) != 0) {
                    throw new UnsupportedOperationException("Unsupported MMU");
                } else {
                    long ccr = DCemu.sh4regs.read32(CCR);
                    if (((ccr & SH4_CCR_OCE_MASK) != 0) && ((ccr & SH4_CCR_ORA_MASK) != 0) && sh4_ocache_in_ram_area(address)) {
                        System.out.println("cache write16 0x" + Integer.toHexString(address));
                        if ((ccr & 0x80) != 0) {
                            UnsignedBuffer.putUnsignedShort(CacheArea, (((address >> 13) & 0x1000) + (address & 0x0fff)), value);
                            if (Debug.logCache) {
                                DCemu.logger.log(LogUtil.CACHE, "1cache write16 0x" + Integer.toHexString(address) + " value = " + Long.toHexString(value & 0xffffffffL));
                            }
                            return;
                        } else {
                            UnsignedBuffer.putUnsignedShort(CacheArea, (((address >> 1) & 0x1000) + (address & 0x0fff)), value);
                            if (Debug.logCache) {
                                DCemu.logger.log(LogUtil.CACHE, "2cache write16 0x" + Integer.toHexString(address) + " value = " + Long.toHexString(value & 0xffffffffL));
                            }
                            return;
                        }
                    }
                }
                DCemu.memmap.mem_write16(address, value);
                break;
            case SH4_AREA_P1:
                DCemu.memmap.mem_write16(address, value);
                break;
            case SH4_AREA_P2:
                DCemu.memmap.mem_write16(address, value);
                break;
            case SH4_AREA_P4:
                if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
                {
                    DCemu.sh4regs.write16(address, value);
                    return;
                }
                break;
            default:
                throw new UnsupportedOperationException("Unimplemented");
        }
    }

    public void write32(int address, long value) {
        VirtMemArea virt_area = sh4GetMemoryArea(address);
        switch (virt_area) {
            case SH4_AREA_P0:
            case SH4_AREA_P3:
                long mmucr = DCemu.sh4regs.read32(MMUCR);
                if ((mmucr & SH4_MMUCR_AT_MASK) != 0) {
                    throw new UnsupportedOperationException("Unsupported MMU");
                } else {
                    long ccr = DCemu.sh4regs.read32(CCR);
                    if (((ccr & SH4_CCR_OCE_MASK) != 0) && ((ccr & SH4_CCR_ORA_MASK) != 0) && sh4_ocache_in_ram_area(address)) {
                        if ((ccr & 0x80) != 0) {
                            UnsignedBuffer.putUnsignedInt(CacheArea, (((address >> 13) & 0x1000) + (address & 0x0fff)), value);
                            if (Debug.logCache) {
                                DCemu.logger.log(LogUtil.CACHE, "1cache write32 0x" + Integer.toHexString(address) + " value = " + Long.toHexString(value & 0xffffffffL));
                            }
                            System.out.println("1cache write32 0x" + Integer.toHexString(address) + " value = " + Long.toHexString(value & 0xffffffffL));
                            return;
                        } else {
                            UnsignedBuffer.putUnsignedInt(CacheArea, (((address >> 1) & 0x1000) + (address & 0x0fff)), value);
                            if (Debug.logCache) {
                                DCemu.logger.log(LogUtil.CACHE, "2cache write32 0x" + Integer.toHexString(address) + " value = " + Long.toHexString(value & 0xffffffffL) + " real = " + Long.toHexString(UnsignedBuffer.getUnsignedInt(CacheArea, (((address >> 1) & 0x1000) + (address & 0x0fff)))));
                            }
                            System.out.println("2 cache write32 0x" + Integer.toHexString(address) + " value = " + Long.toHexString(value & 0xffffffffL));
                            return;
                        }
                    }
                }
                DCemu.memmap.mem_write32(address, value);
                break;
            case SH4_AREA_P1:
                DCemu.memmap.mem_write32(address, value);
                break;
            case SH4_AREA_P2:
                DCemu.memmap.mem_write32(address, value);
                break;
            case SH4_AREA_P4:
                if (address >= 0xe0000000 && address <= 0xffffffff)//map SH4 memory mapped registers
                {
                    DCemu.sh4regs.write32(address, value);
                    return;
                }
                break;
            default:
                throw new UnsupportedOperationException("Unimplemented");
        }
    }
}
