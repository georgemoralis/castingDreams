package dc.emu.castingdreams.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shadow
 */
public class Config {

    private File file;
    private Properties configuration;
    private FileInputStream streamIn;
    private FileOutputStream streamOut;

    //properties
    public String biosPath;

    public Config() {
        file = new File("castingDreams.ini");
        configuration = new Properties();
        if (!file.exists()) {
            write("dc_bios", "dc_boot.bin");
        }
    }

    public boolean load() {
        try {
            streamIn = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.println("error opening castingDreams.ini");
        }

        try {
            configuration.load(streamIn);
            streamIn.close();
        } catch (IOException e) {
            System.out.println("error reading castingDreams.ini");
            return false;
        }
        //read values
        biosPath = configuration.getProperty("dc_bios");
        return true;
    }

    public boolean write(String key, String value) {
        try {
            //write default file
            streamOut = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {

        }
        /*write config file*/
        configuration.setProperty(key, value);
        try {
            configuration.store(streamOut, "Casting Dreams configuration file");
            streamOut.close();
        } catch (IOException e) {
            System.out.println("Error writing to configuration file");
            return false;
        }
        return true;
    }

    public String getBiosPath() {
        return biosPath;
    }
}
