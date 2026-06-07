import java.io.*;
import java.util.HashMap;

public class IndiceSerializador {

    private static final String ARQUIVO = "indice.dat";

    public static void salvar(HashMap mapa) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            out.writeObject(mapa);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static HashMap carregar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (HashMap) in.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean indiceExiste() {
        return new File(ARQUIVO).exists();
    }
}