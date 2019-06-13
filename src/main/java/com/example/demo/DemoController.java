package com.example.demo;

import com.example.demo.viewdata.index.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {
	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public ModelAndView welcome() {
            ModelAndView ret = new ModelAndView("index");
            ret.addObject("text", message);
            return ret;
	}    

        @RequestMapping(value = "/areYouReady", method = RequestMethod.POST)
	public ModelAndView ready(@RequestParam(value="ready", required=true) String ready ) {
            ModelAndView ret = new ModelAndView("readyResponse");
            UserResponse userResponse = new UserResponse();
            this.readyResponse(ret, userResponse, ready);
            return ret;
	}    

        @RequestMapping(value = "/rethought", method = RequestMethod.POST)
	public ModelAndView rethought(@ModelAttribute(value="userResponse") UserResponse ur) {
            String ready;
            ModelAndView ret;
            ret = new ModelAndView("readyResponse :: result");
            if(ur.isReady()){
                ready = "yes";
            }else{
                ready = "no";
            }
            readyResponse(ret, ur, ready);
            return ret;
	}    
        
        private void readyResponse(ModelAndView ret, UserResponse ur, String ready){
            if(ready.equalsIgnoreCase("yes")){
                ret.addObject("message", "Congratulations! We are going to learn a lot!");
            }else{
                ret.addObject("message", "Oh! what a pity. Maybe at another time...");
            }
            ret.addObject("userResponse", ur);
            ret.addObject("ready", ready);
        }
        
        
}
