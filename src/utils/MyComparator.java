package utils;

import elementit.Elementti;
import java.util.Comparator;

public class MyComparator implements Comparator<Elementti>{
    @Override
    public int compare(Elementti t, Elementti t1) {
        return t.toString().compareToIgnoreCase(t1.toString());
    }
}
