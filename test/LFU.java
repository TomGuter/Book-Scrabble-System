package test;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFU implements  CacheReplacementPolicy{
    //______________/// O(1) soloution____________________
    private final Map<String, Integer> FrequencyHashMap; // another map to store frequencies for each word
    private final Map<Integer, LinkedHashSet<String>> wordsMapByFrequencyLFU; // (frequency leads to the right linkedHashSet) -> the map store the word by the frequency of each word. the key is an intiger and the value is the word
    private int minFreqForAllWords;
    private int counterTimesByCurrWord;

    public LFU() {
        this.FrequencyHashMap = new HashMap<>();
        this.wordsMapByFrequencyLFU = new HashMap<>();
        this.minFreqForAllWords = Integer.MAX_VALUE;
        this.counterTimesByCurrWord = 0;
    }

    public void add(String word) {
        boolean currGotBigger = false;
        counterTimesByCurrWord = FrequencyHashMap.getOrDefault(word, 0) + 1;
        if (counterTimesByCurrWord > 1) { // means we have more than 1 by the same word
            currGotBigger = true;
        }
        FrequencyHashMap.put(word, counterTimesByCurrWord); // update the current frequency of the word

        minFreqForAllWords = Math.min(minFreqForAllWords, counterTimesByCurrWord);

        wordsMapByFrequencyLFU.computeIfAbsent(counterTimesByCurrWord, k -> new LinkedHashSet<>()).add(word); // check if wordsMapByFrequencyLFU in index counterTimesByCurrWord is existed. if it is so it creates a new element to the current hashList. if it's not it creates a new list by the same key of the current word frequency

        if (currGotBigger) { // means we have more than 1 by the same word
            wordsMapByFrequencyLFU.get(counterTimesByCurrWord - 1).remove(word);
            if (wordsMapByFrequencyLFU.get(counterTimesByCurrWord-1).isEmpty()) {
                wordsMapByFrequencyLFU.remove(counterTimesByCurrWord-1);
                minFreqForAllWords += 1; // the key by the minimum frequency word
            }
        }
    }

    public String remove() {
        if (wordsMapByFrequencyLFU.isEmpty()) {
            return "";
        }

        String lfuWord = wordsMapByFrequencyLFU.get(minFreqForAllWords).iterator().next(); // get the first word in the hashList by the index of the minFrequencies

        FrequencyHashMap.remove(lfuWord);
        wordsMapByFrequencyLFU.get(minFreqForAllWords).remove(lfuWord);

        if (wordsMapByFrequencyLFU.get(minFreqForAllWords).isEmpty()) {
            wordsMapByFrequencyLFU.remove(minFreqForAllWords);
            minFreqForAllWords += 1;
        }

        return lfuWord;
    }
    
    //________//O(n) soloution///___________
    
//    private final LinkedHashMap<String, Integer> wordsHashMapLFU; // LinkedHashMap instead of HashMap. because LinkedHashMap maintains the order of insertion
//    public LFU() {
//        this.wordsHashMapLFU = new LinkedHashMap<>();
//    }
//
//    @Override
//    public void add(String word) {
//        if (wordsHashMapLFU.containsKey(word)) {
//            int counter = wordsHashMapLFU.get(word);
//            wordsHashMapLFU.put(word, counter + 1); // update the number that the same word has appeared
//        }
//        else {
//            wordsHashMapLFU.put(word, 1);
//        }
//    }
//
//    @Override
//    public  String remove() {
//        if (wordsHashMapLFU.isEmpty()) {
//            return "";
//        }
//        String lfuWord = wordsHashMapLFU.entrySet().iterator().next().getKey();
//        int minCounts = wordsHashMapLFU.get(lfuWord);
//        for (Map.Entry<String, Integer> word: wordsHashMapLFU.entrySet()) {
//            if (word.getValue() < minCounts) {
//                lfuWord = word.getKey();
//                minCounts = word.getValue();
//            }
//        }
//        if (lfuWord != null) {
//            wordsHashMapLFU.remove(lfuWord);
//        }
//        return lfuWord;
//
//    }



}

