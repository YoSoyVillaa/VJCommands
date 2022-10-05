package com.yosoyvillaa.vjcommands.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface PrefixCommand extends ICommand {

    /**
    default String getPrefix() {
        return "!";
    }

    void execute(MessageReceivedEvent event);
}
