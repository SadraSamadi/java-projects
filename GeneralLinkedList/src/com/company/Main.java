package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GeneralList generalList = new GeneralList();
        label:
        while (true) {
            System.out.print("-> ");
            String input = scanner.nextLine().trim().replaceAll(",", " , ").replaceAll("\\s+", " ");
            String cmd = getCommand(input);
            String[] data = getData(input);
            switch (cmd) {
                case "add":
                    for (String aData : data) {
                        generalList.add(aData);
                    }
                    break;
                case "remove":
                    for (String aData : data) {
                        generalList.remove(aData);
                    }
                    break;
                case "exist":
                    for (String aData : data) {
                        System.out.println(generalList.exist(aData));
                    }
                    break;
                case "print":
                    generalList.printAll();
                    break;
                case "exit":
                    break label;
                default:
                    System.out.println("Invalid Command ! Try again ...");
            }
        }
    }

    private static String[] getData(String input) {
        int index = input.indexOf(' ');
        String s = index == -1 ? "" : input.substring(index + 1, input.length());
        return s.split(" , ");
    }

    private static String getCommand(String input) {
        String tmp = input.toLowerCase();
        if (tmp.startsWith("add")) {
            return "add";
        } else if (tmp.startsWith("remove")) {
            return "remove";
        } else if (tmp.startsWith("exist")) {
            return "exist";
        } else if (tmp.startsWith("print")) {
            return "print";
        } else if (tmp.startsWith("exit")) {
            return "exit";
        } else {
            return "";
        }
    }

}
