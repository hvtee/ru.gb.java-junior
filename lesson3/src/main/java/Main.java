public class Main {
    public static void main(String[] args) {
        ObjectFileManager manager = new ObjectFileManager();

        String objectToSave = "Hello, World!";
        manager.saveObject(objectToSave);

        String fileName = "java.lang.String_51fe1961-f213-4474-86cf-c4e6db44664d";
        Object loadedObject = manager.loadObjectAndDeleteFile(fileName);
        if (loadedObject != null) {
            System.out.println("Loaded object: " + loadedObject);
        }
    }
}
