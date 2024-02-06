import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeskovkyGUI extends JFrame {
    private JTextField text;
    private JCheckBox cB;
    private JRadioButton rB1;
    private JRadioButton rB2;
    private JRadioButton rB3;

    private JPanel panel;
    private JButton bTnext;
    private JButton bTsave;
    private JButton bTprev;
    private final List<Deskovky> BGList = new ArrayList<>();
    private int index = 0;
    private final int[] selectedScore = {1};


    public Deskovky getBoG(int i){
        return BGList.get(i);
    }
    public DeskovkyGUI() {
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(rB1);
        btnGroup.add(rB2);
        btnGroup.add(rB3);
        rB1.addItemListener(e -> handleRadioButtonClick(1));
        rB2.addItemListener(e -> handleRadioButtonClick(2));
        rB3.addItemListener(e -> handleRadioButtonClick(3));
        bTprev.addActionListener(e -> {
            if (index < BGList.size() - 1) {
                index++;
                displayBG(getBoG(index));
            }
        });
        bTnext.addActionListener(e -> {
            if (index > 0){
                index--;
                displayBG(getBoG(index));
            }
        });

        bTsave.addActionListener(e -> saveToFile());
        readingFromFIle();
        displayBG(getBoG(index));
    }

    public void readingFromFIle() {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader("Deskovky.txt")))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(";");
                String nazev = (parts[0]);
                boolean koupeno = parts[1].equals("koupeno");
                int oblib = Integer.parseInt(parts[2]);
                Deskovky bg = new Deskovky(nazev, koupeno, oblib);
                BGList.add(bg);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            System.err.println("Wrongly formatted number: " + e.getLocalizedMessage());
        }
    }
    private void handleRadioButtonClick(int score) {
        selectedScore[0] = score;
    }
    public void saveToFile() {
        int selectedIndex = index;
        Deskovky selectedBG = BGList.get(selectedIndex);
        selectedBG.setName(text.getText());
        selectedBG.setOwned(cB.isSelected());
        selectedBG.setScore(selectedScore[0]);

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("Deskovky.txt")))) {
            for (Deskovky bg : BGList) {
                writer.println(bg.getName() + ";" + (bg.isOwned() ? "koupeno" : "nekoupeno") + ";" + bg.getScore());
            }
            JOptionPane.showMessageDialog(this, "Změny byly uloženy do souboru.", "Zpráva uložena", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getLocalizedMessage());
        }
    }
    public void displayBG(Deskovky bg){
        text.setText(bg.getName());
        cB.setSelected(bg.isOwned());
        switch (bg.getScore()){
            case 1 -> rB1.setSelected(true);
            case 2 -> rB2.setSelected(true);
            case 3 -> rB3.setSelected(true);
        }
    }
    public static void main(String[] args) {
        DeskovkyGUI desk = new DeskovkyGUI();
        desk.setContentPane(desk.panel);
        desk.setSize(500, 250);
        desk.setDefaultCloseOperation(EXIT_ON_CLOSE);
        desk.setTitle("Deskové hry");
        desk.setVisible(true);
    }
}

