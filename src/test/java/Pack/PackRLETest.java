package Pack;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class PackRLETest {

    @Test
    void test1() throws Exception {
        DataInputStream input = new DataInputStream(new FileInputStream("text"));
        DataOutputStream output = new DataOutputStream(new FileOutputStream("RlEtext"));
        DataInputStream input1 = new DataInputStream(new FileInputStream("RlEtext"));
        DataOutputStream output1 = new DataOutputStream(new FileOutputStream("atext"));
        PackRLE pack = new PackRLE();
        pack.rle(input,output);
        pack.antiRle(input1,output1);
        File file = new File("text");
        File file1 = new File("atext");
        assertTrue(FileUtils.contentEquals(file,file1));
        output.close();
        input.close();
        output1.close();
        input1.close();
    }
    @Test
    void test2() throws Exception {
        DataInputStream input = new DataInputStream(new FileInputStream("text1"));
        DataOutputStream output = new DataOutputStream(new FileOutputStream("RlEtext1"));
        DataInputStream input1 = new DataInputStream(new FileInputStream("RlEtext1"));
        DataOutputStream output1 = new DataOutputStream(new FileOutputStream("atext1"));
        PackRLE pack = new PackRLE();
        pack.rle(input,output);
        pack.antiRle(input1,output1);
        File file = new File("text1");
        File file1 = new File("atext1");
        assertTrue(FileUtils.contentEquals(file,file1));
        output.close();
        input.close();
        output1.close();
        input1.close();
    }
    @Test
    void test3() throws Exception {
        DataInputStream input = new DataInputStream(new FileInputStream("text2"));
        DataOutputStream output = new DataOutputStream(new FileOutputStream("RlEtext2"));
        DataInputStream input1 = new DataInputStream(new FileInputStream("RlEtext2"));
        DataOutputStream output1 = new DataOutputStream(new FileOutputStream("atext2"));
        PackRLE pack = new PackRLE();
        pack.rle(input,output);
        pack.antiRle(input1,output1);
        File file = new File("text2");
        File file1 = new File("atext2");
        assertTrue(FileUtils.contentEquals(file,file1));
        output.close();
        input.close();
        output1.close();
        input1.close();
    }
}