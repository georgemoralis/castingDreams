package dc.emu.castingdreams.sh4;

import dc.emu.castingdreams.DCemu;
import static dc.emu.castingdreams.sh4.Sh4Constants.*;
import static dc.emu.castingdreams.sh4.Sh4RegsConstants.CCR;
import static dc.emu.castingdreams.sh4.Sh4RegsConstants.MMUCR;
import dc.emu.castingdreams.util.DCUtil;

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

        registers[15] = 0x8c00f400; //intial value (taken from kallistos)
        DCemu.sh4regs.hardReset();
    }
    boolean unimplemented = false;

    public void run() {
        int stop = 0;
        while (stop != -1 && !unimplemented) {
            int opcode = DCemu.memory.read16(pc);
            stop = decode(opcode);
        }
    }

    private int decode(int opcode) {
        Disassembler dis = new Disassembler();
        switch ((opcode >>> 12) & 0xf) {
            case 0:
                switch ((opcode >>> 0) & 0xf) {
                    case 2:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                STCSR(opcode);
                                return 0;
                            case 1:
                                STCGBR(opcode);
                                return 0;
                            case 2:
                                STCVBR(opcode);
                                return 0;
                            case 3:
                                STCSSR(opcode);
                                return 0;
                            case 4:
                                STCSPC(opcode);
                                return 0;
                            case 8:
                                STCRBANK(opcode);
                                return 0;
                            case 9:
                                STCRBANK(opcode);
                                return 0;
                            case 10:
                                STCRBANK(opcode);
                                return 0;
                            case 11:
                                STCRBANK(opcode);
                                return 0;
                            case 12:
                                STCRBANK(opcode);
                                return 0;
                            case 13:
                                STCRBANK(opcode);
                                return 0;
                            case 14:
                                STCRBANK(opcode);
                                return 0;
                            case 15:
                                STCRBANK(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 3:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                BSRF(opcode);
                                return 0;
                            case 2:
                                BRAF(opcode);
                                return 0;
                            case 8:
                                PREF(opcode);
                                return 0;
                            case 9:
                                OCBI(opcode);
                                return 0;
                            case 10:
                                OCBP(opcode);
                                return 0;
                            case 11:
                                OCBWB(opcode);
                                return 0;
                            case 12:
                                MOVCAL(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 4:
                        MOVBS0(opcode);
                        return 0;
                    case 5:
                        MOVWS0(opcode);
                        return 0;
                    case 6:
                        MOVLS0(opcode);
                        return 0;
                    case 7:
                        MULL(opcode);
                        return 0;

                    case 8:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                CLRT(opcode);
                                return 0;
                            case 1:
                                SETT(opcode);
                                return 0;
                            case 2:
                                CLRMAC(opcode);
                                return 0;
                            case 3:
                                CLRS(opcode);
                                return 0;
                            case 4:
                                SETS(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 9:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                NOP(opcode);
                                return 0;
                            case 1:
                                DIV0U(opcode);
                                return 0;
                            case 2:
                                MOVT(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 10:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                STSMACH(opcode);
                                return 0;
                            case 1:
                                STSMACL(opcode);
                                return 0;
                            case 2:
                                STSPR(opcode);
                                return 0;
                            case 3:
                                STCSGR(opcode);
                                return 0;
                            case 5:
                                STSFPUL(opcode);
                                return 0;
                            case 6:
                                STSFPSCR(opcode);
                                return 0;
                            case 15:
                                STCDBR(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 11:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                RTS(opcode);
                                return 0;
                            case 1:
                                SLEEP(opcode);
                                return 0;
                            case 2:
                                RTE(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 12:
                        MOVBL0(opcode);
                        return 0;
                    case 13:
                        MOVWL0(opcode);
                        return 0;
                    case 14:
                        MOVLL0(opcode);
                        return 0;
                    case 15:
                        MACL(opcode);
                        return 0;
                    default:
                        NI(opcode);
                        return 0;
                }

            case 1:
                MOVLS4(opcode);
                return 0;

            case 2:
                switch ((opcode >>> 0) & 0xf) {
                    case 0:
                        MOVBS(opcode);
                        return 0;
                    case 1:
                        MOVWS(opcode);
                        return 0;
                    case 2:
                        MOVLS(opcode);
                        return 0;
                    case 4:
                        MOVBM(opcode);
                        return 0;
                    case 5:
                        MOVWM(opcode);
                        return 0;
                    case 6:
                        MOVLM(opcode);
                        return 0;
                    case 7:
                        DIV0S(opcode);
                        return 0;
                    case 8:
                        TST(opcode);
                        return 0;
                    case 9:
                        AND(opcode);
                        return 0;
                    case 10:
                        XOR(opcode);
                        return 0;
                    case 11:
                        OR(opcode);
                        return 0;
                    case 12:
                        CMPSTR(opcode);
                        return 0;
                    case 13:
                        XTRCT(opcode);
                        return 0;
                    case 14:
                        MULUW(opcode);
                        return 0;
                    case 15:
                        MULSW(opcode);
                        return 0;
                    default:
                        NI(opcode);
                        return 0;
                }

            case 3:
                switch ((opcode >>> 0) & 0xf) {
                    case 0:
                        CMPEQ(opcode);
                        return 0;
                    case 2:
                        CMPHS(opcode);
                        return 0;
                    case 3:
                        CMPGE(opcode);
                        return 0;
                    case 4:
                        DIV1(opcode);
                        return 0;
                    case 5:
                        DMULU(opcode);
                        return 0;
                    case 6:
                        CMPHI(opcode);
                        return 0;
                    case 7:
                        CMPGT(opcode);
                        return 0;
                    case 8:
                        SUB(opcode);
                        return 0;
                    case 10:
                        SUBC(opcode);
                        return 0;
                    case 11:
                        SUBV(opcode);
                        return 0;
                    case 12:
                        ADD(opcode);
                        return 0;
                    case 13:
                        DMULS(opcode);
                        return 0;
                    case 14:
                        ADDC(opcode);
                        return 0;
                    case 15:
                        ADDV(opcode);
                        return 0;
                    default:
                        NI(opcode);
                        return 0;
                }

            case 4:
                switch ((opcode >>> 0) & 0xf) {
                    case 0:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                SHLL(opcode);
                                return 0;
                            case 1:
                                DT(opcode);
                                return 0;
                            case 2:
                                SHAL(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 1:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                SHLR(opcode);
                                return 0;
                            case 1:
                                CMPPZ(opcode);
                                return 0;
                            case 2:
                                SHAR(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 2:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                STSMMACH(opcode);
                                return 0;
                            case 1:
                                STSMMACL(opcode);
                                return 0;
                            case 2:
                                STSMPR(opcode);
                                return 0;
                            case 5:
                                STSMFPUL(opcode);
                                return 0;
                            case 6:
                                STSMFPSCR(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 3:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                STCMSR(opcode);
                                return 0;
                            case 1:
                                STCMGBR(opcode);
                                return 0;
                            case 2:
                                STCMVBR(opcode);
                                return 0;
                            case 3:
                                STCMSSR(opcode);
                                return 0;
                            case 4:
                                STCMSPC(opcode);
                                return 0;
                            case 8:
                                STCMRBANK(opcode);
                                return 0;
                            case 9:
                                STCMRBANK(opcode);
                                return 0;
                            case 10:
                                STCMRBANK(opcode);
                                return 0;
                            case 11:
                                STCMRBANK(opcode);
                                return 0;
                            case 12:
                                STCMRBANK(opcode);
                                return 0;
                            case 13:
                                STCMRBANK(opcode);
                                return 0;
                            case 14:
                                STCMRBANK(opcode);
                                return 0;
                            case 15:
                                STCMRBANK(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 4:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                ROTL(opcode);
                                return 0;
                            case 2:
                                ROTCL(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 5:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                ROTR(opcode);
                                return 0;
                            case 1:
                                CMPPL(opcode);
                                return 0;
                            case 2:
                                ROTCR(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 6:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                LDSMMACH(opcode);
                                return 0;
                            case 1:
                                LDSMMACL(opcode);
                                return 0;
                            case 2:
                                LDSMPR(opcode);
                                return 0;
                            case 5:
                                LDSMFPUL(opcode);
                                return 0;
                            case 6:
                                LDSMFPSCR(opcode);
                                return 0;
                            case 15:
                                LDCMDBR(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 7:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                LDCMSR(opcode);
                                return 0;
                            case 1:
                                LDCMGBR(opcode);
                                return 0;
                            case 2:
                                LDCMVBR(opcode);
                                return 0;
                            case 3:
                                LDCMSSR(opcode);
                                return 0;
                            case 4:
                                LDCMSPC(opcode);
                                return 0;
                            case 8:
                                LDCMRBANK(opcode);
                                return 0;
                            case 9:
                                LDCMRBANK(opcode);
                                return 0;
                            case 10:
                                LDCMRBANK(opcode);
                                return 0;
                            case 11:
                                LDCMRBANK(opcode);
                                return 0;
                            case 12:
                                LDCMRBANK(opcode);
                                return 0;
                            case 13:
                                LDCMRBANK(opcode);
                                return 0;
                            case 14:
                                LDCMRBANK(opcode);
                                return 0;
                            case 15:
                                LDCMRBANK(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 8:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                SHLL2(opcode);
                                return 0;
                            case 1:
                                SHLL8(opcode);
                                return 0;
                            case 2:
                                SHLL16(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 9:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                SHLR2(opcode);
                                return 0;
                            case 1:
                                SHLR8(opcode);
                                return 0;
                            case 2:
                                SHLR16(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 10:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                LDSMACH(opcode);
                                return 0;
                            case 1:
                                LDSMACL(opcode);
                                return 0;
                            case 2:
                                LDSPR(opcode);
                                return 0;
                            case 5:
                                LDSFPUL(opcode);
                                return 0;
                            case 6:
                                LDSFPSCR(opcode);
                                return 0;
                            case 15:
                                LDCDBR(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 11:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                JSR(opcode);
                                return 0;
                            case 1:
                                TAS(opcode);
                                return 0;
                            case 2:
                                JMP(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 12:
                        SHAD(opcode);
                        return 0;
                    case 13:
                        SHLD(opcode);
                        return 0;

                    case 14:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                LDCSR(opcode);
                                return 0;
                            case 1:
                                LDCGBR(opcode);
                                return 0;
                            case 2:
                                LDCVBR(opcode);
                                return 0;
                            case 3:
                                LDCSSR(opcode);
                                return 0;
                            case 4:
                                LDCSPC(opcode);
                                return 0;
                            case 8:
                                LDCRBANK(opcode);
                                return 0;
                            case 9:
                                LDCRBANK(opcode);
                                return 0;
                            case 10:
                                LDCRBANK(opcode);
                                return 0;
                            case 11:
                                LDCRBANK(opcode);
                                return 0;
                            case 12:
                                LDCRBANK(opcode);
                                return 0;
                            case 13:
                                LDCRBANK(opcode);
                                return 0;
                            case 14:
                                LDCRBANK(opcode);
                                return 0;
                            case 15:
                                LDCRBANK(opcode);
                                return 0;
                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 15:
                        MACW(opcode);
                        return 0;
                    default:
                        NI(opcode);
                        return 0;
                }

            case 5:
                MOVLL4(opcode);
                return 0;

            case 6:
                switch ((opcode >>> 0) & 0xf) {
                    case 0:
                        MOVBL(opcode);
                        return 0;
                    case 1:
                        MOVWL(opcode);
                        return 0;
                    case 2:
                        MOVLL(opcode);
                        return 0;
                    case 3:
                        MOV(opcode);
                        return 0;
                    case 4:
                        MOVBP(opcode);
                        return 0;
                    case 5:
                        MOVWP(opcode);
                        return 0;
                    case 6:
                        MOVLP(opcode);
                        return 0;
                    case 7:
                        NOT(opcode);
                        return 0;
                    case 8:
                        SWAPB(opcode);
                        return 0;
                    case 9:
                        SWAPW(opcode);
                        return 0;
                    case 10:
                        NEGC(opcode);
                        return 0;
                    case 11:
                        NEG(opcode);
                        return 0;
                    case 12:
                        EXTUB(opcode);
                        return 0;
                    case 13:
                        EXTUW(opcode);
                        return 0;
                    case 14:
                        EXTSB(opcode);
                        return 0;
                    case 15:
                        EXTSW(opcode);
                        return 0;
                }

            case 7:
                ADDI(opcode);
                return 0;

            case 8:
                switch ((opcode >>> 8) & 0xf) {
                    case 0:
                        MOVBS4(opcode);
                        return 0;
                    case 1:
                        MOVWS4(opcode);
                        return 0;
                    case 4:
                        MOVBL4(opcode);
                        return 0;
                    case 5:
                        MOVWL4(opcode);
                        return 0;
                    case 8:
                        CMPIM(opcode);
                        return 0;
                    case 9:
                        BT(opcode);
                        return 0;
                    case 11:
                        BF(opcode);
                        return 0;
                    case 13:
                        BTS(opcode);
                        return 0;
                    case 15:
                        BFS(opcode);
                        return 0;
                    default:
                        NI(opcode);
                        return 0;
                }

            case 9:
                MOVWI(opcode);
                return 0;
            case 10:
                BRA(opcode);
                return 0;
            case 11:
                BSR(opcode);
                return 0;

            case 12:
                switch ((opcode >>> 8) & 0xf) {
                    case 0:
                        MOVBSG(opcode);
                        return 0;
                    case 1:
                        MOVWSG(opcode);
                        return 0;
                    case 2:
                        MOVLSG(opcode);
                        return 0;
                    case 3:
                        TRAPA(opcode);
                        return 0;
                    case 4:
                        MOVBLG(opcode);
                        return 0;
                    case 5:
                        MOVWLG(opcode);
                        return 0;
                    case 6:
                        MOVLLG(opcode);
                        return 0;
                    case 7:
                        MOVA(opcode);
                        return 0;
                    case 8:
                        TSTI(opcode);
                        return 0;
                    case 9:
                        ANDI(opcode);
                        return 0;
                    case 10:
                        XORI(opcode);
                        return 0;
                    case 11:
                        ORI(opcode);
                        return 0;
                    case 12:
                        TSTM(opcode);
                        return 0;
                    case 13:
                        ANDM(opcode);
                        return 0;
                    case 14:
                        XORM(opcode);
                        return 0;
                    case 15:
                        ORM(opcode);
                        return 0;
                }

            case 13:
                MOVLI(opcode);
                return 0;
            case 14:
                MOVI(opcode);
                return 0;

            case 15:
                switch ((opcode >>> 0) & 0xf) {
                    case 0:
                        FADD(opcode);
                        return 0;
                    case 1:
                        FSUB(opcode);
                        return 0;
                    case 2:
                        FMUL(opcode);
                        return 0;
                    case 3:
                        FDIV(opcode);
                        return 0;
                    case 4:
                        FCMPEQ(opcode);
                        return 0;
                    case 5:
                        FCMPGT(opcode);
                        return 0;
                    case 6:
                        FMOV_INDEX_LOAD(opcode);
                        return 0;
                    case 7:
                        FMOV_INDEX_STORE(opcode);
                        return 0;
                    case 8:
                        FMOV_LOAD(opcode);
                        return 0;
                    case 9:
                        FMOV_RESTORE(opcode);
                        return 0;
                    case 10:
                        FMOV_STORE(opcode);
                        return 0;
                    case 11:
                        FMOV_SAVE(opcode);
                        return 0;
                    case 12:
                        FMOV(opcode);
                        return 0;

                    case 13:
                        switch ((opcode >>> 4) & 0xf) {
                            case 0:
                                FSTS(opcode);
                                return 0;
                            case 1:
                                FLDS(opcode);
                                return 0;
                            case 2:
                                FLOAT1(opcode);
                                return 0;
                            case 3:
                                FTRC(opcode);
                                return 0;
                            case 4:
                                FNEG(opcode);
                                return 0;
                            case 5:
                                FABS(opcode);
                                return 0;
                            case 6:
                                FSQRT(opcode);
                                return 0;
                            case 7:
                                FSRRA(opcode);
                                return 0;
                            case 8:
                                FLDI0(opcode);
                                return 0;
                            case 9:
                                FLDI1(opcode);
                                return 0;
                            case 10:
                                FCNVSD(opcode);
                                return 0;
                            case 11:
                                FCNVDS(opcode);
                                return 0;
                            case 14:
                                FIPR(opcode);
                                return 0;

                            case 15:
                                switch ((opcode >>> 8) & 0xf) {
                                    case 0:
                                        FSCA(opcode);
                                        return 0;
                                    case 1:
                                        FTRV(opcode);
                                        return 0;
                                    case 2:
                                        FSCA(opcode);
                                        return 0;
                                    case 3:
                                        FSCHG(opcode);
                                        return 0;
                                    case 4:
                                        FSCA(opcode);
                                        return 0;
                                    case 5:
                                        FTRV(opcode);
                                        return 0;
                                    case 6:
                                        FSCA(opcode);
                                        return 0;
                                    case 8:
                                        FSCA(opcode);
                                        return 0;
                                    case 9:
                                        FTRV(opcode);
                                        return 0;
                                    case 10:
                                        FSCA(opcode);
                                        return 0;
                                    case 11:
                                        FRCHG(opcode);
                                        return 0;
                                    case 12:
                                        FSCA(opcode);
                                        return 0;
                                    case 13:
                                        FTRV(opcode);
                                        return 0;
                                    case 14:
                                        FSCA(opcode);
                                        return 0;
                                    default:
                                        NI(opcode);
                                        return 0;
                                }

                            default:
                                NI(opcode);
                                return 0;
                        }

                    case 14:
                        FMAC(opcode);
                        return 0;
                    case 15:
                        NI(opcode);
                        return 0;
                }
        }
        // Disassembler dis = new Disassembler();
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
		}*/
            if (i == 2) {
                int ccr = (int) DCemu.sh4regs.read32(CCR);
                System.out.print(String.format("CCR: [%s %s %s %s %s %s %s %s %s]",
                        DCUtil.bitExtracted(ccr, 15, 15) != 0 ? "IIX" : "    ",
                        DCUtil.bitExtracted(ccr, 11, 11) != 0 ? "ICI" : "    ",
                        DCUtil.bitExtracted(ccr, 8, 8) != 0 ? "ICE" : "  ",
                        DCUtil.bitExtracted(ccr, 7, 7) != 0 ? "OIX" : "  ",
                        DCUtil.bitExtracted(ccr, 5, 5) != 0 ? "ORA" : "  ",
                        DCUtil.bitExtracted(ccr, 3, 3) != 0 ? "OCI" : "  ",
                        DCUtil.bitExtracted(ccr, 2, 2) != 0 ? "CB" : "  ",
                        DCUtil.bitExtracted(ccr, 1, 1) != 0 ? "WT" : "  ",
                        DCUtil.bitExtracted(ccr, 0, 0) != 0 ? "OCE" : "  "));
            }
            if (i == 4) {
                int mmucr = (int) DCemu.sh4regs.read32(MMUCR);
                System.out.print(String.format("MMUCR: [LRUI=%02X URB=%02X URC=%02X %s %s %s %s]",
                        DCUtil.bitExtracted(mmucr, 31, 26),
                        DCUtil.bitExtracted(mmucr, 23, 18),
                        DCUtil.bitExtracted(mmucr, 15, 10),
                        DCUtil.bitExtracted(mmucr, 9, 9) != 0 ? "SQMD" : "    ",
                        DCUtil.bitExtracted(mmucr, 8, 8) != 0 ? "SV" : "  ",
                        DCUtil.bitExtracted(mmucr, 2, 2) != 0 ? "TI" : "  ",
                        DCUtil.bitExtracted(mmucr, 0, 0) != 0 ? "AT" : "  "));
            }
            /*if(i==6)
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
        System.out.print(String.format("PC : %08X", pc));
        System.out.print("\n");
    }

    public int RN(int x) {
        return ((x >> 8) & 0xf);
    }

    public int RM(int x) {
        return ((x >> 4) & 0xf);
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

    /* SWAP.W Rm,Rn */
    private void SWAPW(int code) {
        int m = RM(code);
        int n = RN(code);
        int temp = 0;
        temp = (registers[m] >>> 16) & 0x0000FFFF;
        registers[n] = registers[m] << 16;
        registers[n] |= temp;

        cycles--;
        pc += 2;
    }

    /* MOV.L @(disp,Rm),Rn */
    private void MOVLL4(int code) {
        int d = (code & 0x0f);
        int m = RM(code);
        int n = RN(code);

        registers[n] = (int) DCemu.memory.read32(registers[m] + (d << 2));//lossy convertion(to check!)

        cycles--;
        pc += 2;
    }

    /* SHLL8 Rn */
    private void SHLL8(int code) {
        int n = RN(code);
        registers[n] <<= 8;
        cycles--;
        pc += 2;
    }

    /* SHLR2 Rn */
    private void SHLR2(int code) {
        int n = RN(code);
        registers[n] >>>= 2;
        registers[n] &= 0x3FFFFFFF;
        cycles--;
        pc += 2;
    }

    /* XOR Rm,Rn */
    private void XOR(int code) {
        int m = RM(code);
        int n = RN(code);
        registers[n] ^= registers[m];
        cycles--;
        pc += 2;
    }

    /* MULU.W Rm,Rn */
    private void MULUW(int code) {
        int m = RM(code);
        int n = RN(code);
        macl = (((int) registers[n] & 0xFFFF) * ((int) registers[m] & 0xFFFF));
        cycles -= 2;
        pc += 2;
    }

    /* STS MACL,Rn */
    private void STSMACL(int code) {
        int n = RN(code);
        registers[n] = macl;
        cycles--;
        pc += 2;
    }

    /* TST Rm,Rn */
    private void TST(int code) {
        int m = RM(code);
        int n = RN(code);

        if ((registers[n] & registers[m]) == 0) {
            sr |= SR_FLAG_T_MASK;
        } else {
            sr &= (~SR_FLAG_T_MASK);
        }

        cycles--;
        pc += 2;
    }

    /* MOV.L Rm,@(disp,Rn) */
    private void MOVLS4(int code) {//recheck!
        int d = (code & 0x0f);
        int m = RM(code);
        int n = RN(code);

        DCemu.memory.write32(registers[n] + (d << 2), registers[m]);
        cycles--;
        pc += 2;
    }

    /* ADD #imm,Rn */
    private void ADDI(int code) { //recheck!!
        int n = RN(code);
        byte b = (byte) (code & 0xff);
        registers[n] += (int) b;
        cycles--;
        pc += 2;
    }

    /* SHAR Rn */
    private void SHAR(int code) {
        int n = RN(code);
        int temp = 0;
        if ((registers[n] & 0x00000001) == 0) {
            sr &= (~SR_FLAG_T_MASK);
        } else {
            sr |= SR_FLAG_T_MASK;
        }
        if ((registers[n] & 0x80000000) == 0) {
            temp = 0;
        } else {
            temp = 1;
        }
        registers[n] >>= 1;
        if (temp == 1) {
            registers[n] |= 0x80000000;
        } else {
            registers[n] &= 0x7FFFFFFF;
        }
        cycles--;
        pc += 2;
    }

    /* MOV.W R0,@(disp,Rn) */
    private void MOVWS4(int code) {
        int d = ((code >> 0) & 0x0f);
        int n = RM(code);
        DCemu.memory.write16(registers[n] + (d << 1), registers[0]);
        cycles--;
        pc += 2;
    }

    /* OR #imm,R0 */
    private void ORI(int code) {
        int i = ((code >> 0) & 0xff);
        registers[0] |= i;
        cycles--;
        pc += 2;
    }

    /* SHLR Rn */
    private void SHLR(int code) {
        int n = RN(code);

        sr = (sr & ~SR_FLAG_T_MASK) | (registers[n] & 1);
        registers[n] >>>= 1;
        registers[n] &= 0x7FFFFFFF;

        cycles--;
        pc += 2;

    }

    /* ROTR Rn */
    private void ROTR(int code) {
        int n = RN(code);

        if ((registers[n] & SR_FLAG_T_MASK) != 0) {
            sr |= SR_FLAG_T_MASK;
        } else {
            sr &= ~SR_FLAG_T_MASK;
        }

        registers[n] >>>= 1;

        if ((sr & SR_FLAG_T_MASK) != 0) {
            registers[n] |= 0x80000000;
        } else {
            registers[n] &= 0x7FFFFFFF;
        }
        cycles--;
        pc += 2;
    }

    /* MOV Rm,Rn */
    private void MOV(int code) {
        int m = RM(code);
        int n = RN(code);

        registers[n] = registers[m];

        cycles--;
        pc += 2;
    }

    /* TST #imm,R0 */
    private void TSTI(int code) {
        int i = ((code >> 0) & 0xff);

        if ((registers[0] & i) != 0) {
            sr |= SR_FLAG_T_MASK;
        } else {
            sr &= (~SR_FLAG_T_MASK);
        }

        cycles--;
        pc += 2;
    }

    /* MOV.L @(disp,PC),Rn */
    private void MOVLI(int code) {//recheck!
        int d = (code & 0xff);
        int n = ((code >> 8) & 0x0f);

        registers[n] = (int) DCemu.memory.read32((pc & 0xfffffffc) + 4 + (d << 2));

        cycles--;
        pc += 2;
    }

    /* LDS Rm,PR */
    private void LDSPR(int code) {
        dumpRegisters();
        int m = RN(code);
        pr = registers[m];
        pc += 2;
        cycles -= 2;
    }

    /* JMP @Rn */
    private void JMP(int code) { //recheck!
        int n = RN(code);
        int target = registers[n];
        decode(DCemu.memory.read16(pc + 2));
        pc = target;

        cycles -= 2;
    }

    /* STS.L PR,@-Rn */
    private void STSMPR(int code) {
        int n = RN(code);
        registers[n] -= 4;
        DCemu.memory.write32(registers[n], pr);
        cycles -= 2;
        pc += 2;
    }

    /* BSR disp */
    private void BSR(int code) {
        int disp = 0;
        if ((code & 0x800) == 0) {
            disp = (0x00000FFF & code);
        } else {
            disp = (0xFFFFF000 | code);
        }
        pr = pc + 4;
        int newpc = pc + (disp << 1) + 4;
        decode(DCemu.memory.read16(pc + 2));
        pc = newpc;
        cycles -= 2;
    }

    /* MOV.L @Rm,Rn */
    private void MOVLL(int code) {
        int m = RM(code);
        int n = RN(code);

        registers[n] = (int) DCemu.memory.read32(registers[m]);

        cycles--;
        pc += 2;
    }

    /* BRA disp */
    private void BRA(int code) {
        int disp;

        if ((code & 0x800) == 0) {
            disp = (0x00000FFF & code);
        } else {
            disp = (0xFFFFF000 | code);
        }

        int newpc = pc + 4 + (disp << 1);

        decode(DCemu.memory.read16(pc + 2));

        pc = newpc;

        cycles -= 2;
    }

    /* CMP_HS Rm,Rn */
    private void CMPHS(int code) {
        int m = RM(code);
        int n = RN(code);

        if (((long) (registers[n] & 0xFFFFFFFFL)) >= ((long) (registers[m] & 0xFFFFFFFFL))) {
            sr |= SR_FLAG_T_MASK;
        } else {
            sr &= (~SR_FLAG_T_MASK);
        }
        cycles--;
        pc += 2;
    }

    /* BF disp */
    private void BF(int code) {
        if ((sr & SR_FLAG_T_MASK) == 0) {
            int d = (code & 0xff);

            if ((code & 0x80) == 0) {
                d = (0x000000FF & code);
            } else {
                d = (0xFFFFFF00 | code);
            }
            pc += (d << 1) + 4;
            cycles--;
        } else {
            cycles--;
            pc += 2;
        }
    }

    /* MOV.B Rm,@Rn */
    private void MOVBS(int code) {
        int m = RM(code);
        int n = RN(code);

        DCemu.memory.write8(registers[n], registers[m]);

        cycles--;
        pc += 2;
    }

    /* RTS */
    private void RTS(int code) {
        int newpc = pr;
        decode(DCemu.memory.read16(pc + 2));
        pc = newpc;
        cycles -= 2;
    }

    /* MOV.L Rm,@Rn */
    private void MOVLS(int code) {
        int m = RM(code);
        int n = RN(code);
        DCemu.memory.write32(registers[n], registers[m]);

        cycles--;
        pc += 2;
    }

    /* JSR @Rn */
    private void JSR(int code) {
        int n = RN(code);
        pr = pc + 4;
        int target = registers[n];
        decode(DCemu.memory.read16(pc + 2));
        pc = target;
        cycles -= 2;
    }

    /* LDS.L @Rm+,PR */
    private void LDSMPR(int code) {

        int m = RN(code);
        pr = (int) (DCemu.memory.read32(registers[m]));

        registers[m] += 4;

        cycles -= 2;
        pc += 2;
        dumpRegisters();
    }

    /* MOV.L Rm,@-Rn */
    private void MOVLM(int code) {
        int m = RM(code);
        int n = RN(code);
        registers[n] -= 4;
        DCemu.memory.write32(registers[n], registers[m]);
        cycles--;
        pc += 2;
    }

    /* CMP_EQ #imm,R0 */
    private void CMPIM(int code) {
        int i = 0;

        if ((code & 0x80) == 0) {
            i = (0x000000FF & code);
        } else {
            i = (0xFFFFFF00 | code);
        }

        if (registers[0] == i) {
            sr |= SR_FLAG_T_MASK;
        } else {
            sr &= (~SR_FLAG_T_MASK);
        }

        cycles--;
        pc += 2;
    }

    /* MOV.B @Rm,Rn */
    private void MOVBL(int code) { //recheck!
        int m = RM(code);
        int n = RN(code);
        byte c;

        c = (byte) (DCemu.memory.read8(registers[m]) & 0xFF);

        registers[n] = (int) c;

        cycles--;
        pc += 2;
    }

    /* AND #imm,R0 */
    private void ANDI(int code) {
        int i = ((code >> 0) & 0xff);
        registers[0] &= i;
        cycles--;
        pc += 2;
    }

    /* MOV.W Rm,@Rn */
    private void MOVWS(int code) {
        int m = RM(code);
        int n = RN(code);

        DCemu.memory.write16(registers[n], registers[m]);

        cycles--;
        pc += 2;
    }

    /* STC SR,Rn : Privileged */
    private void STCSR(int code) {
        //TODO check if it is in privileged mode else we should raise illegal instruction exception
        int n = RN(code);

        registers[n] = sr;

        cycles -= 2;
        pc += 2;
    }

    /* MOV.W @(disp,PC),Rn */
    private void MOVWI(int code) {
        int d = (code & 0xff);
        int n = ((code >> 8) & 0x0f);

        registers[n] = DCemu.memory.read16(pc + 4 + (d << 1));

        if ((registers[n] & 0x8000) == 0) {
            registers[n] &= 0x0000FFFF;
        } else {
            registers[n] |= 0xFFFF0000;
        }

        cycles--;
        pc += 2;
    }

    /* AND Rm,Rn */
    private void AND(int code) {
        int m = RM(code);
        int n = RN(code);

        registers[n] &= registers[m];

        cycles--;
        pc += 2;
    }

    /* MOV.W @(disp,Rm),R0 */
    private void MOVWL4(int code) {
        int d = ((code >> 0) & 0x0f);
        int m = RM(code);

        short w = (short) DCemu.memory.read16(registers[m] + (d << 1));
        registers[0] = (int) w;

        cycles--;
        pc += 2;
    }

    /**
     *
     *
     *
     * Unimplemented opcodes
     *
     *
     */
    private void NI(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("illegal instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVWL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVBM(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVWM(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void MOVBP(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVWP(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVLP(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVBS4(int code) {
        int d = ((code >> 0) & 0x0f);
        int n = RM(code);

        DCemu.memory.write8(registers[n] + (d << 0), (byte) registers[0]);

        cycles--;
        pc += 2;
    }

    private void MOVBL4(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVBS0(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVWS0(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVLS0(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void MOVBL0(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVWL0(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void MOVLL0(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void MOVBSG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVWSG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVLSG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVBLG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void MOVWLG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVLLG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVCAL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void MOVA(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MOVT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void SWAPB(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void XTRCT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void ADD(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void ADDC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void ADDV(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void CMPEQ(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void CMPGE(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void CMPHI(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void CMPGT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void CMPPZ(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void CMPPL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void CMPSTR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void DIV1(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void DIV0S(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void DIV0U(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void DMULS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void DMULU(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void DT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void EXTSB(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void EXTSW(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void EXTUB(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void EXTUW(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MACL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MACW(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MULL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void MULSW(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void NEG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void NEGC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void SUB(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void SUBC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void SUBV(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void ANDM(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void NOT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void OR(int code) {
        int m = RM(code);
        int n = RN(code);
        registers[n] |= registers[m];
        cycles--;
        pc += 2;
    }

    private void ORM(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void TAS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void TSTM(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void XORI(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void XORM(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void ROTCR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void ROTL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void ROTCL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void SHAD(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void SHLD(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void SHAL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void SHLL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void SHLL2(int code) {
        int n = RN(code);

        registers[n] <<= 2;

        cycles--;
        pc += 2;

    }

    private void SHLR8(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void SHLR16(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void BFS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void BT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void BTS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void BRAF(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void BSRF(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void RTE(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void CLRMAC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void CLRS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void CLRT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    /* LDC Rm,SR : Privileged */
    private void LDCSR(int code) {
        int m = RN(code);

        int rb = (sr & SR_RB_MASK);

        sr = registers[m] & 0x700083f3;

        if (((sr & SR_RB_MASK) != rb) && ((sr & SR_MD_MASK) != 0)) {
            //TODO must switch grp banks!!!
            Disassembler dis = new Disassembler();
            System.out.println("Unsupported instruction");
            System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
            dumpRegisters();
        }
        cycles -= 4;
        pc += 2;

    }

    private void LDCGBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void LDCVBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDCSSR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDCSPC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDCDBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDCRBANK(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDCMSR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void LDCMGBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void LDCMVBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void LDCMSSR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDCMSPC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDCMDBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDCMRBANK(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDSMACH(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDSMACL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void LDSMMACH(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void LDSMMACL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDTLB(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void NOP(int code) {
        cycles--;
        pc += 2;
    }

    private void OCBI(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void OCBP(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void OCBWB(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void PREF(int code) {
        int n = RN(code);
        if (registers[n] >= 0xe0000000 && registers[n] <= 0xeffffffc) {
            Disassembler dis = new Disassembler();
            System.out.println("Unsupported pref sq write?");
            System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
            dumpRegisters();
            unimplemented = true;
        }
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void SETS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void SETT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void SLEEP(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void STCGBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STCVBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void STCSSR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void STCSPC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void STCSGR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void STCDBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void STCRBANK(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STCMSR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STCMGBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STCMVBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STCMSSR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STCMSPC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void STCMSGR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void STCMDBR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void STCMRBANK(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void STSMACH(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
        unimplemented = true;
    }

    private void STSPR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STSMMACH(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STSMMACL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void TRAPA(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void LDSFPSCR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDSFPUL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void LDSMFPSCR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void LDSMFPUL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STSFPSCR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STSFPUL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STSMFPSCR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void STSMFPUL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FLDI0(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FLDI1(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FMOV(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FMOV_LOAD(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FMOV_INDEX_LOAD(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FMOV_RESTORE(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }//

    private void FMOV_SAVE(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FMOV_STORE(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FMOV_INDEX_STORE(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FLDS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FSTS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FABS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FNEG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FCNVDS(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FCNVSD(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FTRC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FLOAT1(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FCMPEQ(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FCMPGT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FMAC(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FDIV(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FMUL(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FSQRT(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FSRRA(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FADD(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FSUB(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FIPR(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FTRV(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FSCA(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }

    private void FRCHG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();

    }

    private void FSCHG(int code) {
        Disassembler dis = new Disassembler();
        System.out.println("Unsupported instruction");
        System.out.println(String.format("0x%08x: %04x %s", pc, code, dis.disasm(pc, code)));
        dumpRegisters();
    }
}
