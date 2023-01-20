// Java Program to take a Snapshot from System Camera
// using OpenCV

// Importing openCV modules
package cnn.driver;
// importing swing and awt classes
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Importing date class of sql package
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
// Importing VideoCapture class
// This class is responsible for taking screenshot
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Point;

// Class - Swing Class
public class Camera extends JFrame {

    // Camera screen
    private JLabel cameraScreen;

    // Button for image capture
    private JButton btnCapture;

    // Start camera
    private VideoCapture capture;

    // Store image as 2D matrix
    private Mat image;

    private boolean clicked = false;

    public Camera()
    {

        // Designing UI
        setLayout(null);

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        add(cameraScreen);

        JButton btn1 = new JButton("Prediction: FIRE");
        btn1.setBounds(300, 480, 200, 40);
        add(btn1);

        btnCapture = new JButton("capture");
        btnCapture.setBounds(100, 480, 80, 40);
        add(btnCapture);

        btnCapture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                clicked = true;
            }
        });

        setSize(new Dimension(640, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Creating a camera
    public void startCamera()
    {
        capture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;
        while (true) {
            // read image to matrix
            capture.read(image);
            Imgproc.rectangle(
                    image,                    //Matrix obj of the image
                    new Point(230, 50),        //p1
                    new Point(400, 280),       //p2
                    new Scalar(0, 0, 255),     //Scalar object for color
                    5                      //Thickness of the line
            );
            // convert matrix to byte
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);

            imageData = buf.toArray();

            // Add to JLabel
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
            int i = 0;
            // Capture and save to file
            if (clicked) {
                // prompt for enter image name
                i++;
                String name = new SimpleDateFormat(
                            "fire_image_001")
                            .format(new Date(
                                    HEIGHT, WIDTH, getX()));

                // Write to file
                Imgcodecs.imwrite("images/" + name + i + ".jpg",
                        image);


            }
        }
    }

    // Main driver method
    public static void main(String[] args)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {
            // Overriding existing run() method
            @Override public void run()
            {
                final Camera camera = new Camera();

                // Start camera in thread
                new Thread(new Runnable() {
                    @Override public void run()
                    {
                        camera.startCamera();
                    }
                }).start();
            }
        });
    }
}
