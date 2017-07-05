package edu.mum.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.mum.domain.Authority;
import edu.mum.domain.Group;
import edu.mum.domain.UserCredentials;
import edu.mum.service.GroupService;
import edu.mum.service.UserCredentialsService;

@Component
public class Groups {

	@Autowired
	GroupService groupService;
	
	@Autowired
	UserCredentialsService userCredentialsService;
	
 	public void addGroups() {
	
 	   // Create ADMIN Group


    // Create SUPERVISOR Group


	    
 	    // Add LIST to both groups

 	    
	    //Add READ to both Groups


 		
	    //Add Update to Super only
 

 	   // Add users to groups
  	
 	   
 	   // Save groups
 	    groupService.save(groupAdmin);
 	    groupService.update(groupSuper);



	}
}
