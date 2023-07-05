package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FerrariMain {
    public static void main(String []args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String getDriver = bufferedReader.readLine();
        if(!getDriver.isEmpty()) {
            try{
                    Car ferrari = new Ferrari(getDriver);
                    System.out.println(ferrari);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }else {
            throw new IllegalArgumentException("error");
        }

        System.out.println();
    }
}
