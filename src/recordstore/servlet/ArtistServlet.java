package recordstore.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recordstore.dao.ArtistDao;
import recordstore.models.Artist;

@WebServlet("/")
public class ArtistServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private ArtistDao artistDao = new ArtistDao();
	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String artistid = req.getParameter("id");

		if (artistid == null || artistid.equals("")) {
			
			List<Artist> artists = artistDao.getAllArtists();
			req.setAttribute("artists", artists);

		}

		req.getRequestDispatcher("/WEB-INF/views/artist.jsp").include(req, resp);
	} 
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String artistname = req.getParameter("artistname");
		artistname = artistname.substring(0, 1).toUpperCase()+artistname.substring(1);
		artistDao.addArtist(artistname);
		
		List<Artist> artists = artistDao.getAllArtists();
		req.setAttribute("artists", artists);
		
		req.getRequestDispatcher("/WEB-INF/views/artist.jsp").include(req, resp);
	} 
}