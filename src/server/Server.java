package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Input;
import fileio.MovieInput;
import server.pages.AuthHomePage;
import server.pages.IPage;
import server.pages.LoginPage;
import server.pages.LogoutPage;
import server.pages.MoviesPage;
import server.pages.RegisterPage;
import server.pages.SeeDetailsPage;
import server.pages.UnauthHomePage;
import server.pages.UpgradesPage;
import server.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Server {

    /**
     *
     * @param inputData
     * @param output
     */
    public void serverRun(final Input inputData,
                          final ArrayNode output) {
        UnauthHomePage unauthHomePage = new UnauthHomePage();
        AuthHomePage authHomePage = new AuthHomePage();
        LoginPage loginPage = new LoginPage(inputData);
        RegisterPage registerPage = new RegisterPage(inputData);
        MoviesPage moviesPage = new MoviesPage();
        SeeDetailsPage seeDetailsPage = new SeeDetailsPage();
        LogoutPage logoutPage = new LogoutPage();
        UpgradesPage upgradesPage = new UpgradesPage();
        ServerState.setCurrentPage(unauthHomePage);

        for (ActionsInput action : inputData.getActions()) {

            if (action.getType().equals("change page")) {
                boolean result = ServerState.getCurrentPage().changePage(action.getPage(), inputData, output, action);
                if (!result) {
                    Utils.printStatus(output, "Error", new ArrayList<>(), null);
                    continue;
                }

                if (action.getPage().equals("login")) {
                    ServerState.setCurrentPage(loginPage);
                } else if (action.getPage().equals("register")) {
                    ServerState.setCurrentPage(registerPage);
                } else if (action.getPage().equals("movies")) {
                    ServerState.setCurrentPage(moviesPage);
                    List<MovieInput> currentMoviesList =
                            Utils.getCurrentMoviesList(inputData.getMovies(), ServerState.getCurrentUser());
                    Utils.printStatus(output, null, currentMoviesList, ServerState.getCurrentUser());
                } else if (action.getPage().equals("see details")) {
                    ServerState.setCurrentPage(seeDetailsPage);
                    seeDetailsPage.setCurrentMovieName(action.getMovie());
                } else if (action.getPage().equals("logout")) {
                    ServerState.setCurrentPage(logoutPage);
                    ServerState.setCurrentUser(null);
                } else if (action.getPage().equals("upgrades")) {
                    ServerState.setCurrentPage(upgradesPage);
                }
            } else if (action.getType().equals("on page")) {
                boolean result = ServerState.getCurrentPage().executeAction(action, output, inputData);
                if (!result) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ObjectNode outputNode = objectMapper.createObjectNode();
                    outputNode.put("error", "Error");
                    outputNode.set("currentMoviesList", objectMapper.createArrayNode());
                    outputNode.putNull("currentUser");
                    output.add(outputNode);
                }

                IPage currentPage = ServerState.getCurrentPage();
                if (currentPage instanceof LoginPage || currentPage instanceof RegisterPage) {
                     if (result) {
                         ServerState.setCurrentPage(authHomePage);
                    } else {
                         ServerState.setCurrentPage(unauthHomePage);
                    }
                } else if (currentPage instanceof LogoutPage) {
                    ServerState.setCurrentPage(unauthHomePage);
                }
            }
        }
    }
}
