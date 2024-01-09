package clientSide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View extends Application {

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("KanPlan");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}
