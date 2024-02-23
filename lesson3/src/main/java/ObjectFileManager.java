import java.io.*;
import java.util.UUID;

public class ObjectFileManager {

    public void saveObject(Serializable object) {
        String fileName = object.getClass().getName() + "_" + UUID.randomUUID().toString();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
            System.out.println("Object saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving object to file: " + e.getMessage());
        }
    }

    public Object loadObjectAndDeleteFile(String fileName) {
        Object loadedObject = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            loadedObject = ois.readObject();
            System.out.println("Object loaded from file: " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading object from file: " + e.getMessage());
        }

        if (loadedObject != null) {
            File file = new File(fileName);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File deleted: " + fileName);
                } else {
                    System.err.println("Failed to delete file: " + fileName);
                }
            }
        }

        return loadedObject;
    }
}