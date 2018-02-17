package org.sjlchatham.sjlcweb.data;

import org.sjlchatham.sjlcweb.models.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PostDao extends PagingAndSortingRepository<Post, Integer> {
}
