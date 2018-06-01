package learn.basic;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {

    private static String fileName = "src/main/resources/test.myfile";
    private static File file = new File(fileName);

    public static void main(String[] args) throws IOException {
        byte[] a = new byte[10];
        RandomAccessFile randomAccess = new RandomAccessFile(file, "rw");
        //跳过 2 个字节
        randomAccess.seek(2);
        //写入 6 个字节
        randomAccess.write(new byte[]{1,2,3,4,5,6});
        System.out.println("当前位置: " + randomAccess.getFilePointer());
        System.out.println("文件长度: "+randomAccess.length());

        //从头读取 10 个 bytes
        randomAccess.seek(0);
        randomAccess.read(a);
        print(a);

        //移动到 offset=3 的位置
        randomAccess.seek(3);
        System.out.println("当前位置: " + randomAccess.getFilePointer());
        //写入一个byte，直接覆盖原来的数据
        randomAccess.write(new byte[]{7});

        //从头读取 10 个 bytes
        randomAccess.seek(0);
        randomAccess.read(a);
        print(a);

        //seek 到 offset=2 处
        randomAccess.seek(2);
        a = new byte[3];
        //读 2 个字节到 a
        randomAccess.read(a, 0, 2);
        print(a);

        randomAccess.close();
        file.delete();
    }


    private static void print(byte[] array) {
        System.out.print("读取：");
        for (byte b : array) {
            System.out.print(b);
        }
        System.out.println();
    }
}
