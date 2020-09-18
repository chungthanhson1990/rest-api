package com.restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import java.util.*;

@RestController
public class RestfulWSController {
	
	@Value("${\"git.commit.id\"}")
    private String lastcommitsha;
	
	@Value("${\"git.build.version\"}")
    private String version;
	
	@GetMapping(path = "/version")
	public Map<String, String> helloWorld() {
	    Map<String, String> result = new HashMap<>();
	    result.put("version", version);
	    result.put("lastcommitsha", lastcommitsha);
	    result.put("message", "pre-interview technical test");
		return result;
	}

}
