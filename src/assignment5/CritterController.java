package assignment5;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CritterController {
	  	@FXML
	    private GridPane root;
	    @FXML
	    private GridPane world;
	    @FXML
	    private Button CreateButton;
	    @FXML
	    private Button quitButton;
	    @FXML
	    private TextField textNum;
	    @FXML
	    private TextField CritterText;
	    @FXML
	    private TextField stepText;
	    @FXML
	    private TextField seedText;
	    @FXML
	    private Button setWorldButton;
	    @FXML 
	    private TextField worldWidthText;
	    @FXML
	    private TextField worldHeightText;
	    @FXML
	    private Button stepButton;
	    @FXML
	    private Button seedButton;
	    @FXML
	    private static Polygon star;
	    @FXML
	    private static Rectangle square;
	    @FXML
	    private static Polygon tri;
	    @FXML
	    private static Rectangle dia;
	    @FXML
	    private static Circle circle;
    
	public void CreateButtonPressed () {
		textNum.setPromptText("How many?");
		CritterText.setPromptText("What Critter?");
		try {
			for(int i=0; i<Integer.parseInt(textNum.getText()); i++) {
				Critter.createCritter(CritterText.getText());
			}
			Critter.displayWorld(Main.root);
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
				Critter.displayWorld(Main.root);
			}
		}
			catch(NullPointerException|NumberFormatException e) {
				Critter.worldTimeStep();
				Critter.displayWorld(Main.root);
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
	
	public static Shape toShape(Critter me) {
		Shape solution;
		int size = 800/Params.WORLD_HEIGHT;

		switch(me.viewShape()) {
		case CIRCLE:
			solution = new Circle(size/2);
			break;
		case SQUARE:
			solution = new Rectangle(size,size);
			break;
		case TRIANGLE:
			solution = new Path(new MoveTo(0,0), new LineTo(-size+2,0), new LineTo(-size/2,-size+2), new LineTo(0,0));
			break;
		case DIAMOND:
			solution = new Path(new MoveTo(0,0), new LineTo(size/2,size/2-2), new LineTo(size-2,0), new LineTo(size/2,-size/2+2), new LineTo(0,0));
			break;
		case STAR:
			solution = new Path(new MoveTo(0,0), new LineTo(size/3+size/25, size/3-size/6), new LineTo(size/2,size/2-2), new LineTo(3*size/4.7, size/3-size/6), new LineTo(size-3,0), new LineTo(3*size/4.7, -size/3+size/6),new LineTo(size/2,-size/2+2), new LineTo(size/3+size/25, -size/3+size/6), new LineTo(0,0));
			break;
		default:
			solution = new Polygon();
			break;
		}
		solution.setFill(me.viewFillColor());
		solution.setStroke(me.viewOutlineColor());
		solution.setStrokeWidth(5);
		//world.add(solution,0,0)
		return solution;
	}
	
	public void quitButtonPressed() {
		System.exit(0);
	}
	
	public void setWorldButtonPressed() {
		worldWidthText.setPromptText("World Width?");
		worldHeightText.setPromptText("World Height?");
		try {
			int width = Integer.parseInt(worldWidthText.getText());
			int height = Integer.parseInt(worldHeightText.getText());
			Params.WORLD_WIDTH = width;
			Params.WORLD_HEIGHT = height;
			Critter.displayWorld(Main.root);			
		}
		catch(Exception e){
			worldWidthText.clear();
			worldHeightText.clear();
			worldWidthText.setPromptText("Not a Number!!!");
			worldHeightText.setPromptText("Not a number!!!");
		}
	}
	public boolean isNumeric(String str) { 
		  try {  
		    Integer.valueOf(str);  
		    return true;
		  } 
		  catch(NumberFormatException e){  
		    return false;  
		  }  
	}
		
		
	}
	

