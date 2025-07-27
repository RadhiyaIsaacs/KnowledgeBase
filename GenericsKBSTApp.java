import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;

/*
 * Assignment 2 
 * University of Cape Town – 2025
 * Author: Radhiya Isaacs
 *
 * Description:
 * This project was created as part of my coursework at UCT.
 * GenericsKbArrayApp, has multiple functions to search and update a knowledge database
 *
 * ⚠️ Academic Integrity Notice:
 * This code is my own work and was submitted for academic credit.
 * It may not be copied, used, or distributed by others (especially UCT students)
 * as doing so may constitute plagiarism and result in disciplinary action.
 *
 * Repository shared for educational and portfolio purposes only.
 */





//Class to create statement objects
class StatementBST {
    String term;
    String definition;
    double score;

    /**
     * Constructs a new StatementBST.
     * @param word The term.
     * @param definition The explanation of the term.
     * @param score The relevance score.
     */
    public StatementBST(String term, String definition, double score) {
        this.term = term;
        this.definition = definition;
        this.score = score;
    }

     /**
     * Getter for word
     * @return The word (term).
     */
    public String getWord(){
        return term;
    }

     /**
     * Getter for definition
     * @return The definition 
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * Setter for definition
     * @param defnition 
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

     /**
     * Getter for relevance score
     * @return The score
     */
    public double getScore() {
        return score;
    }

    /**
     * Setter for score.
     * @param score The new score.
     */
    public void setScore(double score) {
        this.score = score;
    }


}

    /**
 * Class representing a node in the Binary Search Tree (BST)
 */
class BSTNode {
    StatementBST statement;
    BSTNode left, right;

    public BSTNode(StatementBST statement) {
        this.statement = statement;
        this.left = this.right = null;
    }
}


class BinarySearchTree {
    private BSTNode root;

    public BinarySearchTree() {
        root = null;
    }

    // Insert method
    public void insert(StatementBST statement) {
        root = insertBST(root, statement); //Statemtn object is root
    }

    private BSTNode insertBST(BSTNode root, StatementBST statement) {
        if (root == null) {
            return new BSTNode(statement); //create root if root doesnt exist
        }
        //checking whether it should go on the left or right 
        if (statement.term.compareToIgnoreCase(root.statement.term) < 0) {
            root.left = insertBST(root.left, statement);
        } else if (statement.term.compareToIgnoreCase(root.statement.term) > 0) {
            root.right = insertBST(root.right, statement);
        }
        return root;
          
    }


    // Search method
    public StatementBST search(String term) {
        return searchRec(root, term);
    }

    private StatementBST searchRec(BSTNode root, String term) {
        if (root == null) {
            return null;
        } 
    
        if (term.equalsIgnoreCase(root.statement.term)) { 
            return root.statement;
        } 
        
        if (term.compareToIgnoreCase(root.statement.term) < 0) {
            return searchRec(root.left, term);
        } 
        
        return searchRec(root.right, term);
    }
    
    //TO DO : Fix this 
    // Method to update definition and score of a term 
    public boolean update(String term, String newDefinition, double newScore) {
        StatementBST statement = search(term);
        if (statement != null) {
            statement.setDefinition(newDefinition);
            statement.setScore(newScore);
            return true;
        }
        return false;
    }


    // Method to write the BST to a file
    public void writeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeToFileRec(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to recursively write the BST to a file
    private void writeToFileRec(BSTNode root, BufferedWriter writer) throws IOException {
        if (root != null) {
            writeToFileRec(root.left, writer); // left subtree
            writer.write(root.statement.term + "\t" + root.statement.definition + "\t" + root.statement.score);
            writer.newLine();
            writeToFileRec(root.right, writer); //right subtree
        }
    }
}





public class GenericsKBSTApp{

    //Image and text to update 
    private static JLabel updateLabel; 
    private static JLabel textLabel;

    //initializing icons 
    private static ImageIcon loadedIcon; 
    private static ImageIcon waitIcon;
    private static ImageIcon failIcon;
    private static ImageIcon welcomeIcon; 

    public static BinarySearchTree tree = new BinarySearchTree();
    public static int count = 0;

