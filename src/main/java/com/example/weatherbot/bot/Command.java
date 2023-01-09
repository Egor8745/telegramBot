package com.example.weatherbot.bot;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
public abstract class Command extends BotCommand {
    public Command(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    protected void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        log.info("send message {} to user {}", text, userName);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Command {} cannot be send to user {}", commandName, userName, e);
        }
    }
}
