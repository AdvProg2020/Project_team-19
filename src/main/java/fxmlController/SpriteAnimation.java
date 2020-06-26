package fxmlController;

    import javafx.animation.Animation;
    import javafx.animation.Interpolator;
    import javafx.animation.Transition;
    import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

    import java.awt.*;


public class SpriteAnimation extends Transition {

        private static int COLUMNS ;
        private static int COUNT   ;
        private static int OFFSET_X ;
        private static int OFFSET_Y ;
        private static int WIDTH ;
        private static int HEIGHT;
        private static ImageView imageView;

    public SpriteAnimation(ImageView image,int columns,int count,int offsetX,int offsetY,int width,int height,Duration duration) {
        HEIGHT = height;
        WIDTH = width;
        OFFSET_X = offsetX;
        OFFSET_Y = offsetY;
        COLUMNS = columns;
        COUNT = count;
        imageView = image;
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        imageView.setViewport(new Rectangle2D(OFFSET_X,OFFSET_Y,width,height));
    }

    public static void setOffsetX(int offsetX) {
        OFFSET_X = offsetX;
    }

    public static void setOffsetY(int offsetY) {
        OFFSET_Y = offsetY;
    }

    @Override
    protected void interpolate(double frac) {
        final int index = Math.min((int)Math.floor(COUNT*frac),COUNT-1);
        final int x = (index%COLUMNS)*WIDTH+OFFSET_X;
        final int y = (index/COLUMNS)*HEIGHT+OFFSET_Y;
        imageView.setViewport(new Rectangle2D(x,y,WIDTH,HEIGHT));
    }
}
