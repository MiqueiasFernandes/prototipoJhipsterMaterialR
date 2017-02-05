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
 * A TBProjeto.
 */
@Entity
@Table(name = "tb_projeto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbprojeto")
public class TBProjeto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idprojeto", nullable = false)
    private String idprojeto;

    @NotNull
    @Column(name = "idusuario", nullable = false)
    private Long idusuario;

    @Column(name = "idsessao")
    private Long idsessao;

    @Column(name = "idbase")
    private String idbase;

    @Column(name = "script")
    private String script;

    @ManyToOne
    private TBUsuario tBUsuario;

    @OneToMany(mappedBy = "tBProjeto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TBBase> referencias = new HashSet<>();

    @OneToMany(mappedBy = "tBProjeto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TBSessao> mantems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdprojeto() {
        return idprojeto;
    }

    public TBProjeto idprojeto(String idprojeto) {
        this.idprojeto = idprojeto;
        return this;
    }

    public void setIdprojeto(String idprojeto) {
        this.idprojeto = idprojeto;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public TBProjeto idusuario(Long idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public Long getIdsessao() {
        return idsessao;
    }

    public TBProjeto idsessao(Long idsessao) {
        this.idsessao = idsessao;
        return this;
    }

    public void setIdsessao(Long idsessao) {
        this.idsessao = idsessao;
    }

    public String getIdbase() {
        return idbase;
    }

    public TBProjeto idbase(String idbase) {
        this.idbase = idbase;
        return this;
    }

    public void setIdbase(String idbase) {
        this.idbase = idbase;
    }

    public String getScript() {
        return script;
    }

    public TBProjeto script(String script) {
        this.script = script;
        return this;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public TBUsuario getTBUsuario() {
        return tBUsuario;
    }

    public TBProjeto tBUsuario(TBUsuario tBUsuario) {
        this.tBUsuario = tBUsuario;
        return this;
    }

    public void setTBUsuario(TBUsuario tBUsuario) {
        this.tBUsuario = tBUsuario;
    }

    public Set<TBBase> getReferencias() {
        return referencias;
    }

    public TBProjeto referencias(Set<TBBase> tBBases) {
        this.referencias = tBBases;
        return this;
    }

    public TBProjeto addReferencia(TBBase tBBase) {
        this.referencias.add(tBBase);
        tBBase.setTBProjeto(this);
        return this;
    }

    public TBProjeto removeReferencia(TBBase tBBase) {
        this.referencias.remove(tBBase);
        tBBase.setTBProjeto(null);
        return this;
    }

    public void setReferencias(Set<TBBase> tBBases) {
        this.referencias = tBBases;
    }

    public Set<TBSessao> getMantems() {
        return mantems;
    }

    public TBProjeto mantems(Set<TBSessao> tBSessaos) {
        this.mantems = tBSessaos;
        return this;
    }

    public TBProjeto addMantem(TBSessao tBSessao) {
        this.mantems.add(tBSessao);
        tBSessao.setTBProjeto(this);
        return this;
    }

    public TBProjeto removeMantem(TBSessao tBSessao) {
        this.mantems.remove(tBSessao);
        tBSessao.setTBProjeto(null);
        return this;
    }

    public void setMantems(Set<TBSessao> tBSessaos) {
        this.mantems = tBSessaos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TBProjeto tBProjeto = (TBProjeto) o;
        if (tBProjeto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tBProjeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TBProjeto{" +
            "id=" + id +
            ", idprojeto='" + idprojeto + "'" +
            ", idusuario='" + idusuario + "'" +
            ", idsessao='" + idsessao + "'" +
            ", idbase='" + idbase + "'" +
            ", script='" + script + "'" +
            '}';
    }
}
