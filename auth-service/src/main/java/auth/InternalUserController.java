package auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController { // внутрений контрллер для общеения с сервисами
    //todo метод который ищет юзера и проверяет есть ли он вобще по имейлу и возвращает его айдишник
}
