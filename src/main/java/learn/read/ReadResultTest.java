package learn.read;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadResultTest {

    private static final Path path = Paths.get("test.myfile");
    private static final File file = path.toFile();
    private static int size = 10;
    private static byte[] b1 = new byte[size];

    public static void main(String[] args) {
        if (file.exists())
            file.delete();

        try (OutputStream out = new FileOutputStream(file, false)) {
            out.write(b1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {

            // 数据长度为 10 字节
            int length = 10;

            // 数据起始位置为 offset=5
            int offset = 5;

            // 开辟长度为 length 数组
            byte[] result = new byte[length];

            // 将 RAF 移动到数据起始位置，准备读 length 个字节
            raf.seek(offset);

            // 读 length 个字节
            int count = raf.read(result);
            System.out.println("读了 " + count + " 个字节");

            // 再读一次
            count = raf.read(result);
            System.out.println("读了 " + count + " 个字节");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            int length = 10;
            int offset = 5;
            byte[] result = new byte[length];
            raf.seek(offset);

            // 必须读 length 个字节，只要没读够就抛异常
            raf.readFully(result);
        } catch (EOFException e) {
            System.out.println("没读够 10 个字节就到文件末尾了，抛出 EOFException");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        file.delete();
    }

}
