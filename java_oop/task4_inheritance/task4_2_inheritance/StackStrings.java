package interview_tasks_paysafe.object_oriented.softuni.java_oop.task4_inheritance.task4_2_inheritance;

import java.util.List;
// example of composition and delegation. Composition represents a bunch of attributes which could be instaces of other classes, data structures and so on.
public class StackStrings {
    private List<String> stringList; // delegation is called when StackString class delegate to some of its attributes(such as instance of classes or data structures)
    // to make some action such as stringList.add(element) or persion.makePerson.
    private int index;

    public StackStrings(List<String> stringList) {
        this.stringList = stringList;
    }
    public void push(String element){
        stringList.add(element);
        this.index++;
    }
    public String peek(){

        return this.stringList.get(this.index);
    }
    public String pop(){
        return this.stringList.remove(this.index--);
    }
}
