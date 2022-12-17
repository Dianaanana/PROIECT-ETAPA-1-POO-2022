package server.pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.Input;

public interface IPage {
    /**\
     *
     * @param action
     * @param output
     * @param inputData
     * @return
     */
    boolean executeAction(ActionsInput action,
                          ArrayNode output,
                          Input inputData);

    /**
     *
     * @param page
     * @param inputData
     * @param action
     * @return
     */
    boolean changePage(String page,
                       Input inputData,
                       ArrayNode output,
                       ActionsInput action);
}
