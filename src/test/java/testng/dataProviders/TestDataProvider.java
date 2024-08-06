package testng.dataProviders;

import org.testng.annotations.DataProvider;
import utils.ExcelUtil;
import utils.PropertiesReader;

import java.nio.file.Path;

public class TestDataProvider {
    @DataProvider(name = "testDataProvider")
    public Object[][] provider() {
        ExcelUtil excelUtil = new ExcelUtil();
        String[][] data = excelUtil.load(Path.of(PropertiesReader.readString("in.excel"))).read(null);
        return data;
    }
}
