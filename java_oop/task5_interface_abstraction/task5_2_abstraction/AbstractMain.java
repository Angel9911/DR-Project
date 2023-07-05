package interview_tasks_paysafe.object_oriented.softuni.java_oop.task5_interface_abstraction.task5_2_abstraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractMain {
    public static void main(String []args) throws IOException {
        BulgarianAbstract bulgarianAbstract = new BulgarianAbstract("angel");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<Identifiable> identifiables = new ArrayList<>();
        String line  = bufferedReader.readLine();

        while (!line.equals("End")){
            String []tokens = line.split("\\s+");
            System.out.println(tokens[0]+tokens[1]);
            Identifiable identifiable = tokens.length == 2 ? new Robot(tokens[0],tokens[1]) :
                    new Citizen(tokens[0],Integer.parseInt(tokens[1]),tokens[2]);

            identifiables.add(identifiable);

            line = bufferedReader.readLine();
        }
        String fakeDigits = bufferedReader.readLine();
        System.out.println(bulgarianAbstract.sayHello());
        System.out.println(bulgarianAbstract.getName());
        System.out.println(identifiables.stream()
                .map(Identifiable::getId)
                .filter(id -> id.endsWith(fakeDigits))
                .collect(Collectors.joining(System.lineSeparator())));
        System.out.println();
    }
}
