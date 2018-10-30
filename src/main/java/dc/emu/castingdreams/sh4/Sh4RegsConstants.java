package dc.emu.castingdreams.sh4;

/**
 *
 * Memory Mapped I/O hardware regs (
 */
public class Sh4RegsConstants {

    /*Exception-Related Registers*/
    public static final int EXPEVT = 0xff000024;
    public static final int INTEVT = 0xff000028;
    public static final int TRA = 0xff000020;

    public static final int CCR = 0xff00001c;
    public static final int MMUCR = 0xff000010;
    public static final int QACR0 = 0xff000038;
    public static final int QACR1 = 0xff00003c;

    /* Bus-state registers */
    public static final int BCR1 = 0xff800000;
    public static final int BCR2 = 0xff800004;
    public static final int WCR1 = 0xff800008;
    public static final int WCR2 = 0xff80000c;
    /* interrupt controller */
    public static final int IPRA = 0xffd00004;
    public static final int IPRB = 0xffd00008;
    public static final int IPRC = 0xffd0000c;
    /*The Timer Unit */
    public static final int TOCR = 0xffd80000;
    public static final int TSTR = 0xffd80004;
    public static final int TCOR0 = 0xffd80008;
    public static final int TCNT0 = 0xffd8000c;
    public static final int TCR0 = 0xffd80010;
    public static final int TCOR1 = 0xffd80014;
    public static final int TCNT1 = 0xffd80018;
    public static final int TCR1 = 0xffd8001c;
    public static final int TCOR2 = 0xffd80020;
    public static final int TCNT2 = 0xffd80024;
    public static final int TCR2 = 0xffd80028;
    public static final int TCPR2 = 0xffd8002c;
    /* Serial port */
    public static final int SCBRR2 = 0xffe80004;
    public static final int SCFTDR2 = 0xffe8000c;
    public static final int SCFSR2 = 0xffe80010;

    /* Serial status register flags */
    public static final int SCFSR2_ER = 0x80;
    public static final int SCFSR2_TEND = 0x40;
    public static final int SCFSR2_TDFE = 0x20;
    public static final int SCFSR2_BRK = 0x10;
    public static final int SCFSR2_RDF = 0x02;
    public static final int SCFSR2_DR = 0x01;
}
