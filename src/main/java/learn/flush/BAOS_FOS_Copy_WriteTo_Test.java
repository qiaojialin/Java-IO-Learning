package learn.flush;

import java.io.*;

class MyRandomAccessFileOutputStream extends OutputStream {

    private RandomAccessFile raf;

    public MyRandomAccessFileOutputStream(RandomAccessFile raf) {
        this.raf = raf;
    }

    @Override
    public void write(int b) throws IOException {
        raf.write(b);
    }

    @Override
    public void write(byte b[]) throws IOException {
        raf.write(b);
    }

    public void seek(long pos) throws IOException {
        raf.seek(pos);
    }

    public long length() throws IOException {
        return raf.length();
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }

}

public class BAOS_FOS_Copy_WriteTo_Test {

    private static final String fileName = "src/main/resources/test.myfile";
    private static final File file = new File(fileName);
    private static int size = 1024*1024;
    private static byte[] b1 = new byte[size];
    private static ByteArrayOutputStream out = new ByteArrayOutputStream();

    public static void main(String[] args) throws IOException {
        out.write(b1);

        writeToFOS();

        copyToFOS();

        copyToRaf();

//        writeToMyRaf();
    }

    // 将 BAOS 中的数据写入 MyRandomAccessFileOutputStream
    private static void writeToMyRaf() throws IOException {
        if(file.exists())
            file.delete();
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        MyRandomAccessFileOutputStream myraf = new MyRandomAccessFileOutputStream(raf);

        long time = System.currentTimeMillis();
        out.writeTo(myraf);
        myraf.close();
        time = System.currentTimeMillis() - time;
        System.out.println("将 "+ size + " 个字节写入 MyRaf 耗时：" + time + "ms");
        file.delete();
    }

    // 将 BAOS 中的数据 copy 写入 RandomAccessFile
    private static void copyToRaf() throws IOException {
        if(file.exists())
            file.delete();
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        long time = System.currentTimeMillis();
        raf.write(out.toByteArray());
        raf.close();
        time = System.currentTimeMillis() - time;
        System.out.println("将 "+ size + " 个字节 copy 写入 Raf 耗时：" + time + "ms");
        file.delete();
    }

    // 将 BAOS 中的数据写入 FileOutputStream
    private static void writeToFOS() throws IOException {
        if(file.exists())
            file.delete();
        // 将 ByteArrayOutputStream 缓存的数据写入 FileOutputStream 中，即写入文件中
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);

        long time = System.currentTimeMillis();
        out.writeTo(fileOutputStream);
        fileOutputStream.close();
        time = System.currentTimeMillis() - time;
        System.out.println("将 "+ size + " 个字节写入 FOS 耗时：" + time + "ms");
        file.delete();
    }

    // 将 BAOS 中的数据 copy 写入 FileOutputStream
    private static void copyToFOS() throws IOException {
        if(file.exists())
            file.delete();
        // 将 ByteArrayOutputStream 缓存的数据写入 FileOutputStream 中，即写入文件中
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);

        long time = System.currentTimeMillis();
        fileOutputStream.write(out.toByteArray());
        fileOutputStream.close();
        time = System.currentTimeMillis() - time;
        System.out.println("将 "+ size + " 个字节 copy 写入 FOS 耗时：" + time + "ms");
        file.delete();
    }
}
