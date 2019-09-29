package exercise;

import org.junit.Assert;
import org.junit.Test;
import utils.JoinHelper;

import java.util.Arrays;
import java.util.List;

public class JoinHelperTest {

    @Test
    public void whenCallingGetColumnNumber_thenReturnCorrectColumnNumber(){

        //ARRANGE
        String targetColumnName = "USER_ID";
        List<String> columnNames = Arrays.asList("USER_ID", "USER_NAME","EMAIL");
        int expectedColumnNumber = 0;

        //ACT
        int columnNumber = JoinHelper.getColumnNumber(targetColumnName, columnNames);

        //ASSERT
        Assert.assertEquals(columnNumber, expectedColumnNumber);

    }

    @Test(expected = RuntimeException.class)
    public void whenCallingGetColumnNumberWithInvalidColumnName_thenFail(){

        //ARRANGE
        String targetColumnName = "NON_EXISTED_COLUMN_NAME";
        List<String> columnNames = Arrays.asList("USER_ID", "USER_NAME","EMAIL");


        //ACT
        JoinHelper.getColumnNumber(targetColumnName, columnNames);

        //ASSERT

        //should never reach here
        Assert.fail();

    }

    @Test
    public void whenCallingGetAllColumn_returnCorrectResult() {
        //ARRANGE
        List<String> leftTableColumnNames = Arrays.asList("USER_ID", "USER_NAME", "EMAIL");
        List<String> rightTableColumnNames = Arrays.asList("PURCHASE_ID", "ITEM_ID", "USER_ID");

        List<String> expectedResult = Arrays.asList("USER_ID", "USER_NAME", "EMAIL",
                "PURCHASE_ID", "ITEM_ID", "USER_ID");

        //ACT
        List<String> result = JoinHelper.getAllColumns(leftTableColumnNames, rightTableColumnNames);

        Assert.assertEquals(result, expectedResult);

    }
}
