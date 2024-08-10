package com.keyin.binary_search_tree.Repository;

import com.keyin.binary_search_tree.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
}