package com.pw.ui;

import com.pw.rb.ConsoleResource;

import java.util.ResourceBundle;

/**
 * Utility class for basic console writes.
 *
 */
public class ConsolePrinter {

    private static final ConsoleResource consoleResource = (ConsoleResource) ResourceBundle.getBundle(ConsoleResource.class.getName());
    public static final int LINE_LENGTH = 40;

    /**
     * Prints Welcome Message
     *
     */
    public static void printWelcomeMessage() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LINE_LENGTH; i++) {
            sb.append("*");
        }
        sb.append("\n*");
        appendSpaces(sb, consoleResource.getString("welcome"), 0);
        sb.append(consoleResource.getString("welcome"));
        appendSpaces(sb, consoleResource.getString("welcome"), 1);
        sb.append("*\n");
        for (int i = 0; i < LINE_LENGTH; i++) {
            sb.append("*");
        }
        System.out.println(sb.toString());
    }

    /**
     * Appends spaces to given StringBuilder according to given String
     * which will be centered @see getSpaceCountForAString
     *
     * @param sb        - the StringBuilder
     * @param str       - string for which spaces will be counted
     * @param trimCount - how many spaces should be trimmed
     */
    private static void appendSpaces(StringBuilder sb, String str, int trimCount) {
        for (int i = 0; i < getSpaceCountForAString(str) - trimCount; i++) {
            sb.append(" ");
        }
    }

    /**
     * Counts number of spaces to add before or after the String to
     * center it according to LINE_LENGTH
     *
     * @param str   - the string to be centered
     * @return      - number of spaces to center the string
     */
    private static int getSpaceCountForAString(String str) {
        return (LINE_LENGTH - str.length()) / 2;
    }

    /**
     * Print program usage.
     *
     */
    public static void printUsage() {
        System.out.println("java -jar GoEuroTest.jar [CITY_NAME] [FILE_PATH] [SEPARATOR]");
        System.out.println("\tCITY_NAME [Required] - the city to search for");
        System.out.println("\tFILE_PATH [Optional] - file path to write to");
        System.out.println("\tSEPARATOR [Optional] - CSV separator");
    }
}
