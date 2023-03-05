import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class cropper {
    public static void main(String[] args) {
        try{
            BufferedImage ogImg = ImageIO.read(
                    new File("C:/Users/acer/Desktop/gitg.jpg")
            );
            System.out.println("Original Dimensions: "+ogImg.getWidth()+"x"
            +ogImg.getHeight());
            int xset = ogImg.getWidth()*25/100;
            int yset = ogImg.getHeight()*25/100;


            BufferedImage SbImg = ogImg.getSubimage(xset,yset,1000,1000);
            System.out.println("Cropped Image: "+SbImg.getWidth()+"x"+ SbImg.getHeight());

            File outputFile = new File("C:/Users/acer/Desktop/gitgf.jpg");
            ImageIO.write(SbImg,"png",outputFile);

            System.out.println("Crop done");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
