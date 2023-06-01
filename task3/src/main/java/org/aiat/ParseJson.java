package org.aiat;

import org.aiat.deserializer.TestsData;
import org.aiat.deserializer.ValuesData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ParseJson {
    public static List<TestsData> parseTests(String fileName) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(fileName));
            JSONArray jsonArray = (JSONArray) jsonObject.get("tests");
            List<TestsData> tests = new ArrayList<>();
            for (Object obj : jsonArray) {
                JSONObject testJson = (JSONObject) obj;
                TestsData testsData = parseTestsData(testJson);
                tests.add(testsData);
            }
            return tests;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static TestsData parseTestsData(JSONObject testJson) {
        long id = (long) testJson.get("id");
        String title = (String) testJson.get("title");
        String value = (String) testJson.get("value");

        List<TestsData> values = new ArrayList<>();
        if (testJson.containsKey("values")) {
            JSONArray valuesJsonArray = (JSONArray) testJson.get("values");
            for (Object valueObj : valuesJsonArray) {
                JSONObject valueJson = (JSONObject) valueObj;
                TestsData nestedTestsData = parseTestsData(valueJson);
                values.add(nestedTestsData);
            }
        }
        return new TestsData(id, title, value, values);
    }

    public static List<ValuesData> parseValues(String fileName) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(fileName));
            JSONArray jsonArray = (JSONArray) jsonObject.get("values");
            List<ValuesData> valuesData = new ArrayList<>();
            for (Object obj : jsonArray) {
                JSONObject valueJson = (JSONObject) obj;
                long id = (long) valueJson.get("id");
                String value = (String) valueJson.get("value");
                valuesData.add(new ValuesData(id, value));
            }
            return valuesData;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void generateReport(List<TestsData> tests, List<ValuesData> values, String reportFileName) {
        List<TestsData> testsWithValues = new ArrayList<>();
        for (TestsData test : tests) {
            TestsData testWithValue = fillTestValues(test, values);
            testsWithValues.add(testWithValue);
        }
        JSONObject reportJson = new JSONObject();
        JSONArray testsArray = new JSONArray();
        for (TestsData test : testsWithValues) {
            JSONObject testJson = new JSONObject();
            testJson.put("id", test.getId());
            testJson.put("title", test.getTitle());
            if (test.getValue() != null) {
                testJson.put("value", test.getValue());
            }
            if (!test.getValues().isEmpty()) {
                JSONArray nestedValuesArray = new JSONArray();
                for (TestsData nestedTest : test.getValues()) {
                    JSONObject nestedValueJson = new JSONObject();
                    nestedValueJson.put("id", nestedTest.getId());
                    nestedValueJson.put("title", nestedTest.getTitle());
                    if (nestedTest.getValue() != null) {
                        nestedValueJson.put("value", nestedTest.getValue());
                    }
                    if (!nestedTest.getValues().isEmpty()) {
                        JSONArray nestedNestedValuesArray = new JSONArray();
                        for (TestsData nestedNestedTest : nestedTest.getValues()) {
                            JSONObject nestedNestedValueJson = new JSONObject();
                            nestedNestedValueJson.put("id", nestedNestedTest.getId());
                            nestedNestedValueJson.put("title", nestedNestedTest.getTitle());
                            if (nestedNestedTest.getValue() != null) {
                                nestedNestedValueJson.put("value", nestedNestedTest.getValue());
                            }
                            if (!nestedNestedTest.getValues().isEmpty()) {
                                JSONArray nestedNestedNestedValuesArray = new JSONArray();
                                for (TestsData nestedNestedNestedTest : nestedNestedTest.getValues()) {
                                    JSONObject nestedNestedNestedValueJson = new JSONObject();
                                    nestedNestedNestedValueJson.put("id", nestedNestedNestedTest.getId());
                                    nestedNestedNestedValueJson.put("title", nestedNestedNestedTest.getTitle());
                                    if (nestedNestedNestedTest.getValue() != null) {
                                        nestedNestedNestedValueJson.put("value", nestedNestedNestedTest.getValue());
                                    }
                                    nestedNestedNestedValuesArray.add(nestedNestedNestedValueJson);
                                }
                                nestedNestedValueJson.put("values", nestedNestedNestedValuesArray);
                            }
                            nestedNestedValuesArray.add(nestedNestedValueJson);
                        }
                        nestedValueJson.put("values", nestedNestedValuesArray);
                    }
                    nestedValuesArray.add(nestedValueJson);
                }
                testJson.put("values", nestedValuesArray);
            }
            testsArray.add(testJson);
        }
        reportJson.put("tests", testsArray);

        try (FileWriter file = new FileWriter(reportFileName)) {
            file.write(reportJson.toJSONString());
            System.out.println("Файл " + reportFileName + " успешно создан.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static TestsData fillTestValues(TestsData test, List<ValuesData> values) {
        if (test.getValue() != null) {
            return test;
        } else if (test.getValues().isEmpty()) {
            for (ValuesData value : values) {
                if (value.getId() == test.getId()) {
                    return new TestsData(test.getId(), test.getTitle(), value.getValue(), new ArrayList<>());
                }
            }
            return test;
        } else {
            List<TestsData> nestedTests = new ArrayList<>();
            for (TestsData nestedTest : test.getValues()) {
                TestsData nestedTestWithValue = fillTestValues(nestedTest, values);
                nestedTests.add(nestedTestWithValue);
            }
            return new TestsData(test.getId(), test.getTitle(), null, nestedTests);
        }
    }
}
