# Anagrams
This is kata exercise. It's a simple console program that receives one parameter when executed. This parameter is a path to a folder containing one or more text files that will be used as dictionaries with one entry per line. Then, the program will ask the user for a word or a phrase to look anagrams for, using the dictionary previously loaded from the file(s). The program will answer with the longest anagram found.

The user can quit the program by typing a 'Q' as the word to look anagrams for.

## How to compile the code
**Note:** this guide assumes that you have already downloaded and configured a JDK. In case you haven't done that, click [this link](https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html).

Before running the program it's necessary to build it first:

1. Download the source code. You can either fork this repo or use the download link.
1. Open a terminal, and go to the src directory you just downloaded. It should contain this structure:
```
src
`-- com
    `-- dhalcojor
        |-- Main.java
        `-- Anagrams.java
```
1. Compile the java code with this command: `javac com/dhalcojor/*.java`

## How to run the program
Once you have compiled the two files containing the source code, run this command to execute the program: `java -cp . com.dhalcojor.Main [path]`

[path] is the mandatory parameter the program needs to run. It must be a path in your machine to a folder containing one or more text files. Each line in those files will be a word or phrase that will be loaded into the dictionary. Once the files are loaded, the program will ask the user to type a word or phrase and will look in the dictionary for an anagram for the user input. This anagram will be at least 4 characters long and doesn't need to have as many characters as the input.

## Have fun!
That's all, have fun with the program. In case you don't want to create the files for the dictionary yourself, you can find some examples in the folder `examples` of the repo.

You could run these two examples:

`java -cp . com.dhalcojor.Main ./english`

`java -cp . com.dhalcojor.Main ./spanish`