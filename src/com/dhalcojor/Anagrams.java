package com.dhalcojor;

/* Note: In real production code I wouldn't use wildcards to import classes */

import sun.util.resources.th.CalendarData_th;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Anagrams {

    private static final int MIN_ANAGRAM = 4;
    private SortedMap<String, List<String>> dictionary;

    void loadDictionaries(String path) {
        dictionary = new TreeMap<>();

        try {
            if (Files.list(Paths.get(path)).count() == 0) {
                throw new RuntimeException("Error: There are no files in selected path.");
            }

            if (Files.list(Paths.get(path)).noneMatch(Files::isReadable)) {
                throw new RuntimeException("Error: There are no readable files in selected path");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error: There are no files in selected path.");
        }

        try (Stream<Path> files = Files.list(Paths.get(path))) {
            files
                    .filter(file -> !file.getFileName().startsWith("."))
                    .filter(Files::isReadable)
                    .forEach(file -> {
                        try {
                            if (Files.lines(file).count() == 0) {
                                System.out.println("Warning: '" + file + "' is empty.");
                            } else {
                                System.out.println(file + " has " + Files.lines(file).count() + " lines");
                            }
                        } catch (UncheckedIOException e) {
                            System.out.println("Warning: '" + file + "' is not readable (binary?).");
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try (Stream<String> lines = Files.lines(file)) {
                            lines.forEach(line -> {
                                String key = sortString(line);
                                List<String> list = dictionary.containsKey(key) ?
                                        dictionary.get(key) : new ArrayList<>();
                                list.add(line);
                                dictionary.put(key, list);
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(("Error: " + e.getLocalizedMessage()));
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getLocalizedMessage());
        }

        System.out.println("Dictionary has " + dictionary.keySet().size() + " entries");
    }

    /**
     * Gets the longest anagram (if any) for a word or sentence.
     * @param sentence the sentence to get anagrams for
     * @return a list containing the anagrams or an empty list if there aren't any
     */
    List<String> getLongestAnagram(String sentence) {
        List<String> anagramKeys = new ArrayList<>();

        // Get from the dictionary only words or sentences longer than MIN_ANAGRAM
        List<String> workList =
                dictionary.keySet().stream().filter(key -> key.length() >= MIN_ANAGRAM).collect(Collectors.toList());

        // Look for anagrams in the work list
        String sorted = sortString(sentence);
        for (String sequence : workList) {
            if (isIn(sequence, sorted)) {
                anagramKeys.add(sequence);
            }
        }

        if (!anagramKeys.isEmpty()) {
            anagramKeys.sort((k1, k2) -> Integer.compare(k2.length(), k1.length()));
            List<String> anagrams = new ArrayList<>(dictionary.get(anagramKeys.get(0)));

            // In case the word or sentence we're looking for is in the resulting list
            anagrams.remove(sentence);

            // If the list is empty that means we only found one 'anagram' and it was the word itself. So, we use
            // the next anagram in the list.
            if (anagrams.isEmpty()) {
                anagrams = new ArrayList<>(dictionary.get(anagramKeys.get(1)));
            }

            return anagrams;
        }

        return anagramKeys;
    }

    /**
     * Transforms a string into a key by sorting all characters alphabetically and getting rid of spaces.
     * @param s The input string
     * @return the input string transformed into a key
     */
    private String sortString(String s) {
        char[] temp = s.toLowerCase().toCharArray();
        Arrays.sort(temp);

        return new String(temp).trim();
    }

    /**
     * Checks if s2 contains all the characters in s1.
     * @param s1 the string of characters to look for in s2.
     * @param s2 the string of characters to look into.
     * @return true if all the caracters in s1 are in s2.
     */
    private boolean isIn(String s1, String s2) {
        List<Character> list1 = toList(s1);
        List<Character> list2 = toList(s2);

        for (Character c : list1) {
            if (list2.contains(c)) {
                list2.remove(c);
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * Transforms a String into a list of Character objects.
     * @param s The string to transform.
     * @return a list of Character objects.
     */
    private List<Character> toList(String s) {
        List<Character> list = new ArrayList<>();

        for (Character c : s.toCharArray()) {
            list.add(c);
        }

        return list;
    }

}
