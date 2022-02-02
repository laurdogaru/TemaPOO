package GUI;

import Entities.*;
import Initial.*;
import SpellsAndPotions.Potion;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.Character;

public class GameWindow extends JFrame {
    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public GameWindow(Entities.Character chosenCharacter) throws IOException, FontFormatException{
        ImageIcon game_logo = new ImageIcon("src\\Extras\\login_game_logo.png");
        File fontFile = new File("src\\Extras\\LifeCraft_Font.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        Game game = Game.getInstance();
        Grid grid = Grid.generateRandomMap();
        grid.currentCharacter = chosenCharacter;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("World of Marcel");
        setSize(1200, 675);
        getContentPane().setBackground(new Color(3, 7, 36));
        setIconImage(game_logo.getImage());
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setBackground(new Color(139, 69, 19));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        gridPanel.setLayout(new GridLayout(grid.length, grid.width));
        for(int i = 0 ; i < grid.length; i++) {
            for(int j = 0 ; j < grid.width ; j++) {
                JLabel label = new JLabel();
                label.setVerticalAlignment(JLabel.CENTER);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
                label.setFont(font.deriveFont(40f));
                label.setForeground(Color.BLACK);
                String labelText;
                if( grid.get(i).get(j) == grid.currentCell ) {
                    labelText = "P";
                    labelText += Character.valueOf(grid.get(i).get(j).element.toCharacter()).toString();
                    label.setText(labelText);
                    gridPanel.add(label);
                    continue;
                }
                if( !grid.get(i).get(j).visited ) {
                    labelText = Character.valueOf('?').toString();
                } else {
                    labelText = Character.valueOf(grid.get(i).get(j).element.toCharacter()).toString();
                }
                label.setText(labelText);
                gridPanel.add(label);
            }
        }
        gridBagConstraints.weightx = 1.5;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(gridPanel, gridBagConstraints);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setBackground(new Color(253, 198, 10));
        optionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        optionsPanel.setLayout(new GridLayout(2, 1));
        gridBagConstraints.weightx = 1;
        add(optionsPanel, gridBagConstraints);

        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(new Color(253, 198, 10));
        statsPanel.setLayout(new BorderLayout());
        statsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        optionsPanel.add(statsPanel);

        JLabel statsLabel = new JLabel("Stats");
        statsLabel.setOpaque(true);
        statsLabel.setForeground(new Color(3, 7, 36));
        statsLabel.setBackground(new Color(253, 198, 10));
        statsLabel.setFont(font.deriveFont(40f));
        statsLabel.setHorizontalAlignment(JLabel.CENTER);
        statsPanel.add(statsLabel,BorderLayout.NORTH);

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBackground(new Color(253, 198, 10));
        inventoryPanel.setLayout(new BorderLayout());
        inventoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        optionsPanel.add(inventoryPanel);

        JLabel inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setOpaque(true);
        inventoryLabel.setForeground(new Color(3, 7, 36));
        inventoryLabel.setBackground(new Color(253, 198, 10));
        inventoryLabel.setFont(font.deriveFont(40f));
        inventoryLabel.setHorizontalAlignment(JLabel.CENTER);
        inventoryPanel.add(inventoryLabel, BorderLayout.NORTH);

        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new GridLayout(1, 2));
        progressPanel.setBackground(new Color(253, 198, 10));
        statsPanel.add(progressPanel, BorderLayout.EAST);

        JProgressBar healthBar = new JProgressBar(0, grid.currentCharacter.maxHealth);
        healthBar.setValue(grid.currentCharacter.maxHealth);
        healthBar.setForeground(Color.RED);
        healthBar.setString(Integer.valueOf(grid.currentCharacter.currentHealth).toString());
        healthBar.setStringPainted(true);
        healthBar.setFont(font.deriveFont(35f));
        healthBar.setBackground(Color.BLACK);
        healthBar.setPreferredSize(new Dimension(70, 0));
        progressPanel.add(healthBar);

        JProgressBar manaBar = new JProgressBar(0, grid.currentCharacter.maxMana);
        manaBar.setValue(grid.currentCharacter.maxMana);
        manaBar.setForeground(Color.BLUE);
        manaBar.setString(Integer.valueOf(grid.currentCharacter.currentMana).toString());
        manaBar.setStringPainted(true);
        manaBar.setFont(font.deriveFont(35f));
        manaBar.setBackground(Color.BLACK);
        manaBar.setPreferredSize(new Dimension(70, 0));
        progressPanel.add(manaBar);

        JTextArea statsField = new JTextArea();
        statsField.setBackground(new Color(253, 198, 10));
        statsField.setFont(font.deriveFont(35f));
        String data = "Name: " + grid.currentCharacter.name + "\n";
        data += grid.currentCharacter.stats();
        statsField.setText(data);
        statsField.setEditable(false);
        statsField.setForeground(Color.BLACK);
        statsPanel.add(statsField, BorderLayout.WEST);

        JPanel inventoryLeftPanel = new JPanel();
        inventoryLeftPanel.setLayout(new GridLayout(3, 1));
        inventoryLeftPanel.setBackground(new Color(253, 198, 10));
        inventoryPanel.add(inventoryLeftPanel, BorderLayout.WEST);

        JLabel coinsLabel = new JLabel();
        String coinsString = "Coins: ";
        coinsString += grid.currentCharacter.inventory.nrCoins;
        coinsLabel.setText(coinsString);
        coinsLabel.setOpaque(true);
        coinsLabel.setForeground(Color.BLACK);
        coinsLabel.setBackground(new Color(253, 198, 10));
        coinsLabel.setFont(font.deriveFont(35f));
        inventoryLeftPanel.add(coinsLabel);

        JPanel potionsPanel = new JPanel();
        potionsPanel.setBackground(new Color(253, 198, 10));
        potionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        inventoryLeftPanel.add(potionsPanel);

        JLabel potionsLabel = new JLabel();
        String potionsString = "Potions: ";
        potionsLabel.setText(potionsString);
        potionsLabel.setOpaque(true);
        potionsLabel.setForeground(Color.BLACK);
        potionsLabel.setBackground(new Color(253, 198, 10));
        potionsLabel.setFont(font.deriveFont(35f));
        potionsPanel.add(potionsLabel);

        JComboBox<Potion> potionsComboBox = new JComboBox<>();
        potionsComboBox.setEditable(false);
        potionsComboBox.setFont(font.deriveFont(35f));
        potionsComboBox.setBackground(new Color(3, 7, 36));
        potionsPanel.add(potionsComboBox);

        JLabel weightLabel = new JLabel();
        String weightString = "Remaining weight: ";
        weightString += grid.currentCharacter.inventory.remainingWeight();
        weightLabel.setText(weightString);
        weightLabel.setOpaque(true);
        weightLabel.setForeground(Color.BLACK);
        weightLabel.setBackground(new Color(253, 198, 10));
        weightLabel.setFont(font.deriveFont(35f));
        inventoryLeftPanel.add(weightLabel);

        setVisible(true);
    }
}
