import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;

/*
 * Assignment 1
 * University of Cape Town – 2025
 * Author: Radhiya Isaacs
 *
 * Description:
 * This project was created as part of my coursework at UCT.
 * It fulfills the requirements of allowing the lookup of a term from the genericsKB
 *
 * ⚠️ Academic Integrity Notice:
 * This code is my own work and was submitted for academic credit.
 * It may not be copied, used, or distributed by others (especially UCT students)
 * as doing so may constitute plagiarism and result in disciplinary action.
 *
 * Repository shared for educational and portfolio purposes only.
 */



//Class to create statement objects
class Statement {
    String term;
    String definition;
    double score;

    /**
     * Constructs a new Statement.
     * @param word The term.
     * @param definition The explanation of the term.
     * @param score The relevance score.
     */
    public Statement(String term, String definition, double score) {
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

public class KnowledgeBaseGUI {

    //Image and text to update 
    private static JLabel updateLabel; 
    private static JLabel textLabel;

    //initializing icons 
    private static ImageIcon loadedIcon; 
    private static ImageIcon waitIcon;
    private static ImageIcon failIcon;
    private static ImageIcon welcomeIcon; 

    //initializing array for statements
    public static final int MAX_SIZE = 100000;
    public static Statement[] knowledgeBase = new Statement[MAX_SIZE];
    public static int count = 0;


    public static void main(String[] args) {
        // Create the main frame 
        JFrame frame = new JFrame("LexiTree (Array)");
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
        
            button.addActionListener(new ButtonClickListener(name, frame));
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
class ButtonClickListener implements ActionListener {
    private String buttonName;
    private JFrame frame;

    public ButtonClickListener(String name, JFrame frame) {
        this.buttonName = name;
        this.frame = frame;
    }
    
    
    private void searchByTerm() {
        String term = JOptionPane.showInputDialog(frame, "Enter search term:");
        
        //ensuring term isnt empty
        if (term != null && !term.trim().isEmpty()) {
            boolean found = false;
            String result = "";
            
            //searching knowledge base for word
            for (int i = 0; i < KnowledgeBaseGUI.count; i++) {
                if (KnowledgeBaseGUI.knowledgeBase[i] != null && 
                    KnowledgeBaseGUI.knowledgeBase[i].term.equalsIgnoreCase(term.trim())) {
    
                    result = "Term Found ! Definition: " + KnowledgeBaseGUI.knowledgeBase[i].definition + 
                             "\n Confidence Score: " + KnowledgeBaseGUI.knowledgeBase[i].score;

                    KnowledgeBaseGUI.updateContent(result , true);
                    found = true;
                    break;
                }
            }
            if (!found) {
                KnowledgeBaseGUI.updateContent("Term " + term + " not found in Knowledge base", false);
            }
        }
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (buttonName) {
            case "Load a knowledge base from a file":
                loadKnowledgeBase();
                break;
            case "Add a new statement to the knowledge base":
                addStatement();
                break;
            case "Search for a statement by term":
                searchByTerm();
                break;
             case "Search for a statement by term and sentence":
                searchByTermAndSentence();
                break; 
            case "Quit":
                System.exit(0);
                break;
            case "Update term":
                updateTerm();
               
        }
    }

    //method to update a term in the knowledge base
    private void updateTerm(){
        String term = JOptionPane.showInputDialog(frame, "Enter the term you want to update:");

    if (term == null || term.trim().isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Invalid term!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    term = term.trim();
    boolean found = false;

        // Search for the term in the knowledge base
        for (int i = 0; i < KnowledgeBaseGUI.count; i++) {
            if (KnowledgeBaseGUI.knowledgeBase[i].getWord().equalsIgnoreCase(term)) {
                found = true;
                
                // Ask for a new definition and confidence score
                String newDefinition = JOptionPane.showInputDialog(frame, "Enter the new definition for '" + term + "':");
                if (newDefinition == null || newDefinition.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Definition cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String newScoreStr = JOptionPane.showInputDialog(frame, "Enter the new score for '" + term + "':");
                double newScore;

                try {
                    newScore = Double.parseDouble(newScoreStr.trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid score!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Get current confidence score of term from knowledge base
                double currentScore = KnowledgeBaseGUI.knowledgeBase[i].getScore();


                // Only update if the new score is highter than old score 
                if (newScore >= currentScore){
                    KnowledgeBaseGUI.knowledgeBase[i].setDefinition(newDefinition.trim());
                    KnowledgeBaseGUI.knowledgeBase[i].setScore(newScore);
                    System.out.println("updated term");
                    JOptionPane.showMessageDialog(frame, "Term '" + term + "' updated successfully!");
                    KnowledgeBaseGUI.updateContent("Term updated: " + term, true);
                    System.out.println("Checked confidence score");

                }
                else{
                    
                    KnowledgeBaseGUI.updateContent("New confidence score must be higher than the current confidence score " , false);

                }
                
                return;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(frame, "Term not found in the knowledge base!", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("didnt updated term");
        }

    }


    
   /** Loads a knowledge base from a text file 
    * @implNote Each item is found seoerated on each line by a tab
    * @throws IOException,file cannot be read.
    * @throws NumberFormatException 
    */
    private void loadKnowledgeBase() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    int lineNumber = 0; 
                    
                    while ((line = br.readLine()) != null) {
                        lineNumber++;

                if (KnowledgeBaseGUI.count >= KnowledgeBaseGUI.MAX_SIZE) {
                    JOptionPane.showMessageDialog(frame, "Knowledge Base limit reached at line: " + lineNumber);
                    break;
                }

                // Ensure valid format
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

                System.out.println("Adding statement at index: " + KnowledgeBaseGUI.count);
                KnowledgeBaseGUI.knowledgeBase[KnowledgeBaseGUI.count++] = new Statement(word, definition, score);
            }
                KnowledgeBaseGUI.updateContent("Knowledge base loaded successfully!", true); 

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Failed to load file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                KnowledgeBaseGUI.updateContent("Error loading file",false);

            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid file name!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    //Method to add a new term to knowledge base
    private void addStatement() {
        // Get term from user
        String term = JOptionPane.showInputDialog(frame, "Enter the term:");
        if (term == null || term.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Term cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Get definition from user
        String definition = JOptionPane.showInputDialog(frame, "Enter the definition:");
        if (definition == null || definition.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Definition cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String scoreStr = JOptionPane.showInputDialog(frame, "Enter confidence score (0.0 - 1.0):");
        double score;
        try {
            score = Double.parseDouble(scoreStr.trim());
            if (score < 0.0 || score > 1.0) {
                throw new NumberFormatException();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid confidence score! Enter a number between 0.0 and 1.0.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
         // Add the new statement to the knowledgeBase array
        KnowledgeBaseGUI.knowledgeBase[KnowledgeBaseGUI.count++] = new Statement(term.trim(), definition.trim(), score);

        String fileName = JOptionPane.showInputDialog(frame, "Enter the file name to save the new statement to:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { // Open in append mode
                writer.write( term.trim() + "\t" + definition.trim() + "\t" + score + "\n"); 
                System.out.println("it worked");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Failed to save the term to the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        KnowledgeBaseGUI.updateContent("New term added: " + term + " " + definition +  " " +score, true);
    }
    

    


     /** @implNote Method to search for a statement by term and sentence 
      *  @throws NullPointerException if KnowledgeBaseGUI.knowledgeBase is not initialized.
      *  @param term The search term to look for.     
      */
     private void searchByTermAndSentence() {
        String term = JOptionPane.showInputDialog(frame, "Enter search term:");
        String sentence = JOptionPane.showInputDialog(frame, "Enter part of the definition :");
    
        if (term != null && !term.trim().isEmpty()) {
            boolean found = false;
            String result = "";
    
            for (int i = 0; i < KnowledgeBaseGUI.count; i++) {
                Statement statement = KnowledgeBaseGUI.knowledgeBase[i];
    
                if (statement != null && statement.term.equalsIgnoreCase(term.trim())) {
                    
                    if (sentence == null || sentence.trim().isEmpty() || 
                        statement.definition.toLowerCase().contains(sentence.toLowerCase().trim())) {
                        
                        result = "The statement was found and has a confidence score of " + statement.score ;
    
                        KnowledgeBaseGUI.updateContent(result, true); 
                        found = true;
                        break;
                    }
                }
            }
    
            if (!found) {
                JOptionPane.showMessageDialog(frame, "No matching term and sentence found.");
                KnowledgeBaseGUI.updateContent("No matching term and sentence found for "+ term, false); 
            }
        }
    }
    
}
