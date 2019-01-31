package Framework;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SEMRushLogger {

    private Logger logger;

    public SEMRushLogger() {
        logger = Logger.getLogger("SEMRush log");
        FileHandler fh;

        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler(System.getProperty("user.dir") + "log");
            logger.addHandler(fh);
            SEMRushFormatter formatter = new SEMRushFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addInfo(String info) {
        logger.info(info);
    }

    public void addStep(String step) {
        logger.fine(step);
    }

    public void addWarn(String warning) {
        logger.warning(warning);
    }

}
