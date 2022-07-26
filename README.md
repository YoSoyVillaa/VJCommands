# VJCommands

A command framework compatible with [JDA](https://github.com/DV8FromTheWorld/JDA)

## Usage
You can find the Javadocs [here](https://yosoyvillaa.github.io/VJCommands/apidocs/)

Is really easy to use it, the only thing you need is implements the `PrefixCommand` class or the `SlashCommand` class, and register it with the `CommandMamager` using `CommandManager#registerCommand()`

**Example**:

```java
    public static void main(String[] args) throws LoginException {
        // Creates an instance of a bot             
        JDA jda = JDABuilder.createDefault(args[0]).build();
                
        CommandManager commandManager = new CommandManager(jda);
        commandManager.registerCommands(new PingCommand());
    }
```

**PingCommand**: _(As Slash(/) command)_

```java
public class PingCommand implements SlashCommand {

    @Override
    public String getDescription() {
        return "Shows server ping";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        event.reply("Ping: " slashCommandInteractionEvent.getJDA().getGatewayPing() + "ms").queue();
    }

    @Override
    public String getName() {
        return "ping";
    }
}
```

**PingCommand**: _(As Prefix(!) command)_

```java
public class PingCommand implements PrefixCommand {

    @Override
    public String getPrefix() {
        return "!";
    }

    @Override
    public void execute(MessageReceivedEvent messageReceivedEvent) {
        messageReceivedEvent.getMessage().reply("Ping: " messageReceivedEvent.getJDA().getGatewayPing() + "ms").queue();
    }

    @Override
    public String getName() {
        return "ping";
    }
}
```
