package com.example.demo.controller;

import com.example.demo.controller.input.AccountListInputForm;
import com.example.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/account")
public class AccountController {
  private final AccountService service;

  @PostMapping("/list")
  public String list(@RequestBody AccountListInputForm param, Model model){
    model.addAttribute("accounts", service.list(param));
    return "account/list";
  }
}
