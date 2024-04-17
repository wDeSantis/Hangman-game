
package jeupendu;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.lang.IllegalArgumentException;
import java.util.regex.Pattern;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



public class FXMLDocumentController implements Initializable {
    // Déclaration des ArrayList pour les mots cachés
    // les boutons des lettres et Hashet des lettres déjà essayées
   private ArrayList<String> motsCaches = new ArrayList<>(Arrays.asList("Chien",
   "Chat", "Soleil", "Fleur", "Maison", "Arbre", "Étoile", "Plage", "Lune", "Coeur",
   "Éléphant", "Licorne", "Piano", "Crocodile", "Téléphone", "Parapluie"));
   private ArrayList<Button> boutonsLettres = new ArrayList<>();
   private HashSet<String> lettresEssayees = new HashSet<>();
   // Déclaration des variables
   private String motCaché; 
   private int Pointage = 0;
   private boolean jeuEnCours = false; 
   private boolean jeuCommence = false;
   private int essaisInfructueux = 0;

    @FXML
    private Button aButton;

    @FXML
    private Button bButton;

    @FXML
    private Button btnDebutJeu;
    
    @FXML
    private Button btnNouvellePartie;

    @FXML
    private Button cButton;

    @FXML
    private Canvas canvasPoteau;

    @FXML
    private Button dButton;

    @FXML
    private Button eButton;

    @FXML
    private Button fButton;

    @FXML
    private Button gButton;

    @FXML
    private Button hButton;

    @FXML
    private Button iButton;

    @FXML
    private Button jButton;

    @FXML
    private Button kButton;

    @FXML
    private Button lButton;

    @FXML
    private Label labelPointage;
    
    @FXML
    private Label labelNomJoueur;
     

    @FXML
    private Button mButton;

    @FXML
    private Button nButton;

    @FXML
    private Button oButton;

    @FXML
    private Button pButton;

    @FXML
    private Button qButton;

    @FXML
    private Button rButton;

    @FXML
    private Button sButton;

    @FXML
    private Button tButton;

      @FXML
    private Label labelMotCaché;
      
    @FXML
    private TextField textfieldNomJoueur;
    
     @FXML
    private Label labelDescriptifNomJoueur;    
    
     @FXML
    private Label labelRegle1;

    @FXML
    private Label labelRegle2;

    @FXML
    private Label labelRegle3;
    
    @FXML
    private Pane paneInstructions;
    
    @FXML
    private Label labelInstructions1;
     
    @FXML
    private Label labelInstructions2;

    @FXML
    private Label labelInstructions3;

    @FXML
    private Label labelInstructions4;

    @FXML
    private Label labelInstructions5;

    @FXML
    private Label labelInstructions6;
    
    @FXML
    private Pane paneRegle;

    @FXML
    private Button uButton;
    
    @FXML
    private Button btnQuitter;

    @FXML
    private Button vButton;

    @FXML
    private Button wButton;

    @FXML
    private Button xButton;

    @FXML
    private Button yButton;

    @FXML
    private Button zButton;
    
    // Méthode appelée lorsque le joueur clique sur débuter le jeu
    @FXML
    void débuterJeu(ActionEvent e){
        String nomJoueur = textfieldNomJoueur.getText(); // Récuperer l'entrée du nom
        
        try{
            // Appel de la méthode pour valider le nom du joueur
            validerNomJoueur(nomJoueur);
            // mis a jour du label
            labelNomJoueur.setText("Nom du Joueur : " + nomJoueur);
            textfieldNomJoueur.setVisible(false); // Mettre text field off
            labelDescriptifNomJoueur.setVisible(false);
            // Débuter la nouvelle partie
            initialiserPartie();
            // Désactivation du bouton Débuter le jeu
            btnDebutJeu.setDisable(true);
            jeuEnCours = true;
            jeuCommence = true;
          // appel d'alerte si erreur        
        } catch (IllegalArgumentException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : " + ex.getMessage());
            alert.showAndWait();
            
        }
        
    }
    
    // Méthode appeler lorsqu'on clique sur nouvelle partie
    @FXML
    void nouvellePartie(ActionEvent e){
        if(jeuCommence){
        Pointage = 0;
        labelPointage.setText("0");
        reinitialiserBonhommePendu();
        // Débuter la nouvelle partie
        initialiserPartie();
        jeuEnCours = false;
        } else{ 
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Vous devez d'abord entrer un nom puis appuyer sur 'Débuter le Jeu'.");
        alert.showAndWait();
        }
    }
    
