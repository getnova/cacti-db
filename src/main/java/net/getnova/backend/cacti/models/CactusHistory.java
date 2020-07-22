package net.getnova.backend.cacti.models;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.getnova.backend.json.JsonBuilder;
import net.getnova.backend.json.JsonSerializable;
import net.getnova.backend.sql.model.TableModelAutoId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_cactus_history")
public final class CactusHistory extends TableModelAutoId implements JsonSerializable {

    @ManyToOne
    @JoinColumn(name = "cactus_id", nullable = false, updatable = false)
    private Cactus cactus;

    @Column(name = "timestamp", nullable = false, updatable = true)
    private OffsetDateTime timestamp;

    @Column(name = "content", nullable = false, updatable = true, length = 2048)
    private String content;

    @Override
    public JsonElement serialize() {
        return JsonBuilder.create("id", this.getId())
                .add("cactusId", this.getCactus().getId())
                .add("timestamp", this.getTimestamp())
                .add("content", this.getContent())
                .build();
    }
}
