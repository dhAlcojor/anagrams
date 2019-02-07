package com.dhalcojor;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    if (!checkMandatoryArgument(args)) {
            System.out.println("Error: Path argument is mandatory");
            System.exit(0);
        }

        Anagrams a = new Anagrams();

        System.out.println("Loading dictionaries...");

        long before = System.currentTimeMillis();
        a.loadDictionaries(args[0]);
        long elapsed = System.currentTimeMillis() - before;

        System.out.println("Dictionaries loaded after " + elapsed + " ms");

        String input;
        List<String> anagrams;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\n\nType a word or sentence to look for anagrams in the dictionaries.");
            System.out.println("Or type 'Q' to quit the program.");

            input = scanner.nextLine().trim();

            if (!input.isEmpty() && !input.equalsIgnoreCase("q")) {
                if (!input.matches("^\\w+( +\\w+)*$")) {
                    System.out.println("Error: Only letters allowed");
                    continue;
                }

                anagrams = a.getLongestAnagram(input);

                if (anagrams.isEmpty()) {
                    System.out.println("\nNo anagrams found for '" + input + "'");
                } else {
                    System.out.println("\nLongest anagram(s) found for '" + input + "'");

                    anagrams.forEach(System.out::println);
                }
            }
        } while (!input.equalsIgnoreCase("q"));

        System.out.println("\nBye, see you next time!");
    }

    /**
     * Checks for the existence of the mandatory parameter.
     * @param args the args array to check.
     * @return true if there's at least one parameter and it's not empty.
     */
    private static boolean checkMandatoryArgument(String[] args) {
        return args.length != 0 && !args[0].isEmpty();
    }

}
