package com.example.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/HelloWorldServlet")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloWorldServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		User user = null;
		
		try {
			Connection con = getConnection("localhost", 3306, "Hello-World",
					"root", "oNrBE6rvoyMmQDV8");
			PreparedStatement statement = con
					.prepareStatement("SELECT * FROM User WHERE firstName = ?");
			statement.setString(1, firstName);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				user = new User();
				user.id = result.getInt("id");
				user.firstName = result.getString("firstName");
				user.lastName = result.getString("lastName");
				user.statement = result.getString("statement");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		String userAsJson = new Gson().toJson(user);

		ServletOutputStream outputStream = response.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		
		
		
		writer.write(userAsJson);
		writer.close();
		outputStream.close();
	}

	public static Connection getConnection(String host, int port,
			String database, String user, String password) throws SQLException {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(host);
		dataSource.setPort(port);
		dataSource.setDatabaseName(database);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setConnectTimeout(100);
		return dataSource.getConnection();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
