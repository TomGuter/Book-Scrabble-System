package test;

import test.Tile.Bag;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class MainTrain {

	public static void testBag() {
		Bag b = Tile.Bag.getBag();
		Bag b1 = Tile.Bag.getBag();
		if (b1 != b)
			System.out.println("your Bag in not a Singleton");

		int s = b.size();
		if (s != 98) {
			System.out.println("problem with size()");
		}
		int[] q0 = b.getQuantities();
		q0[0] += 1;
		int[] q1 = b.getQuantities();
		if (q0[0] != q1[0] + 1)
			System.out.println("getQuantities did not return a clone");

		for (int k = 0; k < 98; k++) {
			if (k == 97) {
				int x = 1;
			}
			int[] qs = b.getQuantities();
			Tile t = b.getRand();
			int i = t.letter - 'A';
			int[] qs1 = b.getQuantities();
			if (qs1[i] != qs[i] - 1)
				System.out.println("problem with getRand");

			b.put(t);
			b.put(t);
			b.put(t);

			if (b.getQuantities()[i] != qs[i])
				System.out.println("problem with put");
		}

		if (b.getTile('a') != null || b.getTile('$') != null || b.getTile('A') == null)
			System.out.println("your getTile is wrong");

	}

	private static Tile[] get(String s) {
		Tile[] ts = new Tile[s.length()];
		int i = 0;
		for (char c : s.toCharArray()) {
			ts[i] = Bag.getBag().getTile(c);
			i++;
		}
		return ts;
	}

	public static void testBoard() {
		Board b = Board.getBoard();
		if (b != Board.getBoard())
			System.out.println("board should be a Singleton");

		Bag bag = Bag.getBag();
		Tile[] ts = new Tile[10];
		for (int i = 0; i < ts.length; i++)
			ts[i] = bag.getRand();

		Word w0 = new Word(ts, 0, 6, true);
		Word w1 = new Word(ts, 7, 6, false);
		Word w2 = new Word(ts, 6, 7, true);
		Word w3 = new Word(ts, -1, 7, true);
		Word w4 = new Word(ts, 7, -1, false);
		Word w5 = new Word(ts, 0, 7, true);
		Word w6 = new Word(ts, 7, 0, false);

		// if(!b.boardLegal(w6))
		if (b.boardLegal(w0) || b.boardLegal(w1) || b.boardLegal(w2) || b.boardLegal(w3) || b.boardLegal(w4)
				|| !b.boardLegal(w5) || !b.boardLegal(w6))
			System.out.println("your boardLegal function is wrong");

		for (Tile t : ts)
			bag.put(t);

		Word horn = new Word(get("HORN"), 7, 5, false);
		if (b.tryPlaceWord(horn) != 14)
			System.out.println("problem in placeWord for 1st word");

		Word farm = new Word(get("FA_M"), 5, 7, true);
		if (b.tryPlaceWord(farm) != 9)
			System.out.println("problem in placeWord for 2ed word");

		Word paste = new Word(get("PASTE"), 9, 5, false);
		if (b.tryPlaceWord(paste) != 25)
			System.out.println("problem in placeWord for 3ed word");

		Word mob = new Word(get("_OB"), 8, 7, false);
		if (b.tryPlaceWord(mob) != 18)
			System.out.println("problem in placeWord for 4th word");

		Word bit = new Word(get("BIT"), 10, 4, false);
		if (b.tryPlaceWord(bit) != 22)
			System.out.println("problem in placeWord for 5th word");

		Word tea = new Word(get("TEA"), 9, 5, false);
		if (b.tryPlaceWord(tea) != 0)
			System.out.println("problem in placeWord for 6th word");

		Word is4 = new Word(get("_is4!"), 7, 5, true);
		if (b.tryPlaceWord(is4) != 0)
			System.out.println("problem in placeWord for 7th word");

		Word is = new Word(get("IS"), 14, 0, false);
		if (b.tryPlaceWord(is) != 0)
			System.out.println("problem in placeWord for 8th word");

		Word cabintet = new Word(get("CABINET"), 4, 4, false);
		if (b.tryPlaceWord(cabintet) != 55)
			System.out.println("problem in placeWord for 9th word");

		Word c = new Word(get("AABINET"), 4, 4, false);
		if (b.tryPlaceWord(c) != 0)
			System.out.println("problem in placeWord for 10th word");

		Word ca = new Word(get("CABINETTTTTTTTTTTTTTTTTTTT"), 7, 9, true);
		if (b.tryPlaceWord(ca) != 0)
			System.out.println("problem in placeWord for 11th word");

		Word h = new Word(get("__A"), 7, 9, false);
		if (b.tryPlaceWord(h) != 0)
			System.out.println("problem in placeWord for 12th word");

		Word h2 = new Word(get("A"), 7, 7, false);
		if (b.tryPlaceWord(h2) != 0)
			System.out.println("problem in placeWord for 13th word");

		Word h3 = new Word(get("ABC"), 10, 3, true);
		if (b.tryPlaceWord(h3) != 26)
			System.out.println("problem in placeWord for 14th word");

		Word abcde = new Word(get("___DE"), 10, 3, true);
		if (b.tryPlaceWord(abcde) != 22)
			System.out.println("problem in placeWord for 15th word");

		Word col = new Word(get("_OL"), 4, 4, true);
		if (b.tryPlaceWord(col) != 10)
			System.out.println("problem in placeWord for 16th word");

		Word tot = new Word(get("TO_"), 2, 10, true);
		if (b.tryPlaceWord(tot) != 6)
			System.out.println("problem in placeWord for 17th word");

		Word tot2 = new Word(get("TOC"), 2, 10, true);
		if (b.tryPlaceWord(tot2) != 0)
			System.out.println("problem in placeWord for 18th word");

		Word akaton = new Word(get("AKA_ON"), 2, 7, false);
		if (b.tryPlaceWord(akaton) != 30)
			System.out.println("problem in placeWord for 19th word");

		Word kon = new Word(get("KO_"), 0, 12, true);
		if (b.tryPlaceWord(kon) != 14)
			System.out.println("problem in placeWord for 20th word");

		Word kor = new Word(get("_OR"), 0, 12, false);
		if (b.tryPlaceWord(kor) != 21)
			System.out.println("problem in placeWord for 21th word");

		Word nond = new Word(get("NOND"), 2, 12, false);
		if (b.tryPlaceWord(nond) != 0)
			System.out.println("problem in placeWord for 22th word");

		Word rear = new Word(get("_EAR"), 0, 14, true);
		if (b.tryPlaceWord(rear) != 15)
			System.out.println("problem in placeWord for 23th word");

		Word eaek = new Word(get("EA_K"), 14, 1, false);
		if (b.tryPlaceWord(eaek) != 9)
			System.out.println("problem in placeWord for 24th word");

		Word caer = new Word(get("CAER"), 11, 0, true);
		if (b.tryPlaceWord(caer) != 57)
			System.out.println("problem in placeWord for 25th word");

		Word become = new Word(get("__COME"), 8, 9, true);
		if (b.tryPlaceWord(become) != 16)
			System.out.println("problem in placeWord for 26th word");

		Word sponge2 = new Word(get("SPONGES"), 14, 9, false);
		if (b.tryPlaceWord(sponge2) != 0)
			System.out.println("problem in placeWord for 27th word");

		Tile[][] tempB = b.getTiles();
		if (tempB[0][0] != null) {
			System.out.println("problem with Board getTiles");
		}

	}


//----------------------------------------Dictionary Tests--------------------------------------------------


	public static void testLRU() {
		CacheReplacementPolicy lru=new LRU();
		lru.add("a");
		lru.add("b");
		lru.add("c");
		lru.add("a");


		String removedWord1 = lru.remove();
		if (!removedWord1.equals("b")) {
			System.out.println("LRU test failed: Expected 'b' to be removed first, but got " + removedWord1);
		}

		String removedWord2 = lru.remove();
		if (!removedWord2.equals("c")) {
			System.out.println("LRU test failed: Expected 'c' to be removed second, but got " + removedWord2);
		}

		String removedWord3 = lru.remove();
		if (!removedWord3.equals("a")) {
			System.out.println("LRU test failed: Expected 'a' to be removed third, but got " + removedWord3);
		}

		// Test behavior after cache is empty
		String removedWord4 = lru.remove();
		if (!removedWord4.isEmpty()) {
			System.out.println("LRU test failed: Expected cache to be empty, but got " + removedWord4);
		}

	}

	public static void testLFU() {
		CacheReplacementPolicy lfu=new LFU();
		lfu.add("a");
		lfu.add("b");
		lfu.add("b");
		lfu.add("c");
		lfu.add("a");



		if(!lfu.remove().equals("c"))
			System.out.println("wrong implementation for LFU");



	}

	public static void testCacheManager() {
		CacheManager exists=new CacheManager(3, new LRU());
		boolean b = exists.query("a");
		b|=exists.query("b");
		b|=exists.query("c");

		if(b)
			System.out.println("wrong result for CacheManager first queries");

		exists.add("a");
		exists.add("b");
		exists.add("c");


		b=exists.query("a");
		b&=exists.query("b");
		b&=exists.query("c");

		if(!b)
			System.out.println("wrong result for CacheManager second queries");

		boolean bf = exists.query("d"); // false, LRU is "a"
		exists.add("d");
		boolean bt = exists.query("d"); // true
		bf|= exists.query("a"); // false
		exists.add("a");
		bt &= exists.query("a"); // true, LRU is "b"

		if(bf || ! bt)
			System.out.println("wrong result for CacheManager last queries");

	}

	public static void testBloomFilter() {
		BloomFilter bf =new BloomFilter(256,"MD5","SHA1");
		String[] words = "the quick brown fox jumps over the lazy dog".split(" ");
		for(String w : words)
			bf.add(w);

		if(!bf.toString().equals("0010010000000000000000000000000000000000000100000000001000000000000000000000010000000001000000000000000100000010100000000010000000000000000000000000000000110000100000000000000000000000000010000000001000000000000000000000000000000000000000000000000000001"))
			System.out.println("problem in the bit vector of the bloom filter");

		boolean found=true;
		for(String w : words)
			found &= bf.contains(w);

		if(!found)
			System.out.println("problem finding words that should exist in the bloom filter");

		found=false;
		for(String w : words)
			found |= bf.contains(w+"!");

		if(found)
			System.out.println("problem finding words that should not exist in the bloom filter");
	}

	public static void testIOSearch() throws Exception{
		String words1 = "the quick brown fox \n jumps over the lazy dog";
		String words2 = "A Bloom filter is a space efficient probabilistic data structure, \n conceived by Burton Howard Bloom in 1970";
		PrintWriter out = new PrintWriter(new FileWriter("text1.txt"));
		out.println(words1);
		out.close();
		out = new PrintWriter(new FileWriter("text2.txt"));
		out.println(words2);
		out.close();

		if(!IOSearcher.search("is", "text1.txt","text2.txt"))
			System.out.println("oyur IOsearch did not found a word (-5)");
		if(IOSearcher.search("cat", "text1.txt","text2.txt"))
			System.out.println("your IOsearch found a word that does not exist");
	}

	public static void testDictionary() {
		Dictionary d = new Dictionary("text1.txt","text2.txt");
		if(!d.query("is"))
			System.out.println("problem with dictionarry in query");
		if(!d.challenge("lazy"))
			System.out.println("problem with dictionarry in query");
	}




	//----------------------------------------Server Handle Tests--------------------------------------------------


	public static class ClientHandler1 implements ClientHandler{
		PrintWriter out;
		Scanner in;
		@Override
		public void handleClient(InputStream inFromclient, OutputStream outToClient) {
			out=new PrintWriter(outToClient);
			in=new Scanner(inFromclient);
			String text = in.next();
			out.println(new StringBuilder(text).reverse().toString());
			out.flush();
		}

		@Override
		public void close() {
			in.close();
			out.close();
		}

	}


	public static void client1(int port) throws Exception{
		Socket server=new Socket("localhost", port);
		Random r=new Random();
		String text = ""+(1000+r.nextInt(100000));
		String rev=new StringBuilder(text).reverse().toString();
		PrintWriter outToServer=new PrintWriter(server.getOutputStream());
		Scanner in=new Scanner(server.getInputStream());
		outToServer.println(text);
		outToServer.flush();
		String response=in.next();
		if(response==null || !response.equals(rev))
			System.out.println("problem getting the right response from your server, cannot continue the test");
		in.close();
		outToServer.println(text);
		outToServer.close();
		server.close();
	}

	public static void client2(int port) throws Exception{
		Socket server=new Socket("localhost", port);
		Random r=new Random();
		String text = ""+(1000+r.nextInt(100000));
		String rev=new StringBuilder(text).reverse().toString();
		PrintWriter outToServer=new PrintWriter(server.getOutputStream());
		Scanner in=new Scanner(server.getInputStream());
		outToServer.println(text);
		outToServer.flush();
		String response=in.next();
		if(response==null || !response.equals(rev))
			System.out.println("problem getting the right response from your server, cannot continue the test");
		in.close();
		outToServer.println(text);
		outToServer.close();
		server.close();
	}

	public static boolean testServer() {
		boolean ok=true;
		Random r=new Random();
		int port=6000+r.nextInt(1000);
		MyServer s=new MyServer(port, new ClientHandler1());
		int c = Thread.activeCount();
		s.start(); // runs in the background
		try {
			client1(port);
			client2(port);
		}catch(Exception e) {
			System.out.println("some exception was thrown while testing your server, cannot continue the test");
			ok=false;
		}
		s.close();

		try {Thread.sleep(2000);} catch (InterruptedException e) {}

		if (Thread.activeCount()!=c) {
			System.out.println("you have a thread open after calling close method");
			ok=false;
		}
		return ok;
	}


	public static String[] writeFile(String name) {
		Random r=new Random();
		String txt[]=new String[10];
		for(int i=0;i<txt.length;i++)
			txt[i]=""+(10000+r.nextInt(10000));

		try {
			PrintWriter out=new PrintWriter(new FileWriter(name));
			for(String s : txt) {
				out.print(s+" ");
			}
			out.println();
			out.close();
		}catch(Exception e) {}

		return txt;
	}

	public static void testDM() {
		String t1[]=writeFile("t1.txt");
		String t2[]=writeFile("t2.txt");
		String t3[]=writeFile("t3.txt");

		DictionaryManager dm=DictionaryManager.get();

		if(!dm.query("t1.txt","t2.txt",t2[4]))
			System.out.println("problem for Dictionary Manager query");
		if(!dm.query("t1.txt","t2.txt",t1[9]))
			System.out.println("problem for Dictionary Manager query");
		if(dm.query("t1.txt","t3.txt","2"+t3[2]))
			System.out.println("problem for Dictionary Manager query");
		if(dm.query("t2.txt","t3.txt","3"+t2[5]))
			System.out.println("problem for Dictionary Manager query");
		if(!dm.challenge("t1.txt","t2.txt","t3.txt",t3[2]))
			System.out.println("problem for Dictionary Manager challenge");
		if(dm.challenge("t2.txt","t3.txt","t1.txt","3"+t2[5]))
			System.out.println("problem for Dictionary Manager challenge");

		if(dm.getSize()!=3)
			System.out.println("wrong size for the Dictionary Manager");

	}

	public static void runClient(int port,String query,boolean result) {
		try {
			Socket server=new Socket("localhost",port);
			PrintWriter out=new PrintWriter(server.getOutputStream());
			Scanner in=new Scanner(server.getInputStream());
			out.println(query);
			out.flush();
			String res=in.next();
			if((result && !res.equals("true")) || (!result && !res.equals("false")))
				System.out.println("problem getting the right answer from the server");
			in.close();
			out.close();
			server.close();
		} catch (IOException e) {
			System.out.println("your code ran into an IOException");
		}
	}

	public static void testBSCH() {
		String s1[]=writeFile("s1.txt");
		String s2[]=writeFile("s2.txt");

		Random r=new Random();
		int port=6000+r.nextInt(1000);
		MyServer s=new MyServer(port, new BookScrabbleHandler());
		s.start();
		runClient(port, "Q,s1.txt,s2.txt,"+s1[1], true);
		runClient(port, "Q,s1.txt,s2.txt,"+s2[4], true);
		runClient(port, "Q,s1.txt,s2.txt,2"+s1[1], false);
		runClient(port, "Q,s1.txt,s2.txt,3"+s2[4], false);
		runClient(port, "C,s1.txt,s2.txt,"+s1[9], true);
		runClient(port, "C,s1.txt,s2.txt,#"+s2[1], false);
		s.close();
	}





	public static void main(String[] args) {
		testBag();
		testBoard();
		System.out.println("done successfully game rules design part");

		// Start testing the dictionary
		testLRU();
		testLFU();
		testCacheManager();
		testBloomFilter();
		try {
			testIOSearch();
		} catch(Exception e) {
			System.out.println("error");
		}
		testDictionary();
		System.out.println("done successfully dictionary methodology part");

		// Start testing the server with using dictionary part
		if(testServer()) {
			testDM();
			testBSCH();
		}
		System.out.println("done successfully server handle using dictionary methodology part");
	}

}
