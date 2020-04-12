package assignment5;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CritterController {
	  	@FXML
	    private GridPane root;

	    @FXML
	    private Button CreateButton;

	    @FXML
	    private TextField textNum;

	    @FXML
	    private TextField CritterText;

	    @FXML
	    private TextField stepText;

	    @FXML
	    private TextField seedText;
	    
	    @FXML
	    private Button stepButton;
	    
	    @FXML
	    private Button seedButton;
	    
	    @FXML
	    private Button quitButton;
	    
	    @FXML
	    private Polygon star;

	    @FXML
	    private Rectangle square;

	    @FXML
	    private Polygon tri;

	    @FXML
	    private Rectangle dia;

	    @FXML
	    private Circle circle;
    
	public void CreateButtonPressed () {
		textNum.setPromptText("How many?");
		CritterText.setPromptText("What Critter?");
		try {
			for(int i=0; i<Integer.parseInt(textNum.getText()); i++) {
				Critter.createCritter(CritterText.getText());
			}
		} catch (InvalidCritterException e) {
			CritterText.clear();
			CritterText.setPromptText("not a critter!");
			return;
		} catch (NumberFormatException e) {
			textNum.clear();
			textNum.setPromptText("not a number!");
		}
	}
	
	public void stepButtonPressed() {
		stepText.setPromptText("How many steps?");
		try {
			/*To get the step count, parse the second argument to java*/
			int loop = Integer.parseInt(stepText.getText());
			/*call worldTimeStep the number of times the user put in the second argument*/
			for(int i=0;i<loop;i++) {
				Critter.worldTimeStep();
			}
		}
			catch(NullPointerException|NumberFormatException e) {
				Critter.worldTimeStep();
			}
	}
	
	public void seedButtonPressed() {
		seedText.setPromptText("Seed #");
		try {
			long seed = Long.parseLong(seedText.getText());
			Critter.setSeed(seed);
		}
		catch(Exception e) {
			seedText.clear();
			seedText.setPromptText("not a number!");
		}
	}
	
	public void quitButtonPressed() {
		System.exit(0);
	}
	public Shape toShape(Critter me) {
		switch(me.viewShape()) {
		//case()
		}
		return null;
	}
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Integer.valueOf(str);  
		    return true;
		  } 
		  catch(NumberFormatException e){  
		    return false;  
		  }  
	}
		
		
	}
	

