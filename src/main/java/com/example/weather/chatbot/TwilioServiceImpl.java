package com.example.weather.chatbot;

import com.example.weather.chatbot.models.WeatherResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TwilioServiceImpl implements TwillioService {

  private final RestTemplate restTemplate;

  @Value("${twillio.accountSid}")
  private String accountSid;

  @Value("${twillio.authToken}")
  private String authToken;

  @Value("${twillio.bot.whatsapp.number}")
  private String botWhatsappNumber;

  @Value("${twillio.my.whatsapp.number}")
  private String myWhatsappNumber;

  @Value("${weather.api.token}")
  private String token;

  public TwilioServiceImpl(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void sendWeather(final MultiValueMap<String, String> requestBody) {
    final List<String> latitudeList = requestBody.get("Latitude");
    final List<String> longitudeList = requestBody.get("Longitude");
    if (Objects.nonNull(latitudeList) && Objects.nonNull(longitudeList)) {
      final String latitude = latitudeList.get(0);
      final String longitude = longitudeList.get(0);
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
      String formatDateTime = now.format(formatter);
      final String url =
          "https://api.meteomatics.com/"
              + formatDateTime
              + "/t_2m:C/"
              + longitude
              + ","
              + latitude
              + "/json?access_token="
              + token;
      final WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
      final String value =
          weatherResponse.getData().get(0).getCoordinates().get(0).getDates().get(0).getValue();
      Twilio.init(accountSid, authToken);
      Message.creator(
              new PhoneNumber("whatsapp:" + myWhatsappNumber),
              new PhoneNumber("whatsapp:" + botWhatsappNumber),
              "Hava Anlık Olarak " + value + " Derece!")
          .create();
    } else {
      Twilio.init(accountSid, authToken);
      Message.creator(
              new PhoneNumber("whatsapp:" + myWhatsappNumber),
              new PhoneNumber("whatsapp:" + botWhatsappNumber),
              "Sadece Konuma Göre Hava Durumu Söylebilirim Lütfen Konum Gönder!")
          .create();
    }
  }
}
