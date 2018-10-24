package dc.emu.castingdreams;

import dc.emu.castingdreams.sh4.Disassembler;

/**
 *
 * @author shadow
 */
public class Main {

    public static void main(String[] args) {
        if (!DCemu.config.load()) {
            System.out.println("error loading configuration file Exiting...");
            return;
        }
        if (!DCemu.loader.loadBinFile(DCemu.config.getBiosPath(), DCemu.memory.bios, 0, 0)) {
            System.out.println("error loading bios into memory");
        }
        DCemu.sh4cpu.run();
        /*
        int pc = 0xA0000000; //fake program counter address
        //print 100 opcodes
        //dreamcast program counter increase +2
        Disassembler dis = new Disassembler();
        for (int i = pc; i < pc + 200; i += 2) {
            int opcode = DCemu.memory.read16(i);
            System.out.println(String.format("0x%08x: %04x %s", i, opcode, dis.disasm(i, opcode)));
        }*/
    }
}
