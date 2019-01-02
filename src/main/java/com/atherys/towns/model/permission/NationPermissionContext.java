package com.atherys.towns.model.permission;

import com.atherys.towns.model.Nation;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "nation_permission_context")
public class NationPermissionContext extends AbstractPermissionContext<Nation> {

    @OneToOne
    private Nation nation;

    @Override
    public Nation getHolder() {
        return nation;
    }
}
