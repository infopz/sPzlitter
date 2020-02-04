package core;

import javax.crypto.Cipher;
import java.io.*;
import java.util.Arrays;
import java.util.zip.*;
import java.security.*;
import javax.crypto.spec.IvParameterSpec;

public class Decompresser {

    public static void back(File f){
        try {
            File file = new File(f.getAbsolutePath()+".part0");
            byte[] b = new byte[(int)file.length()];
            String sourcePath = file.getAbsolutePath();
            FileInputStream i = new FileInputStream(sourcePath);
            i.read(b);


            Inflater decompresser = new Inflater();
            decompresser.setInput(b, 0, (int)file.length());
            //FIXME: cambiare la dimensione
            byte[] result = new byte[15000];
            int resultLength = decompresser.inflate(result);
            decompresser.end();
            File out = new File("output.txt");
            FileOutputStream o = new FileOutputStream(out.getAbsolutePath());
            o.write(result, 0, resultLength);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void backCypher(File f){
        try {
            File file = new File(f.getAbsolutePath()+".part0");
            byte[] b = new byte[(int) file.length()];
            FileInputStream i = new FileInputStream(file.getAbsolutePath());
            i.read(b);

            File keyFile = new File(f.getAbsolutePath()+".key.PROTECTME");
            FileInputStream k = new FileInputStream(keyFile.getAbsolutePath());
            ObjectInputStream ks = new ObjectInputStream(k);

            Key key = (Key) ks.readObject();
            String buffersDim  = (String) ks.readObject();
            String[] dim = buffersDim.split(";");

            System.out.println(buffersDim);

            IvParameterSpec iv = new IvParameterSpec(key.getEncoded());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  // Transformation of the algorithm
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            File out = new File(Utils.getDirectory(f), "output.txt");
            FileOutputStream o = new FileOutputStream(out.getAbsolutePath());
            int total = 0;
            for(int j=0; j<dim.length; j++){
                System.out.println(total);
                byte[] decriptBuffer = Arrays.copyOfRange(b, total, total + Integer.parseInt(dim[j]));
                byte[] decripted = cipher.doFinal(decriptBuffer);
                o.write(decripted);
                total = total + Integer.parseInt(dim[j]);
            }
            o.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getMedatada(File f){
        try {
            String sourcePath = f.getAbsolutePath();
            FileInputStream i = new FileInputStream(sourcePath);
            byte[] b = new byte[10];
            i.read(b, 0, 10);
            String s = new String(b);
            int index = s.indexOf("pz");
            String s2 = s.substring(0, index);
            System.out.println(s + " " + index + " " + s2);
            return s2;

        } catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
