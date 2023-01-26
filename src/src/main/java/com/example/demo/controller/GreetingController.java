package com.example.demo.controller;

import com.example.demo.entity.MDivision;
import com.example.demo.entity.TAccount;
import com.example.demo.service.GreetingService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@RequiredArgsConstructor
@Controller
@RequestMapping("/greeting")
public class GreetingController {
  private final GreetingService service;

  @GetMapping("/hello")
  public String hello(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      @RequestParam(name = "id", required = false) Long id,
      Model model) {
    TAccount account = null;
    if(Objects.nonNull(id)){
      account = service.findAccountById(id);
    }
    if(Objects.isNull(account)){
      model.addAttribute("name", name);
    } else {
      model.addAttribute("name", account.getName());
    }
    MDivision division = service.findFiresDivision();
    model.addAttribute("divisionName", division.getCodeName());
    return "greeting/hello";
  }

  @GetMapping("/morning")
  public String morning(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    return "greeting/morning";
  }
}
