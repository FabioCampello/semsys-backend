package com.williamdsw.semsys.security;

import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTUtil 
{
	// HELPER FUNCTIONS
	
	public String generateToken (String socialSecurityNumber)
	{
		return Jwts.builder ()
				   .setSubject (socialSecurityNumber)
				   .setExpiration (new Date (System.currentTimeMillis () + SecurityConstants.getExpirationTime ()))
				   .signWith (SignatureAlgorithm.HS512, SecurityConstants.getSecret ().getBytes ())
				   .compact ();
	}
	
	public boolean isTokenValid (String token)
	{
		Claims claims = this.getClaims (token);
		if (claims != null)
		{
			String username = claims.getSubject ();
			Date expiration = claims.getExpiration ();
			Date now = new Date (System.currentTimeMillis ());
			
			return (username != null && expiration != null && now.before (expiration));
		}
		
		return false;
	}
	
	public String getUsername (String token)
	{
		Claims claims = this.getClaims (token);
		if (claims != null)
		{
			return claims.getSubject ();
		}
		
		return null;
	}
	
	private Claims getClaims (String token)
	{
		try 
		{
			JwtParser parse = Jwts.parser ();
			parse.setSigningKey (SecurityConstants.getSecret ().getBytes ());
			return parse.parseClaimsJws (token).getBody ();
		} 
		catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException exception) 
		{
			return null;
		}
	}
}