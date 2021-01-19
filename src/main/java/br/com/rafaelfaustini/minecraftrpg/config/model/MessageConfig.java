package br.com.rafaelfaustini.minecraftrpg.config.model;

import br.com.rafaelfaustini.minecraftrpg.config.CustomConfig;

public class MessageConfig {
    private String welcome;
    private String loadingException;
    private String commandException;
    private String eventException;

    public MessageConfig(CustomConfig customMessages) {
        welcome = customMessages.get("Events.welcome", String.class);
        loadingException = customMessages.get("Utils.loadingException", String.class);
        commandException = customMessages.get("Utils.commandException", String.class);
        eventException = customMessages.get("Utils.eventException", String.class);
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
