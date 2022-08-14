package com.gmarquezp.back.springbootbackclientes.models.dao;

import com.gmarquezp.back.springbootbackclientes.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRegionDao extends JpaRepository<Region, Long> {

    @Query("select r from Region r")
    public List<Region> findAllRegiones();
}
