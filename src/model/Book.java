package model;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lab2.db.BookTableGateWay;
public class Book {
int id;
private BookTableGateWay gateway;
private SimpleStringProperty title;
private SimpleStringProperty summary;
private SimpleIntegerProperty yearPubliched;
private SimpleStringProperty isbn;
private SimpleObjectProperty<Publisher> publisher;
private SimpleObjectProperty<LocalDate> dateAdded;
public Book(String title) {
	// TODO Auto-generated constructor stub
	 this.title = new SimpleStringProperty(title);
     this.summary = new SimpleStringProperty(null);
     this.yearPubliched = new SimpleIntegerProperty(2018); 
     this.isbn = new SimpleStringProperty("un-assigned");
       this.publisher = new SimpleObjectProperty<Publisher>(new Publisher());
    // this.dateAdded = new SimpleObjectProperty<LocalDate>();
}
public Book() {
	this("Untitled Book");
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitle() {
	return title.get();
}
public void setTitle(String title) {
	this.title.set(title);;
}
public String getSummary() {
	return summary.get();
}
public void setSummary(String summary) {
	this.summary.set(summary);;
}
public int getYearPubliched() {
	return yearPubliched.get();
}
public void setYearPubliched(int yearPubliched) {
	this.yearPubliched.set(yearPubliched);
}
public String getIsbn() {
	return isbn.get();
}
public void setIsbn(String isbn) {
	this.isbn.set(isbn);
}
public Publisher getPublisher() {
	return publisher.getValue();
}
public void setPublisher(Publisher publisher) {
	this.publisher.setValue(publisher);
}
public LocalDate getDateAdded() {
	return dateAdded.get();
}
public void setDateAdded(LocalDate dateAdded) {
	this.dateAdded.setValue(dateAdded);
}
public boolean ValidationTitle(String titleEntered) {
	boolean result = true;
	if(titleEntered.length() < 1 || titleEntered.length() > 255){
		result = false;
	}
	return result;
	
  }
public boolean validateSummary() {
	boolean result = true;
	if(this.summary.get().length() < 65536) {
		result = false;
	}
	return result;
	}
public boolean validateYear() {
	boolean result = true;
	int yearCurr = Calendar.getInstance().get(Calendar.YEAR);
	if(this.yearPubliched.get() > yearCurr) {
		result = false;
	}
	return result;
	}
public boolean validateIsbn() {
	boolean result = true;
	if(this.isbn.get().length() > 13) {
		result = false;
	}
	return result;
	}
public void setGateway(BookTableGateWay gateway) {
	this.gateway = gateway;
}
public BookTableGateWay setGateWay() {
   return gateway;
}
public String toString() {
 return this.title.get();
}
public SimpleStringProperty titleproperty() {
	// TODO Auto-generated method stub
	return this.title;
}



public SimpleStringProperty isbnproperty() {
	
	return this.isbn;
}

public SimpleIntegerProperty yearPublishedProperty() {
	// TODO Auto-generated method stub
	return this.yearPubliched ;
}
public SimpleObjectProperty<LocalDate> dateAddedProperty() {
	// TODO Auto-generated method stub
	return this.dateAdded;
}
public void insertBook() throws SQLException {
	if(validateIsbn() && validateYear() && validateSummary()) {
		this.gateway.AddBook(this);
	}
	
}
public List<Publisher> getPubLishersbyId(){
	return this.gateway.getPublishersById(this.publisher.get().getId());
}

}