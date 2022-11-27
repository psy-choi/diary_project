package com.example.Diary.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.Diary.service.*;
import com.example.Diary.Data.dto.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;


@Controller
@RequestMapping("/diary")
public class CalendarController {
    
    @GetMapping("")
    public String calendar() {

        return "calendar";
    }

    @GetMapping("/page")
    public String new_page(Model model, @RequestParam String date){
        System.out.println(date);
        model.addAttribute("date", date);
        return "page";
    }

    @GetMapping("/page/write")
    public String page_model(Model model, @RequestParam String ID, @RequestParam String date){
        // 데이터에서 ID와 date에 해당하는 값을 가져옴
        model.addAttribute("date", date);
        return "write";
    }

    @PostMapping("/page/write_method")
    public String write_something(Model model, @RequestParam String ID, @RequestParam String date, @RequestParam String passage){ //관련된 model 옮기기
        // DB에 일기의 내용을 보냄
        return "redirect:/diary";
    }

    @PutMapping("/page/write_method")
    public String update_somthing(Model model, @RequestParam String ID, @RequestParam String date, @RequestParam String passage){
        // DB에 수정된 내용을 보냄
        return "redirect:/diary";
    }

    @DeleteMapping("/page/delete")
    public String delete_somthing(Model model,@RequestParam String ID,  @RequestParam String date){
        // DB에 일기 내용을 지운다.
        return "redirect:/diary";
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

    @GetMapping("/login")
    public String login(){
        // 이때 로그인과 관련된 내용이 나타남
        return "login_page";
    }

    @GetMapping("/join")
    public String join(){
        // 이때 회원 가입과 관련된 내용이 나타남
        return "join";
    }

    @PostMapping("/join/post")
    public String join_post(Model model, @RequestParam String ID, @RequestParam String PASSWORD){
        memberDTO profile = new memberDTO(ID, PASSWORD);
        memberservice save_profile = null;
        save_profile.saveMember(profile);
        // 데이터에 보냄 : 회원 가입 완료
        return "redirect:/diary/login";
    }

    @DeleteMapping("/join/delete")
    public String join_delete(Model model, @RequestParam Long number){
        ModelAndView mv = new ModelAndView();
        memberservice delete_profile = null;
        try {
            delete_profile.deleteMember(number);
        } catch (Exception e) {
            mv.addObject("data", new Message("해당 정보가 존재하지 않습니다.", "/diary/login"));
            mv.setViewName("Message");
            return "redirect:/diary/login";

        }
        // 데이터에서 삭제함
        return "redirect:/diary/login";
    }

    @GetMapping("/login/get")
    public String login_get(Model model, @RequestParam String ID){
        // 데이터를 가져오고 회원 정보를 login_information에 나타나도록 한다.
        return "login_information";
    }

}
