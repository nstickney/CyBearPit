package is.stma.judgebean.controller;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Provides necessary injects for all JFS-based controllers
 */
abstract class AController {

    /* Injects -------------------------------------------------------------- */
    /**
     * The FacesContext currently in use
     */
    @Inject
    FacesContext facesContext;

    /**
     * The application log
     */
    @Inject
    Logger log;
}
