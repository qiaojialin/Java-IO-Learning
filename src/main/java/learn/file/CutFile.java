package learn.file;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CutFile {

    private static final Path path = Paths.get("src", "main", "resources", "test.myfile");
    private static final File file = path.toFile();

    public static void main(String[] args) {
        if(file.exists())
            file.delete();

        try (FileOutputStream out = new FileOutputStream(file, true)) {
            out.write(new byte[]{1,2,3,4,5,6,7});

            read();

            // 在文件最后 4 字节前截断文件
            out.getChannel().truncate(file.length()-4);
            read();

            out.write(new byte[]{1,2,3});
            read();

            // 在文件最后 4 字节前截断文件
//            RandomAccessFile raf = new RandomAccessFile(file, "rw");
//            raf.setLength(file.length()-4);
//            raf.getChannel().truncate(file.length()-4);
//            raf.close();
//            read();

        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();

    }


    //读取 10 个字节
    public static void read() {
        try (InputStream in = new FileInputStream(file)) {
            byte[] a = new byte[10];
            System.out.print("整个文件有 " + in.available() + " 个字节：");
            in.read(a);
            for (int i = 0; i < a.length; i++) {
                System.out.print(a[i]);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
