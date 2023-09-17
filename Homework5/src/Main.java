import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Classifier classifier = new Classifier();
        classifier.tenFoldCrossValidation();
    }
}
