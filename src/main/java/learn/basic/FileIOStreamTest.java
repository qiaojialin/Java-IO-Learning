package learn.basic;

import java.io.*;

public class FileIOStreamTest {

    private static final String fileName = "src/main/resources/test.myfile";
    private static final File file = new File(fileName);

    public static void main(String[] args) throws IOException {
        if (file.exists())
            file.delete();

        //覆盖写入 6 个字节
        write(new byte[]{1, 2, 3, 4, 5, 6}, false);

        //FileInputStream只能顺序读
        InputStream in = new FileInputStream(file);

        byte[] array = new byte[3];
        //读三个字节并打印
        in.read(array);  print(array);
        System.out.println("\n还剩 " + in.available() + " 个字节可读");

        //再读三个字节并打印
        in.read(array);  print(array);
        System.out.println("\n没得读了：" + in.available());

        //覆盖写入 2 个字节
        write(new byte[]{7, 8}, false);
        read();
        //追加写入 3 个字节
        write(new byte[]{1, 2, 3}, true);
        read();
    }

    private static void print(byte[] array) {
        for (byte b : array) {
            System.out.print(b);
        }
    }

    //将字节数组 a 以 mode 模式写入文件
    private static void write(byte[] a, boolean mode) {
        try (OutputStream out = new FileOutputStream(file, mode)) {
            out.write(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
