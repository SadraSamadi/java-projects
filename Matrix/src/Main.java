import javax.swing.*;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class Main extends JFrame {

    public Main() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        final JRadioButton[][] A = new JRadioButton[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                A[i][j] = new JRadioButton();
                A[i][j].setBounds(i * 16, j * 16, 16, 16);
                A[i][j].setBackground(Color.LIGHT_GRAY);
                panel.add(A[i][j]);
            }

        JButton generate = new JButton("Generate");
        final JButton reverse = new JButton("Invert");
        final JButton selectAll = new JButton("Select All");
        final JButton clearAll = new JButton("Clear All");
        generate.setBounds(140, 4, 120, 30);
        reverse.setBounds(140, 35, 120, 30);
        selectAll.setBounds(140, 66, 120, 30);
        clearAll.setBounds(140, 97, 120, 30);
        panel.add(generate);
        panel.add(reverse);
        panel.add(selectAll);
        panel.add(clearAll);

        JButton copy = new JButton("Copy to ClipBoard");
        copy.setBounds(2, 159, 265, 30);
        panel.add(copy);

        final JTextField text = new JTextField();
        text.setBounds(2, 132, 266, 24);
        panel.add(text);

        setTitle("Matrix");
        getContentPane().add(panel);
        setBounds(500, 300, 275, 220);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final byte[][] B = new byte[8][8];
        final byte[] C = new byte[8];
        final StringBuilder sb = new StringBuilder();

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (A[i][j].isSelected())
                            B[i][j] = 1;
                        else
                            B[i][j] = 0;
                    }
                }

                for (int i = 0; i < 8; i++) {
                        C[i] = (byte) ((B[0][i] * 128) +
                                       (B[1][i] * 64) +
                                       (B[2][i] * 32) +
                                       (B[3][i] * 16) +
                                       (B[4][i] * 8) +
                                       (B[5][i] * 4) +
                                       (B[6][i] * 2) +
                                       (B[7][i]));
                }

                sb.delete(0, sb.length());
                for (byte b : C) {
                    if (b >= 0 && b <= 9)
                        sb.append(String.format("0x0%X, ", b));
                    else
                        sb.append(String.format("0x%X, ", b));
                }

                sb.setLength(sb.length() - 2);
                text.setText(sb.toString());
            }
        });

        reverse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++) {
                        if (A[i][j].isSelected())
                            A[i][j].setSelected(false);
                        else
                            A[i][j].setSelected(true);
                    }
            }
        });

        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++) {
                        A[i][j].setSelected(true);
                    }
            }
        });

        clearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++) {
                        A[i][j].setSelected(false);
                    }
            }
        });

        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy(sb.toString());
            }
        });
    }

    public static void copy(String text) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        clipboard.setContents(new StringSelection(text), null);
    }

    public static void main(String[] args) throws Exception {
        new Main();
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    
}