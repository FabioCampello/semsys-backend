package com.williamdsw.semsys.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.williamdsw.semsys.domain.dto.EmailDTO;
import com.williamdsw.semsys.security.JWTUtil;
import com.williamdsw.semsys.security.SecurityConstants;
import com.williamdsw.semsys.security.UserDetailsSS;
import com.williamdsw.semsys.services.mail.AuthService;
import com.williamdsw.semsys.services.security.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping (path = "/v1")
public class AuthResource 
{
	// FIELDS
	
	@Autowired private AuthService authService;
	@Autowired private JWTUtil jwtUtil;
	
	// ENDPOINTS
	
	@ApiOperation (value = "Generates a new password by given email")
	@ApiResponses (value = { @ApiResponse (code = 404, message = "Email Not Found") })
	@PostMapping (path = "/public/auth/forgot-password")
	public ResponseEntity<Void> forgotPassword (@Valid @RequestBody EmailDTO dto)
	{
		authService.sendNewPassword (dto.getEmail ());
		return ResponseEntity.noContent ().build ();
	}
	
	@ApiOperation (value = "Refresh token of current user")
	@PreAuthorize ("hasRole ('EMPLOYEE') or hasRole ('STUDENT')")
	@PostMapping (path = "/protected/auth/refresh-token")
	public ResponseEntity<Void> refreshToken (HttpServletResponse response)
	{
		UserDetailsSS user = UserService.getAuthenticated ();
		String token = jwtUtil.generateToken (user.getUsername ());
		response.addHeader (SecurityConstants.getAuthorizationHeader (), SecurityConstants.getTokenPrefix () + token);
		response.addHeader ("access-control-expose-headers", SecurityConstants.getAuthorizationHeader ());
		return ResponseEntity.noContent ().build ();
	}
}