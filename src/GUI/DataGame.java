package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DataGame extends JDialog implements ActionListener {
    private JLabel lblTittle;
    private JLabel lblRow;
    private JLabel lblColumn;
    private JButton btnOk;
    private JButton btnCancel;
    private JTextField txtName;
    private JSpinner spnRow;
    private JSpinner spnColumn;
    private JFrame win;
    private ArrayList<Game> juegos;
    /**
     * *******************************************
     * **********Nombre***************************
     * DataGame
     *
     * **********Entradas*************************
     * @param pWin
     *
     * **********Salida***************************
     * una ventana para solicitar los datos del juego
     *
     * **********Objetivo*************************
     * Es el construcutor de la clase DAtaGame
     * ********************************************
     */
    public DataGame(JFrame pWin, ArrayList<Game> pJuegos){
        this.win = pWin;
        juegos=pJuegos;
        componentsDialog();
        components();
        setVisible(true);
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * componentsDialog
     *
     * **********Entradas*************************
     * @param
     *
     * **********Salida***************************
     * crea los componentes de la ventana
     *
     * **********Objetivo*************************
     * Hacer un JDialog con caracteristicas para mostrar
     * los diferentes componentes
     * ********************************************
     */
    public void componentsDialog(){
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(550,340,380,250);
        this.getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setResizable(false);
        setTitle("Datos del Juego");
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
     * Los componentes de la ventana agregados y
     * modificados, label, buttons, spinbox
     *
     * **********Objetivo*************************
     * dar diseño y forma a los componentes de la interfaz
     * ********************************************
     */
    public void components(){
        try {
            Image imgExit = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/cancelar.png"));
            Image imgOk = ImageIO.read(this.getClass().getResourceAsStream("../Imagenes/aceptar.png"));

            lblTittle = new JLabel("Recolección de Datos");
            lblTittle.setBounds(94, 0, 190, 50);
            lblTittle.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            lblTittle.setForeground(new Color(254, 184, 70));
            add(lblTittle);

            txtName = new JTextField("Nombre Jugador");
            txtName.setBounds(15, 70, 126, 25);
            txtName.setBorder(new LineBorder(new Color(254, 184, 70)));
            add(txtName);

            lblRow = new JLabel("Filas:");
            lblRow.setBounds(15, 90, 190, 50);
            lblRow.setFont(new Font("Times New Roman", Font.PLAIN, 19));
            lblRow.setForeground(new Color(254, 184, 70));
            add(lblRow);


            lblColumn = new JLabel("Columnas:");
            lblColumn.setBounds(15, 120, 190, 50);
            lblColumn.setFont(new Font("Times New Roman", Font.PLAIN, 19));
            lblColumn.setForeground(new Color(254, 184, 70));
            add(lblColumn);

            spnRow = new JSpinner(new SpinnerNumberModel(9,7,20,1));
            spnRow.setBounds(110,102,30,20);
            add(spnRow);

            spnColumn = new JSpinner(new SpinnerNumberModel(9,7,20,1));
            spnColumn.setBounds(110,135,30,20);
            add(spnColumn);

            btnOk = new JButton();
            btnOk.setBounds(220,80,66,66);
            btnOk.setIcon(new ImageIcon(imgOk));
            btnOk.setBorder(new LineBorder(Color.WHITE));
            btnOk.setContentAreaFilled(false);
            btnOk.setCursor(Cursor.getPredefinedCursor(12));
            btnOk.addActionListener(this);
            add(btnOk);

        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnOk)){
            GameWindow gm = new GameWindow(getData(),juegos);
            win.dispose();
            dispose();
        }
    }

    /**
     * *******************************************
     * **********Nombre***************************
     * getData
     *
     * **********Entradas*************************
     * @param
     * @param
     * @param
     *
     * **********Salida***************************
     * una clase game
     *
     * **********Objetivo*************************
     * Mostrar una ventana para solicitud de datos,
     * que sirve para formar el nuevo juego
     * ********************************************
     */
    public Game getData(){
        String playerName = txtName.getText();
        int row = (Integer) spnRow.getValue();
        int column = (Integer) spnColumn.getValue();
        System.out.println(playerName+"-"+row+"-"+column);
        Game newGame = new Game(row,column,playerName);
        return newGame;
    }
}
