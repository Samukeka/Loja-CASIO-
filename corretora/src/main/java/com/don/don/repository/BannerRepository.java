package com.don.don.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.don.don.model.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
