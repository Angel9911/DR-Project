package interview_tasks_paysafe.object_oriented.softuni.java_oop.abstraction.task2;

public enum Season {
    AUTUMN(1),
    SPRING(2),
    WINTER(3),
    SUMMER(4);

    private int seasonMultiplier;

    Season(int seasonMultiplier){
        this.seasonMultiplier = seasonMultiplier;
    }

    public int getSeasonMultiplier() {
        return seasonMultiplier;
    }

    public static Season fromString(String seasonString){

        //Season.valueOf(seasonString.toUpperCase()); -  another way to achieve this

        Season getSeason = switch (seasonString){
            case "Autumn" -> AUTUMN;
            case "SPRING" -> SPRING;
            case "WINTER" -> WINTER;
            default -> throw new IllegalArgumentException("unkwon season");
        };
        return getSeason;
    }

}
