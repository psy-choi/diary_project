package com.example.Diary.Exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLIntegrityConstraintViolationException;


@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("alert");
        mv.addObject("msg", "존재하는 데이터가 없습니다. 생성 버튼을 눌러주세요.");

        return mv;
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ModelAndView handleSQLIntegrityConstraintViolationException() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("alert");
        mv.addObject("msg", "해당하는 유저의 데이터가 없습니다.");

        return mv;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("alert");
        mv.addObject("msg", "삭제할 수 있는 데이터가 없습니다.");

        return mv;
    }

    @ExceptionHandler(PasswordException.class)
    public ModelAndView handlePasswordException() {
        String url = String.format("/diary/home");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("alert");
        mv.addObject("msg", "비밀번호가 일치하지 않습니다.");
        mv.addObject("url", url);

        return mv;
    }

}
