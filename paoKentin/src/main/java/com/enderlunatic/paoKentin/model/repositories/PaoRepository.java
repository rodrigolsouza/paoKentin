package com.enderlunatic.paoKentin.model.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enderlunatic.paoKentin.model.entities.Pao;

public class PaoRepository implements GenericRepository<Pao, Integer> {

	PaoRepository() {
		
	}
	
    @Override
    public void create(Pao pao)throws SQLException {
        String sql = "INSERT INTO pao (nome, descricao, tempo_preparo) VALUES (?, ?, ?)";

        
        PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
     
        statement.setString(1, pao.getNome());
        statement.setString(2, pao.getDescricao());
        statement.setInt(3, pao.getTempoPreparo());
        statement.execute();
       
    }
    
    @Override
    public void update(Pao pao) {
        String sql = "UPDATE pao SET nome = ?, descricao = ?, tempo_preparo = ? WHERE id = ?";

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            statement.setString(1, pao.getNome());
            statement.setString(2, pao.getDescricao());
            statement.setInt(3, pao.getTempoPreparo());
            statement.setInt(4, pao.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pao read(Integer id) {
        String sql = "SELECT * FROM pao WHERE id = ?";
        Pao nPao = null;

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	nPao= new Pao();
            	nPao.setId(id);
            	nPao.setNome(resultSet.getString("nome"));
            	nPao.setDescricao(resultSet.getString("descricao"));
            	nPao.setTempoPreparo(resultSet.getInt("tempo_preparo"));
            	
            }
            
            return nPao;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM pao WHERE id = ?";

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pao> readAll() {
        String sql = "SELECT * FROM pao";
        List<Pao> paes = new ArrayList<>();

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Pao nPao=null;
            while (resultSet.next()) {
            	nPao = new Pao();
            	nPao.setId(resultSet.getInt("id"));
            	nPao.setNome(resultSet.getString("nome"));
            	nPao.setDescricao(resultSet.getString("descricao"));
            	nPao.setTempoPreparo(resultSet.getInt("tempo_preparo"));
            	
               
                paes.add(nPao);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paes;
    }
    
}

