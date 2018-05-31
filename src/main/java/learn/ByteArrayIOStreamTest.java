package learn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


class MyByteArrayOutputStream extends ByteArrayOutputStream {

    public byte[] getBuf() {
        return super.buf;
    }
}

public class ByteArrayIOStreamTest {

    public static void main(String[] args) throws IOException {
        outputStreamTest();
        inputStreamTest();
    }

    private static void outputStreamTest() throws IOException {

        // 默认缓冲区大小 32 字节
        MyByteArrayOutputStream out = new MyByteArrayOutputStream();

        // 写入 32 个字节，此时
        for(int i = 0; i < 32; i++) {
            out.write(1);
        }
        System.out.println("当前缓冲区长度：" + out.getBuf().length + " 当前数据长度：" + out.size());

        // 写入 1 个字节，使所需容量为 33 个字节，大于原来 32 字节的容量
        out.write(2);
        System.out.println("当前缓冲区长度：" + out.getBuf().length + " 当前数据长度：" + out.size() + " 扩大到原来的两倍了");

        byte[] ret = out.toByteArray();
        print(ret);

        // 写入新数据，使其空间正好比原空间的 2 倍大 3 个字节
        out.write(new byte[out.getBuf().length*2-out.size()+3]);
        System.out.println("当前缓冲区长度：" + out.getBuf().length + " 当前数据长度：" + out.size() + " 扩大到需要的容量大小了");

    }


    private static void inputStreamTest() throws IOException {
        byte[] b1 = new byte[]{1,2,3,4};
        ByteArrayInputStream input = new ByteArrayInputStream(b1);

        System.out.println("剩余字节数: "+input.available());

        byte[] b2 = new byte[2];
        input.read(b2);
        print(b2);
        System.out.println("剩余字节数: " + input.available());

        input.read(b2);
        print(b2);
        System.out.println("剩余字节数: " + input.available() + " 没得读了");

        b2 = new byte[2];
        input.read(b2);
        print(b2);
    }

    private static void print(byte[] array) {
        System.out.print("长度: " + array.length + " ，内容：");
        for (byte b : array) {
            System.out.print(b);
        }
        System.out.println();
    }


}
