package org.aiat;

import org.aiat.deserializer.TestsData;
import org.aiat.deserializer.ValuesData;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String testsJson = args[0];
        String valuesJson= args[1];;
        List<TestsData> tests = ParseJson.parseTests(testsJson);

        List<ValuesData> values = ParseJson.parseValues(valuesJson);
        if (tests != null) {
            ParseJson.generateReport(tests, values, "report.json");
        }


    }
}