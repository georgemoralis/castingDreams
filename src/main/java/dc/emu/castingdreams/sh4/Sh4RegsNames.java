package dc.emu.castingdreams.sh4;

public enum Sh4RegsNames {
    EXPEVT(0xff000024),
    CCR(0xff00001c),
    MMUCR(0xff000010),
    QACR0(0xff000038),
    QACR1(0xff00003c),
    //The Timer Unit
    TOCR(0xffd80000),
    TSTR(0xffd80004),
    TCOR0(0xffd80008),
    TCNT0(0xffd8000c),
    TCR0(0xffd80010),
    TCOR1(0xffd80014),
    TCNT1(0xffd80018),
    TCR1(0xffd8001c),
    TCOR2(0xffd80020),
    TCNT2(0xffd80024),
    TCR2(0xffd80028),
    TCPR2(0xffd8002c),
    //Bus-state registers.
    PDTRA(0xff800030),
    PCTRA(0xff80002c),
    /* DMA Controller (DMAC) */
    CHCR2(0xffa0002c),
    /* interrupt controller */
    ICR(0xffd00000),
    IPRA(0xffd00004),
    IPRB(0xffd00008),
    IPRC(0xffd0000c),
    /* Serial port */
    SCSMR2(0xffe80000),
    SCBRR2(0xffe80004),
    SCSCR2(0xffe80008),
    SCFTDR2(0xffe8000c),
    SCFSR2(0xffe80010),
    SCFRDR2(0xffe80014),
    SCFCR2(0xffe80018),
    SCFDR2(0xffe8001c),
    SCSPTR2(0xffe80020),
    SCLSR2(0xffe80024);

    private final int address;

    Sh4RegsNames(int address) {
        this.address = address;
    }

    public static String getName(int address) {
        for (Sh4RegsNames s : Sh4RegsNames.values()) {
            if (s.address == address) {
                return s.name();
            }
        }
        return "NOT KNOWN 0x" + Integer.toHexString(address);
    }
}
