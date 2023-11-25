package controller;

import data_access.DBConnection;
import data_access.DBRequests;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ParametersParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/research")
public class AddResearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ADD_RESEARCH_PAGE = "/WEB-INF/view/jsp/add_research.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        if (ParametersParser.parse(request.getQueryString()).isEmpty()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(ADD_RESEARCH_PAGE);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                throw new ServletException();
            }
        }
        else {
            String parameters = request.getQueryString();
            HashMap<String, String> paramsMap = ParametersParser.parse(parameters);
            try {
                DBConnection dbConnection = new DBConnection();
                dbConnection.establish();
                DBRequests.addResearch(dbConnection.connection, paramsMap.get("name"));
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(ADD_RESEARCH_PAGE);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                throw new ServletException();
            }
        }
    }
}
