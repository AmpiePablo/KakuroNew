package GUI;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class mainWindow extends JFrame implements ActionListener {
    private JLabel title;
    private JLabel labelStat;
    private JLabel labelNewG;
    private JLabel labelRep;
    private JButton statistics;
    private JButton newGame;
    private JButton replayGame;
    private ArrayList<Game> juegos;


    /**
     * *******************************************
     * **********Nombre***************************
     * mainWindow
     *
     * **********Entradas*************************
     * @param sinEntradas, pJuegos
     *
     * **********Salida***************************
     * La ventana inicial del programa
     *
     * **********Objetivo*************************
     * Existe una sobre carga, para poder pasar
     * los diferentes juegos realizados entre ventanas
     * y así no usar variables globales
     * ********************************************
     */
    public mainWindow(){
        components();
        componentsPanel();
        juegos = new ArrayList<Game>();

    }
    public mainWindow(ArrayList<Game> pJuegos){
        this.juegos = pJuegos;
        components();
        componentsPanel();
        System.out.println(juegos.size());
    }
    public void printGames(){
        for(int i = 0; i<juegos.size();i++){
            System.out.println(juegos.get(i).getPlayer());
        }
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * componentsPanel
     *
     * **********Entradas*************************
     * @param sinEntradas
     *
     * **********Salida***************************
     * La ventana con caracteristicas de diseño
     *
     * **********Objetivo*************************
     * Crear la ventana o frame del programa
     * ********************************************
     */
    public void componentsPanel(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250,10,950,650);
        this.getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);
        setTitle("Ventana Principal");
        setVisible(true);
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * components
     *
     * **********Entradas*************************
     * @param sinEntradas
     *
     * **********Salida***************************
     * Todos los componentes en el frame
     *
     * **********Objetivo*************************
     * Crea nuevas etiquetas, botones, para la intereaccion
     * del usuario con el sistema
     * ********************************************
     */
    public void components(){
        try {
            title = new JLabel();
            title.setBounds(380, 10, 400, 150);
            title.setText("KAKURO");
            title.setForeground(new Color(248, 196, 113));
            title.setFont(new Font("Times New Roman", Font.PLAIN, 45));
            add(title);

            Image imgStat = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/analysis.png"));
            statistics = new JButton();
            labelStat = new JLabel("Ver Estadisticas");
            statistics.setBounds(160,250,130,130);
            labelStat.setBounds(163,365,154,64);
            statistics.setIcon(new ImageIcon(imgStat));
            labelStat.setFont(new Font("Times New Roman",Font.PLAIN,20));
            statistics.setBackground(Color.WHITE);
            labelStat.setForeground(new Color(235,152,78));
            statistics.setBorder(new LineBorder(Color.WHITE));
            statistics.setContentAreaFilled(false);
            statistics.setCursor(Cursor.getPredefinedCursor(12));
            statistics.addActionListener(this);
            add(labelStat);
            add(statistics);

            Image imgGame = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/sudoku.png"));
            newGame = new JButton();
            labelNewG = new JLabel("Nuevo Juego");
            newGame.setBounds(400,250,130,130);
            labelNewG.setBounds(415,365,104,64);
            newGame.setIcon(new ImageIcon(imgGame));
            labelNewG.setFont(new Font("Times New Roman",Font.PLAIN,20));
            newGame.setBackground(Color.WHITE);
            labelNewG.setForeground(new Color(235,152,78));
            newGame.setBorder(new LineBorder(Color.WHITE));
            newGame.setContentAreaFilled(false);
            newGame.setCursor(Cursor.getPredefinedCursor(12));
            newGame.addActionListener(this);
            add(labelNewG);
            add(newGame);


            Image imgRep = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/replay.png"));
            replayGame = new JButton();
            labelRep = new JLabel("Repeticiones");
            replayGame.setBounds(640,250,130,130);
            labelRep.setBounds(655,365,104,64);
            replayGame.setIcon(new ImageIcon(imgRep));
            labelRep.setFont(new Font("Times New Roman",Font.PLAIN,20));
            replayGame.setBackground(Color.WHITE);
            labelRep.setForeground(new Color(235,152,78));
            replayGame.setBorder(new LineBorder(Color.WHITE));
            replayGame.setContentAreaFilled(false);
            replayGame.setCursor(Cursor.getPredefinedCursor(12));
            replayGame.addActionListener(this);
            add(labelRep);
            add(replayGame);

        }catch (Exception ex){
            System.out.println(ex);
        }

    }

    /*GETS Y SETS*/
    public ArrayList<Game> getJuegos() { return juegos; }
    public void setJuegos(ArrayList<Game> pGames){
        juegos = pGames;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(statistics)){
            statWindow stat = new statWindow(juegos);
            setVisible(false);
        }
        if(e.getSource().equals(newGame)){
            DataGame dg = new DataGame(this,juegos);
        }
    }
}
