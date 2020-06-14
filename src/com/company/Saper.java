package com.company;

import javax.swing.*;
import java.awt.*;

public class Saper{

    public static void main(String[] args) {
        String[] sizeNames = {"Mała", "Średnia", "Duża", "Ogromna"};


        JFrame init = new JFrame("Nowa gra");
        init.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init.setSize(400, 150);
        init.setLocationRelativeTo(null);
        init.setLayout(new FlowLayout());
        init.setResizable(false);

        init.setAlwaysOnTop(true);
        JLabel sizeLabel = new JLabel("Wybierz wielkość planszy: ");


        JComboBox<String> size = new JComboBox<>(sizeNames);
        size.setPreferredSize(new Dimension(100, 20));

        JLabel difficultyLabel = new JLabel("Wybierz poziom trudności: ");

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new FlowLayout());

        ButtonGroup difficultyGroup = new ButtonGroup();

        JRadioButton easy = new JRadioButton("Łatwy");
        easy.setActionCommand("easy");
        difficultyGroup.add(easy);
        JRadioButton medium = new JRadioButton("Średni");
        medium.setActionCommand("medium");
        medium.setSelected(true);
        difficultyGroup.add(medium);
        JRadioButton hard = new JRadioButton("Trudny");
        hard.setActionCommand("hard");
        difficultyGroup.add(hard);
        JRadioButton impossible = new JRadioButton("Niemożliwy");
        impossible.setActionCommand("impossible");
        difficultyGroup.add(impossible);

        difficultyPanel.add(easy);
        difficultyPanel.add(medium);
        difficultyPanel.add(hard);
        difficultyPanel.add(impossible);

        JButton startGameButton = new JButton("Start");

        init.add(sizeLabel);
        init.add(size);
        init.add(difficultyLabel);
        init.add(difficultyPanel);
        init.add(startGameButton);



        JFrame game = new JFrame("Saper");
        game.setSize(600, 600);
        game.setLocationRelativeTo(null);
        game.setLayout(new BorderLayout());
        game.setResizable(false);
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Gra");
        JMenuItem newGameItem = new JMenuItem("Nowa gra");
        gameMenu.add(newGameItem);
        menuBar.add(gameMenu);
        game.setJMenuBar(menuBar);



        game.setVisible(true);
        init.setVisible(true);

        startGameButton.addActionListener(actionEvent1 -> {
            String difficultySelection = difficultyGroup.getSelection().getActionCommand();
            String sizeSelection = String.valueOf(size.getSelectedItem());

            JLabel messageField = new JLabel("");
            messageField.setPreferredSize(new Dimension(200, 20));
            game.add(messageField, BorderLayout.SOUTH);

            Plansza gameField = new Plansza(sizeSelection, difficultySelection, messageField);
            game.add(gameField, BorderLayout.CENTER);
            switch(sizeSelection) {
                case "Mała" : {
                    game.setSize(9*15+1, 8*15+82);
                    break;
                }
                case "Średnia" : {
                    game.setSize(17*15+1, 16*15+82);
                    break;
                }
                case "Duża" : {
                    game.setSize(25*15+1, 30*15+82);
                    break;
                }
                case "Ogromna" : {
                    game.setSize(61*15+1, 60*15+82);
                    break;
                }

            }
            game.setLocationRelativeTo(null);
            game.setVisible(true);
            init.setVisible(false);
        });
        newGameItem.addActionListener(actionEvent -> {
            game.setVisible(false);
            game.getContentPane().removeAll();
            game.repaint();
            game.setSize(600, 600);
            game.setLocationRelativeTo(null);
            init.setLocationRelativeTo(null);

            init.setVisible(true);
            game.setVisible(true);
        });
    }
}
