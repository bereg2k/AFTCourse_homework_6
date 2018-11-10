package other;

import java.util.HashMap;
import java.util.Map;

public class Debugger {

    private static Debugger INSTANCE = null;
    private Map<Integer, String> stepsTaken = new HashMap<>();
    private int currentStepNumber = 1;

    public static Debugger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Debugger();
        }
        return INSTANCE;
    }

    public void addSteps(String step) {
        stepsTaken.put(currentStepNumber, step);
        System.out.println(currentStepNumber + ". " + stepsTaken.get(currentStepNumber));
        currentStepNumber++;
    }

    public Map<Integer, String> getStepsTaken() {
        return stepsTaken;
    }
}
