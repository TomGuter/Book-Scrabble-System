package test;
import java.util.LinkedHashSet;

public class LRU implements  CacheReplacementPolicy{
    private final LinkedHashSet<String> linkedHashSetLRU;

    public LRU() {
        this.linkedHashSetLRU = new LinkedHashSet<>();
    }

    @Override
    public void add(String word) {
        if (linkedHashSetLRU.contains(word)) {
            linkedHashSetLRU.remove(word);
        }
            linkedHashSetLRU.add(word);

    }

    @Override
    public String remove() {
        if (linkedHashSetLRU.isEmpty()) {
            return "";
        }
        String lruWord = linkedHashSetLRU.iterator().next(); // getFirst() doesn't work on the checking system
        linkedHashSetLRU.remove(lruWord);
        return lruWord;
    }
}
