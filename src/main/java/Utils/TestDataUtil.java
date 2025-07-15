package Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class TestDataUtil {

    public static Object[][] getTestDataFromJson() {
        InputStream inputStream = TestDataUtil.class.getClassLoader()
                .getResourceAsStream("testdata.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Map<String, String>> dataList = mapper.readValue(inputStream, new TypeReference<List<Map<String, String>>>() {});
            Object[][] testData = new Object[dataList.size()][6];

            for (int i = 0; i < dataList.size(); i++) {
                Map<String, String> data = dataList.get(i);
                testData[i][0] = data.get("firstName");
                testData[i][1] = data.get("lastName");
                testData[i][2] = data.get("phone");
                testData[i][3] = data.get("country");
                testData[i][4] = data.get("email");
                testData[i][5] = data.get("password");
            }

            return testData;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read test data from JSON file: " + e.getMessage(), e);
        }
    }
}
