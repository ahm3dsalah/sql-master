package exercise;

import lombok.*;

import java.util.List;

/**
 * TODO implement
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private List<String> columnNames;

    private List<List<String>> records;
}
