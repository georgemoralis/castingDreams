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

    /**
     * *****************************************************************************
     *
     * SH4 cache-control register
     *
     *****************************************************************************
     */
// IC index enable
    public static final int SH4_CCR_IIX_SHIFT = 15;
    public static final int SH4_CCR_IIX_MASK = 1 << SH4_CCR_IIX_SHIFT;

// IC invalidation
    public static final int SH4_CCR_ICI_SHIFT = 11;
    public static final int SH4_CCR_ICI_MASK = 1 << SH4_CCR_ICI_SHIFT;

// IC enable
    public static final int SH4_CCR_ICE_SHIFT = 8;
    public static final int SH4_CCR_ICE_MASK = 1 << SH4_CCR_ICE_SHIFT;

// OC index enable
    public static final int SH4_CCR_OIX_SHIFT = 7;
    public static final int SH4_CCR_OIX_MASK = 1 << SH4_CCR_OIX_SHIFT;

// OC RAM enable
    public static final int SH4_CCR_ORA_SHIFT = 5;
    public static final int SH4_CCR_ORA_MASK = 1 << SH4_CCR_ORA_SHIFT;

// OC invalidation
    public static final int SH4_CCR_OCI_SHIFT = 3;
    public static final int SH4_CCR_OCI_MASK = 1 << SH4_CCR_OCI_SHIFT;

// copy-back enable
    public static final int SH4_CCR_CB_SHIFT = 2;
    public static final int SH4_CCR_CB_MASK = 1 << SH4_CCR_CB_SHIFT;

// Write-through
    public static final int SH4_CCR_WT_SHIFT = 1;
    public static final int SH4_CCR_WT_MASK = 1 << SH4_CCR_WT_SHIFT;

// OC enable
    public static final int SH4_CCR_OCE_SHIFT = 0;
    public static final int SH4_CCR_OCE_MASK = 1 << SH4_CCR_OCE_SHIFT;


}
