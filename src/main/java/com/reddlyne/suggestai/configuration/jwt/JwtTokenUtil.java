package com.reddlyne.suggestai.configuration.jwt;

import com.reddlyne.suggestai.model.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

	@Value("${jwt.expire-duration}")
	private long EXPIRE_DURATION;
	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	public String generateAccessToken(User user) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + EXPIRE_DURATION);

		return Jwts.builder()
				.setSubject(String.format("%s,%s", user.getId(), user.getLogin()))
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser()
					.setSigningKey(SECRET_KEY)
					.parseClaimsJws(token);

			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	private Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}
}
