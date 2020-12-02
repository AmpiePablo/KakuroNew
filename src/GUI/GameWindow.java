package GUI;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;


public class GameWindow extends JFrame implements Runnable,ActionListener{
    //labels
    private JLabel title, lblBack,lblExit,lblSave,lblSolution,lblSuggestion,lblRestart, lblVerify,cronometro;
    //buttons
    private JButton back,btnExit,btnSave,btnSolution,btnSuggestion,btnRestart,btnVerify;
    //board
    private ArrayList<Game>  juegos;
    private ArrayList<ArrayList<JButton>> matrixButton;
    private int cX;
    private int cY;
    Thread hilo;
    boolean cronometroActivo;

    private JTextArea results;
    private Game actualGame;
    private String selected;
    private int selectedInt;
    private ArrayList<JButton> numbers2;
    private JDialog numbers;

    //Soluciones y datos para verificaciones
    private int[] actual;
    private String kakuroString;
    private String[][] matrizSolution;
    private String[][] matrizActual;
    private int [] result = new int[5];
    private int ingresoDigitos = 0;
    int gane = 0;

    /**
     * *******************************************
     * **********Nombre***************************
     * GameWindow
     *
     * **********Entradas*************************
     * @param pGame
     *
     * **********Salida***************************
     * una ventana con los componentes del juego
     *
     * **********Objetivo*************************
     * Es el constructor de la clase GameWindow
     * ********************************************
     */
    public GameWindow(Game pGame,ArrayList<Game> pJuegos){
        this.actualGame = pGame;
        this.actual = new int[2];

        this.matrixButton = new ArrayList<ArrayList<JButton>>();
        numbers2 = new ArrayList<JButton>();
        juegos = pJuegos;
        this.cX = 20;
        this.cY = 90;
        result[0] = 0;
        result[1] = 0;
        result[2] = 0;
        result[3] = 0;
        result[4] = 0;
        matrizSolution = new String[actualGame.getRow()][actualGame.getColumn()];
        matrizActual = new String[actualGame.getRow()][actualGame.getColumn()];
        componentsFrame();
        components();
        componentsCrono();
        initCr();
        addTextArea("Resultados del jugador: "+actualGame.getPlayer()+"\n");
        getTablero();

    }



