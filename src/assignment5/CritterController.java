package assignment5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
    private Button CreateButton;

    @FXML
    private TextField textNum;

    @FXML
    private TextField CritterText;

    @FXML
    private Button stepButton;

    @FXML
    private TextField stepText;

    @FXML
    private TextField seedText;

    @FXML
    private Button seedButton;

    @FXML
    private Button quitButton;

    @FXML
    private TextField statsCritterText;

    @FXML
    private Button goButton;

    @FXML
    private TextArea statsText;

    @FXML
    private Button animateButton;

    @FXML
    private Button stopAnimation;

    @FXML
    private TextField aniSpeed;
    @FXML
    private Button setWorldButton;
    @FXML 
    private TextField worldWidthText;
    @FXML
    private TextField worldHeightText;
	   
    public void CreateButtonHovered() {
		if(Critter.getRandomInt(5)==0) {
			CreateButton.setStyle("-fx-background-color: red; ");
		}
		else if(Critter.getRandomInt(5)==1) {
			CreateButton.setStyle("-fx-background-color: yellow; ");
		}
		else if(Critter.getRandomInt(5)==2) {
			CreateButton.setStyle("-fx-background-color: blue; ");
		}
		else if(Critter.getRandomInt(5)==3) {
			CreateButton.setStyle("-fx-background-color: purple; ");
		}
		else if(Critter.getRandomInt(5)==4) {
			CreateButton.setStyle("-fx-background-color: orange; ");
		}
	}
    
	public void CreateButtonPressed () {
		textNum.setPromptText("How many?");
		CritterText.setPromptText("What Critter?");
		try {
			for(int i=0; i<Integer.parseInt(textNum.getText()); i++) {
				Critter.createCritter(CritterText.getText());
			}
			Critter.displayWorld(Main.root);
			updateStats();
		} catch (InvalidCritterException e) {
			CritterText.clear();
			CritterText.setPromptText("not a critter!");
		} catch (NumberFormatException e) {
			textNum.clear();
			textNum.setPromptText("not a number!");
		}
	}
	
	public void stepButtonPressed() {
		stepText.setPromptText("How many steps?");
		try {
			int loop = Integer.parseInt(stepText.getText());
			for(int i=0;i<loop;i++) {
				Critter.worldTimeStep();
			}
			Critter.displayWorld(Main.root);
			updateStats();
		}
		catch(NullPointerException|NumberFormatException e) {
			Critter.worldTimeStep();
			Critter.displayWorld(Main.root);
			updateStats();
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
	
	public void updateStats() {
		String myPackage = Critter.class.getPackage().toString().split(" ")[1];
		String inClass = statsCritterText.getText();
		java.util.List<Critter> lCritter = null;
		Class<?> statClass = null;
		String name = "runStats";

		try {
			lCritter = Critter.getInstances(inClass);
			Class<?>[] paramTypes = {List.class};
			statClass = Class.forName(myPackage+"."+inClass);
			/*get the "runStats" method from the class user puts in as the second input*/
			Method runStats = statClass.getMethod(name, paramTypes);
			String output = (String) runStats.invoke(statClass, lCritter);
			statsText.setText(output);
		}
		catch(InvalidCritterException|SecurityException|IllegalAccessException|InvocationTargetException |ClassNotFoundException|NoClassDefFoundError e) {
			statsText.clear();
			statsText.setPromptText("Not a valid critter");
	}
		/*if no method can be found in the class, use the default "runStats" in the Critter class*/
		catch(NoSuchMethodException e) {
			Critter.runStats(lCritter);
		}
		catch(NullPointerException e) {
			statsText.clear();
			statsText.setPromptText("Not a valid critter");
		}
	}
	
	public boolean animate = true;
	
	private class myTimer extends AnimationTimer {

		int wait;
		int frames;
		
		public myTimer(int frames) {
			wait = 0;
			this.frames = frames;
		}
		
		@Override
		public void handle(long now) {
			if (animate == false) {
				stop();
				animate = true;
				return;
			}
			if(wait >= 20) {
				for (int i=0; i<frames; i++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld(Main.root);
				updateStats();
				wait = 0;
			}
			wait++;
		}
		
	}
	
	public void animate() {
		animateButton.setVisible(false);
		animateButton.setDisable(true);
		stopAnimation.setVisible(true);
		CreateButton.setDisable(true);
		textNum.setDisable(true);
	    CritterText.setDisable(true);
	    stepText.setDisable(true);
	    seedText.setDisable(true);
	    stepButton.setDisable(true);
	    seedButton.setDisable(true);
	    statsText.setDisable(true);
	    goButton.setDisable(true);
	    aniSpeed.setDisable(true);
		int loop;
	    try {
			loop = Integer.parseInt(stepText.getText());
		}
		catch(NullPointerException|NumberFormatException e) {
			loop = 1;
		}
	    AnimationTimer timer = new myTimer(loop);
	    timer.start();
	}
	
	public void stopAnimate() {
		animateButton.setVisible(true);
		animateButton.setDisable(false);
		stopAnimation.setVisible(false);
		CreateButton.setDisable(false);
		textNum.setDisable(false);
	    CritterText.setDisable(false);
	    stepText.setDisable(false);
	    seedText.setDisable(false);
	    stepButton.setDisable(false);
	    seedButton.setDisable(false);
	    statsText.setDisable(false);
	    goButton.setDisable(false);
	    aniSpeed.setDisable(false);
	    animate = false;
	}
	
	public static Shape toShape(Critter me) {
		Shape solution;
		int size = Math.min(1000/Params.WORLD_WIDTH, 1000/Params.WORLD_HEIGHT);
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
			worldWidthText.setPromptText("Not a number!!!");
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
