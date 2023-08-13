//package com.sparta.trelloproject.common.aop;
//
//import com.sparta.trelloproject.card.entity.CardEntity;
//import com.sparta.trelloproject.comment.entity.CommentEntity;
//import com.sparta.trelloproject.common.security.UserDetailsImpl;
//import com.sparta.trelloproject.user.entity.User;
//import com.sparta.trelloproject.user.entity.UserRoleEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.RejectedExecutionException;
//
//@Slf4j(topic = "RoleCheckAop")
//@Aspect
//@Component
//public class RoleCheckAop {
//
//    @Pointcut("execution(* com.sparta.trelloproject.card.service.CardService.updateCard(..))")
//    private void updateCard(){}
//
//    @Pointcut("execution(* com.sparta.trelloproject.card.service.CardService.deleteCard(..))")
//    private void deleteCard(){}
//
//    @Pointcut("execution(* com.sparta.trelloproject.comment.service.CommentService.updateComment(..))")
//    private void updateComment() {}
//
//    @Pointcut("execution(* com.sparta.trelloproject.comment.service.CommentService.deleteComment(..))")
//    private void deleteComment() {}
//
//    @Around("updateCard() || deleteCard()")
//    public Object executeCardRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable{
//        CardEntity card = (CardEntity)joinPoint.getArgs()[0];
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class){
//            UserDetailsImpl userDetails = (UserDetailsImpl)auth.getPrincipal();
//            User loginUser = userDetails.getUser();
//
//            if(!(loginUser.getRole().equals(UserRoleEnum.ADMIN) || card.getUser().getId().equals(loginUser.getId()))){
//                log.warn("[AOP] 작성자만 card를 수정/삭제 할 수 있습니다.");
//                throw new RejectedExecutionException();
//            }
//        }
//
//        return joinPoint.proceed();
//    }
//
//    @Around("updateComment() || deleteComment()")
//    public Object executeCommentRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 첫번째 매개변수로 게시글 받아옴
//        CommentEntity comment = (CommentEntity)joinPoint.getArgs()[0];
//
//        // 로그인 회원이 없는 경우, 수행시간을 기록하지 않음
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class){
//            // 로그인 회원 정보
//            UserDetailsImpl userDetails = (UserDetailsImpl)auth.getPrincipal();
//            User loginUser = userDetails.getUser();
//
//            // 댓글 작성자(comment.user)와 요청자(user)가 같은지 또는 Admin 인지 체크 (아니면 예외 발생)
//            if(!(loginUser.getRole().equals(UserRoleEnum.ADMIN) || comment.getUser().getId().equals(loginUser.getId()))){
//                log.warn("[AOP] 작성자만 댓글을 수정/삭제 할 수 있습니다.");
//                throw new RejectedExecutionException();
//            }
//        }
//        // 핵심기능 수행
//        return joinPoint.proceed();
//    }
//}
