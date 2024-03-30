package kuloud.cinecritique.member.service;

import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.member.dto.AdminPostDto;
import kuloud.cinecritique.member.dto.BasicAdminDto;
import kuloud.cinecritique.member.dto.LoginDto;
import kuloud.cinecritique.member.entity.Admin;
import kuloud.cinecritique.member.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void addBasicAdminAccount() {
        BasicAdminDto dto = new BasicAdminDto();
        adminRepository.save(new Admin(dto.getBasicAccount(), dto.getBasicPassword()));
    }

    public void signIn(LoginDto loginDto) {
        Admin admin = adminRepository.findByAccount(loginDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ADMIN));

        if (!passwordEncoder.matches(loginDto.getPassword(), admin.getPassword())) {
            throw new CustomException(ErrorCode.NOT_EXIST_ADMIN);
        }
    }

    @Transactional
    public void signUp(AdminPostDto dto) {
        checkAccountIsDuplicated(dto.getAccount());
        adminRepository.save(dto.toEntityWithEncoder(passwordEncoder));
    }

    public void checkAccountIsDuplicated(String account) {
        if (adminRepository.findByAccount(account).isPresent()) {
            throw new CustomException(ErrorCode.NOT_EXIST_ADMIN);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByAccount(username)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ADMIN));
    }

}
