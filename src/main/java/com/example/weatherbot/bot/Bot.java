package com.example.weatherbot.bot;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

@Component
@Log4j2
public final class Bot extends TelegramLongPollingCommandBot {

    private static final String BOT_NAME = "weatherbot_denis_bot";
    private static final String BOT_TOKEN = "5829394921:AAGWQptNiunm68_Kp2C534O3xWfN-28fMeY";

    @PostConstruct
    private void init() {
        register(new StartCommand("start", "start"));
    }
    /*private void button() {register(new WetherCommand("checkWeather", "checkWeather"));}не понимаю как кнопку сделать новую*/

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }


    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        if (update.getMessage().hasPhoto()) {
            List<PhotoSize> photos = message.getPhoto();
            photos.forEach(photo -> {
                log.info("Photo {}", photos.size());

                GetFile getFile = new GetFile();
                getFile.setFileId(photo.getFileId());
                String filePath = null;
                try {
                    filePath = execute(getFile).getFilePath();

                    log.info("path: {}", filePath);
                    File file = downloadFile(filePath);
                    file.exists();

                    log.info("path: {}", file.getAbsolutePath());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            });
        }
        SendMessage answer = new SendMessage();
        answer.setText("I do not know it yet: " + message.getText());
        answer.setChatId(message.getChatId());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error during non command update execution", e);
        }
    }

}
