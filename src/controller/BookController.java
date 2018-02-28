package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lab2.db.AuthorTableGateway;
import lab2.db.BookTableGateWay;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import model.Author;
import model.Book;

public class BookController implements Initializable, MyController {
	private Logger logger = LogManager.getLogger(BookController.class);
	@FXML private ListView<Book> bookList;
	private ObservableList<Book> books; 
	private BookTableGateWay gateway;
	private Book book;
	
	
	public BookController(BookTableGateWay gateway) throws SQLException{
		this.gateway = gateway;
		books = this.gateway.getBooks();
	}
  
	public BookController(ObservableList<Book> books) {
	     this.books = books;
	}
   
   @FXML
    void loadBookCell(MouseEvent event) throws IOException, SQLException {
	   if(event.getClickCount() == 2 ) {
    	  Book book =  bookList.getSelectionModel().getSelectedItem(); 
    	   if(book != null) {
    		   logger.info("view Book detail");
    		   AppController.getInstance().changeView(AppController.BOOK_DETAIL,book);
    	   }
       }
    }
	
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.bookList.setItems(books);
	}
}