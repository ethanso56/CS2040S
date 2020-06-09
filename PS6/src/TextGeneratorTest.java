import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextGeneratorTest {

    private PrintStream originalOutputStream;
    private final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();

    @Before
    public void setUpStream() {
        // redirect output to testOutput
        originalOutputStream = System.out;
        System.setOut(new PrintStream(testOutput));
    }

    @After
    public void cleanUpStream() {
        System.setOut(originalOutputStream);
    }


    @Test
    public void standardTest() {
        String[] argsInput = new String[3];
        argsInput[0] = "3";
        argsInput[1] = "15";
        argsInput[2] = "PS6Test.in";
        try {
            TextGenerator.setSeed(100);
            TextGenerator.main(argsInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(testOutput.toString().substring(0, 15));
        // n characters compared, since println might have been used resulting in newline character at the end
        assertEquals("abaadadsasdaslo", testOutput.toString().substring(0, 15));
    }

    @Test
    public void standardTest2() {
        String[] argsInput = new String[3];
        argsInput[0] = "5";
        argsInput[1] = "15";
        argsInput[2] = "PS6Test.in";
        try {
            TextGenerator.setSeed(100);
            TextGenerator.main(argsInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // n characters compared, since println might have been used resulting in newline character at the end
        assertEquals("abaadadsasdasnb", testOutput.toString().substring(0, 15));
    }
}
