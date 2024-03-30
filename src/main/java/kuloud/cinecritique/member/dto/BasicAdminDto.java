package kuloud.cinecritique.member.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class BasicAdminDto {
    @Value("${admin.account}")
    private String basicAccount;
    @Value("${admin.password}")
    private String basicPassword;


}
