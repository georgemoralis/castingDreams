package dc.emu.castingdreams;

/**
 *
 * @author shadow
 */
public class Main {

    public static void main(String[] args) {
        if(!DCemu.loader.loadBinFile("dc_boot.bin", DCemu.memory.bios, 0, 0))
        {
            System.out.println("error loading bios into memory");
        }
    }
}
