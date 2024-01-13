import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KalkulatorGUI extends JFrame implements ActionListener {
    private JTextField textField;
    private JTextArea historyTextArea;
    private double angkaPertama, angkaKedua, hasil;
    private int operasi;

    public KalkulatorGUI() {
        // Konfigurasi frame
        setTitle("Kalkulator Sederhana");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama dengan layout GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        Color lightBlue = new Color(173, 216, 230);
        Color lightGray = new Color(192, 192, 192);
        Color darkBlue = new Color(0, 0, 128);
        Color white = Color.WHITE;

        // TextField untuk menampilkan input dan output
        textField = new JTextField();
        textField.setEditable(false);
        textField.setBackground(white);
        textField.setForeground(darkBlue);
        textField.setHorizontalAlignment(JTextField.RIGHT);

        // JTextArea untuk menampilkan history
        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        historyTextArea.setBackground(lightGray);
        historyTextArea.setForeground(darkBlue);

        // Label "History"
        JLabel historyLabel = new JLabel("History");
        historyLabel.setHorizontalAlignment(JLabel.CENTER);
        historyLabel.setForeground(darkBlue);

        // Mengatur properti GridBag untuk TextField
        GridBagConstraints gbcTextField = new GridBagConstraints();
        gbcTextField.gridx = 0;
        gbcTextField.gridy = 0;
        gbcTextField.gridwidth = 5;
        gbcTextField.fill = GridBagConstraints.BOTH;
        gbcTextField.insets = new Insets(5, 5, 5, 5);
        panel.add(textField, gbcTextField);

        // Mengatur properti GridBag untuk JLabel "History"
        GridBagConstraints gbcHistoryLabel = new GridBagConstraints();
        gbcHistoryLabel.gridx = 0;
        gbcHistoryLabel.gridy = 1;
        gbcHistoryLabel.gridwidth = 5;
        gbcHistoryLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcHistoryLabel.insets = new Insets(5, 5, 0, 5);
        panel.add(historyLabel, gbcHistoryLabel);

        // Mengatur properti GridBag untuk JTextArea
        GridBagConstraints gbcHistoryTextArea = new GridBagConstraints();
        gbcHistoryTextArea.gridx = 0;
        gbcHistoryTextArea.gridy = 2;
        gbcHistoryTextArea.gridwidth = 5;
        gbcHistoryTextArea.gridheight = 2;
        gbcHistoryTextArea.fill = GridBagConstraints.BOTH;
        gbcHistoryTextArea.insets = new Insets(0, 5, 5, 5);
        panel.add(new JScrollPane(historyTextArea), gbcHistoryTextArea);

        // Tombol-tombol angka dan operasi matematika
        String[] buttonLabels = {
                "7", "8", "9", "/", "√",
                "4", "5", "6", "*", "^",
                "1", "2", "3", "-", "%",
                "0", ".", "=", "+", "C", "AC"
        };

        // Mengatur properti GridBag untuk Tombol
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.fill = GridBagConstraints.BOTH;
        gbcButton.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.addActionListener(this);

            // Menyesuaikan tata letak tombol
            gbcButton.gridx = i % 5;
            gbcButton.gridy = i / 5 + 4;

            // Menambahkan tombol ke panel
            panel.add(button, gbcButton);

            // Mengatur warna latar belakang dan teks tombol
            button.setBackground(lightGray);
            button.setForeground(darkBlue);
        }

        // Mengatur warna latar belakang panel
        panel.setBackground(lightBlue);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9.]")) {
            textField.setText(textField.getText() + command);
        } else if (command.matches("[*/\\-+]")) {
            angkaPertama = Double.parseDouble(textField.getText());
            switch (command) {
                case "/":
                    operasi = 4;
                    break;
                case "*":
                    operasi = 3;
                    break;
                case "-":
                    operasi = 2;
                    break;
                case "+":
                    operasi = 1;
                    break;
            }
            textField.setText("");
        } else if (command.equals("=")) {
            if (!textField.getText().isEmpty()) {
                angkaKedua = Double.parseDouble(textField.getText());
                switch (operasi) {
                    case 1:
                        hasil = angkaPertama + angkaKedua;
                        break;
                    case 2:
                        hasil = angkaPertama - angkaKedua;
                        break;
                    case 3:
                        hasil = angkaPertama * angkaKedua;
                        break;
                    case 4:
                        if (angkaKedua == 0) {
                            textField.setText("Error: Tidak bisa dibagi dengan nol");
                            return;
                        } else {
                            hasil = angkaPertama / angkaKedua;
                        }
                        break;
                    case 5: // Akar pangkat
                        hasil = Math.sqrt(angkaKedua);
                        break;
                    case 6: // Pangkat
                        hasil = Math.pow(angkaPertama, angkaKedua);
                        break;
                    case 7: // Persen
                        hasil = angkaPertama * (angkaKedua / 100);
                        break;
                }
                textField.setText(String.valueOf(hasil));

                // Menambahkan entri ke history
                String historyEntry = angkaPertama + " " + getOperatorSymbol(operasi) + " " + angkaKedua + " = " + hasil + "\n";
                historyTextArea.append(historyEntry);
            }
        } else if (command.equals("√")) {
            if (!textField.getText().isEmpty()) {
                angkaPertama = Double.parseDouble(textField.getText());
                hasil = Math.sqrt(angkaPertama);
                textField.setText(String.valueOf(hasil));

                // Menambahkan entri ke history
                String historyEntry = "√" + angkaPertama + " = " + hasil + "\n";
                historyTextArea.append(historyEntry);
            }
        } else if (command.equals("^")) {
            if (!textField.getText().isEmpty()) {
                angkaPertama = Double.parseDouble(textField.getText());
                operasi = 6; // Pangkat
                textField.setText("");
            }
        } else if (command.equals("%")) {
            if (!textField.getText().isEmpty()) {
                angkaPertama = Double.parseDouble(textField.getText());
                operasi = 7; // Persen
                textField.setText("");
            }
        } else if (command.equals("C")) {
            textField.setText("");
        } else if (command.equals("AC")) {
            textField.setText("");
            historyTextArea.setText("");
        }
    }

    private String getOperatorSymbol(int operator) {
        switch (operator) {
            case 1:
                return "+";
            case 2:
                return "-";
            case 3:
                return "*";
            case 4:
                return "/";
            case 5:
                return "√";
            case 6:
                return "^";
            case 7:
                return "%";
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }

            new KalkulatorGUI();
        });
    }
}
