package recordstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recordstore.database.ChinookDatabase;
import recordstore.models.Artist;

public class ArtistDao {
	
	public List<Artist> getAllArtists() {
		 ArrayList<Artist> list = new ArrayList<>();
		
		// Kootaan kaikki artistit
		ChinookDatabase db = new ChinookDatabase();
		Connection connection = db.connect();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			statement = connection.prepareStatement("SELECT * FROM Artist ORDER BY Name ASC");
			results = statement.executeQuery();
			
			while (results.next()) {
				long id = results.getLong("ArtistId");
				String name = results.getString("Name");
				list.add(new Artist(id, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		db.close(results, statement, connection);
		
		return list;
	}
	
	public Artist findArtist(long id) {
		ChinookDatabase db = new ChinookDatabase();
        Connection connection = db.connect();
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM Artist WHERE ArtistId = ?");
            statement.setLong(1, id);
            results = statement.executeQuery();

            if (results.next()) {
                String name = results.getString("Name");
                Artist artist = new Artist(id, name);
                
                return artist;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close(results, statement, connection);
        }
    }
	
	public List<Artist> searchForArtist(String term) {
		 ArrayList<Artist> list = new ArrayList<>();
		
	
		ChinookDatabase db = new ChinookDatabase();
		Connection connection = db.connect();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		
		try {
			/*
			 * 
			 * https://www.w3schools.com/sql/sql_like.asp
			 * 
			 */
			statement = connection.prepareStatement("SELECT * FROM Artist WHERE NAME LIKE ? ORDER BY NAME ASC");
			statement.setString(1, "%"+term+"%");
			results = statement.executeQuery();
			
			while (results.next()) {
				long id = results.getLong("ArtistId");
				String name = results.getString("Name");
				list.add(new Artist(id, name));
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		db.close(results, statement, connection);
		
		return list;
	}
	
	public void addArtist(String name) {
		ChinookDatabase db = new ChinookDatabase();
		Connection connection = db.connect();
		PreparedStatement statement = null;
		
		
		
		try {
			statement = connection.prepareStatement("INSERT INTO Artist (Name) VALUES (?)");
			statement.setString(1, name);
			statement.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
				
			}
			try {
				connection.close();
			} catch (Exception e) {
				
			}
		}
	
	}
	
}
