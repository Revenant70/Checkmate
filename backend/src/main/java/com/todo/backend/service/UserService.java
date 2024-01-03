package com.todo.backend.service;

import com.todo.backend.Exceptions.TokenExpiredException;
import com.todo.backend.repository.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.Serializable;
import java.util.Date;

@Service
public class UserService implements Serializable {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String gmail;

    public UserDetails userSignup(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Role.USER);
        userRepository.save(userEntity);
        return userEntity;
    }

    public UserEntity userLogin(UserEntity userEntity) {
        return userRepository.findByUsername(userEntity.getUsername());
    }

    public void editUserProfile(UserEntity updatedUser, Authentication authentication) {
        try {
            UserEntity dbUserEntity = userRepository.findByUsername(authentication.getName());
            if (dbUserEntity != null) {
                if (!updatedUser.getFirstname().isEmpty()) {
                    dbUserEntity.setFirstname(updatedUser.getFirstname());
                }
                if (!updatedUser.getLastname().isEmpty()) {
                    dbUserEntity.setLastname(updatedUser.getLastname());
                }
                if (!updatedUser.getUsername().isEmpty()) {
                    dbUserEntity.setUsername(updatedUser.getUsername());
                }
                if (!updatedUser.getPassword().isEmpty()) {
                    dbUserEntity.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }
                userRepository.save(dbUserEntity);
            }
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void deleteUserProfile(Authentication authentication) {
        try {
            UserEntity userEntity = userRepository.findByUsername(authentication.getName());
            userRepository.deleteById(userEntity.getUserid());
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }

    public void forgotPassword(String email) {
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            Date date = ResetTokenService.calculateExpiryDate();
            ResetToken token = ResetTokenService.generateToken(userEntity, date);

            String url = "http://localhost:5173/auth/passwordrecovery/changepassword/" +
                     userEntity.getUserid() + "/" + token.getToken();
            resetTokenRepository.save(token);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(gmail);
            message.setTo(email);
            message.setSubject("reset password");
            message.setText("Click this link to reset your password: " + url);
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    @Transactional
    public void authenticateToken(ResetToken resetToken) {
        try {
            ResetToken dbResetToken = resetTokenRepository.findByToken(resetToken.getToken());
            Date currentDate = new Date();
            if(currentDate.before(dbResetToken.getExpiryDate())) {
                resetTokenRepository.deleteByToken(dbResetToken.getToken());
            } else {
                throw new TokenExpiredException("Password reset token has expired");
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void resetPassword(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);;
    }

}
