package lab2.db;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.Launcher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import lab2.db.AppException;
import model.Author;
import model.Book;
import model.Publisher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookTableGateWay {
private Connection conn;
	
	public BookTableGateWay(Connection conn) {
		this.conn = conn;
	}
	private static Logger logger = LogManager.getLogger(Launcher.class);
	public void AddBook(Book book) throws SQLException{
		PreparedStatement st = null;
		ResultSet rs = null;
			st = conn.prepareStatement("insert into book (title, summary,year_published,isbn) "+ "values (?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, book.getTitle());
			st.setString(2, book.getSummary());
			st.setInt(3, book.getYearPubliched());
			st.setString(4, book.getIsbn());
			System.out.println("hey i am in the data base");
			//st.setObject(5, book.getDateAdded());
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			if(rs.first()){
			   book.setId(rs.getInt(1));
			}
			else{
				logger.error("Didn't get the new key.");
			}
			logger.info("New author created. Id = " + book.getId());
		}
	
	
	public void deleteBook(Book book){
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			String query = "delete from book "
					+ "where id = ?";
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, book.getId());
			
			//executeUpdate is used to run insert, update, and delete statements
			st.executeUpdate();
			logger.info("Author with id = " + book.getId() + " deleted from database.");
		} catch (SQLException e) {
			logger.error("Error deleting from author table in database: " + e.getMessage());
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("Statement or Result Set close error: " + e.getMessage());
			}
		}
	}
	public void createTable(String tablename){
		PreparedStatement st = null;
		try {
			
			String query = "CREATE TABLE IF NOT EXISTS " + tablename +
					       "(id int(11) NOT NULL AUTO_INCREMENT, " +
					       "first_name varchar(100) NOT NULL, " +
					       "last_name varchar(100) NOT NULL, " +
					       "dob varchar(10) NOT NULL, " +
					       "gender varchar(1) NOT NULL, " +
					       "web_site varchar(100) NOT NULL, " +
					       "PRIMARY KEY ( id ))";
			
			st = conn.prepareStatement(query);
			
			//execute 
			st.execute();
			logger.info(tablename + " table created in database.");
		} catch (SQLException e) {
			logger.error("Error creating " + tablename + " table in database: " + e.getMessage());
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("Statement or Result Set close error: " + e.getMessage());
			}
		}
	}
	public void close(){
		try{
			if(conn != null){
				//close the connection
				conn.close();
				logger.info("Database connection closed.");
			}
		} catch(SQLException e){
			
			logger.error("Error closing connection: " + e.getMessage());
		}
	}
	
	public void updateBook(Book book) throws AppException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update book set title = ?, "
					+ "summary= ?, year-published=?, isbn = ?, date_added = ? "
					+ "where id = ? ");
			st.setString(1, book.getTitle());
			st.setString(2, book.getSummary());
			st.setInt(3,book.getYearPubliched());
			st.setString(4, book.getIsbn());
			st.setObject(5, book.getDateAdded());
			//st.setInt(6, author.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e);
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AppException(e);
			}
		}
	}
	
	public ObservableList<Book> getBooks() throws SQLException {
		ObservableList<Book> books = FXCollections.observableArrayList();
		PreparedStatement st = null;
			st = conn.prepareStatement("SELECT * FROM book order by title" );
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Book book = new Book(rs.getString("title"));
				book.setGateway(this);
				book.setId(rs.getInt("id"));
				book.setSummary(rs.getString("summary"));
				book.setYearPubliched(rs.getInt("year_published"));
				book.setIsbn(rs.getString("isbn"));
				//book.setDateAdded(LocalDate.parse(rs.getString("date_added")));
			   
				books.add(book);
			}
		
		return books;
	}
	public List<Publisher> getPublishersById(int Id) {
		PublisherGateway gateway = new PublisherGateway(this.conn);
		return gateway.getPublishers(Id);
	}
}
