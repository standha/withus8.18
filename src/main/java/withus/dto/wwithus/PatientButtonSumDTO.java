package withus.dto.wwithus;

public class PatientButtonSumDTO {
    private int goal;
    private int level;
    private int medicine;
    private int bloodPressure;
    private int exercise;
    private int weight;
    private int mindHealth;
    private int board;
    private int alarm;
    private int symptom;
    private int natriumMoisture;
    private int withusRang;
    private int diseaseInfo;
    private int helper;
    private int infoEdit;

    public PatientButtonSumDTO(int goal, int level, int medicine, int bloodPressure, int exercise, int weight,
                                 int mindHealth, int board, int alarm, int symptom, int natriumMoisture,
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
        this.symptom = symptom;
        this.natriumMoisture = natriumMoisture;
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

    public int getSymptom() {
        return symptom;
    }

    public int getNatriumMoisture() {
        return natriumMoisture;
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
