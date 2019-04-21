package Pack;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class PackRLETest {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    void rle() throws Exception {
        DataInputStream input = new DataInputStream(new FileInputStream("text"));
        DataInputStream output = new DataInputStream(new FileInputStream("atext"));
        while (input.available() > 0 && output.available() > 0)
            assertEquals(input.readByte(), output.readByte());
        output.close();
        input.close();
    }

    @Test
    void antiRle() {
    }
}