    public void getTablero(){
        String t = "consult('src/k.pl')";
        Query q = new Query(t);
        System.out.println(t+""+(q.hasSolution()?"si":"no"));

        java.util.HashMap solution;
        Variable x = new Variable("R");
        Query q1 = new Query("generarkakuro("+actualGame.getRow()+","+actualGame.getColumn()+",R)");
        solution = (HashMap) q1.oneSolution();
        Term kakuro = (Term) solution.get("R");

        kakuroString = kakuro.toString();
        System.out.println(kakuroString);
        String[] array = kakuroString.split(", ",actualGame.getColumn()*actualGame.getRow());
        System.out.println(array.length);

        int cont = 0;
        for(int j = 0; j < actualGame.getRow(); j++){
            for(int p = 0; p < actualGame.getColumn(); p++){
                matrizSolution[j][p] = array[cont];
                if(Character.isDigit(array[cont].charAt(0))){
                    matrizActual[j][p] = "0";
                }else{
                    matrizActual[j][p] = "-1";
                }
                cont+=1;
            }
        }
        createBoard(matrizSolution);
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * createBoard
     *
     * **********Entradas*************************
     * @param pBoard
     *
     * **********Salida***************************
     * El tablero del kakuro en pantalla
     *
     * **********Objetivo*************************
     * tomar el tablero de entrada y generar un nuevo
     * tablero para realizar el juego de kakuro
     * ********************************************
     */
    //initial
    public void createBoard(String [][] pBoard){
        for(int i = 0;i<pBoard.length;i++){
            ArrayList<JButton> temp = new ArrayList<JButton>();
            for(int j = 0; j < pBoard[i].length;j++){
                JButton btnTemp = new JButton();
                btnTemp.setBounds(cX,cY,55,50);
                if(pBoard[i][j].length()>2){
                    btnTemp.setText(pBoard[i][j].substring(1,5));
                    btnTemp.setFont(new Font("Times New Roman",Font.BOLD,8));
                    btnTemp.setEnabled(false);
                }else if(Character.isDigit(pBoard[i][j].charAt(0))){
                    btnTemp.setText("R");
                    btnTemp.setEnabled(true);
                }else{
                    btnTemp.setText("N");
                    btnTemp.setEnabled(false);
                }
                btnTemp.addActionListener(this);
                add(btnTemp);
                temp.add(btnTemp);
                cX+=52;
            }
            cX = 20;
            cY += 48;
            matrixButton.add(temp);
        }
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * componentsFrame
     *
     * **********Entradas*************************
     * @param
     *
     * **********Salida***************************
     * La ventana de juego principal con las
     * caracteristicas modificadas y diseñadas
     *
     * **********Objetivo*************************
     * Mostrar la ventana del juego de forma entendible
     * ********************************************
     */
    public void componentsFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250,10,950,650);
        this.getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);
        setTitle("Game Kakuro");
        setVisible(true);
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * components
     *
     * **********Entradas*************************
     * @param
     *
     * **********Salida***************************
     * Los componentes del juego adaptados a la ventana
     * de juego
     *
     * **********Objetivo*************************
     * Crear nuevos componentes como botones, etiquetas,
     * campos de texto, modificados al gusto del equipo
     * ********************************************
     */
    public void components() {
        try {
            Image imgBack = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/back.png"));
            Image imgExit = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/exit.png"));
            Image imgSuggestion = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/suggestion.png"));
            Image imgSolution = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/puzzle.png"));
            Image imgSave = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/diskette.png"));
            Image imgRestart = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/restart.png"));
            Image imgVerify = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/check.png"));


            title = new JLabel("KAKURO");
            lblBack = new JLabel("Volver");
            lblExit = new JLabel("Salir");
            lblRestart = new JLabel("Reiniciar");
            lblSave = new JLabel("Guardar");
            lblSuggestion = new JLabel("Sugerencia");
            lblSolution = new JLabel("Ver Solución");
            lblVerify = new JLabel("Verificar");

            title.setFont(new Font("Times New Roman",Font.PLAIN,36));
            lblBack.setFont(new Font("Times New Roman",Font.PLAIN,10));;
            lblExit.setFont(new Font("Times New Roman",Font.PLAIN,10));
            lblRestart.setFont(new Font("Times New Roman",Font.PLAIN,14));
            lblSave.setFont(new Font("Times New Roman",Font.PLAIN,14));
            lblSuggestion.setFont(new Font("Times New Roman",Font.PLAIN,14));
            lblSolution.setFont(new Font("Times New Roman",Font.PLAIN,14));
            lblVerify.setFont(new Font("Times New Roman",Font.PLAIN,20));

            title.setBounds(600,25,190,90);
            lblBack.setBounds(5,30,90,20);
            lblExit.setBounds(45,30,90,20);
            lblRestart.setBounds(560,340,90,90);
            lblSuggestion.setBounds(560,425,90,90);
            lblSolution.setBounds(665,340,90,90);
            lblSave.setBounds(680,425,90,93);
            lblVerify.setBounds(820,430,170,70);

            title.setBackground(Color.WHITE);
            lblBack.setBackground(Color.WHITE);
            lblExit.setBackground(Color.WHITE);
            lblRestart.setBackground(Color.WHITE);
            lblSave.setBackground(Color.WHITE);
            lblSuggestion.setBackground(Color.WHITE);
            lblSolution.setBackground(Color.WHITE);
            lblVerify.setBackground(Color.WHITE);

            title.setForeground(new Color(48,231,175));
            lblBack.setForeground(new Color(48,231,175));
            lblExit.setForeground(new Color(245,61,86));
            lblRestart.setForeground(new Color(248,196,113));
            lblSave.setForeground(new Color(248,196,113));
            lblSuggestion.setForeground(new Color(248,196,113));
            lblSolution.setForeground(new Color(248,196,113));
            lblVerify.setForeground(new Color(248,196,113));

            add(title);
            add(lblBack);
            add(lblExit);
            add(lblRestart);
            add(lblSave);
            add(lblSolution);
            add(lblSuggestion);
            add(lblVerify);

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

            btnRestart = new JButton();
            btnRestart.setBounds(550,310,65,65);
            btnRestart.setIcon(new ImageIcon(imgRestart));
            btnRestart.setBorder(new LineBorder(Color.WHITE));
            btnRestart.setContentAreaFilled(false);
            btnRestart.setCursor(Cursor.getPredefinedCursor(12));
            btnRestart.addActionListener(this);
            add(btnRestart);

            btnSuggestion = new JButton();
            btnSuggestion.setBounds(550,400,65,65);
            btnSuggestion.setIcon(new ImageIcon(imgSuggestion));
            btnSuggestion.setBorder(new LineBorder(Color.WHITE));
            btnSuggestion.setContentAreaFilled(false);
            btnSuggestion.setCursor(Cursor.getPredefinedCursor(12));
            btnSuggestion.addActionListener(this);
            add(btnSuggestion);

            btnSolution = new JButton();
            btnSolution.setBounds(670,310,65,65);
            btnSolution.setIcon(new ImageIcon(imgSolution));
            btnSolution.setBorder(new LineBorder(Color.WHITE));
            btnSolution.setContentAreaFilled(false);
            btnSolution.setCursor(Cursor.getPredefinedCursor(12));
            btnSolution.addActionListener(this);
            add(btnSolution);

            btnSave = new JButton();
            btnSave.setBounds(670,400,65,65);
            btnSave.setIcon(new ImageIcon(imgSave));
            btnSave.setBorder(new LineBorder(Color.WHITE));
            btnSave.setContentAreaFilled(false);
            btnSave.setCursor(Cursor.getPredefinedCursor(12));
            btnSave.addActionListener(this);
            add(btnSave);

            btnVerify = new JButton();
            btnVerify.setBounds(790,320,130,130);
            btnVerify.setIcon(new ImageIcon(imgVerify));
            btnVerify.setBorder(new LineBorder(Color.WHITE));
            btnVerify.setContentAreaFilled(false);
            btnVerify.setCursor(Cursor.getPredefinedCursor(12));
            btnVerify.addActionListener(this);
            add(btnVerify);

            results = new JTextArea(10,10);
            results.setBounds(600,130,250,150);
            results.setBackground(new Color(255,212,143));
            add(results);

        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * addTextArea
     *
     * **********Entradas*************************
     * @param pData
     *
     * **********Salida***************************
     * el campo de texto modificado
     *
     * **********Objetivo*************************
     * Recibir el valor de entrada y agregarlo a
     * el campo de texto, con la verificaciones o
     * resultados de la misma
     * ********************************************
     */
    public void addTextArea(String pData){
        results.append(pData);
    }

    //listo
    public void reiniciar(){
        for(int i=0;i<matrixButton.size();i++){
            for(int j=0;j<matrixButton.get(i).size();j++){
                if(matrixButton.get(i).get(j).getText()!="N" && matrixButton.get(i).get(j).getText().length()<2){
                    matrixButton.get(i).get(j).setText("R");
                    matrizActual[i][j] = "0";
                }else{
                    matrizActual[i][j] = "-1";
                }

            }
        }
    }

    //listo
    public void verSolucion(){
        for(int i=0;i<matrixButton.size();i++){
            for(int j=0;j<matrixButton.get(i).size();j++){
                String data = matrizSolution[i][j];
                if(matrixButton.get(i).get(j).getText()!="N" && matrixButton.get(i).get(j).getText().length()<2){
                    matrixButton.get(i).get(j).setText(data);
                    actualGame.setEnding("Auto solución");
                    actualGame.setTime(cronometro.getText());
                }
            }
        }
        gane = 1;
        juegos.add(actualGame);
        stopCr();
    }

    //Verificacion
    public void verificar(){
        printMatrices();
        result[0] = 0;
        result[1] = 0;
        result[2] = 0;
        result[3] = 0;
        results.selectAll();
        results.replaceSelection("");
        for(int i=0;i<matrizSolution.length;i++){
            for(int j=0;j<matrizSolution[i].length;j++){
                if(matrizActual[i][j] == "0"){
                    result[0] += 1;
                    result[1] += 1;
                }else if(Character.isDigit(matrizActual[i][j].charAt(0))){
                    result[1]+=1;
                    if(matrizActual[i][j].equals(matrizSolution[i][j])){
                        result[2] += 1;
                    }else{
                        result[3] += 1;
                    }
                }
            }
        }
        result[4] +=1;
        String vacias = "Celdas Vacías: "+result[0]+"\n";
        String total = "Total de celdas: "+result[1]+"\n";
        String incorrectas = "Digitos incorrectos: "+result[3]+"\n";
        String correct = "Digitos correctos: "+result[2]+"\n";
        addTextArea("Jugador: "+actualGame.getPlayer()+"\n");
        addTextArea(vacias);
        addTextArea(total);
        addTextArea(incorrectas);
        addTextArea(correct);

        if(verificaGane(result)){
            actualGame.setTime(cronometro.getText());
            actualGame.setEnding("Exitosa");
            actualGame.setCountVerified(result[4]);
            actualGame.setErrorVerified(result[4]);
            actualGame.setTotalCells(ingresoDigitos);
            juegos.add(actualGame);
            stopCr();
            JOptionPane.showMessageDialog(null, "Felicidad ha ganado",
                    "InfoBox: " + "algo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void printMatrices(){
        System.out.println("1");
        for(int i=0;i<matrizSolution.length;i++){
            for(int j=0;j<matrizSolution.length;j++){
                System.out.print(matrizSolution[i][j]+",");

            }
            System.out.println();
        }
        System.out.println("2");
        for(int i=0;i<matrizActual.length;i++){
            for(int j=0;j<matrizActual.length;j++){
                System.out.print(matrizActual[i][j]+",");
            }
            System.out.println();
        }
    }

    //Verifica el gane
    public boolean verificaGane(int[] pDatos){
        if(pDatos[1] == pDatos[2]){
            gane = 2;
            return true;
        }
        else{ return false; }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(back)){
            if(gane<1) {
                actualGame.setTime(cronometro.getText());
                actualGame.setCountVerified(result[4]);
                actualGame.setErrorVerified(result[4]);
                actualGame.setTotalCells(ingresoDigitos);
                actualGame.setEnding("Abandono");
                juegos.add(actualGame);
                mainWindow win = new mainWindow(juegos);
                dispose();
            }else{
                mainWindow win = new mainWindow(juegos);
                dispose();
            }
        }
        if(e.getSource().equals(btnExit)){
            dispose();
            System.exit(0);
        }
        if(e.getSource().equals(btnRestart)){
            reiniciar();
        }
        if(e.getSource().equals(btnSolution)){
            verSolucion();
        }
        if(e.getSource().equals(btnVerify)){ verificar(); }


        /*No realizadas*/
        if(e.getSource().equals(btnSuggestion)){ System.out.println("Sugerencia"); }
        if(e.getSource().equals(btnSave)){ System.out.println("Save"); }

        for(int i = 0; i<matrixButton.size();i++){
            for(int j = 0; j<matrixButton.get(i).size();j++){
                if(e.getSource().equals(matrixButton.get(i).get(j))){
                    actual[0]=i;
                    actual[1]=j;
                    numberSelect();
                }
            }
        }
        for(int j=0;j<numbers2.size();j++){
            if(e.getSource().equals(numbers2.get(j))){
                selected = numbers2.get(j).getText();
                selectedInt = Integer.parseInt(selected.toString());
                matrixButton.get(actual[0]).get(actual[1]).setText(selected);
                matrizActual[actual[0]][actual[1]] = selected;
                ingresoDigitos+=1;
                numbers.dispose();
            }
        }
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * componentsCrono
     *
     * **********Entradas*************************
     * @param
     *
     * **********Salida***************************
     * un cronometro en pantalla
     *
     * **********Objetivo*************************
     * mostrar un cronometro en pantalla
     * ********************************************
     */
    public void componentsCrono(){
        JLabel titulo = new JLabel("Tiempo:");
        titulo.setBounds(20,60,90,40);
        titulo.setFont(new Font("Times New Roman",Font.BOLD,13));
        titulo.setBackground( Color.WHITE );
        titulo.setForeground( Color.BLACK );
        cronometro = new JLabel("00:00");
        cronometro.setBounds(73,70,60,20);
        cronometro.setFont(new Font("Times New Roman",Font.BOLD,13));
        cronometro.setBackground( Color.WHITE );
        cronometro.setForeground( Color.BLACK );
        cronometro.setOpaque( true );
        add(titulo);
        add(cronometro);
    }


    public void generateTablero(){ }

    /**
     * *******************************************
     * **********Nombre***************************
     * run
     *
     * **********Entradas*************************
     * @param
     *
     * **********Salida***************************
     * El cronometro actualizandose cada segundo
     *
     * **********Objetivo*************************
     * utiliza un hilo para refrescar costantemente
     * la ventana del juego con el cronometro actual
     * ********************************************
     */
    @Override
    public void run() {
        Integer minInt = 0 , segInt = 0, milInt = 0;
        String minStr="", segStr="", milStr="";
        try{
            while(cronometroActivo){
                Thread.sleep( 4 );
                //Incrementamos 4 milesimas de segundo
                milInt += 4;

                //Cuando llega a 1000 osea 1 segundo aumenta 1 segundo
                //y las milesimas de segundo de nuevo a 0
                if( milInt == 1000 )
                {
                    milInt = 0;
                    segInt += 1;
                    //Si los segundos llegan a 60 entonces aumenta 1 los minutos
                    //y los segundos vuelven a 0
                    if( segInt == 60 )
                    {
                        segInt = 0;
                        minInt++;
                    }
                }
                if( minInt < 10 ) minStr = "0" + minInt;
                else minStr = minInt.toString();
                if( segInt < 10 ) segStr = "0" + segInt;
                else segStr = segInt.toString();

                cronometro.setText( minInt + ":" + segInt);
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * initCR
     *
     * **********Entradas*************************
     * @param
     *
     * **********Salida***************************
     * el cronometro iniciado
     *
     * **********Objetivo*************************
     * iniciar el cronometro
     * ********************************************
     */
    public void initCr() {
        cronometroActivo = true;
        hilo = new Thread( this );
        hilo.start();
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * stopCr
     *
     * **********Entradas*************************
     * @param
     *
     * **********Salida***************************
     * el cronometro detenido
     *
     * **********Objetivo*************************
     * detener el hilo
     * ********************************************
     */
    public void stopCr(){ cronometroActivo = false; }
    /*GETS Y SETS*/
    public ArrayList<Game> getJuegos() { return juegos; }
    public void setJuegos(ArrayList<Game> pGames){ juegos = pGames; }
    public int getSelectedInt() { return selectedInt; }
    public void setSelectedInt(int selectedInt) { this.selectedInt = selectedInt; }
    public String getSelected(){ return selected; }
    public void setSelected(String selectedP){ this.selected=selectedP; }

    /**
     * *******************************************
     * **********Nombre***************************
     * numberSelect
     *
     * **********Entradas*************************
     * @param
     *
     * **********Salida***************************
     * muestra una ventana con numeros para seleccionar
     *
     * **********Objetivo*************************
     * Crear una ventana donde el usuario pueda escoger el numero que este desee
     * ********************************************
     */
    public void numberSelect(){
        numbers = new JDialog();
        numbers.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        numbers.setBounds(550,250,315,336);
        numbers.getContentPane().setBackground(Color.WHITE);
        numbers.setLayout(null);
        numbers.setResizable(false);
        numbers.setTitle("Nuevo valor");
        numbers.setVisible(true);
        int x = 0;
        int y = 0;
        Integer index = 1;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                JButton nuevo = new JButton(index.toString());
                nuevo.setBounds(x,y,100,100);
                nuevo.setContentAreaFilled(true);
                nuevo.setOpaque(true);
                nuevo.addActionListener(this);
                numbers.add(nuevo);
                numbers2.add(nuevo);
                index++;
                x+=100;
            }
            x = 0;
            y += 100;
        }
    }
}
