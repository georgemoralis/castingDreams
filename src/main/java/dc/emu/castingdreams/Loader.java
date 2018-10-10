package dc.emu.castingdreams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

/**
 *
 * @author shadow
 */
public class Loader {

    public boolean loadBinFile(String fileName, ByteBuffer mem, int address, int size) {
        File binary;
        FileInputStream stream;
        MappedByteBuffer bin;
        binary = new File(fileName);
        try {
            stream = new FileInputStream(binary);
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file " + fileName);
            return false;
        }
        try {
            bin = stream.getChannel().map(MapMode.READ_ONLY, 0, binary.length());
            bin.order(ByteOrder.LITTLE_ENDIAN);
        } catch (IOException e) {
            System.err.println("Error mapping file" + fileName);
            return false;
        }
        System.out.println("Writing to Address 0x" + Integer.toHexString(address));

        bin.rewind();
        mem.position(address);
        mem.put(bin);

        // before we do anything else
        System.out.println("Binary Loaded  " + fileName);
        return true;
    }
}
