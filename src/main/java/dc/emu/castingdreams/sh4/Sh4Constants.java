package dc.emu.castingdreams.sh4;

/**
 *
 * @author shadow
 */
public class Sh4Constants {
    // true/false condition or carry/borrow bit

    public static final int SR_FLAG_T_SHIFT = 0;
    public static final int SR_FLAG_T_MASK = 1 << SR_FLAG_T_SHIFT;

    // saturation operation for MAC instructions
    public static final int SR_FLAG_S_SHIFT = 1;
    public static final int SR_FLAG_S_MASK = 1 << SR_FLAG_S_SHIFT;

    // interrupt mask level
    public static final int SR_IMASK_SHIFT = 4;
    public static final int SR_IMASK_MASK = 0xf << SR_IMASK_SHIFT;

    public static final int SR_Q_SHIFT = 8;
    public static final int SR_Q_MASK = 1 << SR_Q_SHIFT;

    public static final int SR_M_SHIFT = 9;
    public static final int SR_M_MASK = 1 << SR_M_SHIFT;

    // FPU disable bit
    public static final int SR_FD_SHIFT = 15;
    public static final int SR_FD_MASK = 1 << SR_FD_SHIFT;

    // IRQ mask (1 == masked)
    public static final int SR_BL_SHIFT = 28;
    public static final int SR_BL_MASK = 1 << SR_BL_SHIFT;

    // general register bank switch
    public static final int SR_RB_SHIFT = 29;
    public static final int SR_RB_MASK = 1 << SR_RB_SHIFT;

    // processor mode (0 = user, 1 = priveleged)
    public static final int SR_MD_SHIFT = 30;
    public static final int SR_MD_MASK = 1 << SR_MD_SHIFT;
}
