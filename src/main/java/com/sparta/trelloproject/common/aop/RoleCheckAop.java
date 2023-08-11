package com.sparta.trelloproject.common.aop;

import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import com.sparta.trelloproject.user.entity.User;
import com.sparta.trelloproject.user.entity.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

@Slf4j(topic = "RoleCheckAop")
@Aspect
@Component
public class RoleCheckAop {

    @Pointcut("execution(* com.sparta.trelloproject.card.service.CardService.updateCard(..))")
    private void updateCard(){}

    @Pointcut("execution(* com.sparta.trelloproject.card.service.CardService.deleteCard(..))")
    private void deleteCard(){}

    @Around("updateCard() || deleteCard()")
    public Object executeCardRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable{
        CardEntity card = (CardEntity)joinPoint.getArgs()[0];

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class){
            UserDetailsImpl userDetails = (UserDetailsImpl)auth.getPrincipal();
            User loginUser = userDetails.getUser();

            if(!(loginUser.getRole().equals(UserRoleEnum.ADMIN) || card.getUser().equals(loginUser))){
                log.warn("[AOP] 작성자만 card를 수정/삭제 할 수 있습니다.");
                throw new RejectedExecutionException();
            }
        }

        return joinPoint.proceed();
    }

}
