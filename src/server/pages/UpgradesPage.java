package server.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.CredentialsInput;
import fileio.Input;
import fileio.UserInput;
import server.ServerState;
import server.util.Constants;

public class UpgradesPage implements IPage {
    /**
     *
     * @param action
     * @param output
     * @param inputData
     * @return
     */
    @Override
    public boolean executeAction(final ActionsInput action,
                                 final ArrayNode output,
                                 final Input inputData) {
        UserInput user = ServerState.getCurrentUser();
        CredentialsInput credentials = user.getCredentials();

        if (action.getFeature().equals("buy tokens")) {
            if (credentials.getBalance() < action.getCount()) {
                return false;
            }

            credentials.setBalance(credentials.getBalance() - action.getCount());
            user.setTokensCount(user.getTokensCount() + action.getCount());
            return true;
        } else if (action.getFeature().equals("buy premium account")) {
            if (user.getTokensCount() < Constants.PREMIUM_COST) {
                return false;
            }

            user.setTokensCount(user.getTokensCount() - Constants.PREMIUM_COST);
            credentials.setAccountType(Constants.PREMIUM_ACCOUNT);
            return true;
        }

        return false;
    }

    /**
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
        return true;
    }
}
