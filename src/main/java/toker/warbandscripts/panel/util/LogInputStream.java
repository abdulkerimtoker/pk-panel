package toker.warbandscripts.panel.util;

import java.io.*;
import java.util.Arrays;

public class LogInputStream extends InputStream {

    private File file;
    private String[] words;
    private BufferedReader reader;
    private String row;
    private int anchor = 0;

    public LogInputStream(File file, String... words) {
        this.file = file;
        this.words = words;
        try {
            this.reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean readRow() throws IOException {
        this.row = reader.readLine();
        this.anchor = 0;
        return this.row != null;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        byte[] bytes = this.row.getBytes();

        if (b.length >= bytes.length) {
            System.arraycopy(bytes, 0, b, 0, bytes.length);
            return bytes.length;
        }
        else {
            System.arraycopy(bytes, 0, b, 0, b.length);
            this.anchor += b.length;
        }

        return super.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return super.read(b, off, len);
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        return super.readNBytes(len);
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        return super.readNBytes(b, off, len);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.reader.close();
        this.reader = new BufferedReader(new FileReader(file));
    }

    @Override
    public byte[] readAllBytes() throws IOException {
        return super.readAllBytes();
    }
}
