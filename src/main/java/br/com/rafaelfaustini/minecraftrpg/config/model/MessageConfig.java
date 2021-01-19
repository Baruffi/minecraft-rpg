package br.com.rafaelfaustini.minecraftrpg.config.model;

import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;

public class MessageConfig {
    private String welcome;
    private String loadingException;
    private String commandException;
    private String eventException;

    public MessageConfig(CustomConfig customMessages) {
        welcome = customMessages.getString("Events.welcome");
        loadingException = customMessages.getString("Utils.loadingException");
        commandException = customMessages.getString("Utils.commandException");
        eventException = customMessages.getString("Utils.eventException");
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public String getLoadingException() {
        return loadingException;
    }

    public void setLoadingException(String loadingException) {
        this.loadingException = loadingException;
    }

    public String getCommandException() {
        return commandException;
    }

    public void setCommandException(String commandException) {
        this.commandException = commandException;
    }

    public String getEventException() {
        return eventException;
    }

    public void setEventException(String eventException) {
        this.eventException = eventException;
    }
}
