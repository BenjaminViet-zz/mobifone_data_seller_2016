package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/30/16
 * Time: 22:23
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_PACKAGE_DATA")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class PackageDataEntity {
    private Long packageDataId;
    private String name;
    private Double value;
    private String volume;
    private Integer duration;
    private String durationText;
    private Integer numberOfExtend;
    private String tk;
    private List<PackageDataCodeGenEntity> packageDataCodeGenList;

    @Column(name = "PACKAGEDATAID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_PACKAGE_DATA_SEQ")
    @SequenceGenerator(name="MOBI_DATA_PACKAGE_DATA_SEQ", sequenceName="MOBI_DATA_PACKAGE_DATA_SEQ", allocationSize=1)
    public Long getPackageDataId() {
        return packageDataId;
    }

    public void setPackageDataId(Long packageDataId) {
        this.packageDataId = packageDataId;
    }

    @Column(name = "NAME")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "VALUE")
    @Basic
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Column(name = "VOLUME")
    @Basic
    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Column(name = "DURATION")
    @Basic
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Column(name = "DURATIONTEXT")
    @Basic
    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    @Column(name = "NUMBEROFEXTEND")
    @Basic
    public Integer getNumberOfExtend() {
        return numberOfExtend;
    }

    public void setNumberOfExtend(Integer numberOfExtend) {
        this.numberOfExtend = numberOfExtend;
    }

    @Column(name = "TK")
    @Basic
    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    @OneToMany(cascade=CascadeType.REMOVE, orphanRemoval = false)
    @JoinColumn(name="PACKAGEDATAID", insertable = false, updatable = false)
    public List<PackageDataCodeGenEntity> getPackageDataCodeGenList() {
        return packageDataCodeGenList;
    }

    public void setPackageDataCodeGenList(List<PackageDataCodeGenEntity> packageDataCodeGenList) {
        this.packageDataCodeGenList = packageDataCodeGenList;
    }

    @Override
    public int hashCode() {
        int result = packageDataId != null ? packageDataId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (numberOfExtend != null ? numberOfExtend.hashCode() : 0);
        result = 31 * result + (tk != null ? tk.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageDataEntity that = (PackageDataEntity) o;

        if (packageDataId != null ? !packageDataId.equals(that.packageDataId) : that.packageDataId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (numberOfExtend != null ? !numberOfExtend.equals(that.numberOfExtend) : that.numberOfExtend != null) return false;
        if (tk != null ? !tk.equals(that.tk) : that.tk != null) return false;

        return true;
    }
}
