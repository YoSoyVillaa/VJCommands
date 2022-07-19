package com.yosoyvillaa.vjcommands.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public interface SlashCommand extends ICommand {

    String getDescription();

    void execute(SlashCommandInteractionEvent event);

    default CommandData getCommandData() {
        return new CommandDataImpl(getName(), getDescription());
    }
}
