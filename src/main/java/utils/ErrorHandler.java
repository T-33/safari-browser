package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Служебный класс для централизованного ведения журнала ошибок.
 * Предоставляет методы для ведения журнала ошибок с подробными сообщениями и исключениями.
 *
 * <p> Пример использования: </p>
 * <pre>{@code
 * try {
 *     Некоторый код, который может вызвать исключение
 * } catch (Exception e) {
 *     ErrorHandler.logError("An error occurred while processing your request.", e);
 * }
 * }</pre>
 *
 * @see java.util.logging.Logger
 */
public class ErrorHandler {

    private static final Logger logger = Logger.getLogger(ErrorHandler.class.getName());

    /**
     * Регистрирует ошибку с подробным сообщением.
     *
     * @param message описание ошибки
     * @param e исключение, связанное с ошибкой
     */
    public static void logError(String message, Exception e) {
        if (e != null) {
            logger.log(Level.SEVERE, message, e);
        } else {
            logger.log(Level.SEVERE, message);
        }
    }
}
