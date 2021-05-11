import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.GameInfoDAOImpl;
import model.GameInfo;


@WebServlet("/")
public class GameServlet extends HttpServlet {
    private GameInfoDAOImpl dao = GameInfoDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();
//        for (Map.Entry<String, String[]> entry : params.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue()[0]);
//        }
        String whiteP = "";
        String blackP = "";
        String winner = "";
        String[] rawDate = new String[10];
        StringBuilder builder = new StringBuilder() ;
        List<GameInfo> filtered = dao.findAll();
        if ( params.get("whiteP") != null && params.get("blackP") != null &&  params.get("winner") != null && params.get("date") != null){
            whiteP = params.get("whiteP")[0];
            blackP = params.get("blackP")[0];
            winner = params.get("winner")[0];
            rawDate= params.get("date");
            for (String s : rawDate) {
                builder.append(s);
            }
        }
        String date = builder.toString();

        if (!whiteP.trim().equals("")) {
            String finalWhiteP = whiteP;
            filtered = filtered.stream().filter(
                    game -> game.getWhitePlayerName()
                            .toLowerCase()
                            .startsWith(finalWhiteP.toLowerCase())).collect(Collectors.toList());
        }
        if (blackP != null && !blackP.trim().equals("")) {
            String finalBlackP = blackP;
            filtered = filtered.stream().filter(
                    game -> game.getBlackPlayerName()
                            .toLowerCase()
                            .startsWith(finalBlackP.toLowerCase())).collect(Collectors.toList());
        }
        if (winner != null && !winner.trim().equals("")) {
            String finalWinner = winner;
            filtered = filtered.stream().filter(
                    game -> game.getWinner()
                            .toLowerCase()
                            .startsWith(finalWinner.toLowerCase())).collect(Collectors.toList());
        }
        if (!date.trim().equals("")) {
            filtered = filtered.stream().filter(
                    game -> game.getDate()
                            .startsWith(date)).collect(Collectors.toList());
        }


        req.setAttribute("gameList", filtered);
        getServletConfig().getServletContext().getRequestDispatcher("/pages/index.jsp").forward(req, resp);
        //resp.sendRedirect("pages/index.jsp");
    }
}
