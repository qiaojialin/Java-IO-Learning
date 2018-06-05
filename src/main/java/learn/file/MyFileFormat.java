package learn.file;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyFileFormat {

    private RandomAccessFile raf;
    private FileOutputStream out;
    private ByteArrayOutputStream baos;
    private File file;
    private int count = 0;
    private boolean isWrite;

    public static void main(String[] args) throws IOException {
        // 写数据、关闭文件
        MyFileFormat myfile = new MyFileFormat("test.myfile", true);
        for(int i = 0; i < 20; i++) {
            myfile.write(i);
        }
        myfile.close();

        // 读数据、关闭文件
        myfile = new MyFileFormat("test.myfile", false);
        myfile.read();
        myfile.close();
    }

    // 初始化文件读写接口
    public MyFileFormat(String pathStr, boolean isWrite) throws FileNotFoundException {
        Path path = Paths.get(pathStr);
        file = path.toFile();
        this.isWrite = isWrite;
        if(isWrite) {
            out = new FileOutputStream(file, false);
            baos = new ByteArrayOutputStream();
        } else {
            raf = new RandomAccessFile(file, "r");
        }
    }

    // 写一个 int
    public boolean write(int value) {
        try {
            // 将数据缓存进 baos 中并计数
            baos.write(Utils.intToBytes(value));
            count++;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    // 写入 count 计数，即 metadata，最后关闭文件
    public void close() throws IOException {
        if(isWrite) {
            baos.write(Utils.intToBytes(count));
            baos.writeTo(out);
            out.close();
        } else {
            raf.close();
        }
    }

    // 先读 metadata，再读数据
    public void read() throws IOException {
        long offset = file.length() - 4;
        raf.seek(offset);
        byte[] bytes = new byte[4];
        int result = raf.read(bytes);
        count = Utils.bytesToInt(bytes);
        if(result != bytes.length)
            throw new IOException("not read enough bytes");

        raf.seek(0);
        for(int i = 0; i < count; i++) {
            result = raf.read(bytes);
            if(result != bytes.length)
                throw new IOException("not read enough bytes");
            int value = Utils.bytesToInt(bytes);
            System.out.print(value + " ");
        }
    }

}


class Utils {

    public static byte[] intToBytes(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static int bytesToInt(byte[] bytes) {
        int value = 0;
        // high bit to low
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }
}