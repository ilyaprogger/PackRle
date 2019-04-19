package Pack;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PackRLE {
    private static final byte a = 127;
    private static final byte b = -128;
    private static DataOutputStream output;

    @Option(name = "-z ", usage = "Packages a text file in the command line argument", forbids = "-u")
    private boolean z;

    @Option(name = "-u", usage = "UnPackages a text file in the command line argument", forbids = "-z")
    private boolean u;

    @Option(name = "-out", usage = "output file flag")
    private String outFile;

    @Argument(index = 0)
    private String inputFile;

    void A() throws Exception {
        if (!z && !u) {
        }
        if (z) {
            rle();
        } else if (u) {
            antiRle();
        }
    }

    static void dp(byte a, byte b) throws Exception {
        output.writeByte(a);
        output.writeByte(b);
    }

    public static void rle() throws Exception {
        List<Byte> list = new ArrayList<Byte>();
        DataInputStream input = new DataInputStream(new FileInputStream("text"));
        output = new DataOutputStream(new FileOutputStream("RLEtext"));
        byte count = 1;
        short i = 0;
        byte different_count = 0;
        byte symbol = input.readByte();
        while (input.available() > 0) {
            byte c = input.readByte();
            if (symbol != c) {
                different_count--;
                if (count == 1) {
                    list.add(i, symbol);
                    i++;
                }
                if (different_count == b) {
                    output.writeByte(different_count);
                    for (int j = 0; j < i; j++) {
                        output.writeByte(list.get(j));
                    }
                    list.clear();
                    i = 0;
                    different_count = 0;
                }
            }
            if (symbol == c) {
                if (count == 1 && different_count != 0 && different_count != 1) {
                    output.writeByte(different_count);
                    for (int j = 0; j < i; j++) {
                        output.writeByte(list.get(j));
                    }
                    list.clear();
                    i = 0;
                }
                count++;
                if (count == a) {
                    dp(count,symbol);
                    count = 1;
                }
                different_count = 1;
                continue;
            }
            if (count > 1) {
                dp(count,symbol);
            }
            symbol = c;
            count = 1;
        }
        if (different_count < 1) {
            output.writeByte(different_count - 1);
            for (int j = 0; j < i; j++) {
                output.writeByte(list.get(j));
            }
            output.writeByte(symbol);
        } else {
            if (count != 1) {
                dp(count,symbol);
            } else {
                output.writeByte(-1);
                output.writeByte(symbol);
            }
        }
        output.close();
        input.close();
    }

    public static void antiRle() throws Exception {
        DataInputStream input = new DataInputStream(new FileInputStream("RlEtext"));
        DataOutputStream output = new DataOutputStream(new FileOutputStream("atext"));
        while (input.available() > 0) {
            byte c = input.readByte();
            if (c > 0) {
                byte equal = input.readByte();
                for (int i = 0; i < c; i++) {
                    output.writeByte(equal);
                }
            } else {
                for (int i = 0; i > c; i--) {
                    output.writeByte(input.readByte());
                }
            }
        }
        output.close();
        input.close();
    }
}
