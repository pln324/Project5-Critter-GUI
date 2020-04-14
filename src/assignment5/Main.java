/* CRITTERS Main.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * Kangji Chen
 * kc36498
 * 16295
 * Slip days used: <0>
 * Spring 2020
 */
package assignment5;
	
import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
	
	public Set<String> listFiles(String dir) {
	    return Stream.of(new File(dir).listFiles())
	      .filter(file -> !file.isDirectory())
	      .map(File::getName)
	      .collect(Collectors.toSet());
	}
}
