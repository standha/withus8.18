package withus.dto;

public class ButtonCountSumDTO {
    private int alarm;
    private int blood;
    private int info;
    private int exercise;
    private int goal;
    private int helper;
    private int level;
    private int natrium_moisture;
    private int symptom;
    private int weight;
    private int chat;

    public ButtonCountSumDTO(int alarm, int blood, int info, int exercise, int goal, int helper, int level, int natrium_moisture, int symptom, int weight, int chat) {
        this.alarm = alarm;
        this.blood = blood;
        this.info = info;
        this.exercise = exercise;
        this.goal = goal;
        this.helper = helper;
        this.level = level;
        this.natrium_moisture = natrium_moisture;
        this.symptom = symptom;
        this.weight = weight;
        this.chat = chat;
    }

    public int getAlarm() {
        return alarm;
    }

    public int getBlood() {
        return blood;
    }

    public int getInfo() {
        return info;
    }

    public int getExercise() {
        return exercise;
    }

    public int getGoal() {
        return goal;
    }

    public int getHelper() {
        return helper;
    }

    public int getLevel() {
        return level;
    }

    public int getNatrium_moisture() {
        return natrium_moisture;
    }

    public int getSymptom() {
        return symptom;
    }

    public int getWeight() {
        return weight;
    }

    public int getChat() {
        return chat;
    }
}
