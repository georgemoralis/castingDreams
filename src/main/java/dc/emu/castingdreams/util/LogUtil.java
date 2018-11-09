package dc.emu.castingdreams.util;

/**
 * Simplified log class
 */
import java.io.FileWriter;
import java.io.IOException;

public class LogUtil {

    private FileWriter logRegs;/*Internal I/O register access*/
    private FileWriter aicaRegs;/*AICA registers*/
    private FileWriter logSerialPort;
    private FileWriter logCache;
    private FileWriter logPvr;

    public final static int IOREGS = 0;
    public final static int AICAREGS = 1;
    public final static int CACHE = 2;
    public final static int PVR = 3;

    public LogUtil() {
        try {
            logRegs = new FileWriter("ioregsLog.txt");
            logSerialPort = new FileWriter("serialLog.txt");
            aicaRegs = new FileWriter("aicaregsLog.txt");
            logCache = new FileWriter("cacheLog.txt");
            logPvr = new FileWriter("pvrLog.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialLog(char character) {
        try {
            logSerialPort.append(character);
            logSerialPort.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(int logSource, String str) {
        switch (logSource) {
            case IOREGS:
                try {
                    logRegs.write(str);
                    logRegs.write("\n");
                    logRegs.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case AICAREGS:
                try {
                    aicaRegs.write(str);
                    aicaRegs.write("\n");
                    aicaRegs.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CACHE:
                try {
                    logCache.write(str);
                    logCache.write("\n");
                    logCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case PVR:
                try {
                    logPvr.write(str);
                    logPvr.write("\n");
                    logPvr.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
