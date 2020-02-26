package br.com.jefferson.salesmanegement.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jefferson.salesmanegement.domain.dto.AuthDto;
import br.com.jefferson.salesmanegement.domain.dto.ResponseDto;
import br.com.jefferson.salesmanegement.services.MyUserDetailsService;
import br.com.jefferson.salesmanegement.utils.JwtUtil;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> auth(@Valid @RequestBody AuthDto authDto) throws Exception {
        try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authDto.getMail(), authDto.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Usuário ou senha invalidos", e);
		}

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authDto.getMail());

		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new ResponseDto(jwt));
    }

}