package withus.dto;

public class CaregiverMainButtonCountSumDTO {
    private int goal;
    private int level;
    private int withusRang;
    private int diseaseInfo;
    private int helper;
    private int medicine;
    private int bloodPressure;
    private int exercise;
    private int familyObservation;
    private int dietManagement;
    private int weight;
    private int mindHealth;
    private int alarm;
    private int board;

    public CaregiverMainButtonCountSumDTO(int goal, int level, int withusRang, int diseaseInfo, int helper, int medicine,
                                          int bloodPressure, int exercise, int familyObservation, int dietManagement, int weight, int mindHealth,
                                          int alarm, int board) {
        this.goal = goal;
        this.level = level;
        this.withusRang = withusRang;
        this.diseaseInfo = diseaseInfo;
        this.helper = helper;
        this.medicine = medicine;
        this.bloodPressure = bloodPressure;
        this.exercise = exercise;
        this.familyObservation = familyObservation;
        this.dietManagement = dietManagement;
        this.weight = weight;
        this.mindHealth = mindHealth;
        this.alarm = alarm;
        this.board = board;
    }

    public int getGoal() {
        return goal;
    }

    public int getLevel() {
        return level;
    }

    public int getWithusRang() {
        return withusRang;
    }

    public int getDiseaseInfo() {
        return diseaseInfo;
    }

    public int getHelper() {
        return helper;
    }

    public int getMedicine() {
        return medicine;
    }

    public int getBloodPressure() {
        return bloodPressure;
    }

    public int getExercise() {
        return exercise;
    }

    public int getFamilyObservation() {
        return familyObservation;
    }

    public int getDietManagement() {
        return dietManagement;
    }

    public int getWeight() {
        return weight;
    }

    public int getMindHealth() {
        return mindHealth;
    }

    public int getAlarm() {
        return alarm;
    }

    public int getBoard() {
        return board;
    }
}
