package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class DrawerTask extends Task{
    private final double A = -8;
    private final double B = 8;
    private GraphicsContext gc;
    private double N;
    @Override

    protected Object call() throws Exception {
        Random random = new Random();
        double x,y;
        double result;
        long k=0;
        BufferedImage bi = new BufferedImage((int)gc.getCanvas().getWidth(),(int) gc.getCanvas().getHeight(), BufferedImage.TYPE_INT_RGB);
        for ( int i  = 0 ; i < N ; i++) {
            x=A+(B-A)*random.nextDouble();
            y=A+(B-A)*random.nextDouble();

            if(N > 1000){
                if(Equation.calc(x,y)) {
                    double x1 = gc.getCanvas().getWidth() * (x-A) / (B-A);
                    double y1=  gc.getCanvas().getHeight() * (y-A) / (B-A);
                    bi.setRGB((int)x1,(int)(-y1+gc.getCanvas().getHeight()),Color.YELLOW.getRGB());
                    k++;
                }
                else {
                    double x1 = gc.getCanvas().getWidth() * (x-A) / (B-A);
                    double y1=  gc.getCanvas().getHeight() * (y-A) / (B-A);
                    bi.setRGB((int)x1,(int)(-y1+gc.getCanvas().getHeight()),Color.BLUE.getRGB());
                }
                if (i%5000==0) {
                    // gc.drawImage(SwingFXUtils.toFXImage(bi,null),0,0);
                    WritableImage tmp=SwingFXUtils.toFXImage(bi,null);
                    Platform.runLater(()->
                            gc.drawImage(tmp,0,0));
                }
            }
            if(N <= 1000){
                if(Equation.calc(x,y)) {
                    double x1 = gc.getCanvas().getWidth() * (x-A) / (B-A);
                    double y1=  gc.getCanvas().getHeight() * (y-A) / (B-A);
                    bi.setRGB((int)x1,(int)(-y1+gc.getCanvas().getHeight()),Color.YELLOW.getRGB());
                    k++;
                }
                else {
                    double x1 = gc.getCanvas().getWidth() * (x-A) / (B-A);
                    double y1=  gc.getCanvas().getHeight() * (y-A) / (B-A);
                    bi.setRGB((int)x1,(int)(-y1+gc.getCanvas().getHeight()),Color.BLUE.getRGB());
                }
                if (i%10==0) {
                    // gc.drawImage(SwingFXUtils.toFXImage(bi,null),0,0);
                    WritableImage tmp=SwingFXUtils.toFXImage(bi,null);
                    Platform.runLater(()->
                            gc.drawImage(tmp,0,0));
                }
            }
            updateProgress(i,N);
            if(isCancelled()) break;

        }

        result = k * (B-A)*(B-A)/N;
        return result;

    }

    public DrawerTask(GraphicsContext gc, double N) {
        this.gc=gc;
        this.N=N;
    }
}