package com.solo.LoLStrategy.common.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository<T extends Board> extends JpaRepository<T,Integer>{
	
	

}
