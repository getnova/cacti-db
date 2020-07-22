package net.getnova.backend.cacti.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.getnova.backend.json.JsonTransient;
import net.getnova.backend.sql.model.TableModelId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_cactus_acquisition")
public final class CactusAcquisition extends TableModelId {

    @MapsId
    @OneToOne
    @JsonTransient
    private Cactus cactus;

    @Column(name = "timestamp", nullable = true, updatable = true)
    private OffsetDateTime timestamp;

    @Column(name = "age", nullable = true, updatable = true)
    private Duration age;

    @Column(name = "place", nullable = true, updatable = true, length = 512)
    private String place;

    @Column(name = "plant_type", nullable = true, updatable = true, length = 512)
    private String plantType;
}
