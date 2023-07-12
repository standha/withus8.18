package withus.dto;

public class PatientMainButtonCountSumDTO {
    private int alarm;
    private int blood;
    private int disease_info;
    private int exercise;
    private int goal;
    private int helper;
    private int level;
    private int natrium_moisture;
    private int symptom;
    private int weight;
    private int chat;
    private int medicine;
    private int mind_health;
    private int board;

    public PatientMainButtonCountSumDTO(int alarm, int blood, int disease_info, int exercise, int goal, int helper, int level, int natrium_moisture, int symptom,
                                        int weight, int chat, int medicine, int mind_health, int board) {
        this.alarm = alarm;
        this.blood = blood;
        this.disease_info = disease_info;
        this.exercise = exercise;
        this.goal = goal;
        this.helper = helper;
        this.level = level;
        this.natrium_moisture = natrium_moisture;
        this.symptom = symptom;
        this.weight = weight;
        this.chat = chat;
        this.medicine = medicine;
        this.mind_health = mind_health;
        this.board = board;
    }

    public int getMedicine(){return medicine;}

    public int getMind_health(){return mind_health;}

    public int getBoard(){return board;}
    public int getAlarm() {
        return alarm;
    }

    public int getBlood() {
        return blood;
    }

    public int getDisease_info() {
        return disease_info;
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
