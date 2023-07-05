package interview_tasks_paysafe.object_oriented.softuni.java_oop.task8_handling_exceptions;

import java.util.Scanner;

public class task1_week_days {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

      //  while (true){

            String input = scanner.nextLine();
            try{
                int getNumberWeek = Integer.parseInt(input);
                System.out.println("The day of week is " + getWeekDay(getNumberWeek));
                return;
            }catch (NumberFormatException e){
                System.out.println("invalid input . The number must between 1 - 7");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }finally {
                System.out.println("in finally block we can clean up code. Close db connections and so on.");
            }
       // }

    }
    public static String getWeekDay(int dayInput){
        return switch (dayInput) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> throw new IllegalArgumentException("Unexpected value: " + dayInput);
        };
    }
}
