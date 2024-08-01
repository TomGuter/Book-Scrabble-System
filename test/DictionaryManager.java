package test;
import java.util.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class DictionaryManager {
    private static DictionaryManager dictionaryManagerRef;
    private final Map<String, Dictionary> dictionaryMap;
    private Dictionary dictionary;


    private DictionaryManager() {
        dictionaryMap = new LinkedHashMap<>();
        dictionary = null;
    }

    public static DictionaryManager get() {
        if (dictionaryManagerRef == null) {
            dictionaryManagerRef = new DictionaryManager();
        }
        return dictionaryManagerRef;
    }

    public boolean query(String... args) {
        String wordToSearch = args[args.length - 1];
        return getDictionaryForBook(wordToSearch, "Q", args);
    }

    public boolean challenge(String... args) {
        String wordToSearch = args[args.length - 1];
        return getDictionaryForBook(wordToSearch, "C", args);
    }

    private boolean getDictionaryForBook(String wordName, String ref, String... args) {
        boolean existingFlag = false;
        boolean isExist = false;

        for (int i = 0 ; i < args.length - 1 ; i++) { // make sure that all books are in the map
            String book = args[i];
            dictionaryMap.computeIfAbsent(book, Dictionary::new);
        }
        for (Map.Entry<String, Dictionary> entry : dictionaryMap.entrySet())  { // check for each dictionary if it contains the WordSearch
            dictionary = dictionaryMap.get(entry.getKey()); // point to the current dictionary on Map
            existingFlag = checkInDictionary(wordName, ref, isExist, existingFlag); // existingFlag will be updated to true only if there is one book which contains the searchWord

        }
        return existingFlag;

    }

    private boolean checkInDictionary(String wordName, String ref, boolean isExist, boolean existingFlag) {
        // check which one to send the word name
        if (Objects.equals(ref, "Q")) {
            isExist = dictionary.query(wordName);
        } else {
            isExist = dictionary.challenge(wordName);
        }

        if (isExist) { // only of isExist is true so update eixistingFlag but keep searching and update the cache
            existingFlag = true; // initialize the word is Exist but keep checking the all files, so we could update them
        }
        return existingFlag;
    }


    public int getSize() {
        return dictionaryMap.size();
    }

}