     // Méthode pour initialiser la partie
    private void initialiserPartie(){
        essaisInfructueux = 0;
        reactiverBoutonsLettres(); 
        initialiserDessinPoteau();
        // Réinitialisation de l'ensemble lettresEssayees a chaque nouveau mot
        lettresEssayees.clear();
        // Choix du mot caché aléatoire avec la méthode destinée
        motCaché = choisirMotCaché();
        System.out.println("Mot caché initialisé : " + motCaché);
            
        // Réinitialisation de l'affichage du mot caché
        String motAffiché = "";
        for (int i=0; i < motCaché.length(); i++){
            motAffiché += "*";
        }
        
        // Affichage du mot caché dans le labelMotCaché
        labelMotCaché.setText(motAffiché);
           
    }
    // Méthode qui s'assure que le nom entree est valid
    private void validerNomJoueur(String nomJoueur){
        // if qui assure que le nom du joueur est remplit
        if (nomJoueur.isEmpty()){
            throw new IllegalArgumentException ("Le nom du joueur ne peut pas être vide");
        }
        //if qui assure que le nom du joueur ne contient que des lettres alphabétiques
        if (!nomJoueur.matches("[a-zA-Z ]+")){
            throw new IllegalArgumentException ("Le nom du joueur ne doit contenir "
                    + "que des lettres alphabétiques");
        }
        // if qui assure que le nom du joueur ne dépasse pas 25 caractères
        if (nomJoueur.length() > 25){
            throw new IllegalArgumentException("Le nom du joueur ne peut pas dépasser 25 caractères");
        }
    
    }
    // Méthode qui choisit un mot caché de la liste
    private String choisirMotCaché(){
        Random random = new Random();
        int index = random.nextInt(motsCaches.size()); //Recupere la longueur du mot
        return motsCaches.get(index);
    }

    
    // Méthode qui initialise le dessin du poteau
    private void initialiserDessinPoteau(){
        GraphicsContext gc = canvasPoteau.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasPoteau.getWidth(), canvasPoteau.getHeight());
       
