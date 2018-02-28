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
import lab2.db.AppException;
import model.Author;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AuthorTableGateway {
private Connection conn;
	
	public AuthorTableGateway(Connection conn) {
		this.conn = conn;
	}
	private static Logger logger = LogManager.getLogger(Launcher.class);
	public void insertAuthor(Author author) throws SQLException{
		PreparedStatement st = null;
		ResultSet rs = null;
			st = conn.prepareStatement("insert into author (first_name, last_name,dob,gender, web_site) "+ "values (?, ?, ?,?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, author.getfirstName());
			st.setString(2, author.getLastName());
			st.setObject(3, author.getDob());
			st.setString(4, author.getGender());
			st.setString(5,  author.getWebSite());
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			if(rs.first()){
				author.setId(rs.getInt(1));
			}
			else{
				logger.error("Didn't get the new key.");
			}
			logger.info("New author created. Id = " + author.getId());
		}
	
	
	public void deleteAuthor(Author author){
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			String query = "delete from author "
					+ "where id = ?";
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, author.getId());
			
			//executeUpdate is used to run insert, update, and delete statements
			st.executeUpdate();
			logger.info("Author with id = " + author.getId() + " deleted from database.");
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
	
	public void updateAuthor(Author author) throws AppException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update author set first_name = ?, "
					+ "last_name= ?, dob=?, gender = ?, web_site = ? "
					+ "where id = ? ");
			st.setString(1, author.getfirstName());
			st.setString(2, author.getLastName());
			st.setObject(3,author.getDob());
			st.setString(4, author.getGender());
			st.setString(5, author.getWebSite());
			st.setInt(6, author.getId());
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
	
	public ObservableList<Author> getAuthors() throws SQLException {
		ObservableList<Author> authors = FXCollections.observableArrayList();
		
		PreparedStatement st = null;
			st = conn.prepareStatement("SELECT * FROM author order by first_name" );
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Author author = new Author(rs.getString("first_name"));
				author.setGateway(this);
				author.setId(rs.getInt("id"));
				author.setLastName(rs.getString("last_name"));
			    author.setDob(LocalDate.parse(rs.getString("dob")));
				author.setGender(rs.getString("gender"));
			    author.setWebSite(rs.getString("web_site"));
				authors.add(author);
			}
		
		return authors;
	}
}
