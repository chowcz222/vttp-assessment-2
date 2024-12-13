package vttp.batch5.ssf.noticeboard.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers

@Controller
@RequestMapping
public class NoticeController {

    @Autowired
    private NoticeService noticesvc;


    @GetMapping(path={"/", "index.html"})
    public String createNoticeAttribute(Model model) {

        model.addAttribute("notice", new notice());

        return "index";
    }

    @PostMapping("/notice")
    public String storeJson(@Valid @ModelAttribute("notice") notice noticeJson,
         BindingResult bindings, Model model) throws ParseException {

            if (bindings.hasErrors()) 
            return "index";

            String[] message = noticesvc.postToNoticeServer(noticeJson);
            model.addAttribute("message", message[1]);

            if(message[0].equals("0")) {
                return "success";
            } else {
                return "error";
            }
    }

    @GetMapping(path="/status", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHealth() {

        Boolean key = noticesvc.getRandomKey();

        if(key) {

            return ResponseEntity.ok(" ");
        } else {
            return new ResponseEntity<>(" ", HttpStatus.SERVICE_UNAVAILABLE);

        }
    }



}
