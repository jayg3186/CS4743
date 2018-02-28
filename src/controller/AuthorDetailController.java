package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import  javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Author;

public class AuthorDetailController implements Initializable, MyController{

	
	
		private Logger logger = LogManager.getLogger(AuthorListController.class);
	    @FXML private TextField webSite;
	    @FXML private TextField lName;
	    @FXML private TextField fName;
	    @FXML private Button saveAuthor;
        @FXML private Button delButton;
	    @FXML private DatePicker dob;
	    @FXML private TextField sex;
        private Author author ;
        
        public AuthorDetailController() {
        	
        }
        
        public AuthorDetailController(Author author) {
        	this();
        	this.author = author;
        }
        
        
	    @FXML
	    void saveAuthorClicked(ActionEvent event) {
	    	logger.info("Author's name is " + author.getfirstName());
	    	author.updateAuthor();
	    }


	    @FXML
	    void deleteAuthor(ActionEvent event) throws IOException, SQLException {
	    	author.deleteAuthor();
	    	logger.info(author.getfirstName() + "was deleted from the list of Authors");
	    	AppController.getInstance().changeView(AppController.AUTHOR_LIST, author);
	    	
	    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		fName.textProperty().bindBidirectional(author.firstNameProperty());
		lName.textProperty().bindBidirectional(author.lastNameProperty());
		dob.valueProperty().bindBidirectional(author.dobProperty());
		sex.textProperty().bindBidirectional(author.genderProperty());
		webSite.textProperty().bindBidirectional(author.websiteProperty());
	}
}
