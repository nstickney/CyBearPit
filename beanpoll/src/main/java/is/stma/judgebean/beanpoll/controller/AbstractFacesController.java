package is.stma.judgebean.beanpoll.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides necessary injects for all JFS-based controllers
 */
abstract class AbstractFacesController {

    /**
     * The FacesContext currently in use
     */
    @Inject
    FacesContext facesContext;

    /**
     * The application log
     */
    @Inject
    private
    Logger log;

    /**
     * Handle a Throwable by logging the error message and pushing it to the FacesContext
     *
     * @param t   the Throwable to handle
     * @param msg the detailed message
     */
    void errorOut(Throwable t, String msg) {

        // Default to general error message that registration failed.
        String errorMsg = "Action failed. See server log for more information";

        // Find a better error message if possible
        if (t != null) {

            // Start with the exception and recurse to find the root cause
            while (t != null) {
                // Get the message from the Throwable class instance
                errorMsg = t.getLocalizedMessage();
                t = t.getCause();
            }
        }

        // Log and send the message
        log.log(Level.WARNING, "CONTROLLER: " + msg);
        log.log(Level.WARNING, "CAUSE: " + errorMsg);
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, errorMsg));
    }
}
