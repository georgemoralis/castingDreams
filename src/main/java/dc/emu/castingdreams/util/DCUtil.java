package dc.emu.castingdreams.util;

/**
 *
 * @author shadow
 */
public class DCUtil {

    public static int bitExtracted(int val, int hi, int lo) {
        int ans = val << (31 - hi);
        ans = ans >>> (31 - hi + lo);
        return ans;
    }
}
