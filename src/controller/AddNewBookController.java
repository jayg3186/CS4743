package controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lab2.db.BookTableGateWay;
import model.Book;

public class AddNewBookController implements Initializable, MyController {
	@FXML private TextField publisherId;
  	@FXML private TextArea aboutBook;
	@FXML private TextField title;
	@FXML private TextField Ypublished;
	@FXML private TextField bookIsnb;
	private BookTableGateWay gateway;
	private Book book;
	
	
	public AddNewBookController(BookTableGateWay gateway, Book book) {
	 	this.gateway = gateway;
	}
	public AddNewBookController(Book book) {
	this.book = book;	
	}
    @FXML
	    void addBook(ActionEvent event) throws SQLException, IOException {
         Book book = new Book();
         book.setTitle(title.getText()); 
         book.setSummary(aboutBook.getText());
         book.setYearPubliched(Integer.parseInt(Ypublished.getText()));
         book.setIsbn(bookIsnb.getText());
        // book.setPublisher();
         book.setGateway(this.gateway);
         book.insertBook();
         System.out.print("book is here");
         AppController.getInstance().changeView(AppController.BOOK_LIST, book);
    }

	@FXML
	    void DontAddBook(ActionEvent event) {

	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
	
	
	
	
	
	
	
	
	
	
	  



	}

