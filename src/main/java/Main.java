import exercise.Sql;
import exercise.Table;

import java.io.*;
import java.net.URL;

public class Main {

    public static void main(String [] args) throws FileNotFoundException , IOException {
        Main main = new Main();

        File usersFile = main.getFileFromResources("users.csv");
        InputStream usersInputStream = new FileInputStream(usersFile);

        File purchasesFile = main.getFileFromResources("purchases.csv");
        InputStream purchasesInputStream = new FileInputStream(purchasesFile);

        Sql sql = new Sql();


        Table users = sql.init(usersInputStream);
        Table purchases = sql.init(purchasesInputStream);

        Table sortedUsers = sql.orderByDesc(users, "USER_ID");

        Table usersJoinPurchases = sql.join(users, purchases, "USER_ID", "USER_ID");

        System.out.println("DONE");



    }

    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }
}
