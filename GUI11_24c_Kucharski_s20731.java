import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class GUI11_24c_Kucharski_s20731 {

    private JFrame frame;
    private JTextArea textArea;
    private JMenuBar menuBarUp;
    private JMenuBar menuBarDown;
    private JScrollPane suwak;
    private File wczytanyPlik = null;
    private static String path = "C:\\Users\\48512\\Documents";
    private ColorIcon fgIcon;
    private ColorIcon bgIcon;
    private int fgChanges = 0;
    private int bgChanges = 0;
    private int sizeChanges = 0;

    public GUI11_24c_Kucharski_s20731(){
        frame = new JFrame("Prosty edytor - bez tytułu");
        textArea = new JTextArea();
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                status.setText("modified");
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        suwak = new JScrollPane(textArea);
        menuBarUp = new JMenuBar();
        menuBarDown = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        textArea.setLineWrap(true);

        optionsMenu.add(optionsForegroundCreator());
        optionsMenu.add(optionsBackgroundCreator());
        optionsMenu.add(optionsFontSizeCreator());

        menuBarUp.add(fileMenuCreator());
        menuBarUp.add(editMenuCreator());
        menuBarUp.add(optionsMenu);

        menuBarDown.add(menuDownCreator(),BorderLayout.LINE_START);

        frame.add(menuBarUp, BorderLayout.PAGE_START);
        frame.add(suwak, BorderLayout.CENTER);
        frame.add(menuBarDown, BorderLayout.PAGE_END);

        frame.setPreferredSize(new Dimension(550,550));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JMenu fileMenuCreator(){
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileOpen = new JMenuItem("Open");
        JMenuItem fileSave = new JMenuItem("Save");
        JMenuItem fileSaveAs = new JMenuItem("Save As...");
        JMenuItem fileExit = new JMenuItem("Exit");
        JFileChooser fileChooser = new JFileChooser(path);

        //Opcja Open w menu
        /*FileNameExtensionFilter filtr = new FileNameExtensionFilter(
                "Plik txt", "txt");
        fileChooser.setFileFilter(filtr);*/
        ActionListener openActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String zawartoscPliku = "";
                    wczytanyPlik = fileChooser.getSelectedFile();
                    try{
                        FileReader fr = new FileReader(fileChooser.getSelectedFile());
                        BufferedReader br = new BufferedReader(fr);
                        StringBuilder sb = new StringBuilder();
                        String tmp;
                        while((tmp=br.readLine())!=null){
                            sb.append(tmp);
                            sb.append("\n");
                        }
                        zawartoscPliku=sb.toString();
                    } catch(IOException a){
                        a.printStackTrace();
                    }
                    textArea.setText(null);
                    textArea.setText(zawartoscPliku);
                    path = fileChooser.getSelectedFile().getPath();
                    frame.setTitle("Prosty edytor - "+path);
                    status.setText("new");
                }
            }
        };

        //Opcja Save As w menu
        ActionListener saveAsActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter fw = new FileWriter(fileChooser.getSelectedFile());
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(textArea.getText());
                        bw.flush();
                        bw.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    wczytanyPlik = fileChooser.getSelectedFile();
                    path = wczytanyPlik.getPath();
                    frame.setTitle("Prosty edytor - "+path);
                    status.setText("saved");
                }
            }
        };

        // Opcja Save w menu
        ActionListener saveActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wczytanyPlik!=null){
                    try {
                        FileWriter fw = new FileWriter(wczytanyPlik);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(textArea.getText());
                        bw.flush();
                        bw.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else{
                    saveAsActionListener.actionPerformed(e);
                }
                status.setText("saved");
            }
        };

        //opcja Exit w menu
        ActionListener exitActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        };

        fileOpen.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
        fileSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
        fileSaveAs.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
        fileExit.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));

        fileOpen.setMnemonic('o');
        fileSave.setMnemonic('s');
        fileSaveAs.setMnemonic('a');
        fileExit.setMnemonic('x');

        fileOpen.addActionListener(openActionListener);
        fileSave.addActionListener(saveActionListener);
        fileSaveAs.addActionListener(saveAsActionListener);
        fileExit.addActionListener(exitActionListener);

        fileMenu.add(fileOpen);
        fileMenu.add(fileSave);
        fileMenu.add(fileSaveAs);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBackground(Color.red);

        fileMenu.add(separator);
        fileMenu.add(fileExit);
        return fileMenu;
    }

    public JMenu editMenuCreator(){
        JMenu editMenu = new JMenu("Edit");
        JMenuItem editDom = new JMenuItem("Dom");
        JMenuItem editPraca = new JMenuItem("Praca");
        JMenuItem editSzkola = new JMenuItem("Szkoła");
        JMenu editAdresy = new JMenu("Adresy");

        editPraca.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
        editDom.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
        editSzkola.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));

        editPraca.setMnemonic('p');
        editDom.setMnemonic('d');
        editSzkola.setMnemonic('s');

        editPraca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.insert("Pracownicza 5", textArea.getCaretPosition());
            }
        });

        editDom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.insert("Domowa 21", textArea.getCaretPosition());
            }
        });

        editSzkola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.insert("Szkolna 17", textArea.getCaretPosition());
            }
        });

        editAdresy.add(editPraca);
        editAdresy.add(editDom);
        editAdresy.add(editSzkola);
        editMenu.add(editAdresy);

        return editMenu;
    }

    public JMenu optionsForegroundCreator(){

        JMenu optionsForeground = new JMenu("Foreground");

        ButtonGroup  foregroundGroup = new ButtonGroup();

        ColorIcon greenIcon = new ColorIcon(9,Color.green);
        ColorIcon orangeIcon = new ColorIcon(9,Color.orange);
        ColorIcon redIcon = new ColorIcon(9,Color.red);
        ColorIcon blackIcon = new ColorIcon(9,Color.black);
        ColorIcon whiteIcon = new ColorIcon(9,Color.white);
        ColorIcon yellowIcon = new ColorIcon(9,Color.yellow);
        ColorIcon blueIcon = new ColorIcon(9,Color.blue);

        JRadioButtonMenuItem optionsForegroundGreen = new JRadioButtonMenuItem("Green",greenIcon);
        JRadioButtonMenuItem optionsForegroundOrange = new JRadioButtonMenuItem("Orange",orangeIcon);
        JRadioButtonMenuItem optionsForegroundRed = new JRadioButtonMenuItem("Red",redIcon);
        JRadioButtonMenuItem optionsForegroundBlack = new JRadioButtonMenuItem("Black",blackIcon, true);
        JRadioButtonMenuItem optionsForegroundWhite = new JRadioButtonMenuItem("White",whiteIcon);
        JRadioButtonMenuItem optionsForegroundYellow = new JRadioButtonMenuItem("Yellow",yellowIcon);
        JRadioButtonMenuItem optionsForegroundBlue = new JRadioButtonMenuItem("Blue",blueIcon);

        optionsForegroundGreen.setForeground(new Color(0,150,0));
        optionsForegroundOrange.setForeground(Color.orange);
        optionsForegroundRed.setForeground(Color.red);
        optionsForegroundBlack.setForeground(Color.black);
        optionsForegroundWhite.setForeground(Color.white);
        optionsForegroundYellow.setForeground(Color.yellow);
        optionsForegroundBlue.setForeground(Color.blue);

        optionsForegroundGreen.addActionListener(foregroundActionListenerCreator(Color.green));
        optionsForegroundOrange.addActionListener(foregroundActionListenerCreator(Color.orange));
        optionsForegroundRed.addActionListener(foregroundActionListenerCreator(Color.red));
        optionsForegroundBlack.addActionListener(foregroundActionListenerCreator(Color.black));
        optionsForegroundWhite.addActionListener(foregroundActionListenerCreator(Color.white));
        optionsForegroundYellow.addActionListener(foregroundActionListenerCreator(Color.yellow));
        optionsForegroundBlue.addActionListener(foregroundActionListenerCreator(Color.blue));

        foregroundGroup.add(optionsForegroundGreen);
        foregroundGroup.add(optionsForegroundOrange);
        foregroundGroup.add(optionsForegroundRed);
        foregroundGroup.add(optionsForegroundBlack);
        foregroundGroup.add(optionsForegroundWhite);
        foregroundGroup.add(optionsForegroundYellow);
        foregroundGroup.add(optionsForegroundBlue);

        optionsForeground.add(optionsForegroundGreen);
        optionsForeground.add(optionsForegroundOrange);
        optionsForeground.add(optionsForegroundRed);
        optionsForeground.add(optionsForegroundBlack);
        optionsForeground.add(optionsForegroundWhite);
        optionsForeground.add(optionsForegroundYellow);
        optionsForeground.add(optionsForegroundBlue);

        return optionsForeground;
    }

    public JMenu optionsBackgroundCreator(){
        JMenu optionsBackground = new JMenu("Background");

        ButtonGroup  backgroundGroup = new ButtonGroup();

        ColorIcon greenIcon = new ColorIcon(9,Color.green);
        ColorIcon orangeIcon = new ColorIcon(9,Color.orange);
        ColorIcon redIcon = new ColorIcon(9,Color.red);
        ColorIcon blackIcon = new ColorIcon(9,Color.black);
        ColorIcon whiteIcon = new ColorIcon(9,Color.white);
        ColorIcon yellowIcon = new ColorIcon(9,Color.yellow);
        ColorIcon blueIcon = new ColorIcon(9,Color.blue);

        JRadioButtonMenuItem optionsForegroundGreen = new JRadioButtonMenuItem("Green",greenIcon);
        JRadioButtonMenuItem optionsForegroundOrange = new JRadioButtonMenuItem("Orange",orangeIcon);
        JRadioButtonMenuItem optionsForegroundRed = new JRadioButtonMenuItem("Red",redIcon);
        JRadioButtonMenuItem optionsForegroundBlack = new JRadioButtonMenuItem("Black",blackIcon);
        JRadioButtonMenuItem optionsForegroundWhite = new JRadioButtonMenuItem("White",whiteIcon,true);
        JRadioButtonMenuItem optionsForegroundYellow = new JRadioButtonMenuItem("Yellow",yellowIcon);
        JRadioButtonMenuItem optionsForegroundBlue = new JRadioButtonMenuItem("Blue",blueIcon);

        optionsForegroundGreen.setForeground(new Color(0,150,0));
        optionsForegroundOrange.setForeground(Color.orange);
        optionsForegroundRed.setForeground(Color.red);
        optionsForegroundBlack.setForeground(Color.black);
        optionsForegroundWhite.setForeground(Color.white);
        optionsForegroundYellow.setForeground(Color.yellow);
        optionsForegroundBlue.setForeground(Color.blue);

        optionsForegroundGreen.addActionListener(backgroundActionListenerCreator(Color.green));
        optionsForegroundOrange.addActionListener(backgroundActionListenerCreator(Color.orange));
        optionsForegroundRed.addActionListener(backgroundActionListenerCreator(Color.red));
        optionsForegroundBlack.addActionListener(backgroundActionListenerCreator(Color.black));
        optionsForegroundWhite.addActionListener(backgroundActionListenerCreator(Color.white));
        optionsForegroundYellow.addActionListener(backgroundActionListenerCreator(Color.yellow));
        optionsForegroundBlue.addActionListener(backgroundActionListenerCreator(Color.blue));

        backgroundGroup.add(optionsForegroundGreen);
        backgroundGroup.add(optionsForegroundOrange);
        backgroundGroup.add(optionsForegroundRed);
        backgroundGroup.add(optionsForegroundBlack);
        backgroundGroup.add(optionsForegroundWhite);
        backgroundGroup.add(optionsForegroundYellow);
        backgroundGroup.add(optionsForegroundBlue);

        optionsBackground.add(optionsForegroundGreen);
        optionsBackground.add(optionsForegroundOrange);
        optionsBackground.add(optionsForegroundRed);
        optionsBackground.add(optionsForegroundBlack);
        optionsBackground.add(optionsForegroundWhite);
        optionsBackground.add(optionsForegroundYellow);
        optionsBackground.add(optionsForegroundBlue);

        return optionsBackground;
    }

    public JMenu optionsFontSizeCreator(){
        JMenu optionsFontSize = new JMenu("Font size");
        JMenuItem pts8 = new JMenuItem("8 pts");
        JMenuItem pts10 = new JMenuItem("10 pts");
        JMenuItem pts12 = new JMenuItem("12 pts");
        JMenuItem pts14 = new JMenuItem("14 pts");
        JMenuItem pts16 = new JMenuItem("16 pts");
        JMenuItem pts18 = new JMenuItem("18 pts");
        JMenuItem pts20 = new JMenuItem("20 pts");
        JMenuItem pts22 = new JMenuItem("22 pts");
        JMenuItem pts24 = new JMenuItem("24 pts");

        pts8.setFont(new Font("sans-serif", Font.PLAIN, 8));
        pts10.setFont(new Font("sans-serif", Font.PLAIN, 10));
        pts12.setFont(new Font("sans-serif", Font.PLAIN, 12));
        pts14.setFont(new Font("sans-serif", Font.PLAIN, 14));
        pts16.setFont(new Font("sans-serif", Font.PLAIN, 16));
        pts18.setFont(new Font("sans-serif", Font.PLAIN, 18));
        pts20.setFont(new Font("sans-serif", Font.PLAIN, 20));
        pts22.setFont(new Font("sans-serif", Font.PLAIN, 22));
        pts24.setFont(new Font("sans-serif", Font.PLAIN, 24));

        pts8.addActionListener(fontSizeActionListenerCreator(8));
        pts10.addActionListener(fontSizeActionListenerCreator(10));
        pts12.addActionListener(fontSizeActionListenerCreator(12));
        pts14.addActionListener(fontSizeActionListenerCreator(14));
        pts16.addActionListener(fontSizeActionListenerCreator(16));
        pts18.addActionListener(fontSizeActionListenerCreator(18));
        pts20.addActionListener(fontSizeActionListenerCreator(20));
        pts22.addActionListener(fontSizeActionListenerCreator(22));
        pts24.addActionListener(fontSizeActionListenerCreator(24));

        optionsFontSize.add(pts8);
        optionsFontSize.add(pts10);
        optionsFontSize.add(pts12);
        optionsFontSize.add(pts14);
        optionsFontSize.add(pts16);
        optionsFontSize.add(pts18);
        optionsFontSize.add(pts20);
        optionsFontSize.add(pts22);
        optionsFontSize.add(pts24);

        return optionsFontSize;
    }

    JLabel fontSize,fg,bg,status;

    public JPanel menuDownCreator(){
        JPanel menuDown = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel middlePanel = new JPanel();

        fg = new JLabel("fg",fgIcon,JLabel.LEFT);
        bg = new JLabel("bg",bgIcon,JLabel.LEFT);
        fontSize = new JLabel("size");
        status = new JLabel("new");

        FlowLayout fll = new FlowLayout(FlowLayout.LEFT);
        FlowLayout flr = new FlowLayout(FlowLayout.RIGHT);
        GridLayout menuDownLayout = new GridLayout(1,2);

        leftPanel.setLayout(fll);
        rightPanel.setLayout(flr);
        menuDown.setLayout(menuDownLayout);

        leftPanel.add(fg);
        leftPanel.add(bg);
        leftPanel.add(fontSize);
        rightPanel.add(status);

        menuDown.add(leftPanel);
        menuDown.add(rightPanel);

        return menuDown;
    }

    public ActionListener foregroundActionListenerCreator(Color color){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fgChanges++;
                if(fgChanges>=1) {
                    fgIcon = new ColorIcon(9,textArea.getForeground());
                    fg.setIcon(fgIcon);
                }
                textArea.setForeground(color);
                menuBarDown.repaint();
            }
        };
        return actionListener;
    };

    public ActionListener backgroundActionListenerCreator(Color color){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bgChanges++;
                if(bgChanges>=1) {
                    bgIcon = new ColorIcon(9,textArea.getBackground());
                    bg.setIcon(bgIcon);
                }
                textArea.setBackground(color);
                menuBarDown.repaint();
            }
        };
        return actionListener;
    };

    public ActionListener fontSizeActionListenerCreator(int size){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(new Font("sans-serif", Font.PLAIN, size));
                fontSize.setText(String.valueOf(textArea.getFont().getSize()));
                menuBarDown.repaint();
            }
        };
        return actionListener;
    };


    public static void main(String[] args) {
        new GUI11_24c_Kucharski_s20731();
    }
}
class ColorIcon implements Icon {

    private int size;
    private Color color;

    public ColorIcon(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.fillOval(x, y, size, size);
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    public void setColor(Color color){
        this.color = color;
    }
}