package server.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.CredentialsInput;
import fileio.Input;
import fileio.UserInput;
import server.ServerState;
import server.util.Mapper;

import java.util.List;

public class RegisterPage implements IPage {


    public RegisterPage(final Input input) {
    }

    /**
     * @param action
     * @param output
     * @param input
     * @return
     */
    @Override
    // am schimbat Input inputData --> Input input
    public boolean executeAction(final ActionsInput action,
                                 final ArrayNode output,
                                 final Input input) {
        if (!action.getFeature().equals("register")) {
            return false;
        }

        List<UserInput> users = input.getUsers();
        UserInput user = input.getUsers().stream()
                .filter(u -> u.getCredentials().getName().equals(action.getCredentials().getName()))
                .findFirst()
                .orElse(null);
        if (user != null) {
            ServerState.setCurrentUser(null);
            return false;
        }

        UserInput registeredUser = new UserInput();
        CredentialsInput credentials = new CredentialsInput();
        credentials.setName(action.getCredentials().getName());
        credentials.setPassword(action.getCredentials().getPassword());
        credentials.setAccountType(action.getCredentials().getAccountType());
        credentials.setCountry(action.getCredentials().getCountry());
        credentials.setBalance(action.getCredentials().getBalance());
        registeredUser.setCredentials(credentials);
        users.add(registeredUser);
        ServerState.setCurrentUser(registeredUser);

        // todo remove duplication
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();
        outputNode.putNull("error");
        outputNode.set("currentMoviesList", objectMapper.createArrayNode());
        outputNode.set("currentUser", Mapper.mapUser(registeredUser));
        output.add(outputNode);
        return true;
    }

    /***
     *
     * @param page
     * @param inputData
     * @param actionsInput
     * @return
     */
    @Override
    public boolean changePage(final String page,
                              final Input inputData,
                              final ArrayNode output,
                              final ActionsInput actionsInput) {
        // any page change is accepted
        return true;
    }
}
