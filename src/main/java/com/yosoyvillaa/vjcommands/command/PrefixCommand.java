package com.yosoyvillaa.vjcommands.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * A {@link PrefixCommand} is represented by a prefix.
 * The default prefix is <b><i>!</i></b>.
 */
public interface PrefixCommand extends ICommand {

    /**
     * Gets the command prefix.
     *
     * @return the command prefix
     */
    default String getPrefix() {
        return "!";
    }

    /**
     * This code block will be executed on the command execution.
     *
     * @param event Event given by the Discord API
     */
    void execute(MessageReceivedEvent event);
}
