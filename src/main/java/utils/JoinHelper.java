package utils;

import java.util.ArrayList;
import java.util.List;

public class JoinHelper {

    public static int getColumnNumber(String columnName , List<String> columnNames) {
        for(int i = 0; i< columnNames.size(); i++) {
            if(columnNames.get(i).equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        // field not found
        throw new RuntimeException("field not found");
    }

    public static List<String> getAllColumns(List<String> leftTableColumnNames, List<String> rightColumnTableNames) {
        List<String> allColumn = new ArrayList<>();

        allColumn.addAll(leftTableColumnNames);
        allColumn.addAll(rightColumnTableNames);

        return allColumn;
    }
}
