package fi.omapizzeria.sivusto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fi.omapizzeria.sivusto.bean.WebUser;
import fi.omapizzeria.sivusto.dao.DAO;
import fi.omapizzeria.sivusto.dao.DAOPoikkeus;

public class WebUserDAO extends DAO {
	public WebUserDAO() throws DAOPoikkeus {
		

		try {
			Class.forName(
					DBConnectionProperties.getInstance().getProperty("driver"))
					.newInstance();
		} catch (Exception e) {
			throw new DAOPoikkeus("Tietokannan ajuria ei kyetty lataamaan.", e);
		}
	}

	/**
	 * Lis�� uuden webuserin tietokantaan
	 * 
	 * @param kayttaja
	 *            uuden webuserin tiedot
	 * @throws UsernameVarattuPoikkeus
	 *             Mik�li tietokannasta l�ytyy jo k�ytt�j� samalla usernamella
	 * @throws DAOPoikkeus
	 *             Mik�li tietokantahaussa tapahtuu virhe
	 */
	public void lisaa(WebUser kayttaja) throws UsernameVarattuPoikkeus,
			DAOPoikkeus {
		Connection yhteys = avaaYhteys();
		try {
			// tarkistetaan, ett� usernamella ei jo l�ydy k�ytt�j��
			PreparedStatement usernameHaku = yhteys
					.prepareStatement("select id from webuser where username = ?");
			usernameHaku.setString(1, kayttaja.getUsername());
			ResultSet rs = usernameHaku.executeQuery();
			if (rs.next())
				throw new UsernameVarattuPoikkeus();
			// suoritetaan lis�ys
			PreparedStatement insertLause = yhteys
					.prepareStatement("insert into webuser(username, password_hash, salt) values(?,?,?)");
			insertLause.setString(1, kayttaja.getUsername());
			insertLause.setString(2, kayttaja.getPasswordHash());
			insertLause.setString(3, kayttaja.getSalt());
			insertLause.executeUpdate();
		} catch (SQLException e) {
			// JOTAIN VIRHETT� TAPAHTUI
			throw new DAOPoikkeus("Tietokantahaku aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}
	}

	public WebUser hae(String username) throws DAOPoikkeus {
		WebUser kayttaja;
		Connection yhteys = avaaYhteys();
		try {
			// tarkistetaan, ett� usernamella ei jo l�ydy k�ytt�j��
			PreparedStatement usernameHaku = yhteys
					.prepareStatement("select id, username, salt, password_hash from webuser where username = ?");
			usernameHaku.setString(1, username);
			ResultSet rs = usernameHaku.executeQuery();
			if (rs.next()) {
				// L�YTYI
				kayttaja = new WebUser(rs.getInt("id"),
						rs.getString("username"), rs.getString("salt"),
						rs.getString("password_hash"));
			} else {
				// EI L�YTYNYT
				// generoidaan kuitenkin tyhj� user, jotta
				// login tarkistus kest�� aina yht� kauan
				kayttaja = new WebUser(-1, "-", "-", "-");
			}
		} catch (SQLException e) {
			// JOTAIN VIRHETT� TAPAHTUI
			throw new DAOPoikkeus("Tietokantahaku aiheutti virheen", e);
		} finally {
			// LOPULTA AINA SULJETAAN YHTEYS
			suljeYhteys(yhteys);
		}
		return kayttaja;
	}
}