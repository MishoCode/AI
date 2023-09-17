import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Classifier {
    private final List<String> rows = new ArrayList<>();
    private final int[] yesCounter = new int[16];
    private final int[] noCounter = new int[16];

    public Classifier() throws IOException {
        loadData();
        calculateDataPerAttribute();
        processMissingValues();
    }

    private void loadData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("house-votes-84.data"));
        String line;
        while ((line = reader.readLine()) != null) {
            rows.add(line);
        }
    }

    public void calculateDataPerAttribute() {
        for (String row : rows) {
            String[] values = row.split(",");
            for (int i = 1; i < values.length; i++) {
                if (values[i].equalsIgnoreCase("y")) {
                    yesCounter[i - 1]++;
                } else if (values[i].equalsIgnoreCase("n")) {
                    noCounter[i - 1]++;
                }
            }
        }
    }

    public void processMissingValues() {
        for (int i = 0; i < rows.size(); i++) {
            String[] values = rows.get(i).split(",");

            for (int j = 1; j < values.length; j++) {
                if (values[j].equalsIgnoreCase("?")) {
                    if (yesCounter[j - 1] >= noCounter[j - 1]) {
                        values[j] = "y";
                        yesCounter[j - 1]++;
                    } else {
                        values[j] = "n";
                        noCounter[j - 1]++;
                    }
                }
            }

            String newRow = String.join(",", values);
            rows.set(i, newRow);
        }
    }

    public void tenFoldCrossValidation() {
        List<List<String>> groups = separateData();

        double accuracySum = 0;
        for (int i = 0; i < 10; i++) {
            List<String> learningData = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                if (j != i) {
                    learningData.addAll(groups.get(j));
                }
            }

            List<String> testData = groups.get(i);
            Model trainingModel = new Model(learningData, testData);
            trainingModel.makeModel();
            double accuracy = trainingModel.train();

            System.out.println("Accuracy of model " + (i + 1) + ": " + accuracy);

            accuracySum += accuracy;
        }

        System.out.println("Average accuracy: " + accuracySum / 10);
    }

    private List<List<String>> separateData() {
        Collections.shuffle(rows);

        List<List<String>> groups = new ArrayList<>();
        int separationPoint = rows.size() / 10;

        for (int i = 0; i < 10; i++) {
            groups.add(new ArrayList<>());
            for (int j = 0; j < separationPoint; j++) {
                groups.get(i).add(rows.get(i * separationPoint + j));
            }
        }

        return groups;
    }
}
