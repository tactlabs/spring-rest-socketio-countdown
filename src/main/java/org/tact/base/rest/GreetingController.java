package org.tact.base.rest;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
public class GreetingController {
	
	@Autowired
    private SimpMessagingTemplate template;
	
	private static final Integer DEFAULT_TIME = 20;
	private static final Integer EXTEND_TIME = 5;
	
	private Map<String, Integer> roomTimeMap = new LinkedHashMap<String, Integer>();
	
    @MessageMapping("/start-game")
    public void startCountDown(Room room) throws Exception {
    	
    	String roomName = room.getName();    	
    	roomTimeMap.put(roomName, DEFAULT_TIME);
        
        print("Time updated for "+roomName);
    	
    	//roomTimeMap.put(currentRoom, DEFAULT_TIME);
        
        for(int i = roomTimeMap.get(roomName);i>0;){
        	
        	i = roomTimeMap.get(roomName);        			
        	i--;
        	
        	roomTimeMap.put(roomName, i);
        	
        	Thread.sleep(1000); // simulated delay
        	print("trap1.1 "+i);
        	
        	Greeting g = new Greeting(i+" Seconds Left");
        	
        	this.template.convertAndSend("/topic/print-count-down", g);
        }        
    }
    
    /*
    @MessageMapping("/update-room")    
    public void updateRoom(Room room) throws Exception {
        String roomName = room.getName();
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(roomName) + "!");
     
        roomTimeMap.put(roomName, DEFAULT_TIME);
        
        print("Time updated for "+roomName);
    }
    */
    
    @MessageMapping("/extend-time")    
    public void extendRoomTime(Room room) throws Exception {
        
    	String roomName = room.getName();
        Integer previousTime = roomTimeMap.get(roomName);
        roomTimeMap.put(roomName, (previousTime + EXTEND_TIME));
        
        print("Time extended for "+roomName);
    }
    
    public void print(Object obj){
    	System.out.println(obj);
    }    
}
