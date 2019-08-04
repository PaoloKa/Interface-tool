package com.logging;

import com.rs.cache.loaders.ComponentDefinition;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

/**
 * Interface tool
 * paolo 04/08/2019
 * #Shnek6969
 */
public class LogFactory {


    public static Logger createLogger(String name){
        Logger logger =   Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        Formatter formatter = new LogFormatter();
        handler.setFormatter(formatter);
        logger.addHandler(handler);
        return logger;
    }
}
