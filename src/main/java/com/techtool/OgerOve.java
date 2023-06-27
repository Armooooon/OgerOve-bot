package com.techtool;

package com.techtool;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.*;

public class OgerOve extends ListenerAdapter {

    private final ShardManager shardManager;
    private final String[] responses = {
            "Ja, auf jeden Fall!",
            "Vielleicht...",
            "Ich weiß es nicht.",
            "Frag mich später nochmal.",
            "Nein, auf keinen Fall!"
    };

    private boolean kekswichsenActive;
    private Timer timer;
    private List<User> participants;
    private Map<User, Integer> jerkCounters;

    public OgerOve() {
        String token = "MTEyMTY5MzQ5MDU2NTM0OTM3Nw.Gj9dek.J-FYT7E9jEl_uaOlbARGrekoIjMBrcVswLw54Q";
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("BoarHunt Oger Edition"));

        // Enable necessary intents
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGE_REACTIONS);

        shardManager = builder.build();

        kekswichsenActive = false;
        participants = new ArrayList<>();
        jerkCounters = new HashMap<>();
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        JDABuilder builder = JDABuilder.createDefault("MTEyMTY5MzQ5MDU2NTM0OTM3Nw.Gj9dek.J-FYT7E9jEl_uaOlbARGrekoIjMBrcVswLw54Q");
        builder.addEventListeners(new OgerOve());

        // Enable necessary intents
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGE_REACTIONS);

        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        Member member = event.getMember();

        if (event.getAuthor().isBot() || member == null) return;

        if (message.equalsIgnoreCase("!Oger")) {
            event.getChannel().sendMessage("Oger HUNGRIG!!!!!! 😡").queue();
        } else if (message.equalsIgnoreCase("!commands")) {
            event.getChannel().sendMessage("Verfügbare Befehle:\n" +
                    "!Oger - Wütender Oger\n" +
                    "!commands - Zeigt Befehle\n" +
                    "!michi - postet bild von MICHI!!!!!\n" + "!?\n" + "!gamble\n" + "!Schuledodgen").queue();
        } else if (message.contains(":Oger_Mikeremovebgpreview:")) {
            event.getChannel().sendMessage("aughhhhh").queue();
        } else if (event.getMessage().getStickers().stream().anyMatch(sticker -> sticker.getName().equalsIgnoreCase("WismarerOger"))) {
            event.getChannel().sendMessage("Wildschwein Jagen!!!!!!!!!!!!!").queue();
        } else if (message.equalsIgnoreCase("!michi")) {
            event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/1015302578919190548/1121705937351217182/UgaUgaMichi.jpg").queue();
        } else if (message.equalsIgnoreCase("!cancerdog")) {
            event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/526697763447111680/1121791547898351778/unknown.jpg https://cdn.discordapp.com/attachments/526697763447111680/1121791547390828654/Leon_Pog.png").queue();
        } else if (event.getMessage().getStickers().stream().anyMatch(sticker -> sticker.getName().equalsIgnoreCase("OgerOve"))) {
            event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/1015302578919190548/1121318532559409253/IMG_4628.jpg").queue();
        } else if (message.equalsIgnoreCase("!hebel")) {
            event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/526697763447111680/1121791710436003970/Screenshot_20230218_230908.png").queue();
        } else if (message.equalsIgnoreCase("!?")) {
            event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/738160562352947331/1111762523901935706/china.mp4").queue();
        } else if (message.equalsIgnoreCase("!Jagen")) {
            int randomNumber = (int) (Math.random() * 100); // Generate a random number between 0 and 100
            event.getChannel().sendMessage("Du hast " + randomNumber + " Wildschweine gefangen").queue();
        } else if (message.equalsIgnoreCase("!Schuledodgen")) {
            // Generate a random response
            Random random = new Random();
            boolean shouldDodge = random.nextBoolean();

            // Send the response
            if (shouldDodge) {
                event.getChannel().sendMessage("Bleib zuhause du schwuchtel").queue();
            } else {
                event.getChannel().sendMessage("Du kommst morgen du wixxer").queue();
            }
        } else if (message.equalsIgnoreCase("!holyogre")) {
            // Generate a random response
            Random random = new Random();
            String response = responses[random.nextInt(responses.length)];

            // Send the response
            event.getChannel().sendMessage(response).queue();
        } else if (message.equalsIgnoreCase("!Keks")) {
            startKekswichsen(event);
        } else if (message.equalsIgnoreCase("!Teilnehmen")) {
            addParticipant(member.getUser(), event);
        } else if (message.equalsIgnoreCase("!jerk")) {
            removeJerkCounter(member.getUser(), event);
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;
        String targetEmote = "Oger_Mikeremovebgpreview";
        if (((Activity) event.getReaction()).getName().equalsIgnoreCase(targetEmote)) {
            event.getChannel().sendMessage("AUGHHHHHHHHH AMBATAKAM").queue();
        }
    }

    private void startKekswichsen(MessageReceivedEvent event) {
        if (kekswichsenActive) {
            event.getChannel().sendMessage("Es läuft bereits ein Spiel!").queue();
            return;
        }

        kekswichsenActive = true;
        participants.clear();
        jerkCounters.clear();

        event.getChannel().sendMessage("Das Kekswichsen-Spiel hat begonnen! Schreibe `!Teilnehmen`, um teilzunehmen.").queue();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (participants.isEmpty()) {
                    event.getChannel().sendMessage("Es haben keine Teilnehmer am Spiel teilgenommen. Das Spiel wird beendet.").queue();
                } else {
                    User loser = determineLoser();
                    event.getChannel().sendMessage("Das Spiel ist vorbei! " + loser.getAsMention() + " hat verloren und einen Wichskeks gegessen!").queue();
                    changeNickname(loser, "Ich habe einen Wichskeks gegessen", event);
                }

                kekswichsenActive = false;
                timer.cancel();
                timer = null;
            }
        }, 30000);
    }

    private void addParticipant(User user, MessageReceivedEvent event) {
        if (!kekswichsenActive) {
            event.getChannel().sendMessage("Es läuft kein Spiel, dem du beitreten kannst.").queue();
            return;
        }

        if (participants.contains(user)) {
            event.getChannel().sendMessage("Du nimmst bereits am Spiel teil!").queue();
            return;
        }

        participants.add(user);
        jerkCounters.put(user, 10);

        event.getChannel().sendMessage(user.getAsMention() + " nimmt am Spiel teil und hat 10 Jerk Counter.").queue();
    }

    private void removeJerkCounter(User user, MessageReceivedEvent event) {
        if (!kekswichsenActive) {
            event.getChannel().sendMessage("Es läuft kein Spiel, an dem du teilnehmen kannst.").queue();
            return;
        }

        if (!participants.contains(user)) {
            event.getChannel().sendMessage("Du nimmst nicht am Spiel teil!").queue();
            return;
        }

        int jerkCounter = jerkCounters.getOrDefault(user, 0);
        if (jerkCounter == 0) {
            event.getChannel().sendMessage(user.getAsMention() + ", du hast keine Jerk Counter mehr übrig!").queue();
            return;
        }

        jerkCounters.put(user, jerkCounter - 1);
        event.getChannel().sendMessage(user.getAsMention() + " hat einen Jerk Counter entfernt. Jerk Counter übrig: " + (jerkCounter - 1)).queue();
    }

    private User determineLoser() {
        User loser = null;
        for (User user : participants) {
            int jerkCounter = jerkCounters.getOrDefault(user, 0);
            if (jerkCounter > 0) {
                loser = user;
                break;
            }
        }
        return loser;
    }

    private void changeNickname(User user, String nickname, MessageReceivedEvent event) {
        if (user.getId().equals(event.getGuild().getSelfMember().getId())) {
            event.getGuild().modifyNickname(event.getGuild().getSelfMember(), nickname).queue();
        } else {
            event.getGuild().modifyNickname((Member) user, nickname).queue();
        }
    }
}
