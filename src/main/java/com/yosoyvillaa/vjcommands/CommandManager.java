package com.yosoyvillaa.vjcommands;

import com.yosoyvillaa.vjcommands.command.ICommand;
import com.yosoyvillaa.vjcommands.command.PrefixCommand;
import com.yosoyvillaa.vjcommands.command.SlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandManager {

    private final JDA jda;
    private final Map<String, PrefixCommand> prefixCommands;
    private final Map<String, SlashCommand> slashCommands;

    public CommandManager(JDA jda) {
        this.jda = jda;
        this.jda.setEventManager(new AnnotatedEventManager());
        this.jda.addEventListener(this);
        this.prefixCommands = new HashMap<>();
        this.slashCommands = new HashMap<>();
    }

    public JDA getJda() {
        return jda;
    }

    public Set<ICommand> getCommands() {
        Set<ICommand> commands = new HashSet<>();
        commands.addAll(prefixCommands.values());
        commands.addAll(slashCommands.values());
        return commands;
    }

    public Set<PrefixCommand> getPrefixCommands() {
        return new HashSet<>(prefixCommands.values());
    }

    public Set<SlashCommand> getSlashCommands() {
        return new HashSet<>(slashCommands.values());
    }

    public void registerCommand(ICommand command) {
        if (command instanceof PrefixCommand prefixCommand) {
            if (prefixCommands.containsKey(prefixCommand.getPrefix() + prefixCommand.getName()))
                throw new IllegalArgumentException("A prefix command called `" + prefixCommand.getName() + "` is already registered");

            prefixCommands.put(prefixCommand.getPrefix() + prefixCommand.getName(), prefixCommand);
            return;
        }

        if (command instanceof SlashCommand slashCommand) {
            if (slashCommands.containsKey(slashCommand.getName()))
                throw new IllegalArgumentException("A slash command called `" + slashCommand.getName() + "` is already registered");

            slashCommands.put(slashCommand.getName(), slashCommand);
            jda.upsertCommand(slashCommand.getName(), slashCommand.getDescription()).queue();
        }
    }

    public void registerCommands(ICommand... commands) {
        for (ICommand command : commands) {
            registerCommand(command);
        }
    }

    public void unregisterCommand(ICommand command) {
        if (command instanceof PrefixCommand prefixCommand) {
            prefixCommands.remove(prefixCommand.getPrefix() + prefixCommand.getName());
        }

        if (command instanceof SlashCommand slashCommand) {
            slashCommands.remove(slashCommand.getName());
            System.out.println("Slash commands will be already being shown on the client, you will need to restart the bot with the command disabled");
        }
    }

    public void unregisterAllCommands() {
        for (PrefixCommand prefixCommand : prefixCommands.values()) {
            unregisterCommand(prefixCommand);
        }

        for (SlashCommand slashCommand : slashCommands.values()) {
            unregisterCommand(slashCommand);
        }
    }

    @SubscribeEvent
    public void onPrefixCommand(MessageReceivedEvent event) {
        for (String s : prefixCommands.keySet()) {
            if (s.equalsIgnoreCase(event.getMessage().getContentRaw().split(" ")[0]))
                prefixCommands.get(s).execute(event);
        }
    }

    @SubscribeEvent
    public void onSlashCommand(SlashCommandInteractionEvent event) {
        for (String s : slashCommands.keySet()) {
            if (s.equalsIgnoreCase(event.getName())) {
                slashCommands.get(s).execute(event);
            }
        }
    }
}