    public static void main(String[] args) {
        // Create the main frame 
        JFrame frame = new JFrame("LexiTree (BST)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 950);
        frame.setLayout(new BorderLayout(20, 20)); 

        // Header panel 
        welcomeIcon = new ImageIcon("resourcez/welcome.png");

        JPanel header = new JPanel();
        JLabel titleLabel = new JLabel(welcomeIcon, SwingConstants.CENTER);
       
        header.add(titleLabel);

        // Panel to update searched word or show an image
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
        updatePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

       //set pngs
        loadedIcon = new ImageIcon("resourcez/1.png");
        waitIcon = new ImageIcon ("resourcez/2.png");
        failIcon = new ImageIcon ("resourcez/3.png");

        //text and icon for first opening
        updateLabel = new JLabel(waitIcon , SwingConstants.CENTER );
        textLabel = new JLabel("Please load a Knowledge database");

        updatePanel.add(updateLabel); 
        updatePanel.add(textLabel);
        updateLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        // Button Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); 
        
        // List of button names
        String[] buttonNames = {
            "Load a knowledge base from a file",
            "Search for a statement by term",
            "Update term",
            "Add a new statement to the knowledge base",
            "Search for a statement by term and sentence",
            "Quit"
        };

        //creating buttons and styling
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            
            
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(139, 69, 19)); 
            
            
            button.setFont(new Font("Arial", Font.BOLD, 14));
            
            
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10) 
            ));
        
           
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(160, 82, 45)); 
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(139, 69, 19)); 
                }
            });
        
            button.addActionListener(new ButtonClickListenerBST(name, frame));
            panel.add(button);
        }
        
        

        // Add everything to the frame
        frame.add(header, BorderLayout.NORTH);
        frame.add(updatePanel, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    //method to update the image and text showm
    public static void updateContent(String result, boolean found) {
        SwingUtilities.invokeLater(() -> { 
            if (found) {
                //success image
                textLabel.setText(result); 
                updateLabel.setIcon(loadedIcon); 
            } else {
                //fail image
                textLabel.setText( result);
                updateLabel.setIcon(failIcon); 
            }
            updateLabel.revalidate(); // Ensure UI refreshes
            updateLabel.repaint();
            textLabel.revalidate();  
            textLabel.repaint();
        });
    }
    
}

// Action Listener class for handling button clicks
class ButtonClickListenerBST implements ActionListener {
    private String buttonName;
    private JFrame frame;

