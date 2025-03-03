import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class pwd_crack extends JFrame {
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextArea outputArea;
    private JButton startButton;
    
    private final String correctPassword = "apple"; 
    
    public pwd_crack() {
        setTitle("Password Cracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);
        
        inputPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);
        
        startButton = new JButton("Crack Password");
        inputPanel.add(startButton);
        
        add(inputPanel, BorderLayout.NORTH);
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> startCracking()).start();
            }
        });
    }
    
    private void startCracking() {
        outputArea.setText("Starting Dictionary Attack.......\n");
        if (DictionaryAttack()) {
            outputArea.append("Password found using Dictionary Attack\n");
        } else {
            outputArea.append("Dictionary Attack failed. Starting Brute Force Attack........\n");
            bruteForceAttack();
        }
    }
    
    private boolean DictionaryAttack() {
        HashSet<String> dictionary = loadDictionary("D:\\Maryam Level3\\Semester 2\\Info Sec\\Section\\Task1\\Chinese-common-password-list-top-10000.txt");
        if (dictionary.contains(correctPassword)) {
            outputArea.append("Success: Password found in dictionary\n");
            return true;
        }
        return false;
    }
    
    private HashSet<String> loadDictionary(String filename) {
        HashSet<String> words = new HashSet<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine().trim());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            outputArea.append("Dictionary file not found\n");
        }
        return words;
    }
    
    private void bruteForceAttack() {
        char[] alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] guess = new char[5];
        
        for (char a : alpha) {
            for (char b : alpha) {
                for (char c : alpha) {
                    for (char d : alpha) {
                        for (char e : alpha) {
                            guess[0] = a;
                            guess[1] = b;
                            guess[2] = c;
                            guess[3] = d;
                            guess[4] = e;
                            String trail = new String(guess);
                            outputArea.append("Trying: " + trail + "\n");
                            if (trail.equals(correctPassword)) {
                                outputArea.append("Success: Password cracked using Brute Force!\n");
                                return;
                            }
                        }
                    }
                }
            }
        }
        outputArea.append("Brute Force Attack failed!\n");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new pwd_crack().setVisible(true));
    }
}
