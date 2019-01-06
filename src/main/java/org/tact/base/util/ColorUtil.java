package org.tact.base.util;

import java.util.Random;

public class ColorUtil {

	public static String[] COLOR_CODES = {		
		"#ff4000", "#ff8000", "#ffbf00", "#ffff00", 
		"#bfff00", "#80ff00",  
		"#40ff00", "#00ff00", "#00ff40",
		"#00ff80", "#00ffbf", "#00ffff", "#00bfff",
		"#0080ff", "#0040ff", "#0000ff", "#4000ff",
		"#8000ff", "#bf00ff", "#ff00ff", "#ff00bf",
		"#ff0080", "#ff0040", "#ff0000" 
	};
	
	private static Random random = new Random();
	
	public static Integer getRandomNumber(Integer min, Integer max){
		return random.nextInt(max - min + 1) + min;
	}

	public static String getRandomColor(){
		
		Integer rNo = getRandomNumber(0, COLOR_CODES.length-1);
		
		return COLOR_CODES[rNo];
	}
}
