package com.mfernandes.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TBUsuario.
 */
@Entity
@Table(name = "tb_usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbusuario")
public class TBUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idusuario", nullable = false)
    private Long idusuario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "senha")
    private String senha;

    @Column(name = "email")
    private String email;

    @Column(name = "diretorio")
    private String diretorio;

    @OneToMany(mappedBy = "tBUsuario")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TBProjeto> usas = new HashSet<>();

    @OneToMany(mappedBy = "tBUsuario")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TBModeloGenerico> possuis = new HashSet<>();

    @OneToMany(mappedBy = "tBUsuario")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TBBase> armazenas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public TBUsuario idusuario(Long idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public TBUsuario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public TBUsuario senha(String senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public TBUsuario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public TBUsuario diretorio(String diretorio) {
        this.diretorio = diretorio;
        return this;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public Set<TBProjeto> getUsas() {
        return usas;
    }

    public TBUsuario usas(Set<TBProjeto> tBProjetos) {
        this.usas = tBProjetos;
        return this;
    }

    public TBUsuario addUsa(TBProjeto tBProjeto) {
        this.usas.add(tBProjeto);
        tBProjeto.setTBUsuario(this);
        return this;
    }

    public TBUsuario removeUsa(TBProjeto tBProjeto) {
        this.usas.remove(tBProjeto);
        tBProjeto.setTBUsuario(null);
        return this;
    }

    public void setUsas(Set<TBProjeto> tBProjetos) {
        this.usas = tBProjetos;
    }

    public Set<TBModeloGenerico> getPossuis() {
        return possuis;
    }

    public TBUsuario possuis(Set<TBModeloGenerico> tBModeloGenericos) {
        this.possuis = tBModeloGenericos;
        return this;
    }

    public TBUsuario addPossui(TBModeloGenerico tBModeloGenerico) {
        this.possuis.add(tBModeloGenerico);
        tBModeloGenerico.setTBUsuario(this);
        return this;
    }

    public TBUsuario removePossui(TBModeloGenerico tBModeloGenerico) {
        this.possuis.remove(tBModeloGenerico);
        tBModeloGenerico.setTBUsuario(null);
        return this;
    }

    public void setPossuis(Set<TBModeloGenerico> tBModeloGenericos) {
        this.possuis = tBModeloGenericos;
    }

    public Set<TBBase> getArmazenas() {
        return armazenas;
    }

    public TBUsuario armazenas(Set<TBBase> tBBases) {
        this.armazenas = tBBases;
        return this;
    }

    public TBUsuario addArmazena(TBBase tBBase) {
        this.armazenas.add(tBBase);
        tBBase.setTBUsuario(this);
        return this;
    }

    public TBUsuario removeArmazena(TBBase tBBase) {
        this.armazenas.remove(tBBase);
        tBBase.setTBUsuario(null);
        return this;
    }

    public void setArmazenas(Set<TBBase> tBBases) {
        this.armazenas = tBBases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TBUsuario tBUsuario = (TBUsuario) o;
        if (tBUsuario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tBUsuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TBUsuario{" +
            "id=" + id +
            ", idusuario='" + idusuario + "'" +
            ", nome='" + nome + "'" +
            ", senha='" + senha + "'" +
            ", email='" + email + "'" +
            ", diretorio='" + diretorio + "'" +
            '}';
    }
}
