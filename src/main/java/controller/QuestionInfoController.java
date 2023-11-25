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
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/question_info")
public class QuestionInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String QUESTION_INFO_PAGE = "/WEB-INF/view/jsp/question_info.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HashMap<String, String> params = ParametersParser.parse(request.getQueryString());
        if (params.containsKey("item")) {
            try {
                DBConnection dbConnection = new DBConnection();
                dbConnection.establish();
                HashMap<String, String> answers = DBRequests.getAnswers(dbConnection.connection, params.get("item"));
                request.setAttribute("question", params.get("item"));
                request.setAttribute("answers", answers);
                request.setAttribute("research", params.get("research"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(QUESTION_INFO_PAGE);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                throw new ServletException();
            }
        }
    }
}
