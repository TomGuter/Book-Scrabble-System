package test;
import java.io.IOException;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Dictionary {
    private final CacheManager existingWords;
    private final CacheManager nonExistingWords;
    private final BloomFilter bloomFilter;
    private final String[] filenames;


    public Dictionary(String... fileNames)  {
        this.filenames = fileNames;
        existingWords = new CacheManager(400, new LRU());
        nonExistingWords = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA-1");

        for (String fileName : fileNames) {
            List<String> words = readWordsFromFile(fileName);
            for (String word : words) {
                bloomFilter.add(word);
            }
        }

    }

    public List<String> readWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { // the file is automatically getting closed after it's done
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                String[] lineWords = currLine.split(" "); // split by whitespace
                for (String word : lineWords) {
                    words.add(word);
                }
            }
        } catch (IOException ignored) {
        }
        return words;
    }

    public boolean query(String word) {
        if (existingWords.query(word)) {
            return true;
        }
        else if (nonExistingWords.query(word)) {
            return false;
        }
        else {
            boolean exists = bloomFilter.contains(word);
            if (exists) {
                existingWords.add(word);
            }
            else {
                nonExistingWords.add(word);
            }
            return exists;
        }
    }

    public boolean challenge(String word) {
        try {
            boolean exists = IOSearcher.search(word, filenames);
            if (exists) {
                existingWords.add(word);

            }
            else {
                nonExistingWords.add(word);
            }
            return exists;
        }
        catch (IOException e) {
            return false;
        }
    }


}
