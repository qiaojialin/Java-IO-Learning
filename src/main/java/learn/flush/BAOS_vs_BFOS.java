package learn.flush;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BAOS_vs_BFOS {

    private static final Path path = Paths.get("src", "main", "resources", "test.myfile");
    private static final File file = path.toFile();

    public static void main(String[] args) throws IOException {

        writeToBAOS();

        writeToBFOS();
    }

    private static void writeToBFOS() throws IOException {
        if(file.exists())
            file.delete();

        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        long time = System.currentTimeMillis();
        for(int i = 0; i < 800; i ++) {
            bufferedOutputStream.write(new byte[1024*1024]);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        fileOutputStream.close();
        time = System.currentTimeMillis() - time;
        System.out.println("将 800M 写入 BufferedOutputStream 耗时：" + time + "ms");
        file.delete();
    }

    private static void writeToBAOS() throws IOException {
        if(file.exists())
            file.delete();

        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        long time = System.currentTimeMillis();
        for(int i = 0; i < 800; i ++) {
            byteArrayOutputStream.write(new byte[1024*1024]);
        }
        byteArrayOutputStream.writeTo(fileOutputStream);
        fileOutputStream.close();
        time = System.currentTimeMillis() - time;
        System.out.println("将 800M 写入 ByteArrayOutputStream 耗时：" + time + "ms");
        file.delete();
    }
}
