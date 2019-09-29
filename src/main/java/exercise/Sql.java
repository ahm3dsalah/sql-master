package exercise;

import utils.ListComparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static utils.JoinHelper.getAllColumns;
import static utils.JoinHelper.getColumnNumber;

/**
 * TODO implement
 */
public class Sql {

    private static final Logger log = Logger.getLogger(Sql.class.getName());

    public Table init(InputStream csvContent) {
        BufferedReader br = new BufferedReader(new InputStreamReader(csvContent, StandardCharsets.UTF_8));
        String line;
        try {
            line = br.readLine();
            // read column names
            List<String> columnNames = Arrays.asList(line.split(","));
            List<List<String>> records = new ArrayList<>();
            line = br.readLine();
            while (line != null) {
                String[] data = line.split(",");
                records.add(Arrays.asList(data));

                line = br.readLine();
            }
            br.close();

            return new Table(columnNames, records);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public Table orderByDesc(Table table, String columnName) {

        int fieldNumber = getColumnNumber(columnName, table.getColumnNames());
        List<List<String>> sortedRecords = new ArrayList(table.getRecords());
        sortedRecords.sort(Collections.reverseOrder(new ListComparator(fieldNumber)));

        return new Table(table.getColumnNames(), sortedRecords);
    }


    public Table join(Table left, Table right, String joinColumnTableLeft, String joinColumnTableRight) {
        List<String> allColumnNames = getAllColumns(left.getColumnNames(), right.getColumnNames());

        int leftTableJoinColumnNumber = getColumnNumber(joinColumnTableLeft, left.getColumnNames());
        int rightTableJointColumnNumber = getColumnNumber(joinColumnTableRight, right.getColumnNames());

        List<List<String>> joinedRecords = new ArrayList<>();
        // search for matching records
        for (int i = 0; i < left.getRecords().size(); i++) {
            for (int j = 0; j < right.getRecords().size(); j++) {
                if (left.getRecords().get(i).get(leftTableJoinColumnNumber)
                        .equals(right.getRecords().get(j).get(rightTableJointColumnNumber))) {


                    List<String> joinedRecord = new ArrayList<>();

                    joinedRecord.addAll(left.getRecords().get(i));
                    joinedRecord.addAll(right.getRecords().get(j));

                    joinedRecords.add(joinedRecord);
                }
            }
        }
        return new Table(allColumnNames, joinedRecords);
    }
}
