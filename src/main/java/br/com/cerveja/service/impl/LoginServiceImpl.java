package br.com.cerveja.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cerveja.model.Perfis;
import br.com.cerveja.model.Usuario;
import br.com.cerveja.service.UsuarioService;

@Service("login")
public class LoginServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioService usuarioService;
	@Value("${erro.usuario.naoencontrado}")
	private String UsuarioNaoEncontrado;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.findByEmail(username);
		if(usuario == null) {
			throw new UsernameNotFoundException(UsuarioNaoEncontrado);
		}
		return createUser(usuario);
	}
	
	public void signin(Usuario usuario) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(usuario));
	}
	
	private Authentication authenticate(Usuario usuario) {
		return new UsernamePasswordAuthenticationToken(createUser(usuario), null,createAuthoritys(usuario));		
	}
	
	private User createUser(Usuario usuario) {
		return new User(usuario.getEmail(), usuario.getPassword(), createAuthoritys(usuario));
	}

	private List<GrantedAuthority> createAuthoritys(Usuario usuario) {
		List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
		for(Perfis perfil : usuario.getPerfis()){
			grantedAuthority.add(new SimpleGrantedAuthority(perfil.getRole()));
		}
		return grantedAuthority;
	}

}
