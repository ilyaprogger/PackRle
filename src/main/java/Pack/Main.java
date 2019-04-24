package Pack;
import org.kohsuke.args4j.CmdLineParser;

public class Main {
    public static void main(String[] args) {
        PackRLE pack = new PackRLE();
        CmdLineParser parser = new CmdLineParser(pack);
        try {
            parser.parseArgument(args);
            pack.main();
            System.out.println("Все работает)");
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }
}