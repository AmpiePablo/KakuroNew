package GUI;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class statWindow extends JFrame implements ActionListener{
    private JLabel title;
    private JLabel title2;
    private JLabel lblExit;
    private JLabel lblBack;
    private JLabel celdasT;
    private JLabel cantVerify;
    private JLabel cantErrors;
    private JLabel cantSug;
    private JLabel typeFin;
    private JLabel player;
    private JLabel time;

    private JButton btnExit;
    private JButton back;
    private JButton verify;
    private JList data;

    private ArrayList<Game> juegos = new ArrayList<Game>();

    /**
     * *******************************************
     * **********Nombre***************************
     * statWindow
     *
     * **********Entradas*************************
     * @param pJuegos
     *
     * **********Salida***************************
     * diferentes funciones para la ventana de
     * estadisticas
     *
     * **********Objetivo*************************
     * Es el constructor de la clase statWindow
     * ********************************************
     */
    public statWindow(ArrayList<Game> pJuegos){
        juegos = pJuegos;
        //addgames();
        componentsFrame();
        components();
        components2();
        System.out.println(juegos.size());
    }

    public void addGames2(){
        for(int i = 0;i < juegos.size(); i++){

        }
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * componentsFrame
     *
     * **********Entradas*************************
     * @param sinEntradas
     *
     * **********Salida***************************
     * crea el frama contenedor, y le agrega forma
     * y diseño grafico
     *
     * **********Objetivo*************************
     * tomar el frame y carecterizarlo de manera que se
     * vea bien y estructurado
     * ********************************************
     */
    public void componentsFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250,10,950,650);
        this.getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);
        setTitle("statistics");
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
     * Todos los componentes de interfaz mostrados en
     * la ventana o frame
     *
     * **********Objetivo*************************
     * crear botones, labels, listas de texto, para
     * salir y volver al inicio
     * ********************************************
     */
    public void components(){
        try {
            Image imgBack = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/back.png"));
            Image imgExit = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/exit.png"));

            title = new JLabel("Estadisticas");
            title2 = new JLabel("Juegos");
            lblBack = new JLabel("Volver");
            lblExit = new JLabel("Salir");
            lblBack.setFont(new Font("Times New Roman",Font.PLAIN,10));;
            lblExit.setFont(new Font("Times New Roman",Font.PLAIN,10));
            title.setFont(new Font("Times New Roman",Font.PLAIN,38));
            title2.setFont(new Font("Times New Roman",Font.PLAIN,24));
            lblBack.setBounds(5,30,90,20);
            lblExit.setBounds(45,30,90,20);
            title.setBounds(380,30,190,90);
            title2.setBounds(140,105,90,25);
            lblBack.setBackground(Color.WHITE);
            lblExit.setBackground(Color.WHITE);
            title.setBackground(Color.WHITE);
            title2.setBackground(Color.WHITE);
            lblBack.setForeground(new Color(48,231,175));
            lblExit.setForeground(new Color(245,61,86));
            title.setForeground(new Color(245,61,86));
            title2.setForeground(new Color(48, 231, 175));
            add(lblBack);
            add(lblExit);
            add(title);
            add(title2);

            back = new JButton();
            back.setBounds(1, 1, 33, 33);
            back.setIcon(new ImageIcon(imgBack));
            back.setBorder(new LineBorder(Color.WHITE));
            back.setContentAreaFilled(false);
            back.setCursor(Cursor.getPredefinedCursor(12));
            back.addActionListener(this);
            add(back);

            btnExit = new JButton();
            btnExit.setBounds(40,1,33,33);
            btnExit.setIcon(new ImageIcon(imgExit));
            btnExit.setBorder(new LineBorder(Color.WHITE));
            btnExit.setContentAreaFilled(false);
            btnExit.setCursor(Cursor.getPredefinedCursor(12));
            btnExit.addActionListener(this);
            add(btnExit);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Todos los componentes de interfaz mostrados en
     * la ventana o frame
     *
     * **********Objetivo*************************
     * crear botones, labels, listas de texto, para
     * con sus respectivas caracteristicas
     * ********************************************
     */
    public void components2(){
        try {
            DefaultListModel listModel = new DefaultListModel();
            for (int i = 0; i < juegos.size(); i++) {
                listModel.addElement("Juego de: " + juegos.get(i).getPlayer());
            }
            data = new JList(listModel);
            data.setBounds(40, 140, 300, 350);
            data.setBackground(new Color(255, 212, 143));
            add(data);

            Image imgOk = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/aceptar.png"));
            verify = new JButton();
            verify.setBounds(380,420,66,66);
            verify.setIcon(new ImageIcon(imgOk));
            verify.setBorder(new LineBorder(Color.WHITE));
            verify.setContentAreaFilled(false);
            verify.setCursor(Cursor.getPredefinedCursor(12));
            verify.addActionListener(this);
            add(verify);

            player = new JLabel("Jugador: ");
            celdasT = new JLabel("Celdas Ingresadas:");
            cantVerify = new JLabel("Cantidad de Verificaciones:");
            cantErrors = new JLabel("Cantidad de Errores: ");
            cantSug = new JLabel("Sugerencias usadas:");
            typeFin = new JLabel("Finalización");
            time = new JLabel("Tiempo:");

            player.setFont(new Font("Times New Roman",Font.BOLD,14));
            player.setBounds(400,100,190,90);
            player.setBackground(Color.WHITE);
            player.setForeground(new Color(147, 106, 47));

            celdasT.setFont(new Font("Times New Roman",Font.BOLD,14));
            celdasT.setBounds(400,140,190,90);
            celdasT.setBackground(Color.WHITE);
            celdasT.setForeground(new Color(147, 106, 47));

            cantVerify.setFont(new Font("Times New Roman",Font.BOLD,14));
            cantVerify.setBounds(400,180,190,90);
            cantVerify.setBackground(Color.WHITE);
            cantVerify.setForeground(new Color(147, 106, 47));

            cantErrors.setFont(new Font("Times New Roman",Font.BOLD,14));
            cantErrors.setBounds(400,220,190,90);
            cantErrors.setBackground(Color.WHITE);
            cantErrors.setForeground(new Color(147, 106, 47));

            cantSug.setFont(new Font("Times New Roman",Font.BOLD,14));
            cantSug.setBounds(650,100,190,90);
            cantSug.setBackground(Color.WHITE);
            cantSug.setForeground(new Color(147, 106, 47));

            typeFin.setFont(new Font("Times New Roman",Font.BOLD,14));
            typeFin.setBounds(650,140,190,90);
            typeFin.setBackground(Color.WHITE);
            typeFin.setForeground(new Color(147, 106, 47));

            time.setFont(new Font("Times New Roman",Font.BOLD,14));
            time.setBounds(650,180,190,90);
            time.setBackground(Color.WHITE);
            time.setForeground(new Color(147, 106, 47));

            add(player);
            add(celdasT);
            add(cantVerify);
            add(cantErrors);
            add(cantSug);
            add(typeFin);
            add(time);

        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(back)){
            mainWindow win = new mainWindow();
            win.setVisible(true);
            dispose();
        }
        if(e.getSource().equals(btnExit)){
            dispose();
            System.exit(0);
        }
        if(e.getSource().equals(verify)){
            int index = data.getSelectedIndex();
            if(index>=0){
                showData(index);
            }
            System.out.println(index);
        }
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * showData
     *
     * **********Entradas*************************
     * @param pIndex
     *
     * **********Salida***************************
     * las estadisticas de un juego
     *
     * **********Objetivo*************************
     * toma el indice de entrada, toma el juego
     * en la posición del indice de la lista
     * y muestra los valores del mismo.
     * ********************************************
     */
    public void showData(int pIndex){
        Game temp = juegos.get(pIndex);
        player.setText("Jugador: "+temp.getPlayer());
        celdasT.setText("Celdas Ingresadas: "+temp.getTotalCells());
        cantVerify.setText("Cantidad de Verificaciones: "+temp.getCountVerified());
        cantErrors.setText("Cantidad de Errores: "+temp.getErrorVerified());
        cantSug.setText("Sugerencias usadas: "+temp.getCountSuggestion());
        typeFin.setText("Finalización: "+temp.getEnding());
        time.setText("Tiempo: "+temp.getTime());
    }


    /*SETS Y GETS*/
    public ArrayList<Game> getJuegos() { return juegos; }
    public void setJuegos(ArrayList<Game> pGames){
        juegos = pGames;
    }

    public void addgames(){
        Game n = new Game(10,10,"ampi");
        n.setCountSuggestion(4);
        n.setCountVerified(14);
        n.setErrorVerified(8);
        n.setTotalCells(100);
        n.setTime("10:02");
        n.setEnding("bien");
        Game n2 = new Game(10,10,"ampi2");
        n2.setCountSuggestion(1);
        n2.setCountVerified(2);
        n2.setErrorVerified(21);
        n2.setTotalCells(90);
        n2.setTime("05:02");
        n2.setEnding("bien");
        Game n3 = new Game(10,10,"ampi3");
        n3.setCountSuggestion(4);
        n3.setCountVerified(12);
        n3.setErrorVerified(20);
        n3.setTotalCells(60);
        n3.setTime("13:02");
        n3.setEnding("bien");
        juegos.add(n);
        juegos.add(n2);
        juegos.add(n3);
    }
}
