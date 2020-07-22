package net.getnova.backend.cacti.models;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.getnova.backend.json.JsonBuilder;
import net.getnova.backend.json.JsonSerializable;
import net.getnova.backend.sql.model.TableModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_care_group")
public final class CareGroup extends TableModel implements JsonSerializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 2)
    private String id;

    @Column(name = "name", nullable = false, updatable = true, length = 128)
    private String name;

    @Column(name = "home", nullable = false, updatable = true, length = 512)
    private String home;

    @Column(name = "soil", nullable = false, updatable = true, length = 512)
    private String soil;

    @Column(name = "grow_time_light", nullable = true, updatable = true, length = 512)
    private String growTimeLight;

    @Column(name = "grow_time_air", nullable = true, updatable = true, length = 512)
    private String growTimeAir;

    @Column(name = "grow_time_temperature", nullable = true, updatable = true, length = 512)
    private String growTimeTemperature;

    @Column(name = "grow_time_humidity", nullable = true, updatable = true, length = 512)
    private String growTimeHumidity;

    @Column(name = "grow_time_other", nullable = true, updatable = true, length = 512)
    private String growTimeOther;

    @Column(name = "rest_time_light", nullable = true, updatable = true, length = 512)
    private String restTimeLight;

    @Column(name = "rest_time_air", nullable = true, updatable = true, length = 512)
    private String restTimeAir;

    @Column(name = "rest_time_temperature", nullable = true, updatable = true, length = 512)
    private String restTimeTemperature;

    @Column(name = "rest_time_humidity", nullable = true, updatable = true, length = 512)
    private String restTimeHumidity;

    @Column(name = "rest_time_other", nullable = true, updatable = true, length = 512)
    private String restTimeOther;

    @Override
    public JsonObject serialize() {
        return JsonBuilder.create("id", this.getId())
                .add("name", this.getName())
                .build();
    }
}
