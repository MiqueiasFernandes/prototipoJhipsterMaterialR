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

import com.mfernandes.app.domain.enumeration.TBTiposessao;

/**
 * A TBSessao.
 */
@Entity
@Table(name = "tb_sessao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbsessao")
public class TBSessao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idsessao", nullable = false)
    private Long idsessao;

    @Column(name = "idmodeloexclusivo")
    private String idmodeloexclusivo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TBTiposessao tipo;

    @OneToMany(mappedBy = "tBSessao")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TBModeloExclusivo> associas = new HashSet<>();

    @ManyToOne
    private TBProjeto tBProjeto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdsessao() {
        return idsessao;
    }

    public TBSessao idsessao(Long idsessao) {
        this.idsessao = idsessao;
        return this;
    }

    public void setIdsessao(Long idsessao) {
        this.idsessao = idsessao;
    }

    public String getIdmodeloexclusivo() {
        return idmodeloexclusivo;
    }

    public TBSessao idmodeloexclusivo(String idmodeloexclusivo) {
        this.idmodeloexclusivo = idmodeloexclusivo;
        return this;
    }

    public void setIdmodeloexclusivo(String idmodeloexclusivo) {
        this.idmodeloexclusivo = idmodeloexclusivo;
    }

    public TBTiposessao getTipo() {
        return tipo;
    }

    public TBSessao tipo(TBTiposessao tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TBTiposessao tipo) {
        this.tipo = tipo;
    }

    public Set<TBModeloExclusivo> getAssocias() {
        return associas;
    }

    public TBSessao associas(Set<TBModeloExclusivo> tBModeloExclusivos) {
        this.associas = tBModeloExclusivos;
        return this;
    }

    public TBSessao addAssocia(TBModeloExclusivo tBModeloExclusivo) {
        this.associas.add(tBModeloExclusivo);
        tBModeloExclusivo.setTBSessao(this);
        return this;
    }

    public TBSessao removeAssocia(TBModeloExclusivo tBModeloExclusivo) {
        this.associas.remove(tBModeloExclusivo);
        tBModeloExclusivo.setTBSessao(null);
        return this;
    }

    public void setAssocias(Set<TBModeloExclusivo> tBModeloExclusivos) {
        this.associas = tBModeloExclusivos;
    }

    public TBProjeto getTBProjeto() {
        return tBProjeto;
    }

    public TBSessao tBProjeto(TBProjeto tBProjeto) {
        this.tBProjeto = tBProjeto;
        return this;
    }

    public void setTBProjeto(TBProjeto tBProjeto) {
        this.tBProjeto = tBProjeto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TBSessao tBSessao = (TBSessao) o;
        if (tBSessao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tBSessao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TBSessao{" +
            "id=" + id +
            ", idsessao='" + idsessao + "'" +
            ", idmodeloexclusivo='" + idmodeloexclusivo + "'" +
            ", tipo='" + tipo + "'" +
            '}';
    }
}
