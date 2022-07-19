package com.yosoyvillaa.vjcommands.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand extends ICommand {

    String getDescription();

    void execute(SlashCommandInteractionEvent event);
}
