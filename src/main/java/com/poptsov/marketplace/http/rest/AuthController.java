package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.mapper.JwtAuthenticationDtoMapper;
import com.poptsov.marketplace.security.JwtService;
import com.poptsov.marketplace.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String STEAM_OPENID_URL = "https://steamcommunity.com/openid/login";
    private static final String STEAM_USER_SUMMARIES_URL = "https://api.steampowered.com/ISteamUser /GetPlayerSummaries/v0002/";
    private final RestTemplate restTemplate;
    @Value("${steam.api-key}")
    private static final String STEAM_API_KEY = "";

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;


    @GetMapping("/test")
    public ResponseEntity<OverheadMessageDto> test() {
        return ResponseEntity.ok(OverheadMessageDto.builder()
                .message("test")
                .build());
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        log.info("login start...");
        String returnUrl = "https://poptsov.roman.fvds.ru/auth/callback";
        String realm = "http://rubronameg.temp.swtest.ru"; // фронтенд

        log.info("Try to concat openidUrl...");

        String openidUrl = STEAM_OPENID_URL + "?openid.ns=http://specs.openid.net/auth/2.0" +
                "&openid.mode=checkid_setup" +
                "&openid.return_to=" + returnUrl +
                "&openid.realm=" + realm +
                "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select";

        log.info("concat openidUrl: {}", openidUrl);
        response.sendRedirect(openidUrl); // редирект на стим, который затем редиректит на callback, который
    }

    @GetMapping("/callback")
    public void callback(@RequestParam Map<String, String> params, HttpServletResponse response) throws IOException {
        log.info("Callback params: {}", params);
        if (!params.containsKey("openid.mode") || !params.get("openid.mode").equals("id_res")) {
            log.error("Неверный ответ от Steam");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ответ от Steam");
            return;
        }

        String steamId = params.get("openid.claimed_id").split("/")[5];
        String identity = params.get("openid.identity");

        if (!identity.startsWith("http://steamcommunity.com/openid/id/")) {
            log.error("Неверный идентификатор пользователя");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный идентификатор пользователя");
            return;
        }

        // Получение имени пользователя из Steam API
        String playerSummaryUrl = STEAM_USER_SUMMARIES_URL + "?key=" + STEAM_API_KEY + "&steamids=" + steamId;
        PlayerSummaryResponse playerResponse = restTemplate.getForObject(playerSummaryUrl, PlayerSummaryResponse.class);

        if (playerResponse == null || playerResponse.getResponse().getPlayers().isEmpty()) {
            log.error("Не удалось получить информацию о пользователе");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не удалось получить информацию о пользователе");
            return;
        }

        String username = playerResponse.getResponse().getPlayers().get(0).getPersonaname();
        User user = authenticationService.findOrCreateUserBySteamId(steamId, username);
        String jwt = jwtService.generateToken(user);

        // Возвращаем HTML с JavaScript для отправки данных на фронтенд
        response.setContentType("text/html");
        response.getWriter().write(
                "<html><body>" +
                        "<script>" +
                        "window.opener.postMessage({ token: '" + jwt + "' }, 'http://rubronameg.temp.swtest.ru');" +
                        "window.close();" +
                        "</script>" +
                        "</body></html>"
        );
    }
}


