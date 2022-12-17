package server;

import fileio.UserInput;
import server.pages.IPage;

public final class ServerState {
    private static IPage sCurrentPage = null;
    private static UserInput sCurrentUser = null;

    public static IPage getCurrentPage() {
        return sCurrentPage;
    }

    public static void setCurrentPage(final IPage currentPage) {
        sCurrentPage = currentPage;
    }

    public static UserInput getCurrentUser() {
        return sCurrentUser;
    }

    public static void setCurrentUser(final UserInput currentUser) {
        sCurrentUser = currentUser;
    }

    private ServerState() { }
}
