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
        FileOutputStream fileOutStream = new FileOutputStream(file);
        ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);

        for(Object obj: serializing){
            objOutStream.writeObject(obj);
        }

        objOutStream.flush();
        objOutStream.close();
    }

    /**
     * Reads from the save file.
     * @param length the number of objects to load.
     * @return the list of the loaded objects.
     * @throws IOException error in reading file or file not found.
     * @throws ClassNotFoundException class does not exist.
     */
    public ArrayList<Serializable> readFromFile(int length) throws IOException, ClassNotFoundException {
        FileInputStream fileInStream = new FileInputStream(file);
        ObjectInputStream objInStream = new ObjectInputStream(fileInStream);
        ArrayList<Serializable> serialized = new ArrayList<>();

        for(int i = 0; i < length; i++){
            Object adding = objInStream.readObject();
            if(adding != null) {
                serialized.add((Serializable) adding);
            }
        }

        return serialized;
    }

    public void writeObjToFile(Serializable obj,String path){
        try {
            OutputStream file = new FileOutputStream(path);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            output.writeObject(obj); //students);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Serializable readObjFromFile(String path) throws ClassNotFoundException{
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            Serializable obj = (Serializable) input.readObject();
            input.close();
            return obj;
        } catch (IOException e) {
            System.out.println("No save file found.");
            return null;
        }
    }
}
