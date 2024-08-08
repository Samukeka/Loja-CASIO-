package com.don.corretora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.corretora.model.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
