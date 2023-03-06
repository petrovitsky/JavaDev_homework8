package org.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@WebServlet(value = "/")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ZonedDateTime utcDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        String sufix = "UTC";
        if (request.getParameter("timezone") != null && !request.getParameter("timezone").isEmpty()) {
            String timezoneParam = request.getParameter("timezone").substring(3).replaceAll(" ", "+");
            utcDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.of(timezoneParam));
            sufix = request.getParameter("timezone").replaceAll(" ", "+");
        }

        String timezoneParam = request.getParameter("timezone");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").appendLiteral(" " + sufix).toFormatter();
        String formattedDateTime = utcDateTime.format(formatter);

        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>" + formattedDateTime + "</h2>");
        out.println("</body></html>");
        out.close();
    }
}
