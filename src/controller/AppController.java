package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.Launcher;
import lab2.db.AuthorTableGateway;
import lab2.db.BookTableGateWay;
import model.Author;
import model.Book;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class AppController implements Initializable {
	private static Logger logger = LogManager.getLogger(Launcher.class);

	public static final int AUTHOR_LIST = 1;
	public static final int AUTHOR_DETAIL = 2;
	public static final int NEW_AUTHOR =3;
	public static final int BOOK_LIST = 4;
	public static final int NEW_BOOK =5;
	public static final int BOOK_DETAIL= 6;
    public int state = 0;
	private static AppController myInstance = null;
	
	private BorderPane rootPane = null;
	
	private Connection conn;
	
	private AppController() {
		//TODO: instantiate models
	}
	
	public void changeView(int viewType, Object arg) throws IOException, SQLException {
			MyController controller = null;
			URL fxmlFile = null;
			switch(viewType) {
				case AUTHOR_LIST:
					fxmlFile = this.getClass().getResource("/view/AuthorList.fxml");
					controller = new AuthorListController(new AuthorTableGateway(conn));
					logger.info("switched to author List pane");
					break;
				case AUTHOR_DETAIL:
					fxmlFile = this.getClass().getResource("/view/AuthorDetailView.fxml");
					controller = new AuthorDetailController((Author) arg);
					logger.info("switched to author detail pane");
					break;
				case NEW_AUTHOR:
					fxmlFile = this.getClass().getResource("/view/addauthor.fxml");
					controller = new AuthorAddController(new AuthorTableGateway(conn),(Author)arg); 
					logger.info("switched to add author pane");
					break;
				case BOOK_LIST:
					fxmlFile = this.getClass().getResource("/view/BookList.fxml");
					controller = new BookController(new BookTableGateWay(conn));
					logger.info("switched to book list pane");
					break;
				case NEW_BOOK:
					fxmlFile = this.getClass().getResource("/view/AddNewBook.fxml");
					controller = new AddNewBookController(new BookTableGateWay(conn),(Book)arg); 
					logger.info("switched to add book pane");
					break;
				case BOOK_DETAIL:
					fxmlFile = this.getClass().getResource("/view/BookDetailView.fxml");
					controller = new BookDetailController((Book)arg);
					logger.info("switched to book detail pane");
			}
			FXMLLoader loader = new FXMLLoader(fxmlFile);
			loader.setController(controller);
			Parent viewNode = loader.load();
			rootPane.setCenter(viewNode);

			//throw new AppException(e);
	}
    @FXML
    void ShowAuthorList(ActionEvent event) throws IOException, SQLException {
    	logger.info("Author list item clicked");
    		changeView(AUTHOR_LIST, null);
    }

    @FXML
    void closeMe(ActionEvent event) {
    	logger.info("Quit application item clicked");
    		Platform.exit();
    }
	
    @FXML
    void addNewAuthor(ActionEvent event) throws IOException, SQLException {
    	logger.info("add new author clicked");
           changeView(NEW_AUTHOR, null);
    }
	@FXML
    void showBookList(ActionEvent event) throws IOException, SQLException {
		changeView(BOOK_LIST, null);
    } 
	@FXML
    void addNewBook(ActionEvent event) throws IOException, SQLException {
    	changeView(NEW_BOOK, null);
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	
	

   
 
	public static AppController getInstance() {
		if(myInstance == null)
			myInstance = new AppController();
		return myInstance;
	}
	
	public BorderPane getRootPane() {
		return rootPane;
	}

	public void setRootPane(BorderPane rootPane) {
		this.rootPane = rootPane;
	}

	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
}
