package test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class CacheManager {
    private final int maxSize;
    //private final Map<String, HashSet<String>> cache;
    private final HashSet<String> cache;
    private  final CacheReplacementPolicy crp;

    public CacheManager(int maxSize, CacheReplacementPolicy crp) {
        this.maxSize = maxSize;
        this.cache = new HashSet<>();
        this.crp = crp;
    }

    public boolean query(String word) {
        return cache.contains(word);
    }

    public void add(String word) {
        crp.add(word);
        if(!cache.contains(word)) {
            cache.add(word);
        }

        if (cache.size() > maxSize) {
            String wordToRemove = crp.remove();
            cache.remove(wordToRemove);
        }

    }

}
