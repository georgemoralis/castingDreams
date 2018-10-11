package dc.emu.castingdreams;

import dc.emu.castingdreams.sh4.Disassembler;

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
        Disassembler dis = new Disassembler();
        for (int i = pc; i < pc + 100; i += 2) {
            int opcode = DCemu.memory.read16(i);
            System.out.println(String.format("0x%08x: %04x %s", i,opcode,dis.disasm(i, opcode)));
        }
    }
}
