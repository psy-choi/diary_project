package com.example.Diary.Controller;


import com.example.Diary.DiaryData.dto.DiaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.Diary.service.Diaryservice;
import com.example.Diary.service.memberservice;
import com.example.Diary.Data.dto.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/diary")
public class CalendarController {

    private final memberservice Memberservice;
    private final Diaryservice diaryservice;


    @Autowired
    public CalendarController(memberservice Memberservice, Diaryservice diaryservice){
        this.Memberservice = Memberservice;
        this.diaryservice = diaryservice;
    }

    @GetMapping("/home")
    public String Calendar(Model model, @RequestParam String number){

        model.addAttribute("ID_number", number);

        return "calendar";
    }



    @GetMapping("/page")
    public String new_page(Model model, @RequestParam String date,
                           @CookieValue(value="User") String user){
        // 해당 html 파일에다가 User과 Date이 값을 넣고 일기의 내용을 가져옴

        model.addAttribute("User", Long.parseLong(user));
        model.addAttribute("date", date);
        return "page";
    }

    @GetMapping("/page/read")
    public String page_model(Model model, @RequestParam String User, @RequestParam String date){
        // 데이터에서 ID와 date에 해당하는 값을 가져옴
        String url = String.format("redirect:/diary/home?number=%s",User);
        try {
            DiaryDTO get_Diary = diaryservice.getDiary(User, date);
        } catch (Exception e) {
            model.addAttribute("msg", "해당 날짜에 일기가 존재하지 않습니다. 뒤로가기를 눌러 작성해주세요.");
            model.addAttribute("url", url);
            return "alert";
        }

        model.addAttribute("User", User);
        model.addAttribute("date", date);
        DiaryDTO get_Diary = diaryservice.getDiary(User, date);
        model.addAttribute("Diary", get_Diary.getDiary());
        return "diary/read";
    }

    @GetMapping("/page/write")
    public String page_write(Model model, @RequestParam String User, @RequestParam String date){
        model.addAttribute("User", User);
        model.addAttribute("date", date);
        return "diary/write";
    }

    @GetMapping("/page/changed")
    public String page_changed(Model model, @RequestParam String User, @RequestParam String date){
        model.addAttribute("User", User);
        model.addAttribute("date", date);
        DiaryDTO get_Diary = diaryservice.getDiary(User, date);
        model.addAttribute("Diary", get_Diary.getDiary());
        return "diary/change";
    }



    @PostMapping("/page/write_method")
    public String write_something(Model model, @RequestParam String User, @RequestParam String date, @RequestParam String Diary){ //관련된 model 옮기기
        // DB에 일기의 내용을 보냄
        DiaryDTO get_Diary = new DiaryDTO(User, date, Diary);
        String url = String.format("redirect:/diary/home?number=%s",User);
        try {
            diaryservice.saveDiary(get_Diary);
        } catch (Exception e) {
            model.addAttribute("msg", "이미 데이터에 존재합니다. 수정 버튼을 눌러주세요.");
            model.addAttribute("url", url);
            return "alert";
        }

        return url;
    }

    @PostMapping("/page/change_method")
    public String update_something(Model model, @RequestParam String User, @RequestParam String date, @RequestParam String Diary){
        // DB에 수정된 내용을 보냄
        String url = String.format("redirect:/diary/home?number=%s",User);
        try {
            diaryservice.changedDiary(User, date, Diary);
        } catch (Exception e) {
            model.addAttribute("msg", "존재하는 데이터가 없습니다. 생성 버튼을 눌러주세요.");
            model.addAttribute("url", url);
            return "alert";
        }

        return url;
    }

    @DeleteMapping("/page/delete")
    public String delete_somthing(Model model,@RequestParam String User,  @RequestParam String date){
        // DB에 일기 내용을 지운다.
        String url = String.format("redirect:/diary/home?number=%s",User);
        try {
            diaryservice.deleteDiary(User, date);
        } catch (Exception e) {
            model.addAttribute("msg", "존재하는 데이터가 없습니다. 생성 버튼을 눌러주세요.");
            model.addAttribute("url", model.addAttribute("url", url));
            return "alert";
        }

        return url;
    }




    @GetMapping("/search")
    public String search(){
        // 검색을 할수 있도록 하는 html을 불러온다.
        return "search";
    }

    @GetMapping("/search/get")
    public String searching_result(Model model, @RequestParam String ID, @RequestParam String start_date, @RequestParam String end_date, @RequestParam String Question){
        // API를 활용해서 검색을 하고, search_result 라는 템플릿에 나타낸다.
        return "search_result";
    }

    @GetMapping("/")
    public String login(){
        // 이때 로그인과 관련된 내용이 나타남
        return "login_page";
    }
    @PostMapping("/login/get")
    public String login_get(Model model, @RequestParam String ID, @RequestParam String password,
                            HttpServletResponse response){
        memberresponsDTO profile = Memberservice.getMember(ID);
        if (!profile.getPassword().equals(password)){
            model.addAttribute("msg", "비밀번호가 올바르지 않습니다.");
            model.addAttribute("url", "/diary/");
            return "alert";
        }
        // 여기는 그거 인듯
        Cookie cookie = new Cookie("User", String.valueOf(profile.getNumber()));
        cookie.setMaxAge(60*60*2);
        cookie.setPath("/");
        response.addCookie(cookie);


        String url = String.format("redirect:/diary/home?number=%s",profile.getNumber());
        // 데이터를 가져오고 회원 정보를 login_information에 나타나도록 한다.
        return url;
    }

    @GetMapping("/join")
    public String join(){
        // 이때 회원 가입과 관련된 내용이 나타남
        return "join";
    }


    @PostMapping("/join/post")
    public String join_post(Model model, @RequestParam String ID, @RequestParam String PASSWORD, @RequestParam String PW){
        if (!PASSWORD.equals(PW)){
            model.addAttribute("msg", "비밀번호가 동일하지 않습니다.");
            model.addAttribute("url", "/diary/join");
            return "alert";
        }

        memberDTO profile = new memberDTO(ID, PASSWORD);
        Memberservice.saveMember(profile);
        // 데이터에 보냄 : 회원 가입 완료
        return "redirect:/diary/";
    }

    @DeleteMapping("/join/delete")
    public String join_delete(Model model, @RequestParam Long number){
        memberservice delete_profile = null;
        try {
            delete_profile.deleteMember(number);
        } catch (Exception e) {
            //mv.addObject("data", new Message("해당 정보가 존재하지 않습니다.", "/diary/login"));
            //mv.setViewName("Message");
            return "redirect:/diary/";
        }
        // 데이터에서 삭제함
        return "redirect:/diary/";
    }


}
