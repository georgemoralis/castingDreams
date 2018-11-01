package dc.emu.castingdreams.memory;

/**
 *
 * @author shadow
 */
public class MemoryMapConstants {

    // System Boot ROM
    public static final int ADDR_BIOS_FIRST = 0;
    public static final int ADDR_BIOS_LAST = 0x001fffff;

// flash memory
    public static final int ADDR_FLASH_FIRST = 0x00200000;
    public static final int ADDR_FLASH_LAST = 0x0021ffff;

// main system memory
    public static final int ADDR_RAM_FIRST = 0x0c000000;
    public static final int ADDR_RAM_LAST = 0x0cffffff;

// G1 bus control registers
    public static final int ADDR_G1_FIRST = 0x005F7400;
    public static final int ADDR_G1_LAST = 0x005F74FF;

// system block registers
    public static final int ADDR_SYS_FIRST = 0x005f6800;
    public static final int ADDR_SYS_LAST = 0x005F69FF;

// maple bus registers
    public static final int ADDR_MAPLE_FIRST = 0x5f6c00;
    public static final int ADDR_MAPLE_LAST = 0x5f6cff;

// G2 bus control registers
    public static final int ADDR_G2_FIRST = 0x5f7800;
    public static final int ADDR_G2_LAST = 0x5f78ff;

// NEC PowerVR 2 control registers
    public static final int ADDR_PVR2_FIRST = 0x5f7c00;
    public static final int ADDR_PVR2_LAST = 0x5f7cff;

    public static final int ADDR_PVR2_CORE_FIRST = 0x5f8000;
    public static final int ADDR_PVR2_CORE_LAST = 0x5f9fff;

// modem registers
    public static final int ADDR_MODEM_FIRST = 0x600000;
    public static final int ADDR_MODEM_LAST = 0x60048c;

// AICA registers
    public static final int ADDR_AICA_FIRST = 0x00700000;
    public static final int ADDR_AICA_LAST = 0x00707FFF;
}
