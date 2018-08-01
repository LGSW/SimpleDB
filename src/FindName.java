import java.sql.*;
import simpledb.remote.SimpleDriver;

public class FindName {
	public static void main(String args[]) {
		String Name = args[0];
		System.out.println("Here are the " + Name + " Name");
		System.out.println("Name,SId");

		Connection conn = null;
		try {
			Driver d = new SimpleDriver();

			conn = d.connect("jdbc:simpledb://localhost", null);

			// Step 2: execute the query
			Statement stmt = conn.createStatement();
			String qry = "select sname, SId " + "from student, dept " + "where sname = '" + Name + "'";

			ResultSet rs = stmt.executeQuery(qry);
			while (rs.next()) {
				String sname = rs.getString("sname");
				String SId = rs.getString("SId");
				System.out.println(sname + "\t" + SId);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Step 4: close the connection
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
