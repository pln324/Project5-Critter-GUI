package assignment5;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;


public class Main extends Application {
	public static BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("CritterGUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			world = (GridPane)root.getRight();
//			world.setPrefHeight(Params.WORLD_HEIGHT*5);
//	        world.setPrefWidth(Params.WORLD_WIDTH*5);
//	        for (int i = 0; i < Params.WORLD_WIDTH; i++) {
//	            ColumnConstraints colConst = new ColumnConstraints();
//	            colConst.setMinWidth(1000/Params.WORLD_WIDTH);
//	            if(i==0) {
//	            	world.getColumnConstraints().set(i, colConst);
//	            }
//	            else {
//	            	world.getColumnConstraints().add(colConst);
//	            }
//	        }
//	        for (int i = 0; i < Params.WORLD_HEIGHT; i++) {
//	            RowConstraints rowConst = new RowConstraints();
//	            rowConst.setMinHeight(1000/Params.WORLD_WIDTH);
//	            if(i==0) {
//	            	world.getRowConstraints().set(i, rowConst);
//	            }
//	            else {
//	            	world.getRowConstraints().add(rowConst);   
//	            }
//	        }
			Critter.displayWorld(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
