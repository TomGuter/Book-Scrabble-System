package test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {
    public static boolean search(String word, String... fileNames) throws IOException {
        boolean wasFound = false;
        BufferedReader reader = null;
        for (String fileName : fileNames) {
            try  {
                reader = new BufferedReader(new FileReader(fileName));
                String currline = null;
                while ((currline = reader.readLine()) != null) {
                    if (currline.contains(word)) {
                        wasFound = true;
                        break;

                    }
                }
            }
            finally {
                if (reader != null) {
                    reader.close(); // close each file after checking if the word was found
                }
            }
            if (wasFound) {
                break;
            }
        }

        return wasFound;
    }
}
