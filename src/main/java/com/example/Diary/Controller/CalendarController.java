package com.example.Diary.Controller;


import com.example.Diary.Data.dto.memberDTO;
import com.example.Diary.Data.dto.memberresponsDTO;
import com.example.Diary.DiaryData.Repository.dto.DiaryDTO;
import com.example.Diary.Exception.CustomExceptionHandler;
import com.example.Diary.Exception.PasswordException;
import com.example.Diary.Security.SecurityService;
import com.example.Diary.service.Diaryservice;
import com.example.Diary.service.memberservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/diary")
public class CalendarController {

    private final CustomExceptionHandler customExceptionHandler;
    private final memberservice Memberservice;
    private final Diaryservice diaryservice;
    private final SecurityService securityService;


    @Autowired
    public CalendarController(memberservice Memberservice, Diaryservice diaryservice, CustomExceptionHandler custom
    , SecurityService securityService) {
        this.Memberservice = Memberservice;
        this.diaryservice = diaryservice;
        this.customExceptionHandler = custom;
        this.securityService = securityService;
    }

    @GetMapping("/home")
    public String Calendar(Model model, @CookieValue("User") String User) {
        String user = securityService.getSubject(User);
        model.addAttribute("ID_number", user);
        return "calendar";
    }


    @GetMapping("/page")
    public String new_page(Model model, @RequestParam String date,
                           @CookieValue(value = "User") String User) {
        // 해당 html 파일에다가 User과 Date이 값을 넣고 일기의 내용을 가져옴
        Long user = Long.parseLong(securityService.getSubject(User));
        model.addAttribute("User", user);
        model.addAttribute("date", date);
        return "page";
    }

    @GetMapping("/page/read")
    public String page_model(Model model, @CookieValue("User") String User, @RequestParam String date) {
        // 데이터에서 ID와 date에 해당하는 값을 가져옴
        StringBuffer sb = new StringBuffer(date);
        sb = sb.replace(34,42, "(한국 표준시)");
        String user = securityService.getSubject(User);

        DiaryDTO get_Diary = diaryservice.getDiary(user, sb.toString());


        model.addAttribute("User", user);
        model.addAttribute("date", sb.toString());
        model.addAttribute("Diary", get_Diary.getDiary());
        return "diary/read";
    }

    @GetMapping("/page/write")
    public String page_write(Model model, @CookieValue("User") String User, @RequestParam String date) {
        String user = securityService.getSubject(User);
        model.addAttribute("User", user);
        model.addAttribute("date", date);
        return "diary/write";
    }

    @GetMapping("/page/changed")
    public String page_changed(Model model, @CookieValue("User") String User, @RequestParam String date) {
        String user = securityService.getSubject(User);

        model.addAttribute("User", user);
        model.addAttribute("date", date);
        DiaryDTO get_Diary = diaryservice.getDiary(user, date);
        model.addAttribute("Diary", get_Diary.getDiary());
        return "diary/change";
    }


    @PostMapping("/page/write_method")
    public String write_something(@CookieValue("User") String User, @RequestParam String date, @RequestParam String Diary) { //관련된 model 옮기기
        // DB에 일기의 내용을 보냄
        String user = securityService.getSubject(User);

        DiaryDTO get_Diary = new DiaryDTO(user, date, Diary);
        diaryservice.saveDiary(get_Diary);

        String url = "redirect:/diary/page/read?date="+date;

        return url;
    }

    @PostMapping("/page/change_method")
    public String update_something(@CookieValue("User") String User, @RequestParam String date, @RequestParam String Diary) {
        String user = securityService.getSubject(User);

        // DB에 수정된 내용을 보냄
        diaryservice.changedDiary(user, date, Diary);
        String url = "redirect:/diary/page/read?date="+date;

        return url;
    }

    @DeleteMapping("/page/delete")
    public String delete_somthing(@CookieValue("User") String User, @RequestParam String date) {
        // DB에 일기 내용을 지운다.
        String user = securityService.getSubject(User);

        diaryservice.deleteDiary(user, date);

        String url = String.format("redirect:/diary/home");

        return url;
    }


    @GetMapping("/")
    public String login() {
        // 이때 로그인과 관련된 내용이 나타남
        return "login_page";
    }


    @PostMapping("/login/get")
    public String login_get(Model model, @RequestParam String ID, @RequestParam String password,
                            HttpServletResponse response) throws PasswordException {
        memberresponsDTO profile = Memberservice.getMember(ID);

        if (!profile.getPassword().equals(password)) {
            throw new PasswordException();
        }

        String token = securityService.createToken(profile.getNumber().toString(), (10*1000*60));

        // 쿠기 방식으로 설정할 수 있음
        Cookie cookie = new Cookie("User", token);
        cookie.setMaxAge(60 * 60 * 2);
        cookie.setPath("/");
        response.addCookie(cookie);


        String url = String.format("redirect:/diary/home");
        // 데이터를 가져오고 회원 정보를 login_information에 나타나도록 한다.
        return url;
    }

    @GetMapping("/join")
    public String join() {
        // 이때 회원 가입과 관련된 내용이 나타남
        return "join";
    }


    @PostMapping("/join/post")
    public String join_post(Model model, @RequestParam String ID, @RequestParam String PASSWORD, @RequestParam String PW)
            throws PasswordException{
        if (!PASSWORD.equals(PW)) {
            throw new PasswordException();
        }

        memberDTO profile = new memberDTO(ID, PASSWORD);
        Memberservice.saveMember(profile);
        // 데이터에 보냄 : 회원 가입 완료
        return "redirect:/diary/";
    }

    @DeleteMapping("/join/delete")
    public String join_delete(Model model, @RequestParam Long number) {
        memberservice delete_profile = null;
        delete_profile.deleteMember(number);

        // 데이터에서 삭제함
        return "redirect:/diary/";
    }



}
