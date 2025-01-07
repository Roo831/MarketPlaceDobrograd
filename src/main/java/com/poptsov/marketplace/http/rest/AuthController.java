package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.mapper.JwtAuthenticationDtoMapper;
import com.poptsov.marketplace.security.JwtService;
import com.poptsov.marketplace.service.AuthenticationService;
import com.poptsov.marketplace.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;


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
    private final JwtAuthenticationDtoMapper jwtAuthenticationDtoMapper;


    @GetMapping("/test")
    public ResponseEntity<OverheadMessageDto> test() {
        return ResponseEntity.ok(OverheadMessageDto.builder()
                .message("test")
                .build());
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        String returnUrl = "https://poptsov.roman.fvds.ru/auth/callback";
        String realm = "http://rubronameg.temp.swtest.ru";

        String openidUrl = STEAM_OPENID_URL + "?openid.ns=http://specs.openid.net/auth/2.0" +
                "&openid.mode=checkid_setup" +
                "&openid.return_to=" + returnUrl +
                "&openid.realm=" + realm +
                "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select";

        response.sendRedirect(openidUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<JwtAuthenticationDto> callback(@RequestParam Map<String, String> params) {
        if (!params.containsKey("openid.mode") || !params.get("openid.mode").equals("id_res")) {
            throw new IllegalArgumentException("Неверный ответ от Steam");
        }

        String steamId = params.get("openid.claimed_id").split("/")[5];
        String identity = params.get("openid.identity");

        if (!identity.startsWith("http://steamcommunity.com/openid/id/")) {
            throw new IllegalArgumentException("Неверный идентификатор пользователя");
        }

        // Получение имени пользователя из Steam API
        String playerSummaryUrl = STEAM_USER_SUMMARIES_URL + "?key=" + STEAM_API_KEY + "&steamids=" + steamId;
        PlayerSummaryResponse response = restTemplate.getForObject(playerSummaryUrl, PlayerSummaryResponse.class);

        if (response == null || response.getResponse().getPlayers().isEmpty()) {
            throw new IllegalArgumentException("Не удалось получить информацию о пользователе");
        }

        String username = response.getResponse().getPlayers().get(0).getPersonaname();

        User user = authenticationService.findOrCreateUserBySteamId(steamId, username);
        String jwt = jwtService.generateToken(user);

        return ResponseEntity.ok(jwtAuthenticationDtoMapper.map(user, jwt));
    }
}