    public ButtonClickListenerBST(String name, JFrame frame) {
        this.buttonName = name;
        this.frame = frame;
    }
    
    
    private void searchByTermBST() {
        String term = JOptionPane.showInputDialog(frame, "Enter search term:");
        if (term != null && !term.trim().isEmpty()) {
            StatementBST statement = GenericsKBSTApp.tree.search(term.trim());
            if (statement != null) {
                GenericsKBSTApp.updateContent(
                        "<html>Term Found! <br>Definition: " + statement.getDefinition() + "<br> Confidence Score: <html>" + statement.getScore(),
                        true);
            } else {
                GenericsKBSTApp.updateContent("Term '" + term + "' not found in Knowledge base", false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (buttonName) {
            case "Load a knowledge base from a file":
                loadKnowledgeBaseBST();
                break;
            case "Add a new statement to the knowledge base":
                addStatementBST();
                break;
            case "Search for a statement by term":
                searchByTermBST();
                break;
             case "Search for a statement by term and sentence":
                searchByTermAndSentenceBST();
                break; 
            case "Quit":
                System.exit(0);
                break;
            case "Update term":
                updateTermBST();
               
        }
    }

    // Method to update a term in the knowledge base (BST)
    private void updateTermBST() {
        String term = JOptionPane.showInputDialog(frame, "Enter the term you want to update:");
        if (term == null || term.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Invalid term!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StatementBST statement = GenericsKBSTApp.tree.search(term.trim());
        if (statement != null) {
            double currentScore = statement.getScore(); // Get the current confidence score

            String newDefinition = JOptionPane.showInputDialog(frame, "Enter the new definition for '" + term + "':");
            String newScoreStr = JOptionPane.showInputDialog(frame, "Enter the new score for '" + term + "':");
            
            try {
                double newScore = Double.parseDouble(newScoreStr.trim());

                if (newScore >= currentScore) { // Ensure new score is higher than current score
                    boolean updated = GenericsKBSTApp.tree.update(term.trim(), newDefinition.trim(), newScore);
                    
                    if (updated) {
                        // Write back to the file
                        String fileName = JOptionPane.showInputDialog(frame, "Enter the file name to save the updated knowledge base:");
                        if (fileName != null && !fileName.trim().isEmpty()) {
                            GenericsKBSTApp.tree.writeToFile(fileName.trim());
                            JOptionPane.showMessageDialog(frame, "Term '" + term + "' updated successfully and saved to file!");
                            GenericsKBSTApp.updateContent("Term updated: " + term, true);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid file name!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "New confidence score must be higher than the current score!", "Error", JOptionPane.ERROR_MESSAGE);
                    GenericsKBSTApp.updateContent("Update failed: New score must be higher.", false);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid score!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Term not found in the knowledge base!", "Error", JOptionPane.ERROR_MESSAGE);
        }     
    }


    
   /** Loads a knowledge base from a text file 
    * @implNote Each item is found seoerated on each line by a tab
    * @throws IOException,file cannot be read.
    * @throws NumberFormatException 
    */
    private void loadKnowledgeBaseBST() {
        System.out.println("BST loading");
        String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    //int lineNumber = 0; 
                    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length < 3) {
                    continue;  // Skip bad lines
                }
                // Extract and trim values
                String word = parts[0].trim();
                String definition = parts[1].trim();
                double score;

                try {
                    score = Double.parseDouble(parts[2].trim());

                } catch (NumberFormatException e) {
                    continue; 
                }

                System.out.println("Adding statement at index: " + GenericsKBSTApp.count);
                GenericsKBSTApp.tree.insert(new StatementBST(word, definition, score));

            }
                GenericsKBSTApp.updateContent("Knowledge base loaded successfully!", true); 

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Failed to load file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                GenericsKBSTApp.updateContent("Error loading file",false);

            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid file name!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    //Method to add a new term to knowledge base
    private void addStatementBST() {
        String term = JOptionPane.showInputDialog(frame, "Enter the term:");
        String definition = JOptionPane.showInputDialog(frame, "Enter the definition:");
        String scoreStr = JOptionPane.showInputDialog(frame, "Enter confidence score (0.0 - 1.0):");
        try {
            double score = Double.parseDouble(scoreStr.trim());
            GenericsKBSTApp.tree.insert(new StatementBST(term.trim(), definition.trim(), score));

            GenericsKBSTApp.updateContent("New term added: " + term, true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid confidence score!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    


     /** @implNote Method to search for a statement by term and sentence 
      *  @throws NullPointerException if GenericsKBSTApp
      .knowledgeBase is not initialized.
      *  @param term The search term to look for.     
      */
     private void searchByTermAndSentenceBST() {
        String term = JOptionPane.showInputDialog(frame, "Enter search term:");
        String sentence = JOptionPane.showInputDialog(frame, "Enter definition :");
        
        System.out.println("BST term and sentence search executing");
        if (term != null && !term.trim().isEmpty()) {

            StatementBST statement = GenericsKBSTApp.tree.search(term.trim());

            if (statement != null) {
                // Check if definition contains the provided sentence (if any)
                if (sentence == null || sentence.trim().isEmpty() || 
                    statement.getDefinition().toLowerCase().contains(sentence.toLowerCase().trim())) {
                    
                        String result = "<html>The statement was found!<br> Term: " + statement.getWord() + 
                        "<br> Definition: " + statement.getDefinition() + 
                        "<br> Confidence Score: " + statement.getScore() + "</html>";
        
                    GenericsKBSTApp.updateContent(result, true);
                    return;
                }
            }
        }
    
        // If no match was found
        JOptionPane.showMessageDialog(frame, "No matching term and sentence found.");
        GenericsKBSTApp.updateContent("No matching term and sentence found for " + term, false);
    }
    
}
