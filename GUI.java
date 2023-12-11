package myguiapp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class GUI {

    private JFrame frame;
    private JRadioButton rdbtnHumanBlue, rdbtnComputerBlue, rdbtnSBlue, rdbtnOBlue;
    private JRadioButton rdbtnHumanRed, rdbtnComputerRed, rdbtnSRed, rdbtnORed;
    private JButton[][] boardButtons;
    private JComboBox<String> gameModeComboBox;
    private JComboBox<Integer> boardSizeComboBox;
    private JPanel boardPanel;
    private JLabel lblTurn;
    private int blueScore = 0;
    private int redScore = 0;
    private JLabel lblScore;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                GUI window = new GUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @wbp.parser.entryPoint
     */
    public GUI() {
        initialize();
    }

    private void initialize() {
    frame = new JFrame();
        frame.setBounds(100, 100, 650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        frame.getContentPane().setBackground(Color.BLACK);  // Set background to black
       
        JLabel lblGameTitle = new JLabel("S.O.S.");
        lblGameTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblGameTitle.setForeground(Color.WHITE);
        lblGameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblGameTitle, BorderLayout.NORTH);
       
     // Create a new panel to hold both the game mode and the board
        JPanel centerContentPanel = new JPanel(new BorderLayout());
        centerContentPanel.setBackground(Color.BLACK);
       
        // Add game modes combo box under the game title and above the board
        JPanel gameModePanel = new JPanel();
        gameModePanel.setBackground(Color.BLACK);
        gameModePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
       
        JLabel lblGameMode = new JLabel("Game Mode: ");
        lblGameMode.setForeground(Color.WHITE);
        gameModePanel.add(lblGameMode);

        gameModeComboBox = new JComboBox<>();
        gameModeComboBox.addItem("Simple");
        gameModeComboBox.addItem("General");
        gameModePanel.add(gameModeComboBox);
       
        centerContentPanel.add(gameModePanel, BorderLayout.NORTH);
       
       
        JPanel panelLeft = new JPanel();
        panelLeft.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelLeft.setBackground(Color.BLACK);
        frame.getContentPane().add(panelLeft, BorderLayout.WEST);
        panelLeft.setLayout(new GridLayout(6, 1, 0, 10));

        JLabel lblBluePlayer = new JLabel("Blue player");
        lblBluePlayer.setFont(new Font("Arial", Font.BOLD, 14));
        lblBluePlayer.setForeground(Color.BLUE);
        panelLeft.add(lblBluePlayer);

        rdbtnHumanBlue = new JRadioButton("Human");
        rdbtnHumanBlue.setForeground(Color.WHITE); // Set text color to white
        panelLeft.add(rdbtnHumanBlue);

        rdbtnComputerBlue = new JRadioButton("Computer");
        rdbtnComputerBlue.setForeground(Color.WHITE); // Set text color to white
        panelLeft.add(rdbtnComputerBlue);

        rdbtnSBlue = new JRadioButton("S");
        rdbtnSBlue.setForeground(Color.WHITE); // Set text color to white
        panelLeft.add(rdbtnSBlue);

        rdbtnOBlue = new JRadioButton("O");
        rdbtnOBlue.setForeground(Color.WHITE); // Set text color to white
        panelLeft.add(rdbtnOBlue);
       
        ButtonGroup groupBlue = new ButtonGroup();
        groupBlue.add(rdbtnHumanBlue);
        groupBlue.add(rdbtnComputerBlue);
       
        ButtonGroup groupSOBlue = new ButtonGroup();
        groupSOBlue.add(rdbtnSBlue);
        groupSOBlue.add(rdbtnOBlue);

        JPanel panelRight = new JPanel();
        panelRight.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelRight.setBackground(Color.BLACK);
        frame.getContentPane().add(panelRight, BorderLayout.EAST);
        panelRight.setLayout(new GridLayout(6, 1, 0, 10));

        JLabel lblRedPlayer = new JLabel("Red player");
        lblRedPlayer.setFont(new Font("Arial", Font.BOLD, 14));
        lblRedPlayer.setForeground(Color.RED);
        panelRight.add(lblRedPlayer);

        rdbtnHumanRed = new JRadioButton("Human");
        rdbtnHumanRed.setForeground(Color.WHITE); // Set text color to white
        panelRight.add(rdbtnHumanRed);

        rdbtnComputerRed = new JRadioButton("Computer");
        rdbtnComputerRed.setForeground(Color.WHITE); // Set text color to white
        panelRight.add(rdbtnComputerRed);

        rdbtnSRed = new JRadioButton("S");
        rdbtnSRed.setForeground(Color.WHITE); // Set text color to white
        panelRight.add(rdbtnSRed);

        rdbtnORed = new JRadioButton("O");
        rdbtnORed.setForeground(Color.WHITE); // Set text color to white
        panelRight.add(rdbtnORed);

        ButtonGroup groupRed = new ButtonGroup();
        groupRed.add(rdbtnHumanRed);
        groupRed.add(rdbtnComputerRed);
       
        ButtonGroup groupSORed = new ButtonGroup();
        groupSORed.add(rdbtnSRed);
        groupSORed.add(rdbtnORed);

     // Board panel
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8, 0, 0));
        boardPanel.setBackground(Color.BLACK);

        boardButtons = new JButton[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            boardButtons[i][j] = new JButton("");

                // Start of the new code
                int finalI = i;
                int finalJ = j;
                boardButtons[i][j].addActionListener(e -> {
                    // Check if it's human's turn
                    if (rdbtnHumanBlue.isSelected() || rdbtnHumanRed.isSelected()) {
                        if (isValidMove(finalI, finalJ)) {
                            // Set the move for human
                            boardButtons[finalI][finalJ].setText(rdbtnSBlue.isSelected() ? "S" : "O");
                            updateTurn();

                            // For simplicity, we make the computer move right after the human.
                            // In a real game, you might want to add more logic.
                            if (rdbtnComputerBlue.isSelected() || rdbtnComputerRed.isSelected()) {
                                computerMove();
                            }
                            if (checkForWinner()) {
                            	String currentPlayer = lblTurn.getText().equals("Blue's Turn") ? "Red" : "Blue";
                                if(currentPlayer.equals("Blue")) {
                                    blueScore++;
                                } else {
                                    redScore++;
                                }
                                updateScoreDisplay();
                                JOptionPane.showMessageDialog(frame, currentPlayer + " Wins!");
                                resetBoard();
                                return;
                            }
                        }
                    }
                });
                boardPanel.add(boardButtons[i][j]);
            }
        }
       
        JLabel lblBoardSize = new JLabel("Board Size: ");
        lblBoardSize.setForeground(Color.WHITE);
        gameModePanel.add(lblBoardSize);

        boardSizeComboBox = new JComboBox<>();
        for (int i = 3; i <= 12; i++) {
            boardSizeComboBox.addItem(i);
        }
       
        boardSizeComboBox.addActionListener(e -> {
            int selectedSize = (int) boardSizeComboBox.getSelectedItem();
            updateBoardSize(selectedSize);
        });
       
        gameModePanel.add(boardSizeComboBox);

        // Adjust layout for gameModePanel to have space on the right
        gameModePanel.setLayout(new GridLayout(1, 4)); // Divide into 4 sections
        gameModePanel.add(lblGameMode);
        gameModePanel.add(gameModeComboBox);
        gameModePanel.add(lblBoardSize);
        gameModePanel.add(boardSizeComboBox);
       
        centerContentPanel.add(boardPanel, BorderLayout.CENTER);
        frame.getContentPane().add(centerContentPanel, BorderLayout.CENTER);
       
        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(Color.BLACK);
        panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
       
        JButton btnReplay = new JButton("Replay");
        btnReplay.addActionListener(e -> resetBoard());  // Add the replay functionality here
        panelBottom.add(btnReplay);

        JButton btnNewGame = new JButton("New Game");
        btnNewGame.addActionListener(e -> {  
            resetBoard();
            resetScores();  // Reset the scores
            boardSizeComboBox.setSelectedItem(8); // default size
            rdbtnHumanBlue.setSelected(true);
            rdbtnHumanRed.setSelected(true);
            rdbtnSBlue.setSelected(true);
            rdbtnSRed.setSelected(true);
            gameModeComboBox.setSelectedIndex(0); // Set to "Simple"
            updateBoardSize(8); // Use the default board size
        });
        panelBottom.add(btnNewGame);

        lblTurn = new JLabel("Blue's Turn");  // Assuming Blue goes first
        lblTurn.setForeground(Color.WHITE);  // Set text color to white
        panelBottom.add(lblTurn);
        
        lblScore = new JLabel("Blue: " + blueScore + " | Red: " + redScore);
        lblScore.setForeground(Color.WHITE);
        panelBottom.add(lblScore, FlowLayout.LEFT);
        
        frame.getContentPane().add(panelBottom, BorderLayout.SOUTH);
    }
   
    private void updateBoardSize(int size) {
        boardPanel.removeAll(); // Remove all previous buttons
        boardPanel.setLayout(new GridLayout(size, size)); // Set new layout
        boardButtons = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardButtons[i][j] = new JButton("");
                boardPanel.add(boardButtons[i][j]);
            }
        }
        boardPanel.revalidate(); // Ask panel to layout its components again
        boardPanel.repaint(); // Repaint the panel
    }
   
    private boolean isValidMove(int x, int y) {
        return boardButtons[x][y].getText().equals("");
    }
   
    private void computerMove() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(boardButtons.length);
            y = rand.nextInt(boardButtons[0].length);
        } while (!isValidMove(x, y));
       
        // Set the board button to "S" or "O" based on the computer's choice. For simplicity, I'm just making it random.
        boardButtons[x][y].setText(rand.nextBoolean() ? "S" : "O");
        updateTurn();
        if (checkForWinner()) {
        	String currentPlayer = lblTurn.getText().equals("Blue's Turn") ? "Red" : "Blue";
            if(currentPlayer.equals("Blue")) {
                blueScore++;
            } else {
                redScore++;
            }
            updateScoreDisplay();
            JOptionPane.showMessageDialog(frame, currentPlayer + " Wins!");
            resetBoard();
            return;          
        }
    }
   
    private void resetBoard() {
        for (int i = 0; i < boardButtons.length; i++) {
            for (int j = 0; j < boardButtons[i].length; j++) {
                boardButtons[i][j].setText("");
            }
        } 
    }
   
    private void updateTurn() {
        // Toggle between Blue and Red
        if (lblTurn.getText().equals("Blue's Turn")) {
            lblTurn.setText("Red's Turn");
        } else {
            lblTurn.setText("Blue's Turn");
        }
    }
    
    private boolean checkForWinner() {
        int size = boardButtons.length;

        // Check horizontally
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (boardButtons[i][j].getText().equals("S") &&
                    boardButtons[i][j+1].getText().equals("O") &&
                    boardButtons[i][j+2].getText().equals("S")) {
                    return true;
                }
            }
        }

        // Check vertically
        for (int i = 0; i < size - 2; i++) {
            for (int j = 0; j < size; j++) {
                if (boardButtons[i][j].getText().equals("S") &&
                    boardButtons[i+1][j].getText().equals("O") &&
                    boardButtons[i+2][j].getText().equals("S")) {
                    return true;
                }
            }
        }
     // Check major diagonal (top-left to bottom-right)
        for (int i = 0; i < size - 2; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (boardButtons[i][j].getText().equals("S") &&
                    boardButtons[i+1][j+1].getText().equals("O") &&
                    boardButtons[i+2][j+2].getText().equals("S")) {
                    return true;
                }
            }
        }

        // Check minor diagonal (bottom-left to top-right)
        for (int i = 2; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (boardButtons[i][j].getText().equals("S") &&
                    boardButtons[i-1][j+1].getText().equals("O") &&
                    boardButtons[i-2][j+2].getText().equals("S")) {
                    return true;
                }
            }
        }
            
        if (isBoardFull() && !false) {
            JOptionPane.showMessageDialog(frame, "It's a tie!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return true;  // Ends the game
        }
        
        return false;
    }
    
    private void updateScoreDisplay() {
        lblScore.setText("Blue: " + blueScore + " | Red: " + redScore);
    }
    
    private void resetScores() {
        blueScore = 0;
        redScore = 0;
        updateScoreDisplay();
    }   

    private boolean isBoardFull() {
        for (int i = 0; i < boardButtons.length; i++) {
            for (int j = 0; j < boardButtons[i].length; j++) {
                if (boardButtons[i][j].getText().equals("")) {
                    return false; // Found an empty cell
                }
            }
        }
        return true; // If here, all cells are filled
    } 
}
