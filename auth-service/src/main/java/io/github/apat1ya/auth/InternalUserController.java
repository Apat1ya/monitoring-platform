package io.github.apat1ya.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController { // внутрений контрллер для общеения с сервисами
    //todo получает имейл юзера по кафке, проверяет есть ли он, если да возвращает айди юзер отправителю по другому топику
}
