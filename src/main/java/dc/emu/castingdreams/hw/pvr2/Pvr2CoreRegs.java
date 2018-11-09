package dc.emu.castingdreams.hw.pvr2;

import dc.emu.castingdreams.DCemu;
import dc.emu.castingdreams.Debug;
import static dc.emu.castingdreams.hw.pvr2.Pvr2CoreRegConstants.ID;
import dc.emu.castingdreams.util.LogUtil;

/**
 *
 * @author shadow
 */
public class Pvr2CoreRegs {

    public Pvr2CoreRegs() {
    }
     public long read32(int address) {
        if (Debug.logPvr) {
            DCemu.logger.log(LogUtil.PVR, "read32  0x" + Integer.toHexString(address));
        }
        switch(address)
        {
            case ID:
             return 0x17fd11db;//hardware id!
        }
        throw new UnsupportedOperationException("pvr2 core regs read32");
    }
}
