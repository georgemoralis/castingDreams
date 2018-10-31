package dc.emu.castingdreams.util;

/**
 *
 * @author shadow
 */
public class DCUtil {

    // Function to extract k bits from p position 
    // and returns the extracted value as integer 
    public static int bitExtracted(int number, int k, int p) {
        return (((1 << k) - 1) & (number >> (p - 1)));
    }
}
