package ServicesDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iamfinal.datamodel.Identity;

public class JDBCConnection implements IdentityDAO {

	private static final String DB_Host = "dbURL";

	@Override
	public void create(Identity identity) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();

			String insertTableSQL = "INSERT INTO IDENTITIES(UID, EMAIL, DISPLAY_NAME) VALUES (? , ? ,?)";
			preparedStatement = connection.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, identity.getUid());
			preparedStatement.setString(2, identity.getUid());
			preparedStatement.setString(3, identity.getDisplayName());
			// execute insert SQL statement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public List<Identity> search(Identity criteria) {

		final List<Identity> result = new ArrayList<>();
		Connection connection = null;
		try {
			connection = getConnection();
			final String search = "SELECT DISPLAY_NAME, EMAIL, UID FROM IDENTITIES "
					+ "WHERE (? IS NULL OR DISPLAY_NAME LIKE ?)" + "AND (? IS NULL OR EMAIL LIKE ?)"
					+ " AND (? IS NULL OR UID = ?)";
			final PreparedStatement prstmt = connection.prepareStatement(search);
			prstmt.setString(1, criteria.getDisplayName());
			prstmt.setString(2, criteria.getDisplayName() + "%");
			prstmt.setString(3, criteria.getEmail());
			prstmt.setString(4, criteria.getEmail() + "%");
			prstmt.setString(5, criteria.getUid());
			prstmt.setString(6, criteria.getUid() + "%");
			final ResultSet rs = prstmt.executeQuery();
			while (rs.next()) {
				final Identity currentIdentity = new Identity();
				currentIdentity.setDisplayName(rs.getString("Display_Name"));
				currentIdentity.setEmail(rs.getString("EMAIL"));
				currentIdentity.setUid(rs.getString("UID"));
				result.add(currentIdentity);

			}

		} catch (ClassNotFoundException | SQLException e) {
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	@Override
	public void update(Identity identity) {
		Connection connection = null;
		try {
			connection = getConnection();
			final String update = " UPDATE IDENTITIES SET DISPLAY_NAME = ? ," + "EMAIL = ? " + "UID = ? ";
			final PreparedStatement pstmt = connection.prepareStatement(update);
			pstmt.setString(1, identity.getDisplayName());
			pstmt.setString(2, identity.getEmail());
			pstmt.setString(3, identity.getUid());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void delete(Identity identity) {

		Connection connection = null;
		try {
			connection = getConnection();
			String delete = "DELETE FROM IDENTITIES WHERE DISPLAY_NAME = ?" + "EMAIL = ? " + "UID = ? ";
			PreparedStatement preparestmt = connection.prepareStatement(delete);
			preparestmt.setString(1, identity.getDisplayName());
			preparestmt.setString(2, identity.getEmail());
			preparestmt.setString(3, identity.getUid());
			preparestmt.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Connection getConnection()
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		Connection connection = null;
		try {
			Configuration confService = Configuration.getInstance();
			String url = confService.getConfigurationValue(DB_Host);
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			connection = DriverManager.getConnection(url);
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

}