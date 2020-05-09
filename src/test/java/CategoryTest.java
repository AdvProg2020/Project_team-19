import model.Category;
import org.junit.Assert;
import org.junit.Test;

public class CategoryTest {

    @Test
    public void checkValidCategoryTest (  ) {
        Category food = new Category ( true , "Food" , null );
        Category sweet = new Category ( false , "Sweet" , food );
        Category bitter = new Category ( false , "Bitter" , food );
        Category sour = new Category ( false , "Sour" , food );
        Category aSweet = new Category ( false , "A" , sweet );
        Category bSweet = new Category ( false , "B" , sweet );
        Category aBitter = new Category ( false , "A" , bitter );
        Category cBitter = new Category ( false , "C" , bitter );
        Category bSour = new Category ( false , "B" , sour );
        Category cSour = new Category ( false , "C" , sour );

       // Assert.assertEquals ( false , Category.checkValidCategory ( "Food/Sweet/C" ) );

    }
}
