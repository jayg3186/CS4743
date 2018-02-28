package lab2.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Publisher;

public class PublisherGateway{
	private Connection conn;
	
	public PublisherGateway(Connection conn) {
		this.conn = conn;
	}
	
	/*public void updateDog(Dog dog) throws AppException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update dog set dog_name = ?, breed_id = ? where id = ?");
			st.setString(1, dog.getDogName());
			st.setInt(2, dog.getBreed().getId());
			st.setInt(3, dog.getId());
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
	}*/
	
	public ObservableList<Publisher> getPublishers(int publsiherId) throws AppException {
		ObservableList<Publisher> publishers = FXCollections.observableArrayList();
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("select a.*"
					+ " from publisher a "
					+ " inner join book b on a.id = b.publisher_id "
					+ " where b.publisher_id = ?");
			st.setInt(1, publsiherId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				System.out.println("hey iim joining tables");
				Publisher publisher = new Publisher(rs.getString("Publisher_Name"));
				publisher.setId(rs.getInt("id"));
				publishers.add(publisher);
			}
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
		
		return publishers;
	}
}