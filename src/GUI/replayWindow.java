package GUI;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class replayWindow extends JFrame{
    private JLabel title;

    public replayWindow(){
        componentsFrame();
    }

    public void componentsFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250,10,950,650);
        this.getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);
        setTitle("Repeats Available");
        setVisible(true);
    }

    public void components(){

    }
}
