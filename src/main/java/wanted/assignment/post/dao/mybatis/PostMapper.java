package wanted.assignment.post.dao.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import wanted.assignment.common.basewrapper.BaseEntity;
import wanted.assignment.post.dao.domain.Post;

@Mapper
public interface PostMapper {

	void save(BaseEntity<Post> post);

	Optional<Post> findById(Long id);

	List<Post> findAll(Long id, Integer limit);

	void update(@Param("id") Long id, @Param("updateParam") BaseEntity<Post> post);

	void delete(Long id);
}
