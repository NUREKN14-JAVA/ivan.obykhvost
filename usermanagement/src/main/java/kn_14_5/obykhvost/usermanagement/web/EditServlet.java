package kn_14_5.obykhvost.usermanagement.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kn_14_5.obykhvost.usermanagement.User;
import kn_14_5.obykhvost.usermanagement.db.DaoFactory;
import kn_14_5.obykhvost.usermanagement.db.DatabaseException;

public class EditServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("okButton") != null)
		{
			doOk(req, resp);
		}
		else if(req.getParameter("cancelButton") != null)
		{
			doCancel(req, resp);
		}
		else
		{
			showPage(req, resp);
		}
	}

	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}

	protected void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/browse.jsp").forward(req, resp);
	}

	protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = null;
		try 
		{
			user = getUser(req);
		} 
		catch (ValidationException e1) 
		{
			req.setAttribute("error", e1.getMessage());
			showPage(req, resp);
			return;
		}
		try 
		{
			processUser(user);
		} 
		catch (DatabaseException e) 
		{
			throw new ServletException(e);
		}
		req.getRequestDispatcher("/browse").forward(req, resp);
	}

	protected void processUser(User user) throws DatabaseException {
		DaoFactory.getInstance().getUserDao().update(user);
	}

	protected User getUser(HttpServletRequest req) throws ValidationException {
		User user = new User();
		String idStr = req.getParameter("id");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String dateStr = req.getParameter("dateOfBirth");
		if(firstName == null)
		{
			throw new ValidationException("First name is empty");
		}
		if(lastName == null)
		{
			throw new ValidationException("Last name is empty");
		}
		if(dateStr == null)
		{
			throw new ValidationException("Date is empty");
		}
		if(idStr != null && idStr.trim().length() != 0)
		{
			user.setId(new Long(idStr));
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		try 
		{
			user.setDateOfBirth((new SimpleDateFormat("dd.MM.yyyy")).parse(dateStr));
		} 
		catch (ParseException e) 
		{
			throw new ValidationException(e);
		}
		return user;
	}

}
