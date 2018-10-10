package dc.emu.castingdreams.util;

/**
 * Simplified log class
 */
import java.io.FileWriter;
import java.io.IOException;

public class LogUtil {

    private FileWriter logRegs;/*Internal I/O register access*/
    private FileWriter logSerialPort;

    public final static int IOREGS = 0;

    public LogUtil() {
        try {
            logRegs = new FileWriter("ioregsLog.txt");
            logSerialPort = new FileWriter("serialLog.txt");
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
