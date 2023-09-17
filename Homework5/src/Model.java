import java.util.List;

public class Model {
    private final List<String> learningData;
    private final List<String> testData;
    private final int[] republicansYesCounter = new int[16];
    private final int[] republicansNoCounter = new int[16];
    private final int[] democratsYesCounter = new int[16];
    private final int[] democratsNoCounter = new int[16];
    private int republicansCount = 0;
    private int democratsCount = 0;

    public Model(List<String> learningData, List<String> testData) {
        this.learningData = learningData;
        this.testData = testData;
    }

    public void makeModel() {
        for (String row : learningData) {
            String[] values = row.split(",");
            String className = values[0];
            boolean isRepublican = false;
            if (className.equalsIgnoreCase("republican")) {
                isRepublican = true;
                republicansCount++;
            } else {
                democratsCount++;
            }

            for (int i = 1; i < values.length; i++) {
                if (values[i].equalsIgnoreCase("y") && isRepublican) {
                    republicansYesCounter[i - 1]++;
                } else if (values[i].equalsIgnoreCase("n") && isRepublican) {
                    republicansNoCounter[i - 1]++;
                } else if (values[i].equalsIgnoreCase("y") && !isRepublican) {
                    democratsYesCounter[i - 1]++;
                } else if (values[i].equalsIgnoreCase("n") && !isRepublican) {
                    democratsNoCounter[i - 1]++;
                }
            }
        }
    }

    public double train() {
        int correctPredictions = 0;

        for (String row : testData) {
            double republicanProb = probabilityForRepublican(row);
            double democratProb = probabilityForDemocrat(row);

            if (republicanProb > democratProb && row.startsWith("republican")) {
                correctPredictions++;
            } else if (republicanProb < democratProb && row.startsWith("democrat")) {
                correctPredictions++;
            }
        }

        return ((double) correctPredictions / testData.size()) * 100;
    }

    private double probabilityForRepublican(String row) {
        double result = Math.log(laplaceSmoothing(republicansCount, learningData.size()));
        String[] values = row.split(",");

        for (int i = 1; i < values.length; i++) {
            result += Math.log(probabilityForRepublicanAttribute(i - 1, values[i]));
        }

        return result;
    }

    private double probabilityForDemocrat(String row) {
        double result = Math.log(laplaceSmoothing(democratsCount, learningData.size()));
        String[] values = row.split(",");

        for (int i = 1; i < values.length; i++) {
            result += Math.log(probabilityForDemocratAttribute(i - 1, values[i]));
        }

        return result;
    }

    private double probabilityForRepublicanAttribute(int index, String value) {
        if (value.equalsIgnoreCase("y")) {
            return laplaceSmoothing(republicansYesCounter[index], republicansCount);
        } else {
            return laplaceSmoothing(republicansNoCounter[index], republicansCount);
        }
    }

    private double probabilityForDemocratAttribute(int index, String value) {
        if (value.equalsIgnoreCase("y")) {
            return laplaceSmoothing(democratsYesCounter[index], democratsCount);
        } else {
            return laplaceSmoothing(democratsNoCounter[index], democratsCount);
        }
    }

    private double laplaceSmoothing(int x, int y) {
        return (double) (x + 1) / (y + 2);
    }

}
