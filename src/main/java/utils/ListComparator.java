package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListComparator implements Comparator<List<String>> {

    private int sortingField;
    @Override
    public int compare(List<String> o1, List<String> o2) {
        return o1.get(sortingField).compareTo(o2.get(sortingField));
    }
}
