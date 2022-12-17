package server.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.Input;

public class UnauthHomePage implements IPage {
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
        return page.equals("login") || page.equals("register");
    }
}
