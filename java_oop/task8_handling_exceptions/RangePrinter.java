package interview_tasks_paysafe.object_oriented.softuni.java_oop.task8_handling_exceptions;

import java.util.Scanner;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;

public class RangePrinter {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {

            try {

                String[] rangeNumbers = scanner.nextLine().split("\\s+");

                int start = Integer.parseInt(rangeNumbers[0]);
                int end = Integer.parseInt(rangeNumbers[1]);

                printRange(start, end);

                break;
            } catch (PatternSyntaxException patternExc) {
                System.out.println("Invalid input pattern for numbers");
            } catch (NumberFormatException numberFormatExc) {
                System.out.println("Please enter only valid integers");
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println(illegalArgumentException.getMessage());
            } finally {
                System.out.println("Finally block");
            }
        }

    }

    public static void printRange(int start, int end){
        try {
            validateRange(start,end);
        } catch (InvalidRangeException e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }

        IntStream.range(start,end)
                .forEach(System.out::println);
    }

    private static void validateRange(int start, int end) throws InvalidRangeException{
        if(start <= 1 || start >= end || end >=100){
            throw new InvalidRangeException("the range should be between 1 and 100");
        }
    }

}
