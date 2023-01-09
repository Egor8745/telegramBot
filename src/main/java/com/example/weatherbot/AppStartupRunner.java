package com.example.weatherbot;

import com.example.weatherbot.bot.Bot;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private final Bot bot;

    public AppStartupRunner(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
    }
}
