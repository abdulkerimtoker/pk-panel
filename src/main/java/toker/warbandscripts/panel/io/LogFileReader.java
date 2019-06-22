package toker.warbandscripts.panel.io;

import toker.warbandscripts.panel.util.StringUtils;

import java.io.*;

public class LogFileReader {

    private FileReader reader;
    private char[] buffer;
    private int charsInBuffer = 0;
    private int anchor = 0;

    public LogFileReader(File file, int bufferSize) throws FileNotFoundException {
        this.reader = new FileReader(file);
        this.buffer = new char[bufferSize];
    }

    public int readRelevant(char[] ret, char[] word) throws IOException {
        if (charsInBuffer <= 0) {
            charsInBuffer = reader.read(buffer, 0, buffer.length);
            if (charsInBuffer <= 0) {
                return -1;
            }
        }
        for (int i = anchor; i < charsInBuffer - 1; i++) {
            if (buffer[i] == '\n') {
                if (StringUtils.contains(buffer, anchor, i - 1, word, 0, word.length - 1)) {
                    System.arraycopy(buffer, anchor, ret, 0, i - anchor);

                    if (i < charsInBuffer - 1) {
                        System.arraycopy(buffer, i + 1, buffer, 0, charsInBuffer - i - 1);
                        charsInBuffer = charsInBuffer - i - 1;
                    } else {
                        charsInBuffer = 0;
                    }
                    int read = reader.read(buffer, charsInBuffer, buffer.length - charsInBuffer);
                    charsInBuffer = read <= 0 ? charsInBuffer : charsInBuffer + read;
                    anchor = 0;

                    return i - anchor;
                } else {
                    anchor = i + 1;
                }
            }
        }

        if (anchor < charsInBuffer) {
            System.arraycopy(buffer, anchor, buffer, 0, charsInBuffer - anchor);
            charsInBuffer -= anchor;
            int read = reader.read(buffer, charsInBuffer, buffer.length - charsInBuffer);
            charsInBuffer = read <= 0 ? charsInBuffer : charsInBuffer + read;
            anchor = 0;
        }

        return readRelevant(ret, word);
    }
}
