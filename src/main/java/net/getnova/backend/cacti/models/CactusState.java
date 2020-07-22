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
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_cactus_state")
public final class CactusState extends TableModelId {

    @MapsId
    @OneToOne
    @JsonTransient
    private Cactus cactus;

    @Column(name = "no_longer_in_possession_timestamp", nullable = true, updatable = true)
    private OffsetDateTime noLongerInPossessionTimestamp;

    @Column(name = "no_longer_in_possession_reason", nullable = true, updatable = true)
    private String noLongerInPossessionReason;

    @Column(name = "vitality", nullable = true, updatable = true, length = 128)
    private String vitality;
}
