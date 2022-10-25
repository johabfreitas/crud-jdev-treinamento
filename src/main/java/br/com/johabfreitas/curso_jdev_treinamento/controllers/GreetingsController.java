package br.com.johabfreitas.curso_jdev_treinamento.controllers;

import java.util.List;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.johabfreitas.curso_jdev_treinamento.model.Usuario;
import br.com.johabfreitas.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /*IC/CD ou CDI - Injeção de dependência*/
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornoOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);/*grava no banco de dados*/
    	return "Olá mundo " + nome;
    }
    
    @GetMapping(value = "listatodos")/*Primeiro método de API*/
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	List<Usuario> usuarios = usuarioRepository.findAll();/*Executa a consulta no banco de dados*/
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/*Retorna a lista em JSON*/
    }
    
    @PostMapping(value = "salvar") /*mapeia a url*/
    @ResponseBody /*Descrição da resposta*/
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){/*Recebe os dados para salva*/
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @DeleteMapping(value = "delete") /*mapeia a url*/
    @ResponseBody /*Descrição da resposta*/
    public ResponseEntity<String> delete(@RequestParam Long iduser){/*Recebe os dados para salva*/
    	
    	usuarioRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
    }
    
    @GetMapping(value = "buscaruserid")/*mepeia a url*/
    @ResponseBody /* Descrição da resposta*/
    public ResponseEntity<?> buscaruserid(@RequestParam(name = "iduser") Long iduser){
 
    	Usuario usuario = usuarioRepository.findById(iduser).orElse(null);
    	if(usuario == null){
    		//return ResponseEntity.notFound().build();
    		return new ResponseEntity<String>("Usuário não encontrado.", HttpStatus.OK);
    		
    	}
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @PutMapping(value = "atualizar") /*mapeia a url*/
    @ResponseBody /*Descrição da resposta*/
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){/*Recebe os dados para salva*/
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
}
