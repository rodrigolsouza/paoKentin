package com.enderlunatic.paoKentin.model.entities;

public class Fornada {
    private int id;
	private Long tempoInicial;
    private Pao pao;
    private Long tempoFinal;

    public Fornada() {
        this.tempoInicial= System.currentTimeMillis();
        this.tempoFinal= calculaTempoFinal();
    }

    public Fornada(Long tempoInicial, Pao pao, Long Tempofinal) {
        this.tempoInicial = System.currentTimeMillis();
        this.pao = pao;
        this.tempoFinal=calculaTempoFinal();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTempoInicial() {
        return tempoInicial;
    }

    public void setTempoInicial(Long tempoInicial) {
        this.tempoInicial = tempoInicial;
    }

    public Long getTempoFinal() {
		return tempoFinal;
	}

	public void setTempoFinal(Long tempoFinal) {
		this.tempoFinal = tempoFinal;
	}

	public Pao getPao() {
        return pao;
    }

    public void setPao(Pao pao) {
        this.pao = pao;
    }
    
    public Long calculaTempoFinal() {
    	Long tempoFinal=this.tempoInicial +(pao.getTempoPreparo()*60*1000);
    	return tempoFinal;
    }
    
}

