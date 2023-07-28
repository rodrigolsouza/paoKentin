package com.enderlunatic.paoKentin.model.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.enderlunatic.paoKentin.model.entities.Fornada;
import com.enderlunatic.paoKentin.model.entities.Pao;

public class FornadaRepository implements GenericRepository<Fornada, Integer> {

	FornadaRepository() {
		
	}
	
    @Override
    public void create(Fornada fornada) {
        String sql = "INSERT INTO fornada (tempo_inicial, tempo_final, pao_id) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            statement.setInt(1, fornada.getId());
            statement.setTimestamp(2, new Timestamp(fornada.getTempoInicial()));
            statement.setTimestamp(3, new Timestamp(fornada.getTempoFinal()));
            statement.setInt(4, fornada.getPao().getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//Esse método talvez não seja necessário, a não ser que se precise
//ajustar um novo tempo inicial pro problemas técnicos no forno, falta de energia
// com a fornada já no forno...

    @Override
    public void update(Fornada fornada) {
        String sql = "UPDATE fornada SET tempo_inicial = ?, tempo_final = ?, pao_id = ? WHERE id = ?";

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            statement.setTimestamp(1, new Timestamp(fornada.getTempoInicial()));
            statement.setTimestamp(2, new Timestamp(fornada.getTempoFinal()));
            statement.setInt(3, fornada.getPao().getId());
            
            statement.setInt(4, fornada.getId());
            
            statement.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Fornada read(Integer id) {
        String sql = "select * from fornada as f join pao as p on (f.pao_id = p.id) where f.id = ?";

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Fornada nFornada= null;
            
            if (resultSet.next()) {
                nFornada= new Fornada();
                
                nFornada.setId(id);
                nFornada.setTempoInicial(resultSet.getDate("tempo_inicial").getTime());
                nFornada.setTempoFinal(resultSet.getDate("tempo_final").getTime());
                
                Pao p= new Pao();
                p.setId(resultSet.getInt("pao_id"));
                p.setNome(resultSet.getString("nome"));
                p.setDescricao(resultSet.getString("descricao"));
                p.setTempoPreparo(resultSet.getInt("tempo_preparo"));
               
                nFornada.setPao(p);
            }
            
            return nFornada;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM fornada WHERE id = ?";

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Fornada> readAll() {
        String sql = "select * from fornada as f join pao as p on (f.pao_id = p.id)";
        
        List<Fornada> fornadas = new ArrayList<>();

        try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            
            ResultSet resultSet = statement.executeQuery();

            Fornada nFornada=null;
            
            while (resultSet.next()) {
            	nFornada = new Fornada();
            	
            	nFornada.setId(resultSet.getInt(1));
            	nFornada.setTempoInicial(resultSet.getDate("tempo_inicial").getTime());
                nFornada.setTempoFinal(resultSet.getDate("tempo_final").getTime());
            	
                Pao p= new Pao();
                
                p.setId(resultSet.getInt("pao_id"));
                p.setNome(resultSet.getString("nome"));
                p.setDescricao(resultSet.getString("descricao"));
                p.setTempoPreparo(resultSet.getInt("tempo_preparo"));
                
                nFornada.setPao(p);
                fornadas.add(nFornada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fornadas;
    }
    
    public List<Fornada>getLastFornadas(){
    	String sql = "select * from fornada as f join pao as p on (f.pao_id = p.id)";
    	
    	try {
			List<Fornada> fornadas = RepositoryFacade.getCurrentInstance().readAllFornadas();
			
			List<Fornada> lastFornadas = new ArrayList<>();
			
			for (Fornada forn:fornadas) {
				if(lastFornadas.isEmpty()) {
					lastFornadas.add(forn);
				}
				for(Fornada lForn:lastFornadas) {
					if(forn.getPao().getId()==lForn.getPao().getId()) {
						if(forn.getId()>lForn.getId()){
							lastFornadas.remove(lForn);
							lastFornadas.add(forn);
						}
					}else {
						lastFornadas.add(forn);
					}
				}
			}
			return lastFornadas;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
    }
    
    public List<Fornada> listaEstaNoForno() {
    	String sql = "select * from fornada as f join pao as p on (f.pao_id = p.id) where f.tempo_final>? ";
    	List<Fornada> fornadasQuentes= new ArrayList<>();
    	Long tempoAtual= System.currentTimeMillis();
    	
    	try {
            PreparedStatement statement = ConnectionManager.getCurrentConnection().prepareStatement(sql);
            statement.setLong(1, tempoAtual);
            ResultSet resultSet = statement.executeQuery();

            Fornada nFornada=null;
    	
            while (resultSet.next()) {
            	nFornada = new Fornada();
            	
            	nFornada.setId(resultSet.getInt(1));
            	nFornada.setTempoInicial(resultSet.getDate("tempo_inicial").getTime());
                nFornada.setTempoFinal(resultSet.getDate("tempo_final").getTime());
            	
                Pao p= new Pao();
                
                p.setId(resultSet.getInt("pao_id"));
                p.setNome(resultSet.getString("nome"));
                p.setDescricao(resultSet.getString("descricao"));
                p.setTempoPreparo(resultSet.getInt("tempo_preparo"));
                
                if(nFornada.getTempoFinal()>tempoAtual){
                	Long tempoRestante=nFornada.getTempoFinal()-tempoAtual;
                	iniciaContadorRegressivo(tempoRestante);
                }
                nFornada.setPao(p);
                fornadasQuentes.add(nFornada);
            }
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    	return fornadasQuentes;	
    }
    
    public static void iniciaContadorRegressivo(long milliseconds) {
        long totalSeconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        
        String initialTimeFormat = String.format("%02d:%02d", minutes, seconds);
        System.out.println("Starting countdown: " + initialTimeFormat);

        while (totalSeconds > 0) {
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalSeconds--;
            minutes = totalSeconds / 60;
            seconds = totalSeconds % 60;
            String timeFormat = String.format("%02d:%02d", minutes, seconds);
            System.out.println(timeFormat);
        }

        System.out.println("Countdown completed: 00:00");
    }
}

