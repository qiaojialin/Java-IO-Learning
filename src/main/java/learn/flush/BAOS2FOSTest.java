package learn.flush;

import java.io.*;

/**
 * ByteArrayOutputStream to FileOutputStream
 */
public class BAOS2FOSTest {

    private static final String fileName = "src/main/resources/test.myfile";
    private static final File file = new File(fileName);

    public static void main(String[] args) throws IOException {
        if (file.exists())
            file.delete();

        // 向 ByteArrayOutputStream 中写 4 个字节
        byte[] b1 = new byte[]{0, 1, 2, 0};
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(b1);

        // 将 ByteArrayOutputStream 缓存的数据写入 FileOutputStream 中，即写入文件中
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        baos.writeTo(fileOutputStream);

        // 关闭文件流
        fileOutputStream.close();

        read();

        file.delete();
    }

    //读取 10 个 bytes 验证
    private static void read() {
        try (InputStream in = new FileInputStream(file)) {
            byte[] a = new byte[10];
            int count = in.read(a);
            System.out.println("读了 " + count + " 个字节");
            for(int i = 0; i < a.length; i++) {
                System.out.print(a[i]);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
