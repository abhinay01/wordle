import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class wordle {
    int MAX_WORD_NUMBER = 0;
    // String file_path="C:\\Users\\<userName>\\Downloads\\5_length_words.csv";
    String localDir = System.getProperty("user.dir");
    String file_path = localDir + "\\5_length_words.csv";
    final static int NO_OF_GUESS = 6;

    int getRandomNumber() {
        return (int) (Math.random() * (MAX_WORD_NUMBER));
    }

    public Map readWordFile() {
        Map<Integer, String> wordMap = new HashMap<Integer, String>();
        // ArrayList<String> words=new ArrayList<>();
        // System.out.println(file_path);
        try {
            Scanner sc = new Scanner(new File(file_path));
            sc.useDelimiter("\n");
            int i = 0;
            while (sc.hasNext()) // returns a boolean value
            {
                wordMap.put(i++, sc.next());
                // words.add(sc.next());
                // System.out.print(sc.next()); //find and returns the next complete token from
                // this scanner
            }
            MAX_WORD_NUMBER = i;
            // for(String j:words)
            // System.out.println(j);
        } catch (Exception e) {
            System.out.print(e);
            System.exit(0);
        }
        return wordMap;
    }

    boolean checkInputInWordMap(String gussedWord, Map<Integer, String> wordMap) {
        return wordMap.containsValue(gussedWord);
    }

    public int checkInput(String selectedWord, Map<Integer, String> wordMap) {

        int[] status = new int[5];
        Arrays.fill(status, -1);
        Scanner sc = new Scanner(System.in);
        String gussedWord = sc.next();
        if (gussedWord.length() > 5 || gussedWord.length() < 5)
            return 99;
        boolean cont = checkInputInWordMap(gussedWord, wordMap);
        if (cont == false)
            return -1;
        // boolean success=checkInput
        char selectedWordLetter[] = new char[5];
        char guessedWordLetter[] = new char[5];
        for (int letters = 0; letters < 5; letters++) {
            // System.out.println("letters: "+selectedWord.charAt(letters));
            selectedWordLetter[letters] = selectedWord.charAt(letters);
        }
        for (int letters = 0; letters < 5; letters++)
            guessedWordLetter[letters] = gussedWord.charAt(letters);
        for (int letters = 0; letters < 5; letters++) {
            char c = guessedWordLetter[letters];
            for (int j = 0; j < 5; j++) {
                if (c == selectedWordLetter[j]) {
                    // System.out.print("c : "+c+" l "+selectedWordLetter[j]);
                    status[letters] = 1;
                }
            }
        }
        // for(int letters=0;letters<5;letters++)
        // {
        // System.out.print("status : "+status[letters]);
        // }
        // System.out.println();
        for (int letters = 0; letters < 5; letters++) {
            // System.out.println("selected letter : "+selectedWordLetter[letters]+" guessed
            // word letter "+guessedWordLetter[letters]);
            if (selectedWordLetter[letters] == guessedWordLetter[letters])
                status[letters] = 0;
        }
        for (int letters = 0; letters < 5; letters++) {
            System.out.print(status[letters] + " ");
        }
        System.out.println();
        // int fail=game.checkInput();
        int fail = 0;
        for (int letters = 0; letters < 5; letters++) {
            if (status[letters] != 0)
                fail = 1;
        }
        // System.out.println("");
        return fail;
    }

    public static void main(String[] args) {
        System.out.println("Running Wordle");
        wordle game = new wordle();
        Map<Integer, String> wordMap = game.readWordFile();
        int randomNumber = game.getRandomNumber();
        String selectedWord = wordMap.get(randomNumber);
        for (int i = 0; i < NO_OF_GUESS; i++) {
            int fail = game.checkInput(selectedWord, wordMap);
            if (fail == 0) {
                System.out.println("Success in "+i+" attempt");
                break;
            } else if (fail == -1) {
                System.out.println("Word not in list");
                i--;
            } else if (fail == 99) {
                System.out.println("Word should be of length 5");
                i--;
            }
        }
        System.out.println(selectedWord);
    }
}