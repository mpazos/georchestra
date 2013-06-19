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
@RequestMapping("/public/home.do")
public class HomeController {

	public String home(){
		
		return "home";
	}
}
