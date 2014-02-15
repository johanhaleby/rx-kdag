package com.jayway.kdag.rx;

import rx.Observable;
import rx.observables.SwingObservable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ButtonCombinationExample extends JFrame {

    private static final String PASSWORD = "ABBABA";

    public ButtonCombinationExample() {
        renderUI();
    }

    private void renderUI() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new GridLayout(2, 2));

        JButton buttonA = new JButton("A");
        buttonA.setBounds(50, 60, 80, 30);
        JButton buttonB = new JButton("B");
        buttonA.setBounds(50, 60, 80, 30);
        final TextArea buttonClickTextArea = new TextArea();
        buttonClickTextArea.setEditable(false);
        final TextArea secretRevelationTextArea = new TextArea();
        secretRevelationTextArea.setEditable(false);
        panel.add(secretRevelationTextArea, JLabel.CENTER);
        panel.add(buttonClickTextArea, JLabel.CENTER);
        panel.add(buttonB, JLabel.CENTER);
        panel.add(buttonA, JLabel.CENTER);

        Observable<ActionEvent> buttonAObservable = SwingObservable.fromButtonAction(buttonA);
        Observable<ActionEvent> buttonBObservable = SwingObservable.fromButtonAction(buttonB);

        // TODO Implement

        setTitle("Enter secret combination");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ButtonCombinationExample ex = new ButtonCombinationExample();
            ex.setVisible(true);
        });
    }
}
