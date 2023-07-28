package com.enderlunatic.paoKentin.model.repositories;

import java.sql.SQLException;
import java.util.List;

import com.enderlunatic.paoKentin.model.entities.Fornada;
import com.enderlunatic.paoKentin.model.entities.Pao;

public class RepositoryFacade {

	private static RepositoryFacade myself = new RepositoryFacade();
	
	private GenericRepository<Pao, Integer> paoRepo = null;
	private GenericRepository<Fornada, Integer> fornadaRepo=null;
	
	private RepositoryFacade() {
		this.paoRepo = new PaoRepository();
		this.fornadaRepo= new FornadaRepository();
		
	}
	
	public static RepositoryFacade getCurrentInstance() {
		return myself;
	}
	
	public void create(Pao pao) throws SQLException {
		this.paoRepo.create(pao);
	}
	
	public void create (Fornada fornada)throws SQLException {
		this.fornadaRepo.create(fornada);
	}
	
	public void update(Pao pao)throws SQLException {
		this.paoRepo.update(pao);
	}
	
	public void update(Fornada fornada)throws SQLException {
		this.fornadaRepo.update(fornada);
	}
	
	public Pao readPao(int id)throws SQLException {
		return this.paoRepo.read(id);
	}
	
	public Fornada readFornada(int id)throws SQLException {
		return this.fornadaRepo.read(id);
	}
	
	public void deletePao(int id)throws SQLException {
		this.paoRepo.delete(id);
	}
	public void deleteFornada(int id)throws SQLException {
		this.readFornada(id);
	}
	
	public List<Pao> readAllPaes() throws SQLException{
		return this.paoRepo.readAll();
	}
	
	public List<Fornada> readAllFornadas()throws SQLException {
		return this.fornadaRepo.readAll();
	}
}
