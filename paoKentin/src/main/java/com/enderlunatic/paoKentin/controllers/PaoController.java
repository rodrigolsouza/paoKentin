package com.enderlunatic.paoKentin.controllers;


import java.sql.SQLException;
import java.util.List;

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

import com.enderlunatic.paoKentin.model.entities.Pao;
import com.enderlunatic.paoKentin.model.repositories.RepositoryFacade;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PaoController {

	@CrossOrigin(origins = "*")
	@PostMapping("/pao")
	public String create(@RequestBody Pao pao) {
		try {
			RepositoryFacade.getCurrentInstance().create(pao);
			return "Pão cadastrado com sucesso";
		}catch (SQLException e) {
			e.printStackTrace();
			return "Falha ao cadastrar o pão";
		}
	}
	
	@PutMapping("/pao")
	public String update(@RequestBody Pao pao) {
		
		try {
			RepositoryFacade.getCurrentInstance().update(pao);
			return "O Pao foi alterado com sucesso";
		}catch(SQLException e) {
			return "falha ao alterar o pão";
		}
		
	}
	
	@GetMapping("pao/{id}")
	public Pao read(@PathVariable("id") int id) {
		try {
			return RepositoryFacade.getCurrentInstance().readPao(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	@GetMapping("pao/{id}")
	public String delete(@PathVariable("id") int id) {
		try {
			RepositoryFacade.getCurrentInstance().deletePao(id);
			return "O Pão foi deletado com sucesso";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return "Falha ao deletar o pão";
		}
	
	}
	@GetMapping("pao")
	public List<Pao> readAll(){
		try {
			return RepositoryFacade.getCurrentInstance().readAllPaes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
