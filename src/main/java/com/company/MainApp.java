package com.company;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApp {

    public static void main(String[] args) {
        Board board = new Board();
        board.fillBoard();

        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("([A-J])([0-1]?[0-9])");
        Matcher matcher = pattern.matcher("");

        while (board.getShipsCount() > 0) {
            board.printBoard();

            System.out.println("Input field coordinates(i.e. A1): ");
            String location = scanner.nextLine();
            location = location.toUpperCase();
            matcher.reset(location);
            while (!matcher.reset(location).matches()) {
                System.out.println("Please input correct field indexes");
                location = scanner.nextLine();
            }

            int x = matcher.group(1).charAt(0) - 'A';
            int y = Integer.parseInt(matcher.group(2)) - 1;

            if (board.isOutside(y, x)) {
                System.out.println("You shoot outside the board");
                continue;
            }

            try {
                System.out.println(board.shoot(y, x));
            } catch (IllegalMoveException e) {
                e.getMessage();
            }
            System.out.println();
        }
    }
}
