package dc.emu.castingdreams.memory;

/**
 *
 * @author shadow
 */
public class MemoryConstants {

    public static final int biosSize = 2 * 1024 * 1024; // 2 megabytes
    public static final int ramSize = 16 * 1024 * 1024; // 16 megabytes

    // Physical memory aread boundaries
    public static final int SH4_AREA_P0_FIRST = 0x00000000;
    public static final int SH4_AREA_P0_LAST = 0x7fffffff;
    public static final int SH4_AREA_P1_FIRST = 0x80000000;
    public static final int SH4_AREA_P1_LAST = 0x9fffffff;
    public static final int SH4_AREA_P2_FIRST = 0xa0000000;
    public static final int SH4_AREA_P2_LAST = 0xbfffffff;
    public static final int SH4_AREA_P3_FIRST = 0xc0000000;
    public static final int SH4_AREA_P3_LAST = 0xdfffffff;
    public static final int SH4_AREA_P4_FIRST = 0xe0000000;
    public static final int SH4_AREA_P4_LAST = 0xffffffff;

    //MMUCR bits
    public static final int SH4_MMUCR_AT_SHIFT = 0;
    public static final int SH4_MMUCR_AT_MASK = 1 << SH4_MMUCR_AT_SHIFT;

    /**
     * *
     * OCACHE
     */
    /**
     * if ((addr & OC_RAM_AREA_MASK) == OC_RAM_AREA_VAL) and the ORA bit is set
     * in CCR, then addr is part of the Operand Cache's RAM area
     */
    public static final int SH4_OC_RAM_AREA_MASK = 0xfc000000;
    public static final int SH4_OC_RAM_AREA_VAL = 0x7c000000;

    public static boolean sh4_ocache_in_ram_area(int addr) {
        return (addr & SH4_OC_RAM_AREA_MASK) == SH4_OC_RAM_AREA_VAL;
    }
}
