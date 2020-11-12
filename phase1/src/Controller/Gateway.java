package Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;


public class Gateway {

    private String filepath;

    public Gateway(String filepath){
        this.filepath = filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void writeToFile(List<Serializable> serializing) throws IOException {
        FileOutputStream fileOutStream = new FileOutputStream(filepath);
        ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);

        for(Object obj: serializing){
            objOutStream.writeObject(obj);
        }

        objOutStream.flush();
        objOutStream.close();
    }

    public ArrayList<Serializable> readFromFile(int length) throws IOException, ClassNotFoundException {
        FileInputStream fileInStream = new FileInputStream(filepath);
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
}
