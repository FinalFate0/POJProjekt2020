package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Plansza extends JPanel {

    private final int SIZE_OF_CELL = 15;

    private final int EMPTY = 0;
    private final int COVER = 10;
    private final int COVER_IMAGE = 9;
    private final int MARKED = 10;
    private final int MARK_IMAGE = 10;
    private final int MINE = 9;
    private final int MINE_IMAGE = 11;
    private final int WRONG_MARK_IMAGE = 12;
    private final int COVERED_MINE = COVER+MINE;
    private final int MARKED_MINE = MARKED+COVERED_MINE;

    private final JLabel messageField;
    private boolean duringGame;
    private int sizeY;
    private int sizeX;
    private double difficulty;
    private int cells;
    private int mines;
    private int liveMines;
    private String liveMinesMessage;
    private int[] minefield;

    private Image[] image;


    public Plansza(String size, String difficulty, JLabel messageField) {

        switch(size) {
            case "Mała" : {
                this.sizeX = 8;
                this.sizeY = 8;

                break;
            }
            case "Średnia" : {
                this.sizeX = 16;
                this.sizeY = 16;
                break;
            }
            case "Duża" : {
                this.sizeX = 24;
                this.sizeY = 30;
                break;
            }
            case "Ogromna" : {
                this.sizeX = 60;
                this.sizeY = 60;
                break;
            }
        }
        switch(difficulty) {
            case "easy" : {
                this.difficulty = 0.10;
                break;
            }
            case "medium" : {
                this.difficulty = 0.15;
                break;
            }
            case "hard" : {
                this.difficulty = 0.20;
                break;
            }
            case "impossible" : {
                this.difficulty = 0.25;
                break;
            }
        }
        this.messageField = messageField;
        this.mines = (int)((sizeY*sizeX)*this.difficulty);
        initialState();
    }
    private void initialState() {

        this.setPreferredSize(new Dimension(sizeY, sizeX));
        this.image = new Image[13];

        for(int i=0;i<13;i++) {
            String path = "src/resources/" + i + ".png";
            image[i] = (new ImageIcon(path)).getImage();
        }
        addMouseListener(new MyMouseAdapter());

        startGame();
    }
    private void startGame() {

        int cell;
        Random rand = new Random();

        duringGame = true;

        liveMines = mines;
        cells = sizeY*sizeX;
        minefield = new int[cells];
        for (int i = 0; i < cells; i++) {

            minefield[i] = COVER;
        }
        liveMinesMessage = "Pozostałe miny: " + liveMines;
        messageField.setText(liveMinesMessage);
        int i=0;
        while (mines>i) {
            int position = (int)(cells*rand.nextDouble());

            if((position<cells) && (minefield[position] != COVERED_MINE)) {
                int currentColumn = position % sizeX;
                minefield[position] = COVERED_MINE;
                i++;

                if(currentColumn>0) {
                    cell = position - 1 - sizeX;
                    if (cell>=0) {
                        if(minefield[cell] != COVERED_MINE) {
                            minefield[cell] += 1;
                        }
                    }
                    cell = position-1;
                    if (cell >= 0) {
                        if (minefield[cell] != COVERED_MINE) {
                            minefield[cell] += 1;
                        }
                    }

                    cell = position + sizeX - 1;
                    if (cell<cells) {
                        if (minefield[cell] != COVERED_MINE) {
                            minefield[cell] += 1;
                        }
                    }
                }
                cell = position - sizeX;
                if (cell >= 0) {
                    if (minefield[cell] != COVERED_MINE) {
                        minefield[cell] += 1;
                    }
                }

                cell = position + sizeX;
                if (cell<cells) {
                    if (minefield[cell] != COVERED_MINE) {
                        minefield[cell] += 1;
                    }
                }
                if (currentColumn<(sizeX-1)) {
                    cell = position - sizeX + 1;
                    if (cell>=0) {
                        if(minefield[cell] != COVERED_MINE) {
                            minefield[cell] += 1;
                        }
                    }
                    cell = position + sizeX + 1;
                    if (cell<cells) {
                        if (minefield[cell] != COVERED_MINE) {
                            minefield[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell<cells) {
                        if (minefield[cell] != COVERED_MINE) {
                            minefield[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void searchForEmpty(int i) {
        int currentColumn = i%sizeX;
        int cell;

        if (currentColumn > 0) {
            cell = i - sizeX - 1;
            if (cell >= 0) {
                if (minefield[cell] > MINE) {
                    minefield[cell] -= COVER;
                    if (minefield[cell] == EMPTY) {
                        searchForEmpty(cell);
                    }
                }
            }

            cell = i - 1;
            if (cell >= 0) {
                if (minefield[cell] > MINE) {
                    minefield[cell] -= COVER;
                    if (minefield[cell] == EMPTY) {
                        searchForEmpty(cell);
                    }
                }
            }

            cell = i + sizeX - 1;
            if (cell < cells) {
                if (minefield[cell] > MINE) {
                    minefield[cell] -= COVER;
                    if (minefield[cell] == EMPTY) {
                        searchForEmpty(cell);
                    }
                }
            }
        }

        cell = i - sizeX;
        if (cell >= 0) {
            if (minefield[cell] > MINE) {
                minefield[cell] -= COVER;
                if (minefield[cell] == EMPTY) {
                    searchForEmpty(cell);
                }
            }
        }

        cell = i + sizeX;
        if (cell < cells) {
            if (minefield[cell] > MINE) {
                minefield[cell] -= COVER;
                if (minefield[cell] == EMPTY) {
                    searchForEmpty(cell);
                }
            }
        }

        if (currentColumn < (sizeX - 1)) {
            cell = i - sizeX + 1;
            if (cell >= 0) {
                if (minefield[cell] > MINE) {
                    minefield[cell] -= COVER;
                    if (minefield[cell] == EMPTY) {
                        searchForEmpty(cell);
                    }
                }
            }

            cell = i + sizeX + 1;
            if (cell < cells) {
                if (minefield[cell] > MINE) {
                    minefield[cell] -= COVER;
                    if (minefield[cell] == EMPTY) {
                        searchForEmpty(cell);
                    }
                }
            }

            cell = i + 1;
            if (cell < cells) {
                if (minefield[cell] > MINE) {
                    minefield[cell] -= COVER;
                    if (minefield[cell] == EMPTY) {
                        searchForEmpty(cell);
                    }
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        int uncover = 0;

        for (int i = 0; i < sizeY; i++) {

            for (int j = 0; j < sizeX; j++) {

                int cell = minefield[(i * sizeX) + j];

                if (duringGame && cell == MINE) {

                    duringGame = false;
                }

                if (!duringGame) {

                    if (cell == COVERED_MINE || cell == MINE) {
                        cell = MINE_IMAGE;
                    } else if (cell == MARKED_MINE) {
                        cell = MARK_IMAGE;
                    } else if (cell > COVERED_MINE) {
                        cell = WRONG_MARK_IMAGE;
                    } else if (cell > MINE) {
                        cell = COVER_IMAGE;
                    }

                } else {

                    if (cell > COVERED_MINE) {
                        cell = MARK_IMAGE;
                    } else if (cell > MINE) {
                        cell = COVER_IMAGE;
                        uncover++;
                    }
                }

                g.drawImage(image[cell], (j * SIZE_OF_CELL),
                        (i * SIZE_OF_CELL), this);
            }
        }

        if (uncover == 0 && duringGame) {

            duringGame = false;
            messageField.setText("Wygrałeś!");

        } else if (!duringGame) {
            messageField.setText("Przegrałeś!");
        }

    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int column = x/SIZE_OF_CELL;
            int row = y/SIZE_OF_CELL;
            int currentCell = (row * sizeX) + column;

            boolean repaintPanel = false;

            if (duringGame) {

                if ((x < sizeX * SIZE_OF_CELL) && (y < sizeY * SIZE_OF_CELL)) {

                    if (e.getButton() == MouseEvent.BUTTON3) {

                        if (minefield[currentCell] > MINE) {
                            repaintPanel = true;

                            if (minefield[currentCell] <= COVERED_MINE) {

                                if (liveMines > 0) {
                                    minefield[currentCell] += MARKED;
                                    liveMines--;
                                    liveMinesMessage = "Pozostałe miny: " + liveMines;
                                    messageField.setText(liveMinesMessage);
                                }
                            } else {
                                minefield[currentCell] -= MARKED;
                                liveMines++;
                                liveMinesMessage = "Pozostałe miny: " + liveMines;
                                messageField.setText(liveMinesMessage);
                            }
                        }
                    } else {

                        if (minefield[currentCell] > COVERED_MINE) return;
                        if ((minefield[currentCell] > MINE) && (minefield[currentCell] < MARKED_MINE)) {
                            minefield[currentCell] -= COVER;
                            repaintPanel = true;

                            if (minefield[currentCell] == MINE) {
                                duringGame = false;
                            }

                            if (minefield[currentCell] == EMPTY) {
                                searchForEmpty(currentCell);
                            }
                        }
                    }
                    if (repaintPanel) repaint();
                }
            }
        }
    }
}
