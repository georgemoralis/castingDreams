package dc.emu.castingdreams.sh4;

import dc.emu.castingdreams.DCemu;
import static dc.emu.castingdreams.sh4.Sh4Constants.*;

/**
 *
 * @author shadow SH-4 interpreter class
 */
public class Sh4Int {

    /* General Purpose Registers*/
    public int registers[];
    /* Control Registers */
    public int gbr, sr, ssr, spc, vbr, sgr, dbr;
    /* System Registers */
    public int mach, macl, pr, pc, fpscr, fpul;
    /* floating point Registers*/
    public float fpRegisters[];

    //cycles
    public int cycles;

    public Sh4Int() {
        // GPR registers  R0-R15 + banked registers R0-R7 
        registers = new int[24];
        // floating point registers
        fpRegisters = new float[32];
        hardReset();
    }

    public void hardReset() { //table 2.1 page 25
        sr = SR_MD_MASK | SR_RB_MASK | SR_BL_MASK | SR_IMASK_MASK;
        vbr = 0;
        pc = 0xa0000000;
        fpscr = 0x00040001;
    }

    public void run() {
        int stop = 0;
        while (stop != -1) {
            int opcode = DCemu.memory.read16(pc);
            stop = decode(opcode);
        }
    }

    private int decode(int opcode) {
        switch ((opcode >>> 12) & 0xf) {
            case 4:
                switch ((opcode) & 0xf) {
                    case 8:
                        switch ((opcode >>> 4) & 0xf) {
                            case 2:
                                SHLL16(opcode);
                                return 0;
                        }
                }
            case 14:
                MOVI(opcode);
                return 0;
        }
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, opcode, dis.disasm(pc, opcode)));
        dumpRegisters();
        return -1;
    }

    public void dumpRegisters() {
        int i;
        for (i = 0; i < 16; i += 2) {
            System.out.print(String.format("R%-2d : %08X  R%-2d : %08X  ", i, registers[i], i + 1, registers[i + 1]));
            if (i == 0) // print SR
            {
                String sr_reg;
                sr_reg = String.format("SR:    [");
                if ((sr & SR_MD_MASK) != 0) {
                    sr_reg += ("MD ");
                } else {
                    sr_reg += ("   ");
                }
                if ((sr & SR_RB_MASK) != 0) {
                    sr_reg += ("RB ");
                } else {
                    sr_reg += ("   ");
                }
                if ((sr & SR_BL_MASK) != 0) {
                    sr_reg += ("BL ");
                } else {
                    sr_reg += ("   ");
                }
                if ((sr & SR_FD_MASK) != 0) {
                    sr_reg += ("FD ");
                } else {
                    sr_reg += ("   ");
                }
                if ((sr & SR_M_MASK) != 0) {
                    sr_reg += ("M ");
                } else {
                    sr_reg += ("  ");
                }
                if ((sr & SR_Q_MASK) != 0) {
                    sr_reg += ("Q ");
                } else {
                    sr_reg += ("  ");
                }
                if ((sr & SR_FLAG_S_MASK) != 0) {
                    sr_reg += ("S ");
                } else {
                    sr_reg += ("  ");
                }
                if ((sr & SR_FLAG_T_MASK) != 0) {
                    sr_reg += ("T");
                } else {
                    sr_reg += (" ");
                }
                sr_reg += ("]");
                System.out.print(sr_reg);
            }
            /*		if(i==2)
		{
			int fpscr = cpu->FPSCR;
			printf("FPSCR: [%s %s %s %s %d]",
				fpscr & F_FPSCR_FR ? "FR" : "  ",
				fpscr & F_FPSCR_SZ ? "SZ" : "  ",
				fpscr & F_FPSCR_PR ? "PR" : "  ",
				fpscr & F_FPSCR_DN ? "DN" : "  ",
				bits(fpscr, 1, 0));
		}
		if(i==4)
		{
			//printf("MMUCR: %08X", CCNREG(MMUCR));
			int mmucr = CCNREG(MMUCR);
			printf("MMUCR: [LRUI=%02X URB=%02X URC=%02X %s %s %s %s]",
				bits(mmucr, 31, 26),
				bits(mmucr, 23, 18),
				bits(mmucr, 15, 10),
				bits(mmucr, 9, 9) ? "SQMD" : "    ",
				bits(mmucr, 8, 8) ? "SV" : "  ",
				bits(mmucr, 2, 2) ? "TI" : "  ",
				bits(mmucr, 0, 0) ? "AT" : "  ");
		}
		if(i==6)
		{
			printf("PTEH: %08X  PTEL: %08X PTEA: %02X",
				*((Dword*)(cpu->ccnRegs+PTEH)),
				*((Dword*)(cpu->ccnRegs+PTEL)),
				*((Dword*)(cpu->ccnRegs+PTEA)) );
		}
		if(i==8)
			printf("FPUL: %08X  (%.4ff)", cpu->FPUL, *((float*)&cpu->FPUL));*/
            if (i == 10) {
                System.out.print(String.format("MACH: %08X  MACL: %08X", mach, macl));
            }
            if (i == 12) {
                System.out.print(String.format("GBR : %08X  VBR : %08X", gbr, vbr));
            }
            if (i == 14) {
                System.out.print(String.format("SPC : %08X  PR  : %08X", spc, pr));
            }
            System.out.print("\n");
        }
    }

    public int RN(int x) {
        return ((x >> 8) & 0xf);
    }

    /* MOV #imm,Rn */
    private void MOVI(int code) {

        int i = (code & 0xff);
        int n = ((code >> 8) & 0x0f);

        if ((i & 0x80) == 0) {
            registers[n] = (0x000000FF & i);
        } else {
            registers[n] = (0xFFFFFF00 | i);
        }

        cycles--;
        pc += 2;
    }

    /* SHLL16 Rn */
    private void SHLL16(int code) {
        int n = RN(code);

        registers[n] <<= 16;

        cycles--;
        pc += 2;
    }
}
