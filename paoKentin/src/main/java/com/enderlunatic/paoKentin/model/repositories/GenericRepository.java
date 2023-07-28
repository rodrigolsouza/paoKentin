package com.enderlunatic.paoKentin.model.repositories;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository <Cl, Key>{
	
	public void create(Cl cl) throws SQLException ;
	public void update(Cl cl) throws SQLException;
	public Cl read(Key k)throws SQLException;
	public void delete(Key k)throws SQLException;
	public List<Cl> readAll()throws SQLException;

}