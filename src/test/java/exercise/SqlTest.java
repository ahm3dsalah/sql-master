package exercise;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlTest {

    @Test
    public void whenInitTableWithStream_thenReturnCorrectTable() throws FileNotFoundException {

        //ARRANGE
        InputStream usersInputStream = getInputStream("users.csv");
        Sql sql = new Sql();

        List<String> expectedColumnNames = Arrays.asList("USER_ID", "NAME", "EMAIL");
        List<String> expectedFirstRow = Arrays.asList("2", "manuel", "manuel@foo.de");
        List<String> expectedLaseRow = Arrays.asList("4", "lydia", "lydia@bar.de");
        int expectedRecordsCount = 5;


        //ACT
        Table users = sql.init(usersInputStream);

        //ASSERT
        Assert.assertEquals(users.getColumnNames(), expectedColumnNames);
        Assert.assertEquals(users.getRecords().get(0), expectedFirstRow);
        Assert.assertEquals(users.getRecords().get(4), expectedLaseRow);
        Assert.assertEquals(users.getRecords().size(), expectedRecordsCount);
    }

    @Test
    public void whenInitTableWithStreamAndThenOrderCallOrderBy_thenReturnCorrectOrder() throws FileNotFoundException {

        //ARRANGE
        InputStream usersInputStream = getInputStream("users.csv");
        Sql sql = new Sql();

        List<String> expectedColumnNames = Arrays.asList("USER_ID", "NAME", "EMAIL");

        List<String> expectedFirstRow = Arrays.asList("5", "paul", "paul@foo.de");
        List<String> expectedLastRow =  Arrays.asList("1", "andre", "andre@bar.de");
        int expectedRecordsCount = 5;


        //ACT
        Table users = sql.init(usersInputStream);
        Table sortedUsers = sql.orderByDesc(users, "USER_ID");

        //ASSERT
        Assert.assertEquals(sortedUsers.getColumnNames(), expectedColumnNames);
        Assert.assertEquals(sortedUsers.getRecords().get(0), expectedFirstRow);
        Assert.assertEquals(sortedUsers.getRecords().get(4), expectedLastRow);
        Assert.assertEquals(sortedUsers.getRecords().size(), expectedRecordsCount);
    }

    @Test
    public void whenJoinTwoTables_thenReturnCorrectResult(){
        //ARRANGE

        // users table
        List<String> usersColumnNames = Arrays.asList("USER_ID", "EMAIL");
        List<String> usersRow1 = Arrays.asList("1", "user1@foo.com");
        List<String> usersRow2 = Arrays.asList("2", "user2@foo.com");
        List<List<String>> userRecords = new ArrayList<>(2);
        userRecords.add(usersRow1);
        userRecords.add(usersRow2);

        Table users = new Table(usersColumnNames, userRecords);

        // purchase table

        List<String> purchaseColumnNames = Arrays.asList("USER_ID", "ITEM_NAME");
        List<String> purchaseRow1 = Arrays.asList("1", "ITEM_NAME_1");
        List<String> purchaseRow2 = Arrays.asList("1", "ITEM_NAME_2");
        List<String> purchaseRow3 = Arrays.asList("2", "ITEM_NAME_3");
        List<List<String>> purchaseRecords = new ArrayList<>(3);
        purchaseRecords.add(purchaseRow1);
        purchaseRecords.add(purchaseRow2);
        purchaseRecords.add(purchaseRow3);

        List<String> expectedColumnNames = Arrays.asList("USER_ID", "EMAIL", "USER_ID", "ITEM_NAME");
        String joinColumnName = "USER_ID";

        List<String> expectedFirstRow = Arrays.asList("1", "user1@foo.com", "1", "ITEM_NAME_1");
        List<String> expectedLastRow =  Arrays.asList("2", "user2@foo.com", "2", "ITEM_NAME_3");

        Table purchase = new Table(purchaseColumnNames, purchaseRecords);

        //ACT
        Sql sql = new Sql();
        Table resultTable = sql.join(users, purchase, joinColumnName, joinColumnName);

        //ASSERT
        Assert.assertEquals(resultTable.getColumnNames(), expectedColumnNames);
        Assert.assertEquals(3, resultTable.getRecords().size());
        Assert.assertEquals(expectedFirstRow, resultTable.getRecords().get(0));
        Assert.assertEquals(expectedLastRow, resultTable.getRecords().get(2));
    }

    @Test
    public void whenJoinTwoTablesWithNoMatch_thenReturnEmptyRecords(){
        //ARRANGE

        // users table
        List<String> usersColumnNames = Arrays.asList("USER_ID", "EMAIL");
        List<String> usersRow1 = Arrays.asList("1", "user1@foo.com");
        List<String> usersRow2 = Arrays.asList("2", "user2@foo.com");
        List<List<String>> userRecords = new ArrayList<>(2);
        userRecords.add(usersRow1);
        userRecords.add(usersRow2);

        Table users = new Table(usersColumnNames, userRecords);

        // purchase table

        List<String> purchaseColumnNames = Arrays.asList("USER_ID", "ITEM_NAME");
        List<String> purchaseRow1 = Arrays.asList("100", "ITEM_NAME_1");
        List<String> purchaseRow2 = Arrays.asList("100", "ITEM_NAME_2");
        List<String> purchaseRow3 = Arrays.asList("200", "ITEM_NAME_3");
        List<List<String>> purchaseRecords = new ArrayList<>(3);
        purchaseRecords.add(purchaseRow1);
        purchaseRecords.add(purchaseRow2);
        purchaseRecords.add(purchaseRow3);

        List<String> expectedColumnNames = Arrays.asList("USER_ID", "EMAIL", "USER_ID", "ITEM_NAME");
        String joinColumnName = "USER_ID";


        Table purchase = new Table(purchaseColumnNames, purchaseRecords);

        //ACT
        Sql sql = new Sql();
        Table resultTable = sql.join(users, purchase, joinColumnName, joinColumnName);

        //ASSERT
        Assert.assertEquals(resultTable.getColumnNames(), expectedColumnNames);
        Assert.assertEquals(0, resultTable.getRecords().size());
    }


    private InputStream getInputStream(String fileName) throws FileNotFoundException{
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return  new FileInputStream(file);
    }

}
