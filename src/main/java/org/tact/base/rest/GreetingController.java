package org.tact.base.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@EnableScheduling
@RestController
public class GreetingController {
	
	@Autowired
    private SimpMessagingTemplate template;
	
    @MessageMapping("/start-count-down")
    public void greeting1(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        
        for(int i = 5;i>0;i--){
        	
        	Thread.sleep(1000); // simulated delay
        	print("trap1.1 "+i);
        	
        	//greeting2();
        	
        	Greeting g = new Greeting(i+" Seconds Left");
        	
        	this.template.convertAndSend("/topic/print-count-down", g);
        }
        
        //print("trap1.1");
    }
    
    public void print(Object obj){
    	System.out.println(obj);
    }
    
    @SendTo(" ")
    public Greeting greeting2() throws Exception {
        //Thread.sleep(1000); // simulated delay
        print("trap2.1 ");
        return new Greeting("Hello, " + HtmlUtils.htmlEscape("1012") + "!");
    }
}
