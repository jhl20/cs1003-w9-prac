import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * W09Practical.java.
 */
public class W09Practical {

    /**
     *PUBL is used to shorten publication to publ which is used when searching on dblp.
     */
    public static final int PUBL = 4;

    /**
     * Main method for the W09Practical.java file.
     * @param args      takes a string of arguments
     */
    public static void main(String[] args) {
        int search = 0;
        int query = 0;
        Path cache = null;
        for (int i = 0; i < args.length; i++) {
            if (!args[i].contains("--")) {
                switch (args[i - 1]) {
                    case "--search":
                        search = i;
                        break;
                    case "--query":
                        query = i;
                        break;
                    case "--cache":
                        break;
                    default:
                        System.out.println("Invalid value for " + args[i - 2] + ": " + args[i - 1]);
                        System.out.println("Malformed command line arguments.");
                        System.exit(1);
                }
            }
            if (args[i].equals("--cache")) {
                cache = Paths.get(args[i + 1]);
            }
        }

        if (query == 0) {
            System.out.println("Missing value for --query");
            System.out.println("Malformed command line arguments.");
            System.exit(1);
        }

        if (search == 0) {
            System.out.println("Missing value for --search");
            System.out.println("Malformed command line arguments.");
            System.exit(1);
        }

        if (!Files.exists(cache)) {
            System.out.println("Cache directory doesn't exist: " + cache);
            System.exit(1);
        }

        switch (args[search]) {
            case "venue":
                SearchVenue sv = new SearchVenue();
                sv.search(args, search, query, cache);
                break;
            case "publication":
                args[search] = args[search].substring(0, PUBL); // shorten
                SearchPublication sp = new SearchPublication();
                sp.search(args, search, query, cache);
                break;
            case "author":
                SearchAuthor sa = new SearchAuthor();
                sa.search(args, search, query, cache);
                break;
            default:
                break;
        }
    }
}

