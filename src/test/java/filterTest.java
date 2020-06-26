import org.junit.Test;
import view.FilteringMenu;

import java.util.ArrayList;

public class filterTest {
    @Test
    public void enumTest() {
        ArrayList<FilteringMenu.FilterName> filters = new ArrayList<>();
        filters.add(FilteringMenu.FilterName.NAME);
        filters.add(FilteringMenu.FilterName.PRICE);
        System.out.println(filters);
    }
}
