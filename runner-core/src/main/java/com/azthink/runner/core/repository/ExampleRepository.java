package com.azthink.runner.core.repository;

import com.azthink.runner.core.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Example, Long>{
}
