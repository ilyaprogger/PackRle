package Pack;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TrachukIlya
 * @version 1
 * <p>
 * Class, compression RLE(run-length encoding).
 * <p>
 * Fields:
 * <b>boolean</b> z
 * Packs text.
 *
 * <b>boolean</b> u
 * Unpacks the text.
 *
 * <b>String</b> outFile
 * File name to write (optional).
 *
 * <b>String</b> inputFile
 * Input File Name.
 */

public class PackRLE {
    private static final byte a = 127;
    private static final byte b = -128;
    private static DataOutputStream output;
    /**
     * -z
     * Flag wrap text.
     */
    @Option(name = "-z ", usage = "Packages a text file in the command line argument", forbids = "-u")
    private boolean z;
    /**
     * -u
     * Unpack text flag.
     */
    @Option(name = "-u", usage = "UnPackages a text file in the command line argument", forbids = "-z")
    private boolean u;
    /**
     * -out
     * Flag to indicate the file to write.
     */
    @Option(name = "-out", usage = "output file flag")
    private String outFile;
    /**
     * Input File Name.
     */
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
        //ArrayList for writing various characters.
        List<Byte> list = new ArrayList<Byte>();

        DataInputStream input = new DataInputStream(new FileInputStream("text"));
        output = new DataOutputStream(new FileOutputStream("RLEtext"));

        //Counter sequence of same characters
        byte count = 1;

        //Counter sequence of different characters
        short i = 0;
        byte different_count = 0;

        //Save the first byte for further comparison.
        byte symbol = input.readByte();

        //Comparing each byte of a character with the following.
        while (input.available() > 0) {

            //Save the next byte
            byte c = input.readByte();

            //Если байты различны уменьшаем на 1 different_count и записываем наш байт в ArrayList.
            if (symbol != c) {
                different_count--;
                if (count == 1) {
                    list.add(i, symbol);
                    i++;
                }


                /*
                   If the counter  different_count has reached its minimum value,
                   first write it down then by-byte assembled ArrayList,
                   then clear the ArrayList and reset the counter different_count.
                 */
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

            /*If the bytes are equal, but before that there was a
            sequence of different bytes, we write a different sequence
            after resetting ArrayList and a counter. Then we increase the count by 1
            because the bytes are the same and we check if count has reached the maximum value,
             if it has reached write and zero.
             */
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
                    dp(count, symbol);
                    count = 1;
                }
                different_count = 1;
                continue;
            }

            /*
            When a sequence of identical bytes is interrupted,
             write them down and reset the counter and assign the symbol new to the next byte.
             */
            if (count > 1) {
                dp(count, symbol);
            }
            symbol = c;
            count = 1;
        }
        /*
          If the text ends with a different sequence, write the collected ArrayList
         */
        if (different_count < 1) {
            output.writeByte(different_count - 1);
            for (int j = 0; j < i; j++) {
                output.writeByte(list.get(j));
            }
            output.writeByte(symbol);
        }

        /*
         Otherwise, if at the end there is a sequence of identical bytes,
          we write it down or if 1 byte after such a sequence we write -1
          and after the value of a byte.
         */
        else {
            if (count != 1) {
                dp(count, symbol);
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
        /*
         Unpacking, starting from the first byte, if it is greater than 0,
         write the same sequence using a loop, otherwise, if a byte is less than 0,
         we also write every byte in the loop without the first counter.
         */
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
