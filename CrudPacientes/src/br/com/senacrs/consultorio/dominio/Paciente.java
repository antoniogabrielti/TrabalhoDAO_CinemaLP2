package br.com.senacrs.consultorio.dominio;

import java.util.Date;

/**
 * @author lhries
 */
public class Paciente {
    private int id;
    private String rg, nome;
    private Date dataNascimento;

    public Paciente(String rg, String nome, Date dataNascimento) {
        this.rg = rg;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public Paciente(int id, String rg, String nome, Date dataNascimento) {
        this.id = id;
        this.rg = rg;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRg() {
        return rg;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }    

    public void setNome(String nome) {
        this.nome = nome;
    }
}
