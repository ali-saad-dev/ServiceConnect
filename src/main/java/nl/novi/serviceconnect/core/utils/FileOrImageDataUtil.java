package nl.novi.serviceconnect.core.utils;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class FileOrImageDataUtil {

    public static byte[] compressFileOrImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream(data.length);

        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outPutStream.write(tmp, 0, size);
        }
        try {
            outPutStream.close();
        }
        catch (IOException e ){
            throw new RuntimeException(e);
        }
        return outPutStream.toByteArray();
    }

    public static byte[] decompressFileOrImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()){
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0 , count);
            }
            outputStream.close();
        } catch (DataFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }
}
