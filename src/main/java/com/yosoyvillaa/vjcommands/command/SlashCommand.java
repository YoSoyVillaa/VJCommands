package com.yosoyvillaa.vjcommands.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

/**
 * A {@link SlashCommand} is represented by a slash ('<b><i>/</i></b>').
 * They can have displayed subcommands and more options.
 */
public interface SlashCommand extends ICommand {

    /**
     * Gets the command description.
     *
     * @return the command description
     */
    String getDescription();

    /**
     * This code block will be executed on the command execution.
     *
     * @param event Event given by the Discord API
     */
    void execute(SlashCommandInteractionEvent event);


    /**
     * Gets the command {@link CommandData}.
     * This can contain subcommands, options, and more things.
     *
     * @return the given {@link CommandData} (empty by default)
     */
    default CommandData getCommandData() {
        return new CommandDataImpl(getName(), getDescription());
    }
}
