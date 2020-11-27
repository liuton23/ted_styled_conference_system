package Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages file saving and persistence of information.
 */
public class Gateway {

    private String filepath;
    private File file;

    /**
     * Constructs a gateway path with <code>filepath</code>.
     * @param filepath  the name of the file to save and write to. Could also be changed to save file to a different
     *                  directory.
     */
    public Gateway(String filepath){
        this.filepath = filepath;
        this.file = new File(filepath);
    }

    /**
     * Writes the objects to a file.
     * @param serializing list of objects to be saved.
     * @throws IOException error in writing to file
     */
    public void writeToFile(List<Serializable> serializing) throws IOException {
        OutputStream file = new FileOutputStream(filepath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput objOut = new ObjectOutputStream(buffer);

        for(Object obj: serializing){
            objOut.writeObject(obj);
        }

        objOut.close();
    }

    /**
     * Reads from the save file.
     * @param length the number of objects to load.
     * @return the list of the loaded objects.
     * @throws IOException error in reading file or file not found.
     * @throws ClassNotFoundException class does not exist.
     */
    public ArrayList<Serializable> readFromFile(int length) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(filepath);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput objInput = new ObjectInputStream(buffer);
        ArrayList<Serializable> serialized = new ArrayList<>();

        for(int i = 0; i < length; i++){
            Object adding = objInput.readObject();
            if(adding != null) {
                serialized.add((Serializable) adding);
            }
        }

        return serialized;
    }


}
