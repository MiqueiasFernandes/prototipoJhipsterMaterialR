package com.mfernandes.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TBModeloExclusivo.
 */
@Entity
@Table(name = "tb_modelo_exclusivo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbmodeloexclusivo")
public class TBModeloExclusivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idmodeloexclusivo", nullable = false)
    private String idmodeloexclusivo;

    @NotNull
    @Column(name = "idusuario", nullable = false)
    private Long idusuario;

    @Column(name = "idmodelogenerico")
    private String idmodelogenerico;

    @Column(name = "mapeamento")
    private String mapeamento;

    @ManyToOne
    private TBSessao tBSessao;

    @OneToOne
    @JoinColumn(unique = true)
    private TBModeloGenerico mapeia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdmodeloexclusivo() {
        return idmodeloexclusivo;
    }

    public TBModeloExclusivo idmodeloexclusivo(String idmodeloexclusivo) {
        this.idmodeloexclusivo = idmodeloexclusivo;
        return this;
    }

    public void setIdmodeloexclusivo(String idmodeloexclusivo) {
        this.idmodeloexclusivo = idmodeloexclusivo;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public TBModeloExclusivo idusuario(Long idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getIdmodelogenerico() {
        return idmodelogenerico;
    }

    public TBModeloExclusivo idmodelogenerico(String idmodelogenerico) {
        this.idmodelogenerico = idmodelogenerico;
        return this;
    }

    public void setIdmodelogenerico(String idmodelogenerico) {
        this.idmodelogenerico = idmodelogenerico;
    }

    public String getMapeamento() {
        return mapeamento;
    }

    public TBModeloExclusivo mapeamento(String mapeamento) {
        this.mapeamento = mapeamento;
        return this;
    }

    public void setMapeamento(String mapeamento) {
        this.mapeamento = mapeamento;
    }

    public TBSessao getTBSessao() {
        return tBSessao;
    }

    public TBModeloExclusivo tBSessao(TBSessao tBSessao) {
        this.tBSessao = tBSessao;
        return this;
    }

    public void setTBSessao(TBSessao tBSessao) {
        this.tBSessao = tBSessao;
    }

    public TBModeloGenerico getMapeia() {
        return mapeia;
    }

    public TBModeloExclusivo mapeia(TBModeloGenerico tBModeloGenerico) {
        this.mapeia = tBModeloGenerico;
        return this;
    }

    public void setMapeia(TBModeloGenerico tBModeloGenerico) {
        this.mapeia = tBModeloGenerico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TBModeloExclusivo tBModeloExclusivo = (TBModeloExclusivo) o;
        if (tBModeloExclusivo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tBModeloExclusivo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TBModeloExclusivo{" +
            "id=" + id +
            ", idmodeloexclusivo='" + idmodeloexclusivo + "'" +
            ", idusuario='" + idusuario + "'" +
            ", idmodelogenerico='" + idmodelogenerico + "'" +
            ", mapeamento='" + mapeamento + "'" +
            '}';
    }
}
