package com.poptsov.marketplace.http.rest;

import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.service.BannedService;
import com.poptsov.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController { // Дополнительные проверки авторизации не нужны, так как к контроллерам имеет доступ только администраторы

    private final BannedService bannedService;
    private final UserService userService;

    /**
     * Получить список пользователей.
     * @return List<UserReadDto>
     */

    @GetMapping("/users")
    public ResponseEntity<List<UserReadDto>> getUsers() { // TODO: Сортировка по алфавиту
        return ResponseEntity.ok( userService.getAllUsers());
    }

    /**
     * Получить пользователя по ид
     * @param id Идентификатор пользователя
     * @return UserReadDto
     */

    @GetMapping("/users/{id}")
    public ResponseEntity<UserReadDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok( userService.getUserById(id));
    }


    /**
     * Получить магазин пользователя
     * @param id Идентификатор пользователя
     * @return ShopReadDto
     */

    @GetMapping("/users/{id}/shop")
    public ResponseEntity<ShopReadDto> getShopByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok( userService.getShopByUserId(id));
    }

    /**
     * Получить список заказов пользователя
     * @param id Идентификатор пользователя
     * @return List<OrderReadDto>
     */

    @GetMapping("/users/{id}/orders")
    public ResponseEntity<List<OrderReadDto>> getOrdersByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok( userService.getOrdersByUserId(id));
    }

    /**
     * Переключить админа
     * @param id Идентификатор пользователя,
     * @param switchAdminDto true/false
     * @return UserReadDto
     */

    @PatchMapping("/users/{id}/makeAdmin")
    public ResponseEntity<UserReadDto> switchAdmin(@PathVariable Integer id, @Validated @RequestBody SwitchAdminDto switchAdminDto) {
        return ResponseEntity.ok(userService.updateUser(id, switchAdminDto));
    }

    /**
     * Забанить пользователя
     * @param id Идентификатор пользователя,
     * @param banCreateDto причина бана
     * @return BanReadDto
     */

    @PatchMapping("/users/{id}/ban")
    public ResponseEntity<BanReadDto> banUser(@PathVariable Integer id, @Validated @RequestBody BanCreateDto banCreateDto) {
        return ResponseEntity.ok(bannedService.createBanned(id, banCreateDto));
    }

    /**
     * Разбанить пользователя
     * @param id Идентификатор пользователя,
     * @return UserReadDto
     */

    @DeleteMapping("/users/{id}/unban")
    public ResponseEntity<UserReadDto> unbanUser(@PathVariable Integer id) {
        return ResponseEntity.ok(bannedService.deleteBanned(id));
    }

    /**
     * Получить список забаненных
     * @return List<BanReadDto>
     */

    @GetMapping("/users/banned")
    public ResponseEntity<List<BanReadDto>> listOfBanned(){
       return ResponseEntity.ok(bannedService.getAllBanned());
    }


    /**
     * Получить забаненного по идентификатору
     * @param id Идентификатор пользователя,
     * @return BanReadDto
     */
    @GetMapping("/users/banned/{id}")
    public ResponseEntity<BanReadDto> getBannedById(@PathVariable Integer id){
       return ResponseEntity.ok(bannedService.getBannedById(id));

    }

}
