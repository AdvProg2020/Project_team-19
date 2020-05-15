import org.junit.Test;
import model.wagu.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaguTest {

    @Test
    public void tableTest() {
        List<String> headersList = Arrays.asList("Product ID", "Name", "Price Before Off", "Price After Off");
        List < List <String>> rowsList = new ArrayList <> (  );
        for (int i = 0; i < 2; i++) {
            List <String> row = new ArrayList <> ( 4 );
            row.add ( "1234" );
            row.add ( "Apple" );
            row.add ( "5" + " $" );
            row.add ( "4" + " $" );
            rowsList.add ( row );
        }
        Board board = new Board(75);
        Table table = new Table(board, 75, headersList, rowsList);
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER,
                Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        String tableString = board.getPreview();
        System.out.println(tableString);
    }
}
