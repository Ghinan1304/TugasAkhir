import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Kelas Game yang menangani logika permainan umum
class Game {
    protected int targetNumber;
    protected int attempts;

    public Game() {
        targetNumber = (int) (Math.random() * 100) + 1; // Generate random angka 1 - 100
        attempts = 0;
    }

    // Method untuk mereset permainan
    public void resetGame() {
        targetNumber = (int) (Math.random() * 100) + 1;
        attempts = 0;
    }

    // Method untuk memeriksa tebakan
    public String checkGuess(int userGuess) {
        attempts++;
        if (userGuess < targetNumber) {
            return "Tebakan terlalu kecil!";
        } else if (userGuess > targetNumber) {
            return "Tebakan terlalu besar!";
        } else {
            return "Selamat! Anda menebak dengan benar dalam " + attempts + " percobaan.";
        }
    }

    // Getters untuk targetNumber dan attempts (opsional)
    public int getTargetNumber() {
        return targetNumber;
    }

    public int getAttempts() {
        return attempts;
    }
}

// Kelas TebakAngkaGame yang mewarisi dari Game
class TebakAngkaGame extends Game {
    public TebakAngkaGame() {
        // Create main frame
        JFrame frame = new JFrame("Tebak Angka");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        // Create main panel with background color
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        // Create components
        JLabel instructionLabel = new JLabel("Tebak angka antara 1 dan 100", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.BOLD, 18));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        JButton submitButton = new JButton("Submit");
        JLabel resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add padding to components
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add components to panel
        mainPanel.add(instructionLabel, BorderLayout.NORTH);
        mainPanel.add(inputField, BorderLayout.CENTER);
        mainPanel.add(submitButton, BorderLayout.EAST);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        // Add panel
        frame.add(mainPanel);

        // Add button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int userGuess = Integer.parseInt(inputField.getText());

                    String result = checkGuess(userGuess);  // Using inherited method
                    resultLabel.setText(result);

                    if (result.startsWith("Selamat")) {
                        inputField.setEditable(false);
                        submitButton.setEnabled(false);

                        // Add play again option
                        int playAgain = JOptionPane.showConfirmDialog(frame, "Main lagi?", "Game Selesai", JOptionPane.YES_NO_OPTION);
                        if (playAgain == JOptionPane.YES_OPTION) {
                            resetGame(inputField, resultLabel, submitButton);
                        } else {
                            frame.dispose();
                        }
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Masukkan angka yang valid!");
                }

                inputField.setText("");
            }
        });

        // Show frame
        frame.setVisible(true);
    }

    private void resetGame(JTextField inputField, JLabel resultLabel, JButton submitButton) {
        resetGame();  // Using inherited method to reset the game
        inputField.setEditable(true);
        submitButton.setEnabled(true);
        resultLabel.setText("");
    }
}

// Main class untuk menjalankan aplikasi
public class TebakAngka {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TebakAngkaGame());
    }
}