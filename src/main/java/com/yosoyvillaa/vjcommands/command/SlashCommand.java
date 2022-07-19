package com.yosoyvillaa.vjcommands.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface SlashCommand extends ICommand {

    String getDescription();

    CommandData getCommandData();

    void execute(SlashCommandInteractionEvent event);
}
