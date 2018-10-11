package dc.emu.castingdreams;

/**
 *
 * @author shadow
 */
public class Main {

    public static void main(String[] args) {
        if (!DCemu.loader.loadBinFile("dc_boot.bin", DCemu.memory.bios, 0, 0)) {
            System.out.println("error loading bios into memory");
        }
        int pc = 0xA0000000; //fake program counter address
        //print 50 opcodes
        //dreamcast program counter increase +2
        for (int i = pc; i < pc + 100; i += 2) {
            int opcode = DCemu.memory.read16(i);
            System.out.println("0x" + Integer.toHexString(i) + " 0x" + Integer.toHexString(opcode));
        }
    }
}
