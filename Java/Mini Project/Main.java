import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import javax.imageio.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Image Cropping/Compressing app");
        System.out.println("Enter 1. To Opt for Image Cropping\nEnter 2. To Opt for Image Compressing" +
                "\nEnter 3. To Display Image\nEnter 0. To Quit the App");
        System.out.println("---------------------------------------------------------------------------");

        Scanner sc = new Scanner(System.in);
        boolean Eflag = false; //for exception
        int choice = 0; // default choice
        try{
            choice = sc.nextInt();
        }
        catch (InputMismatchException IMM) {
            System.out.println(IMM);
            Eflag = true;
        }
        if(Eflag) {
            sc.nextLine();
            System.out.println("last chance to enter correct choice");
            try {
                choice = (Eflag)?sc.nextInt():0;
            }
            catch(Exception e) {
                System.out.println(e);
                System.out.println("too many wrong inputs");
                System.exit(0);
            }
        }

        while( choice!=0) {
            switch (choice) {
                case 1: {
                    System.out.println("1. Image Cropper ");
                    Scanner crop = new Scanner(System.in);
                    System.out.println("Enter the Image Address in following format [Users/Username/Desktop/filename.jpg]");
                    String loc = "fallback text";//for exceptions
                    Eflag = false;
                    try{
                        loc = crop.nextLine();
                        loc = loc.trim();
                    }
                    catch (InputMismatchException IMM) {
                        System.out.println(IMM);
                        Eflag = true;
                    }
                    sc.nextLine();
                    if(Eflag) {
                        System.out.println("last chance to enter correct address");
                        loc = sc.nextLine();
                    }
                    System.out.println("Enter the name of Final Image with .jpg extension");
                    String fimage = crop.nextLine();
                    System.out.println("Enter the new image dimensions you want in [Length Height] Format");
                    int length = crop.nextInt();
                    int height = crop.nextInt();
                    if(loc .equals("fallback text")) {
                        System.out.println("too many wrong inputs");
                        System.exit(0);
                    }
                    cropper c1 = new cropper(loc, fimage, length, height);
                    c1.photoCropper();
                    break;
                }
                case 2: {
                    System.out.println("2. Image Compressor ");
                    Scanner compress = new Scanner(System.in);

                    System.out.println("Enter the Image Address in following format [Users/Username/Desktop/filename.jpg]");
                    String loc = compress.nextLine();
                    loc = loc.trim();

                    System.out.println("Enter the name of Final Image without extension");
                    String finimage = compress.nextLine();

                    compressImage(loc, finimage);
                    break;
                }
                case 3:
                {
                    System.out.println("Image Display");
                    Scanner Display = new Scanner(System.in);

                    System.out.println("Enter the Image Address in following format [Users/Username/Desktop/filename.jpg]");
                    String loc = Display.nextLine();
                    loc = loc.trim();

                    try{
                        displayImage(loc);
                    }
                    catch(Exception o)
                    {
                        System.out.println(o);
                    }
                    break;
                }
                default: {
                    System.out.println("Wrong Choice");
                    break;
                }
            }
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("Enter 1. To Opt for Image Cropping\nEnter 2. To Opt for Image Compressing" +
                    "\nEnter 3. To Display Image\nEnter 0. To Quit the App");
            System.out.println("---------------------------------------------------------------------------");
            choice = sc.nextInt();
        }
    }

    static String changeAddress(String OgAddress, String FinalName)
    {
        int i = OgAddress.length()-1;
        while(OgAddress.charAt(i)!='/')
        {
            i--;
        }
        String fadd = OgAddress.substring(0,i+1);
        fadd += FinalName;
        System.out.println(fadd);
        return fadd;
    }
    static void compressImage(String OgAddress, String FinalName)
    {
        try {
            File input = new File(OgAddress);
            BufferedImage image = ImageIO.read(input);

            File compressedImageFile = new File(FinalName+".jpg");
            OutputStream os = new FileOutputStream(compressedImageFile);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();

            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.05f);
            writer.write(null, new IIOImage(image, null, null), param);

            os.close();
            ios.close();
            writer.dispose();
        }
        catch (Exception e)
        {
            System.out.println("Please Ensure Following" +
                    "\n1. The image address is correct and has .jpg extension" +
                    "\n2. The final name entered is empty" );
        }

    }
    static void displayImage(String Image) throws IOException
    {
        File file = new File(Image);
        BufferedImage bufferedImage = ImageIO.read(file);

        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        JFrame jFrame = new JFrame();


        jFrame.setLayout(new FlowLayout());

        jFrame.setSize(4000, 4000);
        JLabel jLabel = new JLabel();


        jLabel.setIcon(imageIcon);
        jFrame.add(jLabel);
        jFrame.setVisible(true);


        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    }
class cropper
{
    String OriginalAddress, ResultName;
    int length = 500, height = 500;
    cropper(String OgAddress, String FinalName, int length, int height) {
        this.OriginalAddress = OgAddress;
        this.ResultName = FinalName;
        this.length = length;
        this.height = height;
    }
    void photoCropper( ) {
        try{
            BufferedImage ogImg = ImageIO.read(new File(this.OriginalAddress));
            System.out.println("Original Dimensions: "+ogImg.getWidth()+"x" +ogImg.getHeight());
            int xset = ogImg.getWidth()*25/100;
            int yset = ogImg.getHeight()*25/100;
            BufferedImage SbImg = ogImg.getSubimage(xset,yset,length,height);
            System.out.println("Cropped Image: "+SbImg.getWidth()+"x"+ SbImg.getHeight());

            Main obj = new Main();
            String FinalAddress = obj.changeAddress(this.OriginalAddress,this.ResultName);
            File outputFile = new File(FinalAddress);
            ImageIO.write(SbImg,"png",outputFile);
            System.out.println("Crop done");
        }
        catch (Exception x) {
            System.out.println("Please Ensure Following" +
                    "\n1. The image address is correct and has .jpg extension" +
                    "\n2. The final name entered is not empty" +
                    "\n3. The Dimesion Entered must not be zero");
        }
    }
}