        // Dessin du poteau initial
        gc.strokeLine(50, 250, 200, 250);
        gc.strokeLine(125, 250, 125, 50);
        gc.strokeLine(125, 50, 175, 50);
        gc.strokeLine(175, 50, 175, 100);
    }
   
    // Méthode pour mettre a  jour le dessin du bonhomme pendu
    // a chaque erreur
    private void mettreAJourDessinPendu(){
        GraphicsContext gc = canvasPoteau.getGraphicsContext2D();
        
        // Redessin du poteau initial
        gc.strokeLine(50, 250, 200, 250);
        gc.strokeLine(125, 250, 125, 50);
        gc.strokeLine(125, 50, 175, 50);
        gc.strokeLine(175, 50, 175, 100);
        // Appel des méthodes pour dessiner les membres du pendu en cas d'erreur
        if (essaisInfructueux == 1){
           dessinerTete(gc);
        }
        if (essaisInfructueux == 2){
            dessinerCorps(gc);
        }
        if (essaisInfructueux == 3){
            dessinerBrasGauche(gc);
        }
        if (essaisInfructueux == 4){
            dessinerBrasDroit(gc);
        }
        if (essaisInfructueux == 5){
            dessinerJambeGauche(gc);
        }
        if (essaisInfructueux == 6){
            dessinerJambeDroite(gc);
        }
               
    }
    // Méthode pour reinitialiser le bonhomme pendu
    private void reinitialiserBonhommePendu(){
        essaisInfructueux = 0;
        initialiserDessinPoteau();
    }
    // Méthode pour dessiner la tete du pendu
    private void dessinerTete(GraphicsContext gc) {
        gc.strokeOval(150, 100, 50, 50); 
    }
    // Méthode pour dessiner le corps du pendu
    private void dessinerCorps(GraphicsContext gc) {
        gc.strokeLine(175, 150, 175, 200); 
    }
    // Méthode pour dessiner bras gauche du pendu
    private void dessinerBrasGauche(GraphicsContext gc) {
        gc.strokeLine(175, 150, 200, 175); // Bras gauche
    }
    // Méthode pour dessiner bras droit du pendu
    private void dessinerBrasDroit(GraphicsContext gc){
        gc.strokeLine(175, 150, 150, 175); // Bras droit
    }
    //Méthode pour dessiner jambe gauche du pendu
    private void dessinerJambeGauche(GraphicsContext gc) {
        gc.strokeLine(175, 200, 200, 250); // Jambe gauche
    }
    // Méthode pour dessiner jambe droite du pendu
    private void dessinerJambeDroite(GraphicsContext gc){
        gc.strokeLine(175, 200, 150, 250); // Jambe droite
    }
    
    // Méthode qui gere la recherche de lettre et la logique
    public void handleAlphabetButton(ActionEvent event) {
    Button buttonClicked = (Button) event.getSource();
    String letterGuessed = buttonClicked.getText().toLowerCase(); // Convertir en minuscules
    // Si lettre essayé est contenu dans la liste LetterGuessed
    if (lettresEssayees.contains(letterGuessed)){
        return;
        
    }
    lettresEssayees.add(letterGuessed); // Ajout de la lettre essayé a la liste
                                        // letterGuessed
        
    // Convertion du mot
    String motCachéNormalized = normalizeLettre(motCaché); 
    // Vérifier si la lettre devinée est dans le mot caché
    boolean lettreDevinee = false;
    // StringBuilder pour gérer la recherche
    StringBuilder nouveauMotAffiché = new StringBuilder(labelMotCaché.getText());
    for (int i = 0; i < motCaché.length(); i++) {
        if (motCachéNormalized.charAt(i) == letterGuessed.charAt(0)) {
            nouveauMotAffiché.setCharAt(i, letterGuessed.charAt(0));
            lettreDevinee = true;
        }
    }
    // filtre et désactive les boutons de lettres une fois cliquée
    boutonsLettres.stream()
        .filter(bouton -> bouton.getText().equalsIgnoreCase(letterGuessed))
        .forEach(bouton -> bouton.setDisable(true));
    
    // Incrémentation de 1 si la lettre devinée est incorrecte
    if (!lettreDevinee){
        essaisInfructueux++;
        mettreAJourDessinPendu();
    }
    // vérification si le bonhomme pendu finit donc 6 essais
    if (essaisInfructueux == 6){
        afficherAlerteDefaite();
        essaisInfructueux = 0;
        initialiserPartie();
    }
    
    // Si une lettre est devinée ajout du pointage +1
    if (lettreDevinee) {
        labelMotCaché.setText(nouveauMotAffiché.toString());
        Pointage += motCachéNormalized.chars().filter(x -> x == letterGuessed.charAt(0)).count();

      // Si un mot complet est devinée ajout du pointage +5
        if (nouveauMotAffiché.toString().equals(motCachéNormalized)) {
            Pointage += 5; // Ajout de 5 au pointage
            afficherAlerteVictoire();
            // Le joueur a gagné on recommence
            initialiserPartie();   
        }
        // Mise a jour du labelPointage pour le pointage
        labelPointage.setText(Integer.toString(Pointage));
    }
}
    // Méthode pour afficher le message de victoire
    private void afficherAlerteVictoire(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoire");
        alert.setHeaderText(null);
        alert.setContentText("Félicitations ! Vous avez gagné !");
        alert.showAndWait();
    }
    
    // Méthode pour afficher le message de défaite
    private void afficherAlerteDefaite(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Défaite");
        alert.setHeaderText(null);
        alert.setContentText("Désolé, vous avez perdu.");
        alert.showAndWait();
    }
    // Méthode pour reactiver les boutons des lettres
    private void reactiverBoutonsLettres(){
        for (Button bouton : boutonsLettres){
            bouton.setDisable(false);           
        }
         
    }
    // Méthode pour normaliser les lettres 
    private String normalizeLettre(String lettre){
        lettre = lettre.toLowerCase();
        lettre = Normalizer.normalize(lettre, Normalizer.Form.NFD);
        lettre = lettre.replaceAll("\\p{M}", "");
        return lettre;       
    }
    
    // Méthode pour quitter l'application avec le bouton quitter
    private void quitterApplication(){
        Platform.exit();
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnNouvellePartie.setOnAction(this::nouvellePartie); 
        btnQuitter.setOnAction(x -> quitterApplication());
        boutonsLettres.addAll(Arrays.asList(aButton, bButton, cButton, dButton,
                eButton, fButton, gButton, hButton, iButton, jButton, kButton, 
                lButton, mButton, nButton, oButton, pButton, qButton, rButton, 
                sButton, tButton, uButton, vButton, wButton, xButton, yButton, zButton));     
       
        // boucle qui initialize les lettres sur les boutons
       char lettre = 'a';
       for (Button bouton : boutonsLettres){
           bouton.setFocusTraversable(false);   // Met le focus a off   
           bouton.setText(String.valueOf(lettre));
           bouton.setOnAction(this::handleAlphabetButton);
           lettre++;
       }
      
    }    
    
}
