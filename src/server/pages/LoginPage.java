package server.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Input;
import fileio.UserInput;
import server.ServerState;
import server.util.Mapper;

public class LoginPage implements IPage {

    /**
     *
     * @param input
     */
    public LoginPage(final Input input) {
    }

    /**
     *
     * @param action
     * @param output
     * @param input
     * @return
     */
    @Override
    public boolean executeAction(final ActionsInput action,
                                 final ArrayNode output,
                                 final Input input) {
        if (!action.getFeature().equals("login")) {
            return false;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        UserInput user = input.getUsers().stream()
                .filter(u -> u.getCredentials().getName().equals(action.getCredentials().getName())
                        && u.getCredentials().getPassword().equals(action.getCredentials().getPassword()))
                .findFirst()
                .orElse(null);
        ServerState.setCurrentUser(user);

        if (user == null) {
            return false;
        }

        outputNode.putNull("error");
        outputNode.set("currentMoviesList", objectMapper.createArrayNode());
        outputNode.set("currentUser", Mapper.mapUser(user));
        output.add(outputNode);
        return true;
    }

    /**
     *
     * @param page
     * @param inputData
     * @param output
     * @param action
     * @return
     */
    @Override
    public boolean changePage(final String page,
                              final Input inputData,
                              final ArrayNode output,
                              final ActionsInput action) {
        if (page.equals("login")) {
            return false;
        }

        if (page.equals("logout")) {
            return false;
        }
        return true;
    }
}
