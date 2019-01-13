package com.atherys.towns.model;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.towns.api.Subject;
import com.atherys.towns.api.Actor;
import com.atherys.towns.persistence.converter.TextConverter;
import org.hibernate.annotations.GenericGenerator;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Nation implements SpongeIdentifiable, Subject, Actor {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID uuid;

    @Convert(converter = TextConverter.class)
    private Text name;

    @Convert(converter = TextConverter.class)
    private Text description;

    @OneToMany(mappedBy = "nation")
    private Set<Town> towns = new HashSet<>();

    @OneToOne
    private Resident leader;

    @OneToOne
    private Town capital;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "nation_allies",
            joinColumns = @JoinColumn(name = "nation_id"),
            inverseJoinColumns = @JoinColumn(name = "ally_nation_id")
    )
    private Set<Nation> allies = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "nation_enemies",
            joinColumns = @JoinColumn(name = "nation_id"),
            inverseJoinColumns = @JoinColumn(name = "enemy_nation_id")
    )
    private Set<Nation> enemies = new HashSet<>();

    public Nation() {
    }

    public Nation(UUID uuid) {
        this.uuid = uuid;
    }

    @Nonnull
    @Override
    public UUID getId() {
        return uuid;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public Text getDescription() {
        return description;
    }

    public void setDescription(Text description) {
        this.description = description;
    }

    public Set<Town> getTowns() {
        return towns;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }

    public Resident getLeader() {
        return leader;
    }

    public void setLeader(Resident leader) {
        this.leader = leader;
    }

    public Town getCapital() {
        return capital;
    }

    public void setCapital(Town capital) {
        this.capital = capital;
    }

    public Set<Nation> getAllies() {
        return allies;
    }

    public void setAllies(Set<Nation> allies) {
        this.allies = allies;
    }

    public Set<Nation> getEnemies() {
        return enemies;
    }

    public void setEnemies(Set<Nation> enemies) {
        this.enemies = enemies;
    }

    @Override
    public boolean hasParent() {
        return false;
    }

    @Override
    public Subject getParent() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nation nation = (Nation) o;
        return uuid.equals(nation.uuid) &&
                name.equals(nation.name) &&
                description.equals(nation.description) &&
                towns.equals(nation.towns) &&
                leader.equals(nation.leader) &&
                capital.equals(nation.capital) &&
                allies.equals(nation.allies) &&
                enemies.equals(nation.enemies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, description, towns, leader, capital, allies, enemies);
    }
}
