package com.enderlunatic.paoKentin.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enderlunatic.paoKentin.model.entities.Fornada;
import com.enderlunatic.paoKentin.model.entities.Pao;
import com.enderlunatic.paoKentin.model.repositories.RepositoryFacade;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class FornadaController {
	
	@CrossOrigin(origins = "*")
	@PostMapping("/fornada/{pao_id}")
	public ResponseEntity<?> create(@RequestBody Fornada fornada, @PathVariable("pao_id") int paoId) {
		
		Pao pao;
		try {
			pao =RepositoryFacade.getCurrentInstance().readPao(paoId);
			fornada.setPao(pao);
			RepositoryFacade.getCurrentInstance().create(fornada);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/fornada")
	public void update( @RequestBody Fornada fornada) {
		try {
			RepositoryFacade.getCurrentInstance().update(fornada);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/fornada/{id}")
	public ResponseEntity<Fornada>read(@PathVariable("id")int id) {
		try {
			Fornada fornada = RepositoryFacade.getCurrentInstance().readFornada(id);
			if(fornada==null) {
				return new ResponseEntity<Fornada>(HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<Fornada>(fornada,HttpStatus.OK);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/fornada/")
	public ResponseEntity<List<Fornada>>readAll() {
		
		try {
			List<Fornada>fornadas =new ArrayList<>();
			fornadas = RepositoryFacade.getCurrentInstance().readAllFornadas();
			return new ResponseEntity<List<Fornada>>(fornadas,HttpStatus.OK);
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erro ao ler a lista");
		}
	}
	
	
	@GetMapping("fornada/{id}")
	public void delete(@PathVariable("id") int id) {
		try {
			RepositoryFacade.getCurrentInstance().deleteFornada(id);
		} catch (SQLException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erro ao deletar");
		}
	
	}
}
