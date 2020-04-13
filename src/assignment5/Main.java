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
			
//			String myPackage = Critter.class.getPackage().toString().split(" ")[1];
//			Set<String> listFiles = listFiles(myPackage);
//			ArrayList<String> files = new ArrayList<String>();
//			for(int i=0; i<listFiles.size(); i++) {
//				String str = (String)listFiles.toArray()[i];
//				files.add(str.substring(0, str.lastIndexOf('.')));
//			}
//			for(int i=0; i<files.size(); i++) {
//				if(!Critter.class.isAssignableFrom(Class.forName(myPackage+"."+files.get(i)))){
//					files.remove(i);
//				}
//			}
//			ComboBox critters = new ComboBox(FXCollections.observableArrayList(files));
//			GridPane buttons = (GridPane)root.getLeft();
//			buttons.add(critters, 3, 0);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
