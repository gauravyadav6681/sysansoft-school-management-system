package com.syansoft.school_management_system.entity;


	import org.springframework.security.core.GrantedAuthority;

	public enum Role implements GrantedAuthority {
	    STUDENT, TEACHER, ADMIN;

	    @Override
	    public String getAuthority() {
	        return name();
	    }
	}

