package GUI;

import Entities.Character;
import Initial.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class LoginWindow extends JFrame implements ActionListener, MouseListener, KeyListener {
    JLabel logoLabel;
    JTextField mailField;
    JPasswordField passwordField;
    JButton loginButton;
    JButton chooseButton;
    JLabel chooseLabel;
    JComboBox<Entities.Character> characterJComboBox;
    JLabel statsLabel;
    JTextArea statsField;
    Entities.Character chosenCharacter;
    public LoginWindow() throws IOException, FontFormatException {
        ImageIcon game_logo = new ImageIcon("src\\Extras\\login_game_logo.png");
        File fontFile = new File("src\\Extras\\LifeCraft_Font.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        setTitle("Login - World of Marcel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 450);
        setIconImage(game_logo.getImage());
        getContentPane().setBackground(new Color(3, 7, 36));
        setLocationRelativeTo(null);
        setLayout(null);

        logoLabel = new JLabel();
        logoLabel.setIcon(game_logo);
        logoLabel.setBounds(200, 0, 400, 160);

        mailField = new JTextField("E-mail address");
        mailField.addMouseListener(this);
        mailField.setBounds(220, 200, 360, 40);
        mailField.setFont(new Font("Consolas", Font.BOLD, 20));
        mailField.setBackground(Color.DARK_GRAY);
        mailField.setForeground(Color.LIGHT_GRAY);
        mailField.setCaretColor(Color.WHITE);

        passwordField = new JPasswordField("Password");
        passwordField.setEchoChar((char)0);
        passwordField.addMouseListener(this);
        passwordField.setBounds(220, 260, 360, 40);
        passwordField.setFont(new Font("Consolas", Font.BOLD, 20));
        passwordField.setBackground(Color.DARK_GRAY);
        passwordField.setForeground(Color.LIGHT_GRAY);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.addKeyListener(this);

        loginButton = new JButton("Login");
        loginButton.setBounds(350, 360, 100, 30);
        loginButton.setFocusable(false);
        loginButton.setFont(new Font("Consolas", Font.BOLD, 20));
        loginButton.addActionListener(this);

        chooseButton = new JButton("Choose");
        chooseButton.setBounds(350, 360, 100, 30);
        chooseButton.setFocusable(false);
        chooseButton.setFont(font.deriveFont(20f));
        chooseButton.addActionListener(this);
        chooseButton.setBackground(new Color(139, 69, 19));
        chooseButton.setForeground(new Color(253, 198, 10));
        chooseButton.setEnabled(false);
        chooseButton.setVisible(false);

        chooseLabel = new JLabel("Choose character:");
        chooseLabel.setBounds(340, 160, 200, 20);
        chooseLabel.setVisible(false);
        chooseLabel.setFont(font.deriveFont(20f));
        chooseLabel.setForeground(new Color(253, 198, 10));

        characterJComboBox = new JComboBox<>();
        characterJComboBox.setEditable(false);
        characterJComboBox.setBounds(300, 185, 200, 30);
        characterJComboBox.setFont(font.deriveFont(20f));
        characterJComboBox.setForeground(new Color(253, 198, 10));
        characterJComboBox.setBackground(new Color(139, 69, 19));
        characterJComboBox.setEnabled(false);
        characterJComboBox.setVisible(false);
        characterJComboBox.addActionListener(this);

        statsLabel = new JLabel("Stats:");
        statsLabel.setBounds(378, 220, 200, 20);
        statsLabel.setVisible(false);
        statsLabel.setFont(font.deriveFont(20f));
        statsLabel.setForeground(new Color(253, 198, 10));

        statsField = new JTextArea();
        statsField.setBounds(300, 240, 200, 120);
        statsField.setForeground(new Color(253, 198, 10));
        statsField.setBackground(new Color(3, 7, 36));
        statsField.setFont(font.deriveFont(18f));
        statsField.setVisible(false);
        statsField.setEditable(false);


        add(logoLabel);
        add(mailField);
        add(passwordField);
        addMouseListener(this);
        add(loginButton);
        add(chooseButton);
        add(chooseLabel);
        add(characterJComboBox);
        add(statsLabel);
        add(statsField);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            String password = new String(passwordField.getPassword());
            String mail = mailField.getText();
            int index = 0;
            //verificarea corectitudinii mail-ului si parolei
            while( index < Game.getInstance().accounts.size() ) {
                if(Game.getInstance().accounts.get(index).information.getCredentials().getMail().
                        equals(mail)) {
                    break;
                }
                index++;
            }
            if( index < Game.getInstance().accounts.size() ) {
                //mail si parola corecte -> interfata in care se alege personajul
                if( Game.getInstance().accounts.get(index).information.getCredentials().
                        getPassword().equals(password) ) {
                    mailField.setEnabled(false);
                    passwordField.setEnabled(false);
                    loginButton.setEnabled(false);
                    mailField.setVisible(false);
                    passwordField.setVisible(false);
                    loginButton.setVisible(false);
                    chooseButton.setEnabled(true);
                    chooseButton.setVisible(true);
                    chooseLabel.setVisible(true);
                    for(Entities.Character character : Game.getInstance().accounts.get(index).characters) {
                        characterJComboBox.addItem(character);
                    }
                    characterJComboBox.setEnabled(true);
                    characterJComboBox.setVisible(true);
                    statsLabel.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Wrong e-mail or password!",
                            "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong e-mail or password!",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        if(e.getSource() == characterJComboBox) {
            statsField.setText(((Entities.Character)characterJComboBox.getSelectedItem()).stats());
            statsField.setVisible(true);
            chosenCharacter = (Character) characterJComboBox.getSelectedItem();
        }
        if(e.getSource() == chooseButton) {
            setVisible(false);
            dispose();
            try {
                new GameWindow(chosenCharacter);
            }
            catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == mailField && mailField.getText().equals("E-mail address")) {
            mailField.setText("");
        }
        else if(e.getSource() == passwordField && new String(passwordField.getPassword()).
                equals("Password")) {
            passwordField.setText("");
        }
        if(e.getSource() != mailField && mailField.getText().equals("")) {
            mailField.setText("E-mail address");
        }
        if(e.getSource() != passwordField && new String(passwordField.getPassword()).equals("")) {
            passwordField.setEchoChar((char)0);
            passwordField.setText("Password");
        }

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
        passwordField.setEchoChar('*');
    }
    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
