package com.yosoyvillaa.vjcommands;

import com.yosoyvillaa.vjcommands.command.ICommand;
import com.yosoyvillaa.vjcommands.command.PrefixCommand;
import com.yosoyvillaa.vjcommands.command.SlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The {@link CommandManager} is the place where you can manage all you commands.
 */
public class CommandManager extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger("VJCommands");
    private final JDA jda;
    private final Map<String, PrefixCommand> prefixCommands;
    private final Map<String, SlashCommand> slashCommands;

    public CommandManager(JDA jda, IEventManager eventManager) {
        this.jda = jda;
        if (eventManager instanceof AnnotatedEventManager) {
            this.jda.addEventListener(this);
        } else if (eventManager instanceof InterfacedEventManager) {
            this.jda.addEventListener(this);
        } else {
            throw new RuntimeException("EventListener is not defined properly as AnnotatedEventManager or InterfacedEventManager");
        }
        this.prefixCommands = new HashMap<>();
        this.slashCommands = new HashMap<>();
    }

    /**
     * Gets the {@link JDA} instance associated to this {@link CommandManager}.
     *
     * @return The {@link JDA} instance
     */
    public JDA getJda() {
        return jda;
    }

    /**
     * Get all the registered commands.
     *
     * @return a set of all the {@link ICommand} registered
     */
    public Set<ICommand> getCommands() {
        Set<ICommand> commands = new HashSet<>();
        commands.addAll(prefixCommands.values());
        commands.addAll(slashCommands.values());
        return commands;
    }

    /**
     * Get all the registered {@link PrefixCommand}.
     *
     * @return a set of all the {@link PrefixCommand} registered
     */
    public Set<PrefixCommand> getPrefixCommands() {
        return new HashSet<>(prefixCommands.values());
    }

    /**
     * Get all the registered {@link SlashCommand}.
     *
     * @return a set of all the {@link SlashCommand} registered
     */
    public Set<SlashCommand> getSlashCommands() {
        return new HashSet<>(slashCommands.values());
    }

    /**
     * Try to register an {@link ICommand}.
     *
     * @param command the {@link ICommand} to register
     *
     * @throws IllegalArgumentException if the command is already registered
     */
    public void registerCommand(ICommand command) throws IllegalArgumentException {
        logger.info("Registering command: " + command.getName());
        if (command instanceof PrefixCommand) {
            PrefixCommand prefixCommand = (PrefixCommand) command;
            if (prefixCommands.containsKey(prefixCommand.getPrefix() + prefixCommand.getName()))
                throw new IllegalArgumentException("A prefix command called `" + prefixCommand.getName() + "` is already registered");

            prefixCommands.put(prefixCommand.getPrefix() + prefixCommand.getName(), prefixCommand);
        }

        if (command instanceof SlashCommand) {
            SlashCommand slashCommand = (SlashCommand) command;
            if (slashCommands.containsKey(slashCommand.getName()))
                throw new IllegalArgumentException("A slash command called `" + slashCommand.getName() + "` is already registered");

            slashCommands.put(slashCommand.getName(), slashCommand);
            jda.upsertCommand(slashCommand.getCommandData()).queue();
        }
        logger.info("Command " + command.getName() + " registered");
    }

    /**
     * Try to register a list of {@link ICommand}.
     *
     * @param commands the {@link ICommand} list to register
     *
     * @throws IllegalArgumentException if a command is already registered
     */
    public void registerCommands(ICommand... commands) throws IllegalArgumentException {
        for (ICommand command : commands) {
            registerCommand(command);
        }
    }

    /**
     * Unregister the given {@link ICommand}.
     *
     * @param command the {@link ICommand} to unregister
     */
    public void unregisterCommand(ICommand command) {
        logger.info("Unregistering command: " + command.getName());
        if (command instanceof PrefixCommand) {
            PrefixCommand prefixCommand = (PrefixCommand) command;
            prefixCommands.remove(prefixCommand.getPrefix() + prefixCommand.getName());
        }

        if (command instanceof SlashCommand) {
            SlashCommand slashCommand = (SlashCommand) command;
            slashCommands.remove(slashCommand.getName());
            logger.warn("Slash commands will be already being shown on the client, you will need to restart the bot with the command disabled");
        }
        logger.info("Command " + command.getName() + " unregistered");
    }

    /**
     * Unregister all registered commands.
     */
    public void unregisterAllCommands() {
        for (PrefixCommand prefixCommand : prefixCommands.values()) {
            unregisterCommand(prefixCommand);
        }

        for (SlashCommand slashCommand : slashCommands.values()) {
            unregisterCommand(slashCommand);
        }
    }

    @SubscribeEvent
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        for (String s : prefixCommands.keySet()) {
            if (s.equalsIgnoreCase(event.getMessage().getContentRaw().split(" ")[0]))
                prefixCommands.get(s).execute(event);
        }
    }

    @SubscribeEvent
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (String s : slashCommands.keySet()) {
            if (s.equalsIgnoreCase(event.getName())) {
                slashCommands.get(s).execute(event);
            }
        }
    }
}
