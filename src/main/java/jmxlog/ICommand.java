package jmxlog;

/**
 * Расшифровывает команду, переданную в командной строке.
 */
interface ICommand {
    /**
     * Применим ли данный набор параметров для этой команды?
     */
    boolean isAppropriateParameters(String[] args);

    /**
     * Выполнить команду.
     */
    void execute(String[] args) throws Exception;
}