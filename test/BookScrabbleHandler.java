package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class BookScrabbleHandler implements ClientHandler {
    PrintWriter out;
    Scanner in;
    private final DictionaryManager dictionaryManager;

    public BookScrabbleHandler() {
        this.dictionaryManager = DictionaryManager.get();
    }

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        in = new Scanner(inFromClient);
        out = new PrintWriter(outToClient);

        if (in.hasNext()) {

            String[] inputArgs = in.next().split(",");
            String[] books = Arrays.copyOfRange(inputArgs, 1, inputArgs.length);
            if (dictionaryManager == null) {
                return;
            }

            boolean result;
            if (inputArgs[0].equals("Q")) {
                result = dictionaryManager.query(books);

            } else if (inputArgs[0].equals("C")) {
                result = dictionaryManager.challenge(books);
            } else {
                close();
                return;
            }

            out.println(result + "\n");
            out.flush();
            close();
        }

    }

    @Override
    public void close() {
        in.close();
        out.close();
    }
}


