package interview_tasks_paysafe.object_oriented.softuni.java_oop.task4_inheritance.task4_2_inheritance;

import java.util.ArrayList;
import java.util.Random;

public class RandomArrayList<E> extends ArrayList<E> {

    public E getRandomElement(){
        int start = 0;
        int end = super.size();
        Random random = new Random();
        int randIndex = random.nextInt(end);
        return remove(randIndex);
    }
}
