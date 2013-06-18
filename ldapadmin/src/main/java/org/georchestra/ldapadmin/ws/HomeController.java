/**
 * 
 */
package org.georchestra.ldapadmin.ws;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Display the home page 
 * 
 * 
 * @author Mauricio Pazos
 *
 */
@Controller
public class HomeController {

	@RequestMapping(value="/public/")
	public String home(){
		
		
		System.out.println("Account Controller");
		
		return "home";
	}
}
