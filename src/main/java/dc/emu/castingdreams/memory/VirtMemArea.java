package dc.emu.castingdreams.memory;

import static dc.emu.castingdreams.memory.MemoryConstants.*;

/**
 *
 * based on http://mc.pp.se/dc/memory.html
 */
public enum VirtMemArea {
    SH4_AREA_P0,
    SH4_AREA_P1,
    SH4_AREA_P2,
    SH4_AREA_P3,
    SH4_AREA_P4;

    public static VirtMemArea sh4GetMemoryArea(int addr) {
        if (addr >= SH4_AREA_P0_FIRST && addr <= SH4_AREA_P0_LAST) {
            return SH4_AREA_P0;
        }
        if (addr >= SH4_AREA_P1_FIRST && addr <= SH4_AREA_P1_LAST) {
            return SH4_AREA_P1;
        }
        if (addr >= SH4_AREA_P2_FIRST && addr <= SH4_AREA_P2_LAST) {
            return SH4_AREA_P2;
        }
        if (addr >= SH4_AREA_P3_FIRST && addr <= SH4_AREA_P3_LAST) {
            return SH4_AREA_P3;
        }
        return SH4_AREA_P4;
    }
}
