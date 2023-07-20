package withus.dto.wwithus;

public class CaregiverButtonSumDTO {
    private int goal;
    private int level;
    private int medicine;
    private int bloodPressure;
    private int exercise;
    private int weight;
    private int mindHealth;
    private int board;
    private int alarm;
    private int familyObservation;
    private int dietManagement;
    private int withusRang;
    private int diseaseInfo;
    private int helper;
    private int infoEdit;

    public CaregiverButtonSumDTO(int goal, int level, int medicine, int bloodPressure, int exercise, int weight,
                                 int mindHealth, int board, int alarm, int familyObservation, int dietManagement,
                                 int withusRang, int diseaseInfo, int helper, int infoEdit) {
        this.goal = goal;
        this.level = level;
        this.medicine = medicine;
        this.bloodPressure = bloodPressure;
        this.exercise = exercise;
        this.weight = weight;
        this.mindHealth = mindHealth;
        this.board = board;
        this.alarm = alarm;
        this.familyObservation = familyObservation;
        this.dietManagement = dietManagement;
        this.withusRang = withusRang;
        this.diseaseInfo = diseaseInfo;
        this.helper = helper;
        this.infoEdit = infoEdit;
    }

    public int getGoal() {
        return goal;
    }

    public int getLevel() {
        return level;
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

    public int getWeight() {
        return weight;
    }

    public int getMindHealth() {
        return mindHealth;
    }

    public int getBoard() {
        return board;
    }

    public int getAlarm() {
        return alarm;
    }

    public int getFamilyObservation() {
        return familyObservation;
    }

    public int getDietManagement() {
        return dietManagement;
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

    public int getInfoEdit() {
        return infoEdit;
    }
}

