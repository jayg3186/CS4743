package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import lab2.db.BookTableGateWay;
import model.Book;
import model.Publisher;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

//import com.sun.javafx.css.converters.StringConverter;

import javafx.fxml.Initializable;

public class BookDetailController implements Initializable, MyController {
        @FXML private DatePicker dateBookAdded;
	    @FXML private TextField YearPublished;
	    @FXML private TextField bookIsbn;
	    @FXML private ComboBox<Publisher> publishers;
	    @FXML private TextField bookTitle;
	    ObservableList<Publisher> listOfBooksPublisher; 
	    private List<Publisher>  bookPublishers;
	     private Book book;
	    
	    public BookDetailController() {
	    }
	    public BookDetailController(Book book) {
	    	this();
	    	this.book = book;
	    	this.bookPublishers = book.getPubLishersbyId();
	    	this.listOfBooksPublisher = FXCollections.observableList
	    			(this.bookPublishers);
	    	
	    }   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	 
     bookTitle.textProperty().bindBidirectional(book.titleproperty());
     YearPublished.textProperty().bindBidirectional(book.yearPublishedProperty(), new NumberStringConverter());
     bookIsbn.textProperty().bindBidirectional(book.isbnproperty());
     publishers.setValue(this.bookPublishers.get(0));
     publishers.setItems(this.listOfBooksPublisher);
     //dateBookAdded.valueProperty().bindBidirectional(book.dateAddedProperty());
	}
	

	}
	
	